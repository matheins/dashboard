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
			IStringToDate strtoD = new StringToDate();
			Date vonDatumExamp = strtoD.convertDate("2020-07-31 17:00:47");
			Date bisDatumExamp = strtoD.convertDate("2021-04-15 17:00:47");
			
			
			//CSV Dateien einlesen
			dm.lesenCSVein();
			am.lesenCSVein();
			
			//Array der Aufenthalte ausgeben (Achtung - Braucht sehr lange!)
			//System.out.println("Aufenthalte Array:"+ as.getAufenthalte().toString());
	
			 
			 CorsFilter.apply(); //APPLY THIS BEFORE MAPPING THE ROUTES
			
			 //Hello World
		     get("/hello", (req, res) -> "Hello World");
		     

		     
		     //es wird immer nur eine begrenzte anzahl ausgegeben
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
		     
		     //Beispiel: http://localhost:4567/aufenthalte/zeitundtyp?start=2021-01-01 00:00:00&end=2022-12-30 00:00:00&typ=Einweisung
		     get("/aufenthalte/zeitundtyp", (request, response) -> {
		    	 IStringToDate Date = new StringToDate();
				 response.type("application/json");
				 String start = request.queryParams("start");
				 String end = request.queryParams("end");
				 String typ = request.queryParams("typ");
				 return new Gson().toJson(
						 as.getAufenthaltNachZeitUndTyp(Date.convertDate(start), Date.convertDate(end),typ)); 
		     });
		     
		     get("/aufenthalte/alter", (request, response) -> {
		    	 response.type("application/json");
		    	 	return as.countAlter();
		     });
		     
		     // Beispiel: http://localhost:4567/aufenthalte/zeit?start=2021-01-01 00:00:00&end=2022-12-30 00:00:00
		     get("/aufenthalte/zeit", (request, response) -> {
		    	 IStringToDate Date = new StringToDate();
				 response.type("application/json");
				 String start = request.queryParams("start");
				 String end = request.queryParams("end");
				 return new Gson().toJson(
						 as.getAufenthaltNachZeit(Date.convertDate(start), Date.convertDate(end)));
		     });
		     
		     //Beispiel: http://localhost:4567/aufenthalte/zeit/tage?vonDatum=2021-01-01%2000:00:00&bisDatum=2022-12-30%2000:00:00&fasseEinweisungsartenZusammen=false
		     get("/aufenthalte/zeit/tage", (request, response) -> {
		    	 response.type("application/json");
		    	 Date vonDatum = strtoD.convertDate(request.queryParams("vonDatum"));
		    	 Date bisDatum = strtoD.convertDate(request.queryParams("bisDatum"));
		    	 boolean fasseEinweisungsartenZusammen = Boolean.parseBoolean(request.queryParams("fasseEinweisungsartenZusammen"));
		    	 	return as.countAufenthaltNachTage(vonDatum, bisDatum, fasseEinweisungsartenZusammen);
		     });
		     
		     // Beispiel: http://localhost:4567/aufenthalte/typ?typ=Einweisung
		     get("/aufenthalte/typ", (request, response) -> {
		    	 IStringToDate Date = new StringToDate();
				 response.type("application/json");
				 String typ = request.queryParams("typ");
				 return new Gson().toJson(
						 as.getAufenthaltNachTyp(typ));
		     });
		     

		     //Beispiel: http://localhost:4567/aufenthalte/zeit/wochen?vonDatum=2021-01-01%2000:00:00&bisDatum=2022-12-30%2000:00:00&fasseEinweisungsartenZusammen=false
		     get("/aufenthalte/zeit/wochen", (request, response) -> {
		    	 response.type("application/json");
		    	 Date vonDatum = strtoD.convertDate(request.queryParams("vonDatum"));
		    	 Date bisDatum = strtoD.convertDate(request.queryParams("bisDatum"));
		    	 boolean fasseEinweisungsartenZusammen = Boolean.parseBoolean(request.queryParams("fasseEinweisungsartenZusammen"));
		    	 	return as.countAufenthaltNachWochen(vonDatum, bisDatum, fasseEinweisungsartenZusammen);
		     });
		     
		     //Beispiel: http://localhost:4567/aufenthalte/zeit/monate?vonDatum=2021-01-01%2000:00:00&bisDatum=2022-12-30%2000:00:00&fasseEinweisungsartenZusammen=false
		     get("/aufenthalte/zeit/monate", (request, response) -> {
		    	 response.type("application/json");
		    	 Date vonDatum = strtoD.convertDate(request.queryParams("vonDatum"));
		    	 Date bisDatum = strtoD.convertDate(request.queryParams("bisDatum"));
		    	 boolean fasseEinweisungsartenZusammen = Boolean.parseBoolean(request.queryParams("fasseEinweisungsartenZusammen"));
		    	 	return as.countAufenthaltNachMonaten(vonDatum, bisDatum, fasseEinweisungsartenZusammen);
		     });
		     
		     get("/aufenthalte/einlieferungsarten", (request, response) -> {
		    	 response.type("application/json");
		    	 	return as.countEinlieferungsarten();
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
			System.out.println(as.countDringlichkeit());
			System.out.println(as.countAlter());//im Alter null stimmt es noch nicht, da -0,xxx mitgezaehlt wird. Vermutlich 
			System.out.println(as.countEinlieferungsarten());
//			System.out.println(as.countAufenthaltNachWochen(strtoD.convertDate("2021-02-07 17:00:47"), strtoD.convertDate("2022-04-16 11:42:00")));
			//System.out.println(as.gefiltertNachDringlichkeit(1).toString());
			System.out.println(as.countAufenthaltNachTage(strtoD.convertDate("2021-07-31 17:00:47"), strtoD.convertDate("2021-08-31 11:42:00"), false));
			System.out.println(as.countAufenthaltNachWochen(strtoD.convertDate("2018-07-31 17:00:47"), strtoD.convertDate("2029-08-31 11:42:00"), false));
			System.out.println(as.countAufenthaltNachMonaten(strtoD.convertDate("2021-07-31 17:00:47"), strtoD.convertDate("2022-04-16 11:42:00"), false));
	}
}
