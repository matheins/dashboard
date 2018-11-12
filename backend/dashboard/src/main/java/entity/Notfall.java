package entity;

import java.util.Date;

public class Notfall {
	Patient patientenID;//hier Zahlenwert oder Patientenobjekt?
	Date startdate;
	//@SuppressWarnings("deprecation")
	//Date startdate= new Date(2012-1900, 05, 19, 20, 15);
							//year,  month,  date,  hrs,  min
	Date enddate;
	String Enweisungsart;
	int dringlichkeit;

	
	@SuppressWarnings("deprecation")
	public Notfall(Patient patient, int Dringlichkeit, Date startdate, Date enddate, String Einweisungsart
		) {
			this.patientenID= patient;
			this.dringlichkeit= Dringlichkeit;
			this.startdate= startdate;
			this.enddate= enddate;
			this.Enweisungsart= Einweisungsart;
	}

	public Patient getPatientenID() {
		return patientenID;
	}

	public void setPatientenID(Patient patientenID) {
		this.patientenID = patientenID;
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
