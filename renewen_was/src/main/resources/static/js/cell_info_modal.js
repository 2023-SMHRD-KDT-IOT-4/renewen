/**
 * 
 */
 $(document).ready(function() {
    	    $("#saveChangesBtn").click(function() {

    	        var selectedIndexArray = [];

    	        $('.use-checkbox:checked').each(function() {
    	            var selectedIndex = $(this).closest('tr').index();
    	            selectedIndexArray.push(selectedIndex);
    	        });
    	        

    	        if (selectedIndexArray.length > 0) {
    	            $.ajax({
    	                type: "GET",
    	                url: "/renewen/updateUseYn",
    	                data: { selectedIndexArray: selectedIndexArray }, 
    	                traditional: true, 
    	                success: function(response) {
    	                    alert("연동 성공!");
    	                    // 성공적으로 업데이트된 경우 실행할 코드를 작성
    	                    window.location.href = "/renewen/plant/list";
    	                },
    	                error: function(xhr, status, error) {
    	                    console.error("Update failed:", error);
    	                    alert("연동 실패");
    	                    
    	                }
    	            });
    	        } else {
    	            console.log("No checkboxes selected."); // 선택된 체크박스가 없는 경우
    	            alert("선택된 항목이 없습니다.")
    	        }
    	    });
    	});
    	
 function getCellInfo(plantNo) {
		    $.ajax({
		        type: "GET",
		        url: "/renewen/plant/modal",
		        data: { plantNo: plantNo },
		        success: function(response) {
		            var modalBody = $('#exampleModal .modal-body');
		            modalBody.empty();
		            
		            //표
		            var table = $('<table class="table">');
		            var thead = $('<thead>').appendTo(table);
		            var tbody = $('<tbody>').appendTo(table);

		            var headerRow = $('<tr>').appendTo(thead);
		            headerRow.append('<th>No</th>');
		            headerRow.append('<th>셀 타입</th>');
		            headerRow.append('<th>시리얼 번호</th>');
		            headerRow.append('<th>용량</th>');
		            headerRow.append('<th>셀 크기(가로x세로x높이)</th>');
		            var selectAllCheckbox = $('<input>').attr('type', 'checkbox').addClass('select-all-checkbox');
		            headerRow.append(selectAllCheckbox);
		            headerRow.append('전체 선택');
		            

		            response.forEach(function(cell, index) {
		                var row = $('<tr>').appendTo(tbody);
		                row.append('<td>' + (index + 1) + '</td>');
		                row.append('<td>' + cell.cellType + '</td>');
		                row.append('<td>' + cell.cellSerialNum + '</td>');
		                row.append('<td>' + cell.cellVolume + '</td>');
		                row.append('<td>' + (cell.cellWidth +' x '+ cell.cellHeight +' x '+ cell.cellDepth) + '</td>');
		                
		                if (cell.useYn === 'N') {
		                    var checkbox = $('<input>').attr('type', 'checkbox').addClass('use-checkbox').data('cell-id', cell.cellId);
		                    var checkboxTd = $('<td>').append(checkbox);
		                    row.append(checkboxTd);
		                } else if (cell.useYn === 'Y') {
		                    var checkbox = $('<input>').attr('type', 'checkbox').addClass('use-checkbox').prop('checked', true).prop('disabled', true).data('cell-id', cell.cellId);
		                    var checkboxTd = $('<td>').append(checkbox);
		                    row.append(checkboxTd);
		                }

		                
		            });
		            

		            modalBody.append(table);
		            

		            $('#exampleModal').modal('show');
		        },
		        error: function(xhr, status, error) {

		            console.error(error);
		        }
		    });
		}
	  
	  
	  $(document).ready(function() {
	        // 전체 선택 체크박스
	        $(document).on('change', '.select-all-checkbox', function() {
	            var isChecked = $(this).prop('checked');
	            $('.use-checkbox').prop('checked', isChecked);
	        });

	        // 각 행 체크박스
	        $(document).on('change', '.use-checkbox', function() {
	            var allChecked = $('.use-checkbox:checked').length === $('.use-checkbox').length;
	            $('.select-all-checkbox').prop('checked', allChecked);
	        });
	    });