<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<title>renewen</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="icon" type="image/x-icon"
	href="${contextPath}/assets/img/favicon.png" />
<link href="${contextPath}/css/styles.css" rel="stylesheet" />
<link href="${contextPath}/css/renewen_plant.css" rel="stylesheet" />
<link
	href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css"
	rel="stylesheet" />

<script data-search-pseudo-elements defer
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>


</head>
<body class="nav-fixed">

	<jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />

	<div id="layoutSidenav">
		<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />
		<div id="layoutSidenav_content">
			<main>
				<header
					class="page-header page-header-dark bg-gradient-primary-to-secondary pb-10">
					<div class="container-xl px-4">
						<div class="page-header-content pt-4">
							<div class="row align-items-center justify-content-between">
								<div class="col-auto mt-4">
									<h1 class="page-header-title">
										<div class="page-header-icon">
											<i data-feather="filter"></i>
										</div>
										승인 대기 목록
									</h1>
									<!-- <div class="page-header-subtitle">An extension of the Simple DataTables library, customized for SB Admin Pro</div> -->
								</div>
							</div>
						</div>
					</div>
				</header>
				<!-- Main page content-->
				<div class="container-xl px-4 mt-n10">
					<div class="row">
						<div class="col-lg-15">
							<div class="card mb-4" id="plantList">
								<div class="card-header">${user.userId}님의승인을기다리고있어요</div>
								<div class="card-body">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>No</th>
												<th>사용자아이디</th>
												<th>사업자등록번호</th>
												<th>발전소 이름</th>
												<th>주소</th>
												<th><input type="checkbox" id="selectAllCheckbox">
													<label for="selectAllCheckbox">모두 선택</label></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="vo" items="${list}" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${vo.userId}</td>
													<c:set var="num" value="${vo.brNumber}" />
													<td>${fn:substring(num,0,3)}-${fn:substring(num,3,5)}-${fn:substring(num,5,10)}</td>
													<td>${vo.plantName}</td>
													<td>${vo.plantAddr}${vo.plantAddr2}</td>
													<td><input type="checkbox" name="selectedItems"
														value="${vo.plantNo}"></td>
												</tr>

												</tr>
											</c:forEach>

										</tbody>

									</table>
								</div>
							</div>
							<!-- end card-plantList -->
							<!-- 승인버튼 가운데 정렬 -->
							<div class="container">
								<div class="row justify-content-center">
									<div class="col-auto">
										<button id="approveSelectedBtn"
											class="btn btn-primary btn-lg px-5 py-3">선택 항목 승인하기</button>
									</div>
								</div>
							</div>
							<!-- 모달 -->
							<div class="modal fade" id="approveModal" tabindex="-1"
								aria-labelledby="approveModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="approveModalLabel">항목 승인</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<p>다음 항목을 승인하시겠습니까?</p>
											<ul id="selectedItemsList"></ul>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-danger"
												data-bs-dismiss="modal">취소</button>
											<button type="button" class="btn btn-success"
												onclick="confirmApproval()">승인</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</main>

			<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />
		</div>
		<!-- end <div id="layoutSidenav_content"> -->
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest"
		crossorigin="anonymous"></script>
	<script src="${contextPath}/js/datatables/datatables-simple-demo.js"></script>
	<!-- renewen -->

	<!-- 추가 현정 -->
	<script src="${contextPath}/js/checkbox.js"></script>
	<script>
		$(document)
				.ready(
						function() {
							$('#approveSelectedBtn')
									.click(
											function() {
												var selectedItems = [];
												$(
														'input[name="selectedItems"]:checked')
														.each(
																function() {
																	selectedItems
																			.push($(
																					this)
																					.closest(
																							'tr')
																					.find(
																							'td:eq(3)')
																					.text());
																});

												if (selectedItems.length === 0) {
													alert('선택된 항목이 없습니다.');
													return;
												}

												var selectedItemsList = $('#selectedItemsList');
												selectedItemsList.empty();
												selectedItems.forEach(function(
														item) {
													selectedItemsList
															.append('<li>'
																	+ item
																	+ '</li>');
												});

												$('#approveModal')
														.modal('show');
											});

							$('#confirmApprovalBtn').click(function() {
								// 여기에 선택한 항목을 승인하는 로직을 추가
								alert('선택한 항목을 승인했습니다.');
								$('#approveModal').modal('hide');
							});
						});
	</script>

</body>
</html>
