<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div id="layoutSidenav_nav">
	<nav class="sidenav shadow-right sidenav-light">
  	
		<div class="sidenav-menu">
    	<div class="nav accordion" id="accordionSidenav">
	      <!-- Sidenav Menu Heading (Account)-->
	      <!-- * * Note: * * Visible only on and above the sm breakpoint-->
	      <div class="sidenav-menu-heading d-sm-none">Account</div>
	      <!-- Sidenav Link (Alerts)-->
	      <!-- * * Note: * * Visible only on and above the sm breakpoint-->
	      <a class="nav-link d-sm-none" href="#!">
       		<div class="nav-link-icon"><i data-feather="bell"></i></div>
		       Alerts
		       <span class="badge bg-warning-soft text-warning ms-auto">4 New!</span>
	      </a>
	      <!-- Sidenav Link (Messages)-->
	      <!-- * * Note: * * Visible only on and above the sm breakpoint-->
	      <a class="nav-link d-sm-none" href="#!">
	        <div class="nav-link-icon"><i data-feather="mail"></i></div>
	        Messages
	        <span class="badge bg-success-soft text-success ms-auto">2 New!</span>
	      </a>
             
        <!-- Sidenav Menu Heading(Core) - DashBoard-->
        <div class="sidenav-menu-heading">Core</div>
        	<!-- Sidenav Link (Charts)-->
          <a class="nav-link" href="${contextPath}/platn/dashboard">
	          <div class="nav-link-icon"><i data-feather="bar-chart"></i></div>
	          Dashboard
          </a>
        	<!-- Sidenav Accordion (Dashboard)-->
          <a class="nav-link collapsed" href="javascript:void(0);" data-bs-toggle="collapse" data-bs-target="#collapseDashboards" aria-expanded="false" aria-controls="collapseDashboards">
          	<div class="nav-link-icon"><i data-feather="activity"></i></div>
       			Dashboards Menu
           	<div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
          </a>
          <div class="collapse" id="collapseDashboards" data-bs-parent="#accordionSidenav">
           <nav class="sidenav-menu-nested nav accordion" id="accordionSidenavPages">
             <a class="nav-link" href="dashboard-1.html">
              	Default
               <span class="badge bg-primary-soft text-primary ms-auto">Updated</span>
             </a>
             <a class="nav-link" href="dashboard-2.html">Multipurpose</a>
             <a class="nav-link" href="dashboard-3.html">Affiliate</a>
          	</nav>
          </div>
             
          <!-- Sidenav Heading (Custom)-->
          <div class="sidenav-menu-heading"></div>
          <!-- Sidenav Accordion (Pages)-->
          <a class="nav-link collapsed" href="javascript:void(0);" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
	          <div class="nav-link-icon"><i data-feather="grid"></i></div>
	          발전소
	          <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
          </a>
          <div class="collapse" id="collapsePages" data-bs-parent="#accordionSidenav">
           <nav class="sidenav-menu-nested nav accordion" id="accordionSidenavPagesMenu">
             <a class="nav-link" href="${contextPath}/plant/register">발전소 등록</a>
             <a class="nav-link" href="${contextPath}/plant/list">발전소 리스트</a>
             <a class="nav-link" href="${contextPath}/plant/cloudImgs">구름형상 이미지</a>
             <a class="nav-link" href="${contextPath}/plant/2">발전소 2</a>
           </nav>
          </div>
             
       	<!-- Sidenav Accordion (Flows)-->
       	<a class="nav-link collapsed" href="javascript:void(0);" data-bs-toggle="collapse" data-bs-target="#collapseFlows" aria-expanded="false" aria-controls="collapseFlows">
	        <div class="nav-link-icon"><i data-feather="repeat"></i></div>
	        Test
	        <div class="sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
       	</a>
	      <div class="collapse" id="collapseFlows" data-bs-parent="#accordionSidenav">
        	<nav class="sidenav-menu-nested nav">
	          <a class="nav-link" href="${contextPath}/api/img/insert">이미지 등록</a>
	        </nav>
	      </div>
             
     	</div><!-- end <div class="nav accordion" id="accordionSidenav"> -->
     </div><!-- end <div class="sidenav-menu"> -->
	  
	  <!-- Sidenav Footer-->
	  <div class="sidenav-footer">
	    <div class="sidenav-footer-content">
	      <div class="sidenav-footer-subtitle">Logged in as:</div>
	      <div class="sidenav-footer-title">Valerie Luna</div>
	    </div>
	  </div>
	</nav>
</div>