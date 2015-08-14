package fly.service.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.userLog.UserLogEntity;

import fly.entity.user.UserEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 系统账号服务类
 * @author feng.gu
 * @date 2015-08-14 10:46:14
 * @version V1.0
 * 
 */
public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static UserService userService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static UserService getInstance() {
		if (userService == null) {
			userService = new UserService();
		}
		return userService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(UserEntity user) {
		boolean result = false;
		if (user != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(user);
				if (user.getUserLogList() != null
						&& user.getUserLogList().size() > 0) {
					// 关联信息保存
					for (UserLogEntity userLog : user.getUserLogList()) {
						dbManager.saveNoTransaction(userLog);
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
	 * 批量保存记录
	 * 
	 * @param list
	 */
	public boolean saveList(List<UserEntity> userList) {
		boolean result = false;
		if (userList != null && userList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (UserEntity user : userList) {
					if (user != null) {
						dbManager.saveNoTransaction(user);
						if (user.getUserLogList() != null
								&& user.getUserLogList().size() > 0) {
							// 关联信息保存
							for (UserLogEntity userLog : user.getUserLogList()) {
								dbManager.saveNoTransaction(userLog);
							}
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
	 * @param userLogListShow
	 *            是否查询关联信息
	 * @param obj
	 */
	public UserEntity getById(Integer id, Boolean userLogListShow) {
		UserEntity obj = null;
		if (id != null) {
			obj = (UserEntity) dbManager.getById(id, UserEntity.class);
			// 查询关联内容
			if (userLogListShow != null && userLogListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						UserLogEntity.class, UserLogEntity.USER_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<UserLogEntity> userLogList = new ArrayList<UserLogEntity>();
					for (Object o : list) {
						userLogList.add((UserLogEntity) o);
					}
					obj.setUserLogList(userLogList);
				}
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object password = queryMap.get("password");
		Object password_like = queryMap.get("password_like");
		Object password_isNull = queryMap.get("password_isNull");
		Object password_isNotNull = queryMap.get("password_isNotNull");
		Object permissions = queryMap.get("permissions");
		Object permissions_gt = queryMap.get("permissions_gt");
		Object permissions_ge = queryMap.get("permissions_ge");
		Object permissions_lt = queryMap.get("permissions_lt");
		Object permissions_le = queryMap.get("permissions_le");
		Object permissions_in = queryMap.get("permissions_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object lstlogindate = queryMap.get("lstlogindate");
		Object lstlogindate_like = queryMap.get("lstlogindate_like");
		Object lstlogindate_isNull = queryMap.get("lstlogindate_isNull");
		Object lstlogindate_isNotNull = queryMap.get("lstlogindate_isNotNull");

		QueryCondition qc = new QueryCondition(UserEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (password != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.eq, password));
		}
		if (password_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.like, password_like));
		}
		if (password_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNull, password_isNull));
		}
		if (password_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNotNull, password_isNotNull));
		}
		if (permissions != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.eq, permissions));
		}
		if (permissions_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.gt, permissions_gt));
		}
		if (permissions_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.ge, permissions_ge));
		}
		if (permissions_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.lt, permissions_lt));
		}
		if (permissions_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.le, permissions_le));
		}
		if (permissions_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.in, permissions_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (lstlogindate != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.eq, lstlogindate));
		}
		if (lstlogindate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.like, lstlogindate_like));
		}
		if (lstlogindate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNull, lstlogindate_isNull));
		}
		if (lstlogindate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNotNull, lstlogindate_isNotNull));
		}

		list = dbManager.queryByCondition(UserEntity.class, qc);
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object password = queryMap.get("password");
		Object password_like = queryMap.get("password_like");
		Object password_isNull = queryMap.get("password_isNull");
		Object password_isNotNull = queryMap.get("password_isNotNull");
		Object permissions = queryMap.get("permissions");
		Object permissions_gt = queryMap.get("permissions_gt");
		Object permissions_ge = queryMap.get("permissions_ge");
		Object permissions_lt = queryMap.get("permissions_lt");
		Object permissions_le = queryMap.get("permissions_le");
		Object permissions_in = queryMap.get("permissions_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object lstlogindate = queryMap.get("lstlogindate");
		Object lstlogindate_like = queryMap.get("lstlogindate_like");
		Object lstlogindate_isNull = queryMap.get("lstlogindate_isNull");
		Object lstlogindate_isNotNull = queryMap.get("lstlogindate_isNotNull");

		QueryCondition qc = new QueryCondition(UserEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (password != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.eq, password));
		}
		if (password_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.like, password_like));
		}
		if (password_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNull, password_isNull));
		}
		if (password_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNotNull, password_isNotNull));
		}
		if (permissions != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.eq, permissions));
		}
		if (permissions_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.gt, permissions_gt));
		}
		if (permissions_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.ge, permissions_ge));
		}
		if (permissions_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.lt, permissions_lt));
		}
		if (permissions_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.le, permissions_le));
		}
		if (permissions_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.in, permissions_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (lstlogindate != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.eq, lstlogindate));
		}
		if (lstlogindate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.like, lstlogindate_like));
		}
		if (lstlogindate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNull, lstlogindate_isNull));
		}
		if (lstlogindate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNotNull, lstlogindate_isNotNull));
		}

		pagelist = dbManager.queryByCondition(UserEntity.class, qc, pageno,
				pagesize);
		return pagelist;
	}

	/**
	 * 删除记录
	 * 
	 * @param id
	 *            主键
	 * @param obj
	 */
	public boolean del(Integer id, Boolean delUserLogList) {
		boolean result = false;
		if (id != null && id > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delNoTransaction(id, UserEntity.class);
				// 删除关联信息
				if (delUserLogList != null && delUserLogList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							UserLogEntity.USER_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(UserLogEntity.class,
							qc);
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
	public boolean delList(Map<String, Object> queryMap, Boolean delUserLogList) {
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object password = queryMap.get("password");
		Object password_like = queryMap.get("password_like");
		Object password_isNull = queryMap.get("password_isNull");
		Object password_isNotNull = queryMap.get("password_isNotNull");
		Object permissions = queryMap.get("permissions");
		Object permissions_gt = queryMap.get("permissions_gt");
		Object permissions_ge = queryMap.get("permissions_ge");
		Object permissions_lt = queryMap.get("permissions_lt");
		Object permissions_le = queryMap.get("permissions_le");
		Object permissions_in = queryMap.get("permissions_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object lstlogindate = queryMap.get("lstlogindate");
		Object lstlogindate_like = queryMap.get("lstlogindate_like");
		Object lstlogindate_isNull = queryMap.get("lstlogindate_isNull");
		Object lstlogindate_isNotNull = queryMap.get("lstlogindate_isNotNull");

		QueryCondition qc = new QueryCondition(UserEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (password != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.eq, password));
		}
		if (password_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.like, password_like));
		}
		if (password_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNull, password_isNull));
		}
		if (password_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.PASSWORD,
					QueryCondition.isNotNull, password_isNotNull));
		}
		if (permissions != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.eq, permissions));
		}
		if (permissions_gt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.gt, permissions_gt));
		}
		if (permissions_ge != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.ge, permissions_ge));
		}
		if (permissions_lt != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.lt, permissions_lt));
		}
		if (permissions_le != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.le, permissions_le));
		}
		if (permissions_in != null) {
			qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS,
					QueryCondition.in, permissions_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (lstlogindate != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.eq, lstlogindate));
		}
		if (lstlogindate_like != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.like, lstlogindate_like));
		}
		if (lstlogindate_isNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNull, lstlogindate_isNull));
		}
		if (lstlogindate_isNotNull != null) {
			qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE,
					QueryCondition.isNotNull, lstlogindate_isNotNull));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						UserEntity.class, qc);
				// 删除关联信息
				if (delUserLogList != null && delUserLogList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							UserEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							UserEntity entity = (UserEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								UserLogEntity.USER_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								UserLogEntity.class, qc1);
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
