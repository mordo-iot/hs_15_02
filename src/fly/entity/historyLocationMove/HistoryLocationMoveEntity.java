package fly.entity.historyLocationMove;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 定位器人卡分离历史状态
 * @author feng.gu
 * @date 2015-09-07 14:15:47
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_state_history_location_move")
public class HistoryLocationMoveEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String MOVING = "MOVING";
    public final static String MOVINGUPDATETIME = "MOVINGUPDATETIME";
    
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
	 * Y-正常，N-人卡分离
	 */
	@ColumnDescription(name = "MOVING")
	private String moving;
    /**
	 * 人卡分离记录更新时间
	 */
	@ColumnDescription(name = "MOVINGUPDATETIME")
	private String movingupdatetime;
        
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
