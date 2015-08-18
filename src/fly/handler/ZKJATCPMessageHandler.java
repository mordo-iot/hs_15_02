package fly.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.QueryCondition;

import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.dev.DevEntity;
import fly.entity.historyLocationManual.HistoryLocationManualEntity;
import fly.entity.historyLocationPos.HistoryLocationPosEntity;
import fly.init.SystemInit;
import fly.service.DataService;
import fly.service.alarmCurrent.AlarmCurrentService;
import fly.service.alarmHistory.AlarmHistoryService;
import fly.service.currentLocation.CurrentLocationService;
import fly.service.historyLocationManual.HistoryLocationManualService;
import fly.service.historyLocationPos.HistoryLocationPosService;
import fly.socket.SendDataThread;
import fly.util.Distance;
import fly.util.JsonUtil;

public class ZKJATCPMessageHandler {
	private static Logger logger = Logger
			.getLogger(ZKJATCPMessageHandler.class);
	private static SimpleDateFormat formater = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private static Map<String, String> jizhan = new HashMap<String, String>();
	private DBManager dbManager = DBManager.getInstance();
	
	private DataService dataService = DataService.getInstance();

	private static AlarmCurrentService alarmCurrentService = AlarmCurrentService
			.getInstance();
	private static AlarmHistoryService alarmHistoryService = AlarmHistoryService
			.getInstance();
	private static CurrentLocationService currentLocationService = CurrentLocationService
			.getInstance();
	private static HistoryLocationManualService historyLocationManualService = HistoryLocationManualService
			.getInstance();
	private static HistoryLocationPosService historyLocationPosService = HistoryLocationPosService
			.getInstance();

	public String hanlder(String data) {
		String returnmessage = "";

		String imei = "";
		try {
			if ((data != null) && (!"".equals(data))) {
				Date sdate = new Date();
				String time = formater.format(sdate);
				String[] sage = data.split("\\|");
                boolean alarm=false;
				if ((data != null)
						&& ((data.contains("SOS")) || (data.contains("LOW")))) {
					imei = sage[2];
					String content = sage[1];
					QueryCondition qc = new QueryCondition(DevEntity.CODE,
							QueryCondition.eq, imei);
					qc.andCondition(new QueryCondition(DevEntity.TYPE,
							QueryCondition.eq, "16"));

					logger.debug("----------------");
					logger.debug("imei: " + imei);
					logger.debug("content: " + content);
					logger.debug("----------------");

					List<Object> devs = dbManager.queryByCondition(
							DevEntity.class, qc);
					if ((devs != null) && (devs.size() > 0)) {
						DevEntity dev = (DevEntity) devs.get(0);
						//获取当前AlarmCurrentEntity
						AlarmCurrentEntity alarmCurrent =null;
						AlarmHistoryEntity alarmHistory = new AlarmHistoryEntity();
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("devId", dev.getId());
						List<Object> list = alarmCurrentService.getListByCondition(queryMap);
						if(list!=null&&list.size()>0){
							alarmCurrent = (AlarmCurrentEntity)list.get(0);
						}else{
							alarmCurrent = new AlarmCurrentEntity();
						}						
						
						// 获取当前CurrentLocationEntity
						CurrentLocationEntity currentLocation = null;
						HistoryLocationManualEntity historyLocationManual = new HistoryLocationManualEntity();							
						
						Map<String, Object> queryMap2 = new HashMap<String, Object>();
						queryMap2.put("devId", dev.getId());
						List<Object> list2 = currentLocationService
								.getListByCondition(queryMap2);
						if (list2 != null && list2.size() > 0) {
							currentLocation = (CurrentLocationEntity) list2.get(0);
						} else {
							currentLocation = new CurrentLocationEntity();
						}
										
						if ("SOS".equals(content)) {
							currentLocation.setManualalarm("N");
							currentLocation.setBodyupdatetime(time);
							
							historyLocationManual.setManualalarm("N");
							historyLocationManual.setBodyupdatetime(time);	
							
							
							alarmCurrent.setCode("E023");
							alarmCurrent.setContent("老人定位器手动报警");
							alarmCurrent.setCreatedate(time);
														
							alarmHistory.setCode("E023");
							alarmHistory.setContent("老人定位器手动报警");
							alarmHistory.setCreatedate(time);		
							alarm = true;
						} else if ("LOW".equals(content)) {
							currentLocation.setPower("N");
							currentLocation.setDevupdatetime(formater.format(sdate));
							
							alarmCurrent.setCode("E002");
							alarmCurrent.setContent("老人定位器低电量报警");
							alarmCurrent.setCreatedate(time);
														
							alarmHistory.setCode("E002");
							alarmHistory.setContent("老人定位器低电量报警");
							alarmHistory.setCreatedate(time);	
						}
						currentLocation.setDevId(dev.getId());	
						historyLocationManual.setDevId(dev.getId());
						alarmCurrent.setDevId(dev.getId());
						alarmHistory.setDevId(dev.getId());
						
						currentLocationService.save(currentLocation);
						historyLocationManualService.save(historyLocationManual);
						alarmCurrentService.save(alarmCurrent);
						alarmHistoryService.save(alarmHistory);
						
						if(alarm){
							//----封装发射器应用帧开始 ----
							byte[] sendData = new byte[23];											
							
							//报警类型
							sendData[17]=(byte)0x02;										
							sendData[18]=(byte)0x00;
							sendData[19]=(byte)0x40;
									
							//----封装发射器应用帧结束 ----
							
							//向信号发射器发送数据
							if(sendData!=null&&sendData.length>0&&dev.getAlarmdevid()!=null&&!"".equals(dev.getAlarmdevid())&&dev.getAlarmcontent()!=null&&!"".equals(dev.getAlarmcontent())){
								String[] sage2 = dev.getAlarmdevid().split(",");
								if(sage2!=null&&sage2.length>0){
									for(int i=0;i<sage2.length;i++){
										Socket devSocket = DataService.socketMap.get(sage2[i]);
										if(devSocket!=null&&sage2[i].length()==8){
											//----封装发射器应用帧开始 ----
											//目的终端												
											sendData[0]=(byte)0x0a;
											sendData[1]=(byte)0x00;
											sendData[2]=(byte)0x00;
											sendData[3]=(byte)0x00;
											//源终端
											sendData[4]=(byte)0x01;
											sendData[5]=(byte)0x00;
											sendData[6]=(byte)0x00;
											sendData[7]=(byte)0x00;												
											//序列号
											byte[] suiji = new byte[2];
											Random random = new Random();
											random.nextBytes(suiji);
											sendData[8]=suiji[0];
											sendData[9]=suiji[1];
											//消息类型
											sendData[10]=(byte)0x01;
											//参数个数
											sendData[11]=(byte)0x03;
											//护工胸牌ID
											byte[] mac = DataService.HexString2Bytes(sage2[i]);
											sendData[12]=(byte)0x01;
											sendData[13]=mac[0];
											sendData[14]=mac[1];
											sendData[15]=mac[2];
											sendData[16]=mac[3];
											
											//报警数据
											sendData[20]=(byte)0x03;	
											if(dev.getAlarmcontent()!=null&&dev.getAlarmcontent().length()==4){
												byte[] alarmData = DataService.decimalToBytes(dev.getAlarmcontent(),2);
												sendData[21]=alarmData[0];
												sendData[22]=alarmData[1];
											}
											//----封装发射器应用帧结束 ----
											logger.debug("向信号发射器发送数据，胸牌code："+sage2[i]);
											sendData = dataService.dataChangeBack(sendData);
											//C0 0A000000 01000000 52EA 010301 0B0101D3 020040 03000128C0
											//C0 02010399 01000000 EAEE 0A 00 96C0
											logger.debug("向信号发射器发送数据，数据内容："+DataService.printHexString(sendData));											
											SendDataThread sendDataThread = new SendDataThread(devSocket,sendData);
											sendDataThread.start();
										}
									}
								}
								
							}
						}
						
						
					} else {
						logger.error("ZKJA数据处理：未找到设备信息 code：" + imei);
					}
				} else if ((data != null) && (data.contains("AUT"))) {

					// |AUT|865609025094097|162447.000,A,30.744899,E,120.487001,N,0.69,41.73,200715|5836,0008B7E|(I2C:*)||
					// |AUT|865609025094097|000000.000,V,00.000000,N,000.000000,E,0.0,0.0,000000|27,21|5089,0006262|(I2C:*)||
					boolean flag = false;
					imei = sage[2];
					QueryCondition qc = new QueryCondition(DevEntity.CODE,
							QueryCondition.eq, imei);
					qc.andCondition(new QueryCondition(DevEntity.TYPE,
							QueryCondition.eq, "16"));


					logger.debug("----------------");
					logger.debug("imei: " + imei);
					logger.debug("content: AUT");
					logger.debug("----------------");

					List<Object> devs = dbManager.queryByCondition(
							DevEntity.class, qc);
					if ((devs != null) && (devs.size() > 0)) {
						DevEntity dev = (DevEntity) devs.get(0);
						String[] temp = sage[3].split(",");						
						
						// 获取当前CurrentLocationEntity
						CurrentLocationEntity currentLocation = null;
						HistoryLocationPosEntity historyLocationPos = new HistoryLocationPosEntity();							
						
						Map<String, Object> queryMap2 = new HashMap<String, Object>();
						queryMap2.put("devId", dev.getId());
						List<Object> list2 = currentLocationService
								.getListByCondition(queryMap2);
						if (list2 != null && list2.size() > 0) {
							currentLocation = (CurrentLocationEntity) list2.get(0);
						} else {
							currentLocation = new CurrentLocationEntity();
						}
												
						if ((temp != null) && (temp.length == 9)) {
							BigDecimal pos_Lat = null;
							BigDecimal pos_Long = null;
							String address = "";
							int deviation = 0;
							if ("V".equals(temp[1])) {
								String laccelltemp = sage[5];
								String[] laccell = laccelltemp.split(",");
								if ((laccell != null) && (laccell.length == 2)) {
									String longlattemp = getLongLatByLacCell2(
											laccell[0], laccell[1]);
									String[] result = longlattemp
											.split("\\|\\,");
									if ((result != null)
											&& (result.length == 4)) {
										pos_Long = BigDecimal.valueOf(Double
												.valueOf(result[1])
												.doubleValue());
										pos_Lat = BigDecimal.valueOf(Double
												.valueOf(result[0])
												.doubleValue());
										deviation = Integer.valueOf(result[2])
												.intValue();
										address = result[3];

										
										flag = true;
										logger.debug("基站定位成功:坐标(" + pos_Lat
												+ "," + pos_Long + ")，误差："
												+ deviation + "米,地址：" + address);
									} else {
										logger.debug("基站定位失败: 远程获取经纬度失败，不更新坐标数据!");
										return returnmessage;
									}
								} else {
									logger.debug("基站定位失败: 终端未上传基站信息，不更新坐标数据!");
									return returnmessage;
								}
							} else if ("A".equals(temp[1])) {
								double poslong = Double.valueOf(temp[2])
										.doubleValue();
								double poslat = Double.valueOf(temp[4])
										.doubleValue();

								pos_Lat = BigDecimal.valueOf(poslat);
								pos_Long = BigDecimal.valueOf(poslong);
								logger.debug("精确定位成功:坐标(" + pos_Lat + ","
										+ pos_Long + ")");

								// GPS转化为百度地图坐标
								String longlattemp = this.getLongLatByBaiDu(
										poslong, poslat);
								String[] result = longlattemp.split("\\|\\,");
								if ((result != null) && (result.length == 2)) {
									pos_Long = BigDecimal.valueOf(Double
											.valueOf(result[1]).doubleValue());
									pos_Lat = BigDecimal.valueOf(Double
											.valueOf(result[0]).doubleValue());
									flag = true;
									logger.debug("百度坐标转换成功:坐标(" + pos_Lat + ","
											+ pos_Long + ")");
								} else {
									logger.debug("百度坐标转换失败: 百度坐标转换失败，不更新坐标数据!");
									return returnmessage;
								}
								

							}
							
							if(flag){
								currentLocation.setDevId(dev.getId());
								currentLocation.setCurrlat(pos_Lat);
								currentLocation.setCurrlog(pos_Long);
								currentLocation.setLeavedupdatetime(time);
								
								
								historyLocationPos.setCurrlat(pos_Lat);
								historyLocationPos.setCurrlog(pos_Long);
								historyLocationPos.setDevId(dev.getId());
								historyLocationPos.setLeavedupdatetime(time);
								
								currentLocationService.save(currentLocation);
								historyLocationPosService.save(historyLocationPos);								
							}						
						}

						
					} else {
						logger.error("未查询到设备，设备code：" + imei);
					}

				}
			} else {
				logger.error("ZKJA数据处理：data为空！");
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return returnmessage;
	}

	public static boolean queryYueJie(BigDecimal f_Long, BigDecimal f_Lat,
			BigDecimal pos_Long, BigDecimal pos_Lat, Integer f_Radius) {
		boolean result = false;

		double dis = Distance.GetDistance(f_Long.doubleValue(),
				f_Lat.doubleValue(), pos_Long.doubleValue(),
				pos_Lat.doubleValue());
		if (dis > Double.valueOf(String.valueOf(f_Radius)).doubleValue()) {
			result = true;
		}
		return result;
	}

	public static String getLongLatByLacCell2(String lac, String cellid) {
		String result = "";
		String key = lac + "_" + cellid;
		try {
			result = (String) jizhan.get(key);
			if ((result != null) && (!"".equals(result))) {
				return result;
			}

			String urlstr = "http://v.juhe.cn/cell/get?hex=16&mnc=0&cell="
					+ cellid + "&lac=" + lac + "&key=" + SystemInit.gpskey;
			logger.debug("基站定位url：" + urlstr);
			URL url = new URL(urlstr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.connect();

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuffer sb = new StringBuffer("");
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}

			String rep = sb.toString();
			logger.debug("基站定位远程返回：" + rep);

			if (rep.indexOf("ADDRESS") > 0) {
				rep = rep.substring(0, rep.indexOf("ADDRESS") - 2)
						+ "}]},\"error_code\":0}";
			}

			Map reqParams = JsonUtil.getMap4Json(rep);
			String resultcode = (String) reqParams.get("resultcode");
			if ("200".equals(resultcode)) {
				JSONObject reslutreq = (JSONObject) reqParams.get("result");
				JSONArray listjson = reslutreq.getJSONArray("data");
				if (listjson != null) {
					JSONObject jsonObj = (JSONObject) listjson.opt(0);
					String lng = (String) jsonObj.get("LNG");
					String lat = (String) jsonObj.get("LAT");
					String PRECISION = (String) jsonObj.get("PRECISION");
					Integer deviation = Integer.valueOf(0);
					if ((PRECISION != null) && (!"".equals(PRECISION))) {
						deviation = Integer.valueOf(PRECISION);
					}
					String address = (String) jsonObj.get("ADDRESS");
					result = lat + "|," + lng + "|," + deviation + "|,"
							+ address;
				}
			}

			reader.close();

			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if ((result != null) && (!"".equals(result)))
			jizhan.put(key, result);
		else {
			result = "";
		}
		return result;
	}

	public static String getLongLatByBaiDu(double posLong, double posLat) {
		String result = "";
		String key = posLat + "," + posLong;
		try {
			String urlstr = "http://api.map.baidu.com/geoconv/v1/?ak=E6faddbe04652555f7f3e9a133f45f6a&coords="
					+ key;
			logger.debug("百度地图url：" + urlstr);
			URL url = new URL(urlstr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.connect();

			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());

			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			StringBuffer sb = new StringBuffer("");
			String lines;
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}

			String rep = sb.toString();
			logger.debug("百度地图url返回：" + rep);

			Map reqParams = JsonUtil.getMap4Json(rep);
			Integer resultcode = (Integer) reqParams.get("status");
			if (resultcode != null && resultcode.intValue() == 0) {
				JSONArray listjson = (JSONArray) reqParams.get("result");
				if (listjson != null) {
					JSONObject jsonObj = (JSONObject) listjson.opt(0);
					Double lng = (Double) jsonObj.get("y");
					Double lat = (Double) jsonObj.get("x");
					result = lat + "|," + lng;
				}
			}

			reader.close();

			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if ((result != null) && (!"".equals(result)))
			jizhan.put(key, result);
		else {
			result = "";
		}
		return result;
	}

	public static void main(String[] args) {
		ZKJATCPMessageHandler a = new ZKJATCPMessageHandler();
		// String b =
		// "|355910044336974|AUTO|093622.000,A,12052.5295,E,3123.3045,N,2.89,182.66,180814|22,99|5089,B075||";
		//
		// a.hanlder(b, null);
		a.getLongLatByBaiDu(31.406172, 120.894153);
	}
}