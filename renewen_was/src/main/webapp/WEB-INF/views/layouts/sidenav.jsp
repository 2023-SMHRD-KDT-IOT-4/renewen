<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- 부트스트랩 아이콘 변경  -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
<!-- Font Awesome CSS 링크, 아이콘 추가 사용 -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        
        
<div id="layoutSidenav_nav">
	<nav class="sidenav shadow-right sidenav-light">
	  <div class="sidenav-menu">
     	<div class="nav accordion" id="accordionSidenav">
      	<div class="sidenav-menu-heading d-sm-none">Account</div>
	
					<c:if test="${not empty user and user.authId eq 'ROLE_ADMIN'}">
	          <!-- Sidenav Menu Heading (master)-->
	          <div class="sidenav-menu-heading">Master</div>
	          <!-- Sidenav 마스터 관리자-->
	          <a class="nav-link collapsed" href="javascript:void(0);" data-bs-toggle="collapse"
	              data-bs-target="#collapseDashboards" aria-expanded="false"
	              aria-controls="collapseDashboards">
	              <div class="nav-link-icon"><i class="fas fa-crown"></i></i></div>
	              마스터 관리자
	              <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
	          </a>
	          <!-- 사이드 내비 발전소 승인목록 수정: 현정 -->
	          <div class="collapse" id="collapseDashboards" data-bs-parent="#accordionSidenav">
	              <nav class="sidenav-menu-nested nav accordion" id="accordionSidenavPages">
	                  <a class="nav-link" href="${contextPath}/plantAuth">
	                      발전소 승인 목록</a>
	                <!--   <a class="nav-link" href="dashboard-2.html">일반 관리자 정보 수정</a>
	                  <a class="nav-link" href="dashboard-3.html">Affiliate</a> -->
	              </nav>
	          </div>
          </c:if>
	     				<!-- 발전소 -->
	            <!-- Sidenav Heading (Custom)-->
	            <div class="sidenav-menu-heading">발전소</div>
	            <c:if test="${not empty user.userId}">
	            
		            <a class="nav-link collapsed" href="${contextPath}/plant/dashboard" >
		                <div class="nav-link-icon"><i class="bi bi-lightning-fill"></i></i></div>
		                발전 현황
		            </a>                        
		            <a class="nav-link collapsed" href="${contextPath}/plant/stats" >
		                <div class="nav-link-icon"><i class="bi bi-search"></i></i></div>
		                발전량 조회
		            </a>  	            	
	             <!-- Sidenav Accordion (Pages)-->
	             <a class="nav-link collapsed" href="javascript:void(0);" data-bs-toggle="collapse"
	                 data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
	                 <div class="nav-link-icon"><i class="bi bi-tools"></i></div>
	                 발전소 관리
	                 <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
	             </a>
	             <div class="collapse" id="collapsePages" data-bs-parent="#accordionSidenav">
	                 <nav class="sidenav-menu-nested nav accordion" id="accordionSidenavPagesMenu">
	                   <!-- Nested Sidenav Accordion (Pages -> Account)-->
	                   <a class="nav-link" href="${contextPath}/plant/register">발전소 등록</a>
	                   <a class="nav-link collapsed" href="${contextPath}/plant/list">발전소 리스트</a>
	                   <a class="nav-link collapsed" href="${contextPath}/plant/status">발전소 현황</a>
	                   <a class="nav-link collapsed" href="${contextPath}/plant/cellImgs">셀 상태 확인</a>
	                   <a class="nav-link collapsed" href="${contextPath}/plant/cloudImgs" >구름형상 이미지 </a>
									</nav>
								</div>
	            </c:if>
	
	
              <div class="collapse" id="pagesCollapseAuth" data-bs-parent="#accordionSidenavPagesMenu">
              	<nav class="sidenav-menu-nested nav accordion" id="accordionSidenavPagesAuth">
	                  <!-- Nested Sidenav Accordion (Pages -> Authentication -> Basic)-->
	                  <a class="nav-link collapsed" href="javascript:void(0);"
	                      data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuthBasic"
	                      aria-expanded="false" aria-controls="pagesCollapseAuthBasic">
	                      Basic
	                      <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
	                  </a>
	                  <div class="collapse" id="pagesCollapseAuthBasic"
	                      data-bs-parent="#accordionSidenavPagesAuth">
	                      <nav class="sidenav-menu-nested nav">
	                          <a class="nav-link" href="auth-login-basic.html">Login</a>
	                          <a class="nav-link" href="auth-register-basic.html">Register</a>
	                          <a class="nav-link" href="auth-password-basic.html">Forgot Password</a>
	                      </nav>
	                  </div>
									</nav>
               </div>
        </div>
     </div>
	</nav>
</div>

        
      