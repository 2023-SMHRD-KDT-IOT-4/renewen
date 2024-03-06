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
  <link href="https://cdn.jsdelivr.net/npm/litepicker/dist/css/litepicker.css" rel="stylesheet" />
  
  <script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/litepicker/dist/litepicker.js"></script>

  
</head>
<body class="nav-fixed">
  
  <jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />
  
  <div id="layoutSidenav">
		<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />
		<div id="layoutSidenav_content">
      <main>
        <!-- Main page content-->
        <div class="container-xl px-4 mt-5">
          
          <div class="d-flex justify-content-between align-items-sm-center flex-column flex-sm-row mb-4">
            <div class="me-4 mb-3 mb-sm-0">
              <h1 class="mb-0">발전소</h1>
              <!-- 현재 시간 -->				
                <div class="small" id="currentDateTimeContainer">
	                 <span  id="currentYear"></span>
	                 &middot; <span id="currentMonth"></span> <span id="currentDayOfMonth"></span>
	                 &middot; <span class="fw-500 text-primary" id="currentDayOfWeek"></span>
	                 &middot; <span id="currentHour"></span>:<span id="currentMinute"></span> <span id="currentPeriod"></span>
             	</div>
              <div class="small">
					<select class="form-control" id="authSelect1" name="authId">
						<c:forEach items="${plantList}" var="vo">
							<option value="${vo.plantNo}">${vo.plantName}</option>
						</c:forEach>
					</select>
	             	<!-- Date range picker example-->
		            <div class="input-group input-group-joined border-0 shadow" style="width: 16.5rem">	
		            	<span class="input-group-text"><i class="text-primary" data-feather="calendar"></i></span>
		                <input class="form-control ps-0 pointer" id="litepickerSingleDate" placeholder="날짜 선택">
		            </div>
		            <button>조회하기</button>
              </div>
              
            </div>
            
          </div>
          
          
          <div>
          	
          	<h2>Cloud Imgs</h2>
	          	<table class="table table-hover">
	         			<tbody id="imgBody">
 								<tr>
 									<th>이미지</th>
 									<th>이름</th>
 									<th>찍힌 시간</th>
 									<th></th>       
        						</tr>							
	          			</tbody>
				</table>
          </div>
        </div>
      </main>
    	<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
		</div> <!-- end <div id="layoutSidenav_content"> -->
  </div>
  
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->  
    <script src="https://cdn.jsdelivr.net/npm/litepicker/dist/bundle.js" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="${contextPath}/js/date.js"></script>
	
	<script src="${contextPath}/js/cloud_img.js" data-context-path="${contextPath}"></script> 
	
	
</body>
</html>

