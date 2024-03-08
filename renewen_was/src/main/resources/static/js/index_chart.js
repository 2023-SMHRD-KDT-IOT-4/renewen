// 차트 생성 함수
const printPredictChart = () => {
	
	// 차트 더미데이터 생성
  let timeData = [];
  let realList = [];
  let predictList = [];
  
  for(let i = 0; i<24; i++) {
		let time = i + ":00";
		timeData.push(time);
		let realVal = Math.floor(Math.random() *1200);
		let predictVal = Math.floor(Math.random() *1500);
		realList.push({ 'time' : time , 'value' : realVal});
		predictList.push({ 'time' : time , 'value' : predictVal});
	}
  
  // 차트 생성
  const dom = document.getElementById('myAreaChart');
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
      data: ['예상 발전량', '실제 발전량']
    },
    xAxis: {
      type: 'category',
      data: timeData,
      axisTick: {
        alignWithLabel: true
      },
    },
    yAxis: [{
      type: 'value',
      name: '예상 발전량(W)',
      max: 1800,
      axisLine: {
        lineStyle: {
          color: '#5470C6' // 선 그래프 색상
        }
      },
      interval: 300 // y축 간격 설정
    }, {
      type: 'value',
      name: '실제 발전량(W)',
      max: 1800,
      axisLine: {
        lineStyle: {
          color: '#008000' // 막대 그래프 색상
        }
      }
    }],
    series: [{
      name: '예상 발전량',
      type: 'line',
      yAxisIndex: 0,
      tooltip: {
        valueFormatter: function (value) {
          return value + 'W';
        }
      },
      data: predictList.map(item => item.value)
    }, {
      name: '실제 발전량',
      type: 'bar',
      yAxisIndex: 1,
      tooltip: {
        valueFormatter: function (value) {
          return value + 'W';
        }
      },
      data: realList.map(item => item.value)
    }]
  };

  // 차트에 옵션 설정
  predictChart.setOption(option);
}


 /* 금일 발전량 */
 
 
 
 
 // 더미 데이터 예시
const dummyData = {
    currentWatt: [100, 200, 300, 400, 500, 600, 700],
    totalWatt: [1000, 1200, 1400, 1600, 1800, 2000, 2200],
    estimatedWatt: [1200, 1100, 1420, 1600, 1750, 1900, 2100]
};

// 차트 그리기 함수
const drawGenElecChart= (currentWatt, totalWatt, estimatedWatt) =>{
    Highcharts.chart('chart-container', {
        chart: {
            type: 'bar'
        },
        
        credits: { // 로고 제거를 위한 credits 옵션 추가
	        enabled: false
	    	},
	    
	    	title: {
	        text: ''
	   	 },
      
        xAxis: {
            categories: ['발전량'],
            labels: {
                enabled: true // x 축 레이블 보이기
            }
        },
        
        yAxis: {
            title: {
                text: ''
            },
            
            labels: {
                formatter: function() {
                    return ''; // 비어있는 문자열 반환하여 빈 칸 표시
                }
            }
        },
        
        series: [{
            name: '현재 발전량',
            data: currentWatt,
            color: '#f9f043'
        }, {
            name: '누적 발전량',
            data: totalWatt,
            color: '#f9ad43'
        }, {
            name: '예상 발전량',
            data: estimatedWatt,
            color: '#fe413e'
        }]
    });
}

document.addEventListener('DOMContentLoaded', function() {
    // 더미 데이터를 사용하여 차트 그리기
    drawGenElecChart(dummyData.currentWatt, dummyData.totalWatt, dummyData.estimatedWatt);
});
