<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
	<title>renewen</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport"	content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
<!-- favicon2추가  -->
<link rel="icon" type="image/x-icon"
	href="${contextPath}/assets/img/favicon2.png" />
	
	<link href="${contextPath}/css/styles.css" rel="stylesheet" />
	<script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
		crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>
	
	<!--현정, 하이차트 사용 위해 라이브러리 로드  -->
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<!-- Highcharts 스타일 시트 로드 -->
	<link rel="stylesheet" href="https://code.highcharts.com/css/highcharts.css">
	
	<style>
	.chart-container-css {
		position: relative;
		height: 80vh;
		overflow: hidden;
	}
	</style>

</head>
<body class="nav-fixed">

	<jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />

	<div id="layoutSidenav">

		<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />

		<div id="layoutSidenav_content">
			<main>
				<!-- Main page content-->
				<div class="container-xl px-4 mt-3">
					<input type="hidden" id="contextPath" value="${contextPath}" />
					<header
						class="page-header page-header-compact page-header-light border-bottom bg-white mb-3">
						<div class="container-xl px-4">
							<div class="page-header-content">
								<div class="row align-items-center justify-content-between pt-3">
									<div class="col-auto mb-3">
										<h1 class="page-header-title">
											<div class="page-header-icon">
												<i data-feather="zap"></i>
											</div>
											금일 발전 형황
										</h1>
									</div>

									<div class="col-12 col-xl-auto mb-3 d-flex align-items-center">
										<c:if test="${empty plantList}">
											<h6 class="page-header-title">발전소 없음</h6>
										</c:if>
										<c:if test="${not empty plantList}">
											<label for="plantList" class="me-2 inline-label">발전소:</label>
											<select class="form-control" id="selectList" name="plantList">
												<c:forEach items="${plantList}" var="vo">
													<option value="${vo.plantNo}">${vo.plantName}</option>
												</c:forEach>
											</select>
										</c:if>
									</div>
								</div>
							</div>
						</div>
					</header>

					<!-- row1 -->
					<div class="row" id="dashDiv1">

						<!-- 금일 발전량 -->
						<div class="col-lg-4">
							<div class="card mb-4">
								<div class="card-header">금일 발전량</div>
								<div class="card-body">
									<!-- 현정 수정 부분, 바 차트 삽입  -->
									<div class="chart-bar" id="chart-container"></div>
								</div>
							</div>
						</div>
						
						<!-- 금일 발전량 추이 -->
						<div class="col-lg-8">
							<!-- Pie chart example-->
							<div class="card mb-4">
								<div class="card-header">금일 발전량 추이</div>
								<div class="card-body">
									<div id="chartPredictElec" class="chart-pie"></div>
								</div>
							</div>
						</div>

					</div>
					<!-- end row1 -->

					<!-- row2 -->
					<div class="row" id="dashDiv2">
						<!-- 셀 상태 -->
						<div class="col-lg-4">
							<div class="card mb-4">
								<div class="card-header">
									셀 상태</div>
								<div class="card-body">
									1)아이콘(상태별 색상) 2)상태 텍스트로	출력 3) 셀 표면 온도
									<div class="chart-bar" id="chart1">
									</div>
								</div>
							</div>
						</div>
						
						<!-- 금일 기상차트 -->
						<div class="col-lg-8">
							<div class="card mb-4">
								<div class="card-header">금일 기상차트</div>
									<div class="card-body">
										<div id="chartWeather" class="chart-container-css"></div>
									</div>
							</div>
						</div>

					</div>
				</div>
				<!-- end <div class="container-xl px-4 mt-5">-->
			</main>

			<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />
		</div>
		<!-- end <div id="layoutSidenav_content"> -->
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="https://fastly.jsdelivr.net/npm/echarts@5.5.0/dist/echarts.min.js"></script>
	<script src="${contextPath}/js/renewen_dashboard.js"></script>
	<script src="${contextPath}/js/bar_chart.js"></script>
</body>
</html>

