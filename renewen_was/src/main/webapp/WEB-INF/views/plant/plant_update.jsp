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
  <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>
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
	            <h1 class="mb-0">발전소 정보 수정</h1>
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
        			

				    <!-- 발전소 이름, 발전소 주소, 사업자 등록 번호  -->
				  <form action="${contextPath}/plant/update" method="post" onsubmit="return validateForm()">
				    <div class="mb-3 mt-3">
				      <label for="userId">발전소 이름</label>
				      <input type="text" class="form-control" id="plantName" placeholder="발전소 이름 입력" name="plantName" value="${vo.plantName}">
				    </div>
				    <div class="mb-3">
				      <label for="userPw">발전소 주소</label>
				      <input type="text" class="form-control" id="plantAddr" onclick="sample6_execDaumPostcode()" placeholder="발전소 주소 입력" name="plantAddr" value="${vo.plantAddr}">
				      
				      <input type="text" class="form-control" id="plantAddr2" placeholder="상세주소입력" name="plantAddr2" value="${vo.plantAddr2 }">
				      <!-- <input type="text" id="sample6_detailAddress" placeholder="상세주소"> -->
				      <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기">
				    </div>
				    <div class="mb-3">
				      <label for="userPw">사업자 등록 번호</label>
				      <input type="text" class="form-control" id="brNumber"  placeholder="사업자 번호 입력(숫자만 입력해주세요)" name="brNumber" oninput="formatNumber(this)" value="${vo.brNumber }">
				    </div>
				    
				    
				    <button type="submit" class="btn btn-primary">수정</button>
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
  	<script type="text/javascript">
	  	 function sample6_execDaumPostcode() {
	         new daum.Postcode({
	             oncomplete: function(data) {
	                
	                 var addr = ''; // 주소 변수
	                
	                 if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                     addr = data.roadAddress;
	                 } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                     addr = data.jibunAddress;
	                 }
	
	                 document.getElementById("plantAddr").value = addr;
	                 document.getElementById("plantAddr2").focus();
	                 
	             }
	         }).open();
	  	 }
	  	 //Tab키 이벤트
	     document.getElementById("plantAddr").addEventListener("focus", sample6_execDaumPostcode);
  	</script>
		

	<script type="text/javascript">

	    var koreanDays = ['일', '월', '화', '수', '목', '금', '토'];

	    var koreanMonths = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];
	

	    function updateDateTime() {
	        var currentDate = new Date();
	
	        var year = currentDate.getFullYear();
	        var month = koreanMonths[currentDate.getMonth()];
	        var dayOfMonth = currentDate.getDate();
	        var dayOfWeek = koreanDays[currentDate.getDay()];
	        var hours = currentDate.getHours();
	        var minutes = currentDate.getMinutes();
	        var ampm = hours >= 12 ? '오후' : '오전';
	

	        hours = hours % 12;
	        hours = hours ? hours : 12; 
	

	        dayOfMonth = (dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth;
	        minutes = (minutes < 10) ? "0" + minutes : minutes;

	        document.getElementById("currentYear").textContent = year + '년';
	        document.getElementById("currentMonth").textContent = month;
	        document.getElementById("currentDayOfMonth").textContent = dayOfMonth + '일';
	        document.getElementById("currentDayOfWeek").textContent = dayOfWeek + '요일';
	        document.getElementById("currentHour").textContent = hours;
	        document.getElementById("currentMinute").textContent = minutes;
	        document.getElementById("currentPeriod").textContent = ampm;
	    }
	
	    window.onload = function() {
	        updateDateTime(); 
	        setInterval(updateDateTime, 1000);
	        
		    // 커서 위치
		    var input = document.querySelector('input');
		    if(input) {
		        var inputValue = input.value;
		        input.selectionStart = input.selectionEnd = inputValue.length;
		        input.focus();
		    }
		    
	    };
		
	</script>
	
	
	
		
	


</body>
</html>

