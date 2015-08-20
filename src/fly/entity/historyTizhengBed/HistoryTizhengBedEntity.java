package fly.entity.historyTizhengBed;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 体征床垫历史信息表
 * @author feng.gu
 * @date 2015-08-20 13:57:06
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_history_tizheng_bed")
public class HistoryTizhengBedEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String HAVINGBODY = "HAVINGBODY";
    public final static String ALARMUPDATETIME = "ALARMUPDATETIME";
    public final static String HEART = "HEART";
    public final static String BREATH = "BREATH";
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
	 * 床垫状态 Y-有人 N-无人
	 */
	@ColumnDescription(name = "HAVINGBODY")
	private String havingbody;
    /**
	 * 报警更新时间
	 */
	@ColumnDescription(name = "ALARMUPDATETIME")
	private String alarmupdatetime;
    /**
	 * 心跳数
	 */
	@ColumnDescription(name = "HEART")
	private Integer heart;
    /**
	 * 呼吸数
	 */
	@ColumnDescription(name = "BREATH")
	private Integer breath;
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
    public String getHavingbody() {
		return havingbody;
	}

	public void setHavingbody(String havingbody) {
		this.havingbody = havingbody;
	}
    public String getAlarmupdatetime() {
		return alarmupdatetime;
	}

	public void setAlarmupdatetime(String alarmupdatetime) {
		this.alarmupdatetime = alarmupdatetime;
	}
    public Integer getHeart() {
		return heart;
	}

	public void setHeart(Integer heart) {
		this.heart = heart;
	}
    public Integer getBreath() {
		return breath;
	}

	public void setBreath(Integer breath) {
		this.breath = breath;
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
