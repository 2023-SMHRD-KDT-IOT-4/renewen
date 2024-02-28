
        // 페이지가 로드될 때 실행되는 함수
       window.onload = function() {
				 	console.log("chart");
            // 도넛 차트 데이터
            var doughnutChartData = {
                labels: ["현재 발전 용량", "최대 발전 용량"],
                datasets: [{
                    data: [30, 70], // 각 항목의 값, 합이 100이 되도록 조정 필요
                    backgroundColor: ["#a7aeb8", "#FFD700"], // 각 항목의 배경색
                    hoverBackgroundColor: ["#666666", "#FFA500"] // 각 항목의 호버 배경색
                }]
            };

            // 도넛 차트 옵션
            var doughnutChartOptions = {
                responsive: true,
                cutoutPercentage: 70, // 도넛 차트의 중심 부분이 빈 공간의 크기 설정 (0 ~ 100 사이의 값)
                legend: {
                    display: false // 라벨 숨김
                },
                animation: {
                    animateRotate: true, // 회전 애니메이션 사용 여부
                    animateScale: true // 크기 변화 애니메이션 사용 여부
                },
                tooltips: {
                    callbacks: {
                        label: function(tooltipItem, data) {
                            // 항목의 값과 데이터셋 인덱스 가져오기
                            var dataset = data.datasets[tooltipItem.datasetIndex];
                            var value = dataset.data[tooltipItem.index];
                            return value + ' kW'; // 단위를 추가하여 반환
                        }
                    }
                }
            };

            // 도넛 차트 생성
            var ctx = document.getElementById("myDoughnutChart").getContext('2d');
            var myDoughnutChart = new Chart(ctx, {
                type: 'doughnut',
                data: doughnutChartData,
                options: doughnutChartOptions
            });
        };


  // 발전소별 발전 용량 데이터 (마스터용)
//   var powerPlantData = {
//     labels: ["발전소 A", "발전소 B", "발전소 C"],
//     datasets: [{
//         data: [500, 700, 300], // kW 단위로 발전 용량 데이터 설정
//         backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56"]
//     }]
// };

// 파이 차트 옵션
// var pieChartOptions = {
//     responsive: true,
//     animation: {
//         animateRotate: true, // 회전 애니메이션 활성화
//         animateScale: true // 크기 애니메이션 활성화
//     }
// };

// 파이 차트 생성
// var ctx = document.getElementById("myPieChart").getContext('2d');
// var myPieChart = new Chart(ctx, {
//     type: 'pie',
//     data: powerPlantData,
//     options: pieChartOptions
// });



// myPlantChart


var renewableEnergyData = {
    labels: ["현재 발전 용량 (kW)", "최대 발전 용량 (kW)"],
    datasets: [{
        data: [150, 200], // 현재 발전 용량과 최대 발전 용량 데이터 (단위: kW)
        backgroundColor: ["#FF6384", "#36A2EB"]
    }]
};

// 도넛 차트 옵션
var doughnutChartOptions = {
    responsive: true,
    animation: {
        animateRotate: true, // 회전 애니메이션 활성화
        animateScale: true // 크기 애니메이션 활성화
    }
};

// 도넛 차트 생성
var ctx = document.getElementById("myPlantChart").getContext('2d');
var myDoughnutChart = new Chart(ctx, {
    type: 'doughnut',
    data: renewableEnergyData,
    options: doughnutChartOptions
});