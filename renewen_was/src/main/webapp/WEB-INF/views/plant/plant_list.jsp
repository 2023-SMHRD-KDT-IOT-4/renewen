<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
  
  <script data-search-pseudo-elements defer src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js" crossorigin="anonymous"></script>
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
                                <div class="page-header-icon"><i data-feather="filter"></i></div>
                                발전소 리스트 
                            </h1>
                            <!-- <div class="page-header-subtitle">An extension of the Simple DataTables library, customized for SB Admin Pro</div> -->
                        </div>
                    </div>
                </div>
            </div>
        </header>
        
        <!-- Main page content-->
        <div class="container-xl px-4 mt-n10">
        	<div class="card mb-4">
          	<div class="card-header">${user.userId}회원님의 발전소 리스트</div>
            	<div class="card-body">
              	<table class="table table-hover">
                	<thead>
	                  <tr>
	                    <th>No</th>
	                    <th>발전소 이름</th>
	                    <th>주소</th>
	                    <th>사업자등록번호</th>
	                    <th>연동키</th>
	                    <th>승인여부</th>
	                    <th>연동시간</th>
	                  </tr>
                   </thead>

                   <tbody>
                   
                   	<c:forEach var="vo" items="${list}" varStatus="status">
                   		<tr>
	                      <td>${status.count}</td>
	                      <td>${vo.plantName}</td>
	                      <td>${vo.plantAddr} ${vo.plantAddr2}</td>
	                     <c:set var="num" value="${vo.brNumber}"/>
	                     <td>${fn:substring(num,0,3)}-${fn:substring(num,3,5)}-${fn:substring(num,5,10)}</td>
	                     
	                     <c:choose>
	                     	<c:when test="${empty vo.plantLinkKey}">
	                     		<td>미연동</td>
                     		</c:when>
	                     	<c:otherwise>
		                      <td>${vo.plantLinkKey}</td>
	                     	</c:otherwise>
	                     </c:choose> 
	                      <td>${vo.grantYn}</td>
	                      <td>${vo.createdAt}</td>
	                      
	                      <td>
	                        <!-- <button  class="btn btn-datatable btn-icon btn-transparent-dark me-2"><i data-feather="more-vertical"></i></button> -->
	                        <a href="${contextPath}/plant/update?plantNo=${vo.plantNo}" class="btn btn-datatable btn-icon btn-transparent-dark me-2"><i data-feather="more-vertical"></i></a>
	                        <!-- <button  class="btn btn-datatable btn-icon btn-transparent-dark"><i data-feather="trash-2"></i></button> -->
	                        <a href="${contextPath}/plant/list/delete?plantNo=${vo.plantNo}" onclick="return delete_event()" class="btn btn-datatable btn-icon btn-transparent-dark"><i data-feather="trash-2"></i></a>
	                      </td>
	                      <c:if test="${vo.grantYn eq 'Y'}">
	                      	<td><button>발전 셀 연동</button></td>			                      	
	                      </c:if>
                    	</tr>
                   	</c:forEach>
                   	
                   </tbody>
                   
              </table>
         		</div>
	   			</div>
     		</div>
    	</main>
    	
    	<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>
		</div> <!-- end <div id="layoutSidenav_content"> -->
  </div>
  
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
  <script src="${contextPath}/js/datatables/datatables-simple-demo.js"></script>	
	<!-- renewen -->  
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  
  
  <script type="text/javascript">
  	function delete_event(){
  		 if(confirm("발전소를 삭제하시겠습니까?") == true){
             return true; 
          }else{
             return false; 
          }
  	}
  </script>

</body>
</html>

