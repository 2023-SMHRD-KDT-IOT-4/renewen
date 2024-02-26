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
     		<div class="container-xl px-4 mt-5">
        	<!-- Custom page header alternative example-->
	        <div class="d-flex justify-content-between align-items-sm-center flex-column flex-sm-row mb-4">
	          <div class="me-4 mb-3 mb-sm-0">
	          
						  <h2>Join form</h2>
						  <form action="${contextPath}/user/join" method="post">
						    <div class="mb-3 mt-3">
						      <label for="userId">id:</label>
						      <input type="text" class="form-control" id="userId" placeholder="Enter id" name="userId">
						    </div>
						    <div class="mb-3">
						      <label for="userPw">Password:</label>
						      <input type="password" class="form-control" id="userPw" placeholder="Enter password" name="userPw">
						    </div>
						    <button type="submit" class="btn btn-primary">Submit</button>
						  </form>
	            
            </div>
	        </div>
        </div>
    	</main>
    	
    	<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
		</div> <!-- end <div id="layoutSidenav_content"> -->
  </div>
  
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</body>
</html>

