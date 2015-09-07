package fly.service.currentUrine;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.currentUrine.CurrentUrineEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 尿湿当前信息服务类
 * @author feng.gu
 * @date 2015-09-07 16:19:46
 * @version V1.0
 * 
 */
public class CurrentUrineService {
	private static Logger logger = Logger.getLogger(CurrentUrineService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static CurrentUrineService currentUrineService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static CurrentUrineService getInstance() {
		if (currentUrineService == null) {
			currentUrineService = new CurrentUrineService();
		}
		return currentUrineService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(CurrentUrineEntity currentUrine) {
		boolean result = false;
		if (currentUrine != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 关联信息保存
				DevEntity dev = currentUrine.getDev();
				if (dev != null) {
					dbManager.saveNoTransaction(dev);
					currentUrine.setDevId(dev.getId());
				}
				result = dbManager.saveNoTransaction(currentUrine);
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
	public boolean saveList(List<CurrentUrineEntity> currentUrineList) {
		boolean result = false;
		if (currentUrineList != null && currentUrineList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (CurrentUrineEntity currentUrine : currentUrineList) {
					if (currentUrine != null) {
						// 关联信息保存
						DevEntity dev = currentUrine.getDev();
						if (dev != null) {
							dbManager.saveNoTransaction(dev);
							currentUrine.setDevId(dev.getId());
						}
						result = dbManager.saveNoTransaction(currentUrine);
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
	public CurrentUrineEntity getById(Integer id, Boolean devShow) {
		CurrentUrineEntity obj = null;
		if (id != null) {
			obj = (CurrentUrineEntity) dbManager.getById(id,
					CurrentUrineEntity.class);
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
	 * 根据条件查询记录集合（不分页）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @return
	 */
	public List<Object> getListByCondition(Map<String, Object> queryMap) {
		List<Object> list = null;
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
		Object alarm_in = queryMap.get("alarm_in");
		Object alarmupdatetime_gt = queryMap.get("alarmupdatetime_gt");
		Object alarmupdatetime_ge = queryMap.get("alarmupdatetime_ge");
		Object alarmupdatetime_lt = queryMap.get("alarmupdatetime_lt");
		Object alarmupdatetime_le = queryMap.get("alarmupdatetime_le");
		Object normal = queryMap.get("normal");
		Object normal_like = queryMap.get("normal_like");
		Object normal_isNull = queryMap.get("normal_isNull");
		Object normal_isNotNull = queryMap.get("normal_isNotNull");
		Object normal_in = queryMap.get("normal_in");
		Object online = queryMap.get("online");
		Object online_like = queryMap.get("online_like");
		Object online_isNull = queryMap.get("online_isNull");
		Object online_isNotNull = queryMap.get("online_isNotNull");
		Object online_in = queryMap.get("online_in");
		Object power = queryMap.get("power");
		Object power_like = queryMap.get("power_like");
		Object power_isNull = queryMap.get("power_isNull");
		Object power_isNotNull = queryMap.get("power_isNotNull");
		Object power_in = queryMap.get("power_in");
		Object devupdatetime_gt = queryMap.get("devupdatetime_gt");
		Object devupdatetime_ge = queryMap.get("devupdatetime_ge");
		Object devupdatetime_lt = queryMap.get("devupdatetime_lt");
		Object devupdatetime_le = queryMap.get("devupdatetime_le");

		QueryCondition qc = new QueryCondition(CurrentUrineEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (alarm != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.eq, alarm));
		}
		if (alarm_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.like, alarm_like));
		}
		if (alarm_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNull, alarm_isNull));
		}
		if (alarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNotNull, alarm_isNotNull));
		}
		if (alarm_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.in, alarm_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.gt,
					devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.ge,
					devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.lt,
					devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.le,
					devupdatetime_le));
		}

		list = dbManager.queryByCondition(CurrentUrineEntity.class, qc);
		return list;
	}

	/**
	 * 根据条件查询记录集合
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
		Object alarm_in = queryMap.get("alarm_in");
		Object alarmupdatetime_gt = queryMap.get("alarmupdatetime_gt");
		Object alarmupdatetime_ge = queryMap.get("alarmupdatetime_ge");
		Object alarmupdatetime_lt = queryMap.get("alarmupdatetime_lt");
		Object alarmupdatetime_le = queryMap.get("alarmupdatetime_le");
		Object normal = queryMap.get("normal");
		Object normal_like = queryMap.get("normal_like");
		Object normal_isNull = queryMap.get("normal_isNull");
		Object normal_isNotNull = queryMap.get("normal_isNotNull");
		Object normal_in = queryMap.get("normal_in");
		Object online = queryMap.get("online");
		Object online_like = queryMap.get("online_like");
		Object online_isNull = queryMap.get("online_isNull");
		Object online_isNotNull = queryMap.get("online_isNotNull");
		Object online_in = queryMap.get("online_in");
		Object power = queryMap.get("power");
		Object power_like = queryMap.get("power_like");
		Object power_isNull = queryMap.get("power_isNull");
		Object power_isNotNull = queryMap.get("power_isNotNull");
		Object power_in = queryMap.get("power_in");
		Object devupdatetime_gt = queryMap.get("devupdatetime_gt");
		Object devupdatetime_ge = queryMap.get("devupdatetime_ge");
		Object devupdatetime_lt = queryMap.get("devupdatetime_lt");
		Object devupdatetime_le = queryMap.get("devupdatetime_le");

		QueryCondition qc = new QueryCondition(CurrentUrineEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (alarm != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.eq, alarm));
		}
		if (alarm_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.like, alarm_like));
		}
		if (alarm_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNull, alarm_isNull));
		}
		if (alarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNotNull, alarm_isNotNull));
		}
		if (alarm_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.in, alarm_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.gt,
					devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.ge,
					devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.lt,
					devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.le,
					devupdatetime_le));
		}

		pagelist = dbManager.queryByCondition(CurrentUrineEntity.class, qc,
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
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					CurrentUrineEntity currentUrine = (CurrentUrineEntity) dbManager
							.getById(id, CurrentUrineEntity.class);
					if (currentUrine != null && currentUrine.getDevId() != null) {
						dbManager.delNoTransaction(currentUrine.getDevId(),
								DevEntity.class);
					}
				}
				result = dbManager.delNoTransaction(id,
						CurrentUrineEntity.class);
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
	 */
	public boolean delList(Map<String, Object> queryMap, Boolean delDev) {
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
		Object alarm_in = queryMap.get("alarm_in");
		Object alarmupdatetime_gt = queryMap.get("alarmupdatetime_gt");
		Object alarmupdatetime_ge = queryMap.get("alarmupdatetime_ge");
		Object alarmupdatetime_lt = queryMap.get("alarmupdatetime_lt");
		Object alarmupdatetime_le = queryMap.get("alarmupdatetime_le");
		Object normal = queryMap.get("normal");
		Object normal_like = queryMap.get("normal_like");
		Object normal_isNull = queryMap.get("normal_isNull");
		Object normal_isNotNull = queryMap.get("normal_isNotNull");
		Object normal_in = queryMap.get("normal_in");
		Object online = queryMap.get("online");
		Object online_like = queryMap.get("online_like");
		Object online_isNull = queryMap.get("online_isNull");
		Object online_isNotNull = queryMap.get("online_isNotNull");
		Object online_in = queryMap.get("online_in");
		Object power = queryMap.get("power");
		Object power_like = queryMap.get("power_like");
		Object power_isNull = queryMap.get("power_isNull");
		Object power_isNotNull = queryMap.get("power_isNotNull");
		Object power_in = queryMap.get("power_in");
		Object devupdatetime_gt = queryMap.get("devupdatetime_gt");
		Object devupdatetime_ge = queryMap.get("devupdatetime_ge");
		Object devupdatetime_lt = queryMap.get("devupdatetime_lt");
		Object devupdatetime_le = queryMap.get("devupdatetime_le");

		QueryCondition qc = new QueryCondition(CurrentUrineEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (alarm != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.eq, alarm));
		}
		if (alarm_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.like, alarm_like));
		}
		if (alarm_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNull, alarm_isNull));
		}
		if (alarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.isNotNull, alarm_isNotNull));
		}
		if (alarm_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ALARM,
					QueryCondition.in, alarm_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentUrineEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.gt,
					devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.ge,
					devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.lt,
					devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentUrineEntity.DEVUPDATETIME, QueryCondition.le,
					devupdatetime_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							CurrentUrineEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							CurrentUrineEntity entity = (CurrentUrineEntity) obj;
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
				result = dbManager.delByConditionsNoTransaction(
						CurrentUrineEntity.class, qc);
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
