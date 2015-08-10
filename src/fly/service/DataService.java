package fly.service;

import org.apache.log4j.Logger;

import com.framework.system.db.manager.DBManager;

public class DataService {
	
	private static Logger logger = Logger.getLogger(DataService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static DataService dataService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static DataService getInstance() {
		if (dataService == null) {
			dataService = new DataService();
		}
		return dataService;
	}

}
