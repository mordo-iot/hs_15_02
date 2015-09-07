package fly.service.historyLocationPos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;


import fly.entity.dev.DevEntity;

import fly.entity.historyLocationPos.HistoryLocationPosEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;



/**   
 * @Title: Service
 * @Description: 定位器位置历史状态服务类
 * @author feng.gu
 * @date 2015-09-07 14:15:48
 * @version V1.0   
 *
 */
public class HistoryLocationPosService {
	   private static Logger logger = Logger.getLogger(HistoryLocationPosService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static HistoryLocationPosService historyLocationPosService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static HistoryLocationPosService getInstance(){
		if(historyLocationPosService==null){
			historyLocationPosService = new HistoryLocationPosService();
		}
		return historyLocationPosService;
	   }
	                 	                 				     				     				     				     
				     	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(HistoryLocationPosEntity historyLocationPos) {			
			boolean result =false;
			if(historyLocationPos!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     					     //关联信息保存
					     DevEntity dev = historyLocationPos.getDev();
						 if(dev!=null){						   
							 dbManager.saveNoTransaction(dev);
							 historyLocationPos.setDevId(dev.getId());
						 }						 
				     				     				     					 					 result=dbManager.saveNoTransaction(historyLocationPos);	
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
		public boolean saveList(List<HistoryLocationPosEntity> historyLocationPosList) {
			boolean result = false;
			if (historyLocationPosList != null && historyLocationPosList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(HistoryLocationPosEntity historyLocationPos:historyLocationPosList){
						if(historyLocationPos!=null){												  								 
								 									 					                 								     								         //关联信息保存
									     DevEntity dev = historyLocationPos.getDev();
										 if(dev!=null){						   
											 dbManager.saveNoTransaction(dev);
											 historyLocationPos.setDevId(dev.getId());
										 }									 
								     								     								     								 								 result=dbManager.saveNoTransaction(historyLocationPos);								 			
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
		public HistoryLocationPosEntity getById(Integer id,Boolean devShow) {
			HistoryLocationPosEntity obj = null;
			if (id!=null) {
				obj = (HistoryLocationPosEntity)dbManager.getById(id, HistoryLocationPosEntity.class);
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
																Object currpositionid=queryMap.get("currpositionid");
					Object currpositionid_gt=queryMap.get("currpositionid_gt");
					Object currpositionid_ge=queryMap.get("currpositionid_ge");
					Object currpositionid_lt=queryMap.get("currpositionid_lt");
					Object currpositionid_le=queryMap.get("currpositionid_le");
					Object currpositionid_in=queryMap.get("currpositionid_in");
																Object currlog=queryMap.get("currlog");
					Object currlog_gt=queryMap.get("currlog_gt");
					Object currlog_ge=queryMap.get("currlog_ge");
					Object currlog_lt=queryMap.get("currlog_lt");
					Object currlog_le=queryMap.get("currlog_le");
					Object currlog_in=queryMap.get("currlog_in");
																Object currlat=queryMap.get("currlat");
					Object currlat_gt=queryMap.get("currlat_gt");
					Object currlat_ge=queryMap.get("currlat_ge");
					Object currlat_lt=queryMap.get("currlat_lt");
					Object currlat_le=queryMap.get("currlat_le");
					Object currlat_in=queryMap.get("currlat_in");
																Object leaved=queryMap.get("leaved");
					Object leaved_like=queryMap.get("leaved_like");
					Object leaved_isNull=queryMap.get("leaved_isNull");
					Object leaved_isNotNull=queryMap.get("leaved_isNotNull");
					Object leaved_in=queryMap.get("leaved_in");
																Object leavedupdatetime=queryMap.get("leavedupdatetime");
					Object leavedupdatetime_like=queryMap.get("leavedupdatetime_like");
					Object leavedupdatetime_isNull=queryMap.get("leavedupdatetime_isNull");
					Object leavedupdatetime_isNotNull=queryMap.get("leavedupdatetime_isNotNull");
					Object leavedupdatetime_in=queryMap.get("leavedupdatetime_in");
							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(currpositionid!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.eq, currpositionid));}
					if(currpositionid_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.gt, currpositionid_gt));}
					if(currpositionid_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.ge, currpositionid_ge));}
					if(currpositionid_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.lt, currpositionid_lt));}
					if(currpositionid_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.le, currpositionid_le));}
					if(currpositionid_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.in, currpositionid_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
		            if(leaved_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
		            if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
										
						  			            list = dbManager.queryByCondition(HistoryLocationPosEntity.class,qc);						
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
																Object currpositionid=queryMap.get("currpositionid");
					Object currpositionid_gt=queryMap.get("currpositionid_gt");
					Object currpositionid_ge=queryMap.get("currpositionid_ge");
					Object currpositionid_lt=queryMap.get("currpositionid_lt");
					Object currpositionid_le=queryMap.get("currpositionid_le");
					Object currpositionid_in=queryMap.get("currpositionid_in");
																Object currlog=queryMap.get("currlog");
					Object currlog_gt=queryMap.get("currlog_gt");
					Object currlog_ge=queryMap.get("currlog_ge");
					Object currlog_lt=queryMap.get("currlog_lt");
					Object currlog_le=queryMap.get("currlog_le");
					Object currlog_in=queryMap.get("currlog_in");
																Object currlat=queryMap.get("currlat");
					Object currlat_gt=queryMap.get("currlat_gt");
					Object currlat_ge=queryMap.get("currlat_ge");
					Object currlat_lt=queryMap.get("currlat_lt");
					Object currlat_le=queryMap.get("currlat_le");
					Object currlat_in=queryMap.get("currlat_in");
																Object leaved=queryMap.get("leaved");
					Object leaved_like=queryMap.get("leaved_like");
					Object leaved_isNull=queryMap.get("leaved_isNull");
					Object leaved_isNotNull=queryMap.get("leaved_isNotNull");
					Object leaved_in=queryMap.get("leaved_in");
																Object leavedupdatetime=queryMap.get("leavedupdatetime");
					Object leavedupdatetime_like=queryMap.get("leavedupdatetime_like");
					Object leavedupdatetime_isNull=queryMap.get("leavedupdatetime_isNull");
					Object leavedupdatetime_isNotNull=queryMap.get("leavedupdatetime_isNotNull");
					Object leavedupdatetime_in=queryMap.get("leavedupdatetime_in");
							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(currpositionid!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.eq, currpositionid));}
					if(currpositionid_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.gt, currpositionid_gt));}
					if(currpositionid_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.ge, currpositionid_ge));}
					if(currpositionid_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.lt, currpositionid_lt));}
					if(currpositionid_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.le, currpositionid_le));}
					if(currpositionid_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.in, currpositionid_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
		            if(leaved_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
		            if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
										
						  			            pagelist = dbManager.queryByCondition(HistoryLocationPosEntity.class,qc,pageno,pagesize);						
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
							HistoryLocationPosEntity historyLocationPos = (HistoryLocationPosEntity)dbManager.getById(id, HistoryLocationPosEntity.class);
							if(historyLocationPos!=null&&historyLocationPos.getDevId()!=null){
								dbManager.delNoTransaction(historyLocationPos.getDevId(), DevEntity.class);
							}						
						}
				     				     				     										result = dbManager.delNoTransaction(id, HistoryLocationPosEntity.class);
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
																Object currpositionid=queryMap.get("currpositionid");
					Object currpositionid_gt=queryMap.get("currpositionid_gt");
					Object currpositionid_ge=queryMap.get("currpositionid_ge");
					Object currpositionid_lt=queryMap.get("currpositionid_lt");
					Object currpositionid_le=queryMap.get("currpositionid_le");
					Object currpositionid_in=queryMap.get("currpositionid_in");
																Object currlog=queryMap.get("currlog");
					Object currlog_gt=queryMap.get("currlog_gt");
					Object currlog_ge=queryMap.get("currlog_ge");
					Object currlog_lt=queryMap.get("currlog_lt");
					Object currlog_le=queryMap.get("currlog_le");
					Object currlog_in=queryMap.get("currlog_in");
																Object currlat=queryMap.get("currlat");
					Object currlat_gt=queryMap.get("currlat_gt");
					Object currlat_ge=queryMap.get("currlat_ge");
					Object currlat_lt=queryMap.get("currlat_lt");
					Object currlat_le=queryMap.get("currlat_le");
					Object currlat_in=queryMap.get("currlat_in");
																Object leaved=queryMap.get("leaved");
					Object leaved_like=queryMap.get("leaved_like");
					Object leaved_isNull=queryMap.get("leaved_isNull");
					Object leaved_isNotNull=queryMap.get("leaved_isNotNull");
					Object leaved_in=queryMap.get("leaved_in");
																Object leavedupdatetime=queryMap.get("leavedupdatetime");
					Object leavedupdatetime_like=queryMap.get("leavedupdatetime_like");
					Object leavedupdatetime_isNull=queryMap.get("leavedupdatetime_isNull");
					Object leavedupdatetime_isNotNull=queryMap.get("leavedupdatetime_isNotNull");
					Object leavedupdatetime_in=queryMap.get("leavedupdatetime_in");
																				
			QueryCondition qc = new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(currpositionid!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.eq, currpositionid));}
					if(currpositionid_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.gt, currpositionid_gt));}
					if(currpositionid_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.ge, currpositionid_ge));}
					if(currpositionid_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.lt, currpositionid_lt));}
					if(currpositionid_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.le, currpositionid_le));}
					if(currpositionid_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRPOSITIONID, QueryCondition.in, currpositionid_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
				    if(leaved_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
				    if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(HistoryLocationPosEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
										
				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 				     					     //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){
							List<Object> list=dbManager.queryByCondition(HistoryLocationPosEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									HistoryLocationPosEntity entity=(HistoryLocationPosEntity)obj;
									strIds += entity.getDevId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(DevEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(DevEntity.class, qc1);
							}
							
						}
				     				     				     										result = dbManager.delByConditionsNoTransaction(HistoryLocationPosEntity.class,qc);				
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
