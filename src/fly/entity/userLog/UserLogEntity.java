package fly.entity.userLog;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import fly.entity.user.UserEntity;
import com.framework.system.db.dao.annotation.ColumnDescription;
import com.framework.system.db.dao.annotation.RelationlDescription;
import com.framework.system.db.dao.annotation.TableDescription;

/**   
 * @Title: Entity
 * @Description: 系统日志
 * @author feng.gu
 * @date 2015-08-07 14:48:16
 * @version V1.0   
 *
 */
@TableDescription(name = "mordo_user_log")
public class UserLogEntity implements java.io.Serializable {
    public final static String ID = "ID";
    public final static String USER_ID = "USER_ID";
    public final static String LOG = "LOG";
    public final static String LEVEL = "LEVEL";
    public final static String CREATEDATE = "CREATEDATE";
    
    /**
	 * 编号
	 */
	@ColumnDescription(name = "ID", isPrimaryKey = true, isAutoIncrement = true)
	private Integer id;
    /**
	 * 用户编号
	 */
	@ColumnDescription(name = "USER_ID")
	private Integer userId;
    /**
	 * 日志描述
	 */
	@ColumnDescription(name = "LOG")
	private String log;
    /**
	 * 等级 0-通知 1-警告 2-错误
	 */
	@ColumnDescription(name = "LEVEL")
	private Integer level;
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
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
    public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
    public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
        
    /**
     * 关系描述
	 */
    @RelationlDescription(relation = "ManyToOne",
		joinEntity="UserEntity"	,joinColumn="ID"	)
			
			
		private UserEntity user;
	        
	public UserEntity getUser() {
		return user;
    }
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
		
}
