package services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Aufenthalt;

public class AufenthaltServiceMapImpl implements IAufenthaltService{
	private int currentValue;//der aktuelle Wert, was gezaehlt wird: Dringlichkeit = 1, = 2, etc.
	private int minValue;//bei Alter: minimaler Wert
	private int maxValue;//bei Alter: maximaler Wert
	private int counter = 0;//wie viel es aktuell von dem zu zaehlenden Wert gibt.
	
	//fuer Zeit
	private ZoneId defaultZoneId = ZoneId.systemDefault();
	private WeekFields week = WeekFields.ISO;
	private TemporalField temporalField;
	
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
	
	public String countAufenthaltNachWochen(Date vonDatum, Date bisDatum){
		boolean jahreswechsel = false;
		int lastWeekOfTheYear = 0;
		JSONArray json = new JSONArray();
		temporalField = week.weekOfWeekBasedYear();
		
		//kleinste Kalenderwoche
		minValue=vonDatum.toInstant().atZone(defaultZoneId).toLocalDate().get(temporalField);
		//groesste Kalenderwoche
		maxValue=bisDatum.toInstant().atZone(defaultZoneId).toLocalDate().get(temporalField);
		if(!(vonDatum.getYear()==bisDatum.getYear())){
			//die beiden Daten gehen ueber den Jahreswechsel hinaus.
			jahreswechsel = true;
			int year = vonDatum.getYear();
			LocalDate lastDayOfTheYear = LocalDate.of(year, 12, 31);
			//ein Jahr kann 52 oder 53 Wochen haben, muss daher geprueft werden.
			lastWeekOfTheYear = lastDayOfTheYear.get(temporalField);
		}
		currentValue = 0;
		if (!jahreswechsel){
			//das Jahr bleibt das gleiche, man braucht die Wochenzahl nicht wieder bei 1 anfangen.
			for(int woche = minValue; woche <= maxValue; woche++){
				currentValue = woche;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().get(temporalField)==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
		} else{
			//das Jahr bleibt nicht das gleiche, nach Ablauf des Jahres muss man die Wochenzahl wieder auf 1 setzen.
			for(int woche = minValue; woche <= lastWeekOfTheYear; woche++){
				currentValue = woche;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().get(temporalField)==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
			for(int woche = 1; woche <= maxValue; woche++){
				currentValue = woche;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().get(temporalField)==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
		}
		return json.toString();
	}
	
	public String countAufenthaltNachMonaten(Date vonDatum, Date bisDatum){
		boolean jahreswechsel = false;
		JSONArray json = new JSONArray();
		
		//kleinster Kalendermonat
		minValue=vonDatum.toInstant().atZone(defaultZoneId).toLocalDate().getMonthValue();
		//groesster Kalendermonat
		maxValue=bisDatum.toInstant().atZone(defaultZoneId).toLocalDate().getMonthValue();
		if(!(vonDatum.getYear()==bisDatum.getYear())){
			//die beiden Daten gehen ueber den Jahreswechsel hinaus.
			jahreswechsel = true;
		}
		currentValue = 0;
		if (!jahreswechsel){
			//das Jahr bleibt das gleiche, man braucht die Monatszahl nicht wieder bei 1 anfangen.
			for(int monat = minValue; monat <= maxValue; monat++){
				currentValue = monat;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().getMonthValue()==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
		} else{
			//das Jahr bleibt nicht das gleiche, nach Ablauf des Jahres muss man die Wochenzahl wieder auf 1 setzen.
			for(int monat = minValue; monat <= 12; monat++){
				currentValue = monat;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().getMonthValue()==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
			for(int month = 1; month <= maxValue; month++){
				currentValue = month;
				counter = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getStartdate().toInstant().atZone(defaultZoneId).toLocalDate().getMonthValue()==currentValue){
						counter++;
					}
				});
				json.put(new JSONObject()
						.put("id", currentValue)
						.put("value", this.counter));
			}
		}
		return json.toString();
	}
	
	public Set<String> listeEinlieferungsarten(){
		Set<String> listeEinlieferungsarten = new HashSet<>();
		this.aufenthaltMap.forEach((String, Aufenthalt) -> {
				listeEinlieferungsarten.add(Aufenthalt.getEinweisungsart());
		});
		return listeEinlieferungsarten;
	}
	
	public String countEinlieferungsarten(){
		JSONArray json = new JSONArray();
		Iterator <String> it = this.listeEinlieferungsarten().iterator();
		while(it.hasNext()){
			String einlieferungsart = it.next();
			counter = 0;
			this.aufenthaltMap.forEach((String, Aufenthalt) -> {
				if(Aufenthalt.getEinweisungsart().equals(einlieferungsart)){
					counter++;
				}
			});
			json.put(new JSONObject()
					.put("id", einlieferungsart)
					.put("value", this.counter)
			);
		}
		return json.toString();
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
