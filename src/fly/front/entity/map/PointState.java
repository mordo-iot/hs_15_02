package fly.front.entity.map;

public class PointState {
	
	public PointState() {
		super();
	}

	public PointState(int devId, int locationId) {
		super();
		this.devId = devId;
		this.locationId = locationId;
	}

	
	private int devId;
	
	private int locationId;

	
	public int getDevId() {
		return devId;
	}

	public void setDevId(int devId) {
		this.devId = devId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

}
