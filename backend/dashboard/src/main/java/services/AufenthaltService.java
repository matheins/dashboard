package services;

import java.util.Collection;

import entity.Aufenthalt;
import usecase.AufenthaltManager;

public interface AufenthaltService {

	public void addAufenthalt(Aufenthalt aufenthalt);
	
	public Collection<Aufenthalt> getAufenthalte();
	
	public Aufenthalt getAufenthalt(String id);
	
	public boolean aufenthaltExists(String id);
	 
	 
}
