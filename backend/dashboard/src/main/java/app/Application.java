package app;

import java.io.IOException;
import java.util.Date;

import entity.Diagnose;
import entity.Aufenthalt;

import usecase.DiagnosenManager;
import usecase.AufenthaltManager;
import static spark.Spark.*;

public class Application {
	
	public static void main(String[] args) throws IOException {
	
			 
		     get("/hello", (req, res) -> "Hello World");
		    
			
			//diagnosedummies zum testen
			Diagnose d1= new Diagnose("1","21cd");
			Diagnose d2= new Diagnose("2","48i");
			Diagnose d3= new Diagnose("3","23.1e");
	
			//notfalldummies zum testen
			
			Aufenthalt n1= new Aufenthalt("h2v3eb23vh23j",1, new Date(2019, 5, 19, 20, 15), new Date(2020, 1, 1, 12, 12),"Helikopter","49076",25);
			Aufenthalt n2= new Aufenthalt("j2k3b42j23432",4, new Date( 2018, 7, 23, 9, 15) ,new Date (2018,  7, 23, 12, 30),"Selbst","64338",23);
			Aufenthalt n3= new Aufenthalt("jk23b4b23b423",2, new Date( 2010, 04, 11, 15, 00),new Date( 2010, 04, 13, 14, 00) ,"Rettungswagen","34212",45);
		
				
			System.out.println("Aufenthalt 1:  "+	n1.getAufenthaltID() +
					", \nStartdatum: "+ n1.getStartdate() +", \nEnddatum: "+ n1.getEnddate()
					+" ,\nEinweisungsart: "+ n1.getEnweisungsart()+ ", Dringlichkeit: "
					+n1.getDringlichkeit()+" alter: "+ n1.getAlter());

			

/*			DiagnosenManager dm = new DiagnosenManager();
			dm.lesenCSVein();
			dm.toString();*/
			
			AufenthaltManager am = new AufenthaltManager();
			am.lesenCSVein();
			am.toString();
			
	}
		
}
