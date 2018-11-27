package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Aufenthalt;

public class AufenthaltServiceMapImpl implements IAufenthaltService{
	private int currentValue;//der aktuelle Wert, was gezaehlt wird: Dringlichkeit = 1, = 2, etc.
	private int minValue;//bei Alter: minimaler Wert
	private int maxValue;//bei Alter: maximaler Wert
	private int counter = 0;//wie viel es aktuell von dem zu zaehlenden Wert gibt.
	
	private HashMap<String, Aufenthalt> aufenthaltMap;
	private int countDringlichkeit = 0;
	
	private int counter(){
		return counter++;
	}
	
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
	
	public List<Aufenthalt> getAufenthaltePaginiert(int start, int size){
		ArrayList<Aufenthalt> list = new ArrayList<Aufenthalt>(aufenthaltMap.values());
		if(start + size > list.size()) return new ArrayList<Aufenthalt>();
		return list.subList(start, start + size);
	}
	
	public boolean aufenthaltExists(String id){
		return aufenthaltMap.containsKey(id);
	}
	
	public HashMap<String, Aufenthalt> gefiltertNachDringlichkeit(int dringlichkeit){
		HashMap<String, Aufenthalt> map = new HashMap<>();
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
			if(Aufenthalt.getDringlichkeit()==dringlichkeit){
				map.put(String, Aufenthalt);
			}
		});
		return map;
	}
	
	public String countDringlichkeitF(){//implementiert ueber Filtermethode oben drueber; belassen wegen vorher benutzt
		//HashMap<Integer, Integer> map = new HashMap<>();
		JSONArray json = new JSONArray();
		
			for(int dringlichkeit = 1; dringlichkeit <= 5; dringlichkeit++){
				json.put(new JSONObject()
						.put("id", dringlichkeit)
						.put("value", this.gefiltertNachDringlichkeit(dringlichkeit).size()));				
				//map.put(dringlichkeit, this.gefiltertNachDringlichkeit(dringlichkeit).size());
			}
		return json.toString();
	}
	
	public String countDringlichkeit(){
//		HashMap<Integer, Integer> map = new HashMap<>();
		JSONArray json = new JSONArray();
		
		currentValue = 0;//da der Zaehler fuer alle Klassen gilt, muss er vor dem Hochzaehlen auf Null gesetzt werden.
			for(int dringlichkeit = 1; dringlichkeit <= 5; dringlichkeit++){
				currentValue++;	
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getDringlichkeit()==currentValue){
						counter++;
					}
//					map.put(currentValue, this.counter);
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));	
			}
		return json.toString();
	}
	
	public String countAlter(){
		//wie soll mit negativen Altersangaben umgegangen werden? Vorlaeufig Datensaetze ignorieren, vermutlich fehlerhafte Zufallsdaten
//		HashMap<Integer, Integer> map = new HashMap<>();
		JSONArray json = new JSONArray();
		maxValue = 0;//groesstes Alter, welches vorkommt
		minValue = 100;//setze juengstes Alter auf fiktiven Wert von 100
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
			if(Aufenthalt.getAlter()>maxValue){
				maxValue = Aufenthalt.getAlter();
			} else if(Aufenthalt.getAlter()<minValue){
				minValue = Aufenthalt.getAlter();
			}
		});
		if(minValue<0){
			minValue=0;
		}
		
		currentValue = 0;
		counter = 0;
		for(int alter = minValue; alter <= maxValue; alter++){
			currentValue = alter;
			counter = 0;
			this.aufenthaltMap.forEach((String, Aufenthalt) -> {
				if(Aufenthalt.getAlter()==currentValue){
					counter++;
				}
			});
//			map.put(currentValue, this.counter);
			json.put(new JSONObject()
					.put("id", currentValue)
					.put("value", this.counter));
		}
//		System.out.println(this.minValue + "," + this.maxValue);
//			map.put(currentValue, this.counter);
//			});
		return json.toString();
	}
	
	public int countNachZeit(Date vonDatum, Date bisDatum){
		//JSONArray json = new JSONArray();
		counter = 0;
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
			if(Aufenthalt.getStartdate().after(vonDatum) && Aufenthalt.getStartdate().after(bisDatum)){
					counter++;
			}
		});
		/*json.put(new JSONObject()
				.put("id", currentValue)
				.put("value", this.counter)
		);
		//return json.toString();*/
		return this.counter;
	}
	
	public int countNachEinlieferungsart(String einlieferungsart){
		//JSONArray json = new JSONArray();
		counter = 0;
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
			if(Aufenthalt.getEinweisungsart().equals(einlieferungsart)){
					counter++;
			}
		});
		/*json.put(new JSONObject()
				.put("id", currentValue)
				.put("value", this.counter)
		);
		return json.toString();*/
		return counter;
	}
	
	public int countNachZeitUndEinlieferungsart(Date vonDatum, Date bisDatum, String einlieferungsart){
		//JSONArray json = new JSONArray();
		counter = 0;
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
			if(Aufenthalt.getStartdate().after(vonDatum) && Aufenthalt.getStartdate().after(bisDatum) &&
				Aufenthalt.getEinweisungsart().equals(einlieferungsart)){
					counter++;
			}
		});
		/*json.put(new JSONObject()
				.put("id", currentValue)
				.put("value", this.counter)
		);
		return json.toString();*/
		return counter;
	}
	
}
