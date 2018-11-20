package app;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import entity.Diagnose;
import services.AufenthaltService;
import services.AufenthaltServiceMapImpl;
import services.responses.CorsFilter;
import services.responses.StandardResponse;
import services.responses.StatusResponse;
import entity.Aufenthalt;

import usecase.DiagnosenManager;
import usecase.AufenthaltManager;
import static spark.Spark.*;

public class Application {
	
	public static void main(String[] args) throws IOException {
			
			DiagnosenManager dm = new DiagnosenManager();
			AufenthaltService as = new AufenthaltServiceMapImpl();
			AufenthaltManager am = new AufenthaltManager(as);
			
			
			//CSV Dateien einlesen
			dm.lesenCSVein();
			am.lesenCSVein();
			
			//Array der Aufenthalte ausgeben (Achtung - Braucht sehr lange!)
			//System.out.println("Aufenthalte Array:"+ as.getAufenthalte().toString());
	
			 
			 CorsFilter.apply(); //APPLY THIS BEFORE MAPPING THE ROUTES
			
		     get("/hello", (req, res) -> "Hello World");
		    
		     

		     
		     //es wird immer nur eine begrenzte anzahl ausgegeben
		     get("/aufenthalte", (request, response) -> {
				 response.type("application/json");
				 String start = request.queryParams("start");
				 String size = request.queryParams("size");
				 return new Gson().toJson(
						 as.getAufenthaltePaginiert(Integer.parseInt(start), Integer.parseInt(size)));
				 
		     });
		     
//		     get("/aufenthalte", (request, response) -> {
//				 response.type("application/json");
//				    return new Gson().toJson(
//				      as.getAufenthalte());
//		     });
		     
//		     get("/diagnosen", (request, response) -> {
//				 response.type("application/json");
//				    return new Gson().toJson(
//				      new StandardResponse(StatusResponse.SUCCESS,new Gson()
//				        .toJsonTree(dm.getDianosen())));
//		     });
		    
			
			//diagnosedummies zum testen
//			Diagnose d1= new Diagnose("1","21cd");
//			Diagnose d2= new Diagnose("2","48i");
//			Diagnose d3= new Diagnose("3","23.1e");
	
			//notfalldummies zum testen
			
//			Aufenthalt n1= new Aufenthalt("h2v3eb23vh23j",1, new Date(2019, 5, 19, 20, 15), new Date(2020, 1, 1, 12, 12),"Helikopter","49076",25);
//			Aufenthalt n2= new Aufenthalt("j2k3b42j23432",4, new Date( 2018, 7, 23, 9, 15) ,new Date (2018,  7, 23, 12, 30),"Selbst","64338",23);
//			Aufenthalt n3= new Aufenthalt("jk23b4b23b423",2, new Date( 2010, 04, 11, 15, 00),new Date( 2010, 04, 13, 14, 00) ,"Rettungswagen","34212",45);
			
				
		
//			System.out.println("Aufenthalt 1:  "+	n1.getAufenthaltID() +
//					", \nStartdatum: "+ n1.getStartdate() +", \nEnddatum: "+ n1.getEnddate()
//					+" ,\nEinweisungsart: "+ n1.getEnweisungsart()+ ", Dringlichkeit: "
//					+n1.getDringlichkeit()+" alter: "+ n1.getAlter());
//
//			
//			HashMap<String, Aufenthalt> mapGefiltert = as.gefiltertNachDringlichkeit(1);
			System.out.println(as.countDringlichkeit());
	}
}
