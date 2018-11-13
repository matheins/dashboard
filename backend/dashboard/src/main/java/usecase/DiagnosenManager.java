package usecase;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

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
		Diagnose aDiagnose;
		
		try{
			diagnosenFile = new FileReader(("diagnoses.csv"));
			final CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
			reader = new CSVReaderBuilder(diagnosenFile).withSkipLines(1)
					.withCSVParser(parser).build();
		} catch (FileNotFoundException e){
			System.err.println("Datei nicht gefunden!");
			weiter = false;
		}
		this.diagnosen.clear();
		
		if(weiter){
			reader.readNext();//ueberspringe die Kopfzeile der CSV
			while((nextLine = reader.readNext()) !=null){
				this.diagnosen.add(
					aDiagnose = new Diagnose(
						nextLine[0],
						nextLine[1]
					)
				);
				System.out.println(aDiagnose.toString());
			}
			try{
				System.out.println("Import der Diagnosedaten aus diagnoses.csv abgeschlossen!");
				diagnosenFile.close();
			} catch(IOException e){
				System.err.println("Datei nicht gefunden!");
			}
		}
	}
}
