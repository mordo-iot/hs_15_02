package fly.entity.currentLocation;

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
 * @Description: 定位器当前状态
 * @author feng.gu
 * @date 2015-08-19 15:45:47
 * @version V1.0
 * 
 */
@TableDescription(name = "mordo_state_current_location")
public class CurrentLocationEntity implements java.io.Serializable {
	public final static String ID = "ID";
	public final static String DEV_ID = "DEV_ID";
	public final static String CURRPOSITION_ID = "CURRPOSITION_ID";
	public final static String CURRLOG = "CURRLOG";
	public final static String CURRLAT = "CURRLAT";
	public final static String LEAVED = "LEAVED";
	public final static String LEAVEDUPDATETIME = "LEAVEDUPDATETIME";
	public final static String BODYSTATE = "BODYSTATE";
	public final static String MANUALALARM = "MANUALALARM";
	public final static String BODYUPDATETIME = "BODYUPDATETIME";
	public final static String MOVING = "MOVING";
	public final static String MOVINGUPDATETIME = "MOVINGUPDATETIME";
	public final static String NORMAL = "NORMAL";
	public final static String ONLINE = "ONLINE";
	public final static String POWER = "POWER";
	public final static String DEVUPDATETIME = "DEVUPDATETIME";

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
	@ColumnDescription(name = "CURRPOSITION_ID")
	private Integer currpositionId;
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
	/**
	 * Y-正常，N-摔倒
	 */
	@ColumnDescription(name = "BODYSTATE")
	private String bodystate;
	/**
	 * Y-正常，N-手动报警
	 */
	@ColumnDescription(name = "MANUALALARM")
	private String manualalarm;
	/**
	 * 身体状态更新时间
	 */
	@ColumnDescription(name = "BODYUPDATETIME")
	private String bodyupdatetime;
	/**
	 * Y-正常，N-人卡分离
	 */
	@ColumnDescription(name = "MOVING")
	private String moving;
	/**
	 * 人卡分离记录更新时间
	 */
	@ColumnDescription(name = "MOVINGUPDATETIME")
	private String movingupdatetime;
	/**
	 * Y-正常 N-故障
	 */
	@ColumnDescription(name = "NORMAL")
	private String normal;
	/**
	 * Y-在线 N-离线
	 */
	@ColumnDescription(name = "ONLINE")
	private String online;
	/**
	 * Y-正常 N-低电压
	 */
	@ColumnDescription(name = "POWER")
	private String power;
	/**
	 * 设备状态更新时间
	 */
	@ColumnDescription(name = "DEVUPDATETIME")
	private String devupdatetime;

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

	public Integer getCurrpositionId() {
		return currpositionId;
	}

	public void setCurrpositionId(Integer currpositionId) {
		this.currpositionId = currpositionId;
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

	public String getBodystate() {
		return bodystate;
	}

	public void setBodystate(String bodystate) {
		this.bodystate = bodystate;
	}

	public String getManualalarm() {
		return manualalarm;
	}

	public void setManualalarm(String manualalarm) {
		this.manualalarm = manualalarm;
	}

	public String getBodyupdatetime() {
		return bodyupdatetime;
	}

	public void setBodyupdatetime(String bodyupdatetime) {
		this.bodyupdatetime = bodyupdatetime;
	}

	public String getMoving() {
		return moving;
	}

	public void setMoving(String moving) {
		this.moving = moving;
	}

	public String getMovingupdatetime() {
		return movingupdatetime;
	}

	public void setMovingupdatetime(String movingupdatetime) {
		this.movingupdatetime = movingupdatetime;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getDevupdatetime() {
		return devupdatetime;
	}

	public void setDevupdatetime(String devupdatetime) {
		this.devupdatetime = devupdatetime;
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
