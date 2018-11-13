package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.opencsv.CSVReader;

import converter.IStringToDate;
import converter.impl.StringToDate;
import entity.Diagnose;
import entity.Aufenthalt;
//import services.PatientenService;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

public class AufenthaltManager {
	private static Collection <Aufenthalt> aufenthalte;
	private Collection<Aufenthalt> notfaelle;
	
	public AufenthaltManager() {
		this.aufenthalte = new ArrayList<Aufenthalt>();
		
	}
	
	public void lesenCSVein() throws IOException{
		IStringToDate strtoD = new StringToDate();
		FileReader patientenFile = null;
		CSVReader reader = null;
		boolean weiter = true;
		String [] nextLine;
		
		try{
			patientenFile = new FileReader("patients.csv");
		    reader = new CSVReader(patientenFile);
		} catch (FileNotFoundException e){
			System.err.println("Datei nicht gefunden!");
			weiter = false;
		}
		
		if(weiter){
			reader.readNext();//ueberspringe die Kopfzeile der CSV
			while ((nextLine = reader.readNext()) != null) {
				if(this.findAufenthaltByID(nextLine[0]) == null){//gucke, ob Patientenobjekt bereits existiert.
//					Patient aPatient;
					this.aufenthalte.add(
						new Aufenthalt(
							nextLine[0],
							nextLine[1],
							//(int) nf.parse(nextLine[2]).doubleValue()
							(int) Double.parseDouble(nextLine[2].replace(',','.'))
						)
					);
				}
				this.notfaelle.add(
					new Aufenthalt(
						this.findAufenthaltByID(nextLine[0]),
						Integer.parseInt(nextLine[3]),//Dringlichkeit der Einweisung
						strtoD.convertDate(nextLine[4]),//Beginn der Behandlung
						strtoD.convertDate(nextLine[5]),//Ende der Behandlung
						nextLine[6]
					)
				);
			}
			
			try{
				patientenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}

	public Aufenthalt findAufenthaltByID(String ID) {
		Iterator<Aufenthalt> it = aufenthalte.iterator();
		while (it.hasNext()) {
			Aufenthalt aufenthalte = it.next();
			if (aufenthalte.getAufenthaltID().equals(ID)){
				return aufenthalte;
			}
		}
		return null;
	}

	
		public static Collection<Aufenthalt> getAufenthaltID() {
			return  aufenthalte;
	}


public static Collection<Aufenthalt> getAufenthalte() {
	return aufenthalte;
}
 
}
