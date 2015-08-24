package fly.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class TZCDTCPSListener extends Thread
{
  private static Logger logger = Logger.getLogger(TZCDTCPSListener.class);
  public static final int SERVERPORT = 5858;
  private SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public void run() {
    try {
      logger.debug("体征床垫数据信息监听：开始监听...");
      ServerSocket serverSocket = new ServerSocket(SERVERPORT);
      while (true) {
        Socket client = serverSocket.accept();
        Date datestart = new Date();
        logger.debug(this.formater1.format(datestart) + "体征床垫数据信息监听：端口监听到数据:");
        try
        {
          TZCDDoHandlerThread tzcdDoHandlerThread = new TZCDDoHandlerThread(client);
          tzcdDoHandlerThread.start();
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