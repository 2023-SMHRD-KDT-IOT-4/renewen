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

### 구름형상 이미지 확인 
<ul><li>발전소와 날짜를 선택하면 당시 구름형상 이미지를 확인할 수 있다.</li></ul>

<br>

## ✨ 팀원 역할
<table>
  <tr>
    <td align="center"><img src="https://i.pinimg.com/originals/96/79/45/967945b77626e6ef12cae02ab1d01ac9.jpg" width="100" height="100"/></td>
    <td align="center"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGTe-5PUwlI_jiHm_l8H7zmeAsQ8tFyqQuTg&usqp=CAU" width="100" height="100"/></td>
    <td align="center"><img src="https://pzip.kr/wp-content/uploads/2023/09/image-75.png" width="100" height="100"/></td>
    <td align="center"><img src="https://image.ajunews.com/content/image/2021/01/04/20210104220435919156.jpg" width="100" height="100"/></td>
    <td align="center"><img src="https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/cnoC/image/DsgJnnSn5FoSogwRJV90VZxyMs0" width="100" height="100"/></td>
    <td align="center"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbRpMQnLoUgkuT8BKefB5QY-6E8nWM8ir6cg&usqp=CAU" width="100" height="100"/></td>
      
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
    <td align="center"><a href="https://github.com/HUNMINRYU" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/dlrjsdud" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/WooRiGyeoRe" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/YangYnuHyeong" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/dlwlsdn808" target='_blank'>github</a></td>
    <td align="center"><a href="https://github.com/sangiot" target='_blank'>github</a></td>

  </tr>
  </table>
  
  ### 류훈민
  * **프론트엔드**
  * **카카오맵api 이용하여 자전거의 현재위치** 구현
  * **카카오맵api 이용하여 라이딩 주행 기록** 구현
  * 전반적인 웹 서비스 **화면 디자인** 및 **반응형 웹 적용**
  * 기획 및 최종 발표 PPT 제작
  * 시연영상 제작

### 이건영
  * **프로젝트 팀장**, 퍼블리싱을 제외한 프로젝트 전반적인 기능 구현
  * Spring 프로젝트 **환경설정** 및 라이브러리 추가
  * **회원가입** 및 **회원정보수정** 프론트엔드/백엔드 기능구현
  * **잠금/잠금해제** 프론트엔드/백엔드 기능구현
  * **카카오맵 api**를 이용한 **일일 라이딩기록 확인** 프론트엔드/백엔드 기능구현
  * **카카오맵 api**를 이용한 내 **자전거 현재 위치 확인 비동기통신** 프론트엔드/백엔드 기능구현
  * 게시판 **글 작성 및 삭제, 게시판 전체보기** 프론트엔드/백엔드 기능구현
  * 자전거 도난 예상시 웹페이지 **사용자 알림** 기능 프론트엔드/백엔드 기능구현
  * 프로젝트 구현중 필요에 따라 데이터베이스 **테이블,컬럼 생성 및 삭제 / 데이터타입 및 제약조건 수정**
  * Arduino **gps, 가속도센서 라이브러리 추가 및 구현**
  * Arduino 3가지 센서 및 모듈, 와이파이 통신 **코드 통합**
  * 각 센서에서 받은 위도,경도,가속도 값을 파싱하여 http통신을 통해 **Arduino to Spring 전달** 기능구현
  * 웹페이지에서 잠금/잠금해제, 알림중지(모듈 청각적 알림 중지) 데이터 **Spring to Arduino 전달** 기능구현

### 이진우
  * **프로젝트 결과 발표 및 포스터 작성**
      - 프로젝트 결과물에 대한 분석 및 효과적인 데이터 시각화를 통한 프레젠테이션 제작 및 전달
    UI/UX 및 웹페이지 문구 설계
  * **WBS 작성**
      - 프로젝트의 전체적인 구조 및 일정을 파악하기 위한 WBS(Work Breakdown Structure) 구축
  * **프론트엔드**
      - 사용자 인터페이스 개발: 로그인, 로그아웃, 회원가입 페이지 개발
      - 커뮤니티 기능 구현: 사용자 간 소통을 위한 게시판 및 게시글 작성 페이지 개발
      - 반응형 웹 디자인: 다양한 디바이스에 최적화된 메인화면 페이지 및 기능 구현
  * **프로젝트 IOT 제품 결과물 설계 및 조립**
      - IoT 기반 프로젝트 결과물의 설계 및 하드웨어 조립을 통한 실제 사용 가능한 프로토타입 제작
    
### 김상현
  * **프론트엔드**
  * **프론트 화면 제작 및 수정**
      * 경보화면, 마이페이지, 로그인, 유의사항
  * 전반적인 웹 서비스 **화면 디자인** 및 **반응형 웹 적용**
  * 메뉴 활성화, 팝업창 기능 구현
  * 화면설계서 및 최종발표 ppt 제작

### 우리겨레
  * **백엔드**
    * **로그인/로그아웃 기능 구현**
    * lock/Unlock 기능 구현
    * 회원정보 수정 및 DB 연결
    * 회원/비회원 구별 기능 구현
  * **데이터베이스**
    * 요구사항 정의서, 데이터분석도, 데이터명세서 문서 작성 및 수정  
  * **아두이노**
    * GPS센서, mp3모듈 연결 및 코드 작성
  * **산출문서**
    * 브레인스토밍, 프로젝트 기획서 문서 작성 

### 양윤형
  * **백엔드**
    * 로그인/로그아웃 기능 구현
    * lock/Unlock 기능 구현
    * 회원정보 수정 및 DB 연결
    * 회원/비회원 구별 기능 구현
  * **데이터베이스**
    * 요구사항 정의서, 데이터분석도, 데이터명세서 문서 작성 및 수정
  * **아두이노**
    * GPS센서, mp3모듈 연결 및 코드 작성
  

## 🤾‍♂️ 트러블슈팅
### ● 문제1
<ol>
    <li>Arduino NANO -> ESP32 d1 r32 => 블루투스 통신의 거리는 환경에 따라 다르지만 약 10m~100m의 거리제한을 가지고있음. 현재 시중에 나와있는 자전거 도난 방지의 제품들은 대부분이 블루투스 통신을 사용중이고 이에 따라, 물리적으로 도난을 막을 수 없다면 2차적인 대응으로 내 자전거의 위치를 확인하고 직접 찾으러 가는 방법이 있으면 좋겠다는 생각에 와이파이 통신으로 변경</li>
    <li>ESP32 d1 r32 -> Arduino UNO+wifi 모듈 => ESP32 d1 r32의 접근성 부족으로 인해 프로젝트 기간준수를 위한 접근성이 좋은 UNO보드+wifi모듈로 변경</li>
    <li>Arduino UNO+wifi모듈 -> ESP32 d1 r32 => wifi모듈을 사용하며 처음 접한 펌웨어 업데이트, 어댑터의 부재(배송 시간 부족)로 인해 직면했던 다양한 이슈 해결 불가</li>
    <li>2번에서 직면한 문제에 대해 팀원들과의 협력 및 개인시간 투자로 인해 문제해결</li>
</ol> 
<ul><li>느낀점 - 문제가 생길거라 생각하지 못한 IoT에서 많은 문제를 겪고, 생각보다 더 많은 시간을 투자하며 기획단계 및 프로젝트 진행 순서의 중요성을 느낌</li></ul> 





 
