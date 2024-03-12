#include <ArduinoJson.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <time.h>
#include "DHT.h"
#include <pm2008_i2c.h>

// DHT 센서 설정
#define DHTPIN 4     // DHT 센서가 연결된 핀 번호
#define DHTTYPE DHT11   // DHT 11 
DHT dht(DHTPIN, DHTTYPE);

PM2008_I2C pm2008_i2c;

const char* ssid = "CAMPUS_G";
const char* password =  "SMHRDg7777";
const char* ntpServer = "pool.ntp.org";
const long gmtOffset_sec = 9 * 3600; // 9 hours
const int daylightOffset_sec = 0; // No daylight saving time

void printLocalTime() {
  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return;
  }
  strftime(timestamp, sizeof(timestamp), "%Y-%m-%d %H:%M:%S", &timeinfo);
}

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("WiFi에 연결 중...");
  }

  Serial.println("WiFi에 연결되었습니다.");
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer); // Initialize and get the time
  printLocalTime();
}

void loop() {
  if(WiFi.status()== WL_CONNECTED){
    HTTPClient http;
    http.begin("https://www.renewen.kr/api/was/sensing"); 
    http.addHeader("Content-Type", "application/json");

    // 데이터 읽기
    float h = dht.readHumidity();
    float t = dht.readTemperature();

    // 미세먼지 센서 데이터 읽기
     uint8_t ret = pm2008_i2c.read();
    float pm10;
    if (ret == 0) {
      pm10 = pm2008_i2c.pm10_grimm;
    }
    
    Serial.print(pm10);


    // 시간 읽기
    struct tm timeinfo;
    getLocalTime(&timeinfo);
    char timestamp[24];
    strftime(timestamp, sizeof(timestamp), "%Y-%m-%d %H:%M:%S", &timeinfo);

    // JSON 버퍼 생성
    DynamicJsonDocument jsonBuffer(1024);
    JsonObject root = jsonBuffer.to<JsonObject>();
    root["plantLinkKey"] = "testKey2";

   
    JsonArray sensing = root.createNestedArray("sensing");

    JsonObject sensor1 = sensing.createNestedObject();
    sensor1["sensorId"] = "DHT11_TEM";
    sensor1["sensorSerialNum"] = "6db13184-91d8-48ed-ba84-9ec460f9f70f";
    sensor1["measureValue"] = t;
    sensor1["measureDesc"] = "test 발전소 온도 측정";
    sensor1["createdAt"] = timestamp; // "current_date"를 현재 날짜/시간으로 설정하세요.

    JsonObject sensor2 = sensing.createNestedObject();
    sensor2["sensorId"] = "DHT11_HUM";
    sensor2["sensorSerialNum"] = "77f85b14-c913-4fb2-a87e-6c37ac46339a";
    sensor2["measureValue"] = h;
    sensor2["measureDesc"] = "test 발전소 습도 측정";
    sensor2["createdAt"] = timestamp; // "current_date"를 현재 날짜/시간으로 설정하세요.

    JsonObject sensor3 = sensing.createNestedObject();
    sensor3["sensorId"] = "PM2008M";
    sensor3["sensorSerialNum"] = "8f22d13q-5d84-122d-z158-3d1f5d6781df"; 
    sensor3["measureValue"] = pm10;
    sensor3["measureDesc"] = "test 발전소 PM10 미세먼지 측정";
    sensor3["createdAt"] = timestamp; 

    // JSON 객체를 문자열로 변환하여 postData에 저장
    String postData;
    serializeJson(root, postData);

    int httpResponseCode = http.POST(postData);

    if(httpResponseCode>0){
      String response = http.getString();
      Serial.println(httpResponseCode);   
      Serial.println(response);           
    }
    else{
      Serial.print("Error on sending POST: ");
      Serial.println(httpResponseCode);
    }
  
    http.end();
  } 
  else{
    Serial.println("WiFi 연결이 끊어졌습니다.");
  }
  delay(1000 * 6000);  
}