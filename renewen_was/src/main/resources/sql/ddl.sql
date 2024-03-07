DROP TABLE if exists weather_api;
DROP TABLE if exists predicted_gen_elec;
DROP TABLE if exists cell_generated_elec;
DROP TABLE if exists cell_shot_img;
DROP TABLE if exists generate_cell;

DROP TABLE if exists plant_sensing_data;
DROP TABLE if exists cloud_shot_img;
DROP TABLE if exists power_plant;

DROP TABLE if exists user_log;
DROP TABLE if exists user_info;
DROP TABLE if exists user_auth;
DROP TABLE if exists sensor_info;

-- ===============================================================================================================

CREATE TABLE user_auth
(
    `auth_id`  VARCHAR(30)    NOT NULL    COMMENT '사용자 권한 id', 
    `auth_nm`  VARCHAR(30)    NOT NULL    COMMENT '사용자 권한 이름', 
    `use_yn`   CHAR(1)        NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
     PRIMARY KEY (auth_id)
);

ALTER TABLE user_auth COMMENT '사용자 권한 정의 테이블';

-- ===============================================================================================================

CREATE TABLE user_info
(
    `user_id`          VARCHAR(30)     NOT NULL    COMMENT '사용자 id', 
    `user_pw`          VARCHAR(100)    NOT NULL    COMMENT '사용자 비밀번호', 
    `auth_id`          VARCHAR(30)     NOT NULL    COMMENT '사용자 권한 id', 
    `user_email`       VARCHAR(50)     NOT NULL    COMMENT '사용자 이메일', 
    `user_name`        VARCHAR(20)     NOT NULL    COMMENT '사용자 이름', 
    `user_tel`         VARCHAR(20)     NOT NULL    COMMENT '사용자 연락처', 
    `user_company`     VARCHAR(30)     NULL        COMMENT '사용자 회사', 
    `manage_position`  VARCHAR(20)     NULL        COMMENT '담당 직위',
    `manage_task`      VARCHAR(50)     NULL        COMMENT '담당 업무명', 
    `use_yn`           CHAR(1)         NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `joined_at`        TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '가입 시간', 
    CONSTRAINT PK_user PRIMARY KEY (user_id)
);

ALTER TABLE user_info COMMENT '가입한 사용자 저장 테이블';

ALTER TABLE user_info
    ADD CONSTRAINT FK_user_info_auth_id_user_auth_auth_id FOREIGN KEY (auth_id)
        REFERENCES user_auth (auth_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
        
-- ===============================================================================================================


CREATE TABLE user_log
(
    `log_id`       INT UNSIGNED    NOT NULL    AUTO_INCREMENT COMMENT '사용자 로그id', 
    `user_id`      VARCHAR(30)     NOT NULL    COMMENT '사용자 id', 
    `connect_ip`   VARCHAR(20)     NOT NULL    COMMENT '접속 ip', 
    `access_menu`  VARCHAR(30)     NOT NULL    COMMENT '접근 메뉴', 
    `log_content`  VARCHAR(100)    NOT NULL    COMMENT '로그 내용',
    `log_type`     VARCHAR(10)     NULL        COMMENT '로그 종류. info, warn, danger', 
    `popup_yn`     CHAR(1)         NOT NULL    DEFAULT 'N' COMMENT '유저 노출유무',    
    `created_at`   TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '로그 시간', 
     PRIMARY KEY (log_id)
);

ALTER TABLE user_log COMMENT '사용자 로그 저장 테이블';

ALTER TABLE user_log
    ADD CONSTRAINT FK_user_log_user_id_user_info_user_id FOREIGN KEY (user_id)
        REFERENCES user_info (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;
        
ALTER TABLE user_log AUTO_INCREMENT=150001;        

-- ===============================================================================================================

CREATE TABLE sensor_info
(
    `sensor_id`       VARCHAR(50)     NOT NULL    COMMENT '센서id(모델명)', 
    `sensor_type_cd`  VARCHAR(30)     NOT NULL    COMMENT '센서 타입 코드', 
    `sensor_type_nm`  VARCHAR(30)     NOT NULL    COMMENT '센서 타입 이름', 
    `sensor_desc`     VARCHAR(100)    NULL        COMMENT '센서 설명', 
    `measure_unit`    VARCHAR(20)     NOT NULL    COMMENT '센서 측정단위', 
    `use_yn`          CHAR(1)         NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
     PRIMARY KEY (sensor_id)
);

ALTER TABLE sensor_info COMMENT '서비스 센서 정의 테이블';

-- ===============================================================================================================

CREATE TABLE power_plant
(
    `plant_no`        INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '발전소 식별번호', 
    `user_id`         VARCHAR(30)      NOT NULL    COMMENT '사용자 아이디', 
    `plant_name`      VARCHAR(30)      NOT NULL    COMMENT '발전소 이름', 
    `plant_addr`     VARCHAR(100)     NOT NULL    COMMENT '발전소 주소(기본)', 
    `plant_addr2`     VARCHAR(50)      NOT NULL    COMMENT '발전소 주소(상세)', 
    `br_number`       VARCHAR(30)      NOT NULL    COMMENT '사업자등록번호', 
    `plant_link_key`  VARCHAR(50)      NULL        COMMENT '발전소 연동키', 
    `grant_yn`        CHAR(1)          NOT NULL    DEFAULT 'N' COMMENT '승인여부', 
    `use_yn`          CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`      TIMESTAMP        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '연동 시간', 
    `stn_no`          VARCHAR(10)      NOT NULL    COMMENT '관측소 지점번호', 
    `latitude`        DECIMAL(11, 8)    NOT NULL    COMMENT '위도', 
    `longitude`       DECIMAL(11, 8)    NOT NULL    COMMENT '경도',
    `sort_no`         INT               NOT NULL    COMMENT '정렬순서', 
    CONSTRAINT PK_power_plant PRIMARY KEY (plant_no)
);

ALTER TABLE power_plant COMMENT '연동 발전소 정보 저장 테이블';

-- Foreign Key 설정 SQL - power_plant(user_id) -> user_info(user_id)
ALTER TABLE power_plant
    ADD CONSTRAINT FK_power_plant_user_id_user_info_user_id FOREIGN KEY (user_id)
        REFERENCES user_info (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE power_plant AUTO_INCREMENT=250001;
        
-- ===============================================================================================================

CREATE TABLE plant_sensing_data
(
    `sd_no`              INT UNSIGNED      NOT NULL    AUTO_INCREMENT COMMENT '센싱데이터 식별번호', 
    `plant_no`           INT UNSIGNED      NOT NULL    COMMENT '발전소 식별번호', 
    `sensor_id`          VARCHAR(50)       NOT NULL    COMMENT '센서id', 
    `sensor_serial_num`  VARCHAR(50)       NOT NULL    COMMENT '센서 시리얼번호', 
    `measure_value`      DECIMAL(12, 2)    NOT NULL    COMMENT '센싱 측정 값', 
    `measure_desc`       VARCHAR(100)      NULL        COMMENT '센싱 측정 설명', 
    `use_yn`             CHAR(1)           NOT NULL    DEFAULT 'Y' COMMENT '사용여부',
    `created_at`         TIMESTAMP         NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '측정 시간',
    `cell_no`            INT UNSIGNED      NULL        COMMENT '발전셀 식별번호',
     PRIMARY KEY (sd_no)
) ;

ALTER TABLE plant_sensing_data COMMENT '발전소 센싱 데이터 저장 테이블';

-- Foreign Key 설정 SQL - plant_sensing_data(plant_no) -> power_plant(plant_no)
ALTER TABLE plant_sensing_data
    ADD CONSTRAINT FK_plant_sensing_data_plant_no_power_plant_plant_no FOREIGN KEY (plant_no)
        REFERENCES power_plant (plant_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 설정 SQL - plant_sensing_data(sensor_id) -> sensor_info(sensor_id)
ALTER TABLE plant_sensing_data
    ADD CONSTRAINT FK_plant_sensing_data_sensor_id_sensor_info_sensor_id FOREIGN KEY (sensor_id)
        REFERENCES sensor_info (sensor_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE plant_sensing_data AUTO_INCREMENT=350001;

-- ===============================================================================================================

CREATE TABLE cloud_shot_img
(
    `cs_no`               INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '구름 이미지 식별번호', 
    `plant_no`            INT UNSIGNED     NOT NULL    COMMENT '발전소 식별번호', 
    `cloud_img_desc`      VARCHAR(100)     NULL        COMMENT '촬영 이미지 설명', 
    `cloud_img_name`      VARCHAR(1200)    NOT NULL    COMMENT '이미지 업로드 이름', 
    `cloud_img_realname`  VARCHAR(1500)    NOT NULL    COMMENT '이미지 파일이름', 
    `cloud_img_size`      INT              NOT NULL    COMMENT '이미지 사이즈', 
    `cloud_img_ext`       VARCHAR(10)      NOT NULL    COMMENT '이미지 확장자', 
    `use_yn`              CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`          TIMESTAMP        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '촬영 시간', 
     PRIMARY KEY (cs_no)
);

ALTER TABLE cloud_shot_img COMMENT '발전소 구름형상 촬영 이미지 저장 테이블';

-- Foreign Key 설정 SQL - cloud_shot_img(plant_no) -> power_plant(plant_no)
ALTER TABLE cloud_shot_img
    ADD CONSTRAINT FK_cloud_shot_img_plant_no_power_plant_plant_no FOREIGN KEY (plant_no)
        REFERENCES power_plant (plant_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE cloud_shot_img AUTO_INCREMENT=450001;

-- ===============================================================================================================

CREATE TABLE generate_cell
(
    `cell_no`         INT UNSIGNED      NOT NULL    AUTO_INCREMENT COMMENT '발전셀 식별번호', 
    `plant_no`        INT UNSIGNED      NOT NULL    COMMENT '발전소 식별번호', 
    `cell_type`       VARCHAR(30)       NOT NULL    COMMENT '발전셀 타입', 
    `cell_serial_num` VARCHAR(50)       NOT NULL    COMMENT '발전셀 시리얼번호',
    `cell_volume`     DECIMAL(12, 2)    NOT NULL    COMMENT '발전셀 용량', 
    `cell_size_unit`  VARCHAR(10)       NOT NULL    COMMENT '셀 크기 단위', 
    `cell_width`      DECIMAL(12, 2)    NOT NULL    COMMENT '셀 크기(가로)', 
    `cell_height`     DECIMAL(12, 2)    NOT NULL    COMMENT '셀 크기(세로)', 
    `cell_depth`      DECIMAL(12, 2)    NOT NULL    COMMENT '셀 크기(높이)', 
    `use_yn`          CHAR(1)           NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`      TIMESTAMP         NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '연동시간', 
     PRIMARY KEY (cell_no)
);

ALTER TABLE generate_cell COMMENT '발전소 내 발전셀 정보 저장 테이블';

-- Foreign Key 설정 SQL - generate_cell(plant_no) -> power_plant(plant_no)
ALTER TABLE generate_cell
    ADD CONSTRAINT FK_generate_cell_plant_no_power_plant_plant_no FOREIGN KEY (plant_no)
        REFERENCES power_plant (plant_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE generate_cell AUTO_INCREMENT=550001;

-- ===============================================================================================================

CREATE TABLE cell_shot_img
(
    `cs_no`              INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '셀 이미지 식별번호', 
    `cell_no`            INT UNSIGNED     NOT NULL    COMMENT '발전셀 식별번호', 
    `cell_img_desc`      VARCHAR(100)     NULL        COMMENT '촬영 이미지 설명', 
    `cell_img_name`      VARCHAR(1200)    NOT NULL    COMMENT '이미지 업로드 이름', 
    `cell_img_realname`  VARCHAR(1500)    NOT NULL    COMMENT '이미지 파일이름', 
    `cell_img_size`      INT              NOT NULL    COMMENT '이미지 사이즈', 
    `cell_img_ext`       VARCHAR(10)      NOT NULL    COMMENT '셀 이미지 확장자', 
    `use_yn`             CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`         TIMESTAMP        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '촬영 시간', 
     PRIMARY KEY (cs_no)
);

ALTER TABLE cell_shot_img COMMENT '발전셀 촬영 이미지 저장 테이블';

-- Foreign Key 설정 SQL - cell_shot_img(cell_no) -> generate_cell(cell_no)
ALTER TABLE cell_shot_img
    ADD CONSTRAINT FK_cell_shot_img_cell_no_generate_cell_cell_no FOREIGN KEY (cell_no)
        REFERENCES generate_cell (cell_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE cell_shot_img AUTO_INCREMENT=650001;

-- ===============================================================================================================

CREATE TABLE cell_generated_elec
(
    `cell_gen_no`       INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '셀 발전량 식별번호', 
    `cell_no`           INT UNSIGNED     NOT NULL    COMMENT '발전셀 식별번호', 
    `gen_voltage`       INT              NOT NULL    COMMENT '발전 전압(V)', 
    `gen_elec_current`  DECIMAL(12,2)    NOT NULL    COMMENT '발전 전류(A)', 
    `use_yn`            CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`        TIMESTAMP        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '측정 시간', 
     PRIMARY KEY (cell_gen_no)
);

ALTER TABLE cell_generated_elec COMMENT '발전셀의 발전량 저장 테이블';

-- Foreign Key 설정 SQL - cell_generated_elec(cell_no) -> generate_cell(cell_no)
ALTER TABLE cell_generated_elec
    ADD CONSTRAINT FK_cell_generated_elec_cell_no_generate_cell_cell_no FOREIGN KEY (cell_no)
        REFERENCES generate_cell (cell_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE cell_generated_elec AUTO_INCREMENT=750001;

-- ===============================================================================================================

CREATE TABLE predicted_gen_elec
(
    `predict_no`        INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '예측 식별번호', 
    `plant_no`          INT UNSIGNED     NOT NULL    COMMENT '발전소 식별번호', 
    `stn_no`            VARCHAR(10)      NOT NULL    COMMENT '관측소 지점번호', 
    `predict_gen_elec`  DECIMAL(12,2)    NOT NULL    COMMENT '예측 발전량(W)', 
    `use_yn`            CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`        TIMESTAMP        NOT NULL    COMMENT '예측 시간',     
     PRIMARY KEY (predict_no)
);

ALTER TABLE predicted_gen_elec COMMENT '발전량(발전셀) 예측 결과 저장 테이블';

-- Foreign Key 설정 SQL - predicted_gen_elec(plant_no) -> power_plant(plant_no)
ALTER TABLE predicted_gen_elec
    ADD CONSTRAINT FK_predicted_gen_elec_plant_no_power_plant_plant_no FOREIGN KEY (plant_no)
        REFERENCES power_plant (plant_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE predicted_gen_elec AUTO_INCREMENT=850001;

-- ===============================================================================================================

CREATE TABLE weather_api
(
    `w_no`           INT UNSIGNED     NOT NULL    AUTO_INCREMENT COMMENT '관측 식별 번호', 
    `stn_no`         VARCHAR(10)      NOT NULL    COMMENT '관측소 지점번호', 
    `weather_type`   VARCHAR(20)      NOT NULL    COMMENT '기상인자 타입', 
    `weather_value`  DECIMAL(12,2)    NOT NULL    COMMENT '기상인자 측정값', 
    `use_yn`         CHAR(1)          NOT NULL    DEFAULT 'Y' COMMENT '사용여부', 
    `created_at`     TIMESTAMP        NOT NULL    COMMENT '측정 시간', 
     PRIMARY KEY (w_no)
);

ALTER TABLE weather_api COMMENT '기상청 api허브에서 받아온 값 저장 테이블';

ALTER TABLE weather_api AUTO_INCREMENT=950001;