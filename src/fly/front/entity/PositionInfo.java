package fly.front.entity;

public class PositionInfo {

	public PositionInfo() {
		super();
	}

	public PositionInfo(Integer positionId, String positionName) {
		super();
		this.positionId = positionId;
		this.positionName = positionName;
	}
	
	public PositionInfo(Integer positionId, String positionName, String photo) {
		super();
		this.positionId = positionId;
		this.positionName = positionName;
		this.setPhoto(photo);
	}

	private Integer positionId;
	
	private String positionName;
	
	private String photo;

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getPhoto() {
		return photo;
	}
	
}
