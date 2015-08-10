package fly.init;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import fly.socket.DataTcpListener;

/**
 * @description 系统启动类
 * @author feng.gu
 * @date 2012-2-6
 */
public class SystemInit implements ServletContextListener{	
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
	}

}
