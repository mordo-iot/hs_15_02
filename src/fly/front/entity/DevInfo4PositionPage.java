package fly.front.entity;

import java.util.ArrayList;

public class DevInfo4PositionPage {
	
	public DevInfo4PositionPage() {
		super();
	}

	public DevInfo4PositionPage(String devType, ArrayList<DeviceInfo> devList) {
		super();
		this.devType = devType;
		this.devList = devList;
	}

	private String devType;
	
	private ArrayList<DeviceInfo> devList;

	public String getDevType() {
		return devType;
	}

	public void setDevType(String devType) {
		this.devType = devType;
	}

	public ArrayList<DeviceInfo> getDevList() {
		return devList;
	}

	public void setDevList(ArrayList<DeviceInfo> devList) {
		this.devList = devList;
	}
	
}
