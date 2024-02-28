package kr.smhrd.renewen.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	/**
	 * @return 현재 날짜시간 yymmddhhmmss
	 */
	public static String getCurrentDateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = currentDateTime.format(formatter);
		return formattedDateTime;
	}
}
