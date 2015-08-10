package fly.service.dev;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.dev.DevEntity;
import fly.entity.dev.DevEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;



/**   
 * @Title: Service
 * @Description: 设备信息服务类
 * @author feng.gu
 * @date 2015-08-10 09:24:12
 * @version V1.0   
 *
 */
public class DevService {
	   private static Logger logger = Logger.getLogger(DevService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static DevService devService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static DevService getInstance(){
		if(devService==null){
			devService = new DevService();
		}
		return devService;
	   }
	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(DevEntity dev) {			
			boolean result =false;
			if(dev!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 
					 dbManager.saveNoTransaction(dev);
					 					 if(dev.getDev()!=null){						   
						 dbManager.saveNoTransaction(dev.getDev());
					 }
					 					 tx.commitAndClose();   
				}catch (Exception e) { 
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
		public boolean saveList(List<DevEntity> devList) {
			boolean result = false;
			if (devList != null && devList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(DevEntity dev:devList){
						if(dev!=null){												  
								 dbManager.saveNoTransaction(dev);
								 								 if(dev.getDev()!=null){						   
									 dbManager.saveNoTransaction(dev.getDev());
								 }	
								 						}	
					}
				    tx.commitAndClose();   
				}catch (Exception e) { 
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
		 * @param id 主键
		 		 * @param devShow 是否查询关联信息
		 		 * @param obj
		 */
		public DevEntity getById(Integer id,Boolean devShow) {
			DevEntity obj = null;
			if (id!=null) {
				obj = (DevEntity)dbManager.getById(id, DevEntity.class);
								//查询关联内容
				if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getParentId()>0){
					DevEntity dev = (DevEntity)dbManager.getById(obj.getParentId(), DevEntity.class);
					obj.setDev(dev);        	  	  
				}
							}
			return obj;
		}
		
		/**
		 * 根据id读取记录集合
		 * @param queryMap 查询条件集合
		 * @param pageno
		 * @param pagesize
		 * @return
		 */
		public PageList getListByCondition(Map<String,Object> queryMap,int pageno,int pagesize) {
			PageList pagelist = null;
			if(queryMap==null){
				queryMap = new HashMap<String,Object>();
			}
												Object id=queryMap.get("id");
					Object id_gt=queryMap.get("id_gt");
					Object id_ge=queryMap.get("id_ge");
					Object id_lt=queryMap.get("id_lt");
					Object id_le=queryMap.get("id_le");
					Object id_in=queryMap.get("id_in");
																Object name=queryMap.get("name");
					Object name_like=queryMap.get("name_like");
					Object name_isNull=queryMap.get("name_isNull");
					Object name_isNotNull=queryMap.get("name_isNotNull");
																Object type=queryMap.get("type");
					Object type_like=queryMap.get("type_like");
					Object type_isNull=queryMap.get("type_isNull");
					Object type_isNotNull=queryMap.get("type_isNotNull");
																Object code=queryMap.get("code");
					Object code_like=queryMap.get("code_like");
					Object code_isNull=queryMap.get("code_isNull");
					Object code_isNotNull=queryMap.get("code_isNotNull");
																Object alarmcontent=queryMap.get("alarmcontent");
					Object alarmcontent_like=queryMap.get("alarmcontent_like");
					Object alarmcontent_isNull=queryMap.get("alarmcontent_isNull");
					Object alarmcontent_isNotNull=queryMap.get("alarmcontent_isNotNull");
																Object alarmdevid=queryMap.get("alarmdevid");
					Object alarmdevid_like=queryMap.get("alarmdevid_like");
					Object alarmdevid_isNull=queryMap.get("alarmdevid_isNull");
					Object alarmdevid_isNotNull=queryMap.get("alarmdevid_isNotNull");
																Object lightno=queryMap.get("lightno");
					Object lightno_gt=queryMap.get("lightno_gt");
					Object lightno_ge=queryMap.get("lightno_ge");
					Object lightno_lt=queryMap.get("lightno_lt");
					Object lightno_le=queryMap.get("lightno_le");
					Object lightno_in=queryMap.get("lightno_in");
																Object lightdevid=queryMap.get("lightdevid");
					Object lightdevid_like=queryMap.get("lightdevid_like");
					Object lightdevid_isNull=queryMap.get("lightdevid_isNull");
					Object lightdevid_isNotNull=queryMap.get("lightdevid_isNotNull");
																Object alarmphone=queryMap.get("alarmphone");
					Object alarmphone_like=queryMap.get("alarmphone_like");
					Object alarmphone_isNull=queryMap.get("alarmphone_isNull");
					Object alarmphone_isNotNull=queryMap.get("alarmphone_isNotNull");
																Object emitid=queryMap.get("emitid");
					Object emitid_gt=queryMap.get("emitid_gt");
					Object emitid_ge=queryMap.get("emitid_ge");
					Object emitid_lt=queryMap.get("emitid_lt");
					Object emitid_le=queryMap.get("emitid_le");
					Object emitid_in=queryMap.get("emitid_in");
																Object parentId=queryMap.get("parentId");
					Object parentId_gt=queryMap.get("parentId_gt");
					Object parentId_ge=queryMap.get("parentId_ge");
					Object parentId_lt=queryMap.get("parentId_lt");
					Object parentId_le=queryMap.get("parentId_le");
					Object parentId_in=queryMap.get("parentId_in");
																Object attribute=queryMap.get("attribute");
					Object attribute_like=queryMap.get("attribute_like");
					Object attribute_isNull=queryMap.get("attribute_isNull");
					Object attribute_isNotNull=queryMap.get("attribute_isNotNull");
																Object createdate=queryMap.get("createdate");
					Object createdate_like=queryMap.get("createdate_like");
					Object createdate_isNull=queryMap.get("createdate_isNull");
					Object createdate_isNotNull=queryMap.get("createdate_isNotNull");
																Object updatedate=queryMap.get("updatedate");
					Object updatedate_like=queryMap.get("updatedate_like");
					Object updatedate_isNull=queryMap.get("updatedate_isNull");
					Object updatedate_isNotNull=queryMap.get("updatedate_isNotNull");
										
			QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in, id_in));}
																if(name!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
																if(type!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.eq, type));}
		            if(type_like!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.like, type_like));}
		            if(type_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.isNull, type_isNull));}
		            if(type_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.isNotNull, type_isNotNull));}
																if(code!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.eq, code));}
		            if(code_like!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.like, code_like));}
		            if(code_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.isNull, code_isNull));}
		            if(code_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.isNotNull, code_isNotNull));}
																if(alarmcontent!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.eq, alarmcontent));}
		            if(alarmcontent_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.like, alarmcontent_like));}
		            if(alarmcontent_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.isNull, alarmcontent_isNull));}
		            if(alarmcontent_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.isNotNull, alarmcontent_isNotNull));}
																if(alarmdevid!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.eq, alarmdevid));}
		            if(alarmdevid_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.like, alarmdevid_like));}
		            if(alarmdevid_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.isNull, alarmdevid_isNull));}
		            if(alarmdevid_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.isNotNull, alarmdevid_isNotNull));}
																if(lightno!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.in, lightno));}
					if(lightno_gt!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.gt, lightno_gt));}
					if(lightno_ge!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.ge, lightno_ge));}
					if(lightno_lt!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.lt, lightno_lt));}
					if(lightno_le!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.le, lightno_le));}
					if(lightno_in!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.in, lightno_in));}
																if(lightdevid!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.eq, lightdevid));}
		            if(lightdevid_like!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.like, lightdevid_like));}
		            if(lightdevid_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.isNull, lightdevid_isNull));}
		            if(lightdevid_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.isNotNull, lightdevid_isNotNull));}
																if(alarmphone!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.eq, alarmphone));}
		            if(alarmphone_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.like, alarmphone_like));}
		            if(alarmphone_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.isNull, alarmphone_isNull));}
		            if(alarmphone_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.isNotNull, alarmphone_isNotNull));}
																if(emitid!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.in, emitid));}
					if(emitid_gt!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.gt, emitid_gt));}
					if(emitid_ge!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.ge, emitid_ge));}
					if(emitid_lt!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.lt, emitid_lt));}
					if(emitid_le!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.le, emitid_le));}
					if(emitid_in!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.in, emitid_in));}
																if(parentId!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.in, parentId));}
					if(parentId_gt!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.gt, parentId_gt));}
					if(parentId_ge!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.ge, parentId_ge));}
					if(parentId_lt!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.lt, parentId_lt));}
					if(parentId_le!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.le, parentId_le));}
					if(parentId_in!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.in, parentId_in));}
																if(attribute!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.eq, attribute));}
		            if(attribute_like!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.like, attribute_like));}
		            if(attribute_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.isNull, attribute_isNull));}
		            if(attribute_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.isNotNull, attribute_isNotNull));}
																if(createdate!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.eq, createdate));}
		            if(createdate_like!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.like, createdate_like));}
		            if(createdate_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.isNull, createdate_isNull));}
		            if(createdate_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.isNotNull, createdate_isNotNull));}
																if(updatedate!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.eq, updatedate));}
		            if(updatedate_like!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.like, updatedate_like));}
		            if(updatedate_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.isNull, updatedate_isNull));}
		            if(updatedate_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.isNotNull, updatedate_isNotNull));}
										
            pagelist = dbManager.queryByCondition(DevEntity.class,qc,pageno,pagesize);						
			return pagelist;
		}
		
		/**
		 * 删除记录
		 * 
		 * @param id 主键
		 * @param obj
		 */
		public boolean del(Integer id,Boolean delDev) {
			boolean result = false;
			if (id !=null&&id>0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					result = dbManager.delNoTransaction(id, DevEntity.class);
										//删除关联信息
					if(delDev!=null&&delDev.booleanValue()){	
						DevEntity dev = (DevEntity)dbManager.getById(id, DevEntity.class);
						if(dev.getParentId()!=null){
							dbManager.delNoTransaction(dev.getParentId(), DevEntity.class);
						}						
					}
										tx.commitAndClose();   
				}catch (Exception e) { 
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
		 * @param queryMap 查询条件集合
		 		 * @param delDevList 是否删除关联信息
		 		 */
		public boolean delList(Map<String,Object> queryMap,Boolean delDevList) {
			boolean result = false;
			if(queryMap==null){
				queryMap = new HashMap<String,Object>();
			}
												Object id=queryMap.get("id");
					Object id_gt=queryMap.get("id_gt");
					Object id_ge=queryMap.get("id_ge");
					Object id_lt=queryMap.get("id_lt");
					Object id_le=queryMap.get("id_le");
					Object id_in=queryMap.get("id_in");
																Object name=queryMap.get("name");
					Object name_like=queryMap.get("name_like");
					Object name_isNull=queryMap.get("name_isNull");
					Object name_isNotNull=queryMap.get("name_isNotNull");
																Object type=queryMap.get("type");
					Object type_like=queryMap.get("type_like");
					Object type_isNull=queryMap.get("type_isNull");
					Object type_isNotNull=queryMap.get("type_isNotNull");
																Object code=queryMap.get("code");
					Object code_like=queryMap.get("code_like");
					Object code_isNull=queryMap.get("code_isNull");
					Object code_isNotNull=queryMap.get("code_isNotNull");
																Object alarmcontent=queryMap.get("alarmcontent");
					Object alarmcontent_like=queryMap.get("alarmcontent_like");
					Object alarmcontent_isNull=queryMap.get("alarmcontent_isNull");
					Object alarmcontent_isNotNull=queryMap.get("alarmcontent_isNotNull");
																Object alarmdevid=queryMap.get("alarmdevid");
					Object alarmdevid_like=queryMap.get("alarmdevid_like");
					Object alarmdevid_isNull=queryMap.get("alarmdevid_isNull");
					Object alarmdevid_isNotNull=queryMap.get("alarmdevid_isNotNull");
																Object lightno=queryMap.get("lightno");
					Object lightno_gt=queryMap.get("lightno_gt");
					Object lightno_ge=queryMap.get("lightno_ge");
					Object lightno_lt=queryMap.get("lightno_lt");
					Object lightno_le=queryMap.get("lightno_le");
					Object lightno_in=queryMap.get("lightno_in");
																Object lightdevid=queryMap.get("lightdevid");
					Object lightdevid_like=queryMap.get("lightdevid_like");
					Object lightdevid_isNull=queryMap.get("lightdevid_isNull");
					Object lightdevid_isNotNull=queryMap.get("lightdevid_isNotNull");
																Object alarmphone=queryMap.get("alarmphone");
					Object alarmphone_like=queryMap.get("alarmphone_like");
					Object alarmphone_isNull=queryMap.get("alarmphone_isNull");
					Object alarmphone_isNotNull=queryMap.get("alarmphone_isNotNull");
																Object emitid=queryMap.get("emitid");
					Object emitid_gt=queryMap.get("emitid_gt");
					Object emitid_ge=queryMap.get("emitid_ge");
					Object emitid_lt=queryMap.get("emitid_lt");
					Object emitid_le=queryMap.get("emitid_le");
					Object emitid_in=queryMap.get("emitid_in");
																Object parentId=queryMap.get("parentId");
					Object parentId_gt=queryMap.get("parentId_gt");
					Object parentId_ge=queryMap.get("parentId_ge");
					Object parentId_lt=queryMap.get("parentId_lt");
					Object parentId_le=queryMap.get("parentId_le");
					Object parentId_in=queryMap.get("parentId_in");
																Object attribute=queryMap.get("attribute");
					Object attribute_like=queryMap.get("attribute_like");
					Object attribute_isNull=queryMap.get("attribute_isNull");
					Object attribute_isNotNull=queryMap.get("attribute_isNotNull");
																Object createdate=queryMap.get("createdate");
					Object createdate_like=queryMap.get("createdate_like");
					Object createdate_isNull=queryMap.get("createdate_isNull");
					Object createdate_isNotNull=queryMap.get("createdate_isNotNull");
																Object updatedate=queryMap.get("updatedate");
					Object updatedate_like=queryMap.get("updatedate_like");
					Object updatedate_isNull=queryMap.get("updatedate_isNull");
					Object updatedate_isNotNull=queryMap.get("updatedate_isNotNull");
										
			QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt, "0");		
												if(id!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in, id_in));}
																if(name!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
																if(type!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.eq, type));}
		            if(type_like!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.like, type_like));}
		            if(type_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.isNull, type_isNull));}
		            if(type_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.TYPE, QueryCondition.isNotNull, type_isNotNull));}
																if(code!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.eq, code));}
		            if(code_like!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.like, code_like));}
		            if(code_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.isNull, code_isNull));}
		            if(code_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.CODE, QueryCondition.isNotNull, code_isNotNull));}
																if(alarmcontent!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.eq, alarmcontent));}
		            if(alarmcontent_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.like, alarmcontent_like));}
		            if(alarmcontent_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.isNull, alarmcontent_isNull));}
		            if(alarmcontent_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT, QueryCondition.isNotNull, alarmcontent_isNotNull));}
																if(alarmdevid!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.eq, alarmdevid));}
		            if(alarmdevid_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.like, alarmdevid_like));}
		            if(alarmdevid_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.isNull, alarmdevid_isNull));}
		            if(alarmdevid_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID, QueryCondition.isNotNull, alarmdevid_isNotNull));}
																if(lightno!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.in, lightno));}
					if(lightno_gt!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.gt, lightno_gt));}
					if(lightno_ge!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.ge, lightno_ge));}
					if(lightno_lt!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.lt, lightno_lt));}
					if(lightno_le!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.le, lightno_le));}
					if(lightno_in!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTNO, QueryCondition.in, lightno_in));}
																if(lightdevid!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.eq, lightdevid));}
		            if(lightdevid_like!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.like, lightdevid_like));}
		            if(lightdevid_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.isNull, lightdevid_isNull));}
		            if(lightdevid_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID, QueryCondition.isNotNull, lightdevid_isNotNull));}
																if(alarmphone!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.eq, alarmphone));}
		            if(alarmphone_like!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.like, alarmphone_like));}
		            if(alarmphone_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.isNull, alarmphone_isNull));}
		            if(alarmphone_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE, QueryCondition.isNotNull, alarmphone_isNotNull));}
																if(emitid!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.in, emitid));}
					if(emitid_gt!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.gt, emitid_gt));}
					if(emitid_ge!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.ge, emitid_ge));}
					if(emitid_lt!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.lt, emitid_lt));}
					if(emitid_le!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.le, emitid_le));}
					if(emitid_in!=null){qc.andCondition(new QueryCondition(DevEntity.EMITID, QueryCondition.in, emitid_in));}
																if(parentId!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.in, parentId));}
					if(parentId_gt!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.gt, parentId_gt));}
					if(parentId_ge!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.ge, parentId_ge));}
					if(parentId_lt!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.lt, parentId_lt));}
					if(parentId_le!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.le, parentId_le));}
					if(parentId_in!=null){qc.andCondition(new QueryCondition(DevEntity.PARENT_ID, QueryCondition.in, parentId_in));}
																if(attribute!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.eq, attribute));}
		            if(attribute_like!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.like, attribute_like));}
		            if(attribute_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.isNull, attribute_isNull));}
		            if(attribute_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE, QueryCondition.isNotNull, attribute_isNotNull));}
																if(createdate!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.eq, createdate));}
		            if(createdate_like!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.like, createdate_like));}
		            if(createdate_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.isNull, createdate_isNull));}
		            if(createdate_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.CREATEDATE, QueryCondition.isNotNull, createdate_isNotNull));}
																if(updatedate!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.eq, updatedate));}
		            if(updatedate_like!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.like, updatedate_like));}
		            if(updatedate_isNull!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.isNull, updatedate_isNull));}
		            if(updatedate_isNotNull!=null){qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE, QueryCondition.isNotNull, updatedate_isNotNull));}
										if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 		
					result = dbManager.delByConditionsNoTransaction(DevEntity.class,qc);
										//删除关联信息
					if(delDevList!=null&&delDevList.booleanValue()){						
							List<Object> list = dbManager.queryByCondition(DevEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									DevEntity entity=(DevEntity)obj;
									strIds += entity.getParentId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(DevEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(DevEntity.class, qc1);
							}					
					}									
										tx.commitAndClose();   
				}catch (Exception e) { 
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
