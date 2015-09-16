package fly.socket;

import org.apache.log4j.Logger;


/**
 *
 * @author Administrator
 *
 */
public class ReadLocationThread extends Thread{
	private static Logger logger = Logger.getLogger(ReadLocationThread.class);
	
	/**
	 * 定时读取远程服务器上的version(小时)
	 */
	private final static int time=1;
	
	private String ip;
	
	
	
	@Override
	public void run() {
		while(true){			
			try {
				
				Thread.sleep(time*3*1000);
			} catch (Exception e) {
				logger.error(e);
			}
		}		
	}
	
	
}
