package fly.socket;

import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import fly.service.DataService;


/**
 * 定时向发射器发送心跳数据
 * @author Administrator
 *
 */
public class SendHeartDataThread extends Thread{
	private static Logger logger = Logger.getLogger(SendHeartDataThread.class);
	
	private DataService dataService = DataService.getInstance();
	
	/**
	 * 定时向发射器发送心跳数据（秒）
	 */
	private final static int time=1800;
	
	@Override
	public void run() {
		logger.debug("发射器心跳定时发送：线程启动，定时间隔："+time+"秒");
		while(true){						
			Map<String,Socket> socketMap = DataService.socketMap;
			if(socketMap!=null&&socketMap.entrySet()!=null){
				Iterator<Entry<String, Socket>> entries = socketMap.entrySet().iterator();  
				while (entries.hasNext()) {  					  
				    Entry<String, Socket> entry = (Entry<String, Socket>) entries.next();  				  
				    Socket socket = (Socket)entry.getValue(); 
				    if(socket!=null){
				    	byte[] sendData = new byte[13];
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
						sendData[11]=(byte)0x01;
						//心跳数据
						sendData[12]=(byte)0x04;
				    	sendData = dataService.dataChangeBack(sendData);
				    	logger.debug("向信号发射器发送【心跳】数据，数据内容："+DataService.printHexString(sendData));											
						SendDataThread sendDataThread = new SendDataThread(socket,sendData);
						sendDataThread.start();
				    }				  				  
				} 
			}
			try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}		
	}
}
