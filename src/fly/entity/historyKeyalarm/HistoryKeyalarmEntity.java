package fly.entity.historyKeyalarm;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 一键报警历史信息
 * @author feng.gu
 * @date 2015-08-10 10:15:43
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_history_keyalarm")
public class HistoryKeyalarmEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String ALARM = "ALARM";
    public final static String ALARMUPDATETIME = "ALARMUPDATETIME";
    
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
