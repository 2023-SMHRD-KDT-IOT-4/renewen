<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <title>renewen</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <link rel="icon" type="image/x-icon" href="${contextPath}/assets/img/favicon.png" />
  <link href="${contextPath}/css/styles.css" rel="stylesheet" />
  
  <script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body class="nav-fixed">
  
  <jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />
  
  <div id="layoutSidenav">
      <jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />
      <div id="layoutSidenav_content">
       <main>
         <!-- Main page content-->
           <div class="container-xl px-4 mt-5">
           <!-- Custom page header alternative example-->
           <div class="d-flex justify-content-between align-items-sm-center flex-column flex-sm-row mb-4">
             <div class="me-4 mb-3 mb-sm-0">
               <h1 class="mb-0">발전소 등록</h1>
               <!-- <div class="small">
                   <span class="fw-500 text-primary">Friday</span>
                   &middot; September 20, 2021 &middot; 12:16 PM
               </div> -->
              <div class="small" id="currentDateTimeContainer">
                 <span  id="currentYear"></span>
                 &middot; <span id="currentMonth"></span> <span id="currentDayOfMonth"></span>
                 &middot; <span class="fw-500 text-primary" id="currentDayOfWeek"></span>
                 &middot; <span id="currentHour"></span>:<span id="currentMinute"></span> <span id="currentPeriod"></span>
             </div>

               
            </div>
           </div>
        </div>
        <div class="container mt-3">
              <!-- <h2>Form</h2> -->
                <!-- 발전소 이름, 발전소 주소, 사업자 등록 번호  -->
              <form action="${contextPath}/plant/register" method="post" onsubmit="return validateForm()" id="plantForm" >
                    <!-- dummy - 관측소 지점번호, 위도, 경도 -->
                 <input type="hidden" name="stnNo" value="156"/> 
                 <input type="hidden" name="latitude" id="latitude" > 
                 <input type="hidden" name="longitude" id="longitude" > 
                <div class="mb-3 mt-3">
                  <label for="userId">발전소 이름</label>
                  <input type="text" class="form-control" id="plantName" placeholder="발전소 이름 입력" name="plantName">
                </div>
                <div class="mb-3">
                  <label for="userPw">발전소 주소</label>
                  <input type="text" class="form-control" id="plantAddr" onclick="sample6_execDaumPostcode()" placeholder="발전소 주소 입력" name="plantAddr" onfocus="sample6_execDaumPostcode()">
                  
                  <input type="text" class="form-control" id="plantAddr2" placeholder="상세주소입력" name="plantAddr2">
                  <!-- <input type="text" id="sample6_detailAddress" placeholder="상세주소"> -->
                  <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">
                </div>
                <div class="mb-3">
                  <label for="userPw">사업자 등록 번호</label>
                  <input type="text" class="form-control" id="brNumber"  placeholder="사업자 번호 입력(숫자만 입력해주세요)" name="brNumber" oninput="formatNumber(this)">
                </div>
                
                
                <button type="button" class="btn btn-primary" onclick="getLALOInfoAndSubmit()">등록</button>
              </form>
            </div>
       </main>
       <jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
      </div> <!-- end <div id="layoutSidenav_content"> -->
  </div>
  
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
   <script src="${contextPath}/js/scripts.js"></script>
   <!-- renewen -->  
     <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
     
     <script src="${contextPath}/js/date.js"></script>
  
     <script type="text/javascript">
        function validateForm() {
           var brNumber = document.getElementById("brNumber").value;
            // 입력값이 숫자 && 길이가 10이 아닌 경우
            if(!/^\d{10}$/.test(brNumber)) {
                alert("사업자 번호를 10자리 숫자로 입력해주세요.");
                return false; 
            } 
            return true;
        }
           
     </script>
     
    <!-- 주소 API -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${contextPath}/js/address.js"></script>

  
	
		<!-- 겨레 추가 -->
<!-- 	<script type="text/javascript">
	function getAddressCoordinates(address) {
	    var geocoder = new daum.maps.services.Geocoder();
	    geocoder.addressSearch(address, function(result, status) {
	        if (status == daum.maps.services.Status.OK) {
	            var latitude = result[0].y;
	            var longitude = result[0].x;
	            
	            // 위도와 경도 값을 hidden input에 설정
	            document.getElementById("latitude").value = latitude;
	            document.getElementById("longitude").value = longitude;
	        } else {
	            console.error("주소를 찾을 수 없습니다.");
	        }
	    });
	}
	</script> -->


</body>
</html>