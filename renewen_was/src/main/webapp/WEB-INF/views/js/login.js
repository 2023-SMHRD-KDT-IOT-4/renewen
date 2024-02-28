// 토글을 위한 변수 설정
let isLoggedIn = false;

// 로그인 및 로그아웃 링크 요소 가져오기
const loginLink = document.getElementById('login-link');
const logoutLink = document.getElementById('logout-link');

// 로그인 및 로그아웃 링크에 클릭 이벤트 추가
var button = document.getElementById('myButton');
button.addEventListener('click', function() {
    // Event handler code here
});
loginLink.addEventListener('click', function(event) {
    event.preventDefault(); // 링크의 기본 동작 방지
    isLoggedIn = true; // 로그인 상태 업데이트
    updateLoginState(); // 로그인 상태에 따라 UI 업데이트
});

logoutLink.addEventListener('click', function(event) {
    event.preventDefault(); // 링크의 기본 동작 방지
    isLoggedIn = false; // 로그아웃 상태 업데이트
    updateLoginState(); // 로그인 상태에 따라 UI 업데이트
});

// 로그인 상태에 따라 UI 업데이트하는 함수 정의
function updateLoginState() {
    if (isLoggedIn) {
        loginLink.style.display = 'none'; // 로그인 링크 숨기기
        logoutLink.style.display = 'inline-block'; // 로그아웃 링크 표시
    } else {
        loginLink.style.display = 'inline-block'; // 로그인 링크 표시
        logoutLink.style.display = 'none'; // 로그아웃 링크 숨기기
    }
}

// 페이지 로드시 초기 로그인 상태에 따라 UI 업데이트
window.onload = function() {
    updateLoginState();
};
