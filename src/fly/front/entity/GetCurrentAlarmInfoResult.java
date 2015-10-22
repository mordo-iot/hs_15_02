package fly.front.entity;

import java.util.ArrayList;

public class GetCurrentAlarmInfoResult {

	public GetCurrentAlarmInfoResult() {
		super();
	}

	public GetCurrentAlarmInfoResult(String hashCode, ArrayList<CurrentAlarmInfo> list, Integer totalItem) {
		super();
		this.hashCode = hashCode;
		this.list = list;
		this.totalItem = totalItem;
	}

	private String hashCode = "";
	
	private ArrayList<CurrentAlarmInfo> list;
	
	private Integer totalItem = 0;

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public ArrayList<CurrentAlarmInfo> getList() {
		return list;
	}

	public void setList(ArrayList<CurrentAlarmInfo> list) {
		this.list = list;
		this.hashCode = generateHashCode();
	}

	public Integer getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}

	private String generateHashCode() {
		String result = "";
		if (list != null && list.size() > 0) {
			for (CurrentAlarmInfo info : list) {
				result += info.getAlarmId() + ",";
			}
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GetCurrentAlarmInfoResult other = (GetCurrentAlarmInfoResult) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}
	
}
