<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<title>renewen</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- favicon2추가  -->
<link rel="icon" type="image/x-icon"
	href="${contextPath}/assets/img/favicon2.png" />

<link href="${contextPath}/css/styles.css" rel="stylesheet" />

<script data-search-pseudo-elements defer
	src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/js/all.min.js"
	crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/feather-icons/4.28.0/feather.min.js"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/feather-icons/dist/feather.min.css">
</head>
<body class="nav-fixed">

	<jsp:include page="/WEB-INF/views/layouts/topnav.jsp" />

	<div id="layoutSidenav">
		<jsp:include page="/WEB-INF/views/layouts/sidenav.jsp" />
		<div id="layoutSidenav_content">
			<main>
				<!-- Main page content-->
				<div class="container px-4 mt-4">
					<!-- 발전소 등록 카드 -->
					<div class="row justify-content-center">
						<div class="col-md-5">
							<!-- 카드 크기 조정 -->
							<div class="card">
								<div class="card-body">
									<h1 class="text-center mb-1">발전소 등록</h1>
									<!-- 시간 -->
									<div class="small text-center mt-2"
										id="currentDateTimeContainer">
										<span id="currentYear"></span> &middot; <span
											id="currentMonth"></span> <span id="currentDayOfMonth"></span>
										&middot; <span class="fw-500 text-primary"
											id="currentDayOfWeek"></span> &middot; <span id="currentHour"></span>:<span
											id="currentMinute"></span> <span id="currentPeriod"></span>
									</div>
									<!-- 발전소 이름 입력 폼 -->
									<div class="d-flex justify-content-center mt-4 ">
										<!-- d-flex, justify-content-center 클래스 추가 -->
										<form action="${contextPath}/plant/register" method="post"
											onsubmit="return validateForm()" id="plantForm"
											class="flex-column">
											<!-- dummy - 관측소 지점번호, 위도, 경도 -->
											<input type="hidden" name="stnNo" value="156" /> <input
												type="hidden" name="latitude" id="latitude"> <input
												type="hidden" name="longitude" id="longitude">

											<div class="mb-2">
												<label for="plantName" class="form-label">발전소 이름</label> <input
													type="text" class="form-control" id="plantName"
													placeholder="발전소 이름 입력" name="plantName">
											</div>

											<div class="mb-2">
												<label for="plantAddr" class="form-label">발전소 주소</label>
												<div class="input-group">
													<input type="text" class="form-control" id="plantAddr" onclick="sample6_execDaumPostcode()" placeholder="발전소 주소 입력" name="plantAddr">
													<!--버튼색 변경 노랑  -->
													<button type="button" onclick="sample6_execDaumPostcode()" class="btn btn-warning">우편번호 찾기</button>
												</div>
												<input type="text" class="form-control mt-2" id="plantAddr2" placeholder="상세주소입력" name="plantAddr2">
											</div>

											<div class="mb-2">
												<label for="brNumber" class="form-label">사업자 등록 번호</label>
												<input type="text" class="form-control" id="brNumber" placeholder="사업자 번호 입력(숫자만 입력해주세요)" name="brNumber">
											</div>

											<div class="text-center mt-3 d-flex justify-content-center">
												<button type="submit" class="btn btn-primary btn-lg flex-grow-1" onclick="getLALOInfoAndSubmit()">등록</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
			</main>
			<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />
		</div>
		<!-- end <div id="layoutSidenav_content"> -->
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
	<script src="${contextPath}/js/scripts.js"></script>
	<!-- renewen -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
	<script src="${contextPath}/js/date.js"></script>

	<!-- 주소 API -->
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	
		<!-- 주소 검색 팝업 닫기  -->
	<script type="text/javascript">
		function closePopup() {
			// 주소 검색 팝업을 닫는 함수
			daum.postcode.resume();
		}
	</script>
	
	
	<script type="text/javascript">
	 //사업자번호
    function validateForm() {
        var brNumber = document.getElementById("brNumber").value;
        console.log(brNumber);

        // 입력값이 숫자 && 길이가 10이 아닌 경우
        if (!/^\d{10}$/.test(brNumber)) {	            
            return false;
        }
        return true;
    }
	</script>

	<script src="${contextPath}/js/address.js"></script>


</body>
</html>

