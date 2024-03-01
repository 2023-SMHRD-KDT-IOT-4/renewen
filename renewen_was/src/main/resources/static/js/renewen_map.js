/**
 * 
 */
 
	// plantListMap div에 발전소 맵 출력
	const printPlantMap = async function (plantList = [], mapDiv) {
	  let mapOptions = {
	    center: new naver.maps.LatLng(35.17294, 126.89156), // 초기 중심 좌표 (광주)
	    zoom: 8 // 초기 확대 수준
	  };
	
	  let plantMap = new naver.maps.Map(mapDiv, mapOptions);
	  let markers = []; // 지도 마커
	
	  // 발전소 마커 생성
	  for (let plant of plantList) {
	    const { latitude, longitude, plantName } = plant;
	    let marker = new naver.maps.Marker({
	      position: new naver.maps.LatLng(latitude, longitude),
	      map: plantMap,
	      title: plantName,
	      myValue: latitude
	    });
	    markers.push(marker); // 생성된 마커를 배열에 추가
	  }
	
	  // 마커 클릭 이벤트 등록
	  markers.forEach(marker => {
	    naver.maps.Event.addListener(marker, 'click', async () => {
	      handleMarkerClick(marker.getTitle());
	    });
	  });
	
	  // 마커 클릭 이벤트 처리
	  async function handleMarkerClick(plantName) {
	    alert(plantName + ' 발전소를 클릭했습니다!');
	  }
	};