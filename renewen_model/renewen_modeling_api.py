#라이브러리
#requirements.txt

from flask import Flask, jsonify, request
from flask_mysqldb import MySQL
from flask_cors import CORS
import pymysql

from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import LabelEncoder

import numpy as np
import pandas as pd
import os
from datetime import datetime, timedelta
import json

import torch
import torch.nn as nn
from torch.utils.data import Dataset, DataLoader

app = Flask(__name__)
CORS(app)

app.secret_key = 'renewen'
app.config['MYSQL_HOST'] = '****'
app.config['MYSQL_PORT'] = 0000
app.config['MYSQL_USER'] = '****'
app.config['MYSQL_PASSWORD'] = '****'
app.config['MYSQL_DB'] = '****'

mysql = MySQL(app)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # spring 서버에서 넘겨받을 데이터 처리
        data = request.get_json()
        plant_nos = data.get('plant_no', [])
        st_date = data.get('st_date')
        
        #요청인자로 넘겨받은 plant_no당 한번씩 예측 실행
        for plant_no in plant_nos:

            # st_date를 datetime 형식으로 변환
            st_date = datetime.strptime(st_date, '%Y%m%d%H')
            date = []
            for i in range(48):
                current_date = st_date - timedelta(hours=i+1)
                date.append(current_date)

            #스프링 서버에서 넘겨받은 기준시간으로 예측인자 수집 기준시간 설정 ( ex. 2024-01-03 의 예측값 요청이 들어오면 48시간 전인 2024-01-01 00시를 예측 기준시간으로 설정)

            # date_list을 사용하여 데이터프레임 생성
            df = pd.DataFrame(columns=['date'], data=date)

            # 데이터프레임을 date 컬럼 기준으로 오름차순 정렬
            df.sort_values(by='date', inplace=True)

            # 인덱스 초기화
            df.reset_index(drop=True, inplace=True)

            #df.date[0] # 예측 기준시간

            # 발전소 식별번호로 generate_cell테이블에서 cell_volume의 총 합 구해서 변수에 담기
            with app.app_context():
                cur = mysql.connection.cursor()
                cur.execute(f"SELECT cell_volume FROM generate_cell WHERE plant_no = {plant_no} ")
                result = cur.fetchall()
                
            plant_volume = sum(row[0] for row in result)


            # weather_api 데이터 추출 + plant_volume 하나의 데이터 프레임으로 만들기

            with app.app_context():
                # df.date[0]에 해당하는 날짜 계산
                reference_date = df['date'][0]

                for weather_type in ['CA', 'SI', 'PA', 'WD', 'WS']:
                    cur = mysql.connection.cursor()
                    cur.execute("SELECT created_at, weather_type, weather_value FROM weather_api WHERE weather_type = %s AND created_at >= %s ORDER BY created_at LIMIT 48", (weather_type, reference_date))
                    data = cur.fetchall()
                    cur.close()

                    # 데이터프레임으로 변환
                    columns = ['created_at', 'weather_type', 'weather_value']
                    globals()[weather_type] = pd.DataFrame(data, columns=columns)

            # CA, SI, PA, WD, WS 데이터프레임을 시간을 기준으로 합치기
            merged_df = CA.merge(SI, on='created_at', how='outer', suffixes=('_CA', '_SI')) \
                        .merge(PA, on='created_at', how='outer', suffixes=('_SI', '_PA')) \
                        .merge(WD, on='created_at', how='outer', suffixes=('_PA', '_WD')) \
                        .merge(WS, on='created_at', how='outer', suffixes=('_WD', '_WS'))

            merged_df = merged_df.filter(regex='^(?!.*weather_type)')

            new_column_names = {
                'created_at': 'date',
                'weather_value_CA': 'CA',
                'weather_value_SI': 'SI',
                'weather_value_PA': 'PA',
                'weather_value_WD': 'WD',
                'weather_value': 'WS'
            }

            merged_df.rename(columns=new_column_names, inplace=True)

            merged_df['plant_volume'] = plant_volume

            #merged_df.head()


            # plant_sensing_data 추출해서 merged_df에 합치기
            with app.app_context():
                # df.date[0]에 해당하는 날짜 계산
                reference_date = df['date'][0]

                for sensor_id in ['DHT11_TEM', 'DHT11_HUM', 'PM2008M']:
                    cur = mysql.connection.cursor()
                    cur.execute("SELECT created_at, sensor_id, measure_value FROM plant_sensing_data WHERE sensor_id = %s AND created_at >= %s AND plant_no = %s ORDER BY created_at LIMIT 48", (sensor_id, reference_date, plant_no))
                    data = cur.fetchall()
                    cur.close()

                    # 데이터프레임으로 변환
                    columns = ['created_at', 'sensor_id', 'measure_value']
                    globals()[sensor_id] = pd.DataFrame(data, columns=columns)
                    
            # TA, HM, PM10 데이터프레임을 시간을 기준으로 합치기
            merged_df_sensor = DHT11_TEM.merge(DHT11_HUM, on='created_at', how='outer', suffixes=('_DHT11_TEM', 'DHT11_HUM')) \
                        .merge(PM2008M, on='created_at', how='outer', suffixes=('_SI', '_PM10')) 

            merged_df_sensor = merged_df_sensor.filter(regex='^(?!.*sensor_id)')

            new_column_names = {
                'created_at': 'date',
                'measure_value_DHT11_TEM': 'TA',
                'measure_valueDHT11_HUM': 'HM',
                'measure_value': 'PM10',
            }

            merged_df_sensor.rename(columns=new_column_names, inplace=True)

            #merged_df_sensor


            # merged_df와 merged_df_sensor를 date를 기준으로 합치기
            merged_df = pd.merge(merged_df, merged_df_sensor, on='date', how='inner')

            # 열 순서 변경
            desired_columns = ['date', 'TA', 'WS', 'WD', 'HM', 'PA', 'SI', 'CA', 'PM10', 'plant_volume']
            merged_df = merged_df[desired_columns]

            #merged_df.head()

            # 하이퍼파라미터세팅
            input_size = 9
            hidden_size = 64
            num_layers = 2
            output_size = 1
            num_epochs = 30
            window_size = 48
            batch_size = 64
            learning_rate = 0.001

            # merged_df를 숫자형 데이터로 변환
            merged_df[['TA', 'WS', 'WD', 'HM', 'PA', 'SI', 'CA', 'PM10', 'plant_volume']] = merged_df[['TA', 'WS', 'WD', 'HM', 'PA', 'SI', 'CA', 'PM10', 'plant_volume']].astype(float)

            # 예측에 사용할 features 선택
            features = ['TA', 'WS', 'WD', 'HM', 'PA', 'SI', 'CA', 'PM10', 'plant_volume']

            # merged_df를 숫자형 데이터로 변환
            merged_df[features] = merged_df[features].astype(float)

            # 새로운 scaler 정의
            new_scaler = MinMaxScaler()

            # 데이터를 새로운 scaler를 이용하여 변환
            input_data = new_scaler.fit_transform(merged_df[features].values)

            # cuda설정
            device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

            input_data = torch.tensor(input_data.reshape(1, window_size, -1), dtype=torch.float).to(device)


            class LSTM(nn.Module):
                def __init__(self, input_size, hidden_size, num_layers, output_size):
                    super(LSTM, self).__init__()

                    self.hidden_size = hidden_size
                    self.num_layers = num_layers

                    self.lstm = nn.LSTM(input_size, hidden_size, num_layers, batch_first=True)
                    self.fc = nn.Linear(hidden_size, output_size)

                def forward(self, x):
                    h0 = torch.zeros(self.num_layers, x.size(0), self.hidden_size).to(x.device) 
                    c0 = torch.zeros(self.num_layers, x.size(0), self.hidden_size).to(x.device)

                    out, _ = self.lstm(x, (h0, c0))
                    out = self.fc(out[:, -1, :])

                    return out

            # 모델 불러오기
            model_path = 'C:/Users/zodlt/OneDrive/바탕 화면/renewen_predict/model.pth'
            model = LSTM(input_size, hidden_size, num_layers, output_size)
            model.load_state_dict(torch.load(model_path))
            
            # 예측 시간 이전 48시간의 데이터를 사용하여 입력 데이터 구성
            input_data = merged_df[features].values
            input_data = new_scaler.transform(input_data)

            # input과 학습데이터의 shape 매치
            input_data = torch.tensor(input_data.reshape(1, window_size, -1), dtype=torch.float).to(device)

            # 예측기준일 24시간 설정
            model.eval()
            with torch.no_grad():
                predicted_powers = []
                for _ in range(24):
                    predicted_power = model(input_data).item()
                    predicted_powers.append(predicted_power)
                    # 현재 예측값을 기반으로 다음 시간 스텝을 예측하기 위해 input_data를 업데이트
                    input_data = torch.cat((input_data[:, 1:, :], torch.tensor([[[predicted_power]*len(features)]], dtype=torch.float)), dim=1)
                    

            predicted_powers = [round(value * 15000, 2) if value * 15000 > 0 else 0 for value in predicted_powers]

            predicted_powers = predicted_powers[19:] + predicted_powers[:19]
            
            pre_date = []
            for i in range(24):
                current_date = st_date + timedelta(hours=i)
                pre_date.append(current_date)

            

            # 두 개의 리스트를 데이터프레임으로 합치기
            pre_df = pd.DataFrame({'created_at': pre_date, 'predict_gen_elec': predicted_powers})

            #pre_df.info()
            
            

            # MySQL 연결 설정
            connection = pymysql.connect(
                                host='****',
                                port=0000,
                                user='****',
                                password='****',
                                database='****'
                            )


            cursor = connection.cursor()

            #예측값 DB에 저장하기
            for i in range(len(pre_date)):
                stn_no = 156
                predict_gen_elec = pre_df.predict_gen_elec[i]
                created_at = pre_df.created_at[i]
                            

                sql = "INSERT INTO predicted_gen_elec(plant_no, stn_no, predict_gen_elec, created_at) VALUES (%s, %s, %s, %s);"
                values = (plant_no, stn_no, predict_gen_elec, created_at)

                cursor.execute(sql, values)

            # st_date를 다시 문자열로 초기화
            st_date = st_date.strftime('%Y%m%d%H')

            
            # 변경사항 저장
            connection.commit()
            # 연결 종료
            connection.close()


    except Exception as e:
        print(f"Error during prediction: {e}")
        return "fail"

    return "success"

if __name__ == '__main__':
    app.run(debug=True)
