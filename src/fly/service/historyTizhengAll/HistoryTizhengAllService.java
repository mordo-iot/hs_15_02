package fly.service.historyTizhengAll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.historyTizhengAll.HistoryTizhengAllEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.query.OrderByCondition;
import com.framework.system.db.query.OrderVO;
import com.framework.system.db.transaction.TransactionManager;
import com.framework.system.util.StringUtil;

/**
 * @Title: Service
 * @Description: 体征床垫历史信息合并表服务类
 * @author feng.gu
 * @date 2015-10-29 12:49:30
 * @version V1.0
 * 
 */
public class HistoryTizhengAllService {
	private static Logger logger = Logger
			.getLogger(HistoryTizhengAllService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static HistoryTizhengAllService historyTizhengAllService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static HistoryTizhengAllService getInstance() {
		if (historyTizhengAllService == null) {
			historyTizhengAllService = new HistoryTizhengAllService();
		}
		return historyTizhengAllService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(HistoryTizhengAllEntity historyTizhengAll) {
		boolean result = false;
		if (historyTizhengAll != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 关联信息保存
				DevEntity dev = historyTizhengAll.getDev();
				if (dev != null) {
					dbManager.saveNoTransaction(dev);
					historyTizhengAll.setDevId(dev.getId());
				}
				result = dbManager.saveNoTransaction(historyTizhengAll);
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
	public boolean saveList(List<HistoryTizhengAllEntity> historyTizhengAllList) {
		boolean result = false;
		if (historyTizhengAllList != null && historyTizhengAllList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (HistoryTizhengAllEntity historyTizhengAll : historyTizhengAllList) {
					if (historyTizhengAll != null) {
						// 关联信息保存
						DevEntity dev = historyTizhengAll.getDev();
						if (dev != null) {
							dbManager.saveNoTransaction(dev);
							historyTizhengAll.setDevId(dev.getId());
						}
						result = dbManager.saveNoTransaction(historyTizhengAll);
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
	public HistoryTizhengAllEntity getById(Integer id, Boolean devShow) {
		HistoryTizhengAllEntity obj = null;
		if (id != null) {
			obj = (HistoryTizhengAllEntity) dbManager.getById(id,
					HistoryTizhengAllEntity.class);
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
	 * 根据条件查询记录集合（不分页 不带排序 不级联查询）
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
		Object id_like = queryMap.get("id_like");
		Object id_isNull = queryMap.get("id_isNull");
		Object id_isNotNull = queryMap.get("id_isNotNull");
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
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryTizhengAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.le, time_le));
		}

		list = dbManager.queryByCondition(HistoryTizhengAllEntity.class, qc);
		return list;
	}

	/**
	 * 根据条件查询记录集合（不分页 带排序 带级联查询）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param orderList
	 *            排序条件集合
	 * @param devShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @return
	 */
	public List<Object> getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList, Boolean devShow) {
		List<Object> list = null;
		if (queryMap == null) {
			queryMap = new HashMap<String, Object>();
		}
		Object id = queryMap.get("id");
		Object id_like = queryMap.get("id_like");
		Object id_isNull = queryMap.get("id_isNull");
		Object id_isNotNull = queryMap.get("id_isNotNull");
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
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryTizhengAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.le, time_le));
		}

		OrderByCondition oc = null;
		if (orderList != null && orderList.size() > 0) {
			for (int i = 0; i < orderList.size(); i++) {
				OrderVO order = orderList.get(i);
				String orderColumnt = null;
				String orderType = null;
				if (order.getName() != null && !"".equals(order.getName())) {
					orderColumnt = StringUtil.formatFieldToColumnt(order
							.getName());
					orderType = order.getOrderType();
					if (orderType == null || "".equals(orderType.trim())) {
						orderType = OrderByCondition.desc;
					}
					if (i == 0) {
						oc = new OrderByCondition(orderColumnt, orderType);
					} else {
						oc.orderByCondition(new OrderByCondition(orderColumnt,
								orderType));
					}
				}

			}
		}
		list = dbManager.queryByConditions(HistoryTizhengAllEntity.class, qc,
				oc);
		int a = 0;
		if (devShow != null && devShow.booleanValue()) {
			a++;
		}
		if (a > 0 && list != null && list.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				HistoryTizhengAllEntity obj = (HistoryTizhengAllEntity) list
						.get(i);
				// 查询关联内容
				if (devShow != null && devShow.booleanValue() && obj != null
						&& obj.getDevId() > 0) {
					DevEntity dev = (DevEntity) dbManager.getById(
							obj.getDevId(), DevEntity.class);
					obj.setDev(dev);
				}
				result.add(obj);
			}
			list = result;
		}
		return list;
	}

	/**
	 * 根据条件查询记录集合（带分页 不带排序 不级联查询）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param pageno
	 *            查询页码
	 * @param pagesize
	 *            查询每页记录条数
	 * @return
	 */
	public PageList getListByCondition(Map<String, Object> queryMap,
			int pageno, int pagesize) {
		PageList pagelist = null;
		if (queryMap == null) {
			queryMap = new HashMap<String, Object>();
		}
		Object id = queryMap.get("id");
		Object id_like = queryMap.get("id_like");
		Object id_isNull = queryMap.get("id_isNull");
		Object id_isNotNull = queryMap.get("id_isNotNull");
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
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryTizhengAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.le, time_le));
		}

		pagelist = dbManager.queryByCondition(HistoryTizhengAllEntity.class,
				qc, pageno, pagesize);
		return pagelist;
	}

	/**
	 * 根据条件查询记录集合（带分页 带排序 带级联查询）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param orderList
	 *            排序条件集合
	 * @param pageno
	 *            查询页码
	 * @param pagesize
	 *            查询每页记录条数
	 * @param devShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @return
	 */
	public PageList getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList, int pageno, int pagesize, Boolean devShow) {
		PageList pagelist = null;
		if (queryMap == null) {
			queryMap = new HashMap<String, Object>();
		}
		Object id = queryMap.get("id");
		Object id_like = queryMap.get("id_like");
		Object id_isNull = queryMap.get("id_isNull");
		Object id_isNotNull = queryMap.get("id_isNotNull");
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
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryTizhengAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.le, time_le));
		}

		OrderByCondition oc = null;
		if (orderList != null && orderList.size() > 0) {
			for (int i = 0; i < orderList.size(); i++) {
				OrderVO order = orderList.get(i);
				String orderColumnt = null;
				String orderType = null;
				if (order.getName() != null && !"".equals(order.getName())) {
					orderColumnt = StringUtil.formatFieldToColumnt(order
							.getName());
					orderType = order.getOrderType();
					if (orderType == null || "".equals(orderType.trim())) {
						orderType = OrderByCondition.desc;
					}
					if (i == 0) {
						oc = new OrderByCondition(orderColumnt, orderType);
					} else {
						oc.orderByCondition(new OrderByCondition(orderColumnt,
								orderType));
					}
				}

			}
		}
		pagelist = dbManager.queryByConditions(HistoryTizhengAllEntity.class,
				qc, oc, pageno, pagesize);
		int a = 0;
		if (devShow != null && devShow.booleanValue()) {
			a++;
		}
		if (a > 0 && pagelist != null && pagelist.getResultList() != null
				&& pagelist.getResultList().size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < pagelist.getResultList().size(); i++) {
				HistoryTizhengAllEntity obj = (HistoryTizhengAllEntity) pagelist
						.getResultList().get(i);
				// 查询关联内容
				if (devShow != null && devShow.booleanValue() && obj != null
						&& obj.getDevId() > 0) {
					DevEntity dev = (DevEntity) dbManager.getById(
							obj.getDevId(), DevEntity.class);
					obj.setDev(dev);
				}
				result.add(obj);
			}
			pagelist.setResultList(result);
		}
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
					HistoryTizhengAllEntity historyTizhengAll = (HistoryTizhengAllEntity) dbManager
							.getById(id, HistoryTizhengAllEntity.class);
					if (historyTizhengAll != null
							&& historyTizhengAll.getDevId() != null) {
						dbManager.delNoTransaction(
								historyTizhengAll.getDevId(), DevEntity.class);
					}
				}
				result = dbManager.delNoTransaction(id,
						HistoryTizhengAllEntity.class);
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
		Object id_like = queryMap.get("id_like");
		Object id_isNull = queryMap.get("id_isNull");
		Object id_isNotNull = queryMap.get("id_isNotNull");
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
		Object breath = queryMap.get("breath");
		Object breath_gt = queryMap.get("breath_gt");
		Object breath_ge = queryMap.get("breath_ge");
		Object breath_lt = queryMap.get("breath_lt");
		Object breath_le = queryMap.get("breath_le");
		Object breath_in = queryMap.get("breath_in");
		Object heart = queryMap.get("heart");
		Object heart_gt = queryMap.get("heart_gt");
		Object heart_ge = queryMap.get("heart_ge");
		Object heart_lt = queryMap.get("heart_lt");
		Object heart_le = queryMap.get("heart_le");
		Object heart_in = queryMap.get("heart_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryTizhengAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (havingbody != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.eq,
					havingbody));
		}
		if (havingbody_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.like,
					havingbody_like));
		}
		if (havingbody_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.isNull,
					havingbody_isNull));
		}
		if (havingbody_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY,
					QueryCondition.isNotNull, havingbody_isNotNull));
		}
		if (havingbody_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryTizhengAllEntity.HAVINGBODY, QueryCondition.in,
					havingbody_in));
		}
		if (breath != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.eq, breath));
		}
		if (breath_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.gt, breath_gt));
		}
		if (breath_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.ge, breath_ge));
		}
		if (breath_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.lt, breath_lt));
		}
		if (breath_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.le, breath_le));
		}
		if (breath_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.BREATH,
					QueryCondition.in, breath_in));
		}
		if (heart != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.eq, heart));
		}
		if (heart_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.gt, heart_gt));
		}
		if (heart_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.ge, heart_ge));
		}
		if (heart_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.lt, heart_lt));
		}
		if (heart_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.le, heart_le));
		}
		if (heart_in != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.HEART,
					QueryCondition.in, heart_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryTizhengAllEntity.TIME,
					QueryCondition.le, time_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							HistoryTizhengAllEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							HistoryTizhengAllEntity entity = (HistoryTizhengAllEntity) obj;
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
						HistoryTizhengAllEntity.class, qc);
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
