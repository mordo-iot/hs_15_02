package fly.front.entity.map;


public class LocationOnMap {

	public LocationOnMap() {
		super();
	}
	
	public LocationOnMap(int posId, int x, int y) {
		super();
		this.posId = posId;
		this.x = x;
		this.y = y;
	}

	/** 关联的PositionEntiy的ID */
	private int posId;
	
	/** 点相对于地图的x */
	private int x;
	
	/** 点相对于地图的y */
	private int y;
	
	public int getPosId() {
		return posId;
	}
	
	public void setPosId(int posId) {
		this.posId = posId;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	} 
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
}
