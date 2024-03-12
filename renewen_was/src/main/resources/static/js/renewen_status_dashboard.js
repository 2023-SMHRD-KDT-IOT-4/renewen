/**
 * 
 */

$(document).ready(function() {
	
	const contextPath = $("#contextPath").val();
	const cellSensingUrl = contextPath + '/plant/sensing/cell';
	const sensorUrl = contextPath + '/plant/sensing';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	// 1. 발전소 셀 상태
	fetchCellSensing(cellSensingUrl, plantNo);
	// 2. 발전소 센싱데이터
	fetchSensorData(sensorUrl, plantNo);
		
	// ====================================================================

	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  	// 1. 셀 상태
  	fetchCellSensing(cellSensingUrl, plantNo);
  	// 2. 발전소 센싱데이터
  	fetchSensorData(sensorUrl, plantNo);

  });
  
	// 발전소 셀 새로고침 버튼
 $("#cellBtn").click(function() {
		$(this).addClass('spinner-border');
   	fetchCellSensing(cellSensingUrl, plantNo);
 	});
	// 발전소 센싱데이터 새로고침 버튼
 $("#sensorBtn").click(function() {
	 	$(this).addClass('spinner-border');
		fetchSensorData(sensorUrl, plantNo);
	});
	

  
}); // document

var timeData = [];
for (var i = 0; i < 24; i++) {
  var hour = (i < 10 ? '0' : '') + i + ':00'; // 시간 형식을 00:00으로 맞춤
  timeData.push(hour);
}


// 발전소 센싱데이터 가져옴
const fetchSensorData = (url, plantNo) => {

	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
    },
    success: function(response) { // 응답 성공
        console.log('fetchSensorData:', response);
        setTimeout(function() {
            $('#sensorBtn').removeClass('spinner-border');
        }, 500);        
        printSensorChar(response);
        
    },
    error: function(xhr, status, error) {
				console.error(error);
        setTimeout(function() {
            $('#sensorBtn').removeClass('spinner-border');
        }, 500); 				
  	}
	});
}// fetchSensorData


const printSensorChar = dataObj => {

	const dom = document.getElementById("sensorChart");
  const sensorChart = echarts.init(dom, null, {
		height: 400,
    renderer: 'canvas',
    useDirtyRect: false
  });
	
  let dustList = dataObj['dust'] ? dataObj['dust'] : []; 
  let humidityList = dataObj['humidity'] ? dataObj['humidity'] : [];
  let tempList = dataObj['temperature'] ? dataObj['temperature'] : [];
  
  let dustData = [];
  let humidityData = [];
  let tempData = [];
  
  for(let i = 0; i < dustList.length; i++) {
		let dateTime = Object.keys(dustList[i]);
		dustData.push(dustList[i][dateTime]);
		humidityData.push(humidityList[i][dateTime]);
		tempData.push(tempList[i][dateTime]);
	}
  
	let option;
 	const colors = ['#FFA500', '#5858FA', '#EE6666'];
  option = {
    color: colors,
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      }
    },
    grid: {
      right: '25%'
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
        //max: 100,
        axisLine: {
          show: true,
          lineStyle: {
            color: colors[1]
          }
        },
        axisLabel: {
          formatter: '{value}%'
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
        data: dustData,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '㎍/㎥';
        	}
        },        
      },
      {
        name: '습도', // WS
        type: 'bar',
        yAxisIndex: 1,
        tooltip: {
        	valueFormatter: function (value) {
	          return value + '%';
        	}
        },
        data: humidityData,
        
        
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
        data: tempData
      }
    ]
  };

  if (option && typeof option === 'object') {
    sensorChart.setOption(option);
  }

  window.addEventListener('resize', sensorChart.resize);
} // printSensorChar



// 1) 셀 상태값 가져옴 2) cellList 출력
const fetchCellSensing = (url, plantNo) => {
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo // 파라미터와 값을 지정
    },
    success: function(response) {
        console.log('fetchCellSensing data:', response);
        setTimeout(function() {
            $('#cellBtn').removeClass('spinner-border');
        }, 500);           
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




