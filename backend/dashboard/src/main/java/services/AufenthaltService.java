package services;

import java.util.Collection;

import entity.Aufenthalt;
import usecase.AufenthaltManager;

public class AufenthaltService {

	  // returns a list of all aufenthalte
	  public static Collection<Aufenthalt> getAllAufenthalte() { 
		  return AufenthaltManager.getAufenthalte();
	  }
	   
	  // returns a single patient by id
//	  public Aufenthalt getAufenthalt(String id) { 
		  //.. 
		  
	//  }
	 
}
