package fly.service.historyTizhengBed;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.historyTizhengBed.HistoryTizhengBedEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 体征床垫历史信息表服务类
 * @author feng.gu
 * @date 2015-08-20 13:57:06
 * @version V1.0
 * 
 */
public class HistoryTizhengBedService {
	private static Logger logger = Logger
			.getLogger(HistoryTizhengBedService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static HistoryTizhengBedService historyTizhengBedService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static HistoryTizhengBedService getInstance() {
		if (historyTizhengBedService == null) {
			historyTizhengBedService = new HistoryTizhengBedService();
		}
		return historyTizhengBedService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(HistoryTizhengBedEntity historyTizhengBed) {
		boolean result = false;
		if (historyTizhengBed != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(historyTizhengBed);
				// 关联信息保存
				if (historyTizhengBed.getDev() != null) {
					dbManager.saveNoTransaction(historyTizhengBed.getDev());
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
	public boolean saveList(List<HistoryTizhengBedEntity> historyTizhengBedList) {
		boolean result = false;
		if (historyTizhengBedList != null && historyTizhengBedList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (HistoryTizhengBedEntity historyTizhengBed : historyTizhengBedList) {
					if (historyTizhengBed != null) {
						dbManager.saveNoTransaction(historyTizhengBed);
						// 关联信息保存
						if (historyTizhengBed.getDev() != null) {
							dbManager.saveNoTransaction(historyTizhengBed
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
	public HistoryTizhengBedEntity getById(Integer id, Boolean devShow) {
		HistoryTizhengBedEntity obj = null;
		if (id != null) {
			obj = (HistoryTizhengBedEntity) dbManager.getById(id,
					HistoryTizhengBedEntity.class);
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
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object alarmupdatetime_in = queryMap.get("alarmupdatetime_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");
		Object devupdatetime_in = queryMap.get("devupdatetime_in");

		QueryCondition qc = new QueryCondition(HistoryTizhengBedEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME, QueryCondition.eq,
					alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (alarmupdatetime_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME, QueryCondition.in,
					alarmupdatetime_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.eq,
					devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.like,
					devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}
		if (devupdatetime_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.in,
					devupdatetime_in));
		}

		list = dbManager.queryByCondition(HistoryTizhengBedEntity.class, qc);
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
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object alarmupdatetime_in = queryMap.get("alarmupdatetime_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");
		Object devupdatetime_in = queryMap.get("devupdatetime_in");

		QueryCondition qc = new QueryCondition(HistoryTizhengBedEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME, QueryCondition.eq,
					alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (alarmupdatetime_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME, QueryCondition.in,
					alarmupdatetime_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.eq,
					devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.like,
					devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}
		if (devupdatetime_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.in,
					devupdatetime_in));
		}

		pagelist = dbManager.queryByCondition(HistoryTizhengBedEntity.class,
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
						HistoryTizhengBedEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					HistoryTizhengBedEntity historyTizhengBed = (HistoryTizhengBedEntity) dbManager
							.getById(id, HistoryTizhengBedEntity.class);
					if (historyTizhengBed.getDevId() != null) {
						dbManager.delNoTransaction(
								historyTizhengBed.getDevId(), DevEntity.class);
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
		Object alarmupdatetime = queryMap.get("alarmupdatetime");
		Object alarmupdatetime_like = queryMap.get("alarmupdatetime_like");
		Object alarmupdatetime_isNull = queryMap.get("alarmupdatetime_isNull");
		Object alarmupdatetime_isNotNull = queryMap
				.get("alarmupdatetime_isNotNull");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object devupdatetime = queryMap.get("devupdatetime");
		Object devupdatetime_like = queryMap.get("devupdatetime_like");
		Object devupdatetime_isNull = queryMap.get("devupdatetime_isNull");
		Object devupdatetime_isNotNull = queryMap
				.get("devupdatetime_isNotNull");

		QueryCondition qc = new QueryCondition(HistoryTizhengBedEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (alarmupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME, QueryCondition.eq,
					alarmupdatetime));
		}
		if (alarmupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.like, alarmupdatetime_like));
		}
		if (alarmupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNull, alarmupdatetime_isNull));
		}
		if (alarmupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.ALARMUPDATETIME,
					QueryCondition.isNotNull, alarmupdatetime_isNotNull));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengBedEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (devupdatetime != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.eq,
					devupdatetime));
		}
		if (devupdatetime_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME, QueryCondition.like,
					devupdatetime_like));
		}
		if (devupdatetime_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNull, devupdatetime_isNull));
		}
		if (devupdatetime_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengBedEntity.DEVUPDATETIME,
					QueryCondition.isNotNull, devupdatetime_isNotNull));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						HistoryTizhengBedEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							HistoryTizhengBedEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							HistoryTizhengBedEntity entity = (HistoryTizhengBedEntity) obj;
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
