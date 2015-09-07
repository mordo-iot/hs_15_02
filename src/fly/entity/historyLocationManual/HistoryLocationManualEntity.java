package fly.entity.historyLocationManual;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 定位器手动报警历史状态
 * @author feng.gu
 * @date 2015-09-07 14:15:46
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_history_location_manual")
public class HistoryLocationManualEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String MANUALALARM = "MANUALALARM";
    public final static String BODYUPDATETIME = "BODYUPDATETIME";
    
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
	 * Y-正常，N-手动报警
	 */
	@ColumnDescription(name = "MANUALALARM")
	private String manualalarm;
    /**
	 * 身体状态更新时间
	 */
	@ColumnDescription(name = "BODYUPDATETIME")
	private String bodyupdatetime;
        
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
