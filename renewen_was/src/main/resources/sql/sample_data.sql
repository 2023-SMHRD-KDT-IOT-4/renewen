-- test 계정
INSERT INTO user_info (user_id, user_pw, auth_id, user_email, user_name, user_tel)
VALUES ('test', SHA2('1234', 256), 'ROLE_PLANT_ADMIN','test@renewen.kr', 'test name', 'tel');
COMMIT;
-- =======================================================================================================================
-- test 계정 발전소
INSERT INTO power_plant 
	(user_id, plant_name, plant_addr, plant_addr2, br_number, 
    plant_link_key, stn_no, latitude, longitude, sort_no) 
VALUES 
	('test', '광주 쌍암동', '광주광역시 광산구 쌍암동', '12-1', '1234567899', 
	 'testKey', '156', 35.2191, 126.8477, 0);
 INSERT INTO power_plant 
	(user_id, plant_name, plant_addr, plant_addr2, br_number, 
    plant_link_key, stn_no, latitude, longitude, sort_no) 
VALUES 
	('test', '광주 운암동', '광주광역시 북구 운안동', '12-1', '1234567899', 
	 'testKey2', '156', 35.1748596, 126.87445, 1); 
 INSERT INTO power_plant 
	(user_id, plant_name, plant_addr, plant_addr2, br_number, 
    plant_link_key, stn_no, latitude, longitude, sort_no) 
VALUES 
	('test', '광주 금호동', '광주광역시 서구 금호1동', '12-1', '1234567899', 
	 'testKey3', '156', 35.1351904, 126.8571375, 2); 
INSERT INTO power_plant 
	(user_id, plant_name, plant_addr, plant_addr2, br_number, 
    plant_link_key, stn_no, latitude, longitude, sort_no) 
VALUES 
	('test', '나주 진포동 발전소', '전라남도 나주시 진포동', '12-1', '1234567899', 
	 'testKey4', '156', 34.9947, 126.6734, 3);
COMMIT;
-- =======================================================================================================================