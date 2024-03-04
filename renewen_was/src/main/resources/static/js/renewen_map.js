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
	    const { latitude, longitude, plantName, stnNo, plantLinkKey, plantAddr, plantAddr2 } = plant;
	    let marker = new naver.maps.Marker({
	      position: new naver.maps.LatLng(latitude, longitude),
	      map: plantMap,
	      title: plantName,
	      myValue: latitude,
	      plant: plant,
	      address: plantAddr, 
	      address2: plantAddr2,
	      plantLinkKey: plantLinkKey,
	      stnNo: stnNo
	    });
	    markers.push(marker); // 생성된 마커를 배열에 추가
	  }
	
	  // 마커 클릭 이벤트 등록
	  markers.forEach(marker => {
	    naver.maps.Event.addListener(marker, 'click', async () => {
	      handleMarkerClick(marker.plant);
	    });
	  });
	
	  // 마커 클릭 이벤트 처리
	  async function handleMarkerClick(plant) {
		  console.log(plant);
	    alert(plant.plantName + ' 발전소를 클릭했습니다!' + '\n주소: ' + plant.plantAddr + plantAddr2);
	  }
	};