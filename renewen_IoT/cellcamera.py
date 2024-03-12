import base64
import requests
from picamera import PiCamera
from time import sleep
from datetime import datetime, timedelta
import pytz

# 서울 시간대로 변경
seoul_timezone = pytz.timezone('Asia/Seoul')
current_time = datetime.now(seoul_timezone).strftime("%Y%m%d%H%M%S")

# 카메라 초기화
camera = PiCamera()

try:
    # 사진 찍기
    camera.start_preview()
    sleep(5)  # 카메라 준비 시간
    camera.capture('/home/tmdwo/Pictures/image.jpg')  # 이미지 저장 위치
    camera.stop_preview()
finally:
    camera.close()  # 카메라 리소스 정리

# 사진 읽기 및 base64 인코딩
with open("/home/tmdwo/Pictures/image.jpg", "rb") as image_file:
    encoded_string = base64.b64encode(image_file.read()).decode('utf-8')

# POST 요청 보내기
url = "https://www.renewen.kr/api/was/img/cells"  # 여기에 실제 서버 주소를 입력해주세요.
headers = {'Content-Type': 'application/json'}  # 헤더 정보
data = {'cells': [
    {"cellSerialNum": "cell02",
    "imgDesc": "testkey2+plant cell shot",
    "imgName": "cell_이미지2",
    "imgExt": "jpg",
    "createdAt": current_time,  # 변경된 현재 시간 적용
    'img': encoded_string}]}

response = requests.post(url, headers=headers, json=data, verify=False)

# 서버 응답 출력
print('response MSG : ', response.text)
print('response code : ', response.status_code)

 # 30분 대기
    time.sleep(30 * 60)  # 30분 대기 후 다음 반복 실행