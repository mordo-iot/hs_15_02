package fly.front.entity;

/**
 * 系统登入操作结果
 * @author Administrator
 */
public class LoginResult {
	
	public LoginResult() {
		super();
	}

	public LoginResult(boolean success, String des, String userName, int permission) {
		super();
		this.success = success;
		this.des = des;
		this.userName = userName;
		this.permission = permission;
	}

	/** 操作结果 */
	private boolean success;
	
	/** 操作结果说明 */
	private String des;
	
	/** 用户名 */
	private String userName;
	
	/** 系统权限 */
	private int permission;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "LoginResult [success=" + success + ", des=" + des + ", permission=" + permission + "]";
	}
	
}
