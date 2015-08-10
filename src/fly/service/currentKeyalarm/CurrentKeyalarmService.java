package fly.service.currentKeyalarm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.currentKeyalarm.CurrentKeyalarmEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 一键报警当前信息服务类
 * @author feng.gu
 * @date 2015-08-10 10:07:15
 * @version V1.0
 * 
 */
public class CurrentKeyalarmService {
	private static Logger logger = Logger
			.getLogger(CurrentKeyalarmService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static CurrentKeyalarmService currentKeyalarmService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static CurrentKeyalarmService getInstance() {
		if (currentKeyalarmService == null) {
			currentKeyalarmService = new CurrentKeyalarmService();
		}
		return currentKeyalarmService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(CurrentKeyalarmEntity currentKeyalarm) {
		boolean result = false;
		if (currentKeyalarm != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(currentKeyalarm);
				// 关联信息保存
				if (currentKeyalarm.getDev() != null) {
					dbManager.saveNoTransaction(currentKeyalarm.getDev());
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
	public boolean saveList(List<CurrentKeyalarmEntity> currentKeyalarmList) {
		boolean result = false;
		if (currentKeyalarmList != null && currentKeyalarmList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (CurrentKeyalarmEntity currentKeyalarm : currentKeyalarmList) {
					if (currentKeyalarm != null) {
						dbManager.saveNoTransaction(currentKeyalarm);
						// 关联信息保存
						if (currentKeyalarm.getDev() != null) {
							dbManager.saveNoTransaction(currentKeyalarm
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
	public CurrentKeyalarmEntity getById(Integer id, Boolean devShow) {
		CurrentKeyalarmEntity obj = null;
		if (id != null) {
			obj = (CurrentKeyalarmEntity) dbManager.getById(id,
					CurrentKeyalarmEntity.class);
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
		Object alarm = queryMap.get("alarm");
		Object alarm_like = queryMap.get("alarm_like");
		Object alarm_isNull = queryMap.get("alarm_isNull");
		Object alarm_isNotNull = queryMap.get("alarm_isNotNull");
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object normal = queryMap.get("normal");
		Object normal_like = queryMap.get("normal_like");
		Object normal_isNull = queryMap.get("normal_isNull");
		Object normal_isNotNull = queryMap.get("normal_isNotNull");
		Object online = queryMap.get("online");
		Object online_like = queryMap.get("online_like");
		Object online_isNull = queryMap.get("online_isNull");
		Object online_isNotNull = queryMap.get("online_isNotNull");
		Object power = queryMap.get("power");
		Object power_like = queryMap.get("power_like");
		Object power_isNull = queryMap.get("power_isNull");
		Object power_isNotNull = queryMap.get("power_isNotNull");
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(CurrentKeyalarmEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (alarm != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.eq, alarm));
		}
		if (alarm_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.like, alarm_like));
		}
		if (alarm_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.isNull, alarm_isNull));
		}
		if (alarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.isNotNull, alarm_isNotNull));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME, QueryCondition.eq,
					alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME, QueryCondition.like,
					alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.eq,
					devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.like,
					devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.isNull,
					devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}

		pagelist = dbManager.queryByCondition(CurrentKeyalarmEntity.class, qc,
				pageno, pagesize);
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
						CurrentKeyalarmEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					CurrentKeyalarmEntity currentKeyalarm = (CurrentKeyalarmEntity) dbManager
							.getById(id, CurrentKeyalarmEntity.class);
					if (currentKeyalarm.getDevId() != null) {
						dbManager.delNoTransaction(currentKeyalarm.getDevId(),
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
		Object alarm = queryMap.get("alarm");
		Object alarm_like = queryMap.get("alarm_like");
		Object alarm_isNull = queryMap.get("alarm_isNull");
		Object alarm_isNotNull = queryMap.get("alarm_isNotNull");
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object normal = queryMap.get("normal");
		Object normal_like = queryMap.get("normal_like");
		Object normal_isNull = queryMap.get("normal_isNull");
		Object normal_isNotNull = queryMap.get("normal_isNotNull");
		Object online = queryMap.get("online");
		Object online_like = queryMap.get("online_like");
		Object online_isNull = queryMap.get("online_isNull");
		Object online_isNotNull = queryMap.get("online_isNotNull");
		Object power = queryMap.get("power");
		Object power_like = queryMap.get("power_like");
		Object power_isNull = queryMap.get("power_isNull");
		Object power_isNotNull = queryMap.get("power_isNotNull");
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(CurrentKeyalarmEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (alarm != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.eq, alarm));
		}
		if (alarm_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.like, alarm_like));
		}
		if (alarm_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.isNull, alarm_isNull));
		}
		if (alarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ALARM,
					QueryCondition.isNotNull, alarm_isNotNull));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME, QueryCondition.eq,
					alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME, QueryCondition.like,
					alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentKeyalarmEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.eq,
					devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.like,
					devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME, QueryCondition.isNull,
					devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					CurrentKeyalarmEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						CurrentKeyalarmEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							CurrentKeyalarmEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							CurrentKeyalarmEntity entity = (CurrentKeyalarmEntity) obj;
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
