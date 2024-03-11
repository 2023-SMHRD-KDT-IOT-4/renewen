# ☀️ Renewen (팀명: Taiyang)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/32b62adb-949b-4a2b-b966-f970daee0fa0)

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
* 링크걸기 <a 로그인/회원가입/회원정보수정</a>
* 링크걸기 <a 발전 현황 확인(금일 발전량/금일 발전량 추이)</a>
* 링크걸기 <a 발전량 조회</a>
* 링크걸기 <a 발전소 등록</a>
* 링크걸기 <a 발전소 리스트 수정/삭제</a>
* 링크걸기 <a 발전소 현황</a>
* 링크걸기 <a 셀 이미지 확인(셀 상태/센서 데이터)</a>
* 링크걸기 <a 구름형상 이미지 확인</a>

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
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/289acd8e-c9b2-48ea-a491-f0d2cf533bd6)
<br>
<ul><li>Renewen 시스템 첫 화면구성</li></ul>
<br>

### 회원가입/로그인/로그아웃/회원정보수정
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/f3c4f3ea-0ef4-41a8-b9e5-2f07b763b2b2)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/19636118-99e5-4d1d-939a-11fbaf15d87e)
<ul><li>회원가입을 클릭하면 회원 가입할 수 있고, 로그인 후 renewen의 다양한 서비스를 사용할 수 있다.</li></ul>

<br>

![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/26c9e947-5b0f-4546-931c-c0091038ef2a)
![image](https://github.com/2023-SMHRD-KDT-IOT-4/renewen/assets/144170214/e2091886-95a2-4e45-bcfc-d584ce6cba2e)
<ul><li>개인 정보 수정이 필요할 경우 회원 정보수정 기능을 이용할 수 있다.</li></ul>
 
<br>
 
### 발전 현황 확인(금일 발전량/금일 발전량 추이)
<ul><li>금일 발전량 추이와 실제 금일 발전량을 확인할 수 있다.</li></ul>

<br>

### 발전량 조회
<ul><li>등록된 등록소별, 날짜별 발전량을 조회할 수 있고 엑셀 다운로드도 가능하다.</li></ul>
 
<br>

### 발전소 등록
<ul><li>발전소 이름, 주소, 상세 주소, 사업자 등록 번호를 입력하면 발전소 등록이 가능하다.</li></ul>

<br>

### 발전소 리스트 수정/삭제
<ul><li>등록된 발전소를 리스트를 확인하고, 수정 및 삭제가 가능하다.</li></ul>
<ul><li>등록된 발전소는 지도를 통해 확인할 수 있고, 마커를 클릭하면 발전소 정보를 확인할 수 있다.</li></ul>
<ul><li>각 발전소별 날씨 정보를 확인할 수 있다.</li></ul>
<br>

### 발전소 현황(셀 상태/센서 데이터)   
<ul><li>등록된 발전소별 셀 상태와 센서 데이터를 확인할 수 있다.</li></ul>

<br>

### 셀 이미지 확인
<ul><li>발전소의 발전 셀은 날짜별로 조회가 가능하다</li></ul>

<br>

#### 구름형상 이미지 확인 
<ul><li>발전소와 날짜를 선택하면 당시 구름형상 이미지를 확인할 수 있다.</li></ul>

<br>
 





 
