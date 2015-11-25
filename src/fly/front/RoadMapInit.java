package fly.front;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import fly.init.SystemInit;

import roadinfo.api.RoadGenerator;

public class RoadMapInit implements ServletContextListener {

	private static Logger logger = Logger.getLogger(SystemInit.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("init road map");
		RoadGenerator.init();
	}

}
