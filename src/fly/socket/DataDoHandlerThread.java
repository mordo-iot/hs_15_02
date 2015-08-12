package fly.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import fly.service.DataService;

public class DataDoHandlerThread extends Thread
{
  private static Logger logger = Logger.getLogger(DataDoHandlerThread.class);
  static final int BUFFER_SIZE = 4096;
  private Socket socket;

  private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
  
  private DataService dataService = DataService.getInstance();

  public DataDoHandlerThread(Socket socket)
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
			OutputStream out = socket.getOutputStream();
	  		
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
	  	      if(data.length>2){
	  	    	  logger.debug("Data数据信息监听: 请求数据长度byte: " + data.length);
	  	    	  logger.debug("Data数据信息监听: 请求数据内容十六进制: " + dataService.printHexString(data));
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
		  	    		 out.write(rep);
	  	    		 }else{
	  	    			logger.debug("Data数据信息监听: 奇偶校验不正确！ ");
	  	    		 }	  	    		 	  	    		 
	  	    	  }else{
	  	    		logger.debug("Data数据信息监听: 帧头帧尾不符合格式！ ");
	  	    	  }
	  	    	  
	  	      }	  	      
           }
    }catch (Exception e) {
   		logger.debug("Data信息监听:数据处理出错！");
   		logger.error(e);
   	   }finally{
   		try {
   			if(socket!=null){
   				socket.close();
   			}
   		} catch (IOException e) {
   			logger.error(e);
   		}
   		logger.debug("Data信息监听:数据处理完毕,链接断开！");	
        }
  }
}