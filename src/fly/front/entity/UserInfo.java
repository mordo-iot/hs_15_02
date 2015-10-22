package fly.front.entity;

public class UserInfo {

	public UserInfo() {
		super();
	}

	public UserInfo(Integer userId, String userName, String regTime, String lastLogin, String permission) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.regTime = regTime;
		this.lastLogin = lastLogin;
		this.permission = permission;
	}

	/** 用户ID */
	private Integer userId;
	
	/** 用户名 */
	private String userName;
	
	/** 注册时间 */
	private String regTime;
	
	/** 最后登录时间 */
	private String lastLogin;
	
	/** 权限 */
	private String permission;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
}
