/*
var currentWatt = 100; // 예시 값, 실제로 사용할 데이터로 대체해야 함
var totalWatt = 500; // 예시 값, 실제로 사용할 데이터로 대체해야 함
var expectedWatt = 300; // 예시 값, 실제로 사용할 데이터로 대체해야 함
*/

// 금일발전량 차트 출력
const drawGenElecChart = (currentWatt, totalWatt, expectedWatt) => {
	
	Highcharts.chart('chart-container', {
	    accessibility: {
	        enabled: false
	    },
	    chart: {
	        type: 'bar'
	    },
	    title: {
	        text: ''
	    },
	    xAxis: {
	        categories: ['발전량'],
	        gridLineColor: '#808080', // X 축의 그리드 라인 색상 변경
	        gridLineWidth: 1 // X 축의 그리드 라인 두께 설정
	    },
	    yAxis: {
	        title: {
	            text: ''
	        },
	        gridLineColor: '#808080', // Y 축의 그리드 라인 색상 변경
	        gridLineWidth: 1, // Y 축의 그리드 라인 두께 설정
	    },
	    credits: { // 로고 제거를 위한 credits 옵션 추가
	        enabled: false
	    },
	    series: [{
	        name: '현재 발전량',
	        data: [currentWatt],
	        color: '#f9f043'
	    }, {
	        name: '누적 발전량',
	        data: [totalWatt],
	        color: '#f9ad43'
	    }, {
	        name: '예상 발전량',
	        data: [expectedWatt],
	        color: '#fe413e'
	    }]
	});
	
}

