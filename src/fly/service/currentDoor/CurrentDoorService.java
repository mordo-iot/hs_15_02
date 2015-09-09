package fly.service.currentDoor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.currentDoor.CurrentDoorEntity;
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
 * @Description: 门当前信息服务类
 * @author feng.gu
 * @date 2015-09-09 13:53:50
 * @version V1.0
 * 
 */
public class CurrentDoorService {
	private static Logger logger = Logger.getLogger(CurrentDoorService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static CurrentDoorService currentDoorService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static CurrentDoorService getInstance() {
		if (currentDoorService == null) {
			currentDoorService = new CurrentDoorService();
		}
		return currentDoorService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(CurrentDoorEntity currentDoor) {
		boolean result = false;
		if (currentDoor != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 关联信息保存
				DevEntity dev = currentDoor.getDev();
				if (dev != null) {
					dbManager.saveNoTransaction(dev);
					currentDoor.setDevId(dev.getId());
				}
				result = dbManager.saveNoTransaction(currentDoor);
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
	public boolean saveList(List<CurrentDoorEntity> currentDoorList) {
		boolean result = false;
		if (currentDoorList != null && currentDoorList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (CurrentDoorEntity currentDoor : currentDoorList) {
					if (currentDoor != null) {
						// 关联信息保存
						DevEntity dev = currentDoor.getDev();
						if (dev != null) {
							dbManager.saveNoTransaction(dev);
							currentDoor.setDevId(dev.getId());
						}
						result = dbManager.saveNoTransaction(currentDoor);
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
	public CurrentDoorEntity getById(Integer id, Boolean devShow) {
		CurrentDoorEntity obj = null;
		if (id != null) {
			obj = (CurrentDoorEntity) dbManager.getById(id,
					CurrentDoorEntity.class);
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
		Object openclose = queryMap.get("openclose");
		Object openclose_like = queryMap.get("openclose_like");
		Object openclose_isNull = queryMap.get("openclose_isNull");
		Object openclose_isNotNull = queryMap.get("openclose_isNotNull");
		Object openclose_in = queryMap.get("openclose_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentDoorEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (openclose != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.eq, openclose));
		}
		if (openclose_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.like, openclose_like));
		}
		if (openclose_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNull, openclose_isNull));
		}
		if (openclose_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNotNull, openclose_isNotNull));
		}
		if (openclose_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.in, openclose_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.gt, devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.ge, devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.lt, devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.le, devupdatetime_le));
		}

		list = dbManager.queryByCondition(CurrentDoorEntity.class, qc);
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
		Object openclose = queryMap.get("openclose");
		Object openclose_like = queryMap.get("openclose_like");
		Object openclose_isNull = queryMap.get("openclose_isNull");
		Object openclose_isNotNull = queryMap.get("openclose_isNotNull");
		Object openclose_in = queryMap.get("openclose_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentDoorEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (openclose != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.eq, openclose));
		}
		if (openclose_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.like, openclose_like));
		}
		if (openclose_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNull, openclose_isNull));
		}
		if (openclose_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNotNull, openclose_isNotNull));
		}
		if (openclose_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.in, openclose_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.gt, devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.ge, devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.lt, devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.le, devupdatetime_le));
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
		list = dbManager.queryByConditions(CurrentDoorEntity.class, qc, oc);
		int a = 0;
		if (devShow) {
			a++;
		}
		if (a > 0 && list != null && list.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				CurrentDoorEntity obj = (CurrentDoorEntity) list.get(i);
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
		Object openclose = queryMap.get("openclose");
		Object openclose_like = queryMap.get("openclose_like");
		Object openclose_isNull = queryMap.get("openclose_isNull");
		Object openclose_isNotNull = queryMap.get("openclose_isNotNull");
		Object openclose_in = queryMap.get("openclose_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentDoorEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (openclose != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.eq, openclose));
		}
		if (openclose_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.like, openclose_like));
		}
		if (openclose_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNull, openclose_isNull));
		}
		if (openclose_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNotNull, openclose_isNotNull));
		}
		if (openclose_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.in, openclose_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.gt, devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.ge, devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.lt, devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.le, devupdatetime_le));
		}

		pagelist = dbManager.queryByCondition(CurrentDoorEntity.class, qc,
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
		Object openclose = queryMap.get("openclose");
		Object openclose_like = queryMap.get("openclose_like");
		Object openclose_isNull = queryMap.get("openclose_isNull");
		Object openclose_isNotNull = queryMap.get("openclose_isNotNull");
		Object openclose_in = queryMap.get("openclose_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentDoorEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (openclose != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.eq, openclose));
		}
		if (openclose_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.like, openclose_like));
		}
		if (openclose_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNull, openclose_isNull));
		}
		if (openclose_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNotNull, openclose_isNotNull));
		}
		if (openclose_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.in, openclose_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.gt, devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.ge, devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.lt, devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.le, devupdatetime_le));
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
		pagelist = dbManager.queryByConditions(CurrentDoorEntity.class, qc, oc,
				pageno, pagesize);
		int a = 0;
		if (devShow) {
			a++;
		}
		if (a > 0 && pagelist != null && pagelist.getResultList() != null
				&& pagelist.getResultList().size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < pagelist.getResultList().size(); i++) {
				CurrentDoorEntity obj = (CurrentDoorEntity) pagelist
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
					CurrentDoorEntity currentDoor = (CurrentDoorEntity) dbManager
							.getById(id, CurrentDoorEntity.class);
					if (currentDoor != null && currentDoor.getDevId() != null) {
						dbManager.delNoTransaction(currentDoor.getDevId(),
								DevEntity.class);
					}
				}
				result = dbManager
						.delNoTransaction(id, CurrentDoorEntity.class);
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
		Object openclose = queryMap.get("openclose");
		Object openclose_like = queryMap.get("openclose_like");
		Object openclose_isNull = queryMap.get("openclose_isNull");
		Object openclose_isNotNull = queryMap.get("openclose_isNotNull");
		Object openclose_in = queryMap.get("openclose_in");
		Object level = queryMap.get("level");
		Object level_gt = queryMap.get("level_gt");
		Object level_ge = queryMap.get("level_ge");
		Object level_lt = queryMap.get("level_lt");
		Object level_le = queryMap.get("level_le");
		Object level_in = queryMap.get("level_in");
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

		QueryCondition qc = new QueryCondition(CurrentDoorEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (openclose != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.eq, openclose));
		}
		if (openclose_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.like, openclose_like));
		}
		if (openclose_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNull, openclose_isNull));
		}
		if (openclose_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.isNotNull, openclose_isNotNull));
		}
		if (openclose_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.OPENCLOSE,
					QueryCondition.in, openclose_in));
		}
		if (level != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.eq, level));
		}
		if (level_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.gt, level_gt));
		}
		if (level_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.ge, level_ge));
		}
		if (level_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.lt, level_lt));
		}
		if (level_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.le, level_le));
		}
		if (level_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.LEVEL,
					QueryCondition.in, level_in));
		}
		if (alarmupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.gt,
					alarmupdatetime_gt));
		}
		if (alarmupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.ge,
					alarmupdatetime_ge));
		}
		if (alarmupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.lt,
					alarmupdatetime_lt));
		}
		if (alarmupdatetime_le != null) {
			qc.andCondition(new QueryCondition(
					CurrentDoorEntity.ALARMUPDATETIME, QueryCondition.le,
					alarmupdatetime_le));
		}
		if (normal != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.eq, normal));
		}
		if (normal_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.like, normal_like));
		}
		if (normal_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNull, normal_isNull));
		}
		if (normal_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.isNotNull, normal_isNotNull));
		}
		if (normal_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.NORMAL,
					QueryCondition.in, normal_in));
		}
		if (online != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.eq, online));
		}
		if (online_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.like, online_like));
		}
		if (online_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNull, online_isNull));
		}
		if (online_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.isNotNull, online_isNotNull));
		}
		if (online_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.ONLINE,
					QueryCondition.in, online_in));
		}
		if (power != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.eq, power));
		}
		if (power_like != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.like, power_like));
		}
		if (power_isNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNull, power_isNull));
		}
		if (power_isNotNull != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.isNotNull, power_isNotNull));
		}
		if (power_in != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.POWER,
					QueryCondition.in, power_in));
		}
		if (devupdatetime_gt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.gt, devupdatetime_gt));
		}
		if (devupdatetime_ge != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.ge, devupdatetime_ge));
		}
		if (devupdatetime_lt != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.lt, devupdatetime_lt));
		}
		if (devupdatetime_le != null) {
			qc.andCondition(new QueryCondition(CurrentDoorEntity.DEVUPDATETIME,
					QueryCondition.le, devupdatetime_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							CurrentDoorEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							CurrentDoorEntity entity = (CurrentDoorEntity) obj;
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
						CurrentDoorEntity.class, qc);
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
