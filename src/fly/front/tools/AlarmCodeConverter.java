package fly.front.tools;

/**
 * 报警代码转换成报警类型
 * @author WongYong
 */
public class AlarmCodeConverter {
	
	/**
	 * 根据报警代码获取报警类型名称
	 * @param alarmCode
	 * @return
	 */
	public static String getAlarmTypeName(String alarmCode) {
		String result = "";
		
		if (alarmCode != null) {
			if ("E001".equals(alarmCode)) {  //离线
				result = "设备离线";
			} else if ("E002".equals(alarmCode)) {  //低电压
				result = "设备低电压";
			} else if ("E003".equals(alarmCode)) {  //设备故障
				result = "设备故障";
			} else if ("E011".equals(alarmCode)) {  //床垫预警
				result = "床垫预警";
			} else if ("E012".equals(alarmCode)) {  //床垫报警
				result = "床垫报警";
			} else if ("E013".equals(alarmCode)) {  //床垫猝死
				result = "猝死报警";
			} else if ("E021".equals(alarmCode)) {  //定位器越界
				result = "越界报警";
			} else if ("E022".equals(alarmCode)) {  //定位器摔倒
				result = "摔倒报警";
			} else if ("E023".equals(alarmCode)) {  //定位器手动报警
				result = "手动报警";
			} else if ("E024".equals(alarmCode)) {  //定位器人卡分离
				result = "人卡分离";
			} else if ("E031".equals(alarmCode)) {  //门磁预警
				result = "门磁预警";
			} else if ("E032".equals(alarmCode)) {  //门磁报警
				result = "门磁报警";
			} else if ("E041".equals(alarmCode)) {  //一键报警-主动报警
				result = "一键报警";
			} else if ("E051".equals(alarmCode)) {  //尿湿报警
				result = "尿湿报警";
			} else if ("E061".equals(alarmCode)) {  //腕带呼叫器主动报警
				result = "腕带主动报警";
			} else if ("E071".equals(alarmCode)) {  //红外设备报警
				result = "红外设备报警";
			} else if ("E081".equals(alarmCode)) {  //体征床垫离床
				result = "体征床垫离床";
			} else {
				result = alarmCode;
			}
		}
		
		return result;
	}
}
