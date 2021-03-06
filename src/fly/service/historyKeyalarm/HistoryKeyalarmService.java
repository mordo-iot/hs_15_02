package fly.service.historyKeyalarm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;


import fly.entity.dev.DevEntity;

import fly.entity.historyKeyalarm.HistoryKeyalarmEntity;
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
 * @Description: 一键报警历史信息服务类
 * @author feng.gu
 * @date 2015-09-17 13:34:42
 * @version V1.0   
 *
 */
public class HistoryKeyalarmService {
	   private static Logger logger = Logger.getLogger(HistoryKeyalarmService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static HistoryKeyalarmService historyKeyalarmService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static HistoryKeyalarmService getInstance(){
		if(historyKeyalarmService==null){
			historyKeyalarmService = new HistoryKeyalarmService();
		}
		return historyKeyalarmService;
	   }
	                 	                 				     				     				     				     
				     	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(HistoryKeyalarmEntity historyKeyalarm) {			
			boolean result =false;
			if(historyKeyalarm!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     					     //关联信息保存
					     DevEntity dev = historyKeyalarm.getDev();
						 if(dev!=null){						   
							 dbManager.saveNoTransaction(dev);
							 historyKeyalarm.setDevId(dev.getId());
						 }						 
				     				     				     					 					 result=dbManager.saveNoTransaction(historyKeyalarm);	
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
		public boolean saveList(List<HistoryKeyalarmEntity> historyKeyalarmList) {
			boolean result = false;
			if (historyKeyalarmList != null && historyKeyalarmList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(HistoryKeyalarmEntity historyKeyalarm:historyKeyalarmList){
						if(historyKeyalarm!=null){												  								 
								 									 					                 								     								         //关联信息保存
									     DevEntity dev = historyKeyalarm.getDev();
										 if(dev!=null){						   
											 dbManager.saveNoTransaction(dev);
											 historyKeyalarm.setDevId(dev.getId());
										 }									 
								     								     								     								 								 result=dbManager.saveNoTransaction(historyKeyalarm);								 			
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
		public HistoryKeyalarmEntity getById(Integer id,Boolean devShow) {
			HistoryKeyalarmEntity obj = null;
			if (id!=null) {
				obj = (HistoryKeyalarmEntity)dbManager.getById(id, HistoryKeyalarmEntity.class);
									 	                 				     				        //查询关联内容
						if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getDevId()>0){
							DevEntity dev = (DevEntity)dbManager.getById(obj.getDevId(), DevEntity.class);
							obj.setDev(dev);        	  	  
						}
				     				     				     				   			}
			return obj;
		}
		
		/**
		 * 根据条件查询记录集合（不分页 不带排序 不级联查询）
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
																										Object alarmupdatetime_gt=queryMap.get("alarmupdatetime_gt");
					Object alarmupdatetime_ge=queryMap.get("alarmupdatetime_ge");
					Object alarmupdatetime_lt=queryMap.get("alarmupdatetime_lt");
					Object alarmupdatetime_le=queryMap.get("alarmupdatetime_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.in, devId_in));}
															  					if(alarm!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.in, alarm_in));}
				  															  					if(alarmupdatetime_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.gt, alarmupdatetime_gt));}
					if(alarmupdatetime_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.ge, alarmupdatetime_ge));}
					if(alarmupdatetime_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.lt, alarmupdatetime_lt));}
					if(alarmupdatetime_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.le, alarmupdatetime_le));}
				  										
						  			            list = dbManager.queryByCondition(HistoryKeyalarmEntity.class,qc);           					
			return list;
		}
		
		
		/**
		 * 根据条件查询记录集合（不分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 		             	                 				     		 * @param devShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     		 		 * @return
		 */
		public List<Object> getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,Boolean devShow) {
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
																										Object alarmupdatetime_gt=queryMap.get("alarmupdatetime_gt");
					Object alarmupdatetime_ge=queryMap.get("alarmupdatetime_ge");
					Object alarmupdatetime_lt=queryMap.get("alarmupdatetime_lt");
					Object alarmupdatetime_le=queryMap.get("alarmupdatetime_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.in, devId_in));}
															  					if(alarm!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.in, alarm_in));}
				  															  					if(alarmupdatetime_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.gt, alarmupdatetime_gt));}
					if(alarmupdatetime_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.ge, alarmupdatetime_ge));}
					if(alarmupdatetime_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.lt, alarmupdatetime_lt));}
					if(alarmupdatetime_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.le, alarmupdatetime_le));}
				  										
						  						OrderByCondition oc = null;
			if(orderList!=null&&orderList.size()>0){
				for(int i=0;i<orderList.size();i++){
					OrderVO order = orderList.get(i);
					String orderColumnt =null;
					String orderType=null;
					if(order.getName()!=null&&!"".equals(order.getName())){
						orderColumnt = StringUtil.formatFieldToColumnt(order.getName());
						orderType = order.getOrderType();
						if(orderType==null||"".equals(orderType.trim())){
							orderType=OrderByCondition.desc;
						}
						if(i==0){
							oc = new OrderByCondition(orderColumnt,orderType);
						}else{
							oc.orderByCondition(new OrderByCondition(orderColumnt,orderType));
						}					
					}
					
				}
			}			
            list = dbManager.queryByConditions(HistoryKeyalarmEntity.class,qc,oc);
                                    int a=0;
            					             	                 				     				     if(devShow!=null&&devShow.booleanValue()){
						a++;
					 }
				     				     				     		    		                if(a>0&&list!=null&&list.size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<list.size();i++){
					HistoryKeyalarmEntity obj = (HistoryKeyalarmEntity)list.get(i);
										 	                 				     				        //查询关联内容
						if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getDevId()>0){
							DevEntity dev = (DevEntity)dbManager.getById(obj.getDevId(), DevEntity.class);
							obj.setDev(dev);        	  	  
						}
				     				     				     				    					result.add(obj);					
				}
				list=result;			
		}	
					return list;
		}
		
		/**
		 * 根据条件查询记录集合（带分页 不带排序 不级联查询）
		 * @param queryMap 查询条件集合
		 * @param pageno 查询页码
		 * @param pagesize 查询每页记录条数		
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
				   															  					Object alarmupdatetime_gt=queryMap.get("alarmupdatetime_gt");
					Object alarmupdatetime_ge=queryMap.get("alarmupdatetime_ge");
					Object alarmupdatetime_lt=queryMap.get("alarmupdatetime_lt");
					Object alarmupdatetime_le=queryMap.get("alarmupdatetime_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.in, devId_in));}
															  					if(alarm!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.in, alarm_in));}
				  															  					if(alarmupdatetime_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.gt, alarmupdatetime_gt));}
					if(alarmupdatetime_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.ge, alarmupdatetime_ge));}
					if(alarmupdatetime_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.lt, alarmupdatetime_lt));}
					if(alarmupdatetime_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.le, alarmupdatetime_le));}
				  										
						  			            pagelist = dbManager.queryByCondition(HistoryKeyalarmEntity.class,qc,pageno,pagesize);	                      			
			return pagelist;
		}
		
		/**
		 * 根据条件查询记录集合（带分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 * @param pageno 查询页码
		 * @param pagesize 查询每页记录条数
		 		             	                 				     		 * @param devShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     		 		 * @return
		 */
		public PageList getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,int pageno,int pagesize,Boolean devShow) {
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
				   															  					Object alarmupdatetime_gt=queryMap.get("alarmupdatetime_gt");
					Object alarmupdatetime_ge=queryMap.get("alarmupdatetime_ge");
					Object alarmupdatetime_lt=queryMap.get("alarmupdatetime_lt");
					Object alarmupdatetime_le=queryMap.get("alarmupdatetime_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.in, devId_in));}
															  					if(alarm!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.in, alarm_in));}
				  															  					if(alarmupdatetime_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.gt, alarmupdatetime_gt));}
					if(alarmupdatetime_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.ge, alarmupdatetime_ge));}
					if(alarmupdatetime_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.lt, alarmupdatetime_lt));}
					if(alarmupdatetime_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.le, alarmupdatetime_le));}
				  										
						  						OrderByCondition oc = null;
			if(orderList!=null&&orderList.size()>0){
				for(int i=0;i<orderList.size();i++){
					OrderVO order = orderList.get(i);
					String orderColumnt =null;
					String orderType=null;
					if(order.getName()!=null&&!"".equals(order.getName())){
						orderColumnt = StringUtil.formatFieldToColumnt(order.getName());
						orderType = order.getOrderType();
						if(orderType==null||"".equals(orderType.trim())){
							orderType=OrderByCondition.desc;
						}
						if(i==0){
							oc = new OrderByCondition(orderColumnt,orderType);
						}else{
							oc.orderByCondition(new OrderByCondition(orderColumnt,orderType));
						}					
					}
					
				}
			}		
            pagelist = dbManager.queryByConditions(HistoryKeyalarmEntity.class,qc,oc,pageno,pagesize);	           
                                    int a=0;
            					             	                 				     				     if(devShow!=null&&devShow.booleanValue()){
						a++;
					 }
				     				     				     		    		                if(a>0&&pagelist!=null&&pagelist.getResultList()!=null&&pagelist.getResultList().size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<pagelist.getResultList().size();i++){
					HistoryKeyalarmEntity obj = (HistoryKeyalarmEntity)pagelist.getResultList().get(i);
										 	                 				     				        //查询关联内容
						if(devShow!=null&&devShow.booleanValue()&&obj!=null&&obj.getDevId()>0){
							DevEntity dev = (DevEntity)dbManager.getById(obj.getDevId(), DevEntity.class);
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
							HistoryKeyalarmEntity historyKeyalarm = (HistoryKeyalarmEntity)dbManager.getById(id, HistoryKeyalarmEntity.class);
							if(historyKeyalarm!=null&&historyKeyalarm.getDevId()!=null){
								dbManager.delNoTransaction(historyKeyalarm.getDevId(), DevEntity.class);
							}						
						}
				     				     				     										result = dbManager.delNoTransaction(id, HistoryKeyalarmEntity.class);
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
				  															  					Object alarmupdatetime_gt=queryMap.get("alarmupdatetime_gt");
					Object alarmupdatetime_ge=queryMap.get("alarmupdatetime_ge");
					Object alarmupdatetime_lt=queryMap.get("alarmupdatetime_lt");
					Object alarmupdatetime_le=queryMap.get("alarmupdatetime_le");
				  																				
			QueryCondition qc = new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ID, QueryCondition.in, id_in));}
																if(devId!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, devId));}
					if(devId_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.gt, devId_gt));}
					if(devId_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.ge, devId_ge));}
					if(devId_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.lt, devId_lt));}
					if(devId_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.le, devId_le));}
					if(devId_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.DEV_ID, QueryCondition.in, devId_in));}
															  					if(alarm!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.eq, alarm));}
		            if(alarm_like!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.like, alarm_like));}
		            if(alarm_isNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNull, alarm_isNull));}
		            if(alarm_isNotNull!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.isNotNull, alarm_isNotNull));}
				    if(alarm_in!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARM, QueryCondition.in, alarm_in));}
				  															  					if(alarmupdatetime_gt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.gt, alarmupdatetime_gt));}
					if(alarmupdatetime_ge!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.ge, alarmupdatetime_ge));}
					if(alarmupdatetime_lt!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.lt, alarmupdatetime_lt));}
					if(alarmupdatetime_le!=null){qc.andCondition(new QueryCondition(HistoryKeyalarmEntity.ALARMUPDATETIME, QueryCondition.le, alarmupdatetime_le));}
				  										
				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 				     					     //删除关联信息
						if(delDev!=null&&delDev.booleanValue()){
							List<Object> list=dbManager.queryByCondition(HistoryKeyalarmEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									HistoryKeyalarmEntity entity=(HistoryKeyalarmEntity)obj;
									strIds += entity.getDevId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(DevEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(DevEntity.class, qc1);
							}
							
						}
				     				     				     										result = dbManager.delByConditionsNoTransaction(HistoryKeyalarmEntity.class,qc);				
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
