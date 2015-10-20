package fly.entity.devLeftJionMap;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 设备左连接位置mapping表
 * @author feng.gu
 * @date 2015-10-20 16:54:59
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_dev_left_jion_map")
public class DevLeftJionMapEntity implements java.io.Serializable {
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
    public final static String ID1 = "ID1";
    public final static String DEV_ID = "DEV_ID";
    public final static String POSITION_ID = "POSITION_ID";
    public final static String CREATEDATE1 = "CREATEDATE1";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID")
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
	 * 报警内容(十进制，4位数)
	 */
	@ColumnDescription(name = "ALARMCONTENT")
	private String alarmcontent;
    /**
	 * 报警设备编号，以,分割(16进制设备code)
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
	 * 信号发射器id
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
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID1")
	private Integer id1;
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
	@ColumnDescription(name = "CREATEDATE1")
	private String createdate1;
        
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
    public Integer getId1() {
		return id1;
	}

	public void setId1(Integer id1) {
		this.id1 = id1;
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
    public String getCreatedate1() {
		return createdate1;
	}

	public void setCreatedate1(String createdate1) {
		this.createdate1 = createdate1;
	}
        
    	
}
