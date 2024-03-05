/**
 * 
 */

	// plantListMap div에 발전소 맵 출력
	const printPlantMap = async function (plantList = [], mapDiv) {
	  let mapOptions = {
	    center: new naver.maps.LatLng(35.17294, 126.89156), // 초기 중심 좌표 (광주)
	    zoom: 10 // 초기 확대 수준
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
	
	 // 마커 클릭 이벤트 등록 및 InfoWindow 표시
	markers.forEach(marker => {
	  naver.maps.Event.addListener(marker, 'click', async () => {
    // 클릭된 마커 정보 가져오기
    const { plantName, plantAddr, plantAddr2, latitude, longitude, plantLinkKey } = marker.plant;

	// 연동키가 null이면 '없음'으로 표시
	let linkKeyContent = plantLinkKey !== null ? plantLinkKey : '없음';

    // InfoWindow에 표시될 내용 생성
	let infoWindowContent = '<div style="text-align: center; margin-top: 10px;">' + '<span style="margin-right: 10px; font-size: 1.0em; font-weight: 600;"><발전소:' +
    					 '<span style="margin-left: 5px; margin-right: 10px; font-size: 1.0em; font-weight: 600;">' + plantName + '>' + '</span>' + '</div>' +
                         '<div style="margin-left: 5px; margin-right: 10px;">*주소: ' + plantAddr + ' '+ plantAddr2 + '</div>' +
                         '<div style="margin-left: 5px; margin-right: 10px;">*위도/경도: ' + latitude + ' / ' + longitude + '</div>' +
                         '<div style="margin-left: 5px; margin-right: 10px; margin-bottom: 10px;">*연동키: ' + linkKeyContent  + '</div>';
      
	// InfoWindow 여닫기
    if (marker.infoWindow && marker.infoWindow.getMap()) {
      marker.infoWindow.close();
    } else {
	// InfoWindow 생성 및 열기
    marker.infoWindow = new naver.maps.InfoWindow({
      content: infoWindowContent
    });
    marker.infoWindow.open(plantMap, marker);
	}
 
  });
});


	};