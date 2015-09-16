package fly.service.alarmAll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;

import fly.entity.alarmAll.AlarmAllEntity;
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
 * @Description: 当天和历史报警信息服务类
 * @author feng.gu
 * @date 2015-09-16 15:25:18
 * @version V1.0
 * 
 */
public class AlarmAllService {
	private static Logger logger = Logger.getLogger(AlarmAllService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static AlarmAllService alarmAllService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static AlarmAllService getInstance() {
		if (alarmAllService == null) {
			alarmAllService = new AlarmAllService();
		}
		return alarmAllService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(AlarmAllEntity alarmAll) {
		boolean result = false;
		if (alarmAll != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 关联信息保存
				DevEntity dev = alarmAll.getDev();
				if (dev != null) {
					dbManager.saveNoTransaction(dev);
					alarmAll.setDevId(dev.getId());
				}
				result = dbManager.saveNoTransaction(alarmAll);
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
	public boolean saveList(List<AlarmAllEntity> alarmAllList) {
		boolean result = false;
		if (alarmAllList != null && alarmAllList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (AlarmAllEntity alarmAll : alarmAllList) {
					if (alarmAll != null) {
						// 关联信息保存
						DevEntity dev = alarmAll.getDev();
						if (dev != null) {
							dbManager.saveNoTransaction(dev);
							alarmAll.setDevId(dev.getId());
						}
						result = dbManager.saveNoTransaction(alarmAll);
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
	public AlarmAllEntity getById(Integer id, Boolean devShow) {
		AlarmAllEntity obj = null;
		if (id != null) {
			obj = (AlarmAllEntity) dbManager.getById(id, AlarmAllEntity.class);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object content = queryMap.get("content");
		Object content_like = queryMap.get("content_like");
		Object content_isNull = queryMap.get("content_isNull");
		Object content_isNotNull = queryMap.get("content_isNotNull");
		Object content_in = queryMap.get("content_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object handlestate = queryMap.get("handlestate");
		Object handlestate_gt = queryMap.get("handlestate_gt");
		Object handlestate_ge = queryMap.get("handlestate_ge");
		Object handlestate_lt = queryMap.get("handlestate_lt");
		Object handlestate_le = queryMap.get("handlestate_le");
		Object handlestate_in = queryMap.get("handlestate_in");
		Object handledesc = queryMap.get("handledesc");
		Object handledesc_like = queryMap.get("handledesc_like");
		Object handledesc_isNull = queryMap.get("handledesc_isNull");
		Object handledesc_isNotNull = queryMap.get("handledesc_isNotNull");
		Object handledesc_in = queryMap.get("handledesc_in");
		Object handledate_gt = queryMap.get("handledate_gt");
		Object handledate_ge = queryMap.get("handledate_ge");
		Object handledate_lt = queryMap.get("handledate_lt");
		Object handledate_le = queryMap.get("handledate_le");

		QueryCondition qc = new QueryCondition(AlarmAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (content_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.in, content_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.eq, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledesc_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.in, handledesc_in));
		}
		if (handledate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.gt, handledate_gt));
		}
		if (handledate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.ge, handledate_ge));
		}
		if (handledate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.lt, handledate_lt));
		}
		if (handledate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.le, handledate_le));
		}

		list = dbManager.queryByCondition(AlarmAllEntity.class, qc);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object content = queryMap.get("content");
		Object content_like = queryMap.get("content_like");
		Object content_isNull = queryMap.get("content_isNull");
		Object content_isNotNull = queryMap.get("content_isNotNull");
		Object content_in = queryMap.get("content_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object handlestate = queryMap.get("handlestate");
		Object handlestate_gt = queryMap.get("handlestate_gt");
		Object handlestate_ge = queryMap.get("handlestate_ge");
		Object handlestate_lt = queryMap.get("handlestate_lt");
		Object handlestate_le = queryMap.get("handlestate_le");
		Object handlestate_in = queryMap.get("handlestate_in");
		Object handledesc = queryMap.get("handledesc");
		Object handledesc_like = queryMap.get("handledesc_like");
		Object handledesc_isNull = queryMap.get("handledesc_isNull");
		Object handledesc_isNotNull = queryMap.get("handledesc_isNotNull");
		Object handledesc_in = queryMap.get("handledesc_in");
		Object handledate_gt = queryMap.get("handledate_gt");
		Object handledate_ge = queryMap.get("handledate_ge");
		Object handledate_lt = queryMap.get("handledate_lt");
		Object handledate_le = queryMap.get("handledate_le");

		QueryCondition qc = new QueryCondition(AlarmAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (content_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.in, content_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.eq, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledesc_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.in, handledesc_in));
		}
		if (handledate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.gt, handledate_gt));
		}
		if (handledate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.ge, handledate_ge));
		}
		if (handledate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.lt, handledate_lt));
		}
		if (handledate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.le, handledate_le));
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
		list = dbManager.queryByConditions(AlarmAllEntity.class, qc, oc);
		int a = 0;
		if (devShow) {
			a++;
		}
		if (a > 0 && list != null && list.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				AlarmAllEntity obj = (AlarmAllEntity) list.get(i);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object content = queryMap.get("content");
		Object content_like = queryMap.get("content_like");
		Object content_isNull = queryMap.get("content_isNull");
		Object content_isNotNull = queryMap.get("content_isNotNull");
		Object content_in = queryMap.get("content_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object handlestate = queryMap.get("handlestate");
		Object handlestate_gt = queryMap.get("handlestate_gt");
		Object handlestate_ge = queryMap.get("handlestate_ge");
		Object handlestate_lt = queryMap.get("handlestate_lt");
		Object handlestate_le = queryMap.get("handlestate_le");
		Object handlestate_in = queryMap.get("handlestate_in");
		Object handledesc = queryMap.get("handledesc");
		Object handledesc_like = queryMap.get("handledesc_like");
		Object handledesc_isNull = queryMap.get("handledesc_isNull");
		Object handledesc_isNotNull = queryMap.get("handledesc_isNotNull");
		Object handledesc_in = queryMap.get("handledesc_in");
		Object handledate_gt = queryMap.get("handledate_gt");
		Object handledate_ge = queryMap.get("handledate_ge");
		Object handledate_lt = queryMap.get("handledate_lt");
		Object handledate_le = queryMap.get("handledate_le");

		QueryCondition qc = new QueryCondition(AlarmAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (content_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.in, content_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.eq, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledesc_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.in, handledesc_in));
		}
		if (handledate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.gt, handledate_gt));
		}
		if (handledate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.ge, handledate_ge));
		}
		if (handledate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.lt, handledate_lt));
		}
		if (handledate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.le, handledate_le));
		}

		pagelist = dbManager.queryByCondition(AlarmAllEntity.class, qc, pageno,
				pagesize);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object content = queryMap.get("content");
		Object content_like = queryMap.get("content_like");
		Object content_isNull = queryMap.get("content_isNull");
		Object content_isNotNull = queryMap.get("content_isNotNull");
		Object content_in = queryMap.get("content_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object handlestate = queryMap.get("handlestate");
		Object handlestate_gt = queryMap.get("handlestate_gt");
		Object handlestate_ge = queryMap.get("handlestate_ge");
		Object handlestate_lt = queryMap.get("handlestate_lt");
		Object handlestate_le = queryMap.get("handlestate_le");
		Object handlestate_in = queryMap.get("handlestate_in");
		Object handledesc = queryMap.get("handledesc");
		Object handledesc_like = queryMap.get("handledesc_like");
		Object handledesc_isNull = queryMap.get("handledesc_isNull");
		Object handledesc_isNotNull = queryMap.get("handledesc_isNotNull");
		Object handledesc_in = queryMap.get("handledesc_in");
		Object handledate_gt = queryMap.get("handledate_gt");
		Object handledate_ge = queryMap.get("handledate_ge");
		Object handledate_lt = queryMap.get("handledate_lt");
		Object handledate_le = queryMap.get("handledate_le");

		QueryCondition qc = new QueryCondition(AlarmAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (content_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.in, content_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.eq, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledesc_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.in, handledesc_in));
		}
		if (handledate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.gt, handledate_gt));
		}
		if (handledate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.ge, handledate_ge));
		}
		if (handledate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.lt, handledate_lt));
		}
		if (handledate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.le, handledate_le));
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
		pagelist = dbManager.queryByConditions(AlarmAllEntity.class, qc, oc,
				pageno, pagesize);
		int a = 0;
		if (devShow) {
			a++;
		}
		if (a > 0 && pagelist != null && pagelist.getResultList() != null
				&& pagelist.getResultList().size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < pagelist.getResultList().size(); i++) {
				AlarmAllEntity obj = (AlarmAllEntity) pagelist.getResultList()
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
					AlarmAllEntity alarmAll = (AlarmAllEntity) dbManager
							.getById(id, AlarmAllEntity.class);
					if (alarmAll != null && alarmAll.getDevId() != null) {
						dbManager.delNoTransaction(alarmAll.getDevId(),
								DevEntity.class);
					}
				}
				result = dbManager.delNoTransaction(id, AlarmAllEntity.class);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object devId = queryMap.get("devId");
		Object devId_gt = queryMap.get("devId_gt");
		Object devId_ge = queryMap.get("devId_ge");
		Object devId_lt = queryMap.get("devId_lt");
		Object devId_le = queryMap.get("devId_le");
		Object devId_in = queryMap.get("devId_in");
		Object content = queryMap.get("content");
		Object content_like = queryMap.get("content_like");
		Object content_isNull = queryMap.get("content_isNull");
		Object content_isNotNull = queryMap.get("content_isNotNull");
		Object content_in = queryMap.get("content_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object handlestate = queryMap.get("handlestate");
		Object handlestate_gt = queryMap.get("handlestate_gt");
		Object handlestate_ge = queryMap.get("handlestate_ge");
		Object handlestate_lt = queryMap.get("handlestate_lt");
		Object handlestate_le = queryMap.get("handlestate_le");
		Object handlestate_in = queryMap.get("handlestate_in");
		Object handledesc = queryMap.get("handledesc");
		Object handledesc_like = queryMap.get("handledesc_like");
		Object handledesc_isNull = queryMap.get("handledesc_isNull");
		Object handledesc_isNotNull = queryMap.get("handledesc_isNotNull");
		Object handledesc_in = queryMap.get("handledesc_in");
		Object handledate_gt = queryMap.get("handledate_gt");
		Object handledate_ge = queryMap.get("handledate_ge");
		Object handledate_lt = queryMap.get("handledate_lt");
		Object handledate_le = queryMap.get("handledate_le");

		QueryCondition qc = new QueryCondition(AlarmAllEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (content_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CONTENT,
					QueryCondition.in, content_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.eq, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledesc_in != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDESC,
					QueryCondition.in, handledesc_in));
		}
		if (handledate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.gt, handledate_gt));
		}
		if (handledate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.ge, handledate_ge));
		}
		if (handledate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.lt, handledate_lt));
		}
		if (handledate_le != null) {
			qc.andCondition(new QueryCondition(AlarmAllEntity.HANDLEDATE,
					QueryCondition.le, handledate_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							AlarmAllEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							AlarmAllEntity entity = (AlarmAllEntity) obj;
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
						AlarmAllEntity.class, qc);
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
