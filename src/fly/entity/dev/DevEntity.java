package fly.entity.dev;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 设备信息
 * @author feng.gu
 * @date 2015-08-10 09:24:12
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_dev_info")
public class DevEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String NAME = "NAME";
    public final static String TYPE = "TYPE";
    public final static String CODE = "CODE";
    public final static String ALARMCONTENT = "ALARMCONTENT";
    public final static String ALARMDEVID = "ALARMDEVID";
    public final static String LIGHTNO = "LIGHTNO";
    public final static String LIGHTDEVID = "LIGHTDEVID";
    public final static String ALARMPHONE = "ALARMPHONE";
    public final static String EMITID = "EMITID";
    public final static String PARENT_ID = "PARENT_ID";
    public final static String ATTRIBUTE = "ATTRIBUTE";
    public final static String CREATEDATE = "CREATEDATE";
    public final static String UPDATEDATE = "UPDATEDATE";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
    /**
	 * 设备名称
	 */
	@ColumnDescription(name = "NAME")
	private String name;
    /**
	 * 设备种类
	 */
	@ColumnDescription(name = "TYPE")
	private String type;
    /**
	 * 设备条码
	 */
	@ColumnDescription(name = "CODE")
	private String code;
    /**
	 * 报警内容
	 */
	@ColumnDescription(name = "ALARMCONTENT")
	private String alarmcontent;
    /**
	 * 报警设备编号，以,分割
	 */
	@ColumnDescription(name = "ALARMDEVID")
	private String alarmdevid;
    /**
	 * 电子看板报警编号
	 */
	@ColumnDescription(name = "LIGHTNO")
	private Integer lightno;
    /**
	 * 报警电子看板编号
	 */
	@ColumnDescription(name = "LIGHTDEVID")
	private String lightdevid;
    /**
	 * 报警短信号码，以,分割
	 */
	@ColumnDescription(name = "ALARMPHONE")
	private String alarmphone;
    /**
	 * 信号发射器编号
	 */
	@ColumnDescription(name = "EMITID")
	private Integer emitid;
    /**
	 * 父节点
	 */
	@ColumnDescription(name = "PARENT_ID")
	private Integer parentId;
    /**
	 * 设备属性，json格式
	 */
	@ColumnDescription(name = "ATTRIBUTE")
	private String attribute;
    /**
	 * 创建日期
	 */
	@ColumnDescription(name = "CREATEDATE")
	private String createdate;
    /**
	 * 更新日期
	 */
	@ColumnDescription(name = "UPDATEDATE")
	private String updatedate;
        
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    public String getAlarmcontent() {
		return alarmcontent;
	}

	public void setAlarmcontent(String alarmcontent) {
		this.alarmcontent = alarmcontent;
	}
    public String getAlarmdevid() {
		return alarmdevid;
	}

	public void setAlarmdevid(String alarmdevid) {
		this.alarmdevid = alarmdevid;
	}
    public Integer getLightno() {
		return lightno;
	}

	public void setLightno(Integer lightno) {
		this.lightno = lightno;
	}
    public String getLightdevid() {
		return lightdevid;
	}

	public void setLightdevid(String lightdevid) {
		this.lightdevid = lightdevid;
	}
    public String getAlarmphone() {
		return alarmphone;
	}

	public void setAlarmphone(String alarmphone) {
		this.alarmphone = alarmphone;
	}
    public Integer getEmitid() {
		return emitid;
	}

	public void setEmitid(Integer emitid) {
		this.emitid = emitid;
	}
    public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
    public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
    public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
        
    /**
     * 关系描述
	 */
    @RelationlDescription(relation = "OneToOne",
		joinEntity="DevEntity"	,joinColumn="ID"	)
			
			
		private DevEntity dev;
	        
	public DevEntity getDev() {
		return dev;
    }
	
	public void setDev(DevEntity dev) {
		this.dev = dev;
	}
		
}
