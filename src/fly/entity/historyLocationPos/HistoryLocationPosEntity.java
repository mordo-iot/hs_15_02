package fly.entity.historyLocationPos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import fly.entity.position.PositionEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**
 * @Title: Entity
 * @Description: 定位器位置历史状态
 * @author feng.gu
 * @date 2015-09-16 15:01:08
 * @version V1.0
 * 
 */
@TableDescription(name = "mordo_state_history_location_pos")
public class HistoryLocationPosEntity implements java.io.Serializable {
	public final static String ID = "ID";
	public final static String DEV_ID = "DEV_ID";
	public final static String POSITION_ID = "POSITION_ID";
	public final static String CURRLOG = "CURRLOG";
	public final static String CURRLAT = "CURRLAT";
	public final static String LEAVED = "LEAVED";
	public final static String LEAVEDUPDATETIME = "LEAVEDUPDATETIME";

	/**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
	/**
	 * 设备编号
	 */
	@ColumnDescription(name = "DEV_ID")
	private Integer devId;
	/**
	 * 当前所在位置编号
	 */
	@ColumnDescription(name = "POSITION_ID")
	private Integer positionId;
	/**
	 * 当前所在经度
	 */
	@ColumnDescription(name = "CURRLOG")
	private BigDecimal currlog;
	/**
	 * 当前所在纬度
	 */
	@ColumnDescription(name = "CURRLAT")
	private BigDecimal currlat;
	/**
	 * Y-正常，N-越界
	 */
	@ColumnDescription(name = "LEAVED")
	private String leaved;
	/**
	 * 位置记录更新时间
	 */
	@ColumnDescription(name = "LEAVEDUPDATETIME")
	private String leavedupdatetime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public BigDecimal getCurrlog() {
		return currlog;
	}

	public void setCurrlog(BigDecimal currlog) {
		this.currlog = currlog;
	}

	public BigDecimal getCurrlat() {
		return currlat;
	}

	public void setCurrlat(BigDecimal currlat) {
		this.currlat = currlat;
	}

	public String getLeaved() {
		return leaved;
	}

	public void setLeaved(String leaved) {
		this.leaved = leaved;
	}

	public String getLeavedupdatetime() {
		return leavedupdatetime;
	}

	public void setLeavedupdatetime(String leavedupdatetime) {
		this.leavedupdatetime = leavedupdatetime;
	}

	/**
	 * 关系描述
	 */
	@RelationlDescription(relation = "ManyToOne", joinEntity = "DevEntity", joinColumn = "ID")
	private DevEntity dev;

	public DevEntity getDev() {
		return dev;
	}

	public void setDev(DevEntity dev) {
		this.dev = dev;
	}

	/**
	 * 关系描述
	 */
	@RelationlDescription(relation = "ManyToOne", joinEntity = "PositionEntity", joinColumn = "ID")
	private PositionEntity position;

	public PositionEntity getPosition() {
		return position;
	}

	public void setPosition(PositionEntity position) {
		this.position = position;
	}

}
