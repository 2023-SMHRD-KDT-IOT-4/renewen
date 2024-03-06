<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${not empty plantList}">
    <c:set var="selectedPlant" value="${plantList[0]}" />
</c:if>

<!DOCTYPE html>
<html lang="ko">
<head>
  <title>renewen</title>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
<!-- favicon2추가  -->
<link rel="icon" type="image/x-icon"
	href="${contextPath}/assets/img/favicon2.png" />
	
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
          
          <div class="d-flex justify-content-between align-items-sm-center flex-column flex-sm-row mb-4">
            <div class="me-4 mb-3 mb-sm-0">
              <h1 class="mb-0">발전소</h1>
              <div class="small">
								<select class="form-control" id="authSelect1" name="authId">
									<c:forEach items="${plantList}" var="vo">
										<option value="${vo.plantNo}">${vo.plantName}</option>
										</c:forEach>
									</select>
                <span class="fw-500 text-primary">Friday</span>
                · September 20, 2021 · 12:16 PM
              </div>
            </div>
            <!-- Date range picker example-->
            <div class="input-group input-group-joined border-0 shadow" style="width: 16.5rem">
            	<h2>d</h2>
            </div>
          </div>
          
          
          	<h4 class="mb-0 mt-5">Cells</h4>
          	<!-- <hr class="mt-2 mb-4"> -->
          	<div id="cellDiv" class="mt-3">
          		<div class="row">
          		
	          		<c:forEach var="cell" items="${selectedPlant.cellList}">
	          			<!-- 해당 셀의 마지막 촬영이미지 1장 -->
	          			<c:set var="cellImg" value="${cell.cellImgList[0]}" />
									<div class="col-lg-4 mb-4">
										<div class="card">
									    <img class="card-img-top" src="${contextPath}/imgs/${cellImg.imgFile}" alt="${vo.cellImgDesc}">
									    	<div class="card-body">
									        <h5 class="card-title">이미지 설명:${cellImg.cellImgDesc}</h5>
									        <h5 class="card-title">촬영시간:${cellImg.createdAt}</h5>
									        <h5 class="card-title">${cellImg.imgFile}</h5>
									        
									        <h6 class="card-subtitle mb-2 text-muted">Cell 정보</h6>
									    		<p class="card-text">${cell.cellType}</p>
									        <p class="card-text">${cell.cellSerialNum}</p>
									        <p class="card-text">${cell.cellVolume}</p>
									        <p class="card-text">
									        	${cell.cellWidth} x ${cell.cellHeight } x ${cell.cellDepth} (${cell.cellSizeUnit})
								        	</p>
								        	<a href="${contextPath}/plant/download/${cellImg.imgFile}" class="btn btn-primary">다운로드</a>
										    </div>
										</div>
									</div>
								</c:forEach>
								
							</div>
          	</div><!-- End cardDiv  -->
          	
          	<table class="table table-hover">
         			<tbody>
	 							<c:forEach var="vo" items="${imgList}">
	 								<tr>
	 									<td>
	 										<img src="${contextPath}/imgs/${vo.imgFile}" alt="${vo.cloudImgDesc}" width="200px" height="200px">
	 									</td>
        						<td><p>${vo.imgFile}</p></td>
										<td><p>${vo.cloudImgDesc}</p></td>
        						<td>
        						<a href="${contextPath}/plant/download/${vo.imgFile}" class="btn btn-primary">다운로드</a>
        						</td>
        						<td>
        						<a href="${contextPath}/plant/download?no=${vo.csNo}&type=cloud" class="btn btn-primary">aaa</a>
        						</td>
        					</tr>
								</c:forEach>
          		</tbody>
						</table>
						
        </div><!-- end <div class="container-xl px-4 mt-5"> -->
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

