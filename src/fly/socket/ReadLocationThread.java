package fly.socket;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import fly.init.SystemInit;
import fly.service.DataService;


/**
 *
 * @author Administrator
 *
 */
public class ReadLocationThread extends Thread{
	private static Logger logger = Logger.getLogger(ReadLocationThread.class);
	
	
	
	
	@Override
	public void run() {
		logger.debug("信号衰减进程：开始启动...");
		while(true){			
			try {
				//1.遍历时间如果超过配置时间 则移除power
				Iterator<Entry<String, Long>> entries =DataService.timeApMap.entrySet().iterator();
				Date date = new Date();
				long nowTime = date.getTime();
				while (entries.hasNext()) {  					  
				    Entry<String, Long> entry = (Entry<String, Long>) entries.next();  	
				    String key = entry.getKey();
				    Long lastTime = entry.getValue(); 
				    if(nowTime-lastTime>SystemInit.ap_location_update_time*SystemInit.ap_location_remove*1000){
				    	//超时移除
				    	DataService.timeApMap.remove(key);
				    	DataService.powerApMap.remove(key);
				    	String[] sage = key.split("_");
				    	if(sage!=null&&sage.length==2){
				    		DataService.apMap.remove(sage[0]);
				    		DataService.lastApMap.remove(sage[0]);
				    	}
				    }else{
				    	//2.若未超过配置时间 则做衰减操作
				    	Integer power = DataService.powerApMap.get(key);				    	
				    	if(power!=null){
				    		int newPower = (int) (power*0.5);
				    		DataService.powerApMap.put(key, newPower);
				    	}
				    }			  				  
				} 							
				Thread.sleep(SystemInit.ap_location_update_time*1000);
			} catch (Exception e) {
				logger.error(e);
			}
		}		
	}
	
	
}
