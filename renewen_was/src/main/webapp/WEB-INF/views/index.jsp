<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <title>Renewen index</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/litepicker/dist/css/litepicker.css" rel="stylesheet" />
    <link href="${contextPath}/css/styles.css" rel="stylesheet" />
    <link rel="icon" type="image/x-icon" href="${contextPath}/assets/img/logo.png" />
    <script data-search-pseudo-elements defer
        src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js"
        crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/feather-icons/dist/feather.min.css">
    <link rel="stylesheet" href="${contextPath}/js/chart.js">
</head>
<body class="nav-fixed">
  
  <jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />
  
  <div id="layoutSidenav">
		<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />
		
		
      <div id="layoutSidenav_content">
            <main>
                <header class="page-header page-header-dark bg-gradient-primary-to-secondary pb-10">
                    <div class="container-xl px-4">
                        <div class="page-header-content pt-4">
                            <div class="row align-items-center justify-content-between">
                                <div class="col-auto mt-4">
                                    <h1 class="page-header-title">
                                        <!-- <div class="page-header-icon"><i data-feather="activity"></i></div> -->
                                        서비스 둘러보기
                                    </h1>
                                    <div class="page-header-subtitle">디지털 트윈을 이용한 AIoT 재생에너지 발전량 예측 서비스</div>
                                </div>
                                <!-- <div class="col-12 col-xl-auto mt-4">
                                        <div class="input-group input-group-joined border-0" style="width: 16.5rem">
                                            <span class="input-group-text"><i class="text-primary" data-feather="calendar"></i></span>
                                            <input class="form-control ps-0 pointer" id="litepickerRangePlugin" placeholder="Select date range..." />
                                        </div>
                                    </div> -->
                            </div>
                        </div>
                    </div>
                </header>
                <!-- Main page content-->
                <div class="container-xl px-4 mt-n10">
                    <div class="row">
                        <div class="col-xxl-4 col-xl-12 mb-4">
                            <div class="card h-100">
                                <div class="card-body h-100 p-5">
                                    <div class="row align-items-center">
                                        <div class="col-xl-8 col-xxl-12">
                                            <div
                                                class="text-center text-xl-start text-xxl-center mb-4 mb-xl-0 mb-xxl-4">
                                                <h1 class="text-primary">발전량 예측</h1>
                                                <p class="text-gray-700 mb-0"></p>
                                            </div>
                                        </div>
                                        <div class="col-xl-4 col-xxl-12 text-center">
                                            <!-- 발전량 도넛차트 -->
                                            <canvas id="myDoughnutChart"></canvas>
                                            <br>
                                            <h1 class="text-primary">총 용량</h1>
                                            <!-- 3발전소 총용량 원형 차트 -->
                                            <!-- <canvas id="myPieChart" width="400" height="400"></canvas> -->

                                            <canvas id="myPlantChart" width="400" height="400"></canvas>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xxl-4 col-xl-6 mb-4">
                            <div class="card card-header-actions h-100">
                                <div class="card-header">
                                    현재 발전량
                                    <div class="dropdown no-caret">
                                        <button class="btn btn-transparent-dark btn-icon dropdown-toggle"
                                            id="dropdownMenuButton" type="button" data-bs-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false"><i class="text-gray-500"
                                                data-feather="more-vertical"></i></button>
                                        <div class="dropdown-menu dropdown-menu-end animated--fade-in-up"
                                            aria-labelledby="dropdownMenuButton">

                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="timeline timeline-xs">

                                        <div class="">
                                            <div class="">
                                                <div class=""></div>
                                                <div class=""></div>
                                            </div>
                                            <div class="">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xxl-4 col-xl-6 mb-4">
                            <div class="card card-header-actions h-100">
                                <div class="card-header">
                                    발전소 위치 정보
                                    <div class="dropdown no-caret">
                                        <button class="btn btn-transparent-dark btn-icon dropdown-toggle"
                                            id="dropdownMenuButton" type="button" data-bs-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="false"><i class="text-gray-500"
                                                data-feather="more-vertical"></i></button>
                                        <div class="dropdown-menu dropdown-menu-end animated--fade-in-up"
                                            aria-labelledby="dropdownMenuButton">
                                            <a class="dropdown-item" href="#!">
                                                <div class="dropdown-item-icon"><i class="text-gray-500"
                                                        data-feather="list"></i></div>
                                                Manage Tasks
                                            </a>
                                            <a class="dropdown-item" href="#!">
                                                <div class="dropdown-item-icon"><i class="text-gray-500"
                                                        data-feather="plus-circle"></i></div>
                                                Add New Task
                                            </a>
                                            <a class="dropdown-item" href="#!">
                                                <div class="dropdown-item-icon"><i class="text-gray-500"
                                                        data-feather="minus-circle"></i></div>
                                                Delete Tasks
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <!-- 현재 발전량 -->


								<!-- 지도 마크업 -->
<!-- 지도 마크업 -->
<div id="map" style="width: 100%; height: 100%;"></div>

<script type="text/javascript"
    src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=l8y7kfobe4"></script>

<script>
    var mapOptions = {
        center: new naver.maps.LatLng(35.17294, 126.89156),
        zoom: 15
    };

    var map = new naver.maps.Map('map', mapOptions);

    var markerPositions = [
        { position: new naver.maps.LatLng(35.17294, 126.89156), name: "발전소 1", content: "주소 1" },
        { position: new naver.maps.LatLng(35.161828, 126.880026), name: "발전소 2", content: "주소 2" },
        { position: new naver.maps.LatLng(35.1653428, 126.9092003), name: "발전소 3", content: "주소 3" },
        { position: new naver.maps.LatLng(35.1682592, 126.8884114), name: "발전소 4", content: "주소 4" },
        { position: new naver.maps.LatLng(35.173121, 126.893802), name: "발전소 5", content: "주소 5" }
    ];

    // 변수를 선언하여 현재 열려 있는 정보창을 저장합니다.
    var openedInfoWindow = null;

    for (var i = 0; i < markerPositions.length; i++) {
        var marker = new naver.maps.Marker({
            position: markerPositions[i].position,
            map: map
        });

        // 클로저를 사용하여 정보 창에 대한 정보를 보존합니다.
        (function (marker, contentString) {
            naver.maps.Event.addListener(marker, 'click', function (e) {
                // 마커를 클릭했을 때 열려 있는 정보창이 있다면 닫습니다.
                if (openedInfoWindow && openedInfoWindow.getMap()) {
                    openedInfoWindow.close();
                }
                // 현재 클릭된 마커에 정보창을 연다.
                var infoWindow = new naver.maps.InfoWindow({
                    content: contentString
                });
                infoWindow.open(map, marker);
                // 열린 정보창을 저장합니다.
                openedInfoWindow = infoWindow;
            });
        })(marker, '<div style="padding:10px;"><h5>' + markerPositions[i].name + '</h5><p>' + markerPositions[i].content + '</p></div>');
    }

    // 지도를 클릭했을 때 열려 있는 정보창이 있다면 닫습니다.
    naver.maps.Event.addListener(map, 'click', function (e) {
        if (openedInfoWindow) {
            openedInfoWindow.close();
            openedInfoWindow = null;
        }
    });
</script>

                        <!-- 지도 마지막단 -->              
            </main>
							<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
        </div>		<!-- end <div id="layoutSidenav_content"> -->
		
  </div>
  
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<!-- renewen -->  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
  <!-- Chart.js 라이브러리 추가 -->
  <script src="${contextPath}/js/chart.js"></script>
</body>
</html>

