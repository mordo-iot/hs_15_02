package fly.entity.alarmCurrent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 当前报警信息
 * @author feng.gu
 * @date 2015-09-07 14:15:27
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_alarm_current")
public class AlarmCurrentEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String CODE = "CODE";
    public final static String DEV_ID = "DEV_ID";
    public final static String CONTENT = "CONTENT";
    public final static String CREATEDATE = "CREATEDATE";
    public final static String HANDLESTATE = "HANDLESTATE";
    public final static String HANDLEDESC = "HANDLEDESC";
    public final static String HANDLEDATE = "HANDLEDATE";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
    /**
	 * 报警代码
	 */
	@ColumnDescription(name = "CODE")
	private String code;
    /**
	 * 设备编号
	 */
	@ColumnDescription(name = "DEV_ID")
	private Integer devId;
    /**
	 * 报警内容
	 */
	@ColumnDescription(name = "CONTENT")
	private String content;
    /**
	 * 创建日期
	 */
	@ColumnDescription(name = "CREATEDATE")
	private String createdate;
    /**
	 * 处理状态 0-未处理 1-已处理 2-手动取消
	 */
	@ColumnDescription(name = "HANDLESTATE")
	private Integer handlestate=0;
    /**
	 * 处理描述
	 */
	@ColumnDescription(name = "HANDLEDESC")
	private String handledesc;
    /**
	 * 处理日期
	 */
	@ColumnDescription(name = "HANDLEDATE")
	private String handledate;
        
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
    public Integer getHandlestate() {
		return handlestate;
	}

	public void setHandlestate(Integer handlestate) {
		this.handlestate = handlestate;
	}
    public String getHandledesc() {
		return handledesc;
	}

	public void setHandledesc(String handledesc) {
		this.handledesc = handledesc;
	}
    public String getHandledate() {
		return handledate;
	}

	public void setHandledate(String handledate) {
		this.handledate = handledate;
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
