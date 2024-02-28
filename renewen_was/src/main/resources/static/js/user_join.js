var authSelect = document.getElementById("authSelect1");
var authIdSection = document.getElementById("authId");

// 권한 선택 드롭다운 메뉴가 변경될 때마다 호출
authSelect.addEventListener("change", function () {
    // 선택한 값이 "발전소 관리자"인지 확인
    if (authSelect.value === "발전소 관리자") {
        // "발전소 관리자"가 선택된 경우 추가 정보 섹션을 표시
        authIdSection.style.display = "block";
    } else {
        // 다른 권한이 선택된 경우 섹션을 숨김
        authIdSection.style.display = "none";
    }
});
