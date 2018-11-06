package main.java.usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import main.java.entity.Diagnose;
import main.java.entity.Patient;

public class PatientenManager {
	private Collection <Patient> patienten;
	
	public PatientenManager() {
		this.patienten = new ArrayList<Patient>();
	}
	
	public void lesenCSVein(){
		FileReader patientenFile = null;
		Scanner dateiEinlesen = null;
		Scanner teilstringEinlesen = null;
		boolean weiter = true;
		try{
			patientenFile = new FileReader(("patients.csv"));
			dateiEinlesen = new Scanner(patientenFile);//.useDelimiter(";");
		} catch (FileNotFoundException e){
			System.err.println("Datei nicht gefunden!");
			weiter = false;
		}
		this.patienten.clear();
		dateiEinlesen.nextLine();//Kopfzeile der .csv mit Angabe, was in der ersten
									//und zweiten Spalte steht, ueberspringen 
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
						Integer.parseInt(teilstringEinlesen.next().//replace(oldChar, newChar)//.substring(1, 6)),
						(int) Double.parseDouble(teilstringEinlesen.next())//TODO: aus meiner Sicht hier Double, entsprechend .csv /Kai
					)
				);
			} try{
				teilstringEinlesen.close();
				patientenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}
}
