package main.java.entity;

public class Diagnose {

	String patientenID;//hier Datentyp Patient? Evtl. auch nicht, da wir die Verknuepfungen zwischen verschiedenen nicht-primitiven Datentypen gering halten wollen
	String icdID;
	
	
	public Diagnose(String patientenID, String icdID) {
		this.patientenID= patientenID;
		this.icdID= icdID;
	}


	public String getPatientenID() {
		return patientenID;
	}


	public void setPatientenID(String patientenID) {
		this.patientenID = patientenID;
	}


	public String getIcdID() {
		return icdID;
	}


	public void setIcdID(String icdID) {
		this.icdID = icdID;
	}
	
	
}
