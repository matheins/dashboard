package app;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import converter.IStringToDate;
import converter.impl.StringToDate;
import entity.Diagnose;
import services.IAufenthaltService;
import services.AufenthaltServiceMapImpl;
import services.IDiagnoseService;
import services.DiagnoseServiceMapImpl;
import services.responses.CorsFilter;
import services.responses.StandardResponse;
import services.responses.StatusResponse;
import entity.Aufenthalt;

import usecase.DiagnosenManager;
import usecase.AufenthaltManager;
import static spark.Spark.*;

public class Application {
	
	public static void main(String[] args) throws IOException {
			
			IDiagnoseService ds = new DiagnoseServiceMapImpl();
			DiagnosenManager dm = new DiagnosenManager(ds);
			IAufenthaltService as = new AufenthaltServiceMapImpl();
			AufenthaltManager am = new AufenthaltManager(as);
			
			
			//CSV Dateien einlesen
			dm.lesenCSVein();
			am.lesenCSVein();
			
			//Array der Aufenthalte ausgeben (Achtung - Braucht sehr lange!)
			//System.out.println("Aufenthalte Array:"+ as.getAufenthalte().toString());
	
			 
			 CorsFilter.apply(); //APPLY THIS BEFORE MAPPING THE ROUTES
			
			 //Hello World
		     get("/hello", (req, res) -> "Hello World");
		     
		     //Aufenthalt Objekte
		     get("/aufenthalte", (request, response) -> {
				 response.type("application/json");
				 
				 String start = request.queryParams("start");
				 String size = request.queryParams("size");
				 
				 if(start == null && size == null ){
					return new Gson().toJson(as.getAufenthalte());		 
				 }else{
					return new Gson().toJson( as.getAufenthaltePaginiert(Integer.parseInt(start), Integer.parseInt(size)));
				 }
				 
		     });
		     

		     //Diagnosen-Objekte
		     get("/diagnosen", (request, response) -> {
				 response.type("application/json");
				 String start = request.queryParams("start");
				 String size = request.queryParams("size");
				 if(start == null && size == null){
					return new Gson().toJson(ds.getDiagnosen());
				 }else{
				 	return new Gson().toJson(
						 ds.getDiagnosenPaginiert(Integer.parseInt(start), Integer.parseInt(size)));
				 }
		     });
		     

		     //Aufenthalte nach Dringlichkeit summiert
		     get("/aufenthalte/dringlichkeit", (request, response) -> {
				 response.type("application/json");
				    return as.countDringlichkeit();
		     });

		     
		     
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
		    IStringToDate strtoD = new StringToDate();
			System.out.println(as.countDringlichkeit());
			System.out.println(as.countAlter());//im Alter null stimmt es noch nicht, da -0,xxx mitgezaehlt wird. Vermutlich 
			System.out.println(as.countEinlieferungsarten());
			System.out.println(as.countAufenthaltNachMonaten(strtoD.convertDate("2021-07-31 17:00:47"), strtoD.convertDate("2022-04-16 11:42:00")));
//			System.out.println(as.countAufenthaltNachWochen(strtoD.convertDate("2021-02-07 17:00:47"), strtoD.convertDate("2022-04-16 11:42:00")));
//			System.out.println(as.countAufenthaltNachWochenNeu2(strtoD.convertDate("2021-07-31 17:00:47"), strtoD.convertDate("2022-04-16 11:42:00")));
			//System.out.println(as.gefiltertNachDringlichkeit(1).toString());
	}
}
