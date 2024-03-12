#include <ArduinoJson.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <Wire.h>
#include <Adafruit_INA219.h>
#include <time.h>

Adafruit_INA219 ina219;

const char* ssid = "CAMPUS_G";
const char* password = "SMHRDg7777";

const char* ntpServer = "pool.ntp.org";
const long gmtOffset_sec = 9 * 3600; // 한국 시간대로 설정 (9시간)
const int daylightOffset_sec = 0; // 일광 절약 시간은 사용하지 않음

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  
  // NTP 서버 설정
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);

  ina219.begin();
}
struct tm timeinfo;
void loop() {
  float voltage = ina219.getBusVoltage_V();
  float elecCurrent = ina219.getCurrent_mA();
  // 전압과 전류 값을 시리얼 모니터에 출력
  Serial.print("Voltage: ");
  Serial.print(voltage);
  Serial.println(" V"); // Volts 단위로 출력

  Serial.print("Current: ");
  Serial.print(elecCurrent);
  Serial.println(" mA"); // miliAmperes 단위로 출력

  char current_date[24];
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return;
  }
  
  getLocalTime(&timeinfo);
  strftime(current_date, sizeof(current_date), "%Y-%m-%d %H:%M:%S", &timeinfo); // 현재 날짜와 시간을 문자열로 변환

  StaticJsonDocument<1024> doc;
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

  JsonObject elec1 = cell1.createNestedObject("elec");
  elec1["genVoltage"] = voltage;
  elec1["genElecCurrent"] = elecCurrent;
  elec1["createdAt"] = current_date;

  JsonObject cell2 = cells.createNestedObject();
  cell2["cellType"] = "solar";
  cell2["cellSerialNum"] = "cell02";
  cell2["cellVolume"] = 300;
  cell2["cellSizeUnit"] = "mm";
  cell2["cellWidth"] = 150;
  cell2["cellHeight"] = 450;
  cell2["cellDepth"] = 40;

  JsonObject elec2 = cell2.createNestedObject("elec");
  elec2["genVoltage"] = voltage; // 동일하거나 다른 센서 값을 사용할 수 있습니다.
  elec2["genElecCurrent"] = elecCurrent; // 동일하거나 다른 센서 값을 사용할 수 있습니다.
  elec2["createdAt"] = current_date;

  String jsonBuffer;
  serializeJson(doc, jsonBuffer);

  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    http.begin("https://www.renewen.kr/api/was/cells/elect"); // 실제 서버 주소로 변경해주세요.
    http.addHeader("Content-Type", "application/json");
    int httpResponseCode = http.POST(jsonBuffer);
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    http.end();
  } else {
    Serial.println("WiFi Disconnected");
  }

  delay(10000*3); // 데이터를 10초마다 보냅니다.
}