/**
 * 
 */

$(document).ready(function() {
	console.log('dashboard js');
	const contextPath = $("#contextPath").val();

	const weatherUrl = contextPath + '/api/was/weather/list';
	//const cellSensingUrl = contextPath + '/plant/sensing/cell';
	//const cellSensingUrl = contextPath + '/plant/sensing';
	const sensingDataUrl = contextPath+'/plant/sensing';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	let stnNo = "156";
	console.log(plantNo);

	// 3. 금일 기상 차트 출력
	// fetchWeather(weatherUrl, stnNo);
	// 4. 발전셀 센싱데이터
	fetchCellSensing(sensingDataUrl, plantNo);
	
	// ====================================================================

	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  	console.log(plantNo);
		// 3. 발전셀 센싱데이터
		//fetchCellSensing(cellSensingUrl, plantNo);
		fetchCellSensing(sensingDataUrl, plantNo);
  });
  

  $("#weatherBtn").click(function() {
		fetchWeather(weatherUrl, plantNo);
	});
  $("#cellBtn").click(function() {
		fetchCellSensing(cellSensingUrl, plantNo);
	});
		
  
}); // document

var timeData = [];
for (var i = 0; i < 24; i++) {
  var hour = (i < 10 ? '0' : '') + i + ':00'; // 시간 형식을 00:00으로 맞춤
  timeData.push(hour);
}

const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고, 문자열로 변환하여 2자리로 만듭니다.
const day = String(currentDate.getDate()).padStart(2, '0'); // 일도 문자열로 변환하여 2자리로 만듭니다.

const formattedDate = `${year}-${month}-${day}`;
console.log(formattedDate);

const fetchCellSensing = (url, plantNo) => {
	
	
	console.log(formattedDate);
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo, // 파라미터와 값을 지정
        currentDate:formattedDate
    },
    success: function(response) {
        console.log('fetchCellSensing data:', response);
        makeCellList(response);
        
    },
    error: function(xhr, status, error) {
				console.error(error);
  	}
	});
}// fetchCellSensing

// 셀 카드리스트 
const makeCellList = (list = []) => {
	const $cellList = $("#cellList");
	$cellList.html(''); // 내용 초기화
	
	let content = '';
	for(let i = 0; i< list.length; i++) {

		let {cell_type, measure_value, cell_volume, created_at} = list[i];
		let cellObj = {};
		if(measure_value > 60) {
			cellObj['color'] = "red";
			cellObj['status'] = "위험";
			cellObj['stStyle'] = "text-danger";
		} else if(measure_value > 45) {
			cellObj['color'] = "orange";
			cellObj['status'] = "경고";
			cellObj['stStyle'] = "text-warning";
		} else {
			cellObj['color'] = "blue";
			cellObj['status'] = "양호";
			cellObj['stStyle'] = "text-success";
		}
		
		content += '<div class="col">';
		content += '<div class="card mb-2">';
		content += '<div class="card-body d-flex align-items-center">';
		content += '<i class="fa-solid fa-square" style="font-size: 40px; color: '+ cellObj['color'] + ';"></i>';
		content += '<div class="ms-3 d-flex flex-column">';
		content += '<label class="mb-1">No.'+ (i+1)+'&nbsp;&nbsp;'+ cell_type+ ' - ' + cell_volume + ' </label>';
		content += '<span class=' + cellObj['stStyle'] + '>' + cellObj['status'] + '</span>';
		content += '<label class="mb-0">셀 표면 온도 : '+ measure_value +'°C</label>';
		content += '<label class="mb-0">측정시간 : '+ created_at +'</label>';
		content += '</div></div></div>';
	}
	
	$cellList.html(content);

} 



// 3) 기상 데이터 
const fetchWeather = (url,plantNo) => {
	
	//const findTypes = ['TEMPERATURE','SI','WS']; // TA => TEMPERATURE
	$.ajax({
    url:url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
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
		height: 450,
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
      data: ['기온', '미세먼지', '습도']
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
        name: '미세먼지',
        position: 'right',
        alignTicks: true,
        axisLine: {
          show: true,
          lineStyle: {
            color: colors[0]
          }
        },
        axisLabel: {
          formatter: '{value}(㎍/㎥)'
        }
      },
      {
        type: 'value',
        name: '습도',
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
          formatter: '{value}(%)'
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
        name: '미세먼지', // SI
        type: 'bar',
        data: siList,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '(㎍/㎥)';
        	}
        },        
      },
      {
        name: '습도', // WS
        type: 'bar',
        yAxisIndex: 1,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '(%)';
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