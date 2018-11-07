package main.java.usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;

import com.opencsv.CSVReader;

import main.java.converter.IStringToDate;
import main.java.converter.impl.StringToDate;
import main.java.entity.Diagnose;
import main.java.entity.Notfall;
import main.java.entity.Patient;

public class PatientenManager {
	private Collection <Patient> patienten;
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
		
		while(weiter){
//			NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
			reader.readNext();//ueberspringe die Kopfzeile der CSV
			while ((nextLine = reader.readNext()) != null) {
				Patient aPatient;
				this.patienten.add(
					aPatient = new Patient(
							nextLine[0],
							nextLine[1],
							//(int) nf.parse(nextLine[2]).doubleValue()
							(int) Double.parseDouble(nextLine[2].replace(',','.'))
					)
				);
				this.notfaelle.add(
					new Notfall(
							aPatient,//beachten: funktioniert so noch nicht, da jedesmal ein neues Patienten-Objekt
									//angelegt wird. Daher notwendig, aus der Collection mittels der ID das Patienten-Objekt
									//ausgeben zu lassen,
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
	     
		/*Scanner teilstringEinlesen = null;
		boolean weiter = true;

		this.patienten.clear();

		while(weiter){
			while(dateiEinlesen.hasNextLine()){

			     
				//lese jeweils nur eine Zeile ein.
				String eineZeile = dateiEinlesen.nextLine();
				//zerlege hier die Zeile mittels des Delimeters ; in zwei Teile.
				teilstringEinlesen = new Scanner(eineZeile).useDelimiter(",");//TODO: hier noch Speicherproblem loesen.//evtl. Hochkommata in PLZ beruecksichtigen///Bibliothek verwenden
				
				//zunaechst in 1. der Spalte mit der ID die Anfuehrungszeichen entfernen, wenn vorhanden
				String ersteSpalte = teilstringEinlesen.next();
				if (ersteSpalte.startsWith("\"")) {
					ersteSpalte = ersteSpalte.substring(1, 33);
				} else {
					//ersteZeile kann so bleiben
				}
				this.patienten.add(
					new Patient(
						ersteSpalte,
						teilstringEinlesen.next().substring(1, 6),
						(int) Double.parseDouble(teilstringEinlesen.next())//TODO: aus meiner Sicht hier Double, entsprechend .csv /Kai
					)
				);
			} try{
//				teilstringEinlesen.close();
				patientenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}*/
		}
	}
}
