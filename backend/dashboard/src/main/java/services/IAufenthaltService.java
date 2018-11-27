package services;

import java.util.Collection;
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
	public HashMap<Integer,Integer> countDringlichkeit();
	public char[] countAlter();
}
