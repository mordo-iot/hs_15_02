package fly.front.entity;

import fly.entity.position.PositionEntity;

public class PositionPersonNumber {

	public PositionPersonNumber() {
		
	}
	
	public PositionPersonNumber(PositionEntity posinfo, int personNumber) {
		super();
		this.posinfo = posinfo;
		this.personNumber = personNumber;
	}

	private PositionEntity posinfo;
	
	private int personNumber;

	public PositionEntity getPosinfo() {
		return posinfo;
	}

	public void setPosinfo(PositionEntity posinfo) {
		this.posinfo = posinfo;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}
	
	
}
