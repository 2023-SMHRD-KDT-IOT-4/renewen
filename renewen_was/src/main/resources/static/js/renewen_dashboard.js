/**
 * 
 */

$(document).ready(function() {
	console.log('dashboard js');
	const contextPath = $("#contextPath").val();
	const genElecUrl = contextPath + '/plant/gen/elec';
	const weatherUrl = contextPath + '/api/was/weather/list';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	let stnNo = "156";
 	// 1. 금일 발전량 텍스트 출력	
	fetchGenElec(genElecUrl, plantNo); 

	// ['00:00', '01:00', '02:00', ..., '23:00']
	let timeArray = seriesData1.map(data => data.time);
	
	// 2. 금일 발전량 추이 차트 출력
	printPredictChart("chartPredictElec", plantNo);

	// 3. 금일 기상 차트 출력
	printWeatherChart(weatherUrl, stnNo, timeArray);
	
	
	// ====================================================================

	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  	// 발전소 발전량 fetch ==> 차트 출력
   	fetchGenElec(genElecUrl, plantNo);
  });
  
}); // document


const fetchGenElec = (url, plantNo) => {
	
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
    },
    success: function(response) {
        console.log('Received data:', response);
        const {totalWatt, currentWatt} = response;
        const expectedWatt = totalWatt * 0.8;
        
        drawGenElecChart(currentWatt, totalWatt, expectedWatt);
        // $("#spanCurrentWatt").text(currentWatt);
        // $("#spanTotalWatt").text(totalWatt);
        // $("#spanExpectedWatt").text(expectedWatt);
    },
    error: function(xhr, status, error) {
				drawGenElecChart(0, 0, 0);
        // $("#spanCurrentWatt").text('N/A');
        // $("#spanTotalWatt").text('N/A');
  	}
	});
}// fetchGenElec



// 기상 차트 출력
const printWeatherChart = (url, stnNo = 156, timeArray) => {
	
	const findTypes = ['TA','SI','WS'];
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        stnNo: stnNo,
        type: findTypes.join(',')
    },
    success: function(response) {
        console.log('Received data:', response);
        drawWeatherChart(response, timeArray)
        
    },
    error: function(xhr, status, error) {
        console.error(error);
  	}
	});

}

const drawWeatherChart = (dataObj = {}, timeArray = []) => {
	
	const dom = document.getElementById("chartWeather");
  const myChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });

	let siList = dataObj['SI'].map(item => item.weatherValue);
	let wsList = dataObj['WS'].map(item => item.weatherValue);
	let tempList = dataObj['TA'].map(item => item.weatherValue);
  let option;

  const colors = ['#5470C6', '#91CC75', '#EE6666'];
  option = {
    color: colors,
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      }
    },
    grid: {
      right: '20%'
    },
    toolbox: {
      feature: {
        dataView: { show: true, readOnly: false },
        // restore: { show: false },
        saveAsImage: { show: true }
      }
    },
    legend: {
      data: ['기온', '일사량', '풍속']
    },
    xAxis: [
      {
        type: 'category',
        axisTick: {
          alignWithLabel: true
        },
        data: timeArray
      }
    ],
    yAxis: [
			// SI 일사량
      {
        type: 'value',
        name: '일사량',
        position: 'right',
        alignTicks: true,
        axisLine: {
          show: true,
          lineStyle: {
            color: colors[0]
          }
        },
        axisLabel: {
          formatter: '{value}MJ/m2'
        }
      },
      {
        type: 'value',
        name: '풍속',
        position: 'right',
        alignTicks: true,
        offset: 80, // 1번 y축간 간격
        axisLine: {
          show: true,
          lineStyle: {
            color: colors[1]
          }
        },
        axisLabel: {
          formatter: '{value}(m/s)'
        }
      },
      {
        type: 'value',
        name: '기온',
        position: 'left',
        alignTicks: true,
        axisLine: {
          show: true,
          lineStyle: {
            color: colors[2]
          }
        },
        axisLabel: {
          formatter: '{value}°C'
        }
      }
    ],
    series: [
      {
        name: '일사량', // SI
        type: 'bar',
        data: siList
      },
      {
        name: '풍속', // WS
        type: 'bar',
        yAxisIndex: 1,
        data: wsList
      },
      {
        name: '기온', // TA
        type: 'line',
        yAxisIndex: 2,
        data: tempList
      }
    ]
  };

  if (option && typeof option === 'object') {
    myChart.setOption(option);
  }

  window.addEventListener('resize', myChart.resize);
}

// 발전량 추이 차트
const printPredictChart = (divId, plantNo = 0) => {
	const chart = echarts.init(document.getElementById(divId)); // 차트 생성
	
	seriesData2.forEach(item => {
    // 현재 시간과 비교하여 현재 시간 이후인 경우 value에 null 할당
    if (item.time > getCurrentTime()) {
        item.value = null;
    } else {
	    item.value -= 50; // 이전에 주석 처리한 코드
		}
    
	});
	
	
	let options = { // 차트 옵션 설정
      title: {
          text: '발전량 추이 차트'
      },
      tooltip: {
          trigger: 'axis'
      },
      legend: {
          data: ['Series 1', 'Series 2']
      },
	    xAxis: {
	        type: 'category', // x축은 카테고리형(시간대)
	        boundaryGap: false,
	        data: seriesData1.map(item => item.time) // 시간대 데이터
	    },
	    yAxis: {
	        type: 'value' // y축은 값 형식
	    },
	    series: [
				{
					name: '예상발전량',
	        type: 'line',
	        data: seriesData1.map(item => item.value), 
	    	},
        {
            name: '실제발전량',
            type: 'line',
            data: seriesData2.map(item => item.value)
        }
	    
	    ]
	};
	
	chart.setOption(options);
	 
} // printPredictchart

let seriesData1 = [
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

let seriesData2 = seriesData1.map(obj => Object.assign({}, obj));


function getCurrentTime() {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    return hours + ':' + minutes;
}