<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<nav class="navbar navbar-expand-sm bg-light">
  <div class="container-fluid">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="${contextPath}">Home</a>
      </li>
    	<c:if test="${empty user}">
	      <li class="nav-item">
	        <a class="nav-link" href="${contextPath}/user/login">로그인</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="${contextPath}/user/join">회원가입</a>
	      </li>
    	</c:if>
    	<c:if test="${not empty user}">
	      <li class="nav-item">
	        <a class="nav-link" href="${contextPath}/user/logout">로그아웃</a>
	      </li>
	      <li class="nav-item">
	      	<span>${user.userCompany}</span>
	      </li>
    	</c:if>
    </ul>
  </div>
</nav>

