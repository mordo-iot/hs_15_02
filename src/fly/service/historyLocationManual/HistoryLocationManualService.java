package fly.service.historyLocationManual;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.historyLocationManual.HistoryLocationManualEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 定位器手动报警历史状态服务类
 * @author feng.gu
 * @date 2015-08-10 10:18:07
 * @version V1.0
 * 
 */
public class HistoryLocationManualService {
	private static Logger logger = Logger
			.getLogger(HistoryLocationManualService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static HistoryLocationManualService historyLocationManualService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static HistoryLocationManualService getInstance() {
		if (historyLocationManualService == null) {
			historyLocationManualService = new HistoryLocationManualService();
		}
		return historyLocationManualService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(HistoryLocationManualEntity historyLocationManual) {
		boolean result = false;
		if (historyLocationManual != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(historyLocationManual);
				// 关联信息保存
				if (historyLocationManual.getDev() != null) {
					dbManager.saveNoTransaction(historyLocationManual.getDev());
				}
				tx.commitAndClose();
			} catch (Exception e) {
				logger.error("数据库提交失败！");
				logger.error(e);
				result = false;
				try {
					tx.rollbackAndClose();
				} catch (Exception ex) {
					logger.error("数据库回滚失败！");
					logger.error(ex);
				}
			}
		}
		return result;
	}

	/**
	 * 批量保存记录
	 * 
	 * @param list
	 */
	public boolean saveList(
			List<HistoryLocationManualEntity> historyLocationManualList) {
		boolean result = false;
		if (historyLocationManualList != null
				&& historyLocationManualList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (HistoryLocationManualEntity historyLocationManual : historyLocationManualList) {
					if (historyLocationManual != null) {
						dbManager.saveNoTransaction(historyLocationManual);
						// 关联信息保存
						if (historyLocationManual.getDev() != null) {
							dbManager.saveNoTransaction(historyLocationManual
									.getDev());
						}
					}
				}
				tx.commitAndClose();
			} catch (Exception e) {
				logger.error("数据库提交失败！");
				logger.error(e);
				result = false;
				try {
					tx.rollbackAndClose();
				} catch (Exception ex) {
					logger.error("数据库回滚失败！");
					logger.error(ex);
				}
			}
		}
		return result;
	}

	/**
	 * 根据id读取记录
	 * 
	 * @param id
	 *            主键
	 * @param devShow
	 *            是否查询关联信息
	 * @param obj
	 */
	public HistoryLocationManualEntity getById(Integer id, Boolean devShow) {
		HistoryLocationManualEntity obj = null;
		if (id != null) {
			obj = (HistoryLocationManualEntity) dbManager.getById(id,
					HistoryLocationManualEntity.class);
			// 查询关联内容
			if (devShow != null && devShow.booleanValue() && obj != null
					&& obj.getDevId() > 0) {
				DevEntity dev = (DevEntity) dbManager.getById(obj.getDevId(),
						DevEntity.class);
				obj.setDev(dev);
			}

		}
		return obj;
	}

	/**
	 * 根据id读取记录集合
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	public PageList getListByCondition(Map<String, Object> queryMap,
			int pageno, int pagesize) {
		PageList pagelist = null;
		if (queryMap == null) {
			queryMap = new HashMap<String, Object>();
		}
		Object id = queryMap.get("id");
		Object id_gt = queryMap.get("id_gt");
		Object id_ge = queryMap.get("id_ge");
		Object id_lt = queryMap.get("id_lt");
		Object id_le = queryMap.get("id_le");
		Object id_in = queryMap.get("id_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object bodyupdatetime = queryMap.get("bodyupdatetime");
		Object bodyupdatetime_like = queryMap.get("bodyupdatetime_like");
		Object bodyupdatetime_isNull = queryMap.get("bodyupdatetime_isNull");
		Object bodyupdatetime_isNotNull = queryMap
				.get("bodyupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(HistoryLocationManualEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.in,
					devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.gt,
					devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.ge,
					devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.lt,
					devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.le,
					devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.in,
					devId_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.like, manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.isNull, manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (bodyupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.eq, bodyupdatetime));
		}
		if (bodyupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.like, bodyupdatetime_like));
		}
		if (bodyupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.isNull, bodyupdatetime_isNull));
		}
		if (bodyupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.isNotNull, bodyupdatetime_isNotNull));
		}

		pagelist = dbManager.queryByCondition(
				HistoryLocationManualEntity.class, qc, pageno, pagesize);
		return pagelist;
	}

	/**
	 * 删除记录
	 * 
	 * @param id
	 *            主键
	 * @param obj
	 */
	public boolean del(Integer id, Boolean delDev) {
		boolean result = false;
		if (id != null && id > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delNoTransaction(id,
						HistoryLocationManualEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					HistoryLocationManualEntity historyLocationManual = (HistoryLocationManualEntity) dbManager
							.getById(id, HistoryLocationManualEntity.class);
					if (historyLocationManual.getDevId() != null) {
						dbManager.delNoTransaction(
								historyLocationManual.getDevId(),
								DevEntity.class);
					}
				}
				tx.commitAndClose();
			} catch (Exception e) {
				logger.error("数据库提交失败！");
				logger.error(e);
				result = false;
				try {
					tx.rollbackAndClose();
				} catch (Exception ex) {
					logger.error("数据库回滚失败！");
					logger.error(ex);
				}
			}
		}
		return result;
	}

	/**
	 * 批量条件删除记录
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param delDevList
	 *            是否删除关联信息
	 */
	public boolean delList(Map<String, Object> queryMap, Boolean delDevList) {
		boolean result = false;
		if (queryMap == null) {
			queryMap = new HashMap<String, Object>();
		}
		Object id = queryMap.get("id");
		Object id_gt = queryMap.get("id_gt");
		Object id_ge = queryMap.get("id_ge");
		Object id_lt = queryMap.get("id_lt");
		Object id_le = queryMap.get("id_le");
		Object id_in = queryMap.get("id_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object bodyupdatetime = queryMap.get("bodyupdatetime");
		Object bodyupdatetime_like = queryMap.get("bodyupdatetime_like");
		Object bodyupdatetime_isNull = queryMap.get("bodyupdatetime_isNull");
		Object bodyupdatetime_isNotNull = queryMap
				.get("bodyupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(HistoryLocationManualEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationManualEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.in,
					devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.gt,
					devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.ge,
					devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.lt,
					devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.le,
					devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.DEV_ID, QueryCondition.in,
					devId_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.like, manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.isNull, manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (bodyupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.eq, bodyupdatetime));
		}
		if (bodyupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.like, bodyupdatetime_like));
		}
		if (bodyupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.isNull, bodyupdatetime_isNull));
		}
		if (bodyupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationManualEntity.BODYUPDATETIME,
					QueryCondition.isNotNull, bodyupdatetime_isNotNull));
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						HistoryLocationManualEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							HistoryLocationManualEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							HistoryLocationManualEntity entity = (HistoryLocationManualEntity) obj;
							strIds += entity.getDevId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(DevEntity.ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(DevEntity.class,
								qc1);
					}

				}
				tx.commitAndClose();
			} catch (Exception e) {
				logger.error("数据库提交失败！");
				logger.error(e);
				result = false;
				try {
					tx.rollbackAndClose();
				} catch (Exception ex) {
					logger.error("数据库回滚失败！");
					logger.error(ex);
				}
			}
		}
		return result;
	}
}