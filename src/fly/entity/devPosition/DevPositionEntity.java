package fly.entity.devPosition;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 设备安装位置映射
 * @author feng.gu
 * @date 2015-08-10 12:41:33
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_map_dev_position")
public class DevPositionEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String DEV_ID = "DEV_ID";
    public final static String POSITION_ID = "POSITION_ID";
    public final static String CREATEDATE = "CREATEDATE";
    
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
	 * 位置编号
	 */
	@ColumnDescription(name = "POSITION_ID")
	private Integer positionId;
    /**
	 * 创建日期
	 */
	@ColumnDescription(name = "CREATEDATE")
	private String createdate;
        
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
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
        
    	
}
