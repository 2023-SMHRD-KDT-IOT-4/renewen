//weather.js
const button = document.querySelector('.button');
const tempSection = document.querySelector('.temperature');
const placeSection = document.querySelector('.place');
const descSection = document.querySelector('.description');
const iconSection = document.querySelector('.icon');

const API_KEY = '5d3b52e64fc331fa29bf21b358d19183'
// 함수 바꾸기
button.addEventListener('click', () => {
    navigator.geolocation.getCurrentPosition(success, fail);
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
        const place = json.name;
        const description = json.weather[0].description;

        tempSection.innerText = temperature;
        placeSection.innerText = place;
        descSection.innerText = description;
    })
    .then((json) => {
        const icon = json.weather[0].icon;
        const iconURL = `http://openweathermap.org/img/wn/${icon}@2x.png`;

        iconSection.setAttribute('src', iconURL);
    })

}