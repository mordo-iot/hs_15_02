package fly.front.entity;

public class CurrentAlarmInfo {
	
	public CurrentAlarmInfo() {
		super();
	}

	public CurrentAlarmInfo(Integer alarmId, String alarmTime, String alarmType, String alarmContent, String alarmTarget) {
		super();
		this.alarmId = alarmId;
		this.alarmTime = alarmTime;
		this.alarmType = alarmType;
		this.alarmContent = alarmContent;
		this.alarmTarget = alarmTarget;
	}

	/** 报警信息ID */
	private Integer alarmId;
	
	/** 报警时间 */
	private String alarmTime;
	
	/** 报警类型 */
	private String alarmType;
	
	/** 报警内容 */
	private String alarmContent;
	
	/** 报警对象 */
	private String alarmTarget;

	public Integer getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmTarget() {
		return alarmTarget;
	}

	public void setAlarmTarget(String alarmTarget) {
		this.alarmTarget = alarmTarget;
	}
	
}
