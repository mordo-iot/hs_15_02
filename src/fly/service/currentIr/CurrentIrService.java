package fly.service.currentIr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.currentIr.CurrentIrEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 红外当前信息服务类
 * @author feng.gu
 * @date 2015-08-17 14:50:25
 * @version V1.0
 * 
 */
public class CurrentIrService {
	private static Logger logger = Logger.getLogger(CurrentIrService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static CurrentIrService currentIrService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static CurrentIrService getInstance() {
		if (currentIrService == null) {
			currentIrService = new CurrentIrService();
		}
		return currentIrService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(CurrentIrEntity currentIr) {
		boolean result = false;
		if (currentIr != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(currentIr);
				// 关联信息保存
				if (currentIr.getDev() != null) {
					dbManager.saveNoTransaction(currentIr.getDev());
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
	public boolean saveList(List<CurrentIrEntity> currentIrList) {
		boolean result = false;
		if (currentIrList != null && currentIrList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (CurrentIrEntity currentIr : currentIrList) {
					if (currentIr != null) {
						dbManager.saveNoTransaction(currentIr);
						// 关联信息保存
						if (currentIr.getDev() != null) {
							dbManager.saveNoTransaction(currentIr.getDev());
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
	public CurrentIrEntity getById(Integer id, Boolean devShow) {
		CurrentIrEntity obj = null;
		if (id != null) {
			obj = (CurrentIrEntity) dbManager
					.getById(id, CurrentIrEntity.class);
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
		Object havingbody = queryMap.get("havingbody");
		Object havingbody_like = queryMap.get("havingbody_like");
		Object havingbody_isNull = queryMap.get("havingbody_isNull");
		Object havingbody_isNotNull = queryMap.get("havingbody_isNotNull");
		Object havingbody_in = queryMap.get("havingbody_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object alarmupdatetime_in = queryMap.get("alarmupdatetime_in");
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
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");
		Object devupdatetime_in = queryMap.get("devupdatetime_in");

		QueryCondition qc = new QueryCondition(CurrentIrEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.eq, havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.like, havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNull, havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.in, havingbody_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.eq, alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (alarmupdatetime_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.in, alarmupdatetime_in));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.eq, devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.like, devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}
		if (devupdatetime_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.in, devupdatetime_in));
		}

		list = dbManager.queryByCondition(CurrentIrEntity.class, qc);
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
		Object havingbody = queryMap.get("havingbody");
		Object havingbody_like = queryMap.get("havingbody_like");
		Object havingbody_isNull = queryMap.get("havingbody_isNull");
		Object havingbody_isNotNull = queryMap.get("havingbody_isNotNull");
		Object havingbody_in = queryMap.get("havingbody_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object alarmupdatetime_in = queryMap.get("alarmupdatetime_in");
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
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");
		Object devupdatetime_in = queryMap.get("devupdatetime_in");

		QueryCondition qc = new QueryCondition(CurrentIrEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.eq, havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.like, havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNull, havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.in, havingbody_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.eq, alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (alarmupdatetime_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.in, alarmupdatetime_in));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.eq, devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.like, devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}
		if (devupdatetime_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.in, devupdatetime_in));
		}

		pagelist = dbManager.queryByCondition(CurrentIrEntity.class, qc,
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
				result = dbManager.delNoTransaction(id, CurrentIrEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					CurrentIrEntity currentIr = (CurrentIrEntity) dbManager
							.getById(id, CurrentIrEntity.class);
					if (currentIr.getDevId() != null) {
						dbManager.delNoTransaction(currentIr.getDevId(),
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
		Object havingbody = queryMap.get("havingbody");
		Object havingbody_like = queryMap.get("havingbody_like");
		Object havingbody_isNull = queryMap.get("havingbody_isNull");
		Object havingbody_isNotNull = queryMap.get("havingbody_isNotNull");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentIrEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.eq, havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.like, havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNull, havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.eq, alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.eq, devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.like, devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentIrEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						CurrentIrEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							CurrentIrEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							CurrentIrEntity entity = (CurrentIrEntity) obj;
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
