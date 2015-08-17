package fly.service.position;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.devPosition.DevPositionEntity;
import fly.entity.position.PositionEntity;

import fly.entity.position.PositionEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;

/**
 * @Title: Service
 * @Description: 位置信息服务类
 * @author feng.gu
 * @date 2015-08-17 09:50:49
 * @version V1.0
 * 
 */
public class PositionService {
	private static Logger logger = Logger.getLogger(PositionService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static PositionService positionService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static PositionService getInstance() {
		if (positionService == null) {
			positionService = new PositionService();
		}
		return positionService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(PositionEntity position) {
		boolean result = false;
		if (position != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(position);
				if (position.getDevList() != null
						&& position.getDevList().size() > 0) {
					// 先删除原先绑定关系
					QueryCondition qc = new QueryCondition(
							DevPositionEntity.POSITION_ID, QueryCondition.eq,
							position.getId());
					List<Object> list = dbManager.queryByCondition(
							DevPositionEntity.class, qc);
					if ((list != null) && (list.size() > 0)) {
						for (Object obj : list) {
							DevPositionEntity entity = (DevPositionEntity) obj;
							dbManager.delNoTransaction(entity.getId(),
									DevPositionEntity.class);
						}
					}
					// 绑定现在的关系
					for (DevEntity dev : position.getDevList()) {
						Integer devId = dev.getId();
						if (devId != null) {
							DevPositionEntity entity = new DevPositionEntity();
							entity.setPositionId(position.getId());
							entity.setDevId(devId);
							dbManager.saveNoTransaction(entity);
						}
					}
				}
				// 关联信息保存
				if (position.getPosition() != null) {
					dbManager.saveNoTransaction(position.getPosition());
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
	public boolean saveList(List<PositionEntity> positionList) {
		boolean result = false;
		if (positionList != null && positionList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (PositionEntity position : positionList) {
					if (position != null) {
						dbManager.saveNoTransaction(position);
						if (position.getDevList() != null
								&& position.getDevList().size() > 0) {
							// 先删除原先绑定关系
							QueryCondition qc = new QueryCondition(
									DevPositionEntity.POSITION_ID,
									QueryCondition.eq, position.getId());
							List<Object> list = dbManager.queryByCondition(
									DevPositionEntity.class, qc);
							if ((list != null) && (list.size() > 0)) {
								for (Object obj : list) {
									DevPositionEntity entity = (DevPositionEntity) obj;
									dbManager.delNoTransaction(entity.getId(),
											DevPositionEntity.class);
								}
							}
							// 绑定现在的关系
							for (DevEntity dev : position.getDevList()) {
								Integer devId = dev.getId();
								if (devId != null) {
									DevPositionEntity entity = new DevPositionEntity();
									entity.setPositionId(position.getId());
									entity.setDevId(devId);
									dbManager.saveNoTransaction(entity);
								}
							}
						}
						// 关联信息保存
						if (position.getPosition() != null) {
							dbManager.saveNoTransaction(position.getPosition());
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
	 * @param devListShow
	 *            是否查询关联信息
	 * @param positionShow
	 *            是否查询关联信息
	 * @param obj
	 */
	public PositionEntity getById(Integer id, Boolean devListShow,
			Boolean positionShow) {
		PositionEntity obj = null;
		if (id != null) {
			obj = (PositionEntity) dbManager.getById(id, PositionEntity.class);
			// 查询关联内容
			if (devListShow != null && devListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> rlist = dbManager.searchListByColumn(
						DevPositionEntity.class, DevPositionEntity.POSITION_ID,
						String.valueOf(id));
				if (rlist != null && rlist.size() > 0) {
					for (Object robj : rlist) {
						DevPositionEntity robject = (DevPositionEntity) robj;
						Integer devId = robject.getDevId();
						if (devId != null) {
							DevEntity dev = (DevEntity) dbManager.getById(
									devId, DevEntity.class);
							List<DevEntity> devList = obj.getDevList();
							if (devList == null || devList.size() == 0) {
								devList = new ArrayList<DevEntity>();
							}
							devList.add(dev);
							obj.setDevList(devList);
						}
					}
				}
			}
			// 查询关联内容
			if (positionShow != null && positionShow.booleanValue()
					&& obj != null && obj.getParentId() > 0) {
				PositionEntity position = (PositionEntity) dbManager.getById(
						obj.getParentId(), PositionEntity.class);
				obj.setPosition(position);
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
		Object name_in = queryMap.get("name_in");
		Object photo = queryMap.get("photo");
		Object photo_like = queryMap.get("photo_like");
		Object photo_isNull = queryMap.get("photo_isNull");
		Object photo_isNotNull = queryMap.get("photo_isNotNull");
		Object photo_in = queryMap.get("photo_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object createdate_in = queryMap.get("createdate_in");
		Object updatedate = queryMap.get("updatedate");
		Object updatedate_like = queryMap.get("updatedate_like");
		Object updatedate_isNull = queryMap.get("updatedate_isNull");
		Object updatedate_isNotNull = queryMap.get("updatedate_isNotNull");
		Object updatedate_in = queryMap.get("updatedate_in");

		Object devId = queryMap.get("devId");

		QueryCondition qc = new QueryCondition(PositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (photo != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.eq, photo));
		}
		if (photo_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.like, photo_like));
		}
		if (photo_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNull, photo_isNull));
		}
		if (photo_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNotNull, photo_isNotNull));
		}
		if (photo_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.in, photo_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (createdate_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.in, createdate_in));
		}
		if (updatedate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.eq, updatedate));
		}
		if (updatedate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.like, updatedate_like));
		}
		if (updatedate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNull, updatedate_isNull));
		}
		if (updatedate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNotNull, updatedate_isNotNull));
		}
		if (updatedate_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.in, updatedate_in));
		}

		if (devId != null) {
			QueryCondition qc1 = new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getPositionId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(PositionEntity.ID,
							QueryCondition.in, strIds));
				}
			}
		}
		list = dbManager.queryByCondition(PositionEntity.class, qc);
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
		Object name_in = queryMap.get("name_in");
		Object photo = queryMap.get("photo");
		Object photo_like = queryMap.get("photo_like");
		Object photo_isNull = queryMap.get("photo_isNull");
		Object photo_isNotNull = queryMap.get("photo_isNotNull");
		Object photo_in = queryMap.get("photo_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object createdate_in = queryMap.get("createdate_in");
		Object updatedate = queryMap.get("updatedate");
		Object updatedate_like = queryMap.get("updatedate_like");
		Object updatedate_isNull = queryMap.get("updatedate_isNull");
		Object updatedate_isNotNull = queryMap.get("updatedate_isNotNull");
		Object updatedate_in = queryMap.get("updatedate_in");

		Object devId = queryMap.get("devId");

		QueryCondition qc = new QueryCondition(PositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (photo != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.eq, photo));
		}
		if (photo_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.like, photo_like));
		}
		if (photo_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNull, photo_isNull));
		}
		if (photo_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNotNull, photo_isNotNull));
		}
		if (photo_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.in, photo_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (createdate_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.in, createdate_in));
		}
		if (updatedate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.eq, updatedate));
		}
		if (updatedate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.like, updatedate_like));
		}
		if (updatedate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNull, updatedate_isNull));
		}
		if (updatedate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNotNull, updatedate_isNotNull));
		}
		if (updatedate_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.in, updatedate_in));
		}

		if (devId != null) {
			QueryCondition qc1 = new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getPositionId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(PositionEntity.ID,
							QueryCondition.in, strIds));
				}
			}
		}
		pagelist = dbManager.queryByCondition(PositionEntity.class, qc, pageno,
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
	public boolean del(Integer id, Boolean delDevPositionList,
			Boolean delPosition) {
		boolean result = false;
		if (id != null && id > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delNoTransaction(id, PositionEntity.class);
				// 删除关联信息
				if (delDevPositionList != null
						&& delDevPositionList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							DevPositionEntity.POSITION_ID, QueryCondition.eq,
							id);
					dbManager.delByConditionsNoTransaction(
							DevPositionEntity.class, qc);
				}
				// 删除关联信息
				if (delPosition != null && delPosition.booleanValue()) {
					PositionEntity position = (PositionEntity) dbManager
							.getById(id, PositionEntity.class);
					if (position.getParentId() != null) {
						dbManager.delNoTransaction(position.getParentId(),
								PositionEntity.class);
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
	public boolean delList(Map<String, Object> queryMap,
			Boolean delDevPositionList, Boolean delPositionList) {
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
		Object photo = queryMap.get("photo");
		Object photo_like = queryMap.get("photo_like");
		Object photo_isNull = queryMap.get("photo_isNull");
		Object photo_isNotNull = queryMap.get("photo_isNotNull");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object createdate = queryMap.get("createdate");
		Object createdate_like = queryMap.get("createdate_like");
		Object createdate_isNull = queryMap.get("createdate_isNull");
		Object createdate_isNotNull = queryMap.get("createdate_isNotNull");
		Object updatedate = queryMap.get("updatedate");
		Object updatedate_like = queryMap.get("updatedate_like");
		Object updatedate_isNull = queryMap.get("updatedate_isNull");
		Object updatedate_isNotNull = queryMap.get("updatedate_isNotNull");
		Object devId = queryMap.get("devId");

		QueryCondition qc = new QueryCondition(PositionEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (photo != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.eq, photo));
		}
		if (photo_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.like, photo_like));
		}
		if (photo_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNull, photo_isNull));
		}
		if (photo_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PHOTO,
					QueryCondition.isNotNull, photo_isNotNull));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(PositionEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (createdate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.eq, createdate));
		}
		if (createdate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.like, createdate_like));
		}
		if (createdate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNull, createdate_isNull));
		}
		if (createdate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.CREATEDATE,
					QueryCondition.isNotNull, createdate_isNotNull));
		}
		if (updatedate != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.eq, updatedate));
		}
		if (updatedate_like != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.like, updatedate_like));
		}
		if (updatedate_isNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNull, updatedate_isNull));
		}
		if (updatedate_isNotNull != null) {
			qc.andCondition(new QueryCondition(PositionEntity.UPDATEDATE,
					QueryCondition.isNotNull, updatedate_isNotNull));
		}

		if (devId != null) {
			QueryCondition qc1 = new QueryCondition(DevPositionEntity.DEV_ID,
					QueryCondition.eq, devId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getPositionId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(PositionEntity.ID,
							QueryCondition.in, strIds));
				}
			}
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						PositionEntity.class, qc);
				// 删除关联信息
				if (delDevPositionList != null
						&& delDevPositionList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							PositionEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							PositionEntity entity = (PositionEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								DevPositionEntity.POSITION_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								DevPositionEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delPositionList != null && delPositionList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							PositionEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							PositionEntity entity = (PositionEntity) obj;
							strIds += entity.getParentId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								PositionEntity.ID, QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								PositionEntity.class, qc1);
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
