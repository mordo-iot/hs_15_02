package fly.service.alarmCurrent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.alarmCurrent.AlarmCurrentEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 当前报警信息服务类
 * @author feng.gu
 * @date 2015-08-10 09:53:31
 * @version V1.0
 * 
 */
public class AlarmCurrentService {
	private static Logger logger = Logger.getLogger(AlarmCurrentService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static AlarmCurrentService alarmCurrentService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static AlarmCurrentService getInstance() {
		if (alarmCurrentService == null) {
			alarmCurrentService = new AlarmCurrentService();
		}
		return alarmCurrentService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(AlarmCurrentEntity alarmCurrent) {
		boolean result = false;
		if (alarmCurrent != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(alarmCurrent);
				// 关联信息保存
				if (alarmCurrent.getDev() != null) {
					dbManager.saveNoTransaction(alarmCurrent.getDev());
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
	public boolean saveList(List<AlarmCurrentEntity> alarmCurrentList) {
		boolean result = false;
		if (alarmCurrentList != null && alarmCurrentList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (AlarmCurrentEntity alarmCurrent : alarmCurrentList) {
					if (alarmCurrent != null) {
						dbManager.saveNoTransaction(alarmCurrent);
						// 关联信息保存
						if (alarmCurrent.getDev() != null) {
							dbManager.saveNoTransaction(alarmCurrent.getDev());
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
	public AlarmCurrentEntity getById(Integer id, Boolean devShow) {
		AlarmCurrentEntity obj = null;
		if (id != null) {
			obj = (AlarmCurrentEntity) dbManager.getById(id,
					AlarmCurrentEntity.class);
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
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
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
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
		Object handledate = queryMap.get("handledate");
		Object handledate_like = queryMap.get("handledate_like");
		Object handledate_isNull = queryMap.get("handledate_isNull");
		Object handledate_isNotNull = queryMap.get("handledate_isNotNull");

		QueryCondition qc = new QueryCondition(AlarmCurrentEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.in, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.eq, handledate));
		}
		if (handledate_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.like, handledate_like));
		}
		if (handledate_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.isNull, handledate_isNull));
		}
		if (handledate_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.isNotNull, handledate_isNotNull));
		}

		pagelist = dbManager.queryByCondition(AlarmCurrentEntity.class, qc,
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
						AlarmCurrentEntity.class);
				// 删除关联信息
				if (delDev != null && delDev.booleanValue()) {
					AlarmCurrentEntity alarmCurrent = (AlarmCurrentEntity) dbManager
							.getById(id, AlarmCurrentEntity.class);
					if (alarmCurrent.getDevId() != null) {
						dbManager.delNoTransaction(alarmCurrent.getDevId(),
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
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
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
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
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
		Object handledate = queryMap.get("handledate");
		Object handledate_like = queryMap.get("handledate_like");
		Object handledate_isNull = queryMap.get("handledate_isNull");
		Object handledate_isNotNull = queryMap.get("handledate_isNotNull");

		QueryCondition qc = new QueryCondition(AlarmCurrentEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.in, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.ID,
					QueryCondition.in, id_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.in, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (content != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.eq, content));
		}
		if (content_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.like, content_like));
		}
		if (content_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.isNull, content_isNull));
		}
		if (content_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CONTENT,
					QueryCondition.isNotNull, content_isNotNull));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (handlestate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.in, handlestate));
		}
		if (handlestate_gt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.gt, handlestate_gt));
		}
		if (handlestate_ge != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.ge, handlestate_ge));
		}
		if (handlestate_lt != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.lt, handlestate_lt));
		}
		if (handlestate_le != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.le, handlestate_le));
		}
		if (handlestate_in != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLESTATE,
					QueryCondition.in, handlestate_in));
		}
		if (handledesc != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.eq, handledesc));
		}
		if (handledesc_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.like, handledesc_like));
		}
		if (handledesc_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.isNull, handledesc_isNull));
		}
		if (handledesc_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDESC,
					QueryCondition.isNotNull, handledesc_isNotNull));
		}
		if (handledate != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.eq, handledate));
		}
		if (handledate_like != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.like, handledate_like));
		}
		if (handledate_isNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.isNull, handledate_isNull));
		}
		if (handledate_isNotNull != null) {
			qc.andCondition(new QueryCondition(AlarmCurrentEntity.HANDLEDATE,
					QueryCondition.isNotNull, handledate_isNotNull));
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						AlarmCurrentEntity.class, qc);
				// 删除关联信息
				if (delDevList != null && delDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							AlarmCurrentEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							AlarmCurrentEntity entity = (AlarmCurrentEntity) obj;
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