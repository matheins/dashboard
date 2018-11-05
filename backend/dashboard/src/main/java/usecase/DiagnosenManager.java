package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import entity.Diagnose;

public class DiagnosenManager {
	private Collection <Diagnose> diagnosen;
	
	public DiagnosenManager() {
		this.diagnosen = new ArrayList<Diagnose>();
	}
	
	public void lesenCSVein(){
		FileReader diagnosenFile = null;
		Scanner dateiEinlesen = null;
		Scanner teilstringEinlesen = null;
		boolean weiter = true;
		try{
			diagnosenFile = new FileReader(("diagnoses.csv"));
			dateiEinlesen = new Scanner(diagnosenFile);//.useDelimiter(";");
		} catch (FileNotFoundException e){
			System.err.println("Datei nicht gefunden!");
			weiter = false;
		}
		this.diagnosen.clear();
		dateiEinlesen.nextLine();//Kopfzeile der .csv mit Angabe, was in der ersten
									//und zweiten Spalte steht, ueberspringen 
		while(weiter){
			while(dateiEinlesen.hasNextLine()){
				//lese jeweils nur eine Zeile ein.
				String eineZeile = dateiEinlesen.nextLine();
				//zerlege hier die Zeile mittels des Delimeters ; in zwei Teile.
				teilstringEinlesen = new Scanner(eineZeile).useDelimiter(";");//TODO: hier noch Speicherproblem loesen.
				
				//zunaechst in 1. der Spalte mit der ID die Anfuehrungszeichen entfernen, wenn vorhanden
				String ersteZeile = teilstringEinlesen.next();
				if (ersteZeile.startsWith("\"")) {
					ersteZeile = ersteZeile.substring(1, 33);
				} else {
					//ersteZeile kann so bleiben
				}
				this.diagnosen.add(
					new Diagnose(
						ersteZeile,
						teilstringEinlesen.next()
					)
				);
			} try{
				teilstringEinlesen.close();
				diagnosenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}
}
