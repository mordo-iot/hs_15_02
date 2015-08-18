package fly.init;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import fly.socket.DataTcpListener;
import fly.socket.SendHeartDataThread;
import fly.socket.ZKJATCPSListener;
import fly.util.FileUtil;

/**
 * @description 系统启动类
 * @author feng.gu
 * @date 2012-2-6
 */
public class SystemInit implements ServletContextListener{	
	private static Logger logger = Logger.getLogger(SystemInit.class);
	public static String gpskey = "";
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
		SendHeartDataThread sendHeartDataThread = new SendHeartDataThread();
		sendHeartDataThread.start();
		readConfig();
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
	      logger.info("gpskey:" + gpskey);

	    }
	    catch (Exception e) {
	      logger.error(e.toString());
	    }
	  }

}
