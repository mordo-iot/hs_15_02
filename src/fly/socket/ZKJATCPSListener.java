package fly.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class ZKJATCPSListener extends Thread
{
  private static Logger logger = Logger.getLogger(ZKJATCPSListener.class);
  public static final int SERVERPORT = 3020;
  private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public void run() {
    try {
      logger.debug("中科健安数据信息监听：开始监听...");
      ServerSocket serverSocket = new ServerSocket(SERVERPORT);
      while (true) {
        Socket client = serverSocket.accept();
        Date datestart = new Date();
        logger.debug(this.formater1.format(datestart) + "中科健安数据信息监听：端口监听到数据:");
        try
        {
          ZKJADoHandlerThread zkjaDoHandlerThread = new ZKJADoHandlerThread(client);
          zkjaDoHandlerThread.start();
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