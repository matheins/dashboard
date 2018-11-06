package main.java.app;

import main.java.entity.Diagnose;
import main.java.entity.Notfall;
import main.java.entity.Patient;
import main.java.usecase.DiagnosenManager;
import main.java.usecase.PatientenManager;

public class Application {

	
	public static void main(String[] args) {
		
			//patientendummies zum testen
			Patient p1= new Patient("we21r2tz2",14,49076);
			Patient p2= new Patient("f1c2ghv32",32,49421);
			Patient p3= new Patient("fcg322bv3",76,53924);
			
			//diagnosedummies zum testen
			Diagnose d1= new Diagnose("1","21cd");
			Diagnose d2= new Diagnose("2","48i");
			Diagnose d3= new Diagnose("3","23.1e");
	
			//notfalldummies zum testen
			Notfall n1= new Notfall("1234678",p1,d1, 2019, 5, 19, 20, 15, 2020, 1, 1, 12, 12,"Helikopter",1);
			Notfall n2= new Notfall("1234679",p2,d3, 2018, 7, 23, 9, 15, 2018, 7, 23, 12, 30,"Selbst",4);
			Notfall n3= new Notfall("1234680",p3,d2, 2010, 04, 11, 15, 00, 2010, 04, 13, 14, 00,"Rettungswagen",2);
		
			
			
		
			System.out.println("Notfall 1:  "+	n1.getNotfallID() +", Patient: "+ n1.getPatientenID().getPatientenID()+
					", \nStartdatum: "+ n1.getStartdate() +", \nEnddatum: "+ n1.getEnddate()
					+" ,\nEinweisungsart: "+ n1.getEnweisungsart()+ ", Dringlichkeit: "
					+n1.getDringlichkeit());
			
/*			DiagnosenManager dm = new DiagnosenManager();
			dm.lesenCSVein();
			dm.toString();*/
			
			PatientenManager pm = new PatientenManager();
			pm.lesenCSVein();
			pm.toString();
			
	}
		
		
}
