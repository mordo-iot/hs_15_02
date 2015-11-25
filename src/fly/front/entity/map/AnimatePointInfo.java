package fly.front.entity.map;

import java.util.ArrayList;

import roadinfo.api.RoadGenerator;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import fly.entity.dev.DevEntity;

public class AnimatePointInfo {

	public AnimatePointInfo() {
		super();
	}

	public AnimatePointInfo(int startLocationId, int endLocationId, ArrayList<DevEntity> devInfo) {
		super();
		this.startLocationId = startLocationId;
		this.endLocationId = endLocationId;
		this.devInfo = devInfo;
	}

	private int startLocationId;
	
	private int endLocationId;
	
	private ArrayList<DevEntity> devInfo = new ArrayList<DevEntity>();

	public int getStartLocationId() {
		return startLocationId;
	}

	public void setStartLocationId(int startLocationId) {
		this.startLocationId = startLocationId;
	}

	public int getEndLocationId() {
		return endLocationId;
	}

	public void setEndLocationId(int endLocationId) {
		this.endLocationId = endLocationId;
	}

	public ArrayList<DevEntity> getDevInfo() {
		return devInfo;
	}

	public void setDevInfo(ArrayList<DevEntity> devInfo) {
		this.devInfo = devInfo;
	}
	
	public JSONObject toJson(int mapId) {
		JSONObject result = new JSONObject();
		JSONArray detailPath = new JSONArray();
		String text = "";
		try {
			detailPath = JSONArray.fromObject(RoadGenerator.find(mapId, this.startLocationId, this.endLocationId));  //[{"index":1,"x":101,"y":102},{"index":2,"x":151,"y":152},{"index":3,"x":201,"y":202}]
		} catch (JSONException e) {
			
		}
		if (detailPath != null && detailPath.size() > 0) {
			result.put("pathLength", detailPath.size());
		} else {
			result.put("pathLength", 0);
		}
		result.put("path", detailPath);
		
		if (this.devInfo.size() == 1) {
			text = this.devInfo.get(0).getName();
		} else if (this.devInfo.size() == 2) {
			text = this.devInfo.get(0).getName() + "," + this.devInfo.get(1).getName();
		} else if (this.devInfo.size() > 2) {
			text = this.devInfo.get(0).getName() + "," + this.devInfo.get(1).getName() + "等" + this.devInfo.size() + "人";
		}
		result.put("text", text);
		return result;
	}
	
}
