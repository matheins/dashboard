package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import converter.IStringToDate;
import converter.impl.StringToDate;
import services.AufenthaltService;
import services.AufenthaltServiceMapImpl;
import entity.Aufenthalt;

public class AufenthaltManager {
	
	//!!!!!Verwaltung der Aufenthalte wurde auf einen eigenen Service mit Interface verlegt. !!!!! 
	
	
	//private Collection <Aufenthalt> aufenthalte;
	//private AufenthaltService as = new AufenthaltServiceMapImpl();
	private AufenthaltService as;
	
	public AufenthaltManager(AufenthaltService as) {
		//this.aufenthalte = new ArrayList<Aufenthalt>();
		this.as = as ;
	}
	
	public void lesenCSVein() throws IOException{
		IStringToDate strtoD = new StringToDate();
		FileReader patientenFile = null;
		CSVReader reader = null;
		boolean weiter = true;
		String [] nextLine;
		Aufenthalt aAufenthalt;
		
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
				as.addAufenthalt(
					aAufenthalt = new Aufenthalt(
						nextLine[0],//ID
						Integer.parseInt(nextLine[3]),//Dringlichkeit
						strtoD.convertDate(nextLine[4]),//Beginn der Behandlung
						strtoD.convertDate(nextLine[5]),//Ende der Behandlung
						nextLine[6],//Einweisungsart
						nextLine[1],//PLZ
						(int) Double.parseDouble(nextLine[2].replace(',','.'))//Alter
					)
				);
				System.out.println(aAufenthalt.toString());	
				}
			try{
				System.out.println("Import der Aufenthaltsdaten aus patients.csv abgeschlossen!");
				patientenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}
	
	//NAchfolgende Aufgaben werden jetzt von eigener Service-Klasse übernommen

//	public Aufenthalt findAufenthaltByID(String ID) {
//		Iterator<Aufenthalt> it = aufenthalte.iterator();
//		while (it.hasNext()) {
//			Aufenthalt aufenthalt = it.next();
//			if (aufenthalt.getAufenthaltID().equals(ID)){
//				return aufenthalt;
//			}
//		}
//		return null;
//	}
//	
//	public Collection<Aufenthalt> getAufenthaltID() {
//			return  aufenthalte;
//	}
//
//	public Collection<Aufenthalt> getAufenthalte() {
//		return aufenthalte;
//	}
 
}
