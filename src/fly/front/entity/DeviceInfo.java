package fly.front.entity;

/**
 * 前端显示设备信息
 * @author WongYong
 */
public class DeviceInfo {

	public DeviceInfo() {
		super();
	}

	public DeviceInfo(Integer devId, String devName, String devType, String devMac, String position, String alarmDev, String startTime, String endTime,
			String alarmDelay, String alarmCard, String doorLight, String led, String eboard, String eboardContent, String cardContent) {
		super();
		this.devId = devId;
		this.devName = devName;
		this.devType = devType;
		this.devMac = devMac;
		this.position = position;
		this.alarmDev = alarmDev;
		this.startTime = startTime;
		this.endTime = endTime;
		this.alarmDelay = alarmDelay;
		this.alarmCard = alarmCard;
		this.doorLight = doorLight;
		this.led = led;
		this.eboard = eboard;
		this.eboardContent = eboardContent;
		this.cardContent = cardContent;
	}

	private Integer devId;
	
	private String devName;
	
	private String devType;
	
	private String devMac;
	
	private Integer positionId;
	
	private String position;
	
	private Integer parentDevid;
	
	private String alarmDev;
	
	private String startTime;
	
	private String endTime;
	
	private String alarmDelay;
	
	private String alarmCard;
	
	private String doorLight;
	
	private String led;
	
	private String eboard;
	
	private String eboardContent;
	
	private String cardContent;
	
	public Integer getDevId() {
		return devId;
	}

	public void setDevId(Integer devId) {
		this.devId = devId;
	}

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public String getDevMac() {
		return devMac;
	}

	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getAlarmDev() {
		return alarmDev;
	}

	public void setAlarmDev(String alarmDev) {
		this.alarmDev = alarmDev;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getAlarmDelay() {
		return alarmDelay;
	}

	public void setAlarmDelay(String alarmDelay) {
		this.alarmDelay = alarmDelay;
	}

	public String getAlarmCard() {
		return alarmCard;
	}

	public void setAlarmCard(String alarmCard) {
		this.alarmCard = alarmCard;
	}

	public String getDoorLight() {
		return doorLight;
	}

	public void setDoorLight(String doorLight) {
		this.doorLight = doorLight;
	}

	public String getLed() {
		return led;
	}

	public void setLed(String led) {
		this.led = led;
	}

	public String getEboard() {
		return eboard;
	}

	public void setEboard(String eboard) {
		this.eboard = eboard;
	}

	public String getEboardContent() {
		return eboardContent;
	}

	public void setEboardContent(String eboardContent) {
		this.eboardContent = eboardContent;
	}

	public String getCardContent() {
		return cardContent;
	}

	public void setCardContent(String cardContent) {
		this.cardContent = cardContent;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setParentDevid(Integer parentDevid) {
		this.parentDevid = parentDevid;
	}

	public Integer getParentDevid() {
		return parentDevid;
	}
}
