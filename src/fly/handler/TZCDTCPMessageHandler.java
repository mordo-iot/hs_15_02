package fly.handler;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.currentTizhengBed.CurrentTizhengBedEntity;
import fly.entity.dev.DevEntity;
import fly.entity.historyTizhengBed.HistoryTizhengBedEntity;
import fly.entity.vo.SocketDataVO;
import fly.service.DataService;
import fly.service.alarmCurrent.AlarmCurrentService;
import fly.service.alarmHistory.AlarmHistoryService;
import fly.service.currentTizhengBed.CurrentTizhengBedService;
import fly.service.dev.DevService;
import fly.service.historyTizhengBed.HistoryTizhengBedService;
import fly.socket.SendDataThread;

public class TZCDTCPMessageHandler {
	private static Logger logger = Logger
			.getLogger(TZCDTCPMessageHandler.class);
	private static SimpleDateFormat formater = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private DataService dataService = DataService.getInstance();

	private static DevService devService = DevService.getInstance();

	private static AlarmCurrentService alarmCurrentService = AlarmCurrentService
			.getInstance();
	private static AlarmHistoryService alarmHistoryService = AlarmHistoryService
			.getInstance();
	private static CurrentTizhengBedService currentTizhengBedService = CurrentTizhengBedService
			.getInstance();
	private static HistoryTizhengBedService historyTizhengBedService = HistoryTizhengBedService
			.getInstance();
	
	public static Map<String,Integer> statusMap = new HashMap<String,Integer>();

	public byte[] hanlder(byte[] data) {
		String returnmessage = "";
		try {
			Date date = new Date();
			byte[] source = { data[1], data[2], data[3], data[4], data[5],
					data[6] };
			String devCode = DataService.printHexString(source).toLowerCase();
			if (devCode != null && !"".equals(devCode)) {
				logger.debug("体征床垫信息监听:床垫code：" + devCode);
				DevEntity dev = null;
				if (devCode != null && !"".equals(devCode)) {
					Map<String, Object> queryMap3 = new HashMap<String, Object>();
					queryMap3.put("type", "16");
					queryMap3.put("code", devCode.toLowerCase());
					List<Object> list3 = devService
							.getListByCondition(queryMap3);
					if (list3 != null && list3.size() > 0) {
						dev = (DevEntity) list3.get(0);
					}
					if (dev != null) {
						// 获取当前AlarmCurrentEntity
						AlarmCurrentEntity alarmCurrent = null;
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("devId", dev.getId());
						List<Object> list = alarmCurrentService
								.getListByCondition(queryMap);
						if (list != null && list.size() > 0) {
							alarmCurrent = (AlarmCurrentEntity) list.get(0);
						} else {
							alarmCurrent = new AlarmCurrentEntity();
						}
						AlarmHistoryEntity alarmHistory = new AlarmHistoryEntity();
						byte[] sendData = null;
						// 获取当前CurrentTizhengBedEntity
						CurrentTizhengBedEntity currentTizhengBed = null;
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("devId", dev.getId());
						List<Object> list2 = currentTizhengBedService
								.getListByCondition(map);
						if (list2 != null && list2.size() > 0) {
							currentTizhengBed = (CurrentTizhengBedEntity) list2
									.get(0);
						} else {
							currentTizhengBed = new CurrentTizhengBedEntity();
						}
						HistoryTizhengBedEntity historyTizhengBed = new HistoryTizhengBedEntity();
						String time = formater.format(date);
                        boolean currentTizhengBedSave = false;
                        boolean historyTizhengBedSave = false;
//						int niaoshibit = DataService.readBit(data[7], 7);					
//						if (niaoshibit == 1) {
//							logger.debug("数据类型:【体征床垫】应用帧：【尿湿报警】："+dev.getCode());	
//
//						} else if (niaoshibit == 0) {
//							// 未尿湿
//							logger.debug("数据类型:【体征床垫】应用帧：【尿湿消警】："+dev.getCode());	
//						}
						if (data[7] == (byte) 0x30 || data[7] == (byte) 0x31
								|| data[7] == (byte) 0xb0
								|| data[7] == (byte) 0xb1) {							
							logger.debug("数据类型:【体征床垫】应用帧：【手动报警】："+dev.getCode());	
	
						} else if (data[7] == (byte) 0x41
								|| data[7] == (byte) 0xc1||data[7] == (byte) 0x20
								|| data[7] == (byte) 0xa0) {							
							// 体动或在床
							int temp1=0;
							Integer tempint1 = statusMap.get(devCode+"_1");
							boolean tempboolean1=false;
							if(tempint1!=null&&tempint1.intValue()==temp1){
								//状态无变化
								tempboolean1 = false;
							}else{
								//状态有变化
								tempboolean1 = true;
								statusMap.put(devCode+"_1", temp1);
							}	
							if(tempboolean1){
								logger.debug("数据类型:【体征床垫】应用帧：【有人消警】："+dev.getCode());
								//取消报警mordo_state_current_TizhengBed,mordo_state_history_TizhengBed
								currentTizhengBed.setHavingbody("Y");
								currentTizhengBed.setAlarmupdatetime(time);
								currentTizhengBed.setDevId(dev.getId());
								currentTizhengBed.setDevupdatetime(time);
								
								historyTizhengBed.setHavingbody("Y");
								historyTizhengBed.setAlarmupdatetime(time);
								historyTizhengBed.setDevId(dev.getId());
								historyTizhengBed.setDevupdatetime(time);
								
								currentTizhengBedSave = true;
								historyTizhengBedSave = true;
								
//								//----封装发射器应用帧开始 ----
//								sendData = new byte[23];											
//								
//								//报警类型
//								sendData[17]=(byte)0x02;										
//								sendData[18]=(byte)0x80;
//								sendData[19]=(byte)0x40;
//								
//								//----封装发射器应用帧结束 ----
							}							
						} else if (data[7] == (byte) 0x50
								|| data[7] == (byte) 0xd0) {
							// 离床
							int temp1=1;
							Integer tempint1 = statusMap.get(devCode+"_1");
							boolean tempboolean1=false;
							if(tempint1!=null&&tempint1.intValue()==temp1){
								//状态无变化
								tempboolean1 = false;
							}else{
								//状态有变化
								tempboolean1 = true;
								statusMap.put(devCode+"_1", temp1);
							}
							if(tempboolean1){
								logger.debug("数据类型:【体征床垫】应用帧：【离床报警】："+dev.getCode());
								//报警mordo_state_current_TizhengBed,mordo_state_history_TizhengBed,mordo_alarm_current,mordo_alarm_history
								currentTizhengBed.setHavingbody("N");
								currentTizhengBed.setAlarmupdatetime(time);
								currentTizhengBed.setDevId(dev.getId());
								currentTizhengBed.setDevupdatetime(time);
								
								historyTizhengBed.setHavingbody("N");
								historyTizhengBed.setAlarmupdatetime(time);
								historyTizhengBed.setDevId(dev.getId());
								historyTizhengBed.setDevupdatetime(time);
								
								
								alarmCurrent.setCode("E081");
								alarmCurrent.setContent("体征床垫离床报警");
								alarmCurrent.setCreatedate(time);
								alarmCurrent.setDevId(dev.getId());
								
								alarmHistory.setCode("E081");
								alarmHistory.setContent("体征床垫离床报警");
								alarmHistory.setCreatedate(time);
								alarmHistory.setDevId(dev.getId());
								
								currentTizhengBedSave = true;
								historyTizhengBedSave = true;
															
								alarmCurrentService.save(alarmCurrent);
								alarmHistoryService.save(alarmHistory);
								
								
								//----封装发射器应用帧开始 ----
								sendData = new byte[23];											
								
								//报警类型
								sendData[17]=(byte)0x02;										
								sendData[18]=(byte)0x00;
								sendData[19]=(byte)0x04;
										
								//----封装发射器应用帧结束 ----
							}							
						}
						//心跳数
						byte[] heart = {data[8],data[9],data[10]};		
						if(DataService.printHexString(heart)!=null&&DataService.printHexString(heart).length()==6){
							String heartNumStr = DataService.printHexString(heart).substring(1, 2)+DataService.printHexString(heart).substring(3, 4)+DataService.printHexString(heart).substring(5, 6);
							if(heartNumStr!=null&&Integer.valueOf(heartNumStr)>0){
								currentTizhengBed.setHeart(Integer.valueOf(heartNumStr));
								currentTizhengBed.setDevupdatetime(time);
								currentTizhengBed.setDevId(dev.getId());
								int temp1=Integer.valueOf(heartNumStr);
								Integer tempint1 = statusMap.get(devCode+"_2");
								boolean tempboolean1=false;
								if(tempint1!=null&&tempint1.intValue()==temp1){
									//状态无变化
									tempboolean1 = false;
								}else{
									//状态有变化
									tempboolean1 = true;
									statusMap.put(devCode+"_2", temp1);
								}
								if(tempboolean1){
									logger.debug("数据类型:【体征床垫】应用帧：【心跳数】："+heartNumStr+"有变化！历史记录新增");
									historyTizhengBed.setHeart(Integer.valueOf(heartNumStr));
									historyTizhengBed.setDevupdatetime(time);
									historyTizhengBed.setDevId(dev.getId());
									
									
									historyTizhengBedSave = true;
								}
								currentTizhengBedSave = true;
								
							}
						}
						
						//呼吸数
						byte[] breath = {data[11],data[12]};
						if(DataService.printHexString(breath)!=null&&DataService.printHexString(breath).length()==4){
							String breathNumStr = DataService.printHexString(breath).substring(1, 2)+DataService.printHexString(breath).substring(3, 4);	
							if(breathNumStr!=null&&Integer.valueOf(breathNumStr)>0){								
								currentTizhengBed.setBreath(Integer.valueOf(breathNumStr));
								currentTizhengBed.setDevupdatetime(time);
								currentTizhengBed.setDevId(dev.getId());
								
								int temp1=Integer.valueOf(breathNumStr);
								Integer tempint1 = statusMap.get(devCode+"_3");
								boolean tempboolean1=false;
								if(tempint1!=null&&tempint1.intValue()==temp1){
									//状态无变化
									tempboolean1 = false;
								}else{
									//状态有变化
									tempboolean1 = true;
									statusMap.put(devCode+"_3", temp1);
								}
								if(tempboolean1){
									logger.debug("数据类型:【体征床垫】应用帧：【呼吸数】："+breathNumStr+"有变化！历史记录新增");	
									historyTizhengBed.setBreath(Integer.valueOf(breathNumStr));
									historyTizhengBed.setDevupdatetime(time);
									historyTizhengBed.setDevId(dev.getId());
									
									
									historyTizhengBedSave = true;
								}
								currentTizhengBedSave = true;
							}
						}
						if(currentTizhengBedSave){
							currentTizhengBedService.save(currentTizhengBed);
						}
						if(historyTizhengBedSave){
							historyTizhengBedService.save(historyTizhengBed);
						}			
						
						//向信号发射器发送数据
						if(sendData!=null&&sendData.length>0&&dev.getAlarmdevid()!=null&&!"".equals(dev.getAlarmdevid())&&dev.getAlarmcontent()!=null&&!"".equals(dev.getAlarmcontent())){
							String[] sage = dev.getAlarmdevid().split(",");
							if(sage!=null&&sage.length>0){
								for(int i=0;i<sage.length;i++){
									if(sage[i].length()==8){
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
										byte[] mac = DataService.HexString2Bytes(sage[i]);
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
										logger.debug("向信号发射器发送数据加入发送队列，胸牌code："+sage[i]);
										sendData = dataService.dataChangeBack(sendData);
										//C0 0A000000 01000000 52EA 010301 0B0101D3 020040 03000128C0
										//C0 02010399 01000000 EAEE 0A 00 96C0
										logger.debug("向信号发射器发送数据加入发送队列，数据内容："+DataService.printHexString(sendData));											
										SocketDataVO sd = new SocketDataVO();
										sd.setAlarmdevid(sage[i]);
								    	sd.setData(sendData);
										SendDataThread.lList.add(sd);
									}
								}
							}
							
						}
						
					} else {
						logger.debug("设备数据不存在，type：" + 16 + " code：" + devCode);
					}
				}
			} else {
				logger.debug("体征床垫信息监听:床垫code为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}