/**
 * 
 */
	//litepicker
	 const litepickerSingleDate = new Litepicker({
         element: document.getElementById('litepickerSingleDate'),
         format: 'YYYY-MM-DD'
     });

     litepickerSingleDate.on('selected', (date) => {
         //console.log('선택한 날짜:', date.format('YYYY-MM-DD'));
     });
	
	//contextPath변수저장
	const contextPath = document.currentScript.getAttribute('data-context-path');
	
    //버튼 클릭시    
    $("button").click(function(){
        
        var selectedPlant = $("#authSelect1").val();
        var selectedDate = $("#litepickerSingleDate").val();
        

        $.ajax({
            type: "POST",
            url: contextPath+"/plant/cloudImgs",            
            data: { selectedPlant: selectedPlant, selectedDate: selectedDate }, 
            success: function(response){
             
                for(let i=0; i<response.length; i++) {
                	
                	let imgObj = response[i];
                	
                	const { cloudImgDesc , imgFile ,createdAt} = imgObj;
                	
                	const tBody = document.getElementById('imgBody');
                	let html = '<tr>' ;
	                	html += '<td><img src="'+contextPath + '/imgs/'+ imgFile +'" alt="'+cloudImgDesc+'" width="200px" height="200px"></td>';
	                	html += '<td>'+cloudImgDesc+'</td>';
	                	html += '<td>'+createdAt+'</td>';
	                	html += '<td><a href="'+contextPath + '/plant/download/'+ imgFile +'" class="btn btn-primary">다운로드</a></td>';
	                	html += '</tr>';
                	
                	tBody.innerHTML += html;	
                }
            },
            error: function(xhr, status, error){
                console.error(xhr.responseText); 
            }
        });
    });
//});