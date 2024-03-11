function fetchWeatherData(latitude, longitude, iconElement, descriptionElement, temperatureElement) {
    const apiKey = "5d3b52e64fc331fa29bf21b358d19183";
    const apiUrl = `https://api.openweathermap.org/data/2.5/weather?lat=${latitude}&lon=${longitude}&appid=${apiKey}&units=metric&lang=kr`;
    
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('날씨 데이터를 가져오지 못했습니다.');
            }
            return response.json();
        })
        .then(data => {
            displayWeatherInfo(data, iconElement, descriptionElement, temperatureElement);
        })
        .catch(error => {
            console.error('날씨 데이터를 가져오는 중 오류 발생:', error);
            alert('날씨 데이터를 가져오지 못했습니다. 다시 시도해 주세요.');
        });
}

function displayWeatherInfo(weatherData, iconElement, descriptionElement, temperatureElement) {
    const weatherIcon = weatherData.weather[0].icon;
    const weatherDescription = weatherData.weather[0].description;
    const temperature = weatherData.main.temp;

    const iconURL = `http://openweathermap.org/img/wn/${weatherIcon}.png`;
    iconElement.setAttribute('src', iconURL);
    descriptionElement.textContent = weatherDescription;
    temperatureElement.textContent = `${temperature}°C`;
}



function fetchPlantWeather() {
    const plantRows = document.querySelectorAll("#plantList tbody tr");
    const fetchPromises = []; // fetch 요청을 저장할 배열 생성
    
    plantRows.forEach((row, index) => {
        const latitudeElement = row.querySelector(".latitude"); // 위도 요소 찾기
        const longitudeElement = row.querySelector(".longitude"); // 경도 요소 찾기
        
        if (latitudeElement && longitudeElement) {
            const latitude = latitudeElement.textContent.trim(); // 위도 가져오기
            const longitude = longitudeElement.textContent.trim(); // 경도 가져오기

            // 발전소 정보를 표시하는 HTML 요소 찾기
            const weatherInfoContainer = document.querySelector(`#weather-info-${index + 1}`); // 수정: document 전체에서 찾도록 변경
            if (weatherInfoContainer) { // weatherInfoContainer가 null이 아닌지 확인
                const iconElement = weatherInfoContainer.querySelector(".icon");
                const descriptionElement = weatherInfoContainer.querySelector(".description");
                const temperatureElement = weatherInfoContainer.querySelector(".temperature");

                // 날씨 정보 가져오기
                const promise = fetchWeatherData(latitude, longitude, iconElement, descriptionElement, temperatureElement);
                fetchPromises.push(promise); // fetchPromise 배열에 Promise 추가
            } else {
                console.error(`발전소 정보를 표시하는 요소를 찾을 수 없습니다: #weather-info-${index}`);
            }
        } else {
            console.error('위도 또는 경도 정보를 찾을 수 없습니다.');
        }
    });

// 모든 fetch 요청이 완료될 때까지 기다리는 Promise 생성
    Promise.all(fetchPromises)
        .then(() => {
            console.log("모든 날씨 정보 요청이 완료되었습니다.");
        })
        .catch(error => {
            console.error('날씨 데이터를 가져오는 중 오류 발생:', error);
            alert('날씨 데이터를 가져오지 못했습니다. 다시 시도해 주세요.');
        });
}

document.addEventListener("DOMContentLoaded", function() {
    fetchPlantWeather();
});