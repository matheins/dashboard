package entity;

import java.util.Date;

public class Notfall {

	Patient patientenID;
	Diagnose diagnoseID;
	Date startdate;
	Date enddate;
	int Enweisungsart;
	int dringlichkeit;

	
	public Notfall(Patient patient, Diagnose diagnose) {
		this.patientenID= patient;
		this.diagnoseID= diagnose;
	}
}
