

/**
 * 
 */
//let datePicker;

$(document).ready(function(){
	let litepickerSingleDate = new Litepicker({
		element:document.getElementById('litepickerSingleDate'),
		startDate:getDateByParam(),
		format:'YYYY-MM-DD',
		utc:true
	});
	  litepickerSingleDate.on('selected', (date) => {
	      console.log('선택한 날짜:', date.format('YYYY-MM-DD'));
	  });
});


const getDateByParam = (dateParam = 0, format = '')=> {
	let today = new Date(); 
	today.setDate(today.getDate() - Math.abs(dateParam)); 
	let year = today.getFullYear(); 
	let month =  ('0' + (today.getMonth() + 1)).slice(-2);
	let day = ('0' + today.getDate()).slice(-2);
	
	return `${year}${format}${month}${format}${day}`;
}    


//contextPath변수 
const contextPath = document.currentScript.getAttribute('data-context-path');
	
 
//발전소 선택 후 발전셀 선택 
document.getElementById("authSelect1").addEventListener("change", function() {
    var selectedPlantName = this.options[this.selectedIndex].text;
    document.getElementById("selectdPowerPlnat").textContent = selectedPlantName;

    // 발전소 선택이 바뀔 때마다 "발전셀 선택" 옵션 숨기기
    document.getElementById("first").style.display = "none";
    document.getElementById("selectdPowerPlnat").style.display = "block";
});
		    
	//발전소 선택시 해당 cell의 발전셀 가져오기
	document.getElementById("authSelect1").addEventListener("change", function() {
	        var selectedPlantNo = this.value;
	        console.log(selectedPlantNo);
	        
	        
	        var xhr = new XMLHttpRequest();
	        xhr.open("GET", contextPath+"/plant/cell?plantNo=" + selectedPlantNo, true);
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState === 4 && xhr.status === 200) {
	            	var responseData = JSON.parse(xhr.responseText);
	            	//console.log(responseData);
		            
		            var selectElement = document.getElementById("cellSelect"); 
		            selectElement.innerHTML = ''; 
		            
		            responseData.forEach(function(cell) {
		                var option = document.createElement("option");
		                option.value = cell.cellNo; 
		                option.textContent = cell.cellType + '-' + cell.cellVolume;
		                selectElement.appendChild(option);
		            });
		      
		           	               
	            }
	        };
	        xhr.send();
	    });
	    
    //조회하기 버튼 클릭시 해당 셀의 이미지 가져오기
    $(document).ready(function() {
	    $("button").click(function() {
	        var selectedCell = $("#cellSelect").val();
	        var selectedDate = $("#litepickerSingleDate").val();
	        
	        
	        
	        $.ajax({
	            type: "POST",
	            url: contextPath+"/plant/cellImgs",
	            data: {selectedCell: selectedCell, selectedDate: selectedDate},
	            success: function(response) {
	                console.log(response);
	                
	                var cellDiv = $("#cellDiv");
                    cellDiv.empty();
	                
                    const generateCellVO = response.GenerateCellVO[0];
                    const cellList = response.cellList;
                    
                    
                    const {cellNo, plantNo, cellType, cellSerialNum, cellVolume} = generateCellVO;
                    
                    var rowDiv = $('<div class="row"></div>');
                    
                    for (let i = 0; i < cellList.length; i++) {
                        const imgObj = cellList[i];
                        
                        const {cellImgDesc, imgFile, createdAt} = imgObj;
                        
                        let html = '<div class="col-lg-4 mb-4">';
                        	html += '<div class="card">';                      
	                        html += '<img class="card-img-top" src="/renewen/imgs/' + imgFile + '" alt="' + cellImgDesc + '">';
	                        html += '<div class="card-body text-center">';
	                        html += '<h4 class="card-title">' + cellImgDesc + '</h4>';
	                        //html += '<h5 class="card-text">생성된 셀 정보</h5>';
	                        html += '<table class="table table-hover">';
	                        html += '<tr>';
	                        html += '<td>셀 크기</td>';
	                        html += '<td >' + generateCellVO.cellWidth + ' x ' + generateCellVO.cellHeight + ' x ' + generateCellVO.cellDepth + ' (' + generateCellVO.cellSizeUnit + ')' + '</td>';
	                        html += '</tr>';
	                        html += '<tr >';
	                        html += '<td >셀 타입</td>';
	                        html += '<td >' + generateCellVO.cellType + '</td>';
	                        html += '</tr>';
	                        html += '<tr>';
	                        html += '<td >셀 시리얼번호</td>';
	                        html += '<td>' + generateCellVO.cellSerialNum + '</td>';
	                        html += '</tr>';
	                        html += '<tr>';
	                        html += '<td>촬영 시간</td>';
	                        html += '<td>' + createdAt + '</td>';	
	                        html += '</tr>';
	                        html += '</table>';
	                        html += '<a href="/renewen/plant/download/' + imgFile + '" class="btn btn-primary mx-auto">다운로드</a>';
	                        html += '</div>'; // card-body 닫기
	                        html += '</div>'; // card 닫기
	                        html += '</div>';//div clo-lg닫기
	                      

                        rowDiv.append(html);
	                        
                        if ((i + 1) % 3 === 0 || i === cellList.length - 1) {
                            cellDiv.append(rowDiv);
                            rowDiv = $('<div class="row"></div>');
                        }
                    }
                },
                error: function(xhr, status, error) {
                    console.error(xhr.responseText);
                }
	        });
	    });
	}); 