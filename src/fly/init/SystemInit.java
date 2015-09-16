package fly.init;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.currentTizhengBed.CurrentTizhengBedEntity;
import fly.entity.dev.DevEntity;
import fly.handler.TZCDTCPMessageHandler;
import fly.service.DataService;
import fly.service.currentLocation.CurrentLocationService;
import fly.service.currentTizhengBed.CurrentTizhengBedService;
import fly.service.dev.DevService;
import fly.socket.DataTcpListener;
import fly.socket.ReadLocationThread;
import fly.socket.SendHeartDataThread;
import fly.socket.TZCDTCPSListener;
import fly.socket.ZKJATCPSListener;
import fly.util.FileUtil;

/**
 * @description 系统启动类
 * @author feng.gu
 * @date 2012-2-6
 */
public class SystemInit implements ServletContextListener{	
	private static Logger logger = Logger.getLogger(SystemInit.class);
	private  CurrentLocationService currentLocationService=CurrentLocationService.getInstance();
	private  CurrentTizhengBedService currentTizhengBedService=CurrentTizhengBedService.getInstance();
	private DevService devService=DevService.getInstance();
	public static String gpskey = "";
	public static int ap_location_update_time = 0;
	public static int ap_location_remove = 0;
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 启动初始化
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {	
		DataTcpListener dataTcpListener = new DataTcpListener();
		dataTcpListener.start();
		ZKJATCPSListener zkjaTCPSListener= new ZKJATCPSListener();
		zkjaTCPSListener.start();
		TZCDTCPSListener tzcdTCPSListener= new TZCDTCPSListener();
		tzcdTCPSListener.start();
		SendHeartDataThread sendHeartDataThread = new SendHeartDataThread();
		ReadLocationThread readLocationThread = new ReadLocationThread();
		readLocationThread.start();
		sendHeartDataThread.start();
		readConfig();
		readStatus();
		readBedStatus();
		
	}
	
	private void readBedStatus() {
		try {
		     //读取CurrentTizhengBedEntity为type为17 体征床垫
			Map<String, Object> queryMap1 = new HashMap<String, Object>();
			queryMap1.put("type", "17");			
			List<Object> list = devService.getListByCondition(queryMap1);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					DevEntity dev = (DevEntity)list.get(i);
					if(dev!=null&&dev.getCode()!=null){
						Map<String, Object> queryMap2 = new HashMap<String, Object>();
						queryMap2.put("devId", String.valueOf(dev.getId()));						
						List<Object> list2 = currentTizhengBedService.getListByCondition(queryMap2);
						if(list2!=null&&list2.size()>0){
							CurrentTizhengBedEntity currentTizhengBed = (CurrentTizhengBedEntity)list2.get(0);
							if("N".equals(currentTizhengBed.getHavingbody())){
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_1", 1);
							}else{
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_1", 0);
							}
							if(currentTizhengBed.getHeart()!=null&&currentTizhengBed.getHeart()>0){
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_2", currentTizhengBed.getHeart());
							}else{
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_2", 0);
							}
							if(currentTizhengBed.getBreath()!=null&&currentTizhengBed.getBreath()>0){
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_3", currentTizhengBed.getBreath());
							}else{
								TZCDTCPMessageHandler.statusMap.put(dev.getCode().toLowerCase()+"_3", 0);
							}
						}
					}
				}
			}

		    }
		    catch (Exception e) {
		      logger.error(e.toString());
		    }
	}
	
	private void readStatus() {
		try {
		     //读取CurrentLocationEntity为type为4 园区一卡通
			Map<String, Object> queryMap1 = new HashMap<String, Object>();
			queryMap1.put("type", "4");			
			List<Object> list = devService.getListByCondition(queryMap1);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					DevEntity dev = (DevEntity)list.get(i);
					if(dev!=null&&dev.getCode()!=null){
						Map<String, Object> queryMap2 = new HashMap<String, Object>();
						queryMap2.put("devId", String.valueOf(dev.getId()));						
						List<Object> list2 = currentLocationService.getListByCondition(queryMap2);
						if(list2!=null&&list2.size()>0){
							CurrentLocationEntity currentLocation = (CurrentLocationEntity)list2.get(0);
							if("N".equals(currentLocation.getBodystate())){
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_1", 1);
							}else{
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_1", 0);
							}
							if("N".equals(currentLocation.getManualalarm())){
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_2", 1);
							}else{
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_2", 0);
							}
							if("N".equals(currentLocation.getPower())){
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_3", 1);
							}else{
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_3", 0);
							}
							if("N".equals(currentLocation.getMoving())){
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_4", 1);
							}else{
								DataService.statusMap.put(dev.getCode().toLowerCase()+"_4", 0);
							}
						}
					}
				}
			}

		    }
		    catch (Exception e) {
		      logger.error(e.toString());
		    }
	}

	private void readConfig()
	  {
	    try {
	      String path = SystemInit.class.getResource("SystemInit.class").toString();
	      String separator = FileUtil.getFileSeparator();
	      String projectName = FileUtil.getProjectName();
	      if ("file".equals(path.substring(0, 4))) {
	        if ("\\".equals(separator))
	          path = path.substring(6);
	        else {
	          path = path.substring(5);
	        }
	      }
	      String localPath = path.substring(0, path.indexOf("webapps")) + "webapps" + separator + projectName + separator + "WEB-INF" + separator + "conf" + separator + "config.properties";
	      localPath = URLDecoder.decode(localPath);
	      localPath = localPath.replace("/", separator);
	      localPath = localPath.replace("\\", separator);

	      Properties localProperties = FileUtil.readProperties(localPath);
	      
	      gpskey = (String)localProperties.get("gps.key");
	      ap_location_update_time = Integer.valueOf((String)localProperties.get("ap.location.update.time"));
	      ap_location_remove = Integer.valueOf((String)localProperties.get("ap.location.remove"));
	      logger.info("gpskey:" + gpskey);
	      logger.info("ap_location_update_time:" + ap_location_update_time);
	      logger.info("ap_location_remove:" + ap_location_remove);

	    }
	    catch (Exception e) {
	      logger.error(e.toString());
	    }
	  }

}
