package fly.service.historyWandai;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;


import fly.entity.dev.DevEntity;

import fly.entity.historyWandai.HistoryWandaiEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;



/**   
 * @Title: Service
 * @Description: 腕带历史信息服务类
 * @author feng.gu
 * @date 2015-09-07 14:15:50
 * @version V1.0   
 *
 */
public class HistoryWandaiService {
	   private static Logger logger = Logger.getLogger(HistoryWandaiService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static HistoryWandaiService historyWandaiService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static HistoryWandaiService getInstance(){
		if(historyWandaiService==null){
			historyWandaiService = new HistoryWandaiService();
		}
		return historyWandaiService;
	   }
	                 	                 				     				     				     				     
				     	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(HistoryWandaiEntity historyWandai) {			
			boolean result =false;
			if(historyWandai!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     					     //关联信息保存
					     DevEntity dev = historyWandai.getDev();
						 if(dev!=null){						   
							 dbManager.saveNoTransaction(dev);
							 historyWandai.setDevId(dev.getId());
						 }						 
				     				     				     					 					 result=dbManager.saveNoTransaction(historyWandai);	
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
		public boolean saveList(List<HistoryWandaiEntity> historyWandaiList) {
			boolean result = false;
			if (historyWandaiList != null && historyWandaiList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(HistoryWandaiEntity historyWandai:historyWandaiList){
						if(historyWandai!=null){												  								 
								 									 					                 								     								         //关联信息保存
									     DevEntity dev = historyWandai.getDev();
										 if(dev!=null){						   
											 dbManager.saveNoTransaction(dev);
											 historyWandai.setDevId(dev.getId());
										 }									 
								     								     								     								 								 result=dbManager.saveNoTransaction(historyWandai);								 			
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
		public HistoryWandaiEntity getById(Integer id,Boolean devShow) {
			HistoryWandaiEntity obj = null;
			if (id!=null) {
				obj = (HistoryWandaiEntity)dbManager.getById(id, HistoryWandaiEntity.class);
									 	                 				     				        //查询关联内容
						if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getDevId()>0){
							DevEntity dev = (DevEntity)dbManager.getById(obj.getDevId(), DevEntity.class);
							obj.setDev(dev);        	  	  
						}
				     				     				     				   			}
			return obj;
		}
		
		/**
		 * 根据条件查询记录集合（不分页）
		 * @param queryMap 查询条件集合
		 * @return
		 */
		public List<Object> getListByCondition(Map<String,Object> queryMap) {
			List<Object> list = null;
			if(queryMap==null){
				queryMap = new HashMap<String,Object>();
			}
												Object id=queryMap.get("id");
					Object id_gt=queryMap.get("id_gt");
					Object id_ge=queryMap.get("id_ge");
					Object id_lt=queryMap.get("id_lt");
					Object id_le=queryMap.get("id_le");
					Object id_in=queryMap.get("id_in");
																Object devId=queryMap.get("devId");
					Object devId_gt=queryMap.get("devId_gt");
					Object devId_ge=queryMap.get("devId_ge");
					Object devId_lt=queryMap.get("devId_lt");
					Object devId_le=queryMap.get("devId_le");
					Object devId_in=queryMap.get("devId_in");
																Object alarm=queryMap.get("alarm");
					Object alarm_like=queryMap.get("alarm_like");
					Object alarm_isNull=queryMap.get("alarm_isNull");
					Object alarm_isNotNull=queryMap.get("alarm_isNotNull");
					Object alarm_in=queryMap.get("alarm_in");
																Object alarmupdatetime=queryMap.get("alarmupdatetime");
					Object alarmupdatetime_like=queryMap.get("alarmupdatetime_like");
					Object alarmupdatetime_isNull=queryMap.get("alarmupdatetime_isNull");
					Object alarmupdatetime_isNotNull=queryMap.get("alarmupdatetime_isNotNull");
					Object alarmupdatetime_in=queryMap.get("alarmupdatetime_in");
							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(alarm!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
		            if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.in, alarm_in));}
																if(alarmupdatetime!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.eq, alarmupdatetime));}
		            if(alarmupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.like, alarmupdatetime_like));}
		            if(alarmupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNull, alarmupdatetime_isNull));}
		            if(alarmupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNotNull, alarmupdatetime_isNotNull));}
		            if(alarmupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.in, alarmupdatetime_in));}
										
						  			            list = dbManager.queryByCondition(HistoryWandaiEntity.class,qc);						
			return list;
		}
		
		
		/**
		 * 根据条件查询记录集合
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
																Object devId=queryMap.get("devId");
					Object devId_gt=queryMap.get("devId_gt");
					Object devId_ge=queryMap.get("devId_ge");
					Object devId_lt=queryMap.get("devId_lt");
					Object devId_le=queryMap.get("devId_le");
					Object devId_in=queryMap.get("devId_in");
																Object alarm=queryMap.get("alarm");
					Object alarm_like=queryMap.get("alarm_like");
					Object alarm_isNull=queryMap.get("alarm_isNull");
					Object alarm_isNotNull=queryMap.get("alarm_isNotNull");
					Object alarm_in=queryMap.get("alarm_in");
																Object alarmupdatetime=queryMap.get("alarmupdatetime");
					Object alarmupdatetime_like=queryMap.get("alarmupdatetime_like");
					Object alarmupdatetime_isNull=queryMap.get("alarmupdatetime_isNull");
					Object alarmupdatetime_isNotNull=queryMap.get("alarmupdatetime_isNotNull");
					Object alarmupdatetime_in=queryMap.get("alarmupdatetime_in");
							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(alarm!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
		            if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.in, alarm_in));}
																if(alarmupdatetime!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.eq, alarmupdatetime));}
		            if(alarmupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.like, alarmupdatetime_like));}
		            if(alarmupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNull, alarmupdatetime_isNull));}
		            if(alarmupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNotNull, alarmupdatetime_isNotNull));}
		            if(alarmupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.in, alarmupdatetime_in));}
										
						  			            pagelist = dbManager.queryByCondition(HistoryWandaiEntity.class,qc,pageno,pagesize);						
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
										 	                 				     					    //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){	
							HistoryWandaiEntity historyWandai = (HistoryWandaiEntity)dbManager.getById(id, HistoryWandaiEntity.class);
							if(historyWandai!=null&&historyWandai.getDevId()!=null){
								dbManager.delNoTransaction(historyWandai.getDevId(), DevEntity.class);
							}						
						}
				     				     				     										result = dbManager.delNoTransaction(id, HistoryWandaiEntity.class);
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
		 */
		public boolean delList(Map<String,Object> queryMap,Boolean delDev) {
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
																Object devId=queryMap.get("devId");
					Object devId_gt=queryMap.get("devId_gt");
					Object devId_ge=queryMap.get("devId_ge");
					Object devId_lt=queryMap.get("devId_lt");
					Object devId_le=queryMap.get("devId_le");
					Object devId_in=queryMap.get("devId_in");
																Object alarm=queryMap.get("alarm");
					Object alarm_like=queryMap.get("alarm_like");
					Object alarm_isNull=queryMap.get("alarm_isNull");
					Object alarm_isNotNull=queryMap.get("alarm_isNotNull");
					Object alarm_in=queryMap.get("alarm_in");
																Object alarmupdatetime=queryMap.get("alarmupdatetime");
					Object alarmupdatetime_like=queryMap.get("alarmupdatetime_like");
					Object alarmupdatetime_isNull=queryMap.get("alarmupdatetime_isNull");
					Object alarmupdatetime_isNotNull=queryMap.get("alarmupdatetime_isNotNull");
					Object alarmupdatetime_in=queryMap.get("alarmupdatetime_in");
																				
			QueryCondition qc = new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(alarm!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARM, QueryCondition.in, alarm_in));}
																if(alarmupdatetime!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.eq, alarmupdatetime));}
		            if(alarmupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.like, alarmupdatetime_like));}
		            if(alarmupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNull, alarmupdatetime_isNull));}
		            if(alarmupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.isNotNull, alarmupdatetime_isNotNull));}
				    if(alarmupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryWandaiEntity.ALARMUPDATETIME, QueryCondition.in, alarmupdatetime_in));}
										
				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 				     					     //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){
							List<Object> list=dbManager.queryByCondition(HistoryWandaiEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									HistoryWandaiEntity entity=(HistoryWandaiEntity)obj;
									strIds += entity.getDevId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(DevEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(DevEntity.class, qc1);
							}
							
						}
				     				     				     										result = dbManager.delByConditionsNoTransaction(HistoryWandaiEntity.class,qc);				
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
