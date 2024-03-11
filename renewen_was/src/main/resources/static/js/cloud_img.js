/**
 * 
 */
//litepicker
$(document).ready(function(){
	let litepickerSingleDate = new Litepicker({
		element:document.getElementById('litepickerSingleDate'),
		startDate:getDateByParam(),
		format:'YYYY-MM-DD',
		utc:true
	});
	/*  litepickerSingleDate.on('selected', (date) => {
	      console.log('선택한 날짜:', date.format('YYYY-MM-DD'));
	  });*/
});


const getDateByParam = (dateParam = 0, format = '')=> {
	let today = new Date(); 
	today.setDate(today.getDate() - Math.abs(dateParam)); 
	let year = today.getFullYear(); 
	let month =  ('0' + (today.getMonth() + 1)).slice(-2);
	let day = ('0' + today.getDate()).slice(-2);
	
	return `${year}${format}${month}${format}${day}`;
}   


	
//contextPath변수저장
const contextPath = document.currentScript.getAttribute('data-context-path');

    //버튼 클릭시    
    $("button").click(function(){
        
        var selectedPlant = $("#authSelect1").val();
        var selectedDate = $("#litepickerSingleDate").val();
        
        $("#imgBody").empty();
        

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