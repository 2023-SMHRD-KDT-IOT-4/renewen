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
	
	<link rel="icon" type="image/x-icon" 	href="${contextPath}/assets/img/favicon.png" />
	<link href="${contextPath}/css/styles.css" rel="stylesheet" />
	<script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
		crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>

	<style>
	.chart-container-css {
		position: relative;
		height: 50vh;
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
											발전량 조회
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
						<div class="col-lg-12">
							<div class="card mb-4">
								<div class="card-header">금일 발전량</div>
								<div class="card-body">
									<div id="chart-container" class="chart-container-css"></div>
									<h2></h2>
								</div>
							</div>
						</div>
						
						<!-- 금일 발전량 추이 -->
<!-- 					
						<div class="col-lg-8">
							Pie chart example
							<div class="card mb-4">
								<div class="card-header">금일 발전량 추이</div>
								<div class="card-body">
									<div id="chartPredictElec" class="chart-pie"></div>
								</div>
							</div>
						</div> -->

					</div>
					<!-- end row1 -->


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
	<%-- <script src="${contextPath}/js/renewen_dashboard.js"></script> --%>
	<script src="${contextPath}/js/renewen_stats.js"></script>
</body>
</html>

