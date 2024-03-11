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
		href="${contextPath}/assets/img/favicon2.png" /><!-- favicon2추가  -->
	<link rel="icon" type="image/x-icon"
		href="${contextPath}/assets/img/favicon2.png" />
		
	<link href="${contextPath}/css/styles.css" rel="stylesheet" />
	<link
		href="https://cdn.jsdelivr.net/npm/litepicker/dist/css/litepicker.css"
		rel="stylesheet" />
	
	<script data-search-pseudo-elements defer
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js"
		crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/litepicker/dist/litepicker.js"></script>

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
          <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-3">
            <div class="container-xl px-4">
              <div class="page-header-content">
                <div class="row col pt-3 pb-4">
                  <h1 class="page-header-title" style="font-size: 1.5rem;">
										<i class="bi bi-search icon-24 me-2"></i>
                    발전량 조회
                  </h1>
                  <div class="row mt-3">
                    <div class="col-lg-4 d-flex align-items-center">
                    	<c:if test="${empty plantList}">
												<h6 class="page-header-title">등록된 발전소 없음</h6>
											</c:if>
											<c:if test="${not empty plantList}">
	                      <label style="font-size: 1.1rem;">발전소 : </label>
	                      <select class="form-control" id="selectList" name="plantList" style="max-width: 18rem; margin-left: 1rem">
	                      	<c:forEach items="${plantList}" var="vo">
	                        	<option value="${vo.plantNo}">${vo.plantName}</option>
	                      	</c:forEach>
	                      </select>
                      </c:if>
                      
                    </div>
                    <div class="col-lg-8 d-flex align-items-center">
                    	<c:if test="${not empty plantList}">
	                      <div class="input-group input-group-joined border-0 shadow me-2" style="width: 15rem;">
	                        <span class="input-group-text"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar text-primary"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg></span> 
	                        <input class="form-control ps-0 pointer" id="startDate" placeholder="시작 날짜">
	                      </div> ~
	                      <div class="input-group input-group-joined border-0 shadow me-2" style="width: 15rem; margin-left: 1rem; margin-right: 20px;">
	                        <span class="input-group-text"><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-calendar text-primary"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg></span> 
	                        <input class="form-control ps-0 pointer" id="endDate" placeholder="종료 날짜">
	                      </div>
	                      <button id="searchBtn" type="button" class="btn btn-warning" style="width:5rem; margin: 0 1rem;">조회</button>
	                      <button type="button" id="downBtn" class="btn btn-success" style="width:8rem; height: 43.6px;">
	                      	<i class="fas fa-file-excel" style="font-size:1.5rem;"></i>&nbsp;&nbsp;다운로드
                     	 	</button>
                      </c:if>
                    </div>
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
								<div class="card-header">발전량 조회 결과</div>
								<div class="card-body">
									<div id="chart-container" class="chart-container-css"></div>
									<h2></h2>
								</div>
							</div>
						</div>
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
	<script src="https://cdn.jsdelivr.net/npm/litepicker/dist/bundle.js" crossorigin="anonymous"></script>	
	<!-- renewen -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="https://fastly.jsdelivr.net/npm/echarts@5.5.0/dist/echarts.min.js"></script>
	<script src="${contextPath}/js/renewen_stats.js"></script>
</body>
</html>

