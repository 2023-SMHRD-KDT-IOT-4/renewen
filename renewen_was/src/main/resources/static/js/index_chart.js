

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
