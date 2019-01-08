package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;

import entity.Aufenthalt;
import entity.Diagnose;

public interface IAufenthaltService {

	public void addAufenthalt(Aufenthalt aufenthalt);
	public Collection<Aufenthalt> getAufenthalte();
	public Aufenthalt getAufenthalt(String id);
	public List<Aufenthalt> getAufenthaltePaginiert(int start, int size);
	public boolean aufenthaltExists(String id);
	public String countDringlichkeit();
	public String countAlter();
	public String countEinlieferungsarten();
	public HashMap<String, Aufenthalt> gefiltertNachDringlichkeit(int dringlichkeit);
	//die nachfolgenden drei in Johannes' Version ohne boolean
	public String countAufenthaltNachZeiteinheit(Date vonDatum, Date bisDatum, String zeiteinheit);
	public String countAufenthaltNachTage(Date vonDatum, Date bisDatum, String einweisungsart);
	public String countAufenthaltNachMonaten(Date vonDatum, Date bisDatum, String einweisungsart);
	public String countAufenthaltNachWochen(Date vonDatum, Date bisDatum, String einweisungsart);
	public List<Aufenthalt> getAufenthaltNachZeit(Date start, Date end);
	public List<Aufenthalt> getAufenthaltNachZeitUndTyp(Date start, Date end, String typ);
	public ArrayList<Aufenthalt> getAufenthaltNachTyp(String einlieferungsart);
	
}
