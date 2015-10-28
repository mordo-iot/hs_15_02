package fly.entity.historyLocation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**
 * @Title: Entity
 * @Description: 园区一卡通历史信息合并表
 * @author feng.gu
 * @date 2015-10-28 15:29:39
 * @version V1.0
 * 
 */
@TableDescription(name = "mordo_history_location")
public class HistoryLocationEntity implements java.io.Serializable {
	public final static String ID = "ID";
	public final static String DEV_ID = "DEV_ID";
	public final static String BODYSTATE = "BODYSTATE";
	public final static String MANUALALARM = "MANUALALARM";
	public final static String MOVING = "MOVING";
	public final static String TIME = "TIME";

	/**
	 * id
	 */
	@ColumnDescription(name = "ID")
	private Object id;
	/**
	 * devId
	 */
	@ColumnDescription(name = "DEV_ID")
	private Integer devId;
	/**
	 * bodystate
	 */
	@ColumnDescription(name = "BODYSTATE")
	private String bodystate;
	/**
	 * manualalarm
	 */
	@ColumnDescription(name = "MANUALALARM")
	private String manualalarm;
	/**
	 * moving
	 */
	@ColumnDescription(name = "MOVING")
	private String moving;
	/**
	 * time
	 */
	@ColumnDescription(name = "TIME")
	private String time;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
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

	public String getMoving() {
		return moving;
	}

	public void setMoving(String moving) {
		this.moving = moving;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

}
