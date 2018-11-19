package services;

import java.util.Collection;
import java.util.HashMap;

import entity.Aufenthalt;

public class AufenthaltServiceMapImpl implements AufenthaltService{
	
	private HashMap<String, Aufenthalt> aufenthaltMap;
	
	public AufenthaltServiceMapImpl(){
		 aufenthaltMap = new HashMap<>();
	}
	
	public void addAufenthalt(Aufenthalt aufenthalt){
		aufenthaltMap.put(aufenthalt.getAufenthaltID(), aufenthalt);
	}
	
	public Collection<Aufenthalt> getAufenthalte(){
		return aufenthaltMap.values();
	}
	
	public Aufenthalt getAufenthalt(String id){
		return aufenthaltMap.get(id);
	}
	
	public boolean aufenthaltExists(String id){
		return aufenthaltMap.containsKey(id);
	}

}
