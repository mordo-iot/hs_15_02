package fly.service.historyLocationMove;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.historyLocationMove.HistoryLocationMoveEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 定位器人卡分离历史状态服务类
 * @author feng.gu
 * @date 2015-08-10 10:18:52
 * @version V1.0
 * 
 */
public class HistoryLocationMoveService {
	private static Logger logger = Logger
			.getLogger(HistoryLocationMoveService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static HistoryLocationMoveService historyLocationMoveService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static HistoryLocationMoveService getInstance() {
		if (historyLocationMoveService == null) {
			historyLocationMoveService = new HistoryLocationMoveService();
		}
		return historyLocationMoveService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(HistoryLocationMoveEntity historyLocationMove) {
		boolean result = false;
		if (historyLocationMove != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(historyLocationMove);
				// 关联信息保存
				if (historyLocationMove.getDev() != null) {
					dbManager.saveNoTransaction(historyLocationMove.getDev());
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
			List<HistoryLocationMoveEntity> historyLocationMoveList) {
		boolean result = false;
		if (historyLocationMoveList != null
				&& historyLocationMoveList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (HistoryLocationMoveEntity historyLocationMove : historyLocationMoveList) {
					if (historyLocationMove != null) {
						dbManager.saveNoTransaction(historyLocationMove);
						// 关联信息保存
						if (historyLocationMove.getDev() != null) {
							dbManager.saveNoTransaction(historyLocationMove
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
	public HistoryLocationMoveEntity getById(Integer id, Boolean devShow) {
		HistoryLocationMoveEntity obj = null;
		if (id != null) {
			obj = (HistoryLocationMoveEntity) dbManager.getById(id,
					HistoryLocationMoveEntity.class);
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
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object movingupdatetime = queryMap.get("movingupdatetime");
		Object movingupdatetime_like = queryMap.get("movingupdatetime_like");
		Object movingupdatetime_isNull = queryMap
				.get("movingupdatetime_isNull");
		Object movingupdatetime_isNotNull = queryMap
				.get("movingupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(HistoryLocationMoveEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.gt,
					devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.ge,
					devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.lt,
					devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.le,
					devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.in,
					devId_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.like,
					moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.isNull,
					moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.isNotNull,
					moving_isNotNull));
		}
		if (movingupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.eq, movingupdatetime));
		}
		if (movingupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.like, movingupdatetime_like));
		}
		if (movingupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.isNull, movingupdatetime_isNull));
		}
		if (movingupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.isNotNull, movingupdatetime_isNotNull));
		}

		pagelist = dbManager.queryByCondition(HistoryLocationMoveEntity.class,
				qc, pageno, pagesize);
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
						HistoryLocationMoveEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					HistoryLocationMoveEntity historyLocationMove = (HistoryLocationMoveEntity) dbManager
							.getById(id, HistoryLocationMoveEntity.class);
					if (historyLocationMove.getDevId() != null) {
						dbManager
								.delNoTransaction(
										historyLocationMove.getDevId(),
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
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object movingupdatetime = queryMap.get("movingupdatetime");
		Object movingupdatetime_like = queryMap.get("movingupdatetime_like");
		Object movingupdatetime_isNull = queryMap
				.get("movingupdatetime_isNull");
		Object movingupdatetime_isNotNull = queryMap
				.get("movingupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(HistoryLocationMoveEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationMoveEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.gt,
					devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.ge,
					devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.lt,
					devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.le,
					devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.DEV_ID, QueryCondition.in,
					devId_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.like,
					moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.isNull,
					moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVING, QueryCondition.isNotNull,
					moving_isNotNull));
		}
		if (movingupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.eq, movingupdatetime));
		}
		if (movingupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.like, movingupdatetime_like));
		}
		if (movingupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.isNull, movingupdatetime_isNull));
		}
		if (movingupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationMoveEntity.MOVINGUPDATETIME,
					QueryCondition.isNotNull, movingupdatetime_isNotNull));
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						HistoryLocationMoveEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							HistoryLocationMoveEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							HistoryLocationMoveEntity entity = (HistoryLocationMoveEntity) obj;
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
