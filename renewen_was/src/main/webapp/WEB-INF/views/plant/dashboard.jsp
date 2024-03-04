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
        <div class="container-xl px-4 mt-3">
					<input type="hidden" id="contextPath" value="${contextPath}" />
          <header class="page-header page-header-compact page-header-light border-bottom bg-white mb-3">
            <div class="container-xl px-4">
              <div class="page-header-content">
                <div class="row align-items-center justify-content-between pt-3">
                  <div class="col-auto mb-3">
                    <h1 class="page-header-title">
                      <div class="page-header-icon"><i data-feather="file"></i></div>
                      Dashboard
                    </h1>
                  </div>
                  <div class="col-12 col-xl-auto mb-3">
                  	<c:if test="${empty plantList}">
	                  	<h6 class="page-header-title">발전소 없음</h6>
                  	</c:if>
                  	<c:if test="${not empty plantList}">
											<select class="form-control" id="plantList" name="plantList">
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

          <!-- <div class="container-xl px-4 mt-5"> -->
          <!-- row 3칸 -->
          <div class="row" id="dashDiv1">
          
            <div class="col-lg-4">
              <!-- 발전소 발전량 -->
              <div class="card mb-4">
                <div class="card-header">발전소 발전량</div>
                <div class="card-body">
                  <div class="chart-bar">
                  	<h2>현재 발전량</h2>
                  	<span id="spanCurrentWatt"></span>
                  	<h2>누적 발전량</h2>
                  	<span id="spanTotalWatt"></span>
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-lg-8">
              <!-- Pie chart example-->
              <div class="card mb-4">
                <div class="card-header">예상 발전량</div>
                <div class="card-body"><!-- style="height: 400px;" -->
                  <div id="chartPredictElec" class="chart-pie" >
                  	<%-- <canvas id="chartPredictElec" width="100%" height="50"></canvas> --%>
                 	</div>
                </div>
              </div>
            </div>

          </div> <!-- end row -->

          <!-- row 3칸 -->
          <div class="row" id="dashDiv2">
            <div class="col-lg-4">
              <!-- Bar chart example-->
              <div class="card mb-4">
                <div class="card-header">Bar Chart Example</div>
                <div class="card-body">
                  <div class="chart-bar" id="chart1" >
                  	<%-- <canvas id="aaa" width="100%" height="50"></canvas> --%>
                  </div>
                </div>
                <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
              </div>
            </div>
            <div class="col-lg-4">
              <!-- Pie chart example-->
              <div class="card mb-4">
                <div class="card-header">Pie Chart Example</div>
                <div class="card-body">
                  <div class="chart-pie"><canvas id="myPieChart" width="100%" height="50"></canvas></div>
                </div>
                <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
              </div>
            </div>
            <div class="col-lg-4">
              <!-- Pie chart example-->
              <div class="card mb-4">
                <div class="card-header">Pie Chart Example</div>
                <div class="card-body">
                  <div class="chart-pie"><canvas id="myPieChart" width="100%" height="50"></canvas></div>
                </div>
                <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
              </div>
            </div>
          </div> <!-- end row -->

        </div> <!-- end <div class="container-xl px-4 mt-5">-->
    	</main>
    	
    	<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
		</div> <!-- end <div id="layoutSidenav_content"> -->
  </div>
  
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/echarts@5"></script>
	<script src="${contextPath}/js/renewen_dashboard.js"></script>
</body>
</html>

