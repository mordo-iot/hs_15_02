package fly.socket;

import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;
/**
 * 发送数据到信号发射器
 * @author Administrator
 *
 */
public class SendDataThread extends Thread
{
  private static Logger logger = Logger.getLogger(SendDataThread.class);
  private Socket socket;
  private byte[] data;
 
  public SendDataThread(Socket socket,byte[] data)
  {
    this.socket = socket;
    this.data = data;
  }

  public synchronized void run(){
    try{
    	OutputStream out = socket.getOutputStream();
    	out.write(data);
    	Thread.sleep(1000);
    }catch (Exception e) {
   		logger.debug("发送数据到信号发射器:数据处理出错！");
   		logger.error(e);
    }
  }
}