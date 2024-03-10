

//주소
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
               
                var addr = ''; // 주소 변수
               
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                document.getElementById("plantAddr").value = addr;
                document.getElementById("plantAddr2").focus();
                
            }
        }).open();
    }
	
	

	document.addEventListener('DOMContentLoaded', function() {
	        document.getElementById('plantAddr').addEventListener('change', function() {
	            const plantAddrValue = this.value;
	            if (plantAddrValue.trim() !== '') { //null 아닐때만
	                setLALOInfo(plantAddrValue);
	            }
	        });
	        
	        if (!document.getElementById('plantAddr').onclick) {
	            document.getElementById('plantAddr').addEventListener('click', sample6_execDaumPostcode);
	        }
	    });

	    async function setLALOInfo(placeAddress) {
	        try {
	            const { latitude, longitude } = await getLALOInfo(placeAddress);
	            console.log(latitude);
	            console.log(longitude);
	            document.getElementById('latitude').value = latitude;
	            document.getElementById('longitude').value = longitude;
	        } catch (error) {
	            console.error("에러 발생:", error);
	            alert("위도 경도 주소 정보를 가져오는 동안 오류가 발생했습니다.");
	        }
	    }
		
	    
	    async function getLALOInfo(placeAddress) {
	        const url = 'https://dapi.kakao.com/v2/local/search/address.json?query=' + encodeURI(placeAddress);
	        try {
	            const axiosResult = await axios({
	                url: url,
	                method: 'get',
	                headers: {
	                    Authorization: 'KakaoAK bfa531f53b0777713d5a854a4ae08651',
	                },
	            });
	            const latitude = axiosResult.data.documents[0].address.y;
	            const longitude = axiosResult.data.documents[0].address.x;
	            console.log("위도:", latitude);
	            console.log("경도:", longitude);
	            return { latitude, longitude };
	        } catch (error) {
	            console.error("에러 발생:", error);
	            throw error;
	        }
	    }
	    
	    function getLALOInfoAndSubmit() {
	        const plantAddrValue = document.getElementById('plantAddr').value;	       
	        
	        if (plantAddrValue.trim() !== '') {
	            setLALOInfo(plantAddrValue)
	                .then(() => {
	                	if (validateForm()) {
	                        document.getElementById('plantForm').submit();
	                    }else{
	                    	alert("사업자 번호를 10자리 숫자로 입력해주세요.")
	                    }
	                })
	                .catch(error => {
	                    console.error("에러 발생:", error);
	                    alert("폼 제출 할 때 주소 정보를 가져오는 동안 오류가 발생했습니다.");
	                });
	        } else {
	            alert("발전소 주소를 입력하세요.");
	        }
	    }
		