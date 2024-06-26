# ☀️ Renewen (팀명: Taiyang)
![폼보드_승재팀_1](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/152847551/bbe237a6-9837-4de3-990e-01b49526349e)

<br>

## 😊 서비스 소개
* 서비스명:   AIoT, LSTM 모델을 활용한 태양광 발전량 예측
* 서비스 설명
  - LSTM 모델을 활용한 태양광 발전량 예측
  - 웹페이지로 실 발전량, 예상 발전량, 기상정보 등 발전소 관리에 용이한 정보 제공
  - IoT 기기로 패널 또는 셀 사진 및 구름 사진을 전송하고 웹페이지에서 확인

<br>

## 📅 프로젝트 기간
2024.02.26 ~ 2024.03.12 (3주)

<br>

## ⭐ 주요 기능
* <a href="https://github.com/2023-SMHRD-KDT-IOT-4/renewen/blob/66ee319944aecb7ecd85bf35abae2fa91476f21a/renewen_was/src/main/java/kr/smhrd/renewen/controller/UserController.java#L53C1-L124C1" target='_blank'> 로그인/로그아웃/회원가입/회원정보수정</a>
* <a href="https://github.com/2023-SMHRD-KDT-IOT-4/renewen/blob/0a65f3cf62fdb782183dee0e11bdb257b7bf2a3b/renewen_was/src/main/java/kr/smhrd/renewen/controller/PlantController.java#L50C1-L149C3" target='_blank'> 발전소 등록/수정/삭제</a>
* <a href="renewen_was/src/main/resources/static/js/renewen_map.js" target='_blank'> 발전소 위치 지도에서 확인</a> 
* <a href="renewen_was/src/main/resources/static/js/plant_weather.js" target='_blank'> 발전소별 날씨</a>
* <a href="renewen_was/src/main/java/kr/smhrd/renewen/controller/PlantRestController.java" target='_blank'> 발전소 현황(예상 발전량/실제 발전량)</a>
* <a href="https://github.com/2023-SMHRD-KDT-IOT-4/renewen/blob/91d87f7a1b9ce9be59e31a5344a99940783ed4d9/renewen_was/src/main/java/kr/smhrd/renewen/controller/APIController.java#L85C1-L246C3" target='_blank'> 셀 이미지 확인(셀 상태/센서 데이터)</a>
* <a href="https://github.com/2023-SMHRD-KDT-IOT-4/renewen/blob/360e0119cbe7c229119b6438b363bca8b9d2f34e/renewen_was/src/main/java/kr/smhrd/renewen/controller/APIController.java#L54C1-L83C3" target='_blank'> 구름형상 이미지 확인</a>
* <a href="renewen_model/renewen_LSTM_model.ipynb" target='_blank'> LSTM모델 소스코드 </a>
* <a href="renewen_model/renewen_modeling_api.py" target='_blank'> Modeling API 소스코드 </a>

<br>

## ⛏ 기술스택
<table>
  
![프로젝트소개_2](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/152847551/00c02317-4db0-4f58-83dc-67d2915ebfae)

  
![프로젝트소개_3](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/152847551/7370e679-f129-468c-906f-318a53cd03f8)

</table>


<br>

## ⚙ 시스템 아키텍처
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/29440ddb-3875-4c89-8563-42c775f37c36)
 
<br>

## 📌 회로도
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/e6047b17-1c9a-488a-908b-416965c57866)

<br>

## 📌 유스케이스 다이어그램
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/d634bf99-6dfd-40a0-8ce2-74e7aeb64997)
 

<br>

## 📌 서비스 흐름도
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/74bcd5ec-54db-44b5-aa7b-424c6a04528d)
 
<br>

## 📌 ER다이어그램
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/6c9750e2-7c52-434d-b434-0f538acea715)
 
<br>

## 📌 LSTM 모델 설계
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/fa6f4b72-c91c-4b0e-9f6f-6c1548cdae73)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/fdf05e00-85d2-4f5e-831b-6c46e69e44b2)

<br>

## 💻 화면 구성
### 메인화면
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/2e903b3e-eb1e-48b3-b6d7-6c3a844e921a)
<ul><li>Renewen 시스템 첫 화면구성</li></ul>


<br>

### 회원가입/로그인/로그아웃/회원정보수정
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/f3c4f3ea-0ef4-41a8-b9e5-2f07b763b2b2)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/19636118-99e5-4d1d-939a-11fbaf15d87e)
<ul><li>회원가입을 클릭하면 회원 가입할 수 있다.</li></ul>
<ul><li>로그인 후 renewen의 다양한 서비스를 사용할 수 있고, 브라우저 위치상 날씨 정보도 확인할 수 있다.</li></ul>

<br>

![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/26c9e947-5b0f-4546-931c-c0091038ef2a)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/e2091886-95a2-4e45-bcfc-d584ce6cba2e)
<ul><li>개인 정보 수정이 필요할 경우 회원 정보수정 기능을 이용할 수 있다.</li></ul>
 
<br>

### 발전소 등록
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/97926a39-ad50-41a9-905b-8cafdc29ec0c)
<ul><li>발전소 이름, 주소, 상세 주소, 사업자 등록 번호를 입력하면 발전소 등록이 가능하다.</li></ul>

<br>

### 발전소 리스트 수정/삭제
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/6afa4657-074e-4f6f-b875-6d73682b801a)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/5ee906d2-721c-4ee1-8326-fde2bb9191ca)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/c5872e74-a30f-4ba2-8ba7-2c1283eb7353)
<ul><li>등록된 발전소를 리스트를 확인하고, 수정 및 삭제가 가능하다.</li></ul>
<ul><li>등록된 발전소는 지도를 통해 확인할 수 있고, 마커를 클릭하면 발전소 정보를 확인할 수 있다.</li></ul>
<ul><li>각 발전소별 날씨 정보를 확인할 수 있다.</li></ul>

<br>
 
### 발전 현황 확인(금일 발전량/금일 발전량 추이)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/0855ebe8-cd92-4e6e-a3a7-351f20c4cf85)
<ul><li>금일 발전량 추이와 실제 금일 발전량을 확인할 수 있다.</li></ul>

<br>

### 발전량 조회
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/9c5ad3a7-18fb-40c8-b1f1-a72e84dd4455)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/d9ae50f9-f58b-4f44-ae49-2c2034d1dc2f)
<ul><li>등록된 등록소별, 날짜별 발전량을 조회할 수 있고 엑셀 다운로드도 가능하다.</li></ul>
 
<br>

### 발전소 현황(셀 상태/센서 데이터) 
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/ce615ec6-12e9-4ac1-a5b8-4eb288dec8c7)
<ul><li>등록된 발전소별 셀 상태와 센서 데이터를 확인할 수 있다.</li></ul>

<br>

### 셀 이미지 확인
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/82692362-6163-4649-a4e7-cb727ca57ce0)
<ul><li>발전소의 발전 셀은 날짜별로 조회 및 이미지 다운로드할 수 있다.</li></ul>

<br>

### 구름형상 이미지 확인 
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/f2f8ae21-7111-4b2e-b6ae-e56085cf225a)
<ul><li>발전소와 날짜를 선택하면 당시 구름형상 이미지를 확인할 수 있고, 이미지는 다운로드할 수 있다.</li></ul>

<br>

## ✨ 팀원 역할
<table>
  <tr>
    <td align="center"><img src="https://i.pinimg.com/originals/96/79/45/967945b77626e6ef12cae02ab1d01ac9.jpg" width="100" height="100"/></td>
    <td align="center"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGTe-5PUwlI_jiHm_l8H7zmeAsQ8tFyqQuTg&usqp=CAU" width="100" height="100"/></td>
    <td align="center"><img src="https://pzip.kr/wp-content/uploads/2023/09/image-75.png" width="100" height="100"/></td>
    <td align="center"><img src="https://image.ajunews.com/content/image/2021/01/04/20210104220435919156.jpg" width="100" height="100"/></td>
    <td align="center"><img src="https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjAzMjZfMjkw%2FMDAxNjQ4MjIwOTkxODc4.ibKo_UeH7BvQudrVrsOErO9Rd24OjR3-ifYgxvg1E_0g.ttihQBg3WOZSW3Fz4J42Ss8gJFkN7gkJ0J_ZBo_qZrgg.JPEG.wewoori1219%2FIMG_5531.JPG&type=sc960_832" width="100" height="100"/></td>
    <td align="center"><img src="https://mblogthumb-phinf.pstatic.net/MjAyMjA2MzBfMjYx/MDAxNjU2NTg2MTY0OTc2.Xnjd2IklmeWWf2nLYbGLulBS5kvjwb9aJC2xC6XsV6Mg.Jm2cMVVmtg-CSoeUGvhmTEisaRonzqWB-v2YkBiN4RIg.PNG.ghkghk94/image.png?type=w800" width="100" height="100"/></td>
      
  </tr>
  <tr>
    <td align="center"><strong>이승재</strong></td>
    <td align="center"><strong>박찬영</strong></td>
    <td align="center"><strong>박준언</strong></td>
    <td align="center"><strong>유민지</strong></td>
    <td align="center"><strong>우리겨레</strong></td>
    <td align="center"><strong>김현정</strong></td>

  </tr>
  
  <tr>
    <td align="center"><b>Data modeling</b></td>
    <td align="center"><b>DataBase,Full stack</b></td>  
    <td align="center"><b>Embedded</b></td>   
    <td align="center"><b>Backend</b></td>  
    <td align="center"><b>Backend</b></td>
    <td align="center"><b>Frontend</b></td>
  </tr>
  
  <tr>
    <td align="center"><a href="https://github.com/tmdwo10" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/cemujax" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/Solokill" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/MinJiYu" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/WooRiGyeoRe" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/lamruth" target='_blank'>github</a></td>

  </tr>
  </table>

  <br>
  
### 이승재
  * **데이터 모델링**
  * 학습데이터 수집
  * 데이터 가공 로직 구현
  * 모델링 API 구현
  * 데이터 샘플링
  * Modeling API 배포
  * PPT, 발표
  * 기획서, 개요서, 발표집, 폼보드


### 박찬영
  * **DataBase,Full stack**
  * DB 설계, 구축 및 DB 요구사항분석서, 테이블명세 작성
  * 리눅스 웹서버, WAS SSL 적용 및 war 배포
  * Spring Boot 프로젝트 구축 및 환경설정
  * Spring 스케줄링으로 API 호출
  * 연동 규격 정의 및 REST API 구축 - IoT 센싱데이터 및 셀 발전량, 촬영 이미지
  * 이미지 서버 업로드 및 다운로드 기능
  * 금일 발전량 추이, 특정기간 발전량 조회(데이터 조회 및 차트 구현)
  * 특정기간 발전량 조회 결과 엑셀 다운로드 기능
  * 셀 상태, 발전소 센싱데이터, 기상차트(데이터 조회 및 차트 구현)


### 박준언
  * **임베디드 제작**
  * 

    
### 유민지
  * **백엔드**
  * 회원정보 수정 페이지
  * (주소검색API, 위도경도API 사용)발전소 등록 및 수정 페이지
  * 발전소 리스트 페이지
  * 셀 이미지 페이지
  * 구름 형상 이미지 페이지
  * 요구사항 정의서
  * WBS 및 회의록 작성

  
### 우리겨레
  * **벡엔드**
  * 날씨, 지도 오픈 API 호출
  * 지도에 발전소 다중 마커 띄우기
  * 마커 클릭시 발전소 정보 제공
  * 사용자 위치 날씨 제공
  * 발전소별 날씨 제공
  * 서비스 둘러보기 화면 UI
  * 요구사항 정의서 작성
  * WBS 및 회의록 작성


### 김현정
  * **프론트엔드**
  * 상단 로고 이미지 제작
  * 금일 발전량 수치화 차트 구현
  * 셀 상태별  UI 변화기능 구현
  * 발전소 리스트,셀 상태 UI 구성
  * 구름형상 페이지 UI구성
  * 발전현황 페이지 구현
    화면설계서 작성
  * PPT

<br>

## 💣 트러블슈팅
### ● 문제1
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/5849c4f2-460e-4560-b29c-2f9f1d9d684e)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/582051cd-3dce-46f8-a01a-bbd7dd5f7fe8)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/104410f8-2b73-4cba-a541-4e3f5c33c009)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/23824ee9-4f14-45b1-9159-b20ad1149efa)

#### Apache 설정 (ssl 인증서 설치된 상태)
#### 가상호스트 및 프록시 설정 (추가 및 수정)
* 경로 : /etc/apache2/sites-enabled/renewen.kr.conf
#### 해결 방안
* 1. Apache 가상호스트 및 프록시 설정
* 2. Permission denied 발생 ⇒ 1024이하 포트 사용하려면 root권한이 필요한데 was(tomcat)에 443 포트로 설정 되어있었기 때문!
* 3. Tomcat server.xml  redirectPort 443 => 8443 변경 후 해결 됨

### ● 문제2
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/14aed52c-16ed-4077-944e-cba6dab5faea)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/da954239-4182-4fde-a71e-a6df38b5cfd3)

#### 각 발전소별 날씨 정보 제공에 대한 트러블 슈팅
#### 원인
* 1. <td>${vo.latitude}${vo.longitude}가  HTML의 요소로 인식되지 않음.
* 2. querySelector 메서드에서 호출한 weatherInfoContainer 변수가 null이라는 문제가 발생하여  각 발전소별 날씨 아이콘, 날씨 상태, 기온이 화면에 보이지 않았음. 
* 3. 마지막 발전소만 날씨 정보가 나오지 않았음. 

#### 해결 방안
* 1. 해당 부분을 <div>태그로 요소 안에 값을 넣어 줌. 해당 과정을 통해 각 발전소의 위도, 경도 값을 저장하고  JavaScript에서 해당 요소를 찾아서 위도와 경도 값을 찾을 수 있게 함.

<div class="latitude" style="display: none;">${vo.latitude}</div>
    <div class="longitude" style="display: none;">${vo.longitude}</div>

* 2. if (weatherInfoContainer)  추가해서  weatherInfoContainer가 null이 아닌지 확인해 줌.

      const weatherInfoContainer = row.querySelector(`#weather-info-${index}`);
            if (weatherInfoContainer) { // weatherInfoContainer가 null이 아닌지 확인
                const iconElement = weatherInfoContainer.querySelector(".icon");
                const descriptionElement = weatherInfoContainer.querySelector(".description");
                const temperatureElement = weatherInfoContainer.querySelector(".temperature");
                
* 3. 마지막 발전소가 출력되지 않은 줄 알았으나 확인해 보니 첫 번째 발전소가 출력되지 않고 있었던 것이었음.

    const weatherInfoContainer = document.querySelector(`#weather-info-${index}`); 을
     const weatherInfoContainer = document.querySelector(`#weather-info-${index + 1}`); 수정해줌.
     그 결과 모든 발전소별 날씨 아이콘, 날씨 상태, 기온이 화면에 나타남.

<br>

## 🏃‍♂️ 발전방향
### WEB
- 사업자 번호 API 추가
- 시각화 요소 추가
### MODEL
- 모델 고도화로 예측 정확도 향상
- API SERVER 데이터 가공 로직 간소화
### EMBEDED
- 모든 기상인자 직접 수집하는 방향
- 구름형상 이미지를 전운량값으로 변환하는 비전인식 적용
- 발전소 별 예측값의 정확도와 기상인자의 정확도 상승시키는 방향





 
