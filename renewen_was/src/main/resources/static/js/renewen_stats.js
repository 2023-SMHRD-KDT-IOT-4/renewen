/**
 * 
 */

$(document).ready(function() {
	console.log('stats js');
	const contextPath = $("#contextPath").val();
	const genElecUrl = contextPath + '/plant/gen/elec';
	
	let plantNo = $("#selectList").val(); // 선택 발전소 식별번호
	let stnNo = "156";

	
	// 2. 금일 발전량 추이 차트 출력
	//fetchPredict(genTimeElecUrl, plantNo);

	printPredictChart([], []);
	// ====================================================================

	 // 발전소 변경 시 
	 $("#selectList").change(function() {
  	plantNo = $(this).val();
  	
 		// 2. 금일 발전량 추이 차트 출력
		//fetchPredict(genTimeElecUrl, plantNo);
  });
  
}); // document

// ['00:00', '01:00', '02:00', ..., '23:00']
var timeData = [];
for (var i = 0; i < 24; i++) {
  var hour = (i < 10 ? '0' : '') + i + ':00'; // 시간 형식을 00:00으로 맞춤
  timeData.push(hour);
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
    
var dom = document.getElementById('chart-container');
var myChart = echarts.init(dom, null, {
  renderer: 'canvas',
  useDirtyRect: false
});
var app = {};

var option;

// 시간 데이터
var timeData = [
  '2009/6/12 2:00', '2009/6/12 3:00', '2009/6/12 4:00', '2009/6/12 5:00', '2009/6/12 6:00', '2009/6/12 7:00', '2009/6/12 8:00', '2009/6/12 9:00', '2009/6/12 10:00', '2009/6/12 11:00', '2009/6/12 12:00', '2009/6/12 13:00', '2009/6/12 14:00', '2009/6/12 15:00', '2009/6/12 16:00', '2009/6/12 17:00', '2009/6/12 18:00', '2009/6/12 19:00', '2009/6/12 20:00', '2009/6/12 21:00', '2009/6/12 22:00', '2009/6/12 23:00'
];

// 시간 데이터 포맷 변경
timeData = timeData.map(function (str) {
  return str.replace('2009/', '');
});

option = {
  title: {
    text: 'Rainfall vs Evaporation',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      animation: false
    }
  },
  legend: {
    data: ['Evaporation', 'Rainfall'],
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
      data: timeData
    }
  ],
  yAxis: [
    {
      name: 'Value', // y축 이름
      type: 'value', // 값의 종류
      max: 100 // y축 최댓값
    }
  ],
  series: [
    {
      name: 'Evaporation',
      type: 'line',
      symbolSize: 8,
      // 데이터 예시
      data: [
        0.97, 0.96, 0.96, 0.95, 0.95, 0.94, 0.94, 11.94, 0.94, 22.94, 0.94, 0.94, 0.94, 0.94, 0.94,11
      ]
    },
    {
      name: 'Rainfall',
      type: 'line',
      symbolSize: 8,
      // 데이터 예시
      data: [
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,22
      ]
    }
  ]
};

if (option && typeof option === 'object') {
  myChart.setOption(option);
}

window.addEventListener('resize', myChart.resize);

  
 }



const getCurrentTime =() => {
    const now = new Date();
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');
    return hours + ':' + minutes;
}