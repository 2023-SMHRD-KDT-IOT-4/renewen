-- 사전 정의 ===============================================================================================================

-- 최고 관리자, 일반사용자, 발전소 관리자, 발전사 소유인, 발전소 설치업체, 발전소 준비인
INSERT INTO user_auth (auth_id, auth_nm, use_yn) VALUES ('ROLE_ADMIN', '최고 관리자', 'Y');
INSERT INTO user_auth (auth_id, auth_nm, use_yn) VALUES ('ROLE_USER', '일반 사용자', 'Y');
INSERT INTO user_auth (auth_id, auth_nm, use_yn) VALUES ('ROLE_PLANT_ADMIN', '발전소 관리자', 'Y');
commit;

-- 센서　데이터　정의
INSERT INTO sensor_info(sensor_id, sensor_type_cd, sensor_type_nm, sensor_desc, measure_unit, use_yn)
VALUES ('GY-906', 'temperature', '온도 측정', '비접촉식 적외선 온도센서 모듈', '°C', 'Y');
INSERT INTO sensor_info(sensor_id, sensor_type_cd, sensor_type_nm, sensor_desc, measure_unit, use_yn)
VALUES ('DHT11_TEM', 'temperature', '온도 측정', '정교한 온도 측정', '°C', 'Y');
INSERT INTO sensor_info(sensor_id, sensor_type_cd, sensor_type_nm, sensor_desc, measure_unit, use_yn)
VALUES ('DHT11_HUM', 'humidity', '습도 측정', '정교한 습도 측정', '%', 'Y');
commit;
-- =======================================================================================================================