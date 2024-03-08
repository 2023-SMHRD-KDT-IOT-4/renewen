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
/*const dummyData = {
    currentWatt: [100, 200, 300,],
    totalWatt: [1000, 1200, 1400],
    estimatedWatt: [1200, 1100, 1420]
};*/

// 랜덤 데이터 생성 함수
const generatRandomData = () => {
    return Math.floor(Math.random() * 10000);
};

document.addEventListener('DOMContentLoaded', function() {
    // 랜덤 데이터 생성
    const currentWatt = generatRandomData();
    const totalWatt = generatRandomData();
    const estimatedWatt = generatRandomData();
    
    drawGenElecChart(currentWatt, totalWatt, estimatedWatt);
});

// 차트 그리기 함수
const drawGenElecChart = (currentWatt, totalWatt, estimatedWatt) => {
    Highcharts.chart('chart-container', {
        chart: {
            type: 'bar' 
        },
        
        credits: {
            enabled: false
        },

        title: {
            text: ''
        },

        xAxis: {
            categories: ['발전량'],
            tickLength: 0,
            labels: {
                align: 'center'
            }
        },

        yAxis: {
            title: {
                text: ''
            },
            labels: {
                formatter: function() {
                    return ''; // 발전량 글자만 표시
                }
            },
            min: 0,
            max: 10000, // Y축 최대값 설정
            tickPositions: [0, 2000, 4000, 6000, 8000, 10000], // Y축에 표시될 눈금 위치 설정
            labels: {
                formatter: function() {
                    if (this.value === 0) return this.value;
                    else return (this.value / 1000) + 'k'; // Y축 눈금에 2k, 4k, 6k, 8k, 10k 표시
                }
            }
        },

        legend: {
            itemStyle: {
                fontSize: '9px'
            }
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
            data: [estimatedWatt],
            color: '#fe413e'
        }]
    });
};
