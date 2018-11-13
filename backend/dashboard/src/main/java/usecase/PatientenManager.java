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
import entity.Notfall;
import entity.Patient;
import entity.PatientenService;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.*;

public class PatientenManager {
	private static Collection <Patient> patienten;
	private Collection<Notfall> notfaelle;
	
	public PatientenManager() {
		this.patienten = new ArrayList<Patient>();
		this.notfaelle = new ArrayList<Notfall>();
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
				if(this.findPatientByID(nextLine[0]) == null){//gucke, ob Patientenobjekt bereits existiert.
//					Patient aPatient;
					this.patienten.add(
						new Patient(
							nextLine[0],
							nextLine[1],
							//(int) nf.parse(nextLine[2]).doubleValue()
							(int) Double.parseDouble(nextLine[2].replace(',','.'))
						)
					);
				}
				this.notfaelle.add(
					new Notfall(
						this.findPatientByID(nextLine[0]),
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

	public Patient findPatientByID(String ID) {
		Iterator<Patient> it = patienten.iterator();
		while (it.hasNext()) {
			Patient patient = it.next();
			if (patient.getPatientenID().equals(ID)){
				return patient;
			}
		}
		return null;
	}

	public PatientenManager(final PatientenService patientenService) {
    
		get("/patienten", new Route() {
			@Override
			public Object handle(Request request, Response response) {
				// process request
				return patientenService.getAllPatienten();
			}
		});
    
		// more routes
	}

	public static Collection<Patient> getPatienten() {
		return patienten;
	}

	public Collection<Notfall> getNotfaelle() {
		return notfaelle;
	} 
}
