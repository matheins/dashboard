package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.opencsv.CSVReader;

import entity.Diagnose;

public class DiagnosenManager {
	private Collection <Diagnose> diagnosen;
	
	public DiagnosenManager() {
		this.diagnosen = new ArrayList<Diagnose>();
	}
	
	public void lesenCSVein() throws IOException{
		FileReader diagnosenFile = null;
		CSVReader reader = null;
		boolean weiter = true;
		String [] nextLine;
		try{
			diagnosenFile = new FileReader(("diagnoses.csv"));
			reader= new CSVReader(diagnosenFile);
		} catch (FileNotFoundException e){
			System.err.println("Datei nicht gefunden!");
			weiter = false;
		}
		this.diagnosen.clear();
		
		if(weiter){
			reader.readNext(); //uebersoringe die Kpfzeile der CSV
			while((nextLine= reader.readNext()) !=null){
				this.diagnosen.add(
				new Diagnose(
						nextLine[0],
						nextLine[1]
						)
				);
				
				
			} try{
				diagnosenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}
}
