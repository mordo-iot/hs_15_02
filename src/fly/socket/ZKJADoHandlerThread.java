package fly.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import fly.handler.ZKJATCPMessageHandler;

public class ZKJADoHandlerThread extends Thread
{
  private static Logger logger = Logger.getLogger(ZKJADoHandlerThread.class);
  static final int BUFFER_SIZE = 4096;
  private Socket socket;
  private static ZKJATCPMessageHandler handler = new ZKJATCPMessageHandler();

  private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");

  public ZKJADoHandlerThread(Socket socket)
  {
    this.socket = socket;
  }

  public void run(){
    try{
      while(!socket.isClosed()){
    	  try {
	       		Thread.sleep(1000);//等待时间		       		
		   	} catch (InterruptedException e1) {
		       		e1.printStackTrace();
		   	}
		   
			InputStream in = socket.getInputStream();
	  		
	  		 byte[] reciveBytes = new byte[0];

	  	      int offset = 0;

	  	      int len = 0;
	  	      byte[] buffer = new byte[4096];
	  	      while (len != -1)
	  	      {
	  	        try
	  	        {
	  	          len = in.read(buffer, 0, 4096);
	  	        }
	  	        catch (SocketTimeoutException e) {
	  	          break;
	  	        }
	  	        if (len != -1)
	  	        {
	  	          reciveBytes = ArrayUtils.addAll(reciveBytes, buffer);
	  	          offset += len;
	  	        }

	  	        byte[] reqbyte = ArrayUtils.subarray(buffer, 0, len);
	  	        if (reqbyte.length >= 0)
	  	        {
	  	          break;
	  	        }
	  	      }
	  	      byte[] data = ArrayUtils.subarray(reciveBytes, 0, offset);
	  	      if(data.length>0){
	  	    	 logger.debug("ZKJA数据信息监听: 数据长度byte: " + data.length);
		  	      String bytestr = new String(data);
		  	      logger.debug("ZKJA数据信息监听: 数据内容: " + bytestr);
		  	    String responsestr = handler.hanlder(bytestr);  
	  	      }
	  	     

	  	      
           }
    }catch (Exception e) {
   		logger.debug("中科健安信息监听:数据处理出错！");
   		logger.error(e);
   	   }finally{
   		try {
   			if(socket!=null){
   				socket.close();
   			}
   		} catch (IOException e) {
   			logger.error(e);
   		}
   		logger.debug("中科健安信息监听:数据处理完毕！");	
        }
  }
}