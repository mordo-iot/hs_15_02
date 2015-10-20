package fly.service.devLeftJionMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.devLeftJionMap.DevLeftJionMapEntity;
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
 * @Description: 设备左连接位置mapping表服务类
 * @author feng.gu
 * @date 2015-10-20 16:54:59
 * @version V1.0
 * 
 */
public class DevLeftJionMapService {
	private static Logger logger = Logger
			.getLogger(DevLeftJionMapService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static DevLeftJionMapService devLeftJionMapService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static DevLeftJionMapService getInstance() {
		if (devLeftJionMapService == null) {
			devLeftJionMapService = new DevLeftJionMapService();
		}
		return devLeftJionMapService;
	}

	/**
	 * 保存记录
	 * 
	 * @param obj
	 */
	public boolean save(DevLeftJionMapEntity devLeftJionMap) {
		boolean result = false;
		if (devLeftJionMap != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.saveNoTransaction(devLeftJionMap);
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
	public boolean saveList(List<DevLeftJionMapEntity> devLeftJionMapList) {
		boolean result = false;
		if (devLeftJionMapList != null && devLeftJionMapList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (DevLeftJionMapEntity devLeftJionMap : devLeftJionMapList) {
					if (devLeftJionMap != null) {
						result = dbManager.saveNoTransaction(devLeftJionMap);
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
	public DevLeftJionMapEntity getById(Integer id) {
		DevLeftJionMapEntity obj = null;
		if (id != null) {
			obj = (DevLeftJionMapEntity) dbManager.getById(id,
					DevLeftJionMapEntity.class);
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object name_in = queryMap.get("name_in");
		Object type = queryMap.get("type");
		Object type_like = queryMap.get("type_like");
		Object type_isNull = queryMap.get("type_isNull");
		Object type_isNotNull = queryMap.get("type_isNotNull");
		Object type_in = queryMap.get("type_in");
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object alarmcontent = queryMap.get("alarmcontent");
		Object alarmcontent_like = queryMap.get("alarmcontent_like");
		Object alarmcontent_isNull = queryMap.get("alarmcontent_isNull");
		Object alarmcontent_isNotNull = queryMap.get("alarmcontent_isNotNull");
		Object alarmcontent_in = queryMap.get("alarmcontent_in");
		Object alarmdevid = queryMap.get("alarmdevid");
		Object alarmdevid_like = queryMap.get("alarmdevid_like");
		Object alarmdevid_isNull = queryMap.get("alarmdevid_isNull");
		Object alarmdevid_isNotNull = queryMap.get("alarmdevid_isNotNull");
		Object alarmdevid_in = queryMap.get("alarmdevid_in");
		Object lightno = queryMap.get("lightno");
		Object lightno_gt = queryMap.get("lightno_gt");
		Object lightno_ge = queryMap.get("lightno_ge");
		Object lightno_lt = queryMap.get("lightno_lt");
		Object lightno_le = queryMap.get("lightno_le");
		Object lightno_in = queryMap.get("lightno_in");
		Object lightdevid = queryMap.get("lightdevid");
		Object lightdevid_like = queryMap.get("lightdevid_like");
		Object lightdevid_isNull = queryMap.get("lightdevid_isNull");
		Object lightdevid_isNotNull = queryMap.get("lightdevid_isNotNull");
		Object lightdevid_in = queryMap.get("lightdevid_in");
		Object alarmphone = queryMap.get("alarmphone");
		Object alarmphone_like = queryMap.get("alarmphone_like");
		Object alarmphone_isNull = queryMap.get("alarmphone_isNull");
		Object alarmphone_isNotNull = queryMap.get("alarmphone_isNotNull");
		Object alarmphone_in = queryMap.get("alarmphone_in");
		Object emitid = queryMap.get("emitid");
		Object emitid_gt = queryMap.get("emitid_gt");
		Object emitid_ge = queryMap.get("emitid_ge");
		Object emitid_lt = queryMap.get("emitid_lt");
		Object emitid_le = queryMap.get("emitid_le");
		Object emitid_in = queryMap.get("emitid_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object attribute = queryMap.get("attribute");
		Object attribute_like = queryMap.get("attribute_like");
		Object attribute_isNull = queryMap.get("attribute_isNull");
		Object attribute_isNotNull = queryMap.get("attribute_isNotNull");
		Object attribute_in = queryMap.get("attribute_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object updatedate_gt = queryMap.get("updatedate_gt");
		Object updatedate_ge = queryMap.get("updatedate_ge");
		Object updatedate_lt = queryMap.get("updatedate_lt");
		Object updatedate_le = queryMap.get("updatedate_le");
		Object id1 = queryMap.get("id1");
		Object id1_gt = queryMap.get("id1_gt");
		Object id1_ge = queryMap.get("id1_ge");
		Object id1_lt = queryMap.get("id1_lt");
		Object id1_le = queryMap.get("id1_le");
		Object id1_in = queryMap.get("id1_in");
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
		Object createdate1_gt = queryMap.get("createdate1_gt");
		Object createdate1_ge = queryMap.get("createdate1_ge");
		Object createdate1_lt = queryMap.get("createdate1_lt");
		Object createdate1_le = queryMap.get("createdate1_le");

		QueryCondition qc = new QueryCondition(DevLeftJionMapEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.eq,
					alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.like,
					alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.isNull,
					alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.in,
					alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}
		if (id1 != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.eq, id1));
		}
		if (id1_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.gt, id1_gt));
		}
		if (id1_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.ge, id1_ge));
		}
		if (id1_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.lt, id1_lt));
		}
		if (id1_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.le, id1_le));
		}
		if (id1_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.in, id1_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.eq,
					positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.gt,
					positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.ge,
					positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.lt,
					positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.le,
					positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.in,
					positionId_in));
		}
		if (createdate1_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.gt,
					createdate1_gt));
		}
		if (createdate1_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.ge,
					createdate1_ge));
		}
		if (createdate1_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.lt,
					createdate1_lt));
		}
		if (createdate1_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.le,
					createdate1_le));
		}

		list = dbManager.queryByCondition(DevLeftJionMapEntity.class, qc);
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object name_in = queryMap.get("name_in");
		Object type = queryMap.get("type");
		Object type_like = queryMap.get("type_like");
		Object type_isNull = queryMap.get("type_isNull");
		Object type_isNotNull = queryMap.get("type_isNotNull");
		Object type_in = queryMap.get("type_in");
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object alarmcontent = queryMap.get("alarmcontent");
		Object alarmcontent_like = queryMap.get("alarmcontent_like");
		Object alarmcontent_isNull = queryMap.get("alarmcontent_isNull");
		Object alarmcontent_isNotNull = queryMap.get("alarmcontent_isNotNull");
		Object alarmcontent_in = queryMap.get("alarmcontent_in");
		Object alarmdevid = queryMap.get("alarmdevid");
		Object alarmdevid_like = queryMap.get("alarmdevid_like");
		Object alarmdevid_isNull = queryMap.get("alarmdevid_isNull");
		Object alarmdevid_isNotNull = queryMap.get("alarmdevid_isNotNull");
		Object alarmdevid_in = queryMap.get("alarmdevid_in");
		Object lightno = queryMap.get("lightno");
		Object lightno_gt = queryMap.get("lightno_gt");
		Object lightno_ge = queryMap.get("lightno_ge");
		Object lightno_lt = queryMap.get("lightno_lt");
		Object lightno_le = queryMap.get("lightno_le");
		Object lightno_in = queryMap.get("lightno_in");
		Object lightdevid = queryMap.get("lightdevid");
		Object lightdevid_like = queryMap.get("lightdevid_like");
		Object lightdevid_isNull = queryMap.get("lightdevid_isNull");
		Object lightdevid_isNotNull = queryMap.get("lightdevid_isNotNull");
		Object lightdevid_in = queryMap.get("lightdevid_in");
		Object alarmphone = queryMap.get("alarmphone");
		Object alarmphone_like = queryMap.get("alarmphone_like");
		Object alarmphone_isNull = queryMap.get("alarmphone_isNull");
		Object alarmphone_isNotNull = queryMap.get("alarmphone_isNotNull");
		Object alarmphone_in = queryMap.get("alarmphone_in");
		Object emitid = queryMap.get("emitid");
		Object emitid_gt = queryMap.get("emitid_gt");
		Object emitid_ge = queryMap.get("emitid_ge");
		Object emitid_lt = queryMap.get("emitid_lt");
		Object emitid_le = queryMap.get("emitid_le");
		Object emitid_in = queryMap.get("emitid_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object attribute = queryMap.get("attribute");
		Object attribute_like = queryMap.get("attribute_like");
		Object attribute_isNull = queryMap.get("attribute_isNull");
		Object attribute_isNotNull = queryMap.get("attribute_isNotNull");
		Object attribute_in = queryMap.get("attribute_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object updatedate_gt = queryMap.get("updatedate_gt");
		Object updatedate_ge = queryMap.get("updatedate_ge");
		Object updatedate_lt = queryMap.get("updatedate_lt");
		Object updatedate_le = queryMap.get("updatedate_le");
		Object id1 = queryMap.get("id1");
		Object id1_gt = queryMap.get("id1_gt");
		Object id1_ge = queryMap.get("id1_ge");
		Object id1_lt = queryMap.get("id1_lt");
		Object id1_le = queryMap.get("id1_le");
		Object id1_in = queryMap.get("id1_in");
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
		Object createdate1_gt = queryMap.get("createdate1_gt");
		Object createdate1_ge = queryMap.get("createdate1_ge");
		Object createdate1_lt = queryMap.get("createdate1_lt");
		Object createdate1_le = queryMap.get("createdate1_le");

		QueryCondition qc = new QueryCondition(DevLeftJionMapEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.eq,
					alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.like,
					alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.isNull,
					alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.in,
					alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}
		if (id1 != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.eq, id1));
		}
		if (id1_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.gt, id1_gt));
		}
		if (id1_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.ge, id1_ge));
		}
		if (id1_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.lt, id1_lt));
		}
		if (id1_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.le, id1_le));
		}
		if (id1_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.in, id1_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.eq,
					positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.gt,
					positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.ge,
					positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.lt,
					positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.le,
					positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.in,
					positionId_in));
		}
		if (createdate1_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.gt,
					createdate1_gt));
		}
		if (createdate1_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.ge,
					createdate1_ge));
		}
		if (createdate1_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.lt,
					createdate1_lt));
		}
		if (createdate1_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.le,
					createdate1_le));
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
		list = dbManager.queryByConditions(DevLeftJionMapEntity.class, qc, oc);
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object name_in = queryMap.get("name_in");
		Object type = queryMap.get("type");
		Object type_like = queryMap.get("type_like");
		Object type_isNull = queryMap.get("type_isNull");
		Object type_isNotNull = queryMap.get("type_isNotNull");
		Object type_in = queryMap.get("type_in");
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object alarmcontent = queryMap.get("alarmcontent");
		Object alarmcontent_like = queryMap.get("alarmcontent_like");
		Object alarmcontent_isNull = queryMap.get("alarmcontent_isNull");
		Object alarmcontent_isNotNull = queryMap.get("alarmcontent_isNotNull");
		Object alarmcontent_in = queryMap.get("alarmcontent_in");
		Object alarmdevid = queryMap.get("alarmdevid");
		Object alarmdevid_like = queryMap.get("alarmdevid_like");
		Object alarmdevid_isNull = queryMap.get("alarmdevid_isNull");
		Object alarmdevid_isNotNull = queryMap.get("alarmdevid_isNotNull");
		Object alarmdevid_in = queryMap.get("alarmdevid_in");
		Object lightno = queryMap.get("lightno");
		Object lightno_gt = queryMap.get("lightno_gt");
		Object lightno_ge = queryMap.get("lightno_ge");
		Object lightno_lt = queryMap.get("lightno_lt");
		Object lightno_le = queryMap.get("lightno_le");
		Object lightno_in = queryMap.get("lightno_in");
		Object lightdevid = queryMap.get("lightdevid");
		Object lightdevid_like = queryMap.get("lightdevid_like");
		Object lightdevid_isNull = queryMap.get("lightdevid_isNull");
		Object lightdevid_isNotNull = queryMap.get("lightdevid_isNotNull");
		Object lightdevid_in = queryMap.get("lightdevid_in");
		Object alarmphone = queryMap.get("alarmphone");
		Object alarmphone_like = queryMap.get("alarmphone_like");
		Object alarmphone_isNull = queryMap.get("alarmphone_isNull");
		Object alarmphone_isNotNull = queryMap.get("alarmphone_isNotNull");
		Object alarmphone_in = queryMap.get("alarmphone_in");
		Object emitid = queryMap.get("emitid");
		Object emitid_gt = queryMap.get("emitid_gt");
		Object emitid_ge = queryMap.get("emitid_ge");
		Object emitid_lt = queryMap.get("emitid_lt");
		Object emitid_le = queryMap.get("emitid_le");
		Object emitid_in = queryMap.get("emitid_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object attribute = queryMap.get("attribute");
		Object attribute_like = queryMap.get("attribute_like");
		Object attribute_isNull = queryMap.get("attribute_isNull");
		Object attribute_isNotNull = queryMap.get("attribute_isNotNull");
		Object attribute_in = queryMap.get("attribute_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object updatedate_gt = queryMap.get("updatedate_gt");
		Object updatedate_ge = queryMap.get("updatedate_ge");
		Object updatedate_lt = queryMap.get("updatedate_lt");
		Object updatedate_le = queryMap.get("updatedate_le");
		Object id1 = queryMap.get("id1");
		Object id1_gt = queryMap.get("id1_gt");
		Object id1_ge = queryMap.get("id1_ge");
		Object id1_lt = queryMap.get("id1_lt");
		Object id1_le = queryMap.get("id1_le");
		Object id1_in = queryMap.get("id1_in");
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
		Object createdate1_gt = queryMap.get("createdate1_gt");
		Object createdate1_ge = queryMap.get("createdate1_ge");
		Object createdate1_lt = queryMap.get("createdate1_lt");
		Object createdate1_le = queryMap.get("createdate1_le");

		QueryCondition qc = new QueryCondition(DevLeftJionMapEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.eq,
					alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.like,
					alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.isNull,
					alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.in,
					alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}
		if (id1 != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.eq, id1));
		}
		if (id1_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.gt, id1_gt));
		}
		if (id1_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.ge, id1_ge));
		}
		if (id1_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.lt, id1_lt));
		}
		if (id1_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.le, id1_le));
		}
		if (id1_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.in, id1_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.eq,
					positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.gt,
					positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.ge,
					positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.lt,
					positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.le,
					positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.in,
					positionId_in));
		}
		if (createdate1_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.gt,
					createdate1_gt));
		}
		if (createdate1_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.ge,
					createdate1_ge));
		}
		if (createdate1_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.lt,
					createdate1_lt));
		}
		if (createdate1_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.le,
					createdate1_le));
		}

		pagelist = dbManager.queryByCondition(DevLeftJionMapEntity.class, qc,
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object name_in = queryMap.get("name_in");
		Object type = queryMap.get("type");
		Object type_like = queryMap.get("type_like");
		Object type_isNull = queryMap.get("type_isNull");
		Object type_isNotNull = queryMap.get("type_isNotNull");
		Object type_in = queryMap.get("type_in");
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object alarmcontent = queryMap.get("alarmcontent");
		Object alarmcontent_like = queryMap.get("alarmcontent_like");
		Object alarmcontent_isNull = queryMap.get("alarmcontent_isNull");
		Object alarmcontent_isNotNull = queryMap.get("alarmcontent_isNotNull");
		Object alarmcontent_in = queryMap.get("alarmcontent_in");
		Object alarmdevid = queryMap.get("alarmdevid");
		Object alarmdevid_like = queryMap.get("alarmdevid_like");
		Object alarmdevid_isNull = queryMap.get("alarmdevid_isNull");
		Object alarmdevid_isNotNull = queryMap.get("alarmdevid_isNotNull");
		Object alarmdevid_in = queryMap.get("alarmdevid_in");
		Object lightno = queryMap.get("lightno");
		Object lightno_gt = queryMap.get("lightno_gt");
		Object lightno_ge = queryMap.get("lightno_ge");
		Object lightno_lt = queryMap.get("lightno_lt");
		Object lightno_le = queryMap.get("lightno_le");
		Object lightno_in = queryMap.get("lightno_in");
		Object lightdevid = queryMap.get("lightdevid");
		Object lightdevid_like = queryMap.get("lightdevid_like");
		Object lightdevid_isNull = queryMap.get("lightdevid_isNull");
		Object lightdevid_isNotNull = queryMap.get("lightdevid_isNotNull");
		Object lightdevid_in = queryMap.get("lightdevid_in");
		Object alarmphone = queryMap.get("alarmphone");
		Object alarmphone_like = queryMap.get("alarmphone_like");
		Object alarmphone_isNull = queryMap.get("alarmphone_isNull");
		Object alarmphone_isNotNull = queryMap.get("alarmphone_isNotNull");
		Object alarmphone_in = queryMap.get("alarmphone_in");
		Object emitid = queryMap.get("emitid");
		Object emitid_gt = queryMap.get("emitid_gt");
		Object emitid_ge = queryMap.get("emitid_ge");
		Object emitid_lt = queryMap.get("emitid_lt");
		Object emitid_le = queryMap.get("emitid_le");
		Object emitid_in = queryMap.get("emitid_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object attribute = queryMap.get("attribute");
		Object attribute_like = queryMap.get("attribute_like");
		Object attribute_isNull = queryMap.get("attribute_isNull");
		Object attribute_isNotNull = queryMap.get("attribute_isNotNull");
		Object attribute_in = queryMap.get("attribute_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object updatedate_gt = queryMap.get("updatedate_gt");
		Object updatedate_ge = queryMap.get("updatedate_ge");
		Object updatedate_lt = queryMap.get("updatedate_lt");
		Object updatedate_le = queryMap.get("updatedate_le");
		Object id1 = queryMap.get("id1");
		Object id1_gt = queryMap.get("id1_gt");
		Object id1_ge = queryMap.get("id1_ge");
		Object id1_lt = queryMap.get("id1_lt");
		Object id1_le = queryMap.get("id1_le");
		Object id1_in = queryMap.get("id1_in");
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
		Object createdate1_gt = queryMap.get("createdate1_gt");
		Object createdate1_ge = queryMap.get("createdate1_ge");
		Object createdate1_lt = queryMap.get("createdate1_lt");
		Object createdate1_le = queryMap.get("createdate1_le");

		QueryCondition qc = new QueryCondition(DevLeftJionMapEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.eq,
					alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.like,
					alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.isNull,
					alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.in,
					alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}
		if (id1 != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.eq, id1));
		}
		if (id1_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.gt, id1_gt));
		}
		if (id1_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.ge, id1_ge));
		}
		if (id1_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.lt, id1_lt));
		}
		if (id1_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.le, id1_le));
		}
		if (id1_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.in, id1_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.eq,
					positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.gt,
					positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.ge,
					positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.lt,
					positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.le,
					positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.in,
					positionId_in));
		}
		if (createdate1_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.gt,
					createdate1_gt));
		}
		if (createdate1_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.ge,
					createdate1_ge));
		}
		if (createdate1_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.lt,
					createdate1_lt));
		}
		if (createdate1_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.le,
					createdate1_le));
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
		pagelist = dbManager.queryByConditions(DevLeftJionMapEntity.class, qc,
				oc, pageno, pagesize);
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
				result = dbManager.delNoTransaction(id,
						DevLeftJionMapEntity.class);
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
		Object name = queryMap.get("name");
		Object name_like = queryMap.get("name_like");
		Object name_isNull = queryMap.get("name_isNull");
		Object name_isNotNull = queryMap.get("name_isNotNull");
		Object name_in = queryMap.get("name_in");
		Object type = queryMap.get("type");
		Object type_like = queryMap.get("type_like");
		Object type_isNull = queryMap.get("type_isNull");
		Object type_isNotNull = queryMap.get("type_isNotNull");
		Object type_in = queryMap.get("type_in");
		Object code = queryMap.get("code");
		Object code_like = queryMap.get("code_like");
		Object code_isNull = queryMap.get("code_isNull");
		Object code_isNotNull = queryMap.get("code_isNotNull");
		Object code_in = queryMap.get("code_in");
		Object alarmcontent = queryMap.get("alarmcontent");
		Object alarmcontent_like = queryMap.get("alarmcontent_like");
		Object alarmcontent_isNull = queryMap.get("alarmcontent_isNull");
		Object alarmcontent_isNotNull = queryMap.get("alarmcontent_isNotNull");
		Object alarmcontent_in = queryMap.get("alarmcontent_in");
		Object alarmdevid = queryMap.get("alarmdevid");
		Object alarmdevid_like = queryMap.get("alarmdevid_like");
		Object alarmdevid_isNull = queryMap.get("alarmdevid_isNull");
		Object alarmdevid_isNotNull = queryMap.get("alarmdevid_isNotNull");
		Object alarmdevid_in = queryMap.get("alarmdevid_in");
		Object lightno = queryMap.get("lightno");
		Object lightno_gt = queryMap.get("lightno_gt");
		Object lightno_ge = queryMap.get("lightno_ge");
		Object lightno_lt = queryMap.get("lightno_lt");
		Object lightno_le = queryMap.get("lightno_le");
		Object lightno_in = queryMap.get("lightno_in");
		Object lightdevid = queryMap.get("lightdevid");
		Object lightdevid_like = queryMap.get("lightdevid_like");
		Object lightdevid_isNull = queryMap.get("lightdevid_isNull");
		Object lightdevid_isNotNull = queryMap.get("lightdevid_isNotNull");
		Object lightdevid_in = queryMap.get("lightdevid_in");
		Object alarmphone = queryMap.get("alarmphone");
		Object alarmphone_like = queryMap.get("alarmphone_like");
		Object alarmphone_isNull = queryMap.get("alarmphone_isNull");
		Object alarmphone_isNotNull = queryMap.get("alarmphone_isNotNull");
		Object alarmphone_in = queryMap.get("alarmphone_in");
		Object emitid = queryMap.get("emitid");
		Object emitid_gt = queryMap.get("emitid_gt");
		Object emitid_ge = queryMap.get("emitid_ge");
		Object emitid_lt = queryMap.get("emitid_lt");
		Object emitid_le = queryMap.get("emitid_le");
		Object emitid_in = queryMap.get("emitid_in");
		Object parentId = queryMap.get("parentId");
		Object parentId_gt = queryMap.get("parentId_gt");
		Object parentId_ge = queryMap.get("parentId_ge");
		Object parentId_lt = queryMap.get("parentId_lt");
		Object parentId_le = queryMap.get("parentId_le");
		Object parentId_in = queryMap.get("parentId_in");
		Object attribute = queryMap.get("attribute");
		Object attribute_like = queryMap.get("attribute_like");
		Object attribute_isNull = queryMap.get("attribute_isNull");
		Object attribute_isNotNull = queryMap.get("attribute_isNotNull");
		Object attribute_in = queryMap.get("attribute_in");
		Object createdate_gt = queryMap.get("createdate_gt");
		Object createdate_ge = queryMap.get("createdate_ge");
		Object createdate_lt = queryMap.get("createdate_lt");
		Object createdate_le = queryMap.get("createdate_le");
		Object updatedate_gt = queryMap.get("updatedate_gt");
		Object updatedate_ge = queryMap.get("updatedate_ge");
		Object updatedate_lt = queryMap.get("updatedate_lt");
		Object updatedate_le = queryMap.get("updatedate_le");
		Object id1 = queryMap.get("id1");
		Object id1_gt = queryMap.get("id1_gt");
		Object id1_ge = queryMap.get("id1_ge");
		Object id1_lt = queryMap.get("id1_lt");
		Object id1_le = queryMap.get("id1_le");
		Object id1_in = queryMap.get("id1_in");
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
		Object createdate1_gt = queryMap.get("createdate1_gt");
		Object createdate1_ge = queryMap.get("createdate1_ge");
		Object createdate1_lt = queryMap.get("createdate1_lt");
		Object createdate1_le = queryMap.get("createdate1_le");

		QueryCondition qc = new QueryCondition(DevLeftJionMapEntity.ID,
				QueryCondition.gt, "0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.eq, id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.gt, id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.ge, id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.lt, id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.le, id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID,
					QueryCondition.in, id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.eq,
					alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.like,
					alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.isNull,
					alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.ALARMCONTENT, QueryCondition.in,
					alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}
		if (id1 != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.eq, id1));
		}
		if (id1_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.gt, id1_gt));
		}
		if (id1_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.ge, id1_ge));
		}
		if (id1_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.lt, id1_lt));
		}
		if (id1_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.le, id1_le));
		}
		if (id1_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.ID1,
					QueryCondition.in, id1_in));
		}
		if (devId != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.eq, devId));
		}
		if (devId_gt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.gt, devId_gt));
		}
		if (devId_ge != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.ge, devId_ge));
		}
		if (devId_lt != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.lt, devId_lt));
		}
		if (devId_le != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.le, devId_le));
		}
		if (devId_in != null) {
			qc.andCondition(new QueryCondition(DevLeftJionMapEntity.DEV_ID,
					QueryCondition.in, devId_in));
		}
		if (positionId != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.eq,
					positionId));
		}
		if (positionId_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.gt,
					positionId_gt));
		}
		if (positionId_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.ge,
					positionId_ge));
		}
		if (positionId_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.lt,
					positionId_lt));
		}
		if (positionId_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.le,
					positionId_le));
		}
		if (positionId_in != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.POSITION_ID, QueryCondition.in,
					positionId_in));
		}
		if (createdate1_gt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.gt,
					createdate1_gt));
		}
		if (createdate1_ge != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.ge,
					createdate1_ge));
		}
		if (createdate1_lt != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.lt,
					createdate1_lt));
		}
		if (createdate1_le != null) {
			qc.andCondition(new QueryCondition(
					DevLeftJionMapEntity.CREATEDATE1, QueryCondition.le,
					createdate1_le));
		}

		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				result = dbManager.delByConditionsNoTransaction(
						DevLeftJionMapEntity.class, qc);
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
