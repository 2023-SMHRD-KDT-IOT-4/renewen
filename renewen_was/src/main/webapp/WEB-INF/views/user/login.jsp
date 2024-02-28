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

			<style>
				.login-form {
					width: 300px;
					/* 원하는 넓이로 조절하세요 */
					margin: auto;
					/* 가운데 정렬을 위해 */
				}

				.login-form .form-control {
					width: 100%;
					/* 폼 요소를 부트스트랩 스타일에 맞게 넓히기 */
				}
			</style>
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

									<!-- 로그인-->
									<div class="card shadow-lg border-0 rounded-lg mt-5">
										<div class="card-header justify-content-center">
											<h3 class="fw-light my-4">로그인</h3>
										</div>
										<div class="card-body">
											<!-- Login form-->
											<form>
												<!-- id-->
												<div class="mb-3">
													<label class="mb-1">아이디</label>
													<input class="form-control" id="userId" type="text"
														placeholder="아이디를 입력하세요" />
												</div>
												<!-- password-->
												<div class="mb-3">
													<label class="mb-1">비밀번호</label>
													<input class="form-control" id="userPw" type="password"
														placeholder="비밀번호를 입력하세요" />
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
			<script src="${contextPath}/js/scripts.js"></script>
			<!-- renewen -->
			<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
		</body>

		</html>