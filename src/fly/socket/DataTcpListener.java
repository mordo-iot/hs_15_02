package fly.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DataTcpListener extends Thread
{
  private static Logger logger = Logger.getLogger(DataTcpListener.class);
  public static final int SERVERPORT = 3021;
  private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  public void run() {
    try {
      logger.debug("福利院信息监听：开始监听...");
      ServerSocket serverSocket = new ServerSocket(SERVERPORT);
      while (true) {
        Socket client = serverSocket.accept();
        Date datestart = new Date();
        logger.debug(this.formater1.format(datestart) + "福利院信息监听：端口监听到数据:");
        try
        {
          DataDoHandlerThread dataDoHandlerThread = new DataDoHandlerThread(client);
          dataDoHandlerThread.start();
        } catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			if(client!=null){
				client.close();
				client=null;
			}
		} 
      }

    }
    catch (Exception e)
    {
      logger.error(e.toString());
      e.printStackTrace();
    }
  }
}