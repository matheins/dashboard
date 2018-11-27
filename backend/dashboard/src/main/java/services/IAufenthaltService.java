package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;

import entity.Aufenthalt;

public interface IAufenthaltService {

	public void addAufenthalt(Aufenthalt aufenthalt);
	public Collection<Aufenthalt> getAufenthalte();
	public Aufenthalt getAufenthalt(String id);
	public List<Aufenthalt> getAufenthaltePaginiert(int start, int size);
	public boolean aufenthaltExists(String id);
	public String countDringlichkeit();
	public String countAlter();
	public int countNachEinlieferungsart(String einlieferungsart);
	public int countNachZeitUndEinlieferungsart(Date vonDatum, Date bisDatum, String einlieferungsart);
	public int countNachZeit(Date vonDatum, Date bisDatum);
}
