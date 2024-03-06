
/*전체선택 체크박스*/
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('selectAllCheckbox').addEventListener('change', function() {
        var isChecked = this.checked;
        document.querySelectorAll('input[name="selectedItems"]').forEach(checkbox => {
            checkbox.checked = isChecked;
        });
    });

    document.querySelectorAll('input[name="selectedItems"]').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            var allChecked = true;
            document.querySelectorAll('input[name="selectedItems"]').forEach(cb => {
                if (!cb.checked) {
                    allChecked = false;
                }
            });
            document.getElementById('selectAllCheckbox').checked = allChecked;
        });
    });
});

/*선택항목 확인 하고 승인하는 버튼 선택한 부분 일괄승인*/
document.getElementById('approveSelectedBtn').addEventListener('click', function() {
    // 선택한 항목을 확인하고 승인 작업 수행
    var selectedItems = document.querySelectorAll('input[name="selectedItems"]:checked');
    var plantNos = [];
    selectedItems.forEach(function(item) {
        plantNos.push(item.value);
    });
    
    // 여기서 plantNos를 이용하여 서버로 선택한 항목을 전송하고 승인 작업을 처리할 수 있습니다.
});