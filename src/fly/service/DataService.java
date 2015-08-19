package fly.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.framework.system.db.manager.DBManager;

import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.currentBed.CurrentBedEntity;
import fly.entity.currentDoor.CurrentDoorEntity;
import fly.entity.currentIr.CurrentIrEntity;
import fly.entity.currentKeyalarm.CurrentKeyalarmEntity;
import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.currentUrine.CurrentUrineEntity;
import fly.entity.currentWandai.CurrentWandaiEntity;
import fly.entity.dev.DevEntity;
import fly.entity.historyBed.HistoryBedEntity;
import fly.entity.historyDoor.HistoryDoorEntity;
import fly.entity.historyIr.HistoryIrEntity;
import fly.entity.historyKeyalarm.HistoryKeyalarmEntity;
import fly.entity.historyLocationBody.HistoryLocationBodyEntity;
import fly.entity.historyLocationManual.HistoryLocationManualEntity;
import fly.entity.historyLocationMove.HistoryLocationMoveEntity;
import fly.entity.historyLocationPos.HistoryLocationPosEntity;
import fly.entity.historyUrine.HistoryUrineEntity;
import fly.entity.historyWandai.HistoryWandaiEntity;
import fly.entity.position.PositionEntity;
import fly.service.alarmCurrent.AlarmCurrentService;
import fly.service.alarmHistory.AlarmHistoryService;
import fly.service.currentBed.CurrentBedService;
import fly.service.currentDoor.CurrentDoorService;
import fly.service.currentIr.CurrentIrService;
import fly.service.currentKeyalarm.CurrentKeyalarmService;
import fly.service.currentLocation.CurrentLocationService;
import fly.service.currentUrine.CurrentUrineService;
import fly.service.currentWandai.CurrentWandaiService;
import fly.service.dev.DevService;
import fly.service.historyBed.HistoryBedService;
import fly.service.historyDoor.HistoryDoorService;
import fly.service.historyIr.HistoryIrService;
import fly.service.historyKeyalarm.HistoryKeyalarmService;
import fly.service.historyLocationBody.HistoryLocationBodyService;
import fly.service.historyLocationManual.HistoryLocationManualService;
import fly.service.historyLocationMove.HistoryLocationMoveService;
import fly.service.historyLocationPos.HistoryLocationPosService;
import fly.service.historyUrine.HistoryUrineService;
import fly.service.historyWandai.HistoryWandaiService;
import fly.service.position.PositionService;
import fly.socket.SendDataThread;

public class DataService {	
	private static Logger logger = Logger.getLogger(DataService.class);
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
	private DBManager dbManager = DBManager.getInstance();

	private static DataService dataService;
	/**
	 * 保存信号发射器对应的护工胸牌code,socket
	 */
	public static Map<String,Socket> socketMap = new HashMap<String,Socket>();
	
	public static Map<String,Integer> statusMap = new HashMap<String,Integer>();

	private static DevService devService=DevService.getInstance();
	private static PositionService positionService=PositionService.getInstance();
	private static AlarmCurrentService alarmCurrentService=AlarmCurrentService.getInstance();
	private static AlarmHistoryService alarmHistoryService=AlarmHistoryService.getInstance();
	private static CurrentKeyalarmService currentKeyalarmService=CurrentKeyalarmService.getInstance();
	private static HistoryKeyalarmService historyKeyalarmService=HistoryKeyalarmService.getInstance();
	private static CurrentWandaiService currentWandaiService=CurrentWandaiService.getInstance();
	private static HistoryWandaiService historyWandaiService=HistoryWandaiService.getInstance();
	private static CurrentDoorService currentDoorService=CurrentDoorService.getInstance();
	private static HistoryDoorService historyDoorService=HistoryDoorService.getInstance();
	private static CurrentBedService currentBedService=CurrentBedService.getInstance();
	private static HistoryBedService historyBedService=HistoryBedService.getInstance();
	private static CurrentIrService currentIrService=CurrentIrService.getInstance();
	private static HistoryIrService historyIrService=HistoryIrService.getInstance();
	private static CurrentUrineService currentUrineService=CurrentUrineService.getInstance();
	private static HistoryUrineService historyUrineService=HistoryUrineService.getInstance();
	private static CurrentLocationService currentLocationService=CurrentLocationService.getInstance();
	private static HistoryLocationBodyService historyLocationBodyService=HistoryLocationBodyService.getInstance();
	private static HistoryLocationManualService historyLocationManualService=HistoryLocationManualService.getInstance();
	private static HistoryLocationMoveService historyLocationMoveService=HistoryLocationMoveService.getInstance();
	private static HistoryLocationPosService historyLocationPosService=HistoryLocationPosService.getInstance();
	

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static DataService getInstance() {
		if (dataService == null) {
			dataService = new DataService();
		}
		return dataService;
	}

	public byte[] dataHandler(byte[] data,Socket socket){
		byte[] result = null;		
		try {
			if(data.length>11){
				Date date = new Date();
				//检查源是否是信号发射器
				byte[] source = {data[4],data[5],data[6],data[7]};
				if(source[0]==(byte)0x0A){
					//信号发射器
					byte dataType=data[10];
					String sourceStr = this.printHexString(source);
					if(dataType==(byte)0x05){
						logger.debug("数据类型:信号发射器【上行心跳帧】："+sourceStr);
						//上行心跳帧
						//记录socket						
						if(sourceStr!=null&&!"".equals(sourceStr)){
							//查询信号发射器是否存在
							Map<String, Object> queryMap = new HashMap<String, Object>();
							queryMap.put("type", 15);
							queryMap.put("code", sourceStr.toLowerCase());
							List<Object> list = devService.getListByCondition(queryMap);
							if(list!=null&&list.size()>0){
								DevEntity dev = (DevEntity)list.get(0);
								if(dev!=null&&dev.getId()!=null){
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("emitid", dev.getId());
									List<Object> list2 = devService.getListByCondition(map);
									if(list2!=null&&list2.size()>0){
										DevEntity dev2 = (DevEntity)list2.get(0);
										if(dev2!=null&&dev2.getCode()!=null){
											logger.debug("信号发射器设备绑定护工胸牌，保存socket，胸牌code："+dev2.getCode());
											//保存胸牌对应的socket
											socketMap.put(dev2.getCode(), socket);
										}
									}else{
										logger.debug("信号发射器设备未绑定护工胸牌，不保存socket，type："+15+" code："+sourceStr.toLowerCase());
									}
								}
							}else{
								
							}
							
						}						
						//----封装回复帧开始 ----
						result = new byte[12];
						//目的终端
						result[0]=data[4];
						result[1]=data[5];
						result[2]=data[6];
						result[3]=data[7];
						//源终端
						result[4]=data[0];
						result[5]=data[1];
						result[6]=data[2];
						result[7]=data[3];
						//序列号
						result[8]=data[8];
						result[9]=data[9];
						//消息类型
						result[10]=(byte)0x06;
						//参数个数
						result[11]=(byte)0x00;									
						//----封装回复帧结束 ----
					}else if(dataType==(byte)0x02){
						//应用帧回复
						logger.debug("数据类型：信号发射器回复帧");
						return null;
					}
				}else if(source[0]==(byte)0x05){
					    //园区一卡通
						//状态应用帧:01000000 05000469 004D 0A 02 50 BE 52 8E
						//定位应用帧:01000000 05000469 0327 09 01 51 060003A9 A6
						//根据类型和编号查询设备是否存在
						String devCode = printHexString(source);
						DevEntity dev = null;
						if(devCode!=null&&!"".equals(devCode)){
							Map<String, Object> queryMap = new HashMap<String, Object>();
							queryMap.put("type", "4");
							queryMap.put("code", devCode.toLowerCase());
							List<Object> list = devService.getListByCondition(queryMap);
							if(list!=null&&list.size()>0){
								dev = (DevEntity)list.get(0);
							}
						}	
				        if(dev!=null){
				        	//获取当前AlarmCurrentEntity
							AlarmCurrentEntity alarmCurrent =null;
							Map<String, Object> queryMap = new HashMap<String, Object>();
							queryMap.put("devId", dev.getId());
							List<Object> list = alarmCurrentService.getListByCondition(queryMap);
							if(list!=null&&list.size()>0){
								alarmCurrent = (AlarmCurrentEntity)list.get(0);
							}else{
								alarmCurrent = new AlarmCurrentEntity();
							}
							AlarmHistoryEntity alarmHistory = new AlarmHistoryEntity();
							byte[] sendData= null;
				        	//获取当前CurrentLocationEntity
				        	CurrentLocationEntity currentLocation =null;
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("devId", dev.getId());
							List<Object> list2 = currentLocationService.getListByCondition(map);
							if(list2!=null&&list2.size()>0){
								currentLocation = (CurrentLocationEntity)list2.get(0);
							}else{
								currentLocation = new CurrentLocationEntity();
							}
							HistoryLocationBodyEntity historyLocationBody = new HistoryLocationBodyEntity();
							HistoryLocationManualEntity historyLocationManual = new HistoryLocationManualEntity();
							HistoryLocationMoveEntity historyLocationMove = new HistoryLocationMoveEntity();
							HistoryLocationPosEntity historyLocationPos = new HistoryLocationPosEntity();
		                    String time=formater.format(date);
							
							if(data[10]==(byte)0x0A){
								//状态应用帧
								int parmnum = this.byteToDecimal(data[11]);
								logger.debug("参数个数："+parmnum);
								for(int i=0;i<parmnum;i++){
									if(data[12+2*i]==(byte)0x50){
										//身体状态
										int temp1 = this.readBit(data[13+2*i], 0);
										int temp2 = this.readBit(data[13+2*i], 4);
										Integer tempint1 = statusMap.get(devCode+"_1");
										Integer tempint2 = statusMap.get(devCode+"_2");
										boolean tempboolean1=false;
										boolean tempboolean2=false;
										if(tempint1!=null&&tempint1.intValue()==temp1){
											//状态无变化
											tempboolean1 = false;
										}else{
											//状态有变化
											tempboolean1 = true;
											statusMap.put(devCode+"_1", temp1);
										}										
										if(tempint2!=null&&tempint2.intValue()==temp2){
											//状态无变化
											tempboolean2 = false;
										}else{
											//状态有变化
											tempboolean2 = true;
											statusMap.put(devCode+"_2", temp2);
										}	
										if(temp1==1&&tempboolean1){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【摔倒报警】："+dev.getCode());
											//报警mordo_state_current_Location,mordo_state_history_Location,mordo_alarm_current,mordo_alarm_history
											currentLocation.setBodystate("N");
											currentLocation.setBodyupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationBody.setBodystate("N");
											historyLocationBody.setBodyupdatetime(time);
											historyLocationBody.setDevId(dev.getId());
											
											alarmCurrent.setCode("E022");
											alarmCurrent.setContent("一卡通摔倒报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E022");
											alarmHistory.setContent("一卡通摔倒报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationBodyService.save(historyLocationBody);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x01;
													
											//----封装发射器应用帧结束 ----
										}else if(temp1==0&&tempboolean1){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【摔倒取消报警】："+dev.getCode());
											//取消报警mordo_state_current_Location,mordo_state_history_Location
											currentLocation.setBodystate("Y");
											currentLocation.setBodyupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationBody.setBodystate("Y");
											historyLocationBody.setBodyupdatetime(time);
											historyLocationBody.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationBodyService.save(historyLocationBody);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
										}
										if(temp2==1&&tempboolean2){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【手动报警】："+dev.getCode());
											//报警mordo_state_current_Location,mordo_state_history_Location,mordo_alarm_current,mordo_alarm_history
											currentLocation.setManualalarm("N");
											currentLocation.setBodyupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationManual.setManualalarm("N");
											historyLocationManual.setBodyupdatetime(time);
											historyLocationManual.setDevId(dev.getId());
											
											alarmCurrent.setCode("E023");
											alarmCurrent.setContent("一卡通手动报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E023");
											alarmHistory.setContent("一卡通手动报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationManualService.save(historyLocationManual);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x02;
													
											//----封装发射器应用帧结束 ----
										}else if(temp2==0&&tempboolean2){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【手动取消报警】："+dev.getCode());
											//取消报警mordo_state_current_Location,mordo_state_history_Location
											currentLocation.setManualalarm("Y");
											currentLocation.setBodyupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationManual.setManualalarm("Y");
											historyLocationManual.setBodyupdatetime(time);
											historyLocationManual.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationManualService.save(historyLocationManual);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
										}
										
									}else if(data[12+2*i]==(byte)0x52){
										//设备报警
										int temp3 = this.readBit(data[13+2*i], 0);
										int temp4 = this.readBit(data[13+2*i], 4);
										Integer tempint3 = statusMap.get(devCode+"_3");
										Integer tempint4 = statusMap.get(devCode+"_4");
										boolean tempboolean3=false;
										boolean tempboolean4=false;
										if(tempint3!=null&&tempint3.intValue()==temp3){
											//状态无变化
											tempboolean3 = false;
										}else{
											//状态有变化
											tempboolean3 = true;
											statusMap.put(devCode+"_3", temp3);
										}
										if(tempint4!=null&&tempint4.intValue()==temp4){
											//状态无变化
											tempboolean4 = false;
										}else{
											//状态有变化
											tempboolean4 = true;
											statusMap.put(devCode+"_4", temp4);
										}
										if(temp3==1&&tempboolean3){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【低电压报警】："+dev.getCode());
											//报警mordo_state_current_Location,mordo_state_history_Location,mordo_alarm_current,mordo_alarm_history
											currentLocation.setPower("N");
											currentLocation.setDevupdatetime(time);
											currentLocation.setDevId(dev.getId());									
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent("一卡通低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent("一卡通低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
	
										}else if(temp3==0&&tempboolean3){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【低电压取消报警】："+dev.getCode());
											currentLocation.setPower("Y");
											currentLocation.setDevupdatetime(time);
											currentLocation.setDevId(dev.getId());		
											
											currentLocationService.save(currentLocation);											
										}
										if(temp4==1&&tempboolean4){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【人卡分离报警】："+dev.getCode());
											//报警mordo_state_current_Location,mordo_state_history_Location,mordo_alarm_current,mordo_alarm_history
											currentLocation.setMoving("N");
											currentLocation.setMovingupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationMove.setMoving("N");
											historyLocationMove.setMovingupdatetime(time);
											historyLocationMove.setDevId(dev.getId());
											
											alarmCurrent.setCode("E024");
											alarmCurrent.setContent("一卡通人卡分离报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E024");
											alarmHistory.setContent("一卡通人卡分离报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationMoveService.save(historyLocationMove);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x10;
													
											//----封装发射器应用帧结束 ----
										}else if(temp4==0&&tempboolean4){
											logger.debug("数据类型:【园区一卡通】状态应用帧：【人卡分离取消报警】："+dev.getCode());
											//取消报警mordo_state_current_Location,mordo_state_history_Location
											currentLocation.setMoving("Y");
											currentLocation.setMovingupdatetime(time);
											currentLocation.setDevId(dev.getId());
											
											historyLocationMove.setMoving("Y");
											historyLocationMove.setMovingupdatetime(time);
											historyLocationMove.setDevId(dev.getId());
											
											currentLocationService.save(currentLocation);
											historyLocationMoveService.save(historyLocationMove);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
										}
									}
								}																					
							}else if(data[10]==(byte)0x09&&data[11]==(byte)0x01&&data[12]==(byte)0x51){
								//定位应用帧C001000000 05000469 07D0 09 01 51 060003A9 A2 E9C0
								logger.debug("数据类型:【园区一卡通】定位应用帧："+dev.getCode());
								byte[] luyoujiedian={data[13],data[14],data[15],data[16]};
								//根据类型和编号查询设备是否存在
								String luyoujiedianStr = printHexString(luyoujiedian);
								logger.debug("数据类型:【园区一卡通】路由节点code："+luyoujiedianStr);
								DevEntity dev2 = null;
								if(luyoujiedianStr!=null&&!"".equals(luyoujiedianStr)){
									Map<String, Object> queryMap4 = new HashMap<String, Object>();
									queryMap4.put("type", "6");
									queryMap4.put("code", luyoujiedianStr.toLowerCase());
									List<Object> list4 = devService.getListByCondition(queryMap4);
									if(list4!=null&&list4.size()>0){
										dev2 = (DevEntity)list4.get(0);
									}
								}
								if(dev2!=null){
									//查询dev对应的pos
									Map<String, Object> queryMap3 = new HashMap<String, Object>();
									queryMap3.put("devId", dev2.getId());
									List<Object> list3 = positionService.getListByCondition(queryMap3);
									if(list3!=null&&list3.size()>0){
										PositionEntity pos = (PositionEntity)list3.get(0);
										if(pos!=null){
											logger.debug("数据类型:【园区一卡通】设备位置信息："+pos.getName());
											currentLocation.setDevId(dev.getId());
											currentLocation.setCurrlat(BigDecimal.valueOf(0));
											currentLocation.setCurrlog(BigDecimal.valueOf(0));
											currentLocation.setCurrpositionId(pos.getId());
											currentLocation.setLeavedupdatetime(time);
											
											
											historyLocationPos.setCurrlat(BigDecimal.valueOf(0));
											historyLocationPos.setCurrlog(BigDecimal.valueOf(0));
											historyLocationPos.setCurrpositionid(pos.getId());
											historyLocationPos.setDevId(dev.getId());
											historyLocationPos.setLeavedupdatetime(time);
											
											currentLocationService.save(currentLocation);
											historyLocationPosService.save(historyLocationPos);	
										}
									}else{
										logger.debug("数据类型:【园区一卡通】未找到设备位置信息！："+luyoujiedianStr);
									}
								}else{
									logger.debug("数据类型:【园区一卡通】未找到路由节点数据！："+luyoujiedianStr);
								}
								
								
							}
							
							//向信号发射器发送数据
							if(sendData!=null&&sendData.length>0&&dev.getAlarmdevid()!=null&&!"".equals(dev.getAlarmdevid())&&dev.getAlarmcontent()!=null&&!"".equals(dev.getAlarmcontent())){
								String[] sage = dev.getAlarmdevid().split(",");
								if(sage!=null&&sage.length>0){
									for(int i=0;i<sage.length;i++){
										Socket devSocket = socketMap.get(sage[i]);
										if(devSocket!=null&&sage[i].length()==8){
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
											byte[] mac = HexString2Bytes(sage[i]);
											sendData[12]=(byte)0x01;
											sendData[13]=mac[0];
											sendData[14]=mac[1];
											sendData[15]=mac[2];
											sendData[16]=mac[3];
											
											//报警数据
											sendData[20]=(byte)0x03;	
											if(dev.getAlarmcontent()!=null&&dev.getAlarmcontent().length()==4){
												byte[] alarmData = decimalToBytes(dev.getAlarmcontent(),2);
												sendData[21]=alarmData[0];
												sendData[22]=alarmData[1];
											}
											//----封装发射器应用帧结束 ----
											logger.debug("向信号发射器发送数据，胸牌code："+sage[i]);
											sendData = dataService.dataChangeBack(sendData);
											//C0 0A000000 01000000 52EA 010301 0B0101D3 020040 03000128C0
											//C0 02010399 01000000 EAEE 0A 00 96C0
											logger.debug("向信号发射器发送数据，数据内容："+this.printHexString(sendData));											
											SendDataThread sendDataThread = new SendDataThread(devSocket,sendData);
											sendDataThread.start();
										}
									}
								}
								
							}
				        }else{
							logger.debug("设备数据不存在，type："+4+" code："+devCode);
						}
				        
				        
																									
					
				}else if(source[0]==(byte)0x02){
					//报警终端
					byte dataType=data[10];
					if(dataType==(byte)0x07){
						logger.debug("数据类型:报警终端问询帧");
						//01000000 02010399 132F 07 010201A1					
						//问询帧
						//参数个数
						int pnum=1;
						//床垫设备个数
						int bednum=0;
						//查询设备编号 type=2,9,10,11,12,13
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("type_in", "2,9,10,11,12,13");
						List<Object> list = devService.getListByCondition(queryMap);
						
						if(list!=null&&list.size()>0){
							for(Object obj:list){
								DevEntity dev = (DevEntity)obj;
								if(dev!=null&&dev.getType()!=null&&"2".equals(dev.getType())){
									bednum++;
								}
							}
							pnum+=list.size();
						}
						//----封装回复帧开始 ----
						result = new byte[17+10*(pnum-1)+bednum];
						//目的终端
						result[0]=data[4];
						result[1]=data[5];
						result[2]=data[6];
						result[3]=data[7];
						//源终端
						result[4]=data[0];
						result[5]=data[1];
						result[6]=data[2];
						result[7]=data[3];
						//消息类型
						result[8]=(byte)0x08;					
						//参数个数
						result[9]=(byte)pnum;
						//系统时间0x03
						result[10] = (byte)0x03;
						byte[] time = this.timeToBytes(date);
						result[11] = time[0];
						result[12] = time[1];
						result[13] = time[2];
						result[14] = time[3];
						result[15] = time[4];
						result[16] = time[5];
						//配置信息*n 0x04
						int a=17;
						if(list!=null&&list.size()>0){
							for(int i=0;i<list.size();i++){
								DevEntity dev = (DevEntity)list.get(i);
								String devCode = dev.getCode();
								byte[] devByte = HexString2Bytes(devCode);
								result[a] = (byte)0x04;
								result[a+1] = (byte)devByte[0];
								result[a+2] = (byte)devByte[1];
								result[a+3] = (byte)devByte[2];
								result[a+4] = (byte)devByte[3];
								result[a+5] = (byte)0x00;
								result[a+6] = (byte)0x00;
								result[a+7] = (byte)0x18;
								result[a+9] = (byte)0x00;
								if(dev!=null&&dev.getType()!=null&&"13".equals(dev.getType())){
									result[a+9] = (byte)0x01;
								}else{
									result[a+9] = (byte)0x00;
								}								
								a=a+10; 
								//床垫多一位
								if(dev!=null&&dev.getType()!=null&&"2".equals(dev.getType())){
									result[a] = (byte)0x00;
									a++;
								}
							}
						}											
						//020103990100000008020303070C092904040801021E0000180000B4				
						//----封装回复帧结束 ----
						
					}else if(dataType==(byte)0x09){
						//01000000 02010399 E828 09 02 03 03070C092739 05 08 01021E 00 5F
						//应用帧					
						//参数个数
						int num = this.byteToDecimal(data[11]);
						logger.debug("参数个数："+num);
						
						//系统时间
						String time = "";
						if(data[12]==(byte)0x03){							
							byte[] dateByte = {data[13],data[14],data[15],data[16],data[17],data[18]};
							time = this.timeBytesToString(dateByte);						
							num--;
						}
						if(time==null||"".equals(time)){
							time = formater.format(date);
						}
						logger.debug("系统时间："+time);
						
						int zhizhen=19;
						//对报警参数循环解析
						while(num>0){
							int devType=0;						
							byte[] devMac = {data[zhizhen+1],data[zhizhen+2],data[zhizhen+3],data[zhizhen+4]};
							String devCode = this.printHexString(devMac);
							if(data[zhizhen+1]==(byte)0x03){
								//智能感应垫
								devType=2;	
							}else if(data[zhizhen+1]==(byte)0x05){
								//园区内定位设备7
								devType=4;	
							}else if(data[zhizhen+1]==(byte)0x08){
								//一键报警设备
								devType=9;								
							}else if(data[zhizhen+1]==(byte)0x09){
								//无线门磁终端
								devType=10;	
							}else if(data[zhizhen+1]==(byte)0x0f){
								//尿湿感应设备
								devType=11;	
							}else if(data[zhizhen+1]==(byte)0x11){
								//腕带设备
								devType=12;	
							}else if(data[zhizhen+1]==(byte)0x12){
								//红外
								devType=13;	
							} 
							//根据类型和编号查询设备是否存在
							DevEntity dev = null;
							if(devType!=0&&devCode!=null&&!"".equals(devCode)){
								Map<String, Object> queryMap = new HashMap<String, Object>();
								queryMap.put("type", devType);
								queryMap.put("code", devCode.toLowerCase());
								List<Object> list = devService.getListByCondition(queryMap);
								if(list!=null&&list.size()>0){
									dev = (DevEntity)list.get(0);
								}
							}
							if(dev!=null){	
								//获取当前AlarmCurrentEntity
								AlarmCurrentEntity alarmCurrent =null;
								Map<String, Object> queryMap = new HashMap<String, Object>();
								queryMap.put("devId", dev.getId());
								List<Object> list = alarmCurrentService.getListByCondition(queryMap);
								if(list!=null&&list.size()>0){
									alarmCurrent = (AlarmCurrentEntity)list.get(0);
								}else{
									alarmCurrent = new AlarmCurrentEntity();
								}
								AlarmHistoryEntity alarmHistory = new AlarmHistoryEntity();
								byte[] sendData= null;
								//数据报警开始							
								if(data[zhizhen]==(byte)0x05){									
									//报警数据
									if(devType==9){
										//一键报警设备
										CurrentKeyalarmEntity currentKeyalarm =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentKeyalarmService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentKeyalarm = (CurrentKeyalarmEntity)list2.get(0);
										}else{
											currentKeyalarm = new CurrentKeyalarmEntity();
										}
										HistoryKeyalarmEntity historyKeyalarm = new HistoryKeyalarmEntity();
										int temp = this.readBit(data[zhizhen+5], 0);
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【一键报警设备：主动报警】："+dev.getCode());
											//报警mordo_state_current_keyalarm,mordo_state_history_keyalarm,mordo_alarm_current,mordo_alarm_history
											currentKeyalarm.setAlarm("Y");
											currentKeyalarm.setAlarmupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											
											historyKeyalarm.setAlarm("Y");
											historyKeyalarm.setAlarmupdatetime(time);
											historyKeyalarm.setDevId(dev.getId());
											
											alarmCurrent.setCode("E041");
											alarmCurrent.setContent("一键报警主动报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E041");
											alarmHistory.setContent("一键报警主动报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentKeyalarmService.save(currentKeyalarm);
											historyKeyalarmService.save(historyKeyalarm);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x40;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==0){
											logger.debug("数据类型:报警终端应用帧：【一键报警设备：取消报警】:"+dev.getCode());
											//取消报警mordo_state_current_keyalarm,mordo_state_history_keyalarm
											currentKeyalarm.setAlarm("N");
											currentKeyalarm.setAlarmupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											
											historyKeyalarm.setAlarm("N");
											historyKeyalarm.setAlarmupdatetime(time);
											historyKeyalarm.setDevId(dev.getId());
											currentKeyalarmService.save(currentKeyalarm);
											historyKeyalarmService.save(historyKeyalarm);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
										}
									}else if(devType==12){
										//腕带报警
										//C00100000002010399EE6E0902030307110B032805110002C201F0C0
										CurrentWandaiEntity currentWandai =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentWandaiService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentWandai = (CurrentWandaiEntity)list2.get(0);
										}else{
											currentWandai = new CurrentWandaiEntity();
										}
										HistoryWandaiEntity historyWandai = new HistoryWandaiEntity();
										int temp = this.readBit(data[zhizhen+5], 0);
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【腕带呼叫器：主动报警】："+dev.getCode());
											//报警mordo_state_current_Wandai,mordo_state_history_Wandai,mordo_alarm_current,mordo_alarm_history
											currentWandai.setAlarm("Y");
											currentWandai.setAlarmupdatetime(time);
											currentWandai.setDevId(dev.getId());
											
											historyWandai.setAlarm("Y");
											historyWandai.setAlarmupdatetime(time);
											historyWandai.setDevId(dev.getId());
											
											alarmCurrent.setCode("E061");
											alarmCurrent.setContent("腕带呼叫器主动报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E061");
											alarmHistory.setContent("腕带呼叫器主动报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentWandaiService.save(currentWandai);
											historyWandaiService.save(historyWandai);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x02;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==0){
											logger.debug("数据类型:报警终端应用帧：【腕带呼叫器：取消报警】:"+dev.getCode());
											//取消报警mordo_state_current_Wandai,mordo_state_history_Wandai
											currentWandai.setAlarm("N");
											currentWandai.setAlarmupdatetime(time);
											currentWandai.setDevId(dev.getId());
											
											historyWandai.setAlarm("N");
											historyWandai.setAlarmupdatetime(time);
											historyWandai.setDevId(dev.getId());
											currentWandaiService.save(currentWandai);
											historyWandaiService.save(historyWandai);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
									    }
									}else if(devType==10){
										//门磁报警										
										CurrentDoorEntity currentDoor =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentDoorService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentDoor = (CurrentDoorEntity)list2.get(0);
										}else{
											currentDoor = new CurrentDoorEntity();
										}
										HistoryDoorEntity historyDoor = new HistoryDoorEntity();
										//bit[0]=1门关, bit[0]=0门开
										int temp = this.readBit(data[zhizhen+5], 0);
										//bit[1]=1预警
										int temp2 = this.readBit(data[zhizhen+5], 1);
										//bit[2]=1报警
										int temp3 = this.readBit(data[zhizhen+5], 2);
										String alarmType = "";
										int alarmLevel=0;
										String alarmCode="";
										if(temp2==1){
											alarmType="预警";
											alarmLevel=1;
											alarmCode="E031";
										}else if(temp3==1){
											alarmType="报警";
											alarmLevel=2;
											alarmCode="E032";
										}else if(temp==1){
											alarmType="门已关";
											alarmLevel=0;
										}
										if(temp==0){
											//门开
											logger.debug("数据类型:报警终端应用帧：【无线门磁终端："+alarmType+"】："+dev.getCode());
											//报警mordo_state_current_Door,mordo_state_history_Door,mordo_alarm_current,mordo_alarm_history
											currentDoor.setOpenclose("N");
											currentDoor.setAlarmupdatetime(time);
											currentDoor.setLevel(alarmLevel);
											currentDoor.setDevId(dev.getId());
											
											historyDoor.setOpenclose("N");
											historyDoor.setAlarmupdatetime(time);
											historyDoor.setLevel(alarmLevel);
											historyDoor.setDevId(dev.getId());
											
											
											alarmCurrent.setCode(alarmCode);
											alarmCurrent.setContent("无线门磁终端"+alarmType);
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode(alarmCode);
											alarmHistory.setContent("无线门磁终端"+alarmType);
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentDoorService.save(currentDoor);
											historyDoorService.save(historyDoor);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x08;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【无线门磁终端："+alarmType+"】:"+dev.getCode());
											//取消报警mordo_state_current_Door,mordo_state_history_Door
											currentDoor.setOpenclose("Y");
											currentDoor.setAlarmupdatetime(time);
											currentDoor.setLevel(alarmLevel);
											currentDoor.setDevId(dev.getId());
											
											historyDoor.setOpenclose("Y");
											historyDoor.setAlarmupdatetime(time);
											historyDoor.setLevel(alarmLevel);
											historyDoor.setDevId(dev.getId());
											currentDoorService.save(currentDoor);
											historyDoorService.save(historyDoor);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
									    }
									}else if(devType==2){
										//床垫报警										
										CurrentBedEntity currentBed =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentBedService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentBed = (CurrentBedEntity)list2.get(0);
										}else{
											currentBed = new CurrentBedEntity();
										}
										HistoryBedEntity historyBed = new HistoryBedEntity();
										//bit[0]=1有人, bit[0]=0无人
										int temp = this.readBit(data[zhizhen+5], 0);
										//bit[1]=1预警
										int temp2 = this.readBit(data[zhizhen+5], 1);
										//bit[2]=1报警
										int temp3 = this.readBit(data[zhizhen+5], 2);
										String alarmType = "";
										int alarmLevel=0;
										String alarmCode="";
										if(temp2==1){
											alarmType="离床预警";
											alarmLevel=1;
											alarmCode="E011";
										}else if(temp3==1){
											alarmType="离床报警";
											alarmLevel=2;
											alarmCode="E012";
										}else if(temp==1){
											alarmType="床垫有人";
											alarmLevel=0;
										}
										if(temp==0){
											//无人
											logger.debug("数据类型:报警终端应用帧：【床垫设备："+alarmType+"】："+dev.getCode());
											//报警mordo_state_current_Bed,mordo_state_history_Bed,mordo_alarm_current,mordo_alarm_history
											currentBed.setHavingbody("N");
											currentBed.setAlarmupdatetime(time);
											currentBed.setLevel(alarmLevel);
											currentBed.setDevId(dev.getId());
											
											historyBed.setHavingbody("N");
											historyBed.setAlarmupdatetime(time);
											historyBed.setLevel(alarmLevel);
											historyBed.setDevId(dev.getId());
											
											
											alarmCurrent.setCode(alarmCode);
											alarmCurrent.setContent("床垫设备"+alarmType);
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode(alarmCode);
											alarmHistory.setContent("床垫设备"+alarmType);
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentBedService.save(currentBed);
											historyBedService.save(historyBed);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x04;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==1){
											//有人
											logger.debug("数据类型:报警终端应用帧：【床垫设备："+alarmType+"】:"+dev.getCode());
											//取消报警mordo_state_current_Bed,mordo_state_history_Bed
											currentBed.setHavingbody("Y");
											currentBed.setAlarmupdatetime(time);
											currentBed.setLevel(alarmLevel);
											currentBed.setDevId(dev.getId());
											
											historyBed.setHavingbody("Y");
											historyBed.setAlarmupdatetime(time);
											historyBed.setLevel(alarmLevel);
											historyBed.setDevId(dev.getId());
											currentBedService.save(currentBed);
											historyBedService.save(historyBed);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
									    }
									}else if(devType==13){
										//红外报警
										CurrentIrEntity currentIr =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentIrService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentIr = (CurrentIrEntity)list2.get(0);
										}else{
											currentIr = new CurrentIrEntity();
										}
										HistoryIrEntity historyIr = new HistoryIrEntity();
										int temp = this.readBit(data[zhizhen+5], 0);
										if(temp==0){
											logger.debug("数据类型:报警终端应用帧：【红外：无人报警】："+dev.getCode());
											//报警mordo_state_current_Ir,mordo_state_history_Ir,mordo_alarm_current,mordo_alarm_history
											currentIr.setHavingbody("N");
											currentIr.setLevel(1);
											currentIr.setAlarmupdatetime(time);
											currentIr.setDevId(dev.getId());
											
											historyIr.setHavingbody("N");
											historyIr.setLevel(1);
											historyIr.setAlarmupdatetime(time);
											historyIr.setDevId(dev.getId());
											
											alarmCurrent.setCode("E071");
											alarmCurrent.setContent("红外设备无人报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E071");
											alarmHistory.setContent("红外设备无人报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentIrService.save(currentIr);
											historyIrService.save(historyIr);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x01;
											sendData[19]=(byte)0x00;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【红外：有人消警】:"+dev.getCode());
											//取消报警mordo_state_current_Ir,mordo_state_history_Ir
											currentIr.setHavingbody("Y");
											currentIr.setLevel(0);
											currentIr.setAlarmupdatetime(time);
											currentIr.setDevId(dev.getId());
											
											historyIr.setHavingbody("Y");
											historyIr.setLevel(0);
											historyIr.setAlarmupdatetime(time);
											historyIr.setDevId(dev.getId());
											currentIrService.save(currentIr);
											historyIrService.save(historyIr);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
									    }
									}else if(devType==11){
										//尿湿设备
										CurrentUrineEntity currentUrine =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentUrineService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentUrine = (CurrentUrineEntity)list2.get(0);
										}else{
											currentUrine = new CurrentUrineEntity();
										}
										HistoryUrineEntity historyUrine = new HistoryUrineEntity();
										int temp = this.readBit(data[zhizhen+5], 0);
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【尿湿感应设备：报警】："+dev.getCode());
											//报警mordo_state_current_Urine,mordo_state_history_Urine,mordo_alarm_current,mordo_alarm_history
											currentUrine.setAlarm("Y");
											currentUrine.setAlarmupdatetime(time);
											currentUrine.setDevId(dev.getId());
											
											historyUrine.setAlarm("Y");
											historyUrine.setAlarmupdatetime(time);
											historyUrine.setDevId(dev.getId());
											
											alarmCurrent.setCode("E051");
											alarmCurrent.setContent("尿湿感应设备报警");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E051");
											alarmHistory.setContent("尿湿感应设备报警");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentUrineService.save(currentUrine);
											historyUrineService.save(historyUrine);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
											
											//----封装发射器应用帧开始 ----
											sendData = new byte[23];											
											
											//报警类型
											sendData[17]=(byte)0x02;										
											sendData[18]=(byte)0x00;
											sendData[19]=(byte)0x80;
													
											//----封装发射器应用帧结束 ----
											
										}else if(temp==0){
											logger.debug("数据类型:报警终端应用帧：【尿湿感应设备：取消报警】:"+dev.getCode());
											//取消报警mordo_state_current_Urine,mordo_state_history_Urine
											currentUrine.setAlarm("N");
											currentUrine.setAlarmupdatetime(time);
											currentUrine.setDevId(dev.getId());
											
											historyUrine.setAlarm("N");
											historyUrine.setAlarmupdatetime(time);
											historyUrine.setDevId(dev.getId());
											currentUrineService.save(currentUrine);
											historyUrineService.save(historyUrine);
//											//----封装发射器应用帧开始 ----
//											sendData = new byte[23];											
//											
//											//报警类型
//											sendData[17]=(byte)0x02;										
//											sendData[18]=(byte)0x80;
//											sendData[19]=(byte)0x40;
//											
//											//----封装发射器应用帧结束 ----
										}
									}
								}else if(data[zhizhen]==(byte)0x06){
									//设备报警开始								
								    int temp = this.readBit(data[zhizhen+5], 0);
								    int temp2 = this.readBit(data[zhizhen+5], 1);
								    
								    
								    if(devType==9){
										//一键报警设备
										CurrentKeyalarmEntity currentKeyalarm =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentKeyalarmService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentKeyalarm = (CurrentKeyalarmEntity)list2.get(0);
										}else{
											currentKeyalarm = new CurrentKeyalarmEntity();
										}
										String devName="一键报警";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_keyalarm,mordo_alarm_current,mordo_alarm_history
											currentKeyalarm.setNormal("N");
											currentKeyalarm.setDevupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent("一键报警设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent("一键报警设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentKeyalarmService.save(currentKeyalarm);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_keyalarm
									    	currentKeyalarm.setNormal("Y");
											currentKeyalarm.setDevupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											currentKeyalarmService.save(currentKeyalarm);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_keyalarm,mordo_alarm_current,mordo_alarm_history
									    	currentKeyalarm.setPower("N");
											currentKeyalarm.setDevupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentKeyalarmService.save(currentKeyalarm);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_keyalarm
									    	currentKeyalarm.setPower("Y");
											currentKeyalarm.setDevupdatetime(time);
											currentKeyalarm.setDevId(dev.getId());
											currentKeyalarmService.save(currentKeyalarm);
									    }
								    }else if(devType==12){
								    	//腕带
										CurrentWandaiEntity currentWandai =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentWandaiService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentWandai = (CurrentWandaiEntity)list2.get(0);
										}else{
											currentWandai = new CurrentWandaiEntity();
										}
										String devName ="腕带呼叫器";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_Wandai,mordo_alarm_current,mordo_alarm_history
											currentWandai.setNormal("N");
											currentWandai.setDevupdatetime(time);
											currentWandai.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent(""+devName+"设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent(""+devName+"设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentWandaiService.save(currentWandai);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_Wandai
									    	currentWandai.setNormal("Y");
											currentWandai.setDevupdatetime(time);
											currentWandai.setDevId(dev.getId());
											currentWandaiService.save(currentWandai);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_Wandai,mordo_alarm_current,mordo_alarm_history
									    	currentWandai.setPower("N");
											currentWandai.setDevupdatetime(time);
											currentWandai.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentWandaiService.save(currentWandai);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_Wandai
									    	currentWandai.setPower("Y");
											currentWandai.setDevupdatetime(time);
											currentWandai.setDevId(dev.getId());
											currentWandaiService.save(currentWandai);
									    }
								    }else if(devType==10){
										//门磁报警
										CurrentDoorEntity currentDoor =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentDoorService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentDoor = (CurrentDoorEntity)list2.get(0);
										}else{
											currentDoor = new CurrentDoorEntity();
										}
										String devName ="无线门磁终端";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_Door,mordo_alarm_current,mordo_alarm_history
											currentDoor.setNormal("N");
											currentDoor.setDevupdatetime(time);
											currentDoor.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent(""+devName+"设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent(""+devName+"设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentDoorService.save(currentDoor);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_Door
									    	currentDoor.setNormal("Y");
											currentDoor.setDevupdatetime(time);
											currentDoor.setDevId(dev.getId());
											currentDoorService.save(currentDoor);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_Door,mordo_alarm_current,mordo_alarm_history
									    	currentDoor.setPower("N");
											currentDoor.setDevupdatetime(time);
											currentDoor.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(""+devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(""+devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentDoorService.save(currentDoor);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_Door
									    	currentDoor.setPower("Y");
											currentDoor.setDevupdatetime(time);
											currentDoor.setDevId(dev.getId());
											currentDoorService.save(currentDoor);
									    }
								    }else if(devType==2){
										//床垫设备
										CurrentBedEntity currentBed =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentBedService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentBed = (CurrentBedEntity)list2.get(0);
										}else{
											currentBed = new CurrentBedEntity();
										}
										String devName ="床垫";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_Bed,mordo_alarm_current,mordo_alarm_history
											currentBed.setNormal("N");
											currentBed.setDevupdatetime(time);
											currentBed.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent(""+devName+"设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent(""+devName+"设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentBedService.save(currentBed);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_Bed
									    	currentBed.setNormal("Y");
											currentBed.setDevupdatetime(time);
											currentBed.setDevId(dev.getId());
											currentBedService.save(currentBed);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_Bed,mordo_alarm_current,mordo_alarm_history
									    	currentBed.setPower("N");
											currentBed.setDevupdatetime(time);
											currentBed.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(""+devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(""+devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentBedService.save(currentBed);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_Bed
									    	currentBed.setPower("Y");
											currentBed.setDevupdatetime(time);
											currentBed.setDevId(dev.getId());
											currentBedService.save(currentBed);
									    }
								    }else if(devType==13){
										//红外设备
										CurrentIrEntity currentIr =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentIrService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentIr = (CurrentIrEntity)list2.get(0);
										}else{
											currentIr = new CurrentIrEntity();
										}
										String devName ="红外";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_Ir,mordo_alarm_current,mordo_alarm_history
											currentIr.setNormal("N");
											currentIr.setDevupdatetime(time);
											currentIr.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent(""+devName+"设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent(""+devName+"设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentIrService.save(currentIr);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_Ir
									    	currentIr.setNormal("Y");
											currentIr.setDevupdatetime(time);
											currentIr.setDevId(dev.getId());
											currentIrService.save(currentIr);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_Ir,mordo_alarm_current,mordo_alarm_history
									    	currentIr.setPower("N");
											currentIr.setDevupdatetime(time);
											currentIr.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(""+devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(""+devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentIrService.save(currentIr);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_Ir
									    	currentIr.setPower("Y");
											currentIr.setDevupdatetime(time);
											currentIr.setDevId(dev.getId());
											currentIrService.save(currentIr);
									    }
								    }else if(devType==11){
										//尿湿设备
										CurrentUrineEntity currentUrine =null;
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("devId", dev.getId());
										List<Object> list2 = currentUrineService.getListByCondition(map);
										if(list2!=null&&list2.size()>0){
											currentUrine = (CurrentUrineEntity)list2.get(0);
										}else{
											currentUrine = new CurrentUrineEntity();
										}
										String devName ="尿湿感应设备";
										if(temp==1){
											logger.debug("数据类型:报警终端应用帧：【"+devName+"：设备故障】:"+dev.getCode());
									    	//故障mordo_state_current_Urine,mordo_alarm_current,mordo_alarm_history
											currentUrine.setNormal("N");
											currentUrine.setDevupdatetime(time);
											currentUrine.setDevId(dev.getId());
											
											alarmCurrent.setCode("E003");
											alarmCurrent.setContent(""+devName+"设备故障");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E003");
											alarmHistory.setContent(""+devName+"设备故障");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentUrineService.save(currentUrine);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：设备正常】:"+dev.getCode());
									    	//正常mordo_state_current_Urine
									    	currentUrine.setNormal("Y");
											currentUrine.setDevupdatetime(time);
											currentUrine.setDevId(dev.getId());
											currentUrineService.save(currentUrine);
									    }
									    if(temp2==1){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：低电压】:"+dev.getCode());
									    	//低电量mordo_state_current_Urine,mordo_alarm_current,mordo_alarm_history
									    	currentUrine.setPower("N");
											currentUrine.setDevupdatetime(time);
											currentUrine.setDevId(dev.getId());
											
											alarmCurrent.setCode("E002");
											alarmCurrent.setContent(""+devName+"低电压");
											alarmCurrent.setCreatedate(time);
											alarmCurrent.setDevId(dev.getId());
											
											alarmHistory.setCode("E002");
											alarmHistory.setContent(""+devName+"低电压");
											alarmHistory.setCreatedate(time);
											alarmHistory.setDevId(dev.getId());
											
											currentUrineService.save(currentUrine);
											alarmCurrentService.save(alarmCurrent);
											alarmHistoryService.save(alarmHistory);
									    }else if(temp2==0){
									    	logger.debug("数据类型:报警终端应用帧：【"+devName+"设备：电压正常】:"+dev.getCode());
									    	//正常mordo_state_current_Urine
									    	currentUrine.setPower("Y");
											currentUrine.setDevupdatetime(time);
											currentUrine.setDevId(dev.getId());
											currentUrineService.save(currentUrine);
									    }
								    }
								}
								//向信号发射器发送数据
								if(sendData!=null&&sendData.length>0&&dev.getAlarmdevid()!=null&&!"".equals(dev.getAlarmdevid())&&dev.getAlarmcontent()!=null&&!"".equals(dev.getAlarmcontent())){
									String[] sage = dev.getAlarmdevid().split(",");
									if(sage!=null&&sage.length>0){
										for(int i=0;i<sage.length;i++){
											Socket devSocket = socketMap.get(sage[i]);
											if(devSocket!=null&&sage[i].length()==8){
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
												byte[] mac = HexString2Bytes(sage[i]);
												sendData[12]=(byte)0x01;
												sendData[13]=mac[0];
												sendData[14]=mac[1];
												sendData[15]=mac[2];
												sendData[16]=mac[3];
												
												//报警数据
												sendData[20]=(byte)0x03;	
												if(dev.getAlarmcontent()!=null&&dev.getAlarmcontent().length()==4){
													byte[] alarmData = decimalToBytes(dev.getAlarmcontent(),2);
													sendData[21]=alarmData[0];
													sendData[22]=alarmData[1];
												}
												//----封装发射器应用帧结束 ----
												logger.debug("向信号发射器发送数据，胸牌code："+sage[i]);
												sendData = dataService.dataChangeBack(sendData);
												//C0 0A000000 01000000 52EA 010301 0B0101D3 020040 03000128C0
												//C0 02010399 01000000 EAEE 0A 00 96C0
												logger.debug("向信号发射器发送数据，数据内容："+this.printHexString(sendData));											
												SendDataThread sendDataThread = new SendDataThread(devSocket,sendData);
												sendDataThread.start();
											}
										}
									}
									
								}
								
							}else{
								logger.debug("设备数据不存在，type："+devType+" code："+devCode);
							}
							zhizhen=zhizhen+6;
							num--;
						}
						
						
						
						
						
						
						
						
						//----封装回复帧开始 ----
						result = new byte[12];
						//目的终端
						result[0]=data[4];
						result[1]=data[5];
						result[2]=data[6];
						result[3]=data[7];
						//源终端
						result[4]=data[0];
						result[5]=data[1];
						result[6]=data[2];
						result[7]=data[3];
						//序列号
						result[8]=data[8];
						result[9]=data[9];
						//消息类型
						result[10]=(byte)0x0a;
						//参数个数
						result[11]=(byte)0x00;					
						//----封装回复帧结束 ----
					}
				}else{
					logger.debug("Data数据信息处理: 未知源终端 ");
				}
				
				
			}else{
				logger.debug("Data数据信息处理: 获取不到消息类型data.length： "+data.length);
			}
		} catch (Exception e) {
			logger.error(e);
		}		
		return result;
	}
	
	
	/**
	 * 按位读取bit
	 * @param content
	 * @param index 76543210
	 * @return
	 */
	public int readBit(byte content,int index){
		int result = 0;
		content = (byte) (content >> index);
		if((content&0x01)==0x01){
			result = 1;
		}
		return result;	
	}
	
	/**
	 * 按位设值bit
	 * @param content
	 * @param index 76543210
	 * @param value 0或1
	 * @return
	 */
	public byte setBit(byte content,int index,int value){
		String temp = this.byteToBit(content);
		if(value==1){
			temp=temp.substring(0,7-index)+"1"+temp.substring(8-index);
		}else if(value==0){
			temp=temp.substring(0,7-index)+"0"+temp.substring(8-index);
		}
		byte result = this.BitToByte(temp);
		return result;	
	}
	/** 
	 * Byte转Bit 
	 */  
	public  String byteToBit(byte b) {  
	    return "" +(byte)((b >> 7) & 0x1) +   
	    (byte)((b >> 6) & 0x1) +   
	    (byte)((b >> 5) & 0x1) +   
	    (byte)((b >> 4) & 0x1) +   
	    (byte)((b >> 3) & 0x1) +   
	    (byte)((b >> 2) & 0x1) +   
	    (byte)((b >> 1) & 0x1) +   
	    (byte)((b >> 0) & 0x1);  
	}  
	  
	/** 
	 * Bit转Byte 
	 */  
	public  byte BitToByte(String byteStr) {  
	    int re, len;  
	    if (null == byteStr) {  
	        return 0;  
	    }  
	    len = byteStr.length();  
	    if (len != 4 && len != 8) {  
	        return 0;  
	    }  
	    if (len == 8) {// 8 bit处理  
	        if (byteStr.charAt(0) == '0') {// 正数  
	            re = Integer.parseInt(byteStr, 2);  
	        } else {// 负数  
	            re = Integer.parseInt(byteStr, 2) - 256;  
	        }  
	    } else {//4 bit处理  
	        re = Integer.parseInt(byteStr, 2);  
	    }  
	    return (byte) re;  
	}  
	
	
	/**
	 * 将 系统时间byte转化为string
	 * @param bytes
	 * @return
	 */	
	public String timeBytesToString(byte[] bytes){
		String result = null;
		int year = bytes[0];
		int month = bytes[1];
		int day = bytes[2];
		int hour = bytes[3];
		int minute = bytes[4];
		int second = bytes[5];
		
		Date date = new Date();
		date.setYear(year+2012-1900);
		date.setMonth(month);
		date.setDate(day);
		date.setHours(hour);
		date.setMinutes(minute);
		date.setSeconds(second);
		result = formater.format(date);
		return result;
	}
	
	/**
	 * 将 date转化为系统时间byte
	 * @param bytes
	 * @return
	 */	
	public byte[] timeToBytes(Date date){
		byte[] result = new byte[6];
		int year = date.getYear()+1900-2012;
		int month = date.getMonth();
		int day = date.getDate();
		int hour = date.getHours();
		int minute = date.getMinutes();
		int second = date.getSeconds();
		result[0] = this.decimalToBytes(String.valueOf(year), 1)[0];
		result[1] = this.decimalToBytes(String.valueOf(month), 1)[0];
		result[2] = this.decimalToBytes(String.valueOf(day), 1)[0];
		result[3] = this.decimalToBytes(String.valueOf(hour), 1)[0];
		result[4] = this.decimalToBytes(String.valueOf(minute), 1)[0];
		result[5] = this.decimalToBytes(String.valueOf(second), 1)[0];
		return result;
	}
	
	/**
	 * 转换1字节byte到十进制
	 * @param bytes
	 * @return
	 */	
	public int byteToDecimal(byte data){
		byte[] a = new byte[1];
		a[0] = data;
		BigInteger bi = new BigInteger(a);
		return bi.intValue(); 
	} 
	
	
	/**
	 * 转换多字节byte数组到十进制
	 * @param bytes
	 * @return
	 */	
	public String bytesToDecimal(byte[] bytes){
		byte[] a = new byte[bytes.length+1];
		//正符号
		a[0] = (byte)0x00;
		for(int i=1;i<a.length;i++){
			a[i] = bytes[i-1];
		}
		BigInteger bi = new BigInteger(a);
		return bi.toString(); 
	} 
	
	/**
	 * 转换多字节byte数组到十进制（带符号）
	 * @param bytes
	 * @return
	 */	
	private String bytesToSignDecimal(byte[] bytes){
		BigInteger bi = new BigInteger(bytes);
		return bi.toString(); 
	} 
	
	/**
	 * 转换十进制到多字节byte数组
	 * @param decimal
	 * @param num
	 * @return
	 */	
	public static byte[] decimalToBytes(String decimal,int num){
		BigInteger bi = new BigInteger(decimal);
		byte[] temp = bi.toByteArray();
		byte[] result = new byte[num];	
		if(temp.length>=num){
			for(int i=0;i<num;i++){
				result[i] = temp[temp.length-num+i];
			}
		}else{
			for(int i=0;i<num;i++){
				if(i<num-temp.length){
					result[i] = (byte)0x00;
				}else{
					result[i] = temp[i-(num-temp.length)];
				}				
			}
		}
		return result;
	} 
	/**
	 * byte数组转十六进制string
	 * @param b
	 */	
	public static String printHexString(byte[] b) {
        String hs="";
        String stmp="";
        for (int n=0;n<b.length;n++) {
            stmp=(Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
        }
        return hs.toUpperCase();
    }
	
	private static int parse(char c) {  
	    if (c >= 'a')  
	        return (c - 'a' + 10) & 0x0f;  
	    if (c >= 'A')  
	        return (c - 'A' + 10) & 0x0f;  
	    return (c - '0') & 0x0f;  
	} 	
	// 从十六进制字符串到字节数组转换  
	public static byte[] HexString2Bytes(String hexstr) {  
	    byte[] b = new byte[hexstr.length() / 2];  
	    int j = 0;  
	    for (int i = 0; i < b.length; i++) {  
	        char c0 = hexstr.charAt(j++);  
	        char c1 = hexstr.charAt(j++);  
	        b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
	    }  
	    return b;  
	}  
    /**
     * 去掉头尾帧 进行转义0xDB 0xDC替换为0xC0，数据中的0xDB 0xDD替换为0xDB(C00100000002010399144A07010201C3C0)
     * @param data
     * @return
     */
	public byte[] dataChange(byte[] data) {		
		int num=0;
		for(int i=0;i<data.length;i++){
			//不是头尾帧
			if(i!=0&&i!=data.length-1&&i!=data.length-2){
				//如果含有转义数据num++
				if((data[i]==(byte)0xdb&&data[i+1]==(byte)0xdc)||(data[i]==(byte)0xdb&&data[i+1]==(byte)0xdd)){
					num++;
				}
			}
		}
		//转义后数据长度去掉头尾帧+转义字符数量
		byte[] tempdate = new byte[data.length-2-num];
		int a=1;
		for(int i=0;i<tempdate.length;i++){
			//不是头尾帧
			if(a!=0&&a!=data.length-1){				
				if(data[a]==(byte)0xdb&&data[a+1]==(byte)0xdc){
					tempdate[i]=(byte)0xc0;
					a++;
					a++;
				}else if(data[a]==(byte)0xdb&&data[a+1]==(byte)0xdd){
					tempdate[i]=(byte)0xdb;
					a++;
					a++;
				}else{
					tempdate[i]=data[a];	
					a++;
				}								
			}
		}
		return tempdate;
	}
	/**
     * 进行转义0xC0替换为0xDB 0xDC，数据中的0xDB替换为0xDB 0xDD 加上头尾帧  奇偶校验
     * @param data
     * @return
     */
	public byte[] dataChangeBack(byte[] data) {
		int num=0;
		for(int i=0;i<data.length;i++){			
				//如果含有转义数据num++
				if(data[i]==(byte)0xc0||data[i]==(byte)0xdb){
					num++;
				}
			
		}
		//头尾帧+转义字符数量+奇偶校验位
		byte[] tempdate = new byte[data.length+2+num+1];
		int a=1;
	    tempdate[0]=(byte)0xc0;
	    tempdate[data.length+2+num]=(byte)0xc0;		
	    //转义
		for(int i=0;i<data.length;i++){					
				if(data[i]==(byte)0xc0){
					tempdate[a]=(byte)0xdb;
					tempdate[a+1]=(byte)0xdc;
					a++;
					a++;
				}else if(data[i]==(byte)0xdb){
					tempdate[a]=(byte)0xdb;
					tempdate[a+1]=(byte)0xdd;
					a++;
					a++;
				}else{
					tempdate[a]=data[i];
					a++;
				}						
		}
		//奇偶校验
		byte[] checkbyte = new byte[1];  //校验位
		for(int i=1;i<tempdate.length-2;i++){		
			checkbyte[0] = (byte) (checkbyte[0] ^ tempdate[i]);
		}	
		tempdate[data.length+1+num] = checkbyte[0];
		return tempdate;
	}
	/**
	 * 请求帧奇偶校验
	 * @param data
	 * @return
	 */
	public boolean checkbyte(byte[] data) {
		boolean result = false;
		//奇偶校验
		byte[] checkbyte = new byte[1];  //校验位
		for(int i=1;i<data.length-2;i++){		
			checkbyte[0] = (byte) (checkbyte[0] ^ data[i]);
		}	
		if(data[data.length-2]==checkbyte[0]){
			result = true;
		}
		return result;
	}
	

	
	public static void main(String[] sage){
		String a="C0010000000500046908BF090151060003A99AB1C0";
//		String a="C0010000000A00010D002905002BC0";
//		String a="C00100000002010399132F07010201A1C0";
			
		DataService dataService = DataService.getInstance();
		byte[] data= dataService.HexString2Bytes(a);
		//判断帧头帧尾0xc0
    	  if(data[0]==(byte)0xc0&&data[data.length-1]==(byte)0xc0){
    		 //1.奇偶校验
    		 boolean checkbyte = dataService.checkbyte(data);
    		 if(checkbyte){
    			//2.进行转义0xDB 0xDC替换为0xC0，数据中的0xDB 0xDD替换为0xDB
	    		 data = dataService.dataChange(data);	  	    
	    		 //3.数据处理
	    		 byte[] rep = dataService.dataHandler(data,null);
	    		 //4.进行转义0xC0替换为0xDB 0xDC，数据中的0xDB替换为0xDB 0xDD 加上奇偶校验位 并加上帧头尾
	    		 rep = dataService.dataChangeBack(rep);
	    		 logger.debug("Data数据信息监听: 返回数据长度byte: " + rep.length);
	  	    	 logger.debug("Data数据信息监听: 返回数据内容十六进制: " + dataService.printHexString(rep));

    		 }	  	    		 	  	    		 
    	  }else{
    		logger.debug("Data数据信息监听: 帧头帧尾不符合格式！ ");
    	  }
		
		
//		byte a= (byte) 0xef;
//		DataService dataService = DataService.getInstance();
//		a= dataService.setBit(a, 0, 0);
		
	}
   
	

	

}
