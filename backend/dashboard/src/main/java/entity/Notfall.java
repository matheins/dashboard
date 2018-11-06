package main.java.entity;

import java.util.Date;

public class Notfall {
	
	String notfallID;
	Patient patientenID;
	Diagnose diagnoseID;
	Date startdate;
	//@SuppressWarnings("deprecation")
	//Date startdate= new Date(2012-1900, 05, 19, 20, 15);
							//year,  month,  date,  hrs,  min
	Date enddate;
	String Enweisungsart;
	int dringlichkeit;

	
	@SuppressWarnings("deprecation")
	public Notfall(String notfallID, Patient patient, Diagnose diagnose, 
			int Jahr, int Monat, int Tag, int Stunde, int Minute,
			int EndJahr, int EndMonat, int EndTag, int EndStunde, int EndMinute,
			String Einweisungsart, int Dringlichkeit
		) {
			this.notfallID= notfallID;
			this.patientenID= patient;
			this.diagnoseID= diagnose;
			this.startdate= new Date(Jahr-1900, Monat, Tag, Stunde, Minute);
			this.enddate= new Date(EndJahr-1900, EndMonat, EndTag, EndStunde, EndMinute);
			this.Enweisungsart= Einweisungsart;
			this.dringlichkeit= Dringlichkeit;
	}





	public String getNotfallID() {
		return notfallID;
	}


	public void setNotfallID(String notfallID) {
		this.notfallID = notfallID;
	}


	public Patient getPatientenID() {
		return patientenID;
	}


	public void setPatientenID(Patient patientenID) {
		this.patientenID = patientenID;
	}


	public Diagnose getDiagnoseID() {
		return diagnoseID;
	}


	public void setDiagnoseID(Diagnose diagnoseID) {
		this.diagnoseID = diagnoseID;
	}


	public Date getStartdate() {
		return startdate;
	}


	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}


	public Date getEnddate() {
		return enddate;
	}


	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}


	public String getEnweisungsart() {
		return Enweisungsart;
	}


	public void setEnweisungsart(String enweisungsart) {
		Enweisungsart = enweisungsart;
	}


	public int getDringlichkeit() {
		return dringlichkeit;
	}


	public void setDringlichkeit(int dringlichkeit) {
		this.dringlichkeit = dringlichkeit;
	}
	
	
	
}
