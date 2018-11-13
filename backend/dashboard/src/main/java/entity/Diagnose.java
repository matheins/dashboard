package entity;

public class Diagnose {

	//Beschriftungen der Variablen & Methoden von "Aufenthalt" zu "Diagnose"
	
	String diagnoseID;
	String icdID;
	
	
	public Diagnose(String diagnoseID, String icdID) {
		this.diagnoseID= diagnoseID;
		this.icdID= icdID;
	}


	public String getDiagnoseID() {
		return diagnoseID;
	}


	public void setDiagnoseID(String diagnoseID) {
		this.diagnoseID = diagnoseID;
	}


	public String getIcdID() {
		return icdID;
	}


	public void setIcdID(String icdID) {
		this.icdID = icdID;
	}
	
	public String toString(){
		return "Diagnose-ID :" + this.diagnoseID + ", ICD-ID: " + this.icdID;
	}
}
