/**
 * 
 */
	var koreanDays = ['일', '월', '화', '수', '목', '금', '토'];
       var koreanMonths = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

       function updateDateTime() {
           var currentDate = new Date();
   
           var year = currentDate.getFullYear();
           var month = koreanMonths[currentDate.getMonth()];
           var dayOfMonth = currentDate.getDate();
           var dayOfWeek = koreanDays[currentDate.getDay()];
           var hours = currentDate.getHours();
           var minutes = currentDate.getMinutes();
           var ampm = hours >= 12 ? '오후' : '오전';
   
           hours = hours % 12;
           hours = hours ? hours : 12; 
   
           dayOfMonth = (dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth;
           minutes = (minutes < 10) ? "0" + minutes : minutes;

           document.getElementById("currentYear").textContent = year + '년';
           document.getElementById("currentMonth").textContent = month;
           document.getElementById("currentDayOfMonth").textContent = dayOfMonth + '일';
           document.getElementById("currentDayOfWeek").textContent = dayOfWeek + '요일';
           document.getElementById("currentHour").textContent = hours;
           document.getElementById("currentMinute").textContent = minutes;
           document.getElementById("currentPeriod").textContent = ampm;
       }
   
       window.onload = function() {
           updateDateTime(); 
           setInterval(updateDateTime, 1000);
           
          // 커서 위치
          var input = document.querySelector('input');
          if(input) {
              var inputValue = input.value;
              input.selectionStart = input.selectionEnd = inputValue.length;
              input.focus();
          }
          
       };  