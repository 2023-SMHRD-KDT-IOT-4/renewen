<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link href="${contextPath}/css/custom_style.css" rel="stylesheet" />
<nav
	class="topnav navbar navbar-expand shadow justify-content-between justify-content-sm-start navbar-light bg-white"
	id="sidenavAccordion">
	<!-- Sidenav Toggle Button-->

	<button
		class="btn btn-icon btn-transparent-dark order-1 order-lg-0 me-2 ms-lg-2 me-lg-0"
		id="sidebarToggle">
		<img src="${contextPath}/assets/img/menu.png" alt="Menu Icon"
			style="height: 20px;">
	</button>
	<!-- Navbar Brand-->

	<%-- <a class="navbar-brand pe-3 ps-4 ps-lg-2" href="${contextPath}/"> <img
		src="${contextPath}/assets/img/logo.png" alt="Logo"
		style="height: 50px;"> Renewen
	</a> --%>
	<!-- 로고 svg 삽입  -->
	<a class="navbar-brand pe-3 ps-4 ps-lg-2" href="${contextPath}/">
    <img src="${contextPath}/assets/img/logo3.svg" alt="Logo"> 
</a>

	<!-- 날씨 API -->
	<!-- <body> -->

	<c:if test="${not empty user }">

		<div class="weather-info">
			<img id="icon" class="icon" style="width: 50px; height: 50px;">
		</div>
		<div>
			<span id="description" class="description" style="margin-right: 8px"></span>
		</div>

		<div>
			<span id="temperature" class="temperature"></span>

		</div>

		<script src="${contextPath}/js/weather.js"></script>
	</c:if>

	<!-- 	</body> -->
	<!-- Navbar Items-->
	<ul class="navbar-nav align-items-center ms-auto">

		<c:choose>
			<c:when test="${empty user.userId}">
				<a class="dropdown-item" href="${contextPath}/user/join"><div
						class="dropdown-item-icon"></div>회원가입</a>
				<a class="dropdown-item" href="${contextPath}/user/login"><div
						class="dropdown-item-icon"></div>로그인</a>
			</c:when>
			<c:otherwise>


				<!-- 알림 Dropdown-->
				
				
				<!-- a->div로 변경 -->
				<li
					class="nav-item dropdown no-caret d-none d-sm-block me-3 dropdown-notifications">
					<div class="dropdown">
						<div class="btn btn-transparent-dark dropdown-toggle"
							id="navbarDropdownAlerts" role="button" data-bs-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"
							style="font-weight: bold;">
							<img src="${contextPath}/assets/img/bell.png" alt="Bell Icon"
								style="height: 30px;"> <span style="margin-left: 5px;">알림</span>
						</div>
						<!-- a->div로 변경 끝 -->
						
						
						<div
							class="dropdown-menu dropdown-menu-end border-0 shadow animated--fade-in-up"
							aria-labelledby="navbarDropdownAlerts">
							<h6 class="dropdown-header dropdown-notifications-header">알림
							</h6>
							<!-- 알림1 -->
							<a class="dropdown-item dropdown-notifications-item" href="#!">
								<div class="dropdown-notifications-item-icon bg-success">
									<i data-feather="user-plus"></i>
								</div>
								<div class="dropdown-notifications-item-content">
									<div class="dropdown-notifications-item-content-details">02.22,
										2024</div>
									<div class="dropdown-notifications-item-content-text">로그인
										성공!</div>
								</div>
							</a>
							<!-- 알림 2-->
							<a class="dropdown-item dropdown-notifications-item" href="#!">
								<div class="dropdown-notifications-item-icon bg-warning">
									<i data-feather="activity"></i>
								</div>
								<div class="dropdown-notifications-item-content">
									<div class="dropdown-notifications-item-content-details">02.22,
										2024</div>
									<div class="dropdown-notifications-item-content-text">등록
										요청이 승인되었습니다.</div>
								</div>
							</a>
							<!-- 알림 3-->
							<!-- 	<a class="dropdown-item dropdown-notifications-item" href="#!">
							<div class="dropdown-notifications-item-icon bg-info">
								<i data-feather="bar-chart"></i>
							</div>
							<div class="dropdown-notifications-item-content">
								<div class="dropdown-notifications-item-content-details">02.22,
									2024</div>
								<div class="dropdown-notifications-item-content-text">오늘의
									발전량을 확인하세요!</div>
							</div>
						</a> -->
							<!-- 알림 3-->
							<a class="dropdown-item dropdown-notifications-item" href="#!">
								<div class="dropdown-notifications-item-icon bg-danger">
									<i class="fas fa-exclamation-triangle"></i>
								</div>
								<div class="dropdown-notifications-item-content">
									<div class="dropdown-notifications-item-content-details">02.24,
										2024</div>
									<div class="dropdown-notifications-item-content-text">비정상적
										접근 감지!</div>
								</div>
							</a> <a class="dropdown-item dropdown-notifications-footer" href="#!">모든
								알림 확인</a>
						</div>
				</li>
				<!-- 발전소 드롭다운 -->

				<!-- Power Plant Dropdown-->
				
				
				<!-- a->div로 변경 -->
				<li
					class="nav-item dropdown no-caret d-none d-sm-block me-3 dropdown-notifications">
					<div class="dropdown">
						<div class="btn btn-transparent-dark dropdown-toggle"
							id="navbarDropdownAlerts" role="button" data-bs-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"
							style="font-weight: bold;">
							<img src="${contextPath}/assets/img/plant.png" alt="plant Icon"
								style="height: 30px;"><span style="margin-left: 5px;">발전소</span>
						</div>
						<!-- a->div로 변경 끝 -->
						
						<div
							class="dropdown-menu dropdown-menu-end border-0 shadow animated--fade-in-up"
							aria-labelledby="navbarDropdownMessages">
							<h6 class="dropdown-header dropdown-notifications-header">
								발전소 목록</h6>
							<!-- 발전소 목록 1  -->
							<c:forEach var="vo" items="${plantList}">
								<a class="dropdown-item dropdown-notifications-item"> <img
									class="dropdown-notifications-item-img"
									src="${contextPath}/assets/img/plant.png" alt="plant Icon" />
									<div class="dropdown-notifications-item-content">
										<div class="dropdown-notifications-item-content-text">${vo.plantName }</div>
										<div class="dropdown-notifications-item-content-details">${vo.plantAddr}
											${vo.plantAddr2 }</div>
									</div>
								</a>
							</c:forEach>
							<a class="dropdown-item dropdown-notifications-footer"
								href="${contextPath}/plant/register">발전소 추가</a>
						</div>
				</li>
				<!-- 발전소 목록 -->

				<!-- User Dropdown-->
				
				<!-- a->div로 변경 -->
				<li
					class="nav-item dropdown no-caret d-none d-sm-block me-3 dropdown-notifications">
					<div class="dropdown">
						<div class="btn btn-transparent-dark dropdown-toggle"
							id="navbarDropdownAlerts" role="button" data-bs-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false"
							style="font-weight: bold;">
							<img src="${contextPath}/assets/img/user.png" alt="plant Icon"
								style="height: 30px;"> <span style="margin-left: 5px;">회원정보</span>
						</div>
						<!-- a->div로 변경 끝 -->
						
						<div
							class="dropdown-menu dropdown-menu-end border-0 shadow animated--fade-in-up"
							aria-labelledby="navbarDropdownMessages">
							<h6 class="dropdown-header dropdown-notifications-header">회원정보</h6>
							<!-- 사용자 정보 -->
							<div class="dropdown-item dropdown-notifications-item"
								id="loginButton">
								<img class="dropdown-notifications-item-img"
									src="${contextPath}/assets/img/user.png" alt="user Icon" />
								<div class="dropdown-notifications-item-content">
									<div class="dropdown-notifications-item-content-text">${user.userId }님
										환영합니다!</div>
									<div class="dropdown-notifications-item-content-details"></div>
									<div class="dropdown-user-details-name">사용자 이름 :
										${user.userName }</div>
									<div class="dropdown-user-details-email">이메일 :
										${user.userEmail }</div>
								</div>
							</div>
							<a class="dropdown-item" id="logoutButton"
								href="${contextPath}/user/logout">
								<div class="dropdown-item-icon me-2" style="font-size: 20px;">
									<i class="fas fa-sign-out-alt"></i>
								</div>
								<div>로그아웃</div>
							</a> <a class="dropdown-item" id="updateButton"
								href="${contextPath}/user/update">
								<div class="dropdown-item-icon" style="font-size: 20px;">
									<i class="fas fa-user-edit"></i>
								</div> 회원 정보 수정
							</a>
						</div>
				</li>
			</c:otherwise>
		</c:choose>
	</ul>
	<!-- Navbar Items끝  -->
</nav>


