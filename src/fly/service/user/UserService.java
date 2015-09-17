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
import com.framework.system.db.query.OrderByCondition;
import com.framework.system.db.query.OrderVO;
import com.framework.system.db.transaction.TransactionManager;
import com.framework.system.util.StringUtil;



/**   
 * @Title: Service
 * @Description: 系统账号服务类
 * @author feng.gu
 * @date 2015-09-17 13:34:39
 * @version V1.0   
 *
 */
public class UserService {
	   private static Logger logger = Logger.getLogger(UserService.class);
	   private DBManager dbManager = DBManager.getInstance();
    		
	   private static UserService userService;
	   /**
	    * 获取实例	
	    * @return
	    */
	   public static UserService getInstance(){
		if(userService==null){
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
			boolean result =false;
			if(user!=null){
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					 tx.beginTransaction();					 					 
					 					 				     				     dbManager.saveNoTransaction(user);
					     if(user.getUserLogList()!=null&&user.getUserLogList().size()>0){						   
			    	            //关联信息保存	    	           
							    for(UserLogEntity userLogEntity:user.getUserLogList()){
							    	dbManager.saveNoTransaction(userLogEntity);
							    }
						 }
				     				     				     				     					 					 result=dbManager.saveNoTransaction(user);	
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
		public boolean saveList(List<UserEntity> userList) {
			boolean result = false;
			if (userList != null && userList.size() > 0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 
					for(UserEntity user:userList){
						if(user!=null){												  								 
								 									 					                 						                 if(user.getUserLogList()!=null&&user.getUserLogList().size()>0){
										 //关联信息保存		    	           
										 for(UserLogEntity userLogEntity:user.getUserLogList()){
										     dbManager.saveNoTransaction(userLogEntity);
										 }
									     }
								     								     								     								     								 								 result=dbManager.saveNoTransaction(user);								 			
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
		 		             	                 	     * @param userLogListShow 是否查询关联信息
				     				     				     				     		 		 * @param obj
		 */
		public UserEntity getById(Integer id,Boolean userLogListShow) {
			UserEntity obj = null;
			if (id!=null) {
				obj = (UserEntity)dbManager.getById(id, UserEntity.class);
									 	                 	                    //查询关联内容
						if(userLogListShow!=null&&userLogListShow.booleanValue()&&obj!=null&&obj.getId()>0){
							List<Object> list = dbManager.searchListByColumn(UserLogEntity.class, UserLogEntity.USER_ID, String.valueOf(id));
			            	  if(list!=null&&list.size()>0){
			            		  List<UserLogEntity> userLogList= new ArrayList<UserLogEntity>();
			            		  for(Object o:list){
			            			  userLogList.add((UserLogEntity)o);
			            		  }
			            		  obj.setUserLogList(userLogList);          		  
			            	  }	            	  	  
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
																					Object name=queryMap.get("name");
					Object name_like=queryMap.get("name_like");
					Object name_isNull=queryMap.get("name_isNull");
					Object name_isNotNull=queryMap.get("name_isNotNull");
					Object name_in=queryMap.get("name_in");
																										Object password=queryMap.get("password");
					Object password_like=queryMap.get("password_like");
					Object password_isNull=queryMap.get("password_isNull");
					Object password_isNotNull=queryMap.get("password_isNotNull");
					Object password_in=queryMap.get("password_in");
																					Object permissions=queryMap.get("permissions");
					Object permissions_gt=queryMap.get("permissions_gt");
					Object permissions_ge=queryMap.get("permissions_ge");
					Object permissions_lt=queryMap.get("permissions_lt");
					Object permissions_le=queryMap.get("permissions_le");
					Object permissions_in=queryMap.get("permissions_in");
																					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
																										Object lstlogindate_gt=queryMap.get("lstlogindate_gt");
					Object lstlogindate_ge=queryMap.get("lstlogindate_ge");
					Object lstlogindate_lt=queryMap.get("lstlogindate_lt");
					Object lstlogindate_le=queryMap.get("lstlogindate_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(UserEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.in, id_in));}
															  					if(name!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
				    if(name_in!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.in, name_in));}
				  															  					if(password!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.eq, password));}
		            if(password_like!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.like, password_like));}
		            if(password_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNull, password_isNull));}
		            if(password_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNotNull, password_isNotNull));}
				    if(password_in!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.in, password_in));}
				  																if(permissions!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.eq, permissions));}
					if(permissions_gt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.gt, permissions_gt));}
					if(permissions_ge!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.ge, permissions_ge));}
					if(permissions_lt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.lt, permissions_lt));}
					if(permissions_le!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.le, permissions_le));}
					if(permissions_in!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.in, permissions_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  															  					if(lstlogindate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.gt, lstlogindate_gt));}
					if(lstlogindate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.ge, lstlogindate_ge));}
					if(lstlogindate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.lt, lstlogindate_lt));}
					if(lstlogindate_le!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.le, lstlogindate_le));}
				  										
						  			            list = dbManager.queryByCondition(UserEntity.class,qc);           					
			return list;
		}
		
		
		/**
		 * 根据条件查询记录集合（不分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 		             	                 	     * @param userLogListShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     				     		 		 * @return
		 */
		public List<Object> getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,Boolean userLogListShow) {
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
																					Object name=queryMap.get("name");
					Object name_like=queryMap.get("name_like");
					Object name_isNull=queryMap.get("name_isNull");
					Object name_isNotNull=queryMap.get("name_isNotNull");
					Object name_in=queryMap.get("name_in");
																										Object password=queryMap.get("password");
					Object password_like=queryMap.get("password_like");
					Object password_isNull=queryMap.get("password_isNull");
					Object password_isNotNull=queryMap.get("password_isNotNull");
					Object password_in=queryMap.get("password_in");
																					Object permissions=queryMap.get("permissions");
					Object permissions_gt=queryMap.get("permissions_gt");
					Object permissions_ge=queryMap.get("permissions_ge");
					Object permissions_lt=queryMap.get("permissions_lt");
					Object permissions_le=queryMap.get("permissions_le");
					Object permissions_in=queryMap.get("permissions_in");
																					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
																										Object lstlogindate_gt=queryMap.get("lstlogindate_gt");
					Object lstlogindate_ge=queryMap.get("lstlogindate_ge");
					Object lstlogindate_lt=queryMap.get("lstlogindate_lt");
					Object lstlogindate_le=queryMap.get("lstlogindate_le");
												
			

													
			
			QueryCondition qc = new QueryCondition(UserEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.in, id_in));}
															  					if(name!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
				    if(name_in!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.in, name_in));}
				  															  					if(password!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.eq, password));}
		            if(password_like!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.like, password_like));}
		            if(password_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNull, password_isNull));}
		            if(password_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNotNull, password_isNotNull));}
				    if(password_in!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.in, password_in));}
				  																if(permissions!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.eq, permissions));}
					if(permissions_gt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.gt, permissions_gt));}
					if(permissions_ge!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.ge, permissions_ge));}
					if(permissions_lt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.lt, permissions_lt));}
					if(permissions_le!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.le, permissions_le));}
					if(permissions_in!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.in, permissions_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  															  					if(lstlogindate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.gt, lstlogindate_gt));}
					if(lstlogindate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.ge, lstlogindate_ge));}
					if(lstlogindate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.lt, lstlogindate_lt));}
					if(lstlogindate_le!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.le, lstlogindate_le));}
				  										
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
            list = dbManager.queryByConditions(UserEntity.class,qc,oc);
                                    int a=0;
            					             	                 	                 if(userLogListShow!=null&&userLogListShow.booleanValue()){
						a++;
					 }
				     				     				     				     		    		                if(a>0&&list!=null&&list.size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<list.size();i++){
					UserEntity obj = (UserEntity)list.get(i);
										 	                 	                    //查询关联内容
						if(userLogListShow!=null&&userLogListShow.booleanValue()&&obj!=null&&obj.getId()>0){
							List<Object> objList = dbManager.searchListByColumn(UserLogEntity.class, UserLogEntity.USER_ID, String.valueOf(obj.getId()));
			            	  if(objList!=null&&objList.size()>0){
			            		  List<UserLogEntity> userLogList= new ArrayList<UserLogEntity>();
			            		  for(Object o:objList){
			            			  userLogList.add((UserLogEntity)o);
			            		  }
			            		  obj.setUserLogList(userLogList);          		  
			            	  }	            	  	  
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
															  					Object name=queryMap.get("name");
					Object name_like=queryMap.get("name_like");
					Object name_isNull=queryMap.get("name_isNull");
					Object name_isNotNull=queryMap.get("name_isNotNull");
					Object name_in=queryMap.get("name_in");
				   															  					Object password=queryMap.get("password");
					Object password_like=queryMap.get("password_like");
					Object password_isNull=queryMap.get("password_isNull");
					Object password_isNotNull=queryMap.get("password_isNotNull");
					Object password_in=queryMap.get("password_in");
				   																Object permissions=queryMap.get("permissions");
					Object permissions_gt=queryMap.get("permissions_gt");
					Object permissions_ge=queryMap.get("permissions_ge");
					Object permissions_lt=queryMap.get("permissions_lt");
					Object permissions_le=queryMap.get("permissions_le");
					Object permissions_in=queryMap.get("permissions_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  															  					Object lstlogindate_gt=queryMap.get("lstlogindate_gt");
					Object lstlogindate_ge=queryMap.get("lstlogindate_ge");
					Object lstlogindate_lt=queryMap.get("lstlogindate_lt");
					Object lstlogindate_le=queryMap.get("lstlogindate_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(UserEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.in, id_in));}
															  					if(name!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
				    if(name_in!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.in, name_in));}
				  															  					if(password!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.eq, password));}
		            if(password_like!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.like, password_like));}
		            if(password_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNull, password_isNull));}
		            if(password_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNotNull, password_isNotNull));}
				    if(password_in!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.in, password_in));}
				  																if(permissions!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.eq, permissions));}
					if(permissions_gt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.gt, permissions_gt));}
					if(permissions_ge!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.ge, permissions_ge));}
					if(permissions_lt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.lt, permissions_lt));}
					if(permissions_le!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.le, permissions_le));}
					if(permissions_in!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.in, permissions_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  															  					if(lstlogindate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.gt, lstlogindate_gt));}
					if(lstlogindate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.ge, lstlogindate_ge));}
					if(lstlogindate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.lt, lstlogindate_lt));}
					if(lstlogindate_le!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.le, lstlogindate_le));}
				  										
						  			            pagelist = dbManager.queryByCondition(UserEntity.class,qc,pageno,pagesize);	                      			
			return pagelist;
		}
		
		/**
		 * 根据条件查询记录集合（带分页 带排序 带级联查询）
		 * @param queryMap 查询条件集合
		 * @param orderList 排序条件集合
		 * @param pageno 查询页码
		 * @param pagesize 查询每页记录条数
		 		             	                 	     * @param userLogListShow 是否查询关联信息,默认false(当为true时注意效率)
				     				     				     				     		 		 * @return
		 */
		public PageList getListByCondition(Map<String,Object> queryMap,List<OrderVO> orderList,int pageno,int pagesize,Boolean userLogListShow) {
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
					Object name_in=queryMap.get("name_in");
				   															  					Object password=queryMap.get("password");
					Object password_like=queryMap.get("password_like");
					Object password_isNull=queryMap.get("password_isNull");
					Object password_isNotNull=queryMap.get("password_isNotNull");
					Object password_in=queryMap.get("password_in");
				   																Object permissions=queryMap.get("permissions");
					Object permissions_gt=queryMap.get("permissions_gt");
					Object permissions_ge=queryMap.get("permissions_ge");
					Object permissions_lt=queryMap.get("permissions_lt");
					Object permissions_le=queryMap.get("permissions_le");
					Object permissions_in=queryMap.get("permissions_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  															  					Object lstlogindate_gt=queryMap.get("lstlogindate_gt");
					Object lstlogindate_ge=queryMap.get("lstlogindate_ge");
					Object lstlogindate_lt=queryMap.get("lstlogindate_lt");
					Object lstlogindate_le=queryMap.get("lstlogindate_le");
				  							
			

													
			
			QueryCondition qc = new QueryCondition(UserEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.in, id_in));}
															  					if(name!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
				    if(name_in!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.in, name_in));}
				  															  					if(password!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.eq, password));}
		            if(password_like!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.like, password_like));}
		            if(password_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNull, password_isNull));}
		            if(password_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNotNull, password_isNotNull));}
				    if(password_in!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.in, password_in));}
				  																if(permissions!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.eq, permissions));}
					if(permissions_gt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.gt, permissions_gt));}
					if(permissions_ge!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.ge, permissions_ge));}
					if(permissions_lt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.lt, permissions_lt));}
					if(permissions_le!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.le, permissions_le));}
					if(permissions_in!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.in, permissions_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  															  					if(lstlogindate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.gt, lstlogindate_gt));}
					if(lstlogindate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.ge, lstlogindate_ge));}
					if(lstlogindate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.lt, lstlogindate_lt));}
					if(lstlogindate_le!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.le, lstlogindate_le));}
				  										
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
            pagelist = dbManager.queryByConditions(UserEntity.class,qc,oc,pageno,pagesize);	           
                                    int a=0;
            					             	                 	                 if(userLogListShow!=null&&userLogListShow.booleanValue()){
						a++;
					 }
				     				     				     				     		    		                if(a>0&&pagelist!=null&&pagelist.getResultList()!=null&&pagelist.getResultList().size()>0){
				List<Object> result = new ArrayList<Object>();
				for(int i=0;i<pagelist.getResultList().size();i++){
					UserEntity obj = (UserEntity)pagelist.getResultList().get(i);
										 	                 	                    //查询关联内容
						if(userLogListShow!=null&&userLogListShow.booleanValue()&&obj!=null&&obj.getId()>0){
							List<Object> objList = dbManager.searchListByColumn(UserLogEntity.class, UserLogEntity.USER_ID, String.valueOf(obj.getId()));
			            	  if(objList!=null&&objList.size()>0){
			            		  List<UserLogEntity> userLogList= new ArrayList<UserLogEntity>();
			            		  for(Object o:objList){
			            			  userLogList.add((UserLogEntity)o);
			            		  }
			            		  obj.setUserLogList(userLogList);          		  
			            	  }	            	  	  
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
		public boolean del(Integer id,Boolean delUserLogList) {
			boolean result = false;
			if (id !=null&&id>0) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 					
										 	                 	                    //删除关联信息
						if(delUserLogList!=null&&delUserLogList.booleanValue()){
							QueryCondition qc = new QueryCondition(UserLogEntity.USER_ID, QueryCondition.eq, id);
							dbManager.delByConditionsNoTransaction(UserLogEntity.class, qc);
						}
				     				     				     				     										result = dbManager.delNoTransaction(id, UserEntity.class);
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
		public boolean delList(Map<String,Object> queryMap,Boolean delUserLogList) {
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
					Object name_in=queryMap.get("name_in");
				  															  					Object password=queryMap.get("password");
					Object password_like=queryMap.get("password_like");
					Object password_isNull=queryMap.get("password_isNull");
					Object password_isNotNull=queryMap.get("password_isNotNull");
					Object password_in=queryMap.get("password_in");
				  																Object permissions=queryMap.get("permissions");
					Object permissions_gt=queryMap.get("permissions_gt");
					Object permissions_ge=queryMap.get("permissions_ge");
					Object permissions_lt=queryMap.get("permissions_lt");
					Object permissions_le=queryMap.get("permissions_le");
					Object permissions_in=queryMap.get("permissions_in");
															  					Object createdate_gt=queryMap.get("createdate_gt");
					Object createdate_ge=queryMap.get("createdate_ge");
					Object createdate_lt=queryMap.get("createdate_lt");
					Object createdate_le=queryMap.get("createdate_le");
				  															  					Object lstlogindate_gt=queryMap.get("lstlogindate_gt");
					Object lstlogindate_ge=queryMap.get("lstlogindate_ge");
					Object lstlogindate_lt=queryMap.get("lstlogindate_lt");
					Object lstlogindate_le=queryMap.get("lstlogindate_le");
				  																				
			QueryCondition qc = new QueryCondition(UserEntity.ID, QueryCondition.gt, "0");				
												if(id!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.eq, id));}
					if(id_gt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.gt, id_gt));}
					if(id_ge!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.ge, id_ge));}
					if(id_lt!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.lt, id_lt));}
					if(id_le!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.le, id_le));}
					if(id_in!=null){qc.andCondition(new QueryCondition(UserEntity.ID, QueryCondition.in, id_in));}
															  					if(name!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.eq, name));}
		            if(name_like!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.like, name_like));}
		            if(name_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNull, name_isNull));}
		            if(name_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.isNotNull, name_isNotNull));}
				    if(name_in!=null){qc.andCondition(new QueryCondition(UserEntity.NAME, QueryCondition.in, name_in));}
				  															  					if(password!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.eq, password));}
		            if(password_like!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.like, password_like));}
		            if(password_isNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNull, password_isNull));}
		            if(password_isNotNull!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.isNotNull, password_isNotNull));}
				    if(password_in!=null){qc.andCondition(new QueryCondition(UserEntity.PASSWORD, QueryCondition.in, password_in));}
				  																if(permissions!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.eq, permissions));}
					if(permissions_gt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.gt, permissions_gt));}
					if(permissions_ge!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.ge, permissions_ge));}
					if(permissions_lt!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.lt, permissions_lt));}
					if(permissions_le!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.le, permissions_le));}
					if(permissions_in!=null){qc.andCondition(new QueryCondition(UserEntity.PERMISSIONS, QueryCondition.in, permissions_in));}
															  					if(createdate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.gt, createdate_gt));}
					if(createdate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.ge, createdate_ge));}
					if(createdate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.lt, createdate_lt));}
					if(createdate_le!=null){qc.andCondition(new QueryCondition(UserEntity.CREATEDATE, QueryCondition.le, createdate_le));}
				  															  					if(lstlogindate_gt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.gt, lstlogindate_gt));}
					if(lstlogindate_ge!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.ge, lstlogindate_ge));}
					if(lstlogindate_lt!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.lt, lstlogindate_lt));}
					if(lstlogindate_le!=null){qc.andCondition(new QueryCondition(UserEntity.LSTLOGINDATE, QueryCondition.le, lstlogindate_le));}
				  										
				            						if (qc.getQueryNextCondition()!=null) {
				TransactionManager tx = DbUtils.getTranManager();   
				try{   
					tx.beginTransaction(); 						
										 	                 		                 //删除关联信息
						if(delUserLogList!=null&&delUserLogList.booleanValue()){
							List<Object> list=dbManager.queryByCondition(UserEntity.class,qc);
							String strIds="";
							if(list!=null&&list.size()>0){
								for(Object obj:list){
									UserEntity entity=(UserEntity)obj;
									strIds += entity.getId()+",";
								}
								strIds = strIds.substring(0, strIds.length()-1);
							}
							if(strIds!=null&&!"".equals(strIds)){
								QueryCondition qc1 = new QueryCondition(UserLogEntity.USER_ID, QueryCondition.in, strIds);
								dbManager.delByConditionsNoTransaction(UserLogEntity.class, qc1);
							}
							
						}
				     				     				     				     										result = dbManager.delByConditionsNoTransaction(UserEntity.class,qc);				
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
