package fly.socket;

import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import fly.entity.vo.SocketDataVO;
import fly.service.DataService;
/**
 * 发送数据到信号发射器
 * @author Administrator
 *
 */
public class SendDataThread extends Thread
{
  private static Logger logger = Logger.getLogger(SendDataThread.class);
  public static LinkedList<SocketDataVO> lList = new LinkedList<SocketDataVO>();  
 

  public void run(){
    try{
    	logger.debug("发送数据到信号发射器线程启动！");
    	while (true) {
    		if(lList.size()>0){
    			SocketDataVO sd = lList.getFirst();
        		if(sd!=null&&sd.getAlarmdevid()!=null&&!"".equals(sd.getAlarmdevid())&&sd.getData()!=null){
        					Socket devSocket = DataService.socketMap.get(sd.getAlarmdevid());
        					if(devSocket!=null&&sd.getAlarmdevid().length()==8){
        						//C0 0A000000 01000000 1ACE 01 03 01 0B0101D3 02 0002 03 000A0DC0
        						//C0 0A000000 01000000 8D760103010B0101D302000403000C22C0
        						logger.debug("向信号发射器【"+DataService.printHexString(sd.getData()).substring(2,10)+"】发送数据:"+DataService.printHexString(sd.getData())+"【信号发射器发送序列号："+DataService.printHexString(sd.getData()).substring(18,22)+"】");
        						OutputStream out = devSocket.getOutputStream();
        		            	out.write(sd.getData());
        		            	lList.removeFirst();    		            	
        					}else{
        						//如果信号发射器连接还未来 则将此消息排到最后 以免卡住已经连接的发射器
        						lList.removeFirst();
        						lList.add(sd);
        					}   				    				
        		} 
    		}   		
    		Thread.sleep(1000);
    	}   	    	
    }catch (Exception e) {
   		logger.debug("发送数据到信号发射器:数据处理出错！");
   		logger.error(e);
    }
  }
}