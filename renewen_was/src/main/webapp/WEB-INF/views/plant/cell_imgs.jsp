<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${not empty plantList}">
	<c:set var="selectedPlant" value="${plantList[0]}" />
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>renewen</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- favicon2추가  -->
<link rel="icon" type="image/x-icon" href="${contextPath}/assets/img/favicon2.png" />

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
					<div class="card mb-4">
						<div class="card-body">
							<h1 class="mb-1">발전소 셀 상태 확인</h1>
							<div class="d-flex justify-content-between align-items-sm-center flex-column flex-sm-row mb-4">
								<div class="me-4 mb-3 mb-sm-0">
									<!-- 현재 시간 -->
									<div class="small" id="currentDateTimeContainer">
										<span id="currentYear"></span> 
										&middot; 
										<span id="currentMonth"></span> 
										<span id="currentDayOfMonth"></span>
										&middot; 
										<span class="fw-500 text-primary" id="currentDayOfWeek"></span>
										&middot;
										<span id="currentHour"></span>:
										<span id="currentMinute"></span>
										<span id="currentPeriod"></span>
									</div>
									
									<p class="mb-2">원하는 발전소의 셀 상태를 확인하기, 필요한 사진은 저장하세요😀</p>
									<div class="small d-flex align-items-center mb-3">
										<!-- 발전소 선택 -->
										<label for="authSelect1" class="form-label mb-0 me-2" style="width: 10rem; text-align: center;">발전소 선택</label>
										<select class="form-control me-2" id="authSelect1" name="authId" style="width: 20rem;">
											<option>======발전소 선택======</option>
											<c:forEach items="${plantList}" var="vo">
												<option value="${vo.plantNo}">${vo.plantName}</option>
											</c:forEach>
										</select>

										<!-- 셀선택 -->
										<select class="form-control" id="cellSelect" style="width: 20rem;margin-right:5px;">
											<option id="first">======발전 셀 선택======</option>
											<option id="selectdPowerPlnat"></option>
										</select>
										
										<!-- 날짜 선택-->
										<div class="input-group input-group-joined border-0 shadow me-2" style="width: 20rem;">
											<span class="input-group-text"><i class="text-primary"	data-feather="calendar"></i></span> 
											<input	class="form-control ps-0 pointer" id="litepickerSingleDate"	placeholder="날짜 선택">
										</div>
										
										
										<button class="btn btn-warning" style="width: 30%; margin-left: 10px;">조회하기</button>
									</div>
									<div>
										<h4 class="mb-0 mt-5">Cells</h4>
										<hr class="mt-2 mb-4">
										<div id="cellDiv" class="mt-3"></div>
										<!-- 이미지 출력 부분 -->
									</div>
									
									<!-- End cardDiv  -->
									</div>
									</div>
									</div>
									</div>
									</div><!-- <div class="container-xl px-4 mt-5">끝 -->
									
			</main>

			<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />
		</div>
		<!-- end <div class="container-xl px-4 mt-5"> -->


	</div>
	<!-- end <div id="layoutSidenav_content"> -->


	<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"	crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->
	<script src="https://cdn.jsdelivr.net/npm/litepicker/dist/bundle.js" crossorigin="anonymous"></script>
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="${contextPath}/js/date.js"></script>

	<script src="${contextPath}/js/cell_img.js" data-context-path="${contextPath}"></script>

</body>
</html>

