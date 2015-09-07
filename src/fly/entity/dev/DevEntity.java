package fly.entity.dev;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.position.PositionEntity;
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
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 设备信息
 * @author feng.gu
 * @date 2015-09-07 14:15:51
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_dev_info")
public class DevEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String NAME = "NAME";
    public final static String TYPE = "TYPE";
    public final static String CODE = "CODE";
    public final static String ALARMCONTENT = "ALARMCONTENT";
    public final static String ALARMDEVID = "ALARMDEVID";
    public final static String LIGHTNO = "LIGHTNO";
    public final static String LIGHTDEVID = "LIGHTDEVID";
    public final static String ALARMPHONE = "ALARMPHONE";
    public final static String EMITID = "EMITID";
    public final static String PARENT_ID = "PARENT_ID";
    public final static String ATTRIBUTE = "ATTRIBUTE";
    public final static String CREATEDATE = "CREATEDATE";
    public final static String UPDATEDATE = "UPDATEDATE";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
    /**
	 * 设备名称
	 */
	@ColumnDescription(name = "NAME")
	private String name;
    /**
	 * 设备种类
	 */
	@ColumnDescription(name = "TYPE")
	private String type;
    /**
	 * 设备条码
	 */
	@ColumnDescription(name = "CODE")
	private String code;
    /**
	 * 报警内容(十进制，4位数)
	 */
	@ColumnDescription(name = "ALARMCONTENT")
	private String alarmcontent;
    /**
	 * 报警设备编号，以,分割(16进制设备code)
	 */
	@ColumnDescription(name = "ALARMDEVID")
	private String alarmdevid;
    /**
	 * 电子看板报警编号
	 */
	@ColumnDescription(name = "LIGHTNO")
	private Integer lightno;
    /**
	 * 报警电子看板编号
	 */
	@ColumnDescription(name = "LIGHTDEVID")
	private String lightdevid;
    /**
	 * 报警短信号码，以,分割
	 */
	@ColumnDescription(name = "ALARMPHONE")
	private String alarmphone;
    /**
	 * 信号发射器id
	 */
	@ColumnDescription(name = "EMITID")
	private Integer emitid;
    /**
	 * 父节点
	 */
	@ColumnDescription(name = "PARENT_ID")
	private Integer parentId;
    /**
	 * 设备属性，json格式
	 */
	@ColumnDescription(name = "ATTRIBUTE")
	private String attribute;
    /**
	 * 创建日期
	 */
	@ColumnDescription(name = "CREATEDATE")
	private String createdate;
    /**
	 * 更新日期
	 */
	@ColumnDescription(name = "UPDATEDATE")
	private String updatedate;
        
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    public String getAlarmcontent() {
		return alarmcontent;
	}

	public void setAlarmcontent(String alarmcontent) {
		this.alarmcontent = alarmcontent;
	}
    public String getAlarmdevid() {
		return alarmdevid;
	}

	public void setAlarmdevid(String alarmdevid) {
		this.alarmdevid = alarmdevid;
	}
    public Integer getLightno() {
		return lightno;
	}

	public void setLightno(Integer lightno) {
		this.lightno = lightno;
	}
    public String getLightdevid() {
		return lightdevid;
	}

	public void setLightdevid(String lightdevid) {
		this.lightdevid = lightdevid;
	}
    public String getAlarmphone() {
		return alarmphone;
	}

	public void setAlarmphone(String alarmphone) {
		this.alarmphone = alarmphone;
	}
    public Integer getEmitid() {
		return emitid;
	}

	public void setEmitid(Integer emitid) {
		this.emitid = emitid;
	}
    public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
    public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
    public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
        
    /**
     * 关系描述
	 */
	    @RelationlDescription(relation = "ManyToMany",
			joinEntity="DevPositionEntity"
					,joinColumn="DEV_ID"
			,inverseJoinColumn="POSITION_ID",inverseJoinEntity="PositionEntity")
	    private List<PositionEntity> positionList;
	        
	public List<PositionEntity> getPositionList() {
		return positionList;
    }
	
	public void setPositionList(List<PositionEntity> positionList) {
		this.positionList = positionList;
	}
    		
			
		/**
     * 关系描述
	 */
		    @RelationlDescription(relation = "ManyToOne",joinEntity="DevEntity"	,joinColumn="PARENT_ID"	)
	private DevEntity parentDev;

	public DevEntity getParentDev() {
		return parentDev;
	}

	public void setParentDev(DevEntity parentDev) {
		this.parentDev = parentDev;
	}
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="AlarmCurrentEntity"			,joinColumn="DEV_ID"
			)
			
		private List<AlarmCurrentEntity> alarmCurrentList;
	        
	public List<AlarmCurrentEntity> getAlarmCurrentList() {
		return alarmCurrentList;
	}
	
	public void setAlarmCurrentList(List<AlarmCurrentEntity> alarmCurrentList) {
		this.alarmCurrentList = alarmCurrentList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="AlarmHistoryEntity"			,joinColumn="DEV_ID"
			)
			
		private List<AlarmHistoryEntity> alarmHistoryList;
	        
	public List<AlarmHistoryEntity> getAlarmHistoryList() {
		return alarmHistoryList;
	}
	
	public void setAlarmHistoryList(List<AlarmHistoryEntity> alarmHistoryList) {
		this.alarmHistoryList = alarmHistoryList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentBedEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentBedEntity> currentBedList;
	        
	public List<CurrentBedEntity> getCurrentBedList() {
		return currentBedList;
	}
	
	public void setCurrentBedList(List<CurrentBedEntity> currentBedList) {
		this.currentBedList = currentBedList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentDoorEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentDoorEntity> currentDoorList;
	        
	public List<CurrentDoorEntity> getCurrentDoorList() {
		return currentDoorList;
	}
	
	public void setCurrentDoorList(List<CurrentDoorEntity> currentDoorList) {
		this.currentDoorList = currentDoorList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentGatewayEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentGatewayEntity> currentGatewayList;
	        
	public List<CurrentGatewayEntity> getCurrentGatewayList() {
		return currentGatewayList;
	}
	
	public void setCurrentGatewayList(List<CurrentGatewayEntity> currentGatewayList) {
		this.currentGatewayList = currentGatewayList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentKeyalarmEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentKeyalarmEntity> currentKeyalarmList;
	        
	public List<CurrentKeyalarmEntity> getCurrentKeyalarmList() {
		return currentKeyalarmList;
	}
	
	public void setCurrentKeyalarmList(List<CurrentKeyalarmEntity> currentKeyalarmList) {
		this.currentKeyalarmList = currentKeyalarmList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentLocationEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentLocationEntity> currentLocationList;
	        
	public List<CurrentLocationEntity> getCurrentLocationList() {
		return currentLocationList;
	}
	
	public void setCurrentLocationList(List<CurrentLocationEntity> currentLocationList) {
		this.currentLocationList = currentLocationList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentUrineEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentUrineEntity> currentUrineList;
	        
	public List<CurrentUrineEntity> getCurrentUrineList() {
		return currentUrineList;
	}
	
	public void setCurrentUrineList(List<CurrentUrineEntity> currentUrineList) {
		this.currentUrineList = currentUrineList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentWandaiEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentWandaiEntity> currentWandaiList;
	        
	public List<CurrentWandaiEntity> getCurrentWandaiList() {
		return currentWandaiList;
	}
	
	public void setCurrentWandaiList(List<CurrentWandaiEntity> currentWandaiList) {
		this.currentWandaiList = currentWandaiList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryBedEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryBedEntity> historyBedList;
	        
	public List<HistoryBedEntity> getHistoryBedList() {
		return historyBedList;
	}
	
	public void setHistoryBedList(List<HistoryBedEntity> historyBedList) {
		this.historyBedList = historyBedList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryDoorEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryDoorEntity> historyDoorList;
	        
	public List<HistoryDoorEntity> getHistoryDoorList() {
		return historyDoorList;
	}
	
	public void setHistoryDoorList(List<HistoryDoorEntity> historyDoorList) {
		this.historyDoorList = historyDoorList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryKeyalarmEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryKeyalarmEntity> historyKeyalarmList;
	        
	public List<HistoryKeyalarmEntity> getHistoryKeyalarmList() {
		return historyKeyalarmList;
	}
	
	public void setHistoryKeyalarmList(List<HistoryKeyalarmEntity> historyKeyalarmList) {
		this.historyKeyalarmList = historyKeyalarmList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryLocationBodyEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryLocationBodyEntity> historyLocationBodyList;
	        
	public List<HistoryLocationBodyEntity> getHistoryLocationBodyList() {
		return historyLocationBodyList;
	}
	
	public void setHistoryLocationBodyList(List<HistoryLocationBodyEntity> historyLocationBodyList) {
		this.historyLocationBodyList = historyLocationBodyList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryLocationManualEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryLocationManualEntity> historyLocationManualList;
	        
	public List<HistoryLocationManualEntity> getHistoryLocationManualList() {
		return historyLocationManualList;
	}
	
	public void setHistoryLocationManualList(List<HistoryLocationManualEntity> historyLocationManualList) {
		this.historyLocationManualList = historyLocationManualList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryLocationMoveEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryLocationMoveEntity> historyLocationMoveList;
	        
	public List<HistoryLocationMoveEntity> getHistoryLocationMoveList() {
		return historyLocationMoveList;
	}
	
	public void setHistoryLocationMoveList(List<HistoryLocationMoveEntity> historyLocationMoveList) {
		this.historyLocationMoveList = historyLocationMoveList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryLocationPosEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryLocationPosEntity> historyLocationPosList;
	        
	public List<HistoryLocationPosEntity> getHistoryLocationPosList() {
		return historyLocationPosList;
	}
	
	public void setHistoryLocationPosList(List<HistoryLocationPosEntity> historyLocationPosList) {
		this.historyLocationPosList = historyLocationPosList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryUrineEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryUrineEntity> historyUrineList;
	        
	public List<HistoryUrineEntity> getHistoryUrineList() {
		return historyUrineList;
	}
	
	public void setHistoryUrineList(List<HistoryUrineEntity> historyUrineList) {
		this.historyUrineList = historyUrineList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentIrEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentIrEntity> currentIrList;
	        
	public List<CurrentIrEntity> getCurrentIrList() {
		return currentIrList;
	}
	
	public void setCurrentIrList(List<CurrentIrEntity> currentIrList) {
		this.currentIrList = currentIrList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryIrEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryIrEntity> historyIrList;
	        
	public List<HistoryIrEntity> getHistoryIrList() {
		return historyIrList;
	}
	
	public void setHistoryIrList(List<HistoryIrEntity> historyIrList) {
		this.historyIrList = historyIrList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="CurrentTizhengBedEntity"			,joinColumn="DEV_ID"
			)
			
		private List<CurrentTizhengBedEntity> currentTizhengBedList;
	        
	public List<CurrentTizhengBedEntity> getCurrentTizhengBedList() {
		return currentTizhengBedList;
	}
	
	public void setCurrentTizhengBedList(List<CurrentTizhengBedEntity> currentTizhengBedList) {
		this.currentTizhengBedList = currentTizhengBedList;
	}
			
		/**
     * 关系描述
	 */
	    @RelationlDescription(relation = "OneToMany",
		joinEntity="HistoryTizhengBedEntity"			,joinColumn="DEV_ID"
			)
			
		private List<HistoryTizhengBedEntity> historyTizhengBedList;
	        
	public List<HistoryTizhengBedEntity> getHistoryTizhengBedList() {
		return historyTizhengBedList;
	}
	
	public void setHistoryTizhengBedList(List<HistoryTizhengBedEntity> historyTizhengBedList) {
		this.historyTizhengBedList = historyTizhengBedList;
	}
			
			
}
