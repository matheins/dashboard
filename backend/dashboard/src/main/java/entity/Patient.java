package main.java.entity;

public class Patient {
	
	
	String patientenID;
	int alter;
	String plz;//IMHO: auf String aendern, da im Ausland PLZ auch Buchstaben haben koennen./Kai
	
	public Patient(String patientenID, String plz, int alter){
		this.patientenID = patientenID;
		this.plz = plz;
		this.alter = alter;
	}

	public int getAlter() {
		return alter;
	}

	public void setAlter(int alter) {
		this.alter = alter;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getPatientenID() {
		return patientenID;
	}

	public void setPatientenID(String patientenID) {
		this.patientenID = patientenID;
	}
	

}
