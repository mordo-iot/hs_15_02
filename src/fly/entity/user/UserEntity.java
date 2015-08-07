package fly.entity.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.userLog.UserLogEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 系统账号
 * @author feng.gu
 * @date 2015-08-07 14:48:35
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_sys_user")
public class UserEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String NAME = "NAME";
    public final static String PASSWORD = "PASSWORD";
    public final static String PERMISSIONS = "PERMISSIONS";
    public final static String CREATEDATE = "CREATEDATE";
    public final static String LSTLOGINDATE = "LSTLOGINDATE";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
    /**
	 * 用户名
	 */
	@ColumnDescription(name = "NAME")
	private String name;
    /**
	 * 密码md5
	 */
	@ColumnDescription(name = "PASSWORD")
	private String password;
    /**
	 * 权限：1-管理员，2-普通用户
	 */
	@ColumnDescription(name = "PERMISSIONS")
	private Integer permissions;
    /**
	 * 创建日期
	 */
	@ColumnDescription(name = "CREATEDATE")
	private String createdate;
    /**
	 * 上次登陆时间
	 */
	@ColumnDescription(name = "LSTLOGINDATE")
	private String lstlogindate;
        
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
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    public Integer getPermissions() {
		return permissions;
	}

	public void setPermissions(Integer permissions) {
		this.permissions = permissions;
	}
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
    public String getLstlogindate() {
		return lstlogindate;
	}

	public void setLstlogindate(String lstlogindate) {
		this.lstlogindate = lstlogindate;
	}
        
    /**
     * 关系描述
	 */
    @RelationlDescription(relation = "OneToMany",
		joinEntity="UserLogEntity"			,joinColumn="USERLOG_ID"
			)
			
		private List<UserLogEntity> userLogList;
	        
	public List<UserLogEntity> getUserLogList() {
		return userLogList;
	}
	
	public void setUserLogList(List<UserLogEntity> userLogList) {
		this.userLogList = userLogList;
	}
			
		
}
