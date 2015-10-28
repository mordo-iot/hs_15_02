package fly.service.historyLocation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.historyLocation.HistoryLocationEntity;
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
 * @Description: 园区一卡通历史信息合并表服务类
 * @author feng.gu
 * @date 2015-10-28 15:29:39
 * @version V1.0
 * 
 */
public class HistoryLocationService {
	private static Logger logger = Logger
			.getLogger(HistoryLocationService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static HistoryLocationService historyLocationService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static HistoryLocationService getInstance() {
		if (historyLocationService == null) {
			historyLocationService = new HistoryLocationService();
		}
		return historyLocationService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(HistoryLocationEntity historyLocation) {
		boolean result = false;
		if (historyLocation != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 关联信息保存
				DevEntity dev = historyLocation.getDev();
				if (dev != null) {
					dbManager.saveNoTransaction(dev);
					historyLocation.setDevId(dev.getId());
				}
				result = dbManager.saveNoTransaction(historyLocation);
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
	public boolean saveList(List<HistoryLocationEntity> historyLocationList) {
		boolean result = false;
		if (historyLocationList != null && historyLocationList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (HistoryLocationEntity historyLocation : historyLocationList) {
					if (historyLocation != null) {
						// 关联信息保存
						DevEntity dev = historyLocation.getDev();
						if (dev != null) {
							dbManager.saveNoTransaction(dev);
							historyLocation.setDevId(dev.getId());
						}
						result = dbManager.saveNoTransaction(historyLocation);
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
	public HistoryLocationEntity getById(Integer id, Boolean devShow) {
		HistoryLocationEntity obj = null;
		if (id != null) {
			obj = (HistoryLocationEntity) dbManager.getById(id,
					HistoryLocationEntity.class);
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
		Object bodystate = queryMap.get("bodystate");
		Object bodystate_like = queryMap.get("bodystate_like");
		Object bodystate_isNull = queryMap.get("bodystate_isNull");
		Object bodystate_isNotNull = queryMap.get("bodystate_isNotNull");
		Object bodystate_in = queryMap.get("bodystate_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object manualalarm_in = queryMap.get("manualalarm_in");
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object moving_in = queryMap.get("moving_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryLocationEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (bodystate != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.eq, bodystate));
		}
		if (bodystate_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.like, bodystate_like));
		}
		if (bodystate_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNull, bodystate_isNull));
		}
		if (bodystate_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNotNull, bodystate_isNotNull));
		}
		if (bodystate_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.in, bodystate_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.like,
					manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.isNull,
					manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (manualalarm_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.in,
					manualalarm_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.like, moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNull, moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNotNull, moving_isNotNull));
		}
		if (moving_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.in, moving_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.le, time_le));
		}

		list = dbManager.queryByCondition(HistoryLocationEntity.class, qc);
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
		Object bodystate = queryMap.get("bodystate");
		Object bodystate_like = queryMap.get("bodystate_like");
		Object bodystate_isNull = queryMap.get("bodystate_isNull");
		Object bodystate_isNotNull = queryMap.get("bodystate_isNotNull");
		Object bodystate_in = queryMap.get("bodystate_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object manualalarm_in = queryMap.get("manualalarm_in");
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object moving_in = queryMap.get("moving_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryLocationEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (bodystate != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.eq, bodystate));
		}
		if (bodystate_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.like, bodystate_like));
		}
		if (bodystate_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNull, bodystate_isNull));
		}
		if (bodystate_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNotNull, bodystate_isNotNull));
		}
		if (bodystate_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.in, bodystate_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.like,
					manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.isNull,
					manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (manualalarm_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.in,
					manualalarm_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.like, moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNull, moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNotNull, moving_isNotNull));
		}
		if (moving_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.in, moving_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
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
		list = dbManager.queryByConditions(HistoryLocationEntity.class, qc, oc);
		int a = 0;
		if (devShow != null && devShow.booleanValue()) {
			a++;
		}
		if (a > 0 && list != null && list.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				HistoryLocationEntity obj = (HistoryLocationEntity) list.get(i);
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
		Object bodystate = queryMap.get("bodystate");
		Object bodystate_like = queryMap.get("bodystate_like");
		Object bodystate_isNull = queryMap.get("bodystate_isNull");
		Object bodystate_isNotNull = queryMap.get("bodystate_isNotNull");
		Object bodystate_in = queryMap.get("bodystate_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object manualalarm_in = queryMap.get("manualalarm_in");
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object moving_in = queryMap.get("moving_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryLocationEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (bodystate != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.eq, bodystate));
		}
		if (bodystate_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.like, bodystate_like));
		}
		if (bodystate_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNull, bodystate_isNull));
		}
		if (bodystate_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNotNull, bodystate_isNotNull));
		}
		if (bodystate_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.in, bodystate_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.like,
					manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.isNull,
					manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (manualalarm_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.in,
					manualalarm_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.like, moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNull, moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNotNull, moving_isNotNull));
		}
		if (moving_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.in, moving_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.le, time_le));
		}

		pagelist = dbManager.queryByCondition(HistoryLocationEntity.class, qc,
				pageno, pagesize);
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
		Object bodystate = queryMap.get("bodystate");
		Object bodystate_like = queryMap.get("bodystate_like");
		Object bodystate_isNull = queryMap.get("bodystate_isNull");
		Object bodystate_isNotNull = queryMap.get("bodystate_isNotNull");
		Object bodystate_in = queryMap.get("bodystate_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object manualalarm_in = queryMap.get("manualalarm_in");
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object moving_in = queryMap.get("moving_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryLocationEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (bodystate != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.eq, bodystate));
		}
		if (bodystate_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.like, bodystate_like));
		}
		if (bodystate_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNull, bodystate_isNull));
		}
		if (bodystate_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNotNull, bodystate_isNotNull));
		}
		if (bodystate_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.in, bodystate_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.like,
					manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.isNull,
					manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (manualalarm_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.in,
					manualalarm_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.like, moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNull, moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNotNull, moving_isNotNull));
		}
		if (moving_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.in, moving_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
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
		pagelist = dbManager.queryByConditions(HistoryLocationEntity.class, qc,
				oc, pageno, pagesize);
		int a = 0;
		if (devShow != null && devShow.booleanValue()) {
			a++;
		}
		if (a > 0 && pagelist != null && pagelist.getResultList() != null
				&& pagelist.getResultList().size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < pagelist.getResultList().size(); i++) {
				HistoryLocationEntity obj = (HistoryLocationEntity) pagelist
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
					HistoryLocationEntity historyLocation = (HistoryLocationEntity) dbManager
							.getById(id, HistoryLocationEntity.class);
					if (historyLocation != null
							&& historyLocation.getDevId() != null) {
						dbManager.delNoTransaction(historyLocation.getDevId(),
								DevEntity.class);
					}
				}
				result = dbManager.delNoTransaction(id,
						HistoryLocationEntity.class);
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
		Object bodystate = queryMap.get("bodystate");
		Object bodystate_like = queryMap.get("bodystate_like");
		Object bodystate_isNull = queryMap.get("bodystate_isNull");
		Object bodystate_isNotNull = queryMap.get("bodystate_isNotNull");
		Object bodystate_in = queryMap.get("bodystate_in");
		Object manualalarm = queryMap.get("manualalarm");
		Object manualalarm_like = queryMap.get("manualalarm_like");
		Object manualalarm_isNull = queryMap.get("manualalarm_isNull");
		Object manualalarm_isNotNull = queryMap.get("manualalarm_isNotNull");
		Object manualalarm_in = queryMap.get("manualalarm_in");
		Object moving = queryMap.get("moving");
		Object moving_like = queryMap.get("moving_like");
		Object moving_isNull = queryMap.get("moving_isNull");
		Object moving_isNotNull = queryMap.get("moving_isNotNull");
		Object moving_in = queryMap.get("moving_in");
		Object time_gt = queryMap.get("time_gt");
		Object time_ge = queryMap.get("time_ge");
		Object time_lt = queryMap.get("time_lt");
		Object time_le = queryMap.get("time_le");

		QueryCondition qc = new QueryCondition(HistoryLocationEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.like, id_like));
		}
		if (id_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNull, id_isNull));
		}
		if (id_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.isNotNull, id_isNotNull));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (bodystate != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.eq, bodystate));
		}
		if (bodystate_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.like, bodystate_like));
		}
		if (bodystate_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNull, bodystate_isNull));
		}
		if (bodystate_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.isNotNull, bodystate_isNotNull));
		}
		if (bodystate_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.BODYSTATE,
					QueryCondition.in, bodystate_in));
		}
		if (manualalarm != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.eq,
					manualalarm));
		}
		if (manualalarm_like != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.like,
					manualalarm_like));
		}
		if (manualalarm_isNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.isNull,
					manualalarm_isNull));
		}
		if (manualalarm_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM,
					QueryCondition.isNotNull, manualalarm_isNotNull));
		}
		if (manualalarm_in != null) {
			qc.andCondition(new QueryCondition(
					HistoryLocationEntity.MANUALALARM, QueryCondition.in,
					manualalarm_in));
		}
		if (moving != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.eq, moving));
		}
		if (moving_like != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.like, moving_like));
		}
		if (moving_isNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNull, moving_isNull));
		}
		if (moving_isNotNull != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.isNotNull, moving_isNotNull));
		}
		if (moving_in != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.MOVING,
					QueryCondition.in, moving_in));
		}
		if (time_gt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.gt, time_gt));
		}
		if (time_ge != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.ge, time_ge));
		}
		if (time_lt != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.lt, time_lt));
		}
		if (time_le != null) {
			qc.andCondition(new QueryCondition(HistoryLocationEntity.TIME,
					QueryCondition.le, time_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							HistoryLocationEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							HistoryLocationEntity entity = (HistoryLocationEntity) obj;
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
						HistoryLocationEntity.class, qc);
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
