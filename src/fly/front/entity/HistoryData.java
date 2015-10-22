package fly.front.entity;

public class HistoryData {
	
	public HistoryData() {
		super();
	}

	public HistoryData(String value, String updateDate) {
		super();
		this.value = value;
		this.updateDate = updateDate;
	}

	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 更新时间
	 */
	private String updateDate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

}
