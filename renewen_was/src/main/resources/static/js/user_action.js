/*top nav 회원정보 토글 클릭이벤트 추가*/

    // JavaScript를 사용하여 클릭 이벤트 추가
    document.getElementById("loginButton").addEventListener("click", function() {
        // 페이지 이동 또는 다른 동작 수행
        window.location.href = "login.jsp";
    });

     // 로그아웃 버튼에 대한 클릭 이벤트
    document.getElementById("logoutButton").addEventListener("click", function() {
        // 페이지 이동 또는 다른 동작 수행
        window.location.href = "${contextPath}/user/logout";
    });

    // 회원 정보 수정 버튼에 대한 클릭 이벤트
    document.getElementById("updateButton").addEventListener("click", function() {
        // 페이지 이동 또는 다른 동작 수행
        window.location.href = "${contextPath}/user/update";
    });
    // 로그아웃 버튼 아이콘 크기 변경
document.getElementById("logoutButton").querySelector(".dropdown-item-icon").style.fontSize = "20px";

// 회원 정보 수정 버튼 아이콘 크기 변경
document.getElementById("updateButton").querySelector(".dropdown-item-icon").style.fontSize = "20px";