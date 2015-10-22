package fly.front.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具
 * @author WongYong
 */
public class Datetools {

	/**
	 * 获取当前时间
	 * @return 当前时间yyyyMMddhhmmss
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 格式化日期字符串
	 * @param dateString yyyyMMddhhmmss 或 yyyyMMdd
	 * @return yyyy-MM-dd hh:mm:ss 或 yyyy-MM-dd
	 */
	public static String formateDate(String dateString) {
		String result = "";
		
		if (dateString != null) {
			if (dateString.length() == 14 || dateString.length() == 8) {
				result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
				if (dateString.length() == 14) {
					result += " " + dateString.substring(8, 10) + ":" + dateString.substring(10, 12) + ":" + dateString.substring(12, 14);
				}
			} else {
				result = dateString;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(Datetools.getCurrentDate());
	}
}
