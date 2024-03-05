// weather.js (JavaScript에서 HTML 문서의 특정 요소 찾기)
// getElementById(' ') => HTML 요소의 고유한 id 속성으로 해당 요소 찾기
const tempSection = document.querySelector('.temperature');
const descSection = document.querySelector('.description');
const iconSection = document.querySelector('.icon');

const API_KEY = '5d3b52e64fc331fa29bf21b358d19183'

// 함수 바꾸기
/*button.addEventListener('click', () => {
    navigator.geolocation.getCurrentPosition(success, fail);
});*/

// 페이지 로드 시, 날씨정보 호출
document.addEventListener('DOMContentLoaded', ()=> {
	getWeatherInfo();
});

// 현재 위치 날씨 정보 가져오기
const getWeatherInfo = () => {
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(success, fail);
	} else {
		alert("사용자의 위치 정보를 알 수 없습니다.")
	}
};


// 현재 위치 가져오기 성공
const success = (position) => {
    console.log(position);
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    getWeather(latitude, longitude);
};

// 실패
const fail = () => {
    alert("좌표를 받아올 수 없습니다.")
}

// OpenWeatherMap API를 통해 날씨 정보 가져오기
const getWeather = (lat, lon) => {
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`)
   	 .then((response) => {
			if(!response.ok){
				throw new Error('날씨 데이터를 가져오지 못했습니다.');
			}
        	return response.json();
    })
    .then((json)=> {
		const temperature = json.main.temp; 
		const description = json.weather[0].description;
		const icon = json.weather[0].icon;
		
		// 화면 표시
		temperature.innerText = temperature;
		description.innerText = description;
	
		// 아이콘 설정
		const iconURL = `http://openweathermap.org/img/wn/${icon}@2x.png`;
		iconSection.setAttribute('src', iconURL);
	})
	.catch((error) => {
		console.error('날씨 데이터를 가져오는 중 오류 발생:', error);
		alert('날씨 데이터를 가져오지 못했습니다. 다시 시도해 주세요.');
	});

}