package kr.smhrd.renewen.global.util;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * Date, File Upload Path
 */
@Component
public class CommonUtil {

	@Value("${upload.path}") 
	String uploadPath; // 업로드 기본경로

	@Value("${upload.img}")
	String imgDir; // 업로드 이미지 경로
	
	/**
	 * @param dateFormat 
	 * yyyyMMddhhmmss
	 * @return
	 */
	public String getCurrentDateTime(String dateFormat) {
		ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 타임존 지정
		ZonedDateTime currentTimeInZone = ZonedDateTime.now(zoneId);
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
		return currentTimeInZone.format(dateFormatter);
	}

	/**
     * @param number 2자리
     * @return "00" "11"
     */
    public String formatNumberWithPadding(int number) {
        return String.format("%02d", number);
    }	
	
	/**
	 * @return 업로드할 이미지 경로 
	 * 	window - C:\\upload\\imgs
	 * 	linux - /home/renewen/upload/imgs
	 */
	public String getUploadImgPath() {
		String filePath = Paths.get(uploadPath, imgDir).toString();
		return filePath;
	}
	
	/**
	 * 업로드 경로 부터 ~ 파일 확장자까지
	 * @param fileName 20240303103237_test_img1.jpg
	 * @return uploadPath/filename.ext 
	 * 	window - C:\\upload\\imgs\\20240303103237_test_img1.jpg
	 * 	linux - /home/renewen/upload/img/20240303103237_test_img1.jpg
	 */
	public String getFileFullPath(String fileName) {
		return getUploadImgPath() + File.separator + fileName;
	}
	
	/**
	 * @param startDateStr yyyymmdd
	 * @param endDateStr yyyymmdd
	 * @return  List<String> dateList  startDateStr ~ endDateStr
	 */
	public List<String> dateList(String startDateStr , String endDateStr ) {
        // 문자열을 LocalDate로 변환
        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.BASIC_ISO_DATE);

        // 시작 날짜부터 종료 날짜까지의 모든 날짜를 리스트에 추가
        List<String> dateList = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            dateList.add(date.format(DateTimeFormatter.BASIC_ISO_DATE));
        }
       return dateList;
	}
	
	/**
	 * @param paramDate YYYYMMDD
	 * @return paramDate + plusDay
	 */
	public String plusDays(String paramDate, int plusDay) {
	
        LocalDate date = LocalDate.parse(paramDate);
        LocalDate tomorrow = date.plusDays(plusDay);
        String plusDate = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		return plusDate;
	}
	
	
}
