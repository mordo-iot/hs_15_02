package fly.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;

import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.currentKeyalarm.CurrentKeyalarmEntity;
import fly.entity.dev.DevEntity;
import fly.entity.historyKeyalarm.HistoryKeyalarmEntity;
import fly.service.alarmCurrent.AlarmCurrentService;
import fly.service.alarmHistory.AlarmHistoryService;
import fly.service.currentKeyalarm.CurrentKeyalarmService;
import fly.service.dev.DevService;
import fly.service.historyKeyalarm.HistoryKeyalarmService;

public class DataService {	
	private static Logger logger = Logger.getLogger(DataService.class);
	private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
	private DBManager dbManager = DBManager.getInstance();

	private static DataService dataService;
	
	private static DevService devService=DevService.getInstance();
	private static AlarmCurrentService alarmCurrentService=AlarmCurrentService.getInstance();
	private static AlarmHistoryService alarmHistoryService=AlarmHistoryService.getInstance();
	private static CurrentKeyalarmService currentKeyalarmService=CurrentKeyalarmService.getInstance();
	private static HistoryKeyalarmService historyKeyalarmService=HistoryKeyalarmService.getInstance();
	

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

	public byte[] dataHandler(byte[] data){
		byte[] result = null;		
		try {
			if(data.length>11){
				Date date = new Date();
				//检查源是否是信号发射器
				byte dataType=data[10];
				if(dataType==(byte)0x07){
					//01000000 02010399 132F 07 010201A1					
					//问询帧
					//----封装回复帧开始 ----
					result = new byte[27];
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
					result[9]=(byte)0x02;
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
					result[17] = (byte)0x04;
					result[18] = (byte)0x08;
					result[19] = (byte)0x01;
					result[20] = (byte)0x02;
					result[21] = (byte)0x1e;
					result[22] = (byte)0x00;
					result[23] = (byte)0x00;
					result[24] = (byte)0x18;
					result[25] = (byte)0x00;
					result[26] = (byte)0x00;
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
						if(data[zhizhen+1]==(byte)0x08){
							//一键报警设备
							devType=9;
						}
						//根据类型和编号查询设备是否存在
						DevEntity dev = null;
						if(devType!=0&&devCode!=null&&!"".equals(devCode)){
							Map<String, Object> queryMap = new HashMap<String, Object>();
							queryMap.put("type", devType);
							queryMap.put("code", devCode.toLowerCase());
							PageList pagelist = devService.getListByCondition(queryMap, 1, 1);
							if(pagelist!=null&&pagelist.getResultList()!=null&&pagelist.getResultList().size()>0){
								dev = (DevEntity)pagelist.getResultList().get(0);
							}
						}
						if(dev!=null){	
							//获取当前AlarmCurrentEntity
							AlarmCurrentEntity alarmCurrent =null;
							Map<String, Object> queryMap = new HashMap<String, Object>();
							queryMap.put("devId", dev.getId());
							PageList pagelist = alarmCurrentService.getListByCondition(queryMap, 1, 1);
							if(pagelist!=null&&pagelist.getResultList()!=null&&pagelist.getResultList().size()>0){
								alarmCurrent = (AlarmCurrentEntity)pagelist.getResultList().get(0);
							}else{
								alarmCurrent = new AlarmCurrentEntity();
							}
							AlarmHistoryEntity alarmHistory = new AlarmHistoryEntity();
							//数据报警开始							
							if(data[zhizhen]==(byte)0x05){
								
								//报警数据
								if(devType==9){
									//一键报警设备
									CurrentKeyalarmEntity currentKeyalarm =null;
									Map<String, Object> map = new HashMap<String, Object>();
									map.put("devId", dev.getId());
									PageList pagelist2 = currentKeyalarmService.getListByCondition(map, 1, 1);
									if(pagelist2!=null&&pagelist2.getResultList()!=null&&pagelist2.getResultList().size()>0){
										currentKeyalarm = (CurrentKeyalarmEntity)pagelist2.getResultList().get(0);
									}else{
										currentKeyalarm = new CurrentKeyalarmEntity();
									}
									HistoryKeyalarmEntity historyKeyalarm = new HistoryKeyalarmEntity();
									int temp = this.readBit(data[zhizhen+5], 0);
									if(temp==1){
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
										
									}else if(temp==0){
										//取消报警mordo_state_current_keyalarm,mordo_state_history_keyalarm
										currentKeyalarm.setAlarm("N");
										currentKeyalarm.setAlarmupdatetime(time);
										currentKeyalarm.setDevId(dev.getId());
										
										historyKeyalarm.setAlarm("N");
										historyKeyalarm.setAlarmupdatetime(time);
										historyKeyalarm.setDevId(dev.getId());
										currentKeyalarmService.save(currentKeyalarm);
										historyKeyalarmService.save(historyKeyalarm);
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
									PageList pagelist2 = alarmCurrentService.getListByCondition(map, 1, 1);
									if(pagelist2!=null&&pagelist2.getResultList()!=null&&pagelist2.getResultList().size()>0){
										currentKeyalarm = (CurrentKeyalarmEntity)pagelist2.getResultList().get(0);
									}else{
										currentKeyalarm = new CurrentKeyalarmEntity();
									}
									HistoryKeyalarmEntity historyKeyalarm = new HistoryKeyalarmEntity();
									
									if(temp==1){
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
								    	//正常mordo_state_current_keyalarm
								    	currentKeyalarm.setNormal("Y");
										currentKeyalarm.setDevupdatetime(time);
										currentKeyalarm.setDevId(dev.getId());
										currentKeyalarmService.save(currentKeyalarm);
								    }
								    if(temp2==1){
								    	//低电量mordo_state_current_keyalarm,mordo_alarm_current,mordo_alarm_history
								    	currentKeyalarm.setPower("N");
										currentKeyalarm.setDevupdatetime(time);
										currentKeyalarm.setDevId(dev.getId());
										
										alarmCurrent.setCode("E002");
										alarmCurrent.setContent("一键报警低电压");
										alarmCurrent.setCreatedate(time);
										alarmCurrent.setDevId(dev.getId());
										
										alarmHistory.setCode("E002");
										alarmHistory.setContent("一键报警低电压");
										alarmHistory.setCreatedate(time);
										alarmHistory.setDevId(dev.getId());
										
										currentKeyalarmService.save(currentKeyalarm);
										alarmCurrentService.save(alarmCurrent);
										alarmHistoryService.save(alarmHistory);
								    }else if(temp2==0){
								    	//正常mordo_state_current_keyalarm
								    	currentKeyalarm.setPower("Y");
										currentKeyalarm.setDevupdatetime(time);
										currentKeyalarm.setDevId(dev.getId());
										currentKeyalarmService.save(currentKeyalarm);
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
	public byte[] decimalToBytes(String decimal,int num){
		BigInteger bi = new BigInteger(decimal);
		byte[] temp = bi.toByteArray();
		byte[] result = new byte[num];	
		if(temp.length>=num){
			for(int i=0;i<num;i++){
				result[i] = temp[temp.length-num+i];
			}
		}else{
			for(int i=0;i<num;i++){
				if(i<temp.length-num){
					result[i] = (byte)0x00;
				}else{
					result[i] = temp[i-(temp.length-num)];
				}				
			}
		}
		return result;
	} 
	/**
	 * byte数组转十六进制string
	 * @param b
	 */	
	public String printHexString(byte[] b) {
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
		String a="C00100000002010399E98B09020303070D0C1A18050801021E00E5C0";
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
	    		 byte[] rep = dataService.dataHandler(data);
	    		 //4.进行转义0xC0替换为0xDB 0xDC，数据中的0xDB替换为0xDB 0xDD 加上奇偶校验位 并加上帧头尾
	    		 rep = dataService.dataChangeBack(rep);
	    		 logger.debug("Data数据信息监听: 返回数据长度byte: " + rep.length);
	  	    	 logger.debug("Data数据信息监听: 返回数据内容十六进制: " + dataService.printHexString(rep));

    		 }	  	    		 	  	    		 
    	  }else{
    		logger.debug("Data数据信息监听: 帧头帧尾不符合格式！ ");
    	  }
	}
   
	

	

}
