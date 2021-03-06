package fly.entity.historyBed;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 床垫历史信息
 * @author feng.gu
 * @date 2015-09-07 14:15:41
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_history_bed")
public class HistoryBedEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String HAVINGBODY = "HAVINGBODY";
    public final static String LEVEL = "LEVEL";
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
	 * 床垫状态 Y-有人 N-无人
	 */
	@ColumnDescription(name = "HAVINGBODY")
	private String havingbody;
    /**
	 * 报警等级
	 */
	@ColumnDescription(name = "LEVEL")
	private Integer level;
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
    public String getHavingbody() {
		return havingbody;
	}

	public void setHavingbody(String havingbody) {
		this.havingbody = havingbody;
	}
    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
