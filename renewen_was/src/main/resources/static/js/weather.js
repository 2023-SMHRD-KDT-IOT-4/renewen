// weather.js (JavaScript에서 HTML 문서의 특정 요소 찾기)
// getElementById(' ') => HTML 요소의 고유한 id 속성으로 해당 요소 찾기
const tempSection = document.getElementById('.temperature');
const descSection = document.getElementById('.description');
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


const success = (position) => {
    console.log(position);
    const latitude = position.coords.latitude;
    const longitude = position.coords.longitude;

    getWeather(latitude, longitude);
}

const fail = () => {
    alert("좌표를 받아올 수 없음")
}

const getWeather = (lat, lon) => {
    fetch(
    `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`
    )
    .then((Response) => {
        return Response.json();
    })
    .catch((error) => {
        alert(error);
    })
    .then((json) => {
        const temperature = json.main.temp;
        const description = json.weather[0].description;

        tempSection.innerText = temperature;
        descSection.innerText = description;
    })
    .then((json) => {
        const icon = json.weather[0].icon;
        const iconURL = `http://openweathermap.org/img/wn/${icon}@2x.png`;

        iconSection.setAttribute('src', iconURL);
    })

}