/**
 * 
 */

$(document).ready(function() {
	console.log('dashboard js');
	console.log('dashboard2 js');
	const contextPath = $("#contextPath").val();
	const genElecUrl = contextPath + '/plant/gen/elec';
	
		// 선택 발전소 식별번호
	 let plantNo = $("#plantList").val();
	 fetchGenElec(genElecUrl, plantNo);

	 // 발전소 변경 시 
	 $("#plantList").change(function() {
  	plantNo = $(this).val();
  	// 발전소 발전량 fetch ==> span에 값 출력
   	fetchGenElec(genElecUrl, plantNo);
   	
   	
  });
  
});


const fetchGenElec = (url, plantNo) => {
	
	console.log('fetch' + plantNo);
	
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
    },
    success: function(response) {
        console.log('Received data:', response);
        const {totalWatt, currentWatt} = response;
        $("#spanCurrentWatt").text(currentWatt);
        $("#spanTotalWatt").text(totalWatt);
    },
    error: function(xhr, status, error) {
        $("#spanCurrentWatt").text('N/A');
        $("#spanTotalWatt").text('N/A');
  	}
	});
}



// 차트를 그릴 div 요소 가져오기
var chartDom1 = document.getElementById('chart1');
// 데이터 예시 (타임라인을 그릴 여러 시계열 데이터)
var data1 = [
    { time: '2022-01-01', value: 100 },
    { time: '2022-02-01', value: 120 },
    { time: '2022-03-01', value: 140 },
    { time: '2022-04-01', value: 110 },
    { time: '2022-05-01', value: 130 },
    // ...
];
// 타임라인 차트 옵션 설정
var options1 = {
    baseOption: {
        timeline: {
            axisType: 'category',
            autoPlay: true,
            data: data1.map(item => item.time), // 타임라인 데이터
            label: {
                formatter: '{value}', // 라벨 형식
                interval: 1 // 라벨 간격
            }
        },
        xAxis: {
            type: 'category',
            data: data1.map(item => item.time) // x축 데이터
        },
        yAxis: {
            type: 'value' // y축은 값 형식
        },
        series: [{
            type: 'line',
            data: data1.map(item => item.value) // 시계열 데이터
        }]
    },
    options: []
};
// 차트 생성
var chart1 = echarts.init(chartDom1);
// 차트 옵션 적용
chart1.setOption(options1);



console.log('renwen chart');

// 차트를 그릴 div 요소 가져오기
var chartDom = document.getElementById('chartPredictElec');
var data = [
    { time: '00:00', value: 100 },
    { time: '01:00', value: 120 },
    { time: '02:00', value: 140 },
    { time: '03:00', value: 110 },
    { time: '04:00', value: 130 },
    { time: '05:00', value: 150 },
    { time: '06:00', value: 160 },
    { time: '07:00', value: 180 },
    { time: '08:00', value: 200 },
    { time: '09:00', value: 190 },
    { time: '10:00', value: 210 },
    { time: '11:00', value: 220 },
    { time: '12:00', value: 230 },
    { time: '13:00', value: 240 },
    { time: '14:00', value: 250 },
    { time: '15:00', value: 260 },
    { time: '16:00', value: 270 },
    { time: '17:00', value: 280 },
    { time: '18:00', value: 290 },
    { time: '19:00', value: 300 },
    { time: '20:00', value: 310 },
    { time: '21:00', value: 320 },
    { time: '22:00', value: 330 },
    { time: '23:00', value: 340 }
];
// 시간대별 추이 차트 옵션 설정
var options = {
    // 차트 타입: 추이(시계열) 차트
    xAxis: {
        type: 'category', // x축은 카테고리형(시간대)
        data: data.map(item => item.time) // 시간대 데이터
    },
    yAxis: {
        type: 'value' // y축은 값 형식
    },
    series: [{
        data: data.map(item => item.value), // 데이터
        type: 'line' // 선 그래프 타입
    }]
};
// 차트 생성
var chart = echarts.init(chartDom);
// 차트 옵션 적용
chart.setOption(options);
