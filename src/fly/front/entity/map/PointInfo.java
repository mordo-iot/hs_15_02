package fly.front.entity.map;

import java.util.ArrayList;

import net.sf.json.JSONObject;

import fly.entity.dev.DevEntity;


public class PointInfo {

	public PointInfo() {
		super();
	}

	public PointInfo(LocationOnMap locationInfo, ArrayList<DevEntity> devInfo) {
		super();
		this.locationInfo = locationInfo;
		this.devInfo = devInfo;
	}

	private LocationOnMap locationInfo;

	private ArrayList<DevEntity> devInfo = new ArrayList<DevEntity>();

	public LocationOnMap getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(LocationOnMap locationInfo) {
		this.locationInfo = locationInfo;
	}

	public ArrayList<DevEntity> getDevInfo() {
		return devInfo;
	}

	public void setDevInfo(ArrayList<DevEntity> devInfo) {
		if (devInfo != null) {
			this.devInfo = devInfo;
		}
	}
	
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		String text = "";
		if (this.devInfo.size() == 1) {
			text = this.devInfo.get(0).getName();
		} else if (this.devInfo.size() == 2) {
			text = this.devInfo.get(0).getName() + "," + this.devInfo.get(1).getName();
		} else if (this.devInfo.size() > 2) {
			text = this.devInfo.get(0).getName() + "," + this.devInfo.get(1).getName() + "等" + this.devInfo.size() + "人";
		} else {
			return result;
		}
		result.put("x", this.locationInfo.getX());
		result.put("y", this.locationInfo.getY());
		result.put("text", text);
		return result;
	}

}
