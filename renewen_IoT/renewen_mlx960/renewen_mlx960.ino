#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <Wire.h>
#include <Adafruit_MLX90614.h>
#include <time.h>

const char* ssid = "CAMPUS_G";
const char* password =  "SMHRDg7777";
const char* ntpServer = "pool.ntp.org";
const long gmtOffset_sec = 9 * 3600; // 9시간
const int daylightOffset_sec = 0; // 써머타임 없음

// 전역 변수로 timestamp 선언
char timestamp[32];

void printLocalTime() {
  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return;
  }
  strftime(timestamp, sizeof(timestamp), "%Y-%m-%d %H:%M:%S", &timeinfo);
}

Adafruit_MLX90614 mlx = Adafruit_MLX90614();

// SERVER_URL 선언
const char* SERVER_URL = "https://www.renewen.kr/api/was/cells/sensing";

void setup() {
  Serial.begin(115200);
  
  // Connect to WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  // WiFi 연결 후 시간 설정
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  mlx.begin();
}

void loop() {
  // 현재 시간 업데이트
  printLocalTime();
  
 // MLX90614 센서에서 온도 읽기
  float temperature1 = mlx.readObjectTempC();
  if (isnan(temperature1)) {  // temperature1이 nan일 경우 0.00으로 대체
    temperature1 = 0.00;
  }
  float temperature2 = mlx.readAmbientTempC();
  if (isnan(temperature2)) {  // temperature2가 nan일 경우 0.00으로 대체
    temperature2 = 0.00;
  }

  // Create JSON object
  StaticJsonDocument<512> doc;
  doc["plantLinkKey"] = "testKey2";

  JsonArray cells = doc.createNestedArray("cells");

  JsonObject cell1 = cells.createNestedObject();
  cell1["cellType"] = "solar";
  cell1["cellSerialNum"] = "cell01";
  cell1["cellVolume"] = 200;
  cell1["cellSizeUnit"] = "mm";
  cell1["cellWidth"] = 100;
  cell1["cellHeight"] = 200;
  cell1["cellDepth"] = 20;
  JsonObject sensor1 = cell1.createNestedObject("sensor");
  sensor1["sensorId"] = "GY-906";
  sensor1["sensorSerialNum"] = "77f85b14-c913-4fb2-a87e-6c37ac46339a";
  sensor1["measureValue"] = temperature1;
  sensor1["measureDesc"] = "발전셀1 온도 측정";
  sensor1["createdAt"] = timestamp;

  JsonObject cell2 = cells.createNestedObject();
  cell2["cellType"] = "solar";
  cell2["cellSerialNum"] = "cell02";
  cell2["cellVolume"] = 300;
  cell2["cellSizeUnit"] = "mm";
  cell2["cellWidth"] = 150;
  cell2["cellHeight"] = 450;
  cell2["cellDepth"] = 40;
  JsonObject sensor2 = cell2.createNestedObject("sensor");
  sensor2["sensorId"] = "GY-906";
  sensor2["sensorSerialNum"] = "a5404648-db83-11ee-b514-00e04c6800de";
  sensor2["measureValue"] = temperature2;
  sensor2["measureDesc"] = "발전셀2 온도 측정";
  sensor2["createdAt"] = timestamp;

  // Serialize JSON to buffer
  String jsonString;
  serializeJson(doc, jsonString);

  // Send HTTP POST request
  HTTPClient http;
  http.begin(SERVER_URL);
  http.addHeader("Content-Type", "application/json");
  int httpResponseCode = http.POST(jsonString);
  
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    String response = http.getString();
    Serial.println(response);
  } else {
    Serial.print("Error code: ");
    Serial.println(httpResponseCode);
  }

  http.end();

  Serial.print(temperature1);

  delay(60000 * 30); // Delay for 1 minute before sending next data
  
}