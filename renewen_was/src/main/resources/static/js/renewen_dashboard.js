/**
 * 
 */

$(document).ready(function() {
	console.log('dashboard js');
	const contextPath = $("#contextPath").val();
	const genElecUrl = contextPath + '/plant/gen/elec';
	const genTimeElecUrl = contextPath + '/plant/gen_time/elec';
	const weatherUrl = contextPath + '/api/was/weather/list';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	let stnNo = "156";

 	// 1. 금일 발전량 차트 출력	
	fetchGenElec(genElecUrl, plantNo); 
	
	// 2. 금일 발전량 추이 차트 출력
	fetchPredict(genTimeElecUrl, plantNo);

	// 3. 금일 기상 차트 출력
	fetchWeather(weatherUrl, stnNo);
	
	
	// ====================================================================

	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  	console.log(plantNo);
  	// 1. 금일 발전량 차트 출력
   	fetchGenElec(genElecUrl, plantNo);
 		// 2. 금일 발전량 추이 차트 출력
		fetchPredict(genTimeElecUrl, plantNo);
  });
  
}); // document

var timeData = [];
for (var i = 0; i < 24; i++) {
  var hour = (i < 10 ? '0' : '') + i + ':00'; // 시간 형식을 00:00으로 맞춤
  timeData.push(hour);
}

const fetchGenElec = (url, plantNo) => {
	
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
    },
    success: function(response) {
        console.log('fetchGenElec data:', response);
        const {totalWatt, currentWatt, predictWatt} = response;
        
        drawGenElecChart(currentWatt, totalWatt, predictWatt);
    },
    error: function(xhr, status, error) {
				console.error(error);
				drawGenElecChart(0, 0, 0);
  	}
	});
}// fetchGenElec

// 금일발전량 차트 출력
const drawGenElecChart = (currentWatt = 0, totalWatt = 0, predictWatt = 0) => {
	
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
	        data: [predictWatt],
	        color: '#fe413e'
	    }]
	});
	
}

const fetchPredict = (url, plantNo) => {
	
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo
    },
    success: function(response) {
        console.log('fetchPredict data:', response);
        const { genReal, genPredict } = response;
        printPredictChart(genReal, genPredict)
        
    },
    error: function(xhr, status, error) {
        console.error(error);
  	}
	});

}


const printPredictChart = (genRealData, genPredictData) => {
    
  let realList = [];
  let predictList = [];
  for(let i=0; i< timeData.length; i++) {
		let time = timeData[i];
		let predictVal = genPredictData[time] !== undefined ? genPredictData[time] : 0; 
		realList.push({ 'time' : time, 'value' : genRealData[time]  });
		predictList.push({ 'time' : time, 'value' : predictVal });
	}
	
	// 차트 생성
  const dom = document.getElementById('chartPredictElec');
  const predictChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });


  // 차트 옵션 설정
  let option = {
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
        restore: { show: false },
        saveAsImage: { show: true }
      }
    },
    legend: {
      data: ['실제 발전량', '예상 발전량']
    },
    xAxis: {
      type: 'category',
      data: timeData,
      axisTick: {
        alignWithLabel: true
      },
    },
    yAxis: [{
      type: 'value', // bar
      name: '실제 발전량(W)',
      position: 'left',
      axisLine: {
        show: true,
        lineStyle: {
          color: '#5470C6' 
        }
      }
    }, {
      type: 'value', // line
      name: '예상 발전량(W)',
      position: 'right',
      show: true,
      axisLine: {
        lineStyle: {
          color: '#008000' 
        }
      }
    }],
    series: [{
      name: '실제 발전량',
      type: 'bar',
      yAxisIndex: 0, 
      tooltip: {
        valueFormatter: function (value) {
          return value + 'W';
        }
      },         
      data: realList.map(item => item.value)
    }, {
      name: '예상 발전량',
      type: 'line',
      yAxisIndex: 1, 
      tooltip: {
        valueFormatter: function (value) {
          return value + 'W';
        }
      },         
      data: predictList.map(item => item.value)
    }]
  };
  // 차트에 옵션 설정
  predictChart.setOption(option);

}


// 3) 기상 데이터 
const fetchWeather = (url, stnNo = 156) => {
	
	const findTypes = ['TEMPERATURE','SI','WS']; // TA => TEMPERATURE
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        stnNo: stnNo,
        type: findTypes.join(',')
    },
    success: function(response) {
        console.log('fetchWeather data:', response);
        drawWeatherChart(response)
        
    },
    error: function(xhr, status, error) {
        console.error(error);
  	}
	});

}

// 3) 기상차트 그리기
const drawWeatherChart = (dataObj = { 'SI': [], 'WS': [], 'TEMPERATURE': [] }) => {

	const dom = document.getElementById("chartWeather");
  const weatherChart = echarts.init(dom, null, {
    renderer: 'canvas',
    useDirtyRect: false
  });

  let siList = dataObj['SI'] ? dataObj['SI'].map(item => item.weatherValue) : [];
  let wsList = dataObj['WS'] ? dataObj['WS'].map(item => item.weatherValue) : [];
  let tempList = dataObj['TEMPERATURE'] ? dataObj['TEMPERATURE'].map(item => item.weatherValue) : [];
  let option;

  const colors = ['#FFA500', '#91CC75', '#EE6666'];
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
        data: timeData
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
          formatter: '{value}(MJ/m2)'
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
        data: siList,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '(MJ/m2)';
        	}
        },        
      },
      {
        name: '풍속', // WS
        type: 'bar',
        yAxisIndex: 1,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '(m/s)';
        	}
        },
        data: wsList,
        
        
      },
      {
        name: '기온', // TA
        type: 'line',
        yAxisIndex: 2,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '°C';
        	}
        },        
        data: tempList
      }
    ]
  };

  if (option && typeof option === 'object') {
    weatherChart.setOption(option);
  }

  window.addEventListener('resize', weatherChart.resize);
} // drawWeatherChart


const getCurrentTime =() => {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    return hours + ':' + minutes;
}