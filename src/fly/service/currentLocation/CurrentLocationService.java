package fly.service.currentLocation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;


import fly.entity.dev.DevEntity;
import fly.entity.position.PositionEntity;

import fly.entity.currentLocation.CurrentLocationEntity;
import com.framework.system.db.connect.DbUtils;
import com.framework.system.db.manager.DBManager;
import com.framework.system.db.query.PageList;
import com.framework.system.db.query.QueryCondition;
import com.framework.system.db.transaction.TransactionManager;



/**   
 * @Title: Service
 * @Description: 定位器当前状态服务类
 * @author feng.gu
 * @date 2015-09-07 14:15:36
 * @version V1.0   
 *
 */
public class CurrentLocationService {
	   private static Logger logger = Logger.getLogger(CurrentLocationService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static CurrentLocationService currentLocationService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static CurrentLocationService getInstance(){
		if(currentLocationService==null){
			currentLocationService = new CurrentLocationService();
		}
		return currentLocationService;
	   }
	                 	                 				     				     				     				     
				     	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(CurrentLocationEntity currentLocation) {			
			boolean result =false;
			if(currentLocation!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     					     //关联信息保存
					     DevEntity dev = currentLocation.getDev();
						 if(dev!=null){						   
							 dbManager.saveNoTransaction(dev);
							 currentLocation.setDevId(dev.getId());
						 }						 
				     				     				     					 					 				     				     					     //关联信息保存
					     PositionEntity position = currentLocation.getPosition();
						 if(position!=null){						   
							 dbManager.saveNoTransaction(position);
							 currentLocation.setPositionId(position.getId());
						 }						 
				     				     				     					 					 result=dbManager.saveNoTransaction(currentLocation);	
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
		public boolean saveList(List<CurrentLocationEntity> currentLocationList) {
			boolean result = false;
			if (currentLocationList != null && currentLocationList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(CurrentLocationEntity currentLocation:currentLocationList){
						if(currentLocation!=null){												  								 
								 									 					                 								     								         //关联信息保存
									     DevEntity dev = currentLocation.getDev();
										 if(dev!=null){						   
											 dbManager.saveNoTransaction(dev);
											 currentLocation.setDevId(dev.getId());
										 }									 
								     								     								     								 									 					                 								     								         //关联信息保存
									     PositionEntity position = currentLocation.getPosition();
										 if(position!=null){						   
											 dbManager.saveNoTransaction(position);
											 currentLocation.setPositionId(position.getId());
										 }									 
								     								     								     								 								 result=dbManager.saveNoTransaction(currentLocation);								 			
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
				     				     				     		 		             	                 				     		 * @param positionShow 是否查询关联信息
				     				     				     		 		 * @param obj
		 */
		public CurrentLocationEntity getById(Integer id,Boolean devShow,Boolean positionShow) {
			CurrentLocationEntity obj = null;
			if (id!=null) {
				obj = (CurrentLocationEntity)dbManager.getById(id, CurrentLocationEntity.class);
									 	                 				     				        //查询关联内容
						if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getDevId()>0){
							DevEntity dev = (DevEntity)dbManager.getById(obj.getDevId(), DevEntity.class);
							obj.setDev(dev);        	  	  
						}
				     				     				     				   					 	                 				     				        //查询关联内容
						if(positionShow!=null&&positionShow.booleanValue()&&obj!=null&&obj.getPositionId()>0){
							PositionEntity position = (PositionEntity)dbManager.getById(obj.getPositionId(), PositionEntity.class);
							obj.setPosition(position);        	  	  
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
																Object positionId=queryMap.get("positionId");
					Object positionId_gt=queryMap.get("positionId_gt");
					Object positionId_ge=queryMap.get("positionId_ge");
					Object positionId_lt=queryMap.get("positionId_lt");
					Object positionId_le=queryMap.get("positionId_le");
					Object positionId_in=queryMap.get("positionId_in");
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
																Object bodystate=queryMap.get("bodystate");
					Object bodystate_like=queryMap.get("bodystate_like");
					Object bodystate_isNull=queryMap.get("bodystate_isNull");
					Object bodystate_isNotNull=queryMap.get("bodystate_isNotNull");
					Object bodystate_in=queryMap.get("bodystate_in");
																Object manualalarm=queryMap.get("manualalarm");
					Object manualalarm_like=queryMap.get("manualalarm_like");
					Object manualalarm_isNull=queryMap.get("manualalarm_isNull");
					Object manualalarm_isNotNull=queryMap.get("manualalarm_isNotNull");
					Object manualalarm_in=queryMap.get("manualalarm_in");
																Object bodyupdatetime=queryMap.get("bodyupdatetime");
					Object bodyupdatetime_like=queryMap.get("bodyupdatetime_like");
					Object bodyupdatetime_isNull=queryMap.get("bodyupdatetime_isNull");
					Object bodyupdatetime_isNotNull=queryMap.get("bodyupdatetime_isNotNull");
					Object bodyupdatetime_in=queryMap.get("bodyupdatetime_in");
																Object moving=queryMap.get("moving");
					Object moving_like=queryMap.get("moving_like");
					Object moving_isNull=queryMap.get("moving_isNull");
					Object moving_isNotNull=queryMap.get("moving_isNotNull");
					Object moving_in=queryMap.get("moving_in");
																Object movingupdatetime=queryMap.get("movingupdatetime");
					Object movingupdatetime_like=queryMap.get("movingupdatetime_like");
					Object movingupdatetime_isNull=queryMap.get("movingupdatetime_isNull");
					Object movingupdatetime_isNotNull=queryMap.get("movingupdatetime_isNotNull");
					Object movingupdatetime_in=queryMap.get("movingupdatetime_in");
																Object normal=queryMap.get("normal");
					Object normal_like=queryMap.get("normal_like");
					Object normal_isNull=queryMap.get("normal_isNull");
					Object normal_isNotNull=queryMap.get("normal_isNotNull");
					Object normal_in=queryMap.get("normal_in");
																Object online=queryMap.get("online");
					Object online_like=queryMap.get("online_like");
					Object online_isNull=queryMap.get("online_isNull");
					Object online_isNotNull=queryMap.get("online_isNotNull");
					Object online_in=queryMap.get("online_in");
																Object power=queryMap.get("power");
					Object power_like=queryMap.get("power_like");
					Object power_isNull=queryMap.get("power_isNull");
					Object power_isNotNull=queryMap.get("power_isNotNull");
					Object power_in=queryMap.get("power_in");
																Object devupdatetime=queryMap.get("devupdatetime");
					Object devupdatetime_like=queryMap.get("devupdatetime_like");
					Object devupdatetime_isNull=queryMap.get("devupdatetime_isNull");
					Object devupdatetime_isNotNull=queryMap.get("devupdatetime_isNotNull");
					Object devupdatetime_in=queryMap.get("devupdatetime_in");
							
			

																				
			
			QueryCondition qc = new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(positionId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.eq, positionId));}
					if(positionId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.gt, positionId_gt));}
					if(positionId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.ge, positionId_ge));}
					if(positionId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.lt, positionId_lt));}
					if(positionId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.le, positionId_le));}
					if(positionId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.in, positionId_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
		            if(leaved_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
		            if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
																if(bodystate!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.eq, bodystate));}
		            if(bodystate_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.like, bodystate_like));}
		            if(bodystate_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNull, bodystate_isNull));}
		            if(bodystate_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNotNull, bodystate_isNotNull));}
		            if(bodystate_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.in, bodystate_in));}
																if(manualalarm!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.eq, manualalarm));}
		            if(manualalarm_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.like, manualalarm_like));}
		            if(manualalarm_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNull, manualalarm_isNull));}
		            if(manualalarm_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNotNull, manualalarm_isNotNull));}
		            if(manualalarm_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.in, manualalarm_in));}
																if(bodyupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.eq, bodyupdatetime));}
		            if(bodyupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.like, bodyupdatetime_like));}
		            if(bodyupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNull, bodyupdatetime_isNull));}
		            if(bodyupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNotNull, bodyupdatetime_isNotNull));}
		            if(bodyupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.in, bodyupdatetime_in));}
																if(moving!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.eq, moving));}
		            if(moving_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.like, moving_like));}
		            if(moving_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNull, moving_isNull));}
		            if(moving_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNotNull, moving_isNotNull));}
		            if(moving_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.in, moving_in));}
																if(movingupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.eq, movingupdatetime));}
		            if(movingupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.like, movingupdatetime_like));}
		            if(movingupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNull, movingupdatetime_isNull));}
		            if(movingupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNotNull, movingupdatetime_isNotNull));}
		            if(movingupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.in, movingupdatetime_in));}
																if(normal!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.eq, normal));}
		            if(normal_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.like, normal_like));}
		            if(normal_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNull, normal_isNull));}
		            if(normal_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNotNull, normal_isNotNull));}
		            if(normal_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.in, normal_in));}
																if(online!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.eq, online));}
		            if(online_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.like, online_like));}
		            if(online_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNull, online_isNull));}
		            if(online_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNotNull, online_isNotNull));}
		            if(online_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.in, online_in));}
																if(power!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.eq, power));}
		            if(power_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.like, power_like));}
		            if(power_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNull, power_isNull));}
		            if(power_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNotNull, power_isNotNull));}
		            if(power_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.in, power_in));}
																if(devupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.eq, devupdatetime));}
		            if(devupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.like, devupdatetime_like));}
		            if(devupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNull, devupdatetime_isNull));}
		            if(devupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNotNull, devupdatetime_isNotNull));}
		            if(devupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.in, devupdatetime_in));}
										
						  						  			            list = dbManager.queryByCondition(CurrentLocationEntity.class,qc);						
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
																Object positionId=queryMap.get("positionId");
					Object positionId_gt=queryMap.get("positionId_gt");
					Object positionId_ge=queryMap.get("positionId_ge");
					Object positionId_lt=queryMap.get("positionId_lt");
					Object positionId_le=queryMap.get("positionId_le");
					Object positionId_in=queryMap.get("positionId_in");
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
																Object bodystate=queryMap.get("bodystate");
					Object bodystate_like=queryMap.get("bodystate_like");
					Object bodystate_isNull=queryMap.get("bodystate_isNull");
					Object bodystate_isNotNull=queryMap.get("bodystate_isNotNull");
					Object bodystate_in=queryMap.get("bodystate_in");
																Object manualalarm=queryMap.get("manualalarm");
					Object manualalarm_like=queryMap.get("manualalarm_like");
					Object manualalarm_isNull=queryMap.get("manualalarm_isNull");
					Object manualalarm_isNotNull=queryMap.get("manualalarm_isNotNull");
					Object manualalarm_in=queryMap.get("manualalarm_in");
																Object bodyupdatetime=queryMap.get("bodyupdatetime");
					Object bodyupdatetime_like=queryMap.get("bodyupdatetime_like");
					Object bodyupdatetime_isNull=queryMap.get("bodyupdatetime_isNull");
					Object bodyupdatetime_isNotNull=queryMap.get("bodyupdatetime_isNotNull");
					Object bodyupdatetime_in=queryMap.get("bodyupdatetime_in");
																Object moving=queryMap.get("moving");
					Object moving_like=queryMap.get("moving_like");
					Object moving_isNull=queryMap.get("moving_isNull");
					Object moving_isNotNull=queryMap.get("moving_isNotNull");
					Object moving_in=queryMap.get("moving_in");
																Object movingupdatetime=queryMap.get("movingupdatetime");
					Object movingupdatetime_like=queryMap.get("movingupdatetime_like");
					Object movingupdatetime_isNull=queryMap.get("movingupdatetime_isNull");
					Object movingupdatetime_isNotNull=queryMap.get("movingupdatetime_isNotNull");
					Object movingupdatetime_in=queryMap.get("movingupdatetime_in");
																Object normal=queryMap.get("normal");
					Object normal_like=queryMap.get("normal_like");
					Object normal_isNull=queryMap.get("normal_isNull");
					Object normal_isNotNull=queryMap.get("normal_isNotNull");
					Object normal_in=queryMap.get("normal_in");
																Object online=queryMap.get("online");
					Object online_like=queryMap.get("online_like");
					Object online_isNull=queryMap.get("online_isNull");
					Object online_isNotNull=queryMap.get("online_isNotNull");
					Object online_in=queryMap.get("online_in");
																Object power=queryMap.get("power");
					Object power_like=queryMap.get("power_like");
					Object power_isNull=queryMap.get("power_isNull");
					Object power_isNotNull=queryMap.get("power_isNotNull");
					Object power_in=queryMap.get("power_in");
																Object devupdatetime=queryMap.get("devupdatetime");
					Object devupdatetime_like=queryMap.get("devupdatetime_like");
					Object devupdatetime_isNull=queryMap.get("devupdatetime_isNull");
					Object devupdatetime_isNotNull=queryMap.get("devupdatetime_isNotNull");
					Object devupdatetime_in=queryMap.get("devupdatetime_in");
							
			

																				
			
			QueryCondition qc = new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(positionId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.eq, positionId));}
					if(positionId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.gt, positionId_gt));}
					if(positionId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.ge, positionId_ge));}
					if(positionId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.lt, positionId_lt));}
					if(positionId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.le, positionId_le));}
					if(positionId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.in, positionId_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
		            if(leaved_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
		            if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
																if(bodystate!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.eq, bodystate));}
		            if(bodystate_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.like, bodystate_like));}
		            if(bodystate_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNull, bodystate_isNull));}
		            if(bodystate_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNotNull, bodystate_isNotNull));}
		            if(bodystate_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.in, bodystate_in));}
																if(manualalarm!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.eq, manualalarm));}
		            if(manualalarm_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.like, manualalarm_like));}
		            if(manualalarm_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNull, manualalarm_isNull));}
		            if(manualalarm_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNotNull, manualalarm_isNotNull));}
		            if(manualalarm_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.in, manualalarm_in));}
																if(bodyupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.eq, bodyupdatetime));}
		            if(bodyupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.like, bodyupdatetime_like));}
		            if(bodyupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNull, bodyupdatetime_isNull));}
		            if(bodyupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNotNull, bodyupdatetime_isNotNull));}
		            if(bodyupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.in, bodyupdatetime_in));}
																if(moving!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.eq, moving));}
		            if(moving_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.like, moving_like));}
		            if(moving_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNull, moving_isNull));}
		            if(moving_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNotNull, moving_isNotNull));}
		            if(moving_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.in, moving_in));}
																if(movingupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.eq, movingupdatetime));}
		            if(movingupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.like, movingupdatetime_like));}
		            if(movingupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNull, movingupdatetime_isNull));}
		            if(movingupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNotNull, movingupdatetime_isNotNull));}
		            if(movingupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.in, movingupdatetime_in));}
																if(normal!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.eq, normal));}
		            if(normal_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.like, normal_like));}
		            if(normal_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNull, normal_isNull));}
		            if(normal_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNotNull, normal_isNotNull));}
		            if(normal_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.in, normal_in));}
																if(online!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.eq, online));}
		            if(online_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.like, online_like));}
		            if(online_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNull, online_isNull));}
		            if(online_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNotNull, online_isNotNull));}
		            if(online_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.in, online_in));}
																if(power!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.eq, power));}
		            if(power_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.like, power_like));}
		            if(power_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNull, power_isNull));}
		            if(power_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNotNull, power_isNotNull));}
		            if(power_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.in, power_in));}
																if(devupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.eq, devupdatetime));}
		            if(devupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.like, devupdatetime_like));}
		            if(devupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNull, devupdatetime_isNull));}
		            if(devupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNotNull, devupdatetime_isNotNull));}
		            if(devupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.in, devupdatetime_in));}
										
						  						  			            pagelist = dbManager.queryByCondition(CurrentLocationEntity.class,qc,pageno,pagesize);						
			return pagelist;
		}
		
		/**
		 * 删除记录
		 * 
		 * @param id 主键
		 * @param obj
		 */
		public boolean del(Integer id,Boolean delDev,Boolean delPosition) {
			boolean result = false;
			if (id !=null&&id>0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 					
										 	                 				     					    //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){	
							CurrentLocationEntity currentLocation = (CurrentLocationEntity)dbManager.getById(id, CurrentLocationEntity.class);
							if(currentLocation!=null&&currentLocation.getDevId()!=null){
								dbManager.delNoTransaction(currentLocation.getDevId(), DevEntity.class);
							}						
						}
				     				     				     										 	                 				     					    //删除关联信息
						if(delPosition!=null&&delPosition.booleanValue()){	
							CurrentLocationEntity currentLocation = (CurrentLocationEntity)dbManager.getById(id, CurrentLocationEntity.class);
							if(currentLocation!=null&&currentLocation.getPositionId()!=null){
								dbManager.delNoTransaction(currentLocation.getPositionId(), PositionEntity.class);
							}						
						}
				     				     				     										result = dbManager.delNoTransaction(id, CurrentLocationEntity.class);
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
		public boolean delList(Map<String,Object> queryMap,Boolean delDev,Boolean delPosition) {
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
																Object positionId=queryMap.get("positionId");
					Object positionId_gt=queryMap.get("positionId_gt");
					Object positionId_ge=queryMap.get("positionId_ge");
					Object positionId_lt=queryMap.get("positionId_lt");
					Object positionId_le=queryMap.get("positionId_le");
					Object positionId_in=queryMap.get("positionId_in");
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
																Object bodystate=queryMap.get("bodystate");
					Object bodystate_like=queryMap.get("bodystate_like");
					Object bodystate_isNull=queryMap.get("bodystate_isNull");
					Object bodystate_isNotNull=queryMap.get("bodystate_isNotNull");
					Object bodystate_in=queryMap.get("bodystate_in");
																Object manualalarm=queryMap.get("manualalarm");
					Object manualalarm_like=queryMap.get("manualalarm_like");
					Object manualalarm_isNull=queryMap.get("manualalarm_isNull");
					Object manualalarm_isNotNull=queryMap.get("manualalarm_isNotNull");
					Object manualalarm_in=queryMap.get("manualalarm_in");
																Object bodyupdatetime=queryMap.get("bodyupdatetime");
					Object bodyupdatetime_like=queryMap.get("bodyupdatetime_like");
					Object bodyupdatetime_isNull=queryMap.get("bodyupdatetime_isNull");
					Object bodyupdatetime_isNotNull=queryMap.get("bodyupdatetime_isNotNull");
					Object bodyupdatetime_in=queryMap.get("bodyupdatetime_in");
																Object moving=queryMap.get("moving");
					Object moving_like=queryMap.get("moving_like");
					Object moving_isNull=queryMap.get("moving_isNull");
					Object moving_isNotNull=queryMap.get("moving_isNotNull");
					Object moving_in=queryMap.get("moving_in");
																Object movingupdatetime=queryMap.get("movingupdatetime");
					Object movingupdatetime_like=queryMap.get("movingupdatetime_like");
					Object movingupdatetime_isNull=queryMap.get("movingupdatetime_isNull");
					Object movingupdatetime_isNotNull=queryMap.get("movingupdatetime_isNotNull");
					Object movingupdatetime_in=queryMap.get("movingupdatetime_in");
																Object normal=queryMap.get("normal");
					Object normal_like=queryMap.get("normal_like");
					Object normal_isNull=queryMap.get("normal_isNull");
					Object normal_isNotNull=queryMap.get("normal_isNotNull");
					Object normal_in=queryMap.get("normal_in");
																Object online=queryMap.get("online");
					Object online_like=queryMap.get("online_like");
					Object online_isNull=queryMap.get("online_isNull");
					Object online_isNotNull=queryMap.get("online_isNotNull");
					Object online_in=queryMap.get("online_in");
																Object power=queryMap.get("power");
					Object power_like=queryMap.get("power_like");
					Object power_isNull=queryMap.get("power_isNull");
					Object power_isNotNull=queryMap.get("power_isNotNull");
					Object power_in=queryMap.get("power_in");
																Object devupdatetime=queryMap.get("devupdatetime");
					Object devupdatetime_like=queryMap.get("devupdatetime_like");
					Object devupdatetime_isNull=queryMap.get("devupdatetime_isNull");
					Object devupdatetime_isNotNull=queryMap.get("devupdatetime_isNotNull");
					Object devupdatetime_in=queryMap.get("devupdatetime_in");
																											
			QueryCondition qc = new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEV_ID, QueryCondition.in, devId_in));}
																if(positionId!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.eq, positionId));}
					if(positionId_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.gt, positionId_gt));}
					if(positionId_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.ge, positionId_ge));}
					if(positionId_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.lt, positionId_lt));}
					if(positionId_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.le, positionId_le));}
					if(positionId_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POSITION_ID, QueryCondition.in, positionId_in));}
																if(currlog!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.eq, currlog));}
					if(currlog_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.gt, currlog_gt));}
					if(currlog_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.ge, currlog_ge));}
					if(currlog_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.lt, currlog_lt));}
					if(currlog_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.le, currlog_le));}
					if(currlog_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLOG, QueryCondition.in, currlog_in));}
																if(currlat!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.eq, currlat));}
					if(currlat_gt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.gt, currlat_gt));}
					if(currlat_ge!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.ge, currlat_ge));}
					if(currlat_lt!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.lt, currlat_lt));}
					if(currlat_le!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.le, currlat_le));}
					if(currlat_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.CURRLAT, QueryCondition.in, currlat_in));}
																if(leaved!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.eq, leaved));}
		            if(leaved_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.like, leaved_like));}
		            if(leaved_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNull, leaved_isNull));}
		            if(leaved_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.isNotNull, leaved_isNotNull));}
				    if(leaved_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVED, QueryCondition.in, leaved_in));}
																if(leavedupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.eq, leavedupdatetime));}
		            if(leavedupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.like, leavedupdatetime_like));}
		            if(leavedupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNull, leavedupdatetime_isNull));}
		            if(leavedupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.isNotNull, leavedupdatetime_isNotNull));}
				    if(leavedupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.LEAVEDUPDATETIME, QueryCondition.in, leavedupdatetime_in));}
																if(bodystate!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.eq, bodystate));}
		            if(bodystate_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.like, bodystate_like));}
		            if(bodystate_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNull, bodystate_isNull));}
		            if(bodystate_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.isNotNull, bodystate_isNotNull));}
				    if(bodystate_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYSTATE, QueryCondition.in, bodystate_in));}
																if(manualalarm!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.eq, manualalarm));}
		            if(manualalarm_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.like, manualalarm_like));}
		            if(manualalarm_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNull, manualalarm_isNull));}
		            if(manualalarm_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.isNotNull, manualalarm_isNotNull));}
				    if(manualalarm_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MANUALALARM, QueryCondition.in, manualalarm_in));}
																if(bodyupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.eq, bodyupdatetime));}
		            if(bodyupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.like, bodyupdatetime_like));}
		            if(bodyupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNull, bodyupdatetime_isNull));}
		            if(bodyupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.isNotNull, bodyupdatetime_isNotNull));}
				    if(bodyupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.BODYUPDATETIME, QueryCondition.in, bodyupdatetime_in));}
																if(moving!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.eq, moving));}
		            if(moving_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.like, moving_like));}
		            if(moving_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNull, moving_isNull));}
		            if(moving_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.isNotNull, moving_isNotNull));}
				    if(moving_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVING, QueryCondition.in, moving_in));}
																if(movingupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.eq, movingupdatetime));}
		            if(movingupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.like, movingupdatetime_like));}
		            if(movingupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNull, movingupdatetime_isNull));}
		            if(movingupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.isNotNull, movingupdatetime_isNotNull));}
				    if(movingupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.MOVINGUPDATETIME, QueryCondition.in, movingupdatetime_in));}
																if(normal!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.eq, normal));}
		            if(normal_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.like, normal_like));}
		            if(normal_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNull, normal_isNull));}
		            if(normal_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.isNotNull, normal_isNotNull));}
				    if(normal_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.NORMAL, QueryCondition.in, normal_in));}
																if(online!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.eq, online));}
		            if(online_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.like, online_like));}
		            if(online_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNull, online_isNull));}
		            if(online_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.isNotNull, online_isNotNull));}
				    if(online_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.ONLINE, QueryCondition.in, online_in));}
																if(power!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.eq, power));}
		            if(power_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.like, power_like));}
		            if(power_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNull, power_isNull));}
		            if(power_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.isNotNull, power_isNotNull));}
				    if(power_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.POWER, QueryCondition.in, power_in));}
																if(devupdatetime!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.eq, devupdatetime));}
		            if(devupdatetime_like!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.like, devupdatetime_like));}
		            if(devupdatetime_isNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNull, devupdatetime_isNull));}
		            if(devupdatetime_isNotNull!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.isNotNull, devupdatetime_isNotNull));}
				    if(devupdatetime_in!=null){qc.andCondition(new QueryCondition(CurrentLocationEntity.DEVUPDATETIME, QueryCondition.in, devupdatetime_in));}
										
				            				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 				     					     //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){
							List<Object> list=dbManager.queryByCondition(CurrentLocationEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									CurrentLocationEntity entity=(CurrentLocationEntity)obj;
									strIds += entity.getDevId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(DevEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(DevEntity.class, qc1);
							}
							
						}
				     				     				     										 	                 				     					     //删除关联信息
						if(delPosition!=null&&delPosition.booleanValue()){
							List<Object> list=dbManager.queryByCondition(CurrentLocationEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									CurrentLocationEntity entity=(CurrentLocationEntity)obj;
									strIds += entity.getPositionId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(PositionEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(PositionEntity.class, qc1);
							}
							
						}
				     				     				     										result = dbManager.delByConditionsNoTransaction(CurrentLocationEntity.class,qc);				
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
