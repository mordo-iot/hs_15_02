package fly.service.devPosition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.devPosition.DevPositionEntity;
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
 * @Description: 设备安装位置映射服务类
 * @author feng.gu
 * @date 2015-09-09 13:53:49
 * @version V1.0
 * 
 */
public class DevPositionService {
	private static Logger logger = Logger.getLogger(DevPositionService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static DevPositionService devPositionService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static DevPositionService getInstance() {
		if (devPositionService == null) {
			devPositionService = new DevPositionService();
		}
		return devPositionService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(DevPositionEntity devPosition) {
		boolean result = false;
		if (devPosition != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.saveNoTransaction(devPosition);
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
	public boolean saveList(List<DevPositionEntity> devPositionList) {
		boolean result = false;
		if (devPositionList != null && devPositionList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (DevPositionEntity devPosition : devPositionList) {
					if (devPosition != null) {
						result = dbManager.saveNoTransaction(devPosition);
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
	 * @param obj
	 */
	public DevPositionEntity getById(Integer id) {
		DevPositionEntity obj = null;
		if (id != null) {
			obj = (DevPositionEntity) dbManager.getById(id,
					DevPositionEntity.class);
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
		Object positionId = queryMap.get("positionId");
		Object positionId_gt = queryMap.get("positionId_gt");
		Object positionId_ge = queryMap.get("positionId_ge");
		Object positionId_lt = queryMap.get("positionId_lt");
		Object positionId_le = queryMap.get("positionId_le");
		Object positionId_in = queryMap.get("positionId_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");

		QueryCondition qc = new QueryCondition(DevPositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.eq, positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.gt, positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.ge, positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.lt, positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.le, positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.in, positionId_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}

		list = dbManager.queryByCondition(DevPositionEntity.class, qc);
		return list;
	}

	/**
	 * 根据条件查询记录集合（不分页 带排序 带级联查询）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param orderList
	 *            排序条件集合
	 * @return
	 */
	public List<Object> getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList) {
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
		Object positionId = queryMap.get("positionId");
		Object positionId_gt = queryMap.get("positionId_gt");
		Object positionId_ge = queryMap.get("positionId_ge");
		Object positionId_lt = queryMap.get("positionId_lt");
		Object positionId_le = queryMap.get("positionId_le");
		Object positionId_in = queryMap.get("positionId_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");

		QueryCondition qc = new QueryCondition(DevPositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.eq, positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.gt, positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.ge, positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.lt, positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.le, positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.in, positionId_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
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
		list = dbManager.queryByConditions(DevPositionEntity.class, qc, oc);
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
		Object positionId = queryMap.get("positionId");
		Object positionId_gt = queryMap.get("positionId_gt");
		Object positionId_ge = queryMap.get("positionId_ge");
		Object positionId_lt = queryMap.get("positionId_lt");
		Object positionId_le = queryMap.get("positionId_le");
		Object positionId_in = queryMap.get("positionId_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");

		QueryCondition qc = new QueryCondition(DevPositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.eq, positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.gt, positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.ge, positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.lt, positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.le, positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.in, positionId_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}

		pagelist = dbManager.queryByCondition(DevPositionEntity.class, qc,
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
	 * @return
	 */
	public PageList getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList, int pageno, int pagesize) {
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
		Object positionId = queryMap.get("positionId");
		Object positionId_gt = queryMap.get("positionId_gt");
		Object positionId_ge = queryMap.get("positionId_ge");
		Object positionId_lt = queryMap.get("positionId_lt");
		Object positionId_le = queryMap.get("positionId_le");
		Object positionId_in = queryMap.get("positionId_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");

		QueryCondition qc = new QueryCondition(DevPositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.eq, positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.gt, positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.ge, positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.lt, positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.le, positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.in, positionId_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
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
		pagelist = dbManager.queryByConditions(DevPositionEntity.class, qc, oc,
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
	public boolean del(Integer id) {
		boolean result = false;
		if (id != null && id > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager
						.delNoTransaction(id, DevPositionEntity.class);
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
	public boolean delList(Map<String, Object> queryMap) {
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
		Object positionId = queryMap.get("positionId");
		Object positionId_gt = queryMap.get("positionId_gt");
		Object positionId_ge = queryMap.get("positionId_ge");
		Object positionId_lt = queryMap.get("positionId_lt");
		Object positionId_le = queryMap.get("positionId_le");
		Object positionId_in = queryMap.get("positionId_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");

		QueryCondition qc = new QueryCondition(DevPositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.eq, positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.gt, positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.ge, positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.lt, positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.le, positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.POSITION_ID,
					QueryCondition.in, positionId_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevPositionEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						DevPositionEntity.class, qc);
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
