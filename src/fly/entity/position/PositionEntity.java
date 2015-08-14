package fly.entity.position;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.dev.DevEntity;
import fly.entity.position.PositionEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**
 * @Title: Entity
 * @Description: 位置信息
 * @author feng.gu
 * @date 2015-08-14 10:49:20
 * @version V1.0
 * 
 */
@TableDescription(name = "mordo_position_info")
public class PositionEntity implements java.io.Serializable {
	public final static String ID = "ID";
	public final static String NAME = "NAME";
	public final static String PHOTO = "PHOTO";
	public final static String PARENT_ID = "PARENT_ID";
	public final static String CREATEDATE = "CREATEDATE";
	public final static String UPDATEDATE = "UPDATEDATE";

	/**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
	/**
	 * 位置名称
	 */
	@ColumnDescription(name = "NAME")
	private String name;
	/**
	 * 照片相对路径
	 */
	@ColumnDescription(name = "PHOTO")
	private String photo;
	/**
	 * 父节点
	 */
	@ColumnDescription(name = "PARENT_ID")
	private Integer parentId;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	@RelationlDescription(relation = "ManyToMany", joinEntity = "DevPositionEntity", joinColumn = "POSITION_ID", inverseJoinColumn = "DEV_ID", inverseJoinEntity = "DevEntity")
	private List<DevEntity> devList;

	public List<DevEntity> getDevList() {
		return devList;
	}

	public void setDevList(List<DevEntity> devList) {
		this.devList = devList;
	}

	/**
	 * 关系描述
	 */
	@RelationlDescription(relation = "OneToOne", joinEntity = "PositionEntity", joinColumn = "PARENT_ID")
	private PositionEntity position;

	public PositionEntity getPosition() {
		return position;
	}

	public void setPosition(PositionEntity position) {
		this.position = position;
	}

}
