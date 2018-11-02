package entity;

public class Patient {
	
	
	String patientenID;
	int alter;
	int plz;
	
	public Patient(String patientenID, int alter, int plz) {
		this.patientenID= patientenID;
		this.alter= alter;
		this.plz= plz;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getPatientenID() {
		return patientenID;
	}

	public void setPatientenID(String patientenID) {
		this.patientenID = patientenID;
	}
	

}
