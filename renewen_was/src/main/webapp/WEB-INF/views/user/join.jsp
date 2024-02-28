<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

			<script data-search-pseudo-elements defer
				src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
				crossorigin="anonymous"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js"
				crossorigin="anonymous"></script>
		</head>

		<body class="nav-fixed">

			<jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />

			<div id="layoutSidenav">

				<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />

				<div id="layoutSidenav_content">
					<main>
						<div class="container-xl px-4">
							<div class="row justify-content-center">
								<div class="col-lg-5">

									<!-- 회원가입 -->
									<div class="card shadow-lg border-0 rounded-lg mt-5">
										<div class="card-header justify-content-center">
											<h3 class="fw-light my-4">회원가입</h3>
										</div>
										<div class="card-body">
											<!--  form-->
											<form>
												<!-- id-->
												<div class="mb-3">
													<label class="mb-1">아이디</label>
													<input class="form-control" id="userId" type="text"
														placeholder="아이디를 입력하세요">
												</div>
												<!-- password-->
												<div class="mb-3">
													<label class="mb-1">비밀번호</label>
													<input class="form-control" id="userPw" type="password"
														placeholder="비밀번호를 입력하세요" />
												</div>
												<!-- email-->
												<div class="mb-3">
													<label class="mb-1">이메일</label>
													<input class="form-control" id="userEmail" type="email"
														placeholder="이메일을 입력하세요" />
												</div>
												<!-- 권한 선택-->
												<div class="mb-3">
													<label for="authSelect">권한 선택</label>
													<select class="form-control" id="authSelect1">
														<option>일반 사용자</option>
														<option>발전소 관리자</option>
													</select>
												</div>
												<!-- 추가정보 발전소관리자 선택 시 -->
												<div id="authId" style="display: none;">
													<div class="mb-3">
														<label class="mb-1">사용자 권한 id</label>
														<input class="form-control" id="authId" type="text"
															placeholder="사용자 권한 ID를 입력하세요" />
													</div>
													<div class="mb-3">
														<label class="mb-1">사용자 회사</label>
														<input class="form-control" id="userCompany" type="text"
															placeholder="회사명을 입력하세요" />
													</div>
													<div class="mb-3">
														<label class="mb-1">담당 직위</label>
														<input class="form-control" id="managePosition" type="text"
															placeholder="담당 직위를 입력하세요" />
													</div>
													<div class="mb-3">
														<label class="mb-1">담당 업무명</label>
														<input class="form-control" id="manageTask" type="text"
															placeholder="담당 업무를 입력하세요" />
													</div>
												</div>


												<!-- 로그인 버튼 -->
												<div class="d-grid">
													<a class="btn btn-primary" href="#">로그인</a>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</main>

					<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />
				</div> <!-- end <div id="layoutSidenav_content"> -->
			</div>

			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
				crossorigin="anonymous"></script>
			<script src="${contextPath}/js/userJoin.js"></script>
			<!-- renewen -->

			<script src="${contextPath}/js/scripts.js"></script>
			<script src="${contextPath}/js/userJoin.js"></script>
			<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		</body>

		</html>