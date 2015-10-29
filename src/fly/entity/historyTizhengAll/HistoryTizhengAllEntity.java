package fly.entity.historyTizhengAll;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**
 * @Title: Entity
 * @Description: 体征床垫历史信息合并表
 * @author feng.gu
 * @date 2015-10-29 12:49:30
 * @version V1.0
 * 
 */
@TableDescription(name = "mordo_tizheng_bed")
public class HistoryTizhengAllEntity implements java.io.Serializable {
	public final static String ID = "ID";
	public final static String DEV_ID = "DEV_ID";
	public final static String HAVINGBODY = "HAVINGBODY";
	public final static String BREATH = "BREATH";
	public final static String HEART = "HEART";
	public final static String TIME = "TIME";

	/**
	 * id
	 */
	@ColumnDescription(name = "ID")
	private Object id;
	/**
	 * devId
	 */
	@ColumnDescription(name = "DEV_ID")
	private Integer devId;
	/**
	 * havingbody
	 */
	@ColumnDescription(name = "HAVINGBODY")
	private String havingbody;
	/**
	 * breath
	 */
	@ColumnDescription(name = "BREATH")
	private Integer breath;
	/**
	 * heart
	 */
	@ColumnDescription(name = "HEART")
	private Integer heart;
	/**
	 * time
	 */
	@ColumnDescription(name = "TIME")
	private String time;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
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

	public Integer getBreath() {
		return breath;
	}

	public void setBreath(Integer breath) {
		this.breath = breath;
	}

	public Integer getHeart() {
		return heart;
	}

	public void setHeart(Integer heart) {
		this.heart = heart;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 关系描述
	 */
	@RelationlDescription(relation = "ManyToOne", joinEntity = "DevEntity", joinColumn = "ID")
	private DevEntity dev;

	public DevEntity getDev() {
		return dev;
	}

	public void setDev(DevEntity dev) {
		this.dev = dev;
	}

}
