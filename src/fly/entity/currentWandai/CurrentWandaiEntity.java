package fly.entity.currentWandai;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 腕带当前信息
 * @author feng.gu
 * @date 2015-08-10 10:11:14
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_current_wandai")
public class CurrentWandaiEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String ALARM = "ALARM";
    public final static String ALARMUPDATETIME = "ALARMUPDATETIME";
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
	 * 报警状态 Y-报警 N-无报警
	 */
	@ColumnDescription(name = "ALARM")
	private String alarm;
    /**
	 * 报警更新时间
	 */
	@ColumnDescription(name = "ALARMUPDATETIME")
	private String alarmupdatetime;
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
    public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
    public String getAlarmupdatetime() {
		return alarmupdatetime;
	}

	public void setAlarmupdatetime(String alarmupdatetime) {
		this.alarmupdatetime = alarmupdatetime;
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
    @RelationlDescription(relation = "ManyToOne",
		joinEntity="DevEntity"	,joinColumn="ID"	)
			
			
		private DevEntity dev;
	        
	public DevEntity getDev() {
		return dev;
    }
	
	public void setDev(DevEntity dev) {
		this.dev = dev;
	}
		
}
