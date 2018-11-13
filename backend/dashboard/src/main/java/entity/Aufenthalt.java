package entity;

import java.util.Date;

public class Aufenthalt {
	String aufenthaltID;//hier Zahlenwert oder Patientenobjekt?
	Date startdate;
	//@SuppressWarnings("deprecation")
	//Date startdate= new Date(2012-1900, 05, 19, 20, 15);
							//year,  month,  date,  hrs,  min
	Date enddate;
	String Enweisungsart;
	int dringlichkeit;
	String plz;
	int alter;

	
	@SuppressWarnings("deprecation")
	public Aufenthalt(String aufenthaltID, int Dringlichkeit, Date startdate, Date enddate, 
			String Einweisungsart, String plz, int alter) {
			this.aufenthaltID= aufenthaltID;
			this.dringlichkeit= Dringlichkeit;
			this.startdate= startdate;
			this.enddate= enddate;
			this.Enweisungsart= Einweisungsart;
			this.plz = plz;
			this.alter = alter;
	}

	
	public String getAufenthaltID() {
		return aufenthaltID;
	}


	public void setAufenthaltID(String aufenthaltID) {
		this.aufenthaltID = aufenthaltID;
	}


	public String getPlz() {
		return plz;
	}


	public void setPlz(String plz) {
		this.plz = plz;
	}


	public int getAlter() {
		return alter;
	}


	public void setAlter(int alter) {
		this.alter = alter;
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
