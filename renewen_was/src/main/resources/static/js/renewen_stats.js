/**
 * 
 */
let startDatePicker;
let endDatePicker;
let predictData = []; // 예상발전량 데이터
let genRealData = []; // 실제 발전량 데이터
let timeDatas = [];

$(document).ready(function() {
	const contextPath = $("#contextPath").val();
	const genPeriodUrl = contextPath + '/plant/gen_period/elec';
	const downUrl = contextPath + '/plant/download/excel';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	
 	startDatePicker = new Litepicker({
     element: document.getElementById('startDate'),
     defaultDate: true,
     startDate:getDateByParam(5),
     format: 'YYYY-MM-DD',
     utc: true
 	});
 	endDatePicker = new Litepicker({
     element: document.getElementById('endDate'),
     startDate: getDateByParam(),
     format: 'YYYY-MM-DD',
     utc: true
 	});

	// ====================================================================
	// 조회버튼 클릭 시
	$("#searchBtn").on('click', function() {
		
	 // checkValidDate(); //날짜 유효 체크
		
		// 조회 날짜
	 	let startDate = startDatePicker.getDate().format('YYYY-MM-DD')
    let endDate = endDatePicker.getDate().format('YYYY-MM-DD');
		fetchPredict(genPeriodUrl, plantNo, startDate, endDate);
			
	});
	
	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  });
  
  $("#downBtn").on('click', function() {
		console.log(timeDatas);
		if(timeDatas.length == 0) {
			alert('조회된  데이터가 없습니다.');
			return;
		}
		
		let plantName = $("#selectList option:selected").text();
		
		// down 요청
		$.ajax({
	    url: downUrl,
	    type: 'POST',
	    contentType : 'application/json',
	    data: JSON.stringify({
	        timeData: timeDatas,
	        genReal: genRealData,
	        genPredict: predictData,
	    }),
      xhrFields: {
          responseType: 'blob' // 바이너리 데이터를 처리할 수 있도록 responseType을 blob으로 설정합니다.
      },	    
	    success: function(response) {
	        console.log('down', response);
	        let url = window.URL.createObjectURL(response);
	        let a = document.createElement('a');
	        a.href = url;
	        a.download = plantName +' 발전량조회.xls';
	        document.body.append(a);
	        a.click();
	        a.remove();
	        window.URL.revokeObjectURL(url);	        
	    },
	    error: function(xhr, status, error) {
	        console.error(error);
	  	}
		});		
		
	}); // excell download
  
}); // document


const checkValidDate = () => {
    let today = new Date();
    let startDate = new Date(startDatePicker.getDate().format('YYYY-MM-DD'));
    let endDate = new Date(endDatePicker.getDate().format('YYYY-MM-DD'));
    console.log(today);
    console.log(startDate);
    console.log(endDate);
    
    if(endDate > today) {
        alert('종료날짜는 오늘까지만 선택가능합니다.');
        endDatePicker.setDate(today);
    } else if (startDate > endDate) {
        alert('시작날짜는 종료날짜보다 이전이어야 합니다.');
        startDatePicker.setDate(endDate); // 시작 날짜를 종료 날짜로 설정합니다.
    } 
}


const getDateByParam = (dateParam = 0, format = '')=> {
	let today = new Date(); 
	today.setDate(today.getDate() - Math.abs(dateParam)); 
	let year = today.getFullYear(); 
	let month =  ('0' + (today.getMonth() + 1)).slice(-2);
	let day = ('0' + today.getDate()).slice(-2);
	
	return `${year}${format}${month}${format}${day}`;
}    

const addDays = (dateString = "", days = 0) => {
  let parts = dateString.split('-');
  let year = parseInt(parts[0], 10);
  let month = parseInt(parts[1], 10) - 1; // month 보정
  let day = parseInt(parts[2], 10);
  
  let date = new Date(year, month, day);
  date.setDate(date.getDate() + days); // + day 처리

  // YYYY-MM-DD 형태의 문자열
  let result = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
  return result;
}


// YYYY-MM-DD
const fetchPredict = (url, plantNo, startDate, endDate) => {
	
	
	$.ajax({
    url: url,
    type: 'GET',
    data: {
        plantNo: plantNo,
        startDate: startDate,
        endDate: endDate,
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


const printPredictChart = (genReal = {}, genPredict = {}) => {
    
	const dom = document.getElementById('chart-container');
	const myChart = echarts.init(dom, null, {
	  renderer: 'canvas',
	  useDirtyRect: false
	});
	
	// genPredict - 시간 데이터 asc
	let timeData = Object.keys(genPredict).sort(function(a, b) {
 		let dateA = new Date("20" + a.replace(/-/g, "/"));
    let dateB = new Date("20" + b.replace(/-/g, "/"));
  	return dateA - dateB;
	});
	timeDatas = timeData;
	
	let chartMax = -9999;
	timeData.forEach(function(key) {
		let predictVal = genPredict[key];
		let realVal = genReal[key] !== undefined ? genReal[key] : 0.0;
    predictData.push({ time: key, value: predictVal });
    genRealData.push({ time: key, value: realVal });
    chartMax = Math.max(chartMax, predictVal, realVal);

	});
	
	let option; // 차트 옵션
	option = {
	  title: {
	    text: timeData[0] + ' ~ ' + timeData[timeData.length-1],
	    left: 'center'
	  },
	  tooltip: {
	    trigger: 'axis',
	    axisPointer: {
	      animation: true
	    }
	  },
	  legend: {
	    data: ['실제 발전량', '예상 발전량' ],
	    left: 10
	  },
	  toolbox: {
	    feature: {
	      dataZoom: {
	        yAxisIndex: 'none'
	      },
	      restore: {},
	      saveAsImage: {}
	    }
	  },
	  axisPointer: {
	    link: [
	      {
	        xAxisIndex: 'all'
	      }
	    ]
	  },
	  dataZoom: [
	    {
	      show: true,
	      realtime: true,
	      start: 30,
	      end: 70,
	      xAxisIndex: [0, 1]
	    },
	    {
	      type: 'inside',
	      realtime: true,
	      start: 30,
	      end: 70,
	      xAxisIndex: [0, 1]
	    }
	  ],
	  grid: [
	    {
	      left: 60,
	      right: 50,
	      height: '50%' // 하단 그래프 영역
	    }
	  ],
	  xAxis: [
	    {
	      type: 'category',
	      boundaryGap: false,
	      axisLine: { onZero: true },
	      data: predictData.map(item => item.time)
	    }
	  ],
	  yAxis: [
	    {
	      name: 'W', // y축 이름
	      type: 'value', // 값의 종류
	      max: Math.floor(chartMax * 1.1) // y축 최댓값 최고값에 보정
	    }
	  ],
	  series: [
	    {
	      name: '실제 발전량',
	      type: 'line',
	      symbolSize: 8,
	      data: genRealData.map(item => item.value)
	    },
	    {
	      name: '예상 발전량',
	      type: 'line',
	      symbolSize: 8,
	      data: predictData.map(item => item.value)
	    }
	  ]
	};
	
	if (option && typeof option === 'object') {
	  myChart.setOption(option);
	}
	window.addEventListener('resize', myChart.resize);
	
 } // printPredictChart
