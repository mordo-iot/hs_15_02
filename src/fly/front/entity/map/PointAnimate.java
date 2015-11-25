package fly.front.entity.map;

public class PointAnimate {
	
	public PointAnimate() {
		super();
	}

	public PointAnimate(int x, int y, String pointName, String animatePath) {
		super();
		this.x = x;
		this.y = y;
		this.pointName = pointName;
		this.animatePath = animatePath;
	}

	private int x;
	
	private int y;
	
	private String pointName;
	
	private String animatePath;

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

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}

	public String getAnimatePath() {
		return animatePath;
	}

	public void setAnimatePath(String animatePath) {
		this.animatePath = animatePath;
	}
	
}
