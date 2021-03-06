package fly.service.dev;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import fly.entity.position.PositionEntity;
import fly.entity.devPosition.DevPositionEntity;
import fly.entity.alarmCurrent.AlarmCurrentEntity;
import fly.entity.alarmHistory.AlarmHistoryEntity;
import fly.entity.currentBed.CurrentBedEntity;
import fly.entity.currentDoor.CurrentDoorEntity;
import fly.entity.currentGateway.CurrentGatewayEntity;
import fly.entity.currentKeyalarm.CurrentKeyalarmEntity;
import fly.entity.currentLocation.CurrentLocationEntity;
import fly.entity.currentUrine.CurrentUrineEntity;
import fly.entity.currentWandai.CurrentWandaiEntity;
import fly.entity.historyBed.HistoryBedEntity;
import fly.entity.historyDoor.HistoryDoorEntity;
import fly.entity.historyKeyalarm.HistoryKeyalarmEntity;
import fly.entity.historyLocationBody.HistoryLocationBodyEntity;
import fly.entity.historyLocationManual.HistoryLocationManualEntity;
import fly.entity.historyLocationMove.HistoryLocationMoveEntity;
import fly.entity.historyLocationPos.HistoryLocationPosEntity;
import fly.entity.historyUrine.HistoryUrineEntity;
import fly.entity.currentIr.CurrentIrEntity;
import fly.entity.historyIr.HistoryIrEntity;
import fly.entity.currentTizhengBed.CurrentTizhengBedEntity;
import fly.entity.historyTizhengBed.HistoryTizhengBedEntity;

import fly.entity.dev.DevEntity;
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
 * @Description: 设备信息服务类
 * @author feng.gu
 * @date 2015-09-17 13:34:43
 * @version V1.0
 * 
 */
public class DevService {
	private static Logger logger = Logger.getLogger(DevService.class);
	private DBManager dbManager = DBManager.getInstance();

	private static DevService devService;

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static DevService getInstance() {
		if (devService == null) {
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
		boolean result = false;
		if (dev != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				dbManager.saveNoTransaction(dev);
				if (dev.getPositionList() != null
						&& dev.getPositionList().size() > 0) {
					// 先删除原先绑定关系
					QueryCondition qc = new QueryCondition(
							DevPositionEntity.DEV_ID, QueryCondition.eq,
							dev.getId());
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
					for (PositionEntity position : dev.getPositionList()) {
						Integer positionId = position.getId();
						if (positionId != null) {
							DevPositionEntity entity = new DevPositionEntity();
							entity.setDevId(dev.getId());
							entity.setPositionId(positionId);
							dbManager.saveNoTransaction(entity);
						}
					}
				}
				// 关联信息保存
				DevEntity parentDev = dev.getParentDev();
				if (parentDev != null) {
					dbManager.saveNoTransaction(parentDev);
					dev.setParentId(parentDev.getId());
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getAlarmCurrentList() != null
						&& dev.getAlarmCurrentList().size() > 0) {
					// 关联信息保存
					for (AlarmCurrentEntity alarmCurrentEntity : dev
							.getAlarmCurrentList()) {
						dbManager.saveNoTransaction(alarmCurrentEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getAlarmHistoryList() != null
						&& dev.getAlarmHistoryList().size() > 0) {
					// 关联信息保存
					for (AlarmHistoryEntity alarmHistoryEntity : dev
							.getAlarmHistoryList()) {
						dbManager.saveNoTransaction(alarmHistoryEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentBedList() != null
						&& dev.getCurrentBedList().size() > 0) {
					// 关联信息保存
					for (CurrentBedEntity currentBedEntity : dev
							.getCurrentBedList()) {
						dbManager.saveNoTransaction(currentBedEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentDoorList() != null
						&& dev.getCurrentDoorList().size() > 0) {
					// 关联信息保存
					for (CurrentDoorEntity currentDoorEntity : dev
							.getCurrentDoorList()) {
						dbManager.saveNoTransaction(currentDoorEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentGatewayList() != null
						&& dev.getCurrentGatewayList().size() > 0) {
					// 关联信息保存
					for (CurrentGatewayEntity currentGatewayEntity : dev
							.getCurrentGatewayList()) {
						dbManager.saveNoTransaction(currentGatewayEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentKeyalarmList() != null
						&& dev.getCurrentKeyalarmList().size() > 0) {
					// 关联信息保存
					for (CurrentKeyalarmEntity currentKeyalarmEntity : dev
							.getCurrentKeyalarmList()) {
						dbManager.saveNoTransaction(currentKeyalarmEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentLocationList() != null
						&& dev.getCurrentLocationList().size() > 0) {
					// 关联信息保存
					for (CurrentLocationEntity currentLocationEntity : dev
							.getCurrentLocationList()) {
						dbManager.saveNoTransaction(currentLocationEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentUrineList() != null
						&& dev.getCurrentUrineList().size() > 0) {
					// 关联信息保存
					for (CurrentUrineEntity currentUrineEntity : dev
							.getCurrentUrineList()) {
						dbManager.saveNoTransaction(currentUrineEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentWandaiList() != null
						&& dev.getCurrentWandaiList().size() > 0) {
					// 关联信息保存
					for (CurrentWandaiEntity currentWandaiEntity : dev
							.getCurrentWandaiList()) {
						dbManager.saveNoTransaction(currentWandaiEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryBedList() != null
						&& dev.getHistoryBedList().size() > 0) {
					// 关联信息保存
					for (HistoryBedEntity historyBedEntity : dev
							.getHistoryBedList()) {
						dbManager.saveNoTransaction(historyBedEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryDoorList() != null
						&& dev.getHistoryDoorList().size() > 0) {
					// 关联信息保存
					for (HistoryDoorEntity historyDoorEntity : dev
							.getHistoryDoorList()) {
						dbManager.saveNoTransaction(historyDoorEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryKeyalarmList() != null
						&& dev.getHistoryKeyalarmList().size() > 0) {
					// 关联信息保存
					for (HistoryKeyalarmEntity historyKeyalarmEntity : dev
							.getHistoryKeyalarmList()) {
						dbManager.saveNoTransaction(historyKeyalarmEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryLocationBodyList() != null
						&& dev.getHistoryLocationBodyList().size() > 0) {
					// 关联信息保存
					for (HistoryLocationBodyEntity historyLocationBodyEntity : dev
							.getHistoryLocationBodyList()) {
						dbManager.saveNoTransaction(historyLocationBodyEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryLocationManualList() != null
						&& dev.getHistoryLocationManualList().size() > 0) {
					// 关联信息保存
					for (HistoryLocationManualEntity historyLocationManualEntity : dev
							.getHistoryLocationManualList()) {
						dbManager
								.saveNoTransaction(historyLocationManualEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryLocationMoveList() != null
						&& dev.getHistoryLocationMoveList().size() > 0) {
					// 关联信息保存
					for (HistoryLocationMoveEntity historyLocationMoveEntity : dev
							.getHistoryLocationMoveList()) {
						dbManager.saveNoTransaction(historyLocationMoveEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryLocationPosList() != null
						&& dev.getHistoryLocationPosList().size() > 0) {
					// 关联信息保存
					for (HistoryLocationPosEntity historyLocationPosEntity : dev
							.getHistoryLocationPosList()) {
						dbManager.saveNoTransaction(historyLocationPosEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryUrineList() != null
						&& dev.getHistoryUrineList().size() > 0) {
					// 关联信息保存
					for (HistoryUrineEntity historyUrineEntity : dev
							.getHistoryUrineList()) {
						dbManager.saveNoTransaction(historyUrineEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentIrList() != null
						&& dev.getCurrentIrList().size() > 0) {
					// 关联信息保存
					for (CurrentIrEntity currentIrEntity : dev
							.getCurrentIrList()) {
						dbManager.saveNoTransaction(currentIrEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryIrList() != null
						&& dev.getHistoryIrList().size() > 0) {
					// 关联信息保存
					for (HistoryIrEntity historyIrEntity : dev
							.getHistoryIrList()) {
						dbManager.saveNoTransaction(historyIrEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getCurrentTizhengBedList() != null
						&& dev.getCurrentTizhengBedList().size() > 0) {
					// 关联信息保存
					for (CurrentTizhengBedEntity currentTizhengBedEntity : dev
							.getCurrentTizhengBedList()) {
						dbManager.saveNoTransaction(currentTizhengBedEntity);
					}
				}
				dbManager.saveNoTransaction(dev);
				if (dev.getHistoryTizhengBedList() != null
						&& dev.getHistoryTizhengBedList().size() > 0) {
					// 关联信息保存
					for (HistoryTizhengBedEntity historyTizhengBedEntity : dev
							.getHistoryTizhengBedList()) {
						dbManager.saveNoTransaction(historyTizhengBedEntity);
					}
				}
				result = dbManager.saveNoTransaction(dev);
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
	public boolean saveList(List<DevEntity> devList) {
		boolean result = false;
		if (devList != null && devList.size() > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				for (DevEntity dev : devList) {
					if (dev != null) {
						if (dev.getPositionList() != null
								&& dev.getPositionList().size() > 0) {
							// 先删除原先绑定关系
							QueryCondition qc = new QueryCondition(
									DevPositionEntity.DEV_ID,
									QueryCondition.eq, dev.getId());
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
							for (PositionEntity position : dev
									.getPositionList()) {
								Integer positionId = position.getId();
								if (positionId != null) {
									DevPositionEntity entity = new DevPositionEntity();
									entity.setDevId(dev.getId());
									entity.setPositionId(positionId);
									dbManager.saveNoTransaction(entity);
								}
							}
						}
						// 关联信息保存
						DevEntity parentDev = dev.getParentDev();
						if (parentDev != null) {
							dbManager.saveNoTransaction(parentDev);
							dev.setParentId(parentDev.getId());
						}
						if (dev.getAlarmCurrentList() != null
								&& dev.getAlarmCurrentList().size() > 0) {
							// 关联信息保存
							for (AlarmCurrentEntity alarmCurrentEntity : dev
									.getAlarmCurrentList()) {
								dbManager.saveNoTransaction(alarmCurrentEntity);
							}
						}
						if (dev.getAlarmHistoryList() != null
								&& dev.getAlarmHistoryList().size() > 0) {
							// 关联信息保存
							for (AlarmHistoryEntity alarmHistoryEntity : dev
									.getAlarmHistoryList()) {
								dbManager.saveNoTransaction(alarmHistoryEntity);
							}
						}
						if (dev.getCurrentBedList() != null
								&& dev.getCurrentBedList().size() > 0) {
							// 关联信息保存
							for (CurrentBedEntity currentBedEntity : dev
									.getCurrentBedList()) {
								dbManager.saveNoTransaction(currentBedEntity);
							}
						}
						if (dev.getCurrentDoorList() != null
								&& dev.getCurrentDoorList().size() > 0) {
							// 关联信息保存
							for (CurrentDoorEntity currentDoorEntity : dev
									.getCurrentDoorList()) {
								dbManager.saveNoTransaction(currentDoorEntity);
							}
						}
						if (dev.getCurrentGatewayList() != null
								&& dev.getCurrentGatewayList().size() > 0) {
							// 关联信息保存
							for (CurrentGatewayEntity currentGatewayEntity : dev
									.getCurrentGatewayList()) {
								dbManager
										.saveNoTransaction(currentGatewayEntity);
							}
						}
						if (dev.getCurrentKeyalarmList() != null
								&& dev.getCurrentKeyalarmList().size() > 0) {
							// 关联信息保存
							for (CurrentKeyalarmEntity currentKeyalarmEntity : dev
									.getCurrentKeyalarmList()) {
								dbManager
										.saveNoTransaction(currentKeyalarmEntity);
							}
						}
						if (dev.getCurrentLocationList() != null
								&& dev.getCurrentLocationList().size() > 0) {
							// 关联信息保存
							for (CurrentLocationEntity currentLocationEntity : dev
									.getCurrentLocationList()) {
								dbManager
										.saveNoTransaction(currentLocationEntity);
							}
						}
						if (dev.getCurrentUrineList() != null
								&& dev.getCurrentUrineList().size() > 0) {
							// 关联信息保存
							for (CurrentUrineEntity currentUrineEntity : dev
									.getCurrentUrineList()) {
								dbManager.saveNoTransaction(currentUrineEntity);
							}
						}
						if (dev.getCurrentWandaiList() != null
								&& dev.getCurrentWandaiList().size() > 0) {
							// 关联信息保存
							for (CurrentWandaiEntity currentWandaiEntity : dev
									.getCurrentWandaiList()) {
								dbManager
										.saveNoTransaction(currentWandaiEntity);
							}
						}
						if (dev.getHistoryBedList() != null
								&& dev.getHistoryBedList().size() > 0) {
							// 关联信息保存
							for (HistoryBedEntity historyBedEntity : dev
									.getHistoryBedList()) {
								dbManager.saveNoTransaction(historyBedEntity);
							}
						}
						if (dev.getHistoryDoorList() != null
								&& dev.getHistoryDoorList().size() > 0) {
							// 关联信息保存
							for (HistoryDoorEntity historyDoorEntity : dev
									.getHistoryDoorList()) {
								dbManager.saveNoTransaction(historyDoorEntity);
							}
						}
						if (dev.getHistoryKeyalarmList() != null
								&& dev.getHistoryKeyalarmList().size() > 0) {
							// 关联信息保存
							for (HistoryKeyalarmEntity historyKeyalarmEntity : dev
									.getHistoryKeyalarmList()) {
								dbManager
										.saveNoTransaction(historyKeyalarmEntity);
							}
						}
						if (dev.getHistoryLocationBodyList() != null
								&& dev.getHistoryLocationBodyList().size() > 0) {
							// 关联信息保存
							for (HistoryLocationBodyEntity historyLocationBodyEntity : dev
									.getHistoryLocationBodyList()) {
								dbManager
										.saveNoTransaction(historyLocationBodyEntity);
							}
						}
						if (dev.getHistoryLocationManualList() != null
								&& dev.getHistoryLocationManualList().size() > 0) {
							// 关联信息保存
							for (HistoryLocationManualEntity historyLocationManualEntity : dev
									.getHistoryLocationManualList()) {
								dbManager
										.saveNoTransaction(historyLocationManualEntity);
							}
						}
						if (dev.getHistoryLocationMoveList() != null
								&& dev.getHistoryLocationMoveList().size() > 0) {
							// 关联信息保存
							for (HistoryLocationMoveEntity historyLocationMoveEntity : dev
									.getHistoryLocationMoveList()) {
								dbManager
										.saveNoTransaction(historyLocationMoveEntity);
							}
						}
						if (dev.getHistoryLocationPosList() != null
								&& dev.getHistoryLocationPosList().size() > 0) {
							// 关联信息保存
							for (HistoryLocationPosEntity historyLocationPosEntity : dev
									.getHistoryLocationPosList()) {
								dbManager
										.saveNoTransaction(historyLocationPosEntity);
							}
						}
						if (dev.getHistoryUrineList() != null
								&& dev.getHistoryUrineList().size() > 0) {
							// 关联信息保存
							for (HistoryUrineEntity historyUrineEntity : dev
									.getHistoryUrineList()) {
								dbManager.saveNoTransaction(historyUrineEntity);
							}
						}
						if (dev.getCurrentIrList() != null
								&& dev.getCurrentIrList().size() > 0) {
							// 关联信息保存
							for (CurrentIrEntity currentIrEntity : dev
									.getCurrentIrList()) {
								dbManager.saveNoTransaction(currentIrEntity);
							}
						}
						if (dev.getHistoryIrList() != null
								&& dev.getHistoryIrList().size() > 0) {
							// 关联信息保存
							for (HistoryIrEntity historyIrEntity : dev
									.getHistoryIrList()) {
								dbManager.saveNoTransaction(historyIrEntity);
							}
						}
						if (dev.getCurrentTizhengBedList() != null
								&& dev.getCurrentTizhengBedList().size() > 0) {
							// 关联信息保存
							for (CurrentTizhengBedEntity currentTizhengBedEntity : dev
									.getCurrentTizhengBedList()) {
								dbManager
										.saveNoTransaction(currentTizhengBedEntity);
							}
						}
						if (dev.getHistoryTizhengBedList() != null
								&& dev.getHistoryTizhengBedList().size() > 0) {
							// 关联信息保存
							for (HistoryTizhengBedEntity historyTizhengBedEntity : dev
									.getHistoryTizhengBedList()) {
								dbManager
										.saveNoTransaction(historyTizhengBedEntity);
							}
						}
						result = dbManager.saveNoTransaction(dev);
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
	 * @param positionListShow
	 *            是否查询关联信息
	 * @param parentDevShow
	 *            是否查询关联信息
	 * @param alarmCurrentListShow
	 *            是否查询关联信息
	 * @param alarmHistoryListShow
	 *            是否查询关联信息
	 * @param currentBedListShow
	 *            是否查询关联信息
	 * @param currentDoorListShow
	 *            是否查询关联信息
	 * @param currentGatewayListShow
	 *            是否查询关联信息
	 * @param currentKeyalarmListShow
	 *            是否查询关联信息
	 * @param currentLocationListShow
	 *            是否查询关联信息
	 * @param currentUrineListShow
	 *            是否查询关联信息
	 * @param currentWandaiListShow
	 *            是否查询关联信息
	 * @param historyBedListShow
	 *            是否查询关联信息
	 * @param historyDoorListShow
	 *            是否查询关联信息
	 * @param historyKeyalarmListShow
	 *            是否查询关联信息
	 * @param historyLocationBodyListShow
	 *            是否查询关联信息
	 * @param historyLocationManualListShow
	 *            是否查询关联信息
	 * @param historyLocationMoveListShow
	 *            是否查询关联信息
	 * @param historyLocationPosListShow
	 *            是否查询关联信息
	 * @param historyUrineListShow
	 *            是否查询关联信息
	 * @param currentIrListShow
	 *            是否查询关联信息
	 * @param historyIrListShow
	 *            是否查询关联信息
	 * @param currentTizhengBedListShow
	 *            是否查询关联信息
	 * @param historyTizhengBedListShow
	 *            是否查询关联信息
	 * @param obj
	 */
	public DevEntity getById(Integer id, Boolean positionListShow,
			Boolean parentDevShow, Boolean alarmCurrentListShow,
			Boolean alarmHistoryListShow, Boolean currentBedListShow,
			Boolean currentDoorListShow, Boolean currentGatewayListShow,
			Boolean currentKeyalarmListShow, Boolean currentLocationListShow,
			Boolean currentUrineListShow, Boolean currentWandaiListShow,
			Boolean historyBedListShow, Boolean historyDoorListShow,
			Boolean historyKeyalarmListShow,
			Boolean historyLocationBodyListShow,
			Boolean historyLocationManualListShow,
			Boolean historyLocationMoveListShow,
			Boolean historyLocationPosListShow, Boolean historyUrineListShow,
			Boolean currentIrListShow, Boolean historyIrListShow,
			Boolean currentTizhengBedListShow, Boolean historyTizhengBedListShow) {
		DevEntity obj = null;
		if (id != null) {
			obj = (DevEntity) dbManager.getById(id, DevEntity.class);
			// 查询关联内容
			if (positionListShow != null && positionListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> rlist = dbManager.searchListByColumn(
						DevPositionEntity.class, DevPositionEntity.DEV_ID,
						String.valueOf(id));
				if (rlist != null && rlist.size() > 0) {
					for (Object robj : rlist) {
						DevPositionEntity robject = (DevPositionEntity) robj;
						Integer positionId = robject.getPositionId();
						if (positionId != null) {
							PositionEntity position = (PositionEntity) dbManager
									.getById(positionId, PositionEntity.class);
							List<PositionEntity> positionList = obj
									.getPositionList();
							if (positionList == null
									|| positionList.size() == 0) {
								positionList = new ArrayList<PositionEntity>();
							}
							positionList.add(position);
							obj.setPositionList(positionList);
						}
					}
				}
			}
			// 查询关联内容
			if (parentDevShow != null && parentDevShow.booleanValue()
					&& obj != null && obj.getParentId() > 0) {
				DevEntity dev = (DevEntity) dbManager.getById(
						obj.getParentId(), DevEntity.class);
				obj.setParentDev(dev);
			}
			// 查询关联内容
			if (alarmCurrentListShow != null
					&& alarmCurrentListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						AlarmCurrentEntity.class, AlarmCurrentEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<AlarmCurrentEntity> alarmCurrentList = new ArrayList<AlarmCurrentEntity>();
					for (Object o : list) {
						alarmCurrentList.add((AlarmCurrentEntity) o);
					}
					obj.setAlarmCurrentList(alarmCurrentList);
				}
			}
			// 查询关联内容
			if (alarmHistoryListShow != null
					&& alarmHistoryListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						AlarmHistoryEntity.class, AlarmHistoryEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<AlarmHistoryEntity> alarmHistoryList = new ArrayList<AlarmHistoryEntity>();
					for (Object o : list) {
						alarmHistoryList.add((AlarmHistoryEntity) o);
					}
					obj.setAlarmHistoryList(alarmHistoryList);
				}
			}
			// 查询关联内容
			if (currentBedListShow != null && currentBedListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentBedEntity.class, CurrentBedEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentBedEntity> currentBedList = new ArrayList<CurrentBedEntity>();
					for (Object o : list) {
						currentBedList.add((CurrentBedEntity) o);
					}
					obj.setCurrentBedList(currentBedList);
				}
			}
			// 查询关联内容
			if (currentDoorListShow != null
					&& currentDoorListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentDoorEntity.class, CurrentDoorEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentDoorEntity> currentDoorList = new ArrayList<CurrentDoorEntity>();
					for (Object o : list) {
						currentDoorList.add((CurrentDoorEntity) o);
					}
					obj.setCurrentDoorList(currentDoorList);
				}
			}
			// 查询关联内容
			if (currentGatewayListShow != null
					&& currentGatewayListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentGatewayEntity.class,
						CurrentGatewayEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentGatewayEntity> currentGatewayList = new ArrayList<CurrentGatewayEntity>();
					for (Object o : list) {
						currentGatewayList.add((CurrentGatewayEntity) o);
					}
					obj.setCurrentGatewayList(currentGatewayList);
				}
			}
			// 查询关联内容
			if (currentKeyalarmListShow != null
					&& currentKeyalarmListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentKeyalarmEntity.class,
						CurrentKeyalarmEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentKeyalarmEntity> currentKeyalarmList = new ArrayList<CurrentKeyalarmEntity>();
					for (Object o : list) {
						currentKeyalarmList.add((CurrentKeyalarmEntity) o);
					}
					obj.setCurrentKeyalarmList(currentKeyalarmList);
				}
			}
			// 查询关联内容
			if (currentLocationListShow != null
					&& currentLocationListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentLocationEntity.class,
						CurrentLocationEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentLocationEntity> currentLocationList = new ArrayList<CurrentLocationEntity>();
					for (Object o : list) {
						currentLocationList.add((CurrentLocationEntity) o);
					}
					obj.setCurrentLocationList(currentLocationList);
				}
			}
			// 查询关联内容
			if (currentUrineListShow != null
					&& currentUrineListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentUrineEntity.class, CurrentUrineEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentUrineEntity> currentUrineList = new ArrayList<CurrentUrineEntity>();
					for (Object o : list) {
						currentUrineList.add((CurrentUrineEntity) o);
					}
					obj.setCurrentUrineList(currentUrineList);
				}
			}
			// 查询关联内容
			if (currentWandaiListShow != null
					&& currentWandaiListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentWandaiEntity.class, CurrentWandaiEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentWandaiEntity> currentWandaiList = new ArrayList<CurrentWandaiEntity>();
					for (Object o : list) {
						currentWandaiList.add((CurrentWandaiEntity) o);
					}
					obj.setCurrentWandaiList(currentWandaiList);
				}
			}
			// 查询关联内容
			if (historyBedListShow != null && historyBedListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryBedEntity.class, HistoryBedEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryBedEntity> historyBedList = new ArrayList<HistoryBedEntity>();
					for (Object o : list) {
						historyBedList.add((HistoryBedEntity) o);
					}
					obj.setHistoryBedList(historyBedList);
				}
			}
			// 查询关联内容
			if (historyDoorListShow != null
					&& historyDoorListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryDoorEntity.class, HistoryDoorEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryDoorEntity> historyDoorList = new ArrayList<HistoryDoorEntity>();
					for (Object o : list) {
						historyDoorList.add((HistoryDoorEntity) o);
					}
					obj.setHistoryDoorList(historyDoorList);
				}
			}
			// 查询关联内容
			if (historyKeyalarmListShow != null
					&& historyKeyalarmListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryKeyalarmEntity.class,
						HistoryKeyalarmEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryKeyalarmEntity> historyKeyalarmList = new ArrayList<HistoryKeyalarmEntity>();
					for (Object o : list) {
						historyKeyalarmList.add((HistoryKeyalarmEntity) o);
					}
					obj.setHistoryKeyalarmList(historyKeyalarmList);
				}
			}
			// 查询关联内容
			if (historyLocationBodyListShow != null
					&& historyLocationBodyListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryLocationBodyEntity.class,
						HistoryLocationBodyEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryLocationBodyEntity> historyLocationBodyList = new ArrayList<HistoryLocationBodyEntity>();
					for (Object o : list) {
						historyLocationBodyList
								.add((HistoryLocationBodyEntity) o);
					}
					obj.setHistoryLocationBodyList(historyLocationBodyList);
				}
			}
			// 查询关联内容
			if (historyLocationManualListShow != null
					&& historyLocationManualListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryLocationManualEntity.class,
						HistoryLocationManualEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryLocationManualEntity> historyLocationManualList = new ArrayList<HistoryLocationManualEntity>();
					for (Object o : list) {
						historyLocationManualList
								.add((HistoryLocationManualEntity) o);
					}
					obj.setHistoryLocationManualList(historyLocationManualList);
				}
			}
			// 查询关联内容
			if (historyLocationMoveListShow != null
					&& historyLocationMoveListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryLocationMoveEntity.class,
						HistoryLocationMoveEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryLocationMoveEntity> historyLocationMoveList = new ArrayList<HistoryLocationMoveEntity>();
					for (Object o : list) {
						historyLocationMoveList
								.add((HistoryLocationMoveEntity) o);
					}
					obj.setHistoryLocationMoveList(historyLocationMoveList);
				}
			}
			// 查询关联内容
			if (historyLocationPosListShow != null
					&& historyLocationPosListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryLocationPosEntity.class,
						HistoryLocationPosEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryLocationPosEntity> historyLocationPosList = new ArrayList<HistoryLocationPosEntity>();
					for (Object o : list) {
						historyLocationPosList
								.add((HistoryLocationPosEntity) o);
					}
					obj.setHistoryLocationPosList(historyLocationPosList);
				}
			}
			// 查询关联内容
			if (historyUrineListShow != null
					&& historyUrineListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryUrineEntity.class, HistoryUrineEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryUrineEntity> historyUrineList = new ArrayList<HistoryUrineEntity>();
					for (Object o : list) {
						historyUrineList.add((HistoryUrineEntity) o);
					}
					obj.setHistoryUrineList(historyUrineList);
				}
			}
			// 查询关联内容
			if (currentIrListShow != null && currentIrListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentIrEntity.class, CurrentIrEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentIrEntity> currentIrList = new ArrayList<CurrentIrEntity>();
					for (Object o : list) {
						currentIrList.add((CurrentIrEntity) o);
					}
					obj.setCurrentIrList(currentIrList);
				}
			}
			// 查询关联内容
			if (historyIrListShow != null && historyIrListShow.booleanValue()
					&& obj != null && obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryIrEntity.class, HistoryIrEntity.DEV_ID,
						String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryIrEntity> historyIrList = new ArrayList<HistoryIrEntity>();
					for (Object o : list) {
						historyIrList.add((HistoryIrEntity) o);
					}
					obj.setHistoryIrList(historyIrList);
				}
			}
			// 查询关联内容
			if (currentTizhengBedListShow != null
					&& currentTizhengBedListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						CurrentTizhengBedEntity.class,
						CurrentTizhengBedEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<CurrentTizhengBedEntity> currentTizhengBedList = new ArrayList<CurrentTizhengBedEntity>();
					for (Object o : list) {
						currentTizhengBedList.add((CurrentTizhengBedEntity) o);
					}
					obj.setCurrentTizhengBedList(currentTizhengBedList);
				}
			}
			// 查询关联内容
			if (historyTizhengBedListShow != null
					&& historyTizhengBedListShow.booleanValue() && obj != null
					&& obj.getId() > 0) {
				List<Object> list = dbManager.searchListByColumn(
						HistoryTizhengBedEntity.class,
						HistoryTizhengBedEntity.DEV_ID, String.valueOf(id));
				if (list != null && list.size() > 0) {
					List<HistoryTizhengBedEntity> historyTizhengBedList = new ArrayList<HistoryTizhengBedEntity>();
					for (Object o : list) {
						historyTizhengBedList.add((HistoryTizhengBedEntity) o);
					}
					obj.setHistoryTizhengBedList(historyTizhengBedList);
				}
			}
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

		Object positionId = queryMap.get("positionId");

		QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt,
				"0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.eq,
					id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt,
					id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge,
					id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt,
					id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le,
					id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in,
					id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.eq, alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.like, alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNull, alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.in, alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}

		if (positionId != null) {
			QueryCondition qc1 = new QueryCondition(
					DevPositionEntity.POSITION_ID, QueryCondition.eq,
					positionId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getDevId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(DevEntity.ID,
							QueryCondition.in, strIds));
				}
			} else {
				return list;
			}
		}
		list = dbManager.queryByCondition(DevEntity.class, qc);
		return list;
	}

	/**
	 * 根据条件查询记录集合（不分页 带排序 带级联查询）
	 * 
	 * @param queryMap
	 *            查询条件集合
	 * @param orderList
	 *            排序条件集合
	 * @param positionListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param parentDevShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param alarmCurrentListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param alarmHistoryListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentDoorListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentGatewayListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentKeyalarmListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentLocationListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentUrineListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentWandaiListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyDoorListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyKeyalarmListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationBodyListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationManualListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationMoveListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationPosListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyUrineListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentIrListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyIrListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentTizhengBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyTizhengBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @return
	 */
	public List<Object> getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList, Boolean positionListShow,
			Boolean parentDevShow, Boolean alarmCurrentListShow,
			Boolean alarmHistoryListShow, Boolean currentBedListShow,
			Boolean currentDoorListShow, Boolean currentGatewayListShow,
			Boolean currentKeyalarmListShow, Boolean currentLocationListShow,
			Boolean currentUrineListShow, Boolean currentWandaiListShow,
			Boolean historyBedListShow, Boolean historyDoorListShow,
			Boolean historyKeyalarmListShow,
			Boolean historyLocationBodyListShow,
			Boolean historyLocationManualListShow,
			Boolean historyLocationMoveListShow,
			Boolean historyLocationPosListShow, Boolean historyUrineListShow,
			Boolean currentIrListShow, Boolean historyIrListShow,
			Boolean currentTizhengBedListShow, Boolean historyTizhengBedListShow) {
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

		Object positionId = queryMap.get("positionId");

		QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt,
				"0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.eq,
					id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt,
					id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge,
					id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt,
					id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le,
					id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in,
					id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.eq, alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.like, alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNull, alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.in, alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}

		if (positionId != null) {
			QueryCondition qc1 = new QueryCondition(
					DevPositionEntity.POSITION_ID, QueryCondition.eq,
					positionId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getDevId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(DevEntity.ID,
							QueryCondition.in, strIds));
				}
			} else {
				return list;
			}
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
		list = dbManager.queryByConditions(DevEntity.class, qc, oc);
		int a = 0;
		if (positionListShow != null && positionListShow.booleanValue()) {
			a++;
		}
		if (alarmCurrentListShow != null && alarmCurrentListShow.booleanValue()) {
			a++;
		}
		if (alarmHistoryListShow != null && alarmHistoryListShow.booleanValue()) {
			a++;
		}
		if (currentBedListShow != null && currentBedListShow.booleanValue()) {
			a++;
		}
		if (currentDoorListShow != null && currentDoorListShow.booleanValue()) {
			a++;
		}
		if (currentGatewayListShow != null
				&& currentGatewayListShow.booleanValue()) {
			a++;
		}
		if (currentKeyalarmListShow != null
				&& currentKeyalarmListShow.booleanValue()) {
			a++;
		}
		if (currentLocationListShow != null
				&& currentLocationListShow.booleanValue()) {
			a++;
		}
		if (currentUrineListShow != null && currentUrineListShow.booleanValue()) {
			a++;
		}
		if (currentWandaiListShow != null
				&& currentWandaiListShow.booleanValue()) {
			a++;
		}
		if (historyBedListShow != null && historyBedListShow.booleanValue()) {
			a++;
		}
		if (historyDoorListShow != null && historyDoorListShow.booleanValue()) {
			a++;
		}
		if (historyKeyalarmListShow != null
				&& historyKeyalarmListShow.booleanValue()) {
			a++;
		}
		if (historyLocationBodyListShow != null
				&& historyLocationBodyListShow.booleanValue()) {
			a++;
		}
		if (historyLocationManualListShow != null
				&& historyLocationManualListShow.booleanValue()) {
			a++;
		}
		if (historyLocationMoveListShow != null
				&& historyLocationMoveListShow.booleanValue()) {
			a++;
		}
		if (historyLocationPosListShow != null
				&& historyLocationPosListShow.booleanValue()) {
			a++;
		}
		if (historyUrineListShow != null && historyUrineListShow.booleanValue()) {
			a++;
		}
		if (currentIrListShow != null && currentIrListShow.booleanValue()) {
			a++;
		}
		if (historyIrListShow != null && historyIrListShow.booleanValue()) {
			a++;
		}
		if (currentTizhengBedListShow != null
				&& currentTizhengBedListShow.booleanValue()) {
			a++;
		}
		if (historyTizhengBedListShow != null
				&& historyTizhengBedListShow.booleanValue()) {
			a++;
		}
		if (a > 0 && list != null && list.size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < list.size(); i++) {
				DevEntity obj = (DevEntity) list.get(i);
				// 查询关联内容
				if (positionListShow != null && positionListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> rlist = dbManager.searchListByColumn(
							DevPositionEntity.class, DevPositionEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (rlist != null && rlist.size() > 0) {
						for (Object robj : rlist) {
							DevPositionEntity robject = (DevPositionEntity) robj;
							Integer objId = robject.getPositionId();
							if (objId != null) {
								PositionEntity position = (PositionEntity) dbManager
										.getById(objId, PositionEntity.class);
								List<PositionEntity> positionList = obj
										.getPositionList();
								if (positionList == null
										|| positionList.size() == 0) {
									positionList = new ArrayList<PositionEntity>();
								}
								positionList.add(position);
								obj.setPositionList(positionList);
							}
						}
					}
				}
				// 查询关联内容
				if (parentDevShow != null && parentDevShow.booleanValue()
						&& obj != null && obj.getParentId() > 0) {
					DevEntity dev = (DevEntity) dbManager.getById(
							obj.getParentId(), DevEntity.class);
					obj.setParentDev(dev);
				}
				// 查询关联内容
				if (alarmCurrentListShow != null
						&& alarmCurrentListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							AlarmCurrentEntity.class,
							AlarmCurrentEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<AlarmCurrentEntity> alarmCurrentList = new ArrayList<AlarmCurrentEntity>();
						for (Object o : objList) {
							alarmCurrentList.add((AlarmCurrentEntity) o);
						}
						obj.setAlarmCurrentList(alarmCurrentList);
					}
				}
				// 查询关联内容
				if (alarmHistoryListShow != null
						&& alarmHistoryListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							AlarmHistoryEntity.class,
							AlarmHistoryEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<AlarmHistoryEntity> alarmHistoryList = new ArrayList<AlarmHistoryEntity>();
						for (Object o : objList) {
							alarmHistoryList.add((AlarmHistoryEntity) o);
						}
						obj.setAlarmHistoryList(alarmHistoryList);
					}
				}
				// 查询关联内容
				if (currentBedListShow != null
						&& currentBedListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentBedEntity.class, CurrentBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentBedEntity> currentBedList = new ArrayList<CurrentBedEntity>();
						for (Object o : objList) {
							currentBedList.add((CurrentBedEntity) o);
						}
						obj.setCurrentBedList(currentBedList);
					}
				}
				// 查询关联内容
				if (currentDoorListShow != null
						&& currentDoorListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentDoorEntity.class, CurrentDoorEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentDoorEntity> currentDoorList = new ArrayList<CurrentDoorEntity>();
						for (Object o : objList) {
							currentDoorList.add((CurrentDoorEntity) o);
						}
						obj.setCurrentDoorList(currentDoorList);
					}
				}
				// 查询关联内容
				if (currentGatewayListShow != null
						&& currentGatewayListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentGatewayEntity.class,
							CurrentGatewayEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentGatewayEntity> currentGatewayList = new ArrayList<CurrentGatewayEntity>();
						for (Object o : objList) {
							currentGatewayList.add((CurrentGatewayEntity) o);
						}
						obj.setCurrentGatewayList(currentGatewayList);
					}
				}
				// 查询关联内容
				if (currentKeyalarmListShow != null
						&& currentKeyalarmListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentKeyalarmEntity.class,
							CurrentKeyalarmEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentKeyalarmEntity> currentKeyalarmList = new ArrayList<CurrentKeyalarmEntity>();
						for (Object o : objList) {
							currentKeyalarmList.add((CurrentKeyalarmEntity) o);
						}
						obj.setCurrentKeyalarmList(currentKeyalarmList);
					}
				}
				// 查询关联内容
				if (currentLocationListShow != null
						&& currentLocationListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentLocationEntity.class,
							CurrentLocationEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentLocationEntity> currentLocationList = new ArrayList<CurrentLocationEntity>();
						for (Object o : objList) {
							currentLocationList.add((CurrentLocationEntity) o);
						}
						obj.setCurrentLocationList(currentLocationList);
					}
				}
				// 查询关联内容
				if (currentUrineListShow != null
						&& currentUrineListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentUrineEntity.class,
							CurrentUrineEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentUrineEntity> currentUrineList = new ArrayList<CurrentUrineEntity>();
						for (Object o : objList) {
							currentUrineList.add((CurrentUrineEntity) o);
						}
						obj.setCurrentUrineList(currentUrineList);
					}
				}
				// 查询关联内容
				if (currentWandaiListShow != null
						&& currentWandaiListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentWandaiEntity.class,
							CurrentWandaiEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentWandaiEntity> currentWandaiList = new ArrayList<CurrentWandaiEntity>();
						for (Object o : objList) {
							currentWandaiList.add((CurrentWandaiEntity) o);
						}
						obj.setCurrentWandaiList(currentWandaiList);
					}
				}
				// 查询关联内容
				if (historyBedListShow != null
						&& historyBedListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryBedEntity.class, HistoryBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryBedEntity> historyBedList = new ArrayList<HistoryBedEntity>();
						for (Object o : objList) {
							historyBedList.add((HistoryBedEntity) o);
						}
						obj.setHistoryBedList(historyBedList);
					}
				}
				// 查询关联内容
				if (historyDoorListShow != null
						&& historyDoorListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryDoorEntity.class, HistoryDoorEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryDoorEntity> historyDoorList = new ArrayList<HistoryDoorEntity>();
						for (Object o : objList) {
							historyDoorList.add((HistoryDoorEntity) o);
						}
						obj.setHistoryDoorList(historyDoorList);
					}
				}
				// 查询关联内容
				if (historyKeyalarmListShow != null
						&& historyKeyalarmListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryKeyalarmEntity.class,
							HistoryKeyalarmEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryKeyalarmEntity> historyKeyalarmList = new ArrayList<HistoryKeyalarmEntity>();
						for (Object o : objList) {
							historyKeyalarmList.add((HistoryKeyalarmEntity) o);
						}
						obj.setHistoryKeyalarmList(historyKeyalarmList);
					}
				}
				// 查询关联内容
				if (historyLocationBodyListShow != null
						&& historyLocationBodyListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationBodyEntity.class,
							HistoryLocationBodyEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationBodyEntity> historyLocationBodyList = new ArrayList<HistoryLocationBodyEntity>();
						for (Object o : objList) {
							historyLocationBodyList
									.add((HistoryLocationBodyEntity) o);
						}
						obj.setHistoryLocationBodyList(historyLocationBodyList);
					}
				}
				// 查询关联内容
				if (historyLocationManualListShow != null
						&& historyLocationManualListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationManualEntity.class,
							HistoryLocationManualEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationManualEntity> historyLocationManualList = new ArrayList<HistoryLocationManualEntity>();
						for (Object o : objList) {
							historyLocationManualList
									.add((HistoryLocationManualEntity) o);
						}
						obj.setHistoryLocationManualList(historyLocationManualList);
					}
				}
				// 查询关联内容
				if (historyLocationMoveListShow != null
						&& historyLocationMoveListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationMoveEntity.class,
							HistoryLocationMoveEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationMoveEntity> historyLocationMoveList = new ArrayList<HistoryLocationMoveEntity>();
						for (Object o : objList) {
							historyLocationMoveList
									.add((HistoryLocationMoveEntity) o);
						}
						obj.setHistoryLocationMoveList(historyLocationMoveList);
					}
				}
				// 查询关联内容
				if (historyLocationPosListShow != null
						&& historyLocationPosListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationPosEntity.class,
							HistoryLocationPosEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationPosEntity> historyLocationPosList = new ArrayList<HistoryLocationPosEntity>();
						for (Object o : objList) {
							historyLocationPosList
									.add((HistoryLocationPosEntity) o);
						}
						obj.setHistoryLocationPosList(historyLocationPosList);
					}
				}
				// 查询关联内容
				if (historyUrineListShow != null
						&& historyUrineListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryUrineEntity.class,
							HistoryUrineEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryUrineEntity> historyUrineList = new ArrayList<HistoryUrineEntity>();
						for (Object o : objList) {
							historyUrineList.add((HistoryUrineEntity) o);
						}
						obj.setHistoryUrineList(historyUrineList);
					}
				}
				// 查询关联内容
				if (currentIrListShow != null
						&& currentIrListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentIrEntity.class, CurrentIrEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentIrEntity> currentIrList = new ArrayList<CurrentIrEntity>();
						for (Object o : objList) {
							currentIrList.add((CurrentIrEntity) o);
						}
						obj.setCurrentIrList(currentIrList);
					}
				}
				// 查询关联内容
				if (historyIrListShow != null
						&& historyIrListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryIrEntity.class, HistoryIrEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryIrEntity> historyIrList = new ArrayList<HistoryIrEntity>();
						for (Object o : objList) {
							historyIrList.add((HistoryIrEntity) o);
						}
						obj.setHistoryIrList(historyIrList);
					}
				}
				// 查询关联内容
				if (currentTizhengBedListShow != null
						&& currentTizhengBedListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentTizhengBedEntity.class,
							CurrentTizhengBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentTizhengBedEntity> currentTizhengBedList = new ArrayList<CurrentTizhengBedEntity>();
						for (Object o : objList) {
							currentTizhengBedList
									.add((CurrentTizhengBedEntity) o);
						}
						obj.setCurrentTizhengBedList(currentTizhengBedList);
					}
				}
				// 查询关联内容
				if (historyTizhengBedListShow != null
						&& historyTizhengBedListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryTizhengBedEntity.class,
							HistoryTizhengBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryTizhengBedEntity> historyTizhengBedList = new ArrayList<HistoryTizhengBedEntity>();
						for (Object o : objList) {
							historyTizhengBedList
									.add((HistoryTizhengBedEntity) o);
						}
						obj.setHistoryTizhengBedList(historyTizhengBedList);
					}
				}
				result.add(obj);
			}
			list = result;
		}
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

		Object positionId = queryMap.get("positionId");

		QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt,
				"0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.eq,
					id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt,
					id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge,
					id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt,
					id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le,
					id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in,
					id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.eq, alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.like, alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNull, alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.in, alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}

		if (positionId != null) {
			QueryCondition qc1 = new QueryCondition(
					DevPositionEntity.POSITION_ID, QueryCondition.eq,
					positionId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getDevId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(DevEntity.ID,
							QueryCondition.in, strIds));
				}
			} else {
				return pagelist;
			}
		}
		pagelist = dbManager.queryByCondition(DevEntity.class, qc, pageno,
				pagesize);
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
	 * @param positionListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param parentDevShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param alarmCurrentListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param alarmHistoryListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentDoorListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentGatewayListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentKeyalarmListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentLocationListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentUrineListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentWandaiListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyDoorListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyKeyalarmListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationBodyListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationManualListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationMoveListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyLocationPosListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyUrineListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentIrListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyIrListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param currentTizhengBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @param historyTizhengBedListShow
	 *            是否查询关联信息,默认false(当为true时注意效率)
	 * @return
	 */
	public PageList getListByCondition(Map<String, Object> queryMap,
			List<OrderVO> orderList, int pageno, int pagesize,
			Boolean positionListShow, Boolean parentDevShow,
			Boolean alarmCurrentListShow, Boolean alarmHistoryListShow,
			Boolean currentBedListShow, Boolean currentDoorListShow,
			Boolean currentGatewayListShow, Boolean currentKeyalarmListShow,
			Boolean currentLocationListShow, Boolean currentUrineListShow,
			Boolean currentWandaiListShow, Boolean historyBedListShow,
			Boolean historyDoorListShow, Boolean historyKeyalarmListShow,
			Boolean historyLocationBodyListShow,
			Boolean historyLocationManualListShow,
			Boolean historyLocationMoveListShow,
			Boolean historyLocationPosListShow, Boolean historyUrineListShow,
			Boolean currentIrListShow, Boolean historyIrListShow,
			Boolean currentTizhengBedListShow, Boolean historyTizhengBedListShow) {
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

		Object positionId = queryMap.get("positionId");

		QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt,
				"0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.eq,
					id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt,
					id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge,
					id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt,
					id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le,
					id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in,
					id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.eq, alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.like, alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNull, alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.in, alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}

		if (positionId != null) {
			QueryCondition qc1 = new QueryCondition(
					DevPositionEntity.POSITION_ID, QueryCondition.eq,
					positionId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getDevId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(DevEntity.ID,
							QueryCondition.in, strIds));
				}
			} else {
				return pagelist;
			}
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
		pagelist = dbManager.queryByConditions(DevEntity.class, qc, oc, pageno,
				pagesize);
		int a = 0;
		if (positionListShow != null && positionListShow.booleanValue()) {
			a++;
		}
		if (alarmCurrentListShow != null && alarmCurrentListShow.booleanValue()) {
			a++;
		}
		if (alarmHistoryListShow != null && alarmHistoryListShow.booleanValue()) {
			a++;
		}
		if (currentBedListShow != null && currentBedListShow.booleanValue()) {
			a++;
		}
		if (currentDoorListShow != null && currentDoorListShow.booleanValue()) {
			a++;
		}
		if (currentGatewayListShow != null
				&& currentGatewayListShow.booleanValue()) {
			a++;
		}
		if (currentKeyalarmListShow != null
				&& currentKeyalarmListShow.booleanValue()) {
			a++;
		}
		if (currentLocationListShow != null
				&& currentLocationListShow.booleanValue()) {
			a++;
		}
		if (currentUrineListShow != null && currentUrineListShow.booleanValue()) {
			a++;
		}
		if (currentWandaiListShow != null
				&& currentWandaiListShow.booleanValue()) {
			a++;
		}
		if (historyBedListShow != null && historyBedListShow.booleanValue()) {
			a++;
		}
		if (historyDoorListShow != null && historyDoorListShow.booleanValue()) {
			a++;
		}
		if (historyKeyalarmListShow != null
				&& historyKeyalarmListShow.booleanValue()) {
			a++;
		}
		if (historyLocationBodyListShow != null
				&& historyLocationBodyListShow.booleanValue()) {
			a++;
		}
		if (historyLocationManualListShow != null
				&& historyLocationManualListShow.booleanValue()) {
			a++;
		}
		if (historyLocationMoveListShow != null
				&& historyLocationMoveListShow.booleanValue()) {
			a++;
		}
		if (historyLocationPosListShow != null
				&& historyLocationPosListShow.booleanValue()) {
			a++;
		}
		if (historyUrineListShow != null && historyUrineListShow.booleanValue()) {
			a++;
		}
		if (currentIrListShow != null && currentIrListShow.booleanValue()) {
			a++;
		}
		if (historyIrListShow != null && historyIrListShow.booleanValue()) {
			a++;
		}
		if (currentTizhengBedListShow != null
				&& currentTizhengBedListShow.booleanValue()) {
			a++;
		}
		if (historyTizhengBedListShow != null
				&& historyTizhengBedListShow.booleanValue()) {
			a++;
		}
		if (a > 0 && pagelist != null && pagelist.getResultList() != null
				&& pagelist.getResultList().size() > 0) {
			List<Object> result = new ArrayList<Object>();
			for (int i = 0; i < pagelist.getResultList().size(); i++) {
				DevEntity obj = (DevEntity) pagelist.getResultList().get(i);
				// 查询关联内容
				if (positionListShow != null && positionListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> rlist = dbManager.searchListByColumn(
							DevPositionEntity.class, DevPositionEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (rlist != null && rlist.size() > 0) {
						for (Object robj : rlist) {
							DevPositionEntity robject = (DevPositionEntity) robj;
							Integer objId = robject.getPositionId();
							if (objId != null) {
								PositionEntity position = (PositionEntity) dbManager
										.getById(objId, PositionEntity.class);
								List<PositionEntity> positionList = obj
										.getPositionList();
								if (positionList == null
										|| positionList.size() == 0) {
									positionList = new ArrayList<PositionEntity>();
								}
								positionList.add(position);
								obj.setPositionList(positionList);
							}
						}
					}
				}
				// 查询关联内容
				if (parentDevShow != null && parentDevShow.booleanValue()
						&& obj != null && obj.getParentId() > 0) {
					DevEntity dev = (DevEntity) dbManager.getById(
							obj.getParentId(), DevEntity.class);
					obj.setParentDev(dev);
				}
				// 查询关联内容
				if (alarmCurrentListShow != null
						&& alarmCurrentListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							AlarmCurrentEntity.class,
							AlarmCurrentEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<AlarmCurrentEntity> alarmCurrentList = new ArrayList<AlarmCurrentEntity>();
						for (Object o : objList) {
							alarmCurrentList.add((AlarmCurrentEntity) o);
						}
						obj.setAlarmCurrentList(alarmCurrentList);
					}
				}
				// 查询关联内容
				if (alarmHistoryListShow != null
						&& alarmHistoryListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							AlarmHistoryEntity.class,
							AlarmHistoryEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<AlarmHistoryEntity> alarmHistoryList = new ArrayList<AlarmHistoryEntity>();
						for (Object o : objList) {
							alarmHistoryList.add((AlarmHistoryEntity) o);
						}
						obj.setAlarmHistoryList(alarmHistoryList);
					}
				}
				// 查询关联内容
				if (currentBedListShow != null
						&& currentBedListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentBedEntity.class, CurrentBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentBedEntity> currentBedList = new ArrayList<CurrentBedEntity>();
						for (Object o : objList) {
							currentBedList.add((CurrentBedEntity) o);
						}
						obj.setCurrentBedList(currentBedList);
					}
				}
				// 查询关联内容
				if (currentDoorListShow != null
						&& currentDoorListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentDoorEntity.class, CurrentDoorEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentDoorEntity> currentDoorList = new ArrayList<CurrentDoorEntity>();
						for (Object o : objList) {
							currentDoorList.add((CurrentDoorEntity) o);
						}
						obj.setCurrentDoorList(currentDoorList);
					}
				}
				// 查询关联内容
				if (currentGatewayListShow != null
						&& currentGatewayListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentGatewayEntity.class,
							CurrentGatewayEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentGatewayEntity> currentGatewayList = new ArrayList<CurrentGatewayEntity>();
						for (Object o : objList) {
							currentGatewayList.add((CurrentGatewayEntity) o);
						}
						obj.setCurrentGatewayList(currentGatewayList);
					}
				}
				// 查询关联内容
				if (currentKeyalarmListShow != null
						&& currentKeyalarmListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentKeyalarmEntity.class,
							CurrentKeyalarmEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentKeyalarmEntity> currentKeyalarmList = new ArrayList<CurrentKeyalarmEntity>();
						for (Object o : objList) {
							currentKeyalarmList.add((CurrentKeyalarmEntity) o);
						}
						obj.setCurrentKeyalarmList(currentKeyalarmList);
					}
				}
				// 查询关联内容
				if (currentLocationListShow != null
						&& currentLocationListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentLocationEntity.class,
							CurrentLocationEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentLocationEntity> currentLocationList = new ArrayList<CurrentLocationEntity>();
						for (Object o : objList) {
							currentLocationList.add((CurrentLocationEntity) o);
						}
						obj.setCurrentLocationList(currentLocationList);
					}
				}
				// 查询关联内容
				if (currentUrineListShow != null
						&& currentUrineListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentUrineEntity.class,
							CurrentUrineEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentUrineEntity> currentUrineList = new ArrayList<CurrentUrineEntity>();
						for (Object o : objList) {
							currentUrineList.add((CurrentUrineEntity) o);
						}
						obj.setCurrentUrineList(currentUrineList);
					}
				}
				// 查询关联内容
				if (currentWandaiListShow != null
						&& currentWandaiListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentWandaiEntity.class,
							CurrentWandaiEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentWandaiEntity> currentWandaiList = new ArrayList<CurrentWandaiEntity>();
						for (Object o : objList) {
							currentWandaiList.add((CurrentWandaiEntity) o);
						}
						obj.setCurrentWandaiList(currentWandaiList);
					}
				}
				// 查询关联内容
				if (historyBedListShow != null
						&& historyBedListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryBedEntity.class, HistoryBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryBedEntity> historyBedList = new ArrayList<HistoryBedEntity>();
						for (Object o : objList) {
							historyBedList.add((HistoryBedEntity) o);
						}
						obj.setHistoryBedList(historyBedList);
					}
				}
				// 查询关联内容
				if (historyDoorListShow != null
						&& historyDoorListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryDoorEntity.class, HistoryDoorEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryDoorEntity> historyDoorList = new ArrayList<HistoryDoorEntity>();
						for (Object o : objList) {
							historyDoorList.add((HistoryDoorEntity) o);
						}
						obj.setHistoryDoorList(historyDoorList);
					}
				}
				// 查询关联内容
				if (historyKeyalarmListShow != null
						&& historyKeyalarmListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryKeyalarmEntity.class,
							HistoryKeyalarmEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryKeyalarmEntity> historyKeyalarmList = new ArrayList<HistoryKeyalarmEntity>();
						for (Object o : objList) {
							historyKeyalarmList.add((HistoryKeyalarmEntity) o);
						}
						obj.setHistoryKeyalarmList(historyKeyalarmList);
					}
				}
				// 查询关联内容
				if (historyLocationBodyListShow != null
						&& historyLocationBodyListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationBodyEntity.class,
							HistoryLocationBodyEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationBodyEntity> historyLocationBodyList = new ArrayList<HistoryLocationBodyEntity>();
						for (Object o : objList) {
							historyLocationBodyList
									.add((HistoryLocationBodyEntity) o);
						}
						obj.setHistoryLocationBodyList(historyLocationBodyList);
					}
				}
				// 查询关联内容
				if (historyLocationManualListShow != null
						&& historyLocationManualListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationManualEntity.class,
							HistoryLocationManualEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationManualEntity> historyLocationManualList = new ArrayList<HistoryLocationManualEntity>();
						for (Object o : objList) {
							historyLocationManualList
									.add((HistoryLocationManualEntity) o);
						}
						obj.setHistoryLocationManualList(historyLocationManualList);
					}
				}
				// 查询关联内容
				if (historyLocationMoveListShow != null
						&& historyLocationMoveListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationMoveEntity.class,
							HistoryLocationMoveEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationMoveEntity> historyLocationMoveList = new ArrayList<HistoryLocationMoveEntity>();
						for (Object o : objList) {
							historyLocationMoveList
									.add((HistoryLocationMoveEntity) o);
						}
						obj.setHistoryLocationMoveList(historyLocationMoveList);
					}
				}
				// 查询关联内容
				if (historyLocationPosListShow != null
						&& historyLocationPosListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryLocationPosEntity.class,
							HistoryLocationPosEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryLocationPosEntity> historyLocationPosList = new ArrayList<HistoryLocationPosEntity>();
						for (Object o : objList) {
							historyLocationPosList
									.add((HistoryLocationPosEntity) o);
						}
						obj.setHistoryLocationPosList(historyLocationPosList);
					}
				}
				// 查询关联内容
				if (historyUrineListShow != null
						&& historyUrineListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryUrineEntity.class,
							HistoryUrineEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryUrineEntity> historyUrineList = new ArrayList<HistoryUrineEntity>();
						for (Object o : objList) {
							historyUrineList.add((HistoryUrineEntity) o);
						}
						obj.setHistoryUrineList(historyUrineList);
					}
				}
				// 查询关联内容
				if (currentIrListShow != null
						&& currentIrListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentIrEntity.class, CurrentIrEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentIrEntity> currentIrList = new ArrayList<CurrentIrEntity>();
						for (Object o : objList) {
							currentIrList.add((CurrentIrEntity) o);
						}
						obj.setCurrentIrList(currentIrList);
					}
				}
				// 查询关联内容
				if (historyIrListShow != null
						&& historyIrListShow.booleanValue() && obj != null
						&& obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryIrEntity.class, HistoryIrEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryIrEntity> historyIrList = new ArrayList<HistoryIrEntity>();
						for (Object o : objList) {
							historyIrList.add((HistoryIrEntity) o);
						}
						obj.setHistoryIrList(historyIrList);
					}
				}
				// 查询关联内容
				if (currentTizhengBedListShow != null
						&& currentTizhengBedListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							CurrentTizhengBedEntity.class,
							CurrentTizhengBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<CurrentTizhengBedEntity> currentTizhengBedList = new ArrayList<CurrentTizhengBedEntity>();
						for (Object o : objList) {
							currentTizhengBedList
									.add((CurrentTizhengBedEntity) o);
						}
						obj.setCurrentTizhengBedList(currentTizhengBedList);
					}
				}
				// 查询关联内容
				if (historyTizhengBedListShow != null
						&& historyTizhengBedListShow.booleanValue()
						&& obj != null && obj.getId() > 0) {
					List<Object> objList = dbManager.searchListByColumn(
							HistoryTizhengBedEntity.class,
							HistoryTizhengBedEntity.DEV_ID,
							String.valueOf(obj.getId()));
					if (objList != null && objList.size() > 0) {
						List<HistoryTizhengBedEntity> historyTizhengBedList = new ArrayList<HistoryTizhengBedEntity>();
						for (Object o : objList) {
							historyTizhengBedList
									.add((HistoryTizhengBedEntity) o);
						}
						obj.setHistoryTizhengBedList(historyTizhengBedList);
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
	 * @param id
	 *            主键
	 * @param obj
	 */
	public boolean del(Integer id, Boolean delDevPositionList,
			Boolean delParentDev, Boolean delAlarmCurrentList,
			Boolean delAlarmHistoryList, Boolean delCurrentBedList,
			Boolean delCurrentDoorList, Boolean delCurrentGatewayList,
			Boolean delCurrentKeyalarmList, Boolean delCurrentLocationList,
			Boolean delCurrentUrineList, Boolean delCurrentWandaiList,
			Boolean delHistoryBedList, Boolean delHistoryDoorList,
			Boolean delHistoryKeyalarmList, Boolean delHistoryLocationBodyList,
			Boolean delHistoryLocationManualList,
			Boolean delHistoryLocationMoveList,
			Boolean delHistoryLocationPosList, Boolean delHistoryUrineList,
			Boolean delCurrentIrList, Boolean delHistoryIrList,
			Boolean delCurrentTizhengBedList, Boolean delHistoryTizhengBedList) {
		boolean result = false;
		if (id != null && id > 0) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDevPositionList != null
						&& delDevPositionList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							DevPositionEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							DevPositionEntity.class, qc);
				}
				// 删除关联信息
				if (delParentDev != null && delParentDev.booleanValue()) {
					DevEntity dev = (DevEntity) dbManager.getById(id,
							DevEntity.class);
					if (dev.getParentId() != null) {
						dbManager.delNoTransaction(dev.getParentId(),
								DevEntity.class);
					}
				}
				// 删除关联信息
				if (delAlarmCurrentList != null
						&& delAlarmCurrentList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							AlarmCurrentEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							AlarmCurrentEntity.class, qc);
				}
				// 删除关联信息
				if (delAlarmHistoryList != null
						&& delAlarmHistoryList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							AlarmHistoryEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							AlarmHistoryEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentBedList != null
						&& delCurrentBedList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentBedEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentBedEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentDoorList != null
						&& delCurrentDoorList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentDoorEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentDoorEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentGatewayList != null
						&& delCurrentGatewayList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentGatewayEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentGatewayEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentKeyalarmList != null
						&& delCurrentKeyalarmList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentKeyalarmEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentKeyalarmEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentLocationList != null
						&& delCurrentLocationList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentLocationEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentLocationEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentUrineList != null
						&& delCurrentUrineList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentUrineEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentUrineEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentWandaiList != null
						&& delCurrentWandaiList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentWandaiEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentWandaiEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryBedList != null
						&& delHistoryBedList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryBedEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryBedEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryDoorList != null
						&& delHistoryDoorList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryDoorEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryDoorEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryKeyalarmList != null
						&& delHistoryKeyalarmList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryKeyalarmEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryKeyalarmEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryLocationBodyList != null
						&& delHistoryLocationBodyList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryLocationBodyEntity.DEV_ID,
							QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryLocationBodyEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryLocationManualList != null
						&& delHistoryLocationManualList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryLocationManualEntity.DEV_ID,
							QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryLocationManualEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryLocationMoveList != null
						&& delHistoryLocationMoveList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryLocationMoveEntity.DEV_ID,
							QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryLocationMoveEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryLocationPosList != null
						&& delHistoryLocationPosList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryLocationPosEntity.DEV_ID, QueryCondition.eq,
							id);
					dbManager.delByConditionsNoTransaction(
							HistoryLocationPosEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryUrineList != null
						&& delHistoryUrineList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryUrineEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryUrineEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentIrList != null && delCurrentIrList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentIrEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							CurrentIrEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryIrList != null && delHistoryIrList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryIrEntity.DEV_ID, QueryCondition.eq, id);
					dbManager.delByConditionsNoTransaction(
							HistoryIrEntity.class, qc);
				}
				// 删除关联信息
				if (delCurrentTizhengBedList != null
						&& delCurrentTizhengBedList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							CurrentTizhengBedEntity.DEV_ID, QueryCondition.eq,
							id);
					dbManager.delByConditionsNoTransaction(
							CurrentTizhengBedEntity.class, qc);
				}
				// 删除关联信息
				if (delHistoryTizhengBedList != null
						&& delHistoryTizhengBedList.booleanValue()) {
					QueryCondition qc = new QueryCondition(
							HistoryTizhengBedEntity.DEV_ID, QueryCondition.eq,
							id);
					dbManager.delByConditionsNoTransaction(
							HistoryTizhengBedEntity.class, qc);
				}
				result = dbManager.delNoTransaction(id, DevEntity.class);
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
			Boolean delDevPositionList, Boolean delParentDevList,
			Boolean delAlarmCurrentList, Boolean delAlarmHistoryList,
			Boolean delCurrentBedList, Boolean delCurrentDoorList,
			Boolean delCurrentGatewayList, Boolean delCurrentKeyalarmList,
			Boolean delCurrentLocationList, Boolean delCurrentUrineList,
			Boolean delCurrentWandaiList, Boolean delHistoryBedList,
			Boolean delHistoryDoorList, Boolean delHistoryKeyalarmList,
			Boolean delHistoryLocationBodyList,
			Boolean delHistoryLocationManualList,
			Boolean delHistoryLocationMoveList,
			Boolean delHistoryLocationPosList, Boolean delHistoryUrineList,
			Boolean delCurrentIrList, Boolean delHistoryIrList,
			Boolean delCurrentTizhengBedList, Boolean delHistoryTizhengBedList) {
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
		Object positionId = queryMap.get("positionId");

		QueryCondition qc = new QueryCondition(DevEntity.ID, QueryCondition.gt,
				"0");
		if (id != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.eq,
					id));
		}
		if (id_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.gt,
					id_gt));
		}
		if (id_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.ge,
					id_ge));
		}
		if (id_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.lt,
					id_lt));
		}
		if (id_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.le,
					id_le));
		}
		if (id_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ID, QueryCondition.in,
					id_in));
		}
		if (name != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.eq, name));
		}
		if (name_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.like, name_like));
		}
		if (name_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNull, name_isNull));
		}
		if (name_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.isNotNull, name_isNotNull));
		}
		if (name_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.NAME,
					QueryCondition.in, name_in));
		}
		if (type != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.eq, type));
		}
		if (type_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.like, type_like));
		}
		if (type_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNull, type_isNull));
		}
		if (type_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.isNotNull, type_isNotNull));
		}
		if (type_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.TYPE,
					QueryCondition.in, type_in));
		}
		if (code != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.eq, code));
		}
		if (code_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.like, code_like));
		}
		if (code_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNull, code_isNull));
		}
		if (code_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.isNotNull, code_isNotNull));
		}
		if (code_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.CODE,
					QueryCondition.in, code_in));
		}
		if (alarmcontent != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.eq, alarmcontent));
		}
		if (alarmcontent_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.like, alarmcontent_like));
		}
		if (alarmcontent_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNull, alarmcontent_isNull));
		}
		if (alarmcontent_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.isNotNull, alarmcontent_isNotNull));
		}
		if (alarmcontent_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMCONTENT,
					QueryCondition.in, alarmcontent_in));
		}
		if (alarmdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.eq, alarmdevid));
		}
		if (alarmdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.like, alarmdevid_like));
		}
		if (alarmdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNull, alarmdevid_isNull));
		}
		if (alarmdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.isNotNull, alarmdevid_isNotNull));
		}
		if (alarmdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMDEVID,
					QueryCondition.in, alarmdevid_in));
		}
		if (lightno != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.eq, lightno));
		}
		if (lightno_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.gt, lightno_gt));
		}
		if (lightno_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.ge, lightno_ge));
		}
		if (lightno_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.lt, lightno_lt));
		}
		if (lightno_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.le, lightno_le));
		}
		if (lightno_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTNO,
					QueryCondition.in, lightno_in));
		}
		if (lightdevid != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.eq, lightdevid));
		}
		if (lightdevid_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.like, lightdevid_like));
		}
		if (lightdevid_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNull, lightdevid_isNull));
		}
		if (lightdevid_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.isNotNull, lightdevid_isNotNull));
		}
		if (lightdevid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.LIGHTDEVID,
					QueryCondition.in, lightdevid_in));
		}
		if (alarmphone != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.eq, alarmphone));
		}
		if (alarmphone_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.like, alarmphone_like));
		}
		if (alarmphone_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNull, alarmphone_isNull));
		}
		if (alarmphone_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.isNotNull, alarmphone_isNotNull));
		}
		if (alarmphone_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ALARMPHONE,
					QueryCondition.in, alarmphone_in));
		}
		if (emitid != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.eq, emitid));
		}
		if (emitid_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.gt, emitid_gt));
		}
		if (emitid_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.ge, emitid_ge));
		}
		if (emitid_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.lt, emitid_lt));
		}
		if (emitid_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.le, emitid_le));
		}
		if (emitid_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.EMITID,
					QueryCondition.in, emitid_in));
		}
		if (parentId != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.eq, parentId));
		}
		if (parentId_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.gt, parentId_gt));
		}
		if (parentId_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.ge, parentId_ge));
		}
		if (parentId_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.lt, parentId_lt));
		}
		if (parentId_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.le, parentId_le));
		}
		if (parentId_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.PARENT_ID,
					QueryCondition.in, parentId_in));
		}
		if (attribute != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.eq, attribute));
		}
		if (attribute_like != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.like, attribute_like));
		}
		if (attribute_isNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNull, attribute_isNull));
		}
		if (attribute_isNotNull != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.isNotNull, attribute_isNotNull));
		}
		if (attribute_in != null) {
			qc.andCondition(new QueryCondition(DevEntity.ATTRIBUTE,
					QueryCondition.in, attribute_in));
		}
		if (createdate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.gt, createdate_gt));
		}
		if (createdate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.ge, createdate_ge));
		}
		if (createdate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.lt, createdate_lt));
		}
		if (createdate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.CREATEDATE,
					QueryCondition.le, createdate_le));
		}
		if (updatedate_gt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.gt, updatedate_gt));
		}
		if (updatedate_ge != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.ge, updatedate_ge));
		}
		if (updatedate_lt != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.lt, updatedate_lt));
		}
		if (updatedate_le != null) {
			qc.andCondition(new QueryCondition(DevEntity.UPDATEDATE,
					QueryCondition.le, updatedate_le));
		}

		if (positionId != null) {
			QueryCondition qc1 = new QueryCondition(
					DevPositionEntity.POSITION_ID, QueryCondition.eq,
					positionId);
			List<Object> rlist = dbManager.queryByCondition(
					DevPositionEntity.class, qc1);
			if (rlist != null && rlist.size() > 0) {
				String strIds = "";
				for (int i = 0; i < rlist.size(); i++) {
					DevPositionEntity entity = (DevPositionEntity) rlist.get(i);
					Integer temp = entity.getDevId();
					if (temp != null) {
						if (i == rlist.size() - 1)
							strIds = strIds + temp;
						else {
							strIds = strIds + temp + ",";
						}
					}
				}
				if (strIds != null && !"".equals(strIds)) {
					qc.andCondition(new QueryCondition(DevEntity.ID,
							QueryCondition.in, strIds));
				}
			} else {
				return result;
			}
		}
		if (qc.getQueryNextCondition() != null) {
			TransactionManager tx = DbUtils.getTranManager();
			try {
				tx.beginTransaction();
				// 删除关联信息
				if (delDevPositionList != null
						&& delDevPositionList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								DevPositionEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								DevPositionEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delParentDevList != null && delParentDevList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getParentId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(DevEntity.ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(DevEntity.class,
								qc1);
					}
				}
				// 删除关联信息
				if (delAlarmCurrentList != null
						&& delAlarmCurrentList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								AlarmCurrentEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								AlarmCurrentEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delAlarmHistoryList != null
						&& delAlarmHistoryList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								AlarmHistoryEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								AlarmHistoryEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentBedList != null
						&& delCurrentBedList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentBedEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentBedEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentDoorList != null
						&& delCurrentDoorList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentDoorEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentDoorEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentGatewayList != null
						&& delCurrentGatewayList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentGatewayEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentGatewayEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentKeyalarmList != null
						&& delCurrentKeyalarmList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentKeyalarmEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentKeyalarmEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentLocationList != null
						&& delCurrentLocationList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentLocationEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentLocationEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentUrineList != null
						&& delCurrentUrineList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentUrineEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentUrineEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentWandaiList != null
						&& delCurrentWandaiList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentWandaiEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentWandaiEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryBedList != null
						&& delHistoryBedList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryBedEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryBedEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryDoorList != null
						&& delHistoryDoorList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryDoorEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryDoorEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryKeyalarmList != null
						&& delHistoryKeyalarmList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryKeyalarmEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryKeyalarmEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryLocationBodyList != null
						&& delHistoryLocationBodyList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryLocationBodyEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryLocationBodyEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryLocationManualList != null
						&& delHistoryLocationManualList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryLocationManualEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryLocationManualEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryLocationMoveList != null
						&& delHistoryLocationMoveList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryLocationMoveEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryLocationMoveEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryLocationPosList != null
						&& delHistoryLocationPosList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryLocationPosEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryLocationPosEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryUrineList != null
						&& delHistoryUrineList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryUrineEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryUrineEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentIrList != null && delCurrentIrList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentIrEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentIrEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryIrList != null && delHistoryIrList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryIrEntity.DEV_ID, QueryCondition.in,
								strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryIrEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delCurrentTizhengBedList != null
						&& delCurrentTizhengBedList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								CurrentTizhengBedEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								CurrentTizhengBedEntity.class, qc1);
					}

				}
				// 删除关联信息
				if (delHistoryTizhengBedList != null
						&& delHistoryTizhengBedList.booleanValue()) {
					List<Object> list = dbManager.queryByCondition(
							DevEntity.class, qc);
					String strIds = "";
					if (list != null && list.size() > 0) {
						for (Object obj : list) {
							DevEntity entity = (DevEntity) obj;
							strIds += entity.getId() + ",";
						}
						strIds = strIds.substring(0, strIds.length() - 1);
					}
					if (strIds != null && !"".equals(strIds)) {
						QueryCondition qc1 = new QueryCondition(
								HistoryTizhengBedEntity.DEV_ID,
								QueryCondition.in, strIds);
						dbManager.delByConditionsNoTransaction(
								HistoryTizhengBedEntity.class, qc1);
					}

				}
				result = dbManager.delByConditionsNoTransaction(
						DevEntity.class, qc);
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
