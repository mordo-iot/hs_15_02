package fly.service.userLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;


import fly.entity.user.UserEntity;

import fly.entity.userLog.UserLogEntity;
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
 * @Description: 系统日志服务类
 * @author feng.gu
 * @date 2015-09-17 13:34:40
 * @version V1.0   
 *
 */
public class UserLogService {
	   private static Logger logger = Logger.getLogger(UserLogService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static UserLogService userLogService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static UserLogService getInstance(){
		if(userLogService==null){
			userLogService = new UserLogService();
		}
		return userLogService;
	   }
	                 	                 				     				     				     				     
				     	 
	   /**
		 * 保存记录
		 * 
		 * @param obj
		 */
		public boolean save(UserLogEntity userLog) {			
			boolean result =false;
			if(userLog!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     					     //关联信息保存
					     UserEntity user = userLog.getUser();
						 if(user!=null){						   
							 dbManager.saveNoTransaction(user);
							 userLog.setUserId(user.getId());
						 }						 
				     				     				     					 					 result=dbManager.saveNoTransaction(userLog);	
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
		public boolean saveList(List<UserLogEntity> userLogList) {
			boolean result = false;
			if (userLogList != null && userLogList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(UserLogEntity userLog:userLogList){
						if(userLog!=null){												  								 
								 									 					                 								     								         //关联信息保存
									     UserEntity user = userLog.getUser();
										 if(user!=null){						   
											 dbManager.saveNoTransaction(user);
											 userLog.setUserId(user.getId());
										 }									 
								     								     								     								 								 result=dbManager.saveNoTransaction(userLog);								 			
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
		 		             	                 				     		 * @param userShow 是否查询关联信息
				     				     				     		 		 * @param obj
		 */
		public UserLogEntity getById(Integer id,Boolean userShow) {
			UserLogEntity obj = null;
			if (id!=null) {
				obj = (UserLogEntity)dbManager.getById(id, UserLogEntity.class);
									 	                 				     				        //查询关联内容
						if(userShow!=null&&userShow.booleanValue()&&obj!=null&&obj.getUserId()>0){
							UserEntity user = (UserEntity)dbManager.getById(obj.getUserId(), UserEntity.class);
							obj.setUser(user);        	  	  
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
																Object userId=queryMap.get("userId");
					Object userId_gt=queryMap.get("userId_gt");
					Object userId_ge=queryMap.get("userId_ge");
					Object userId_lt=queryMap.get("userId_lt");
					Object userId_le=queryMap.get("userId_le");
					Object userId_in=queryMap.get("userId_in");
																					Object log=queryMap.get("log");
					Object log_like=queryMap.get("log_like");
					Object log_isNull=queryMap.get("log_isNull");
					Object log_isNotNull=queryMap.get("log_isNotNull");
					Object log_in=queryMap.get("log_in");
																					Object level=queryMap.get("level");
					Object level_gt=queryMap.get("level_gt");
					Object level_ge=queryMap.get("level_ge");
					Object level_lt=queryMap.get("level_lt");
					Object level_le=queryMap.get("level_le");
					Object level_in=queryMap.get("level_in");
																					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(UserLogEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.in, id_in));}
																if(userId!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, userId));}
					if(userId_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.gt, userId_gt));}
					if(userId_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.ge, userId_ge));}
					if(userId_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.lt, userId_lt));}
					if(userId_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.le, userId_le));}
					if(userId_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, userId_in));}
															  					if(log!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.eq, log));}
		            if(log_like!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.like, log_like));}
		            if(log_isNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNull, log_isNull));}
		            if(log_isNotNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNotNull, log_isNotNull));}
				    if(log_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.in, log_in));}
				  																if(level!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.eq, level));}
					if(level_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.gt, level_gt));}
					if(level_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.ge, level_ge));}
					if(level_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.lt, level_lt));}
					if(level_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.le, level_le));}
					if(level_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.in, level_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  										
						  			            list = dbManager.queryByCondition(UserLogEntity.class,qc);           					
			return list;
		}
		
		
		/**
		 * 根据条件查询记录集合（不分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 		             	                 				     		 * @param userShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     		 		 * @return
		 */
		public List<Object> getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,Boolean userShow) {
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
																Object userId=queryMap.get("userId");
					Object userId_gt=queryMap.get("userId_gt");
					Object userId_ge=queryMap.get("userId_ge");
					Object userId_lt=queryMap.get("userId_lt");
					Object userId_le=queryMap.get("userId_le");
					Object userId_in=queryMap.get("userId_in");
																					Object log=queryMap.get("log");
					Object log_like=queryMap.get("log_like");
					Object log_isNull=queryMap.get("log_isNull");
					Object log_isNotNull=queryMap.get("log_isNotNull");
					Object log_in=queryMap.get("log_in");
																					Object level=queryMap.get("level");
					Object level_gt=queryMap.get("level_gt");
					Object level_ge=queryMap.get("level_ge");
					Object level_lt=queryMap.get("level_lt");
					Object level_le=queryMap.get("level_le");
					Object level_in=queryMap.get("level_in");
																					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(UserLogEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.in, id_in));}
																if(userId!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, userId));}
					if(userId_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.gt, userId_gt));}
					if(userId_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.ge, userId_ge));}
					if(userId_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.lt, userId_lt));}
					if(userId_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.le, userId_le));}
					if(userId_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, userId_in));}
															  					if(log!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.eq, log));}
		            if(log_like!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.like, log_like));}
		            if(log_isNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNull, log_isNull));}
		            if(log_isNotNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNotNull, log_isNotNull));}
				    if(log_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.in, log_in));}
				  																if(level!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.eq, level));}
					if(level_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.gt, level_gt));}
					if(level_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.ge, level_ge));}
					if(level_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.lt, level_lt));}
					if(level_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.le, level_le));}
					if(level_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.in, level_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  										
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
            list = dbManager.queryByConditions(UserLogEntity.class,qc,oc);
                                    int a=0;
            					             	                 				     				     if(userShow!=null&&userShow.booleanValue()){
						a++;
					 }
				     				     				     		    		                if(a>0&&list!=null&&list.size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<list.size();i++){
					UserLogEntity obj = (UserLogEntity)list.get(i);
										 	                 				     				        //查询关联内容
						if(userShow!=null&&userShow.booleanValue()&&obj!=null&&obj.getUserId()>0){
							UserEntity user = (UserEntity)dbManager.getById(obj.getUserId(), UserEntity.class);
							obj.setUser(user);        	  	  
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
																Object userId=queryMap.get("userId");
					Object userId_gt=queryMap.get("userId_gt");
					Object userId_ge=queryMap.get("userId_ge");
					Object userId_lt=queryMap.get("userId_lt");
					Object userId_le=queryMap.get("userId_le");
					Object userId_in=queryMap.get("userId_in");
															  					Object log=queryMap.get("log");
					Object log_like=queryMap.get("log_like");
					Object log_isNull=queryMap.get("log_isNull");
					Object log_isNotNull=queryMap.get("log_isNotNull");
					Object log_in=queryMap.get("log_in");
				   																Object level=queryMap.get("level");
					Object level_gt=queryMap.get("level_gt");
					Object level_ge=queryMap.get("level_ge");
					Object level_lt=queryMap.get("level_lt");
					Object level_le=queryMap.get("level_le");
					Object level_in=queryMap.get("level_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(UserLogEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.in, id_in));}
																if(userId!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, userId));}
					if(userId_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.gt, userId_gt));}
					if(userId_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.ge, userId_ge));}
					if(userId_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.lt, userId_lt));}
					if(userId_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.le, userId_le));}
					if(userId_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, userId_in));}
															  					if(log!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.eq, log));}
		            if(log_like!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.like, log_like));}
		            if(log_isNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNull, log_isNull));}
		            if(log_isNotNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNotNull, log_isNotNull));}
				    if(log_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.in, log_in));}
				  																if(level!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.eq, level));}
					if(level_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.gt, level_gt));}
					if(level_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.ge, level_ge));}
					if(level_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.lt, level_lt));}
					if(level_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.le, level_le));}
					if(level_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.in, level_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  										
						  			            pagelist = dbManager.queryByCondition(UserLogEntity.class,qc,pageno,pagesize);	                      			
			return pagelist;
		}
		
		/**
		 * 根据条件查询记录集合（带分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 * @param pageno 查询页码
		 * @param pagesize 查询每页记录条数
		 		             	                 				     		 * @param userShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     		 		 * @return
		 */
		public PageList getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,int pageno,int pagesize,Boolean userShow) {
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
																Object userId=queryMap.get("userId");
					Object userId_gt=queryMap.get("userId_gt");
					Object userId_ge=queryMap.get("userId_ge");
					Object userId_lt=queryMap.get("userId_lt");
					Object userId_le=queryMap.get("userId_le");
					Object userId_in=queryMap.get("userId_in");
															  					Object log=queryMap.get("log");
					Object log_like=queryMap.get("log_like");
					Object log_isNull=queryMap.get("log_isNull");
					Object log_isNotNull=queryMap.get("log_isNotNull");
					Object log_in=queryMap.get("log_in");
				   																Object level=queryMap.get("level");
					Object level_gt=queryMap.get("level_gt");
					Object level_ge=queryMap.get("level_ge");
					Object level_lt=queryMap.get("level_lt");
					Object level_le=queryMap.get("level_le");
					Object level_in=queryMap.get("level_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(UserLogEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.in, id_in));}
																if(userId!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, userId));}
					if(userId_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.gt, userId_gt));}
					if(userId_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.ge, userId_ge));}
					if(userId_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.lt, userId_lt));}
					if(userId_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.le, userId_le));}
					if(userId_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, userId_in));}
															  					if(log!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.eq, log));}
		            if(log_like!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.like, log_like));}
		            if(log_isNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNull, log_isNull));}
		            if(log_isNotNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNotNull, log_isNotNull));}
				    if(log_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.in, log_in));}
				  																if(level!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.eq, level));}
					if(level_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.gt, level_gt));}
					if(level_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.ge, level_ge));}
					if(level_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.lt, level_lt));}
					if(level_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.le, level_le));}
					if(level_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.in, level_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  										
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
            pagelist = dbManager.queryByConditions(UserLogEntity.class,qc,oc,pageno,pagesize);	           
                                    int a=0;
            					             	                 				     				     if(userShow!=null&&userShow.booleanValue()){
						a++;
					 }
				     				     				     		    		                if(a>0&&pagelist!=null&&pagelist.getResultList()!=null&&pagelist.getResultList().size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<pagelist.getResultList().size();i++){
					UserLogEntity obj = (UserLogEntity)pagelist.getResultList().get(i);
										 	                 				     				        //查询关联内容
						if(userShow!=null&&userShow.booleanValue()&&obj!=null&&obj.getUserId()>0){
							UserEntity user = (UserEntity)dbManager.getById(obj.getUserId(), UserEntity.class);
							obj.setUser(user);        	  	  
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
		public boolean del(Integer id,Boolean delUser) {
			boolean result = false;
			if (id !=null&&id>0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 					
										 	                 				     					    //删除关联信息
						if(delUser!=null&&delUser.booleanValue()){	
							UserLogEntity userLog = (UserLogEntity)dbManager.getById(id, UserLogEntity.class);
							if(userLog!=null&&userLog.getUserId()!=null){
								dbManager.delNoTransaction(userLog.getUserId(), UserEntity.class);
							}						
						}
				     				     				     										result = dbManager.delNoTransaction(id, UserLogEntity.class);
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
		public boolean delList(Map<String,Object> queryMap,Boolean delUser) {
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
																Object userId=queryMap.get("userId");
					Object userId_gt=queryMap.get("userId_gt");
					Object userId_ge=queryMap.get("userId_ge");
					Object userId_lt=queryMap.get("userId_lt");
					Object userId_le=queryMap.get("userId_le");
					Object userId_in=queryMap.get("userId_in");
															  					Object log=queryMap.get("log");
					Object log_like=queryMap.get("log_like");
					Object log_isNull=queryMap.get("log_isNull");
					Object log_isNotNull=queryMap.get("log_isNotNull");
					Object log_in=queryMap.get("log_in");
				  																Object level=queryMap.get("level");
					Object level_gt=queryMap.get("level_gt");
					Object level_ge=queryMap.get("level_ge");
					Object level_lt=queryMap.get("level_lt");
					Object level_le=queryMap.get("level_le");
					Object level_in=queryMap.get("level_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  																				
			QueryCondition qc = new QueryCondition(UserLogEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.ID, QueryCondition.in, id_in));}
																if(userId!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, userId));}
					if(userId_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.gt, userId_gt));}
					if(userId_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.ge, userId_ge));}
					if(userId_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.lt, userId_lt));}
					if(userId_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.le, userId_le));}
					if(userId_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, userId_in));}
															  					if(log!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.eq, log));}
		            if(log_like!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.like, log_like));}
		            if(log_isNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNull, log_isNull));}
		            if(log_isNotNull!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.isNotNull, log_isNotNull));}
				    if(log_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LOG, QueryCondition.in, log_in));}
				  																if(level!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.eq, level));}
					if(level_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.gt, level_gt));}
					if(level_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.ge, level_ge));}
					if(level_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.lt, level_lt));}
					if(level_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.le, level_le));}
					if(level_in!=null){qc.andCondition(new QueryCondition(UserLogEntity.LEVEL, QueryCondition.in, level_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserLogEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  										
				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 				     					     //删除关联信息
						if(delUser!=null&&delUser.booleanValue()){
							List<Object> list=dbManager.queryByCondition(UserLogEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									UserLogEntity entity=(UserLogEntity)obj;
									strIds += entity.getUserId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(UserEntity.ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(UserEntity.class, qc1);
							}
							
						}
				     				     				     										result = dbManager.delByConditionsNoTransaction(UserLogEntity.class,qc);				
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
