package app;

import entity.Diagnose;
import entity.Notfall;
import entity.Patient;

public class Application {

	public static void main(String[] args) {
		//patientendummies zum testen
		Patient p1= new Patient("we21r2tz2",14,49076);
		Patient p2= new Patient("f1c2ghv32",32,49421);
		Patient p3= new Patient("fcg322bv3",76,53924);
		
		//diagnosedummies zum testen
		Diagnose d1= new Diagnose(1,"21cd");
		Diagnose d2= new Diagnose(2,"48i");
		Diagnose d3= new Diagnose(3,"23.1e");
		
		Notfall n1= new Notfall(p1,d1);
		
		System.out.println(p1);
	}
		
		
}
