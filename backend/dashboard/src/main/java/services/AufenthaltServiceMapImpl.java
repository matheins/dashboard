package services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import comparator.impl.NumberAwareStringComparator;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

import converter.IStringToDate;
import converter.impl.StringToDate;
import entity.Aufenthalt;
import entity.Diagnose;

public class AufenthaltServiceMapImpl implements IAufenthaltService{
	IStringToDate strtoD = new StringToDate();
	private Date minDate;
	private Date minDateOfYear;
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
	IDiagnoseService ds = new DiagnoseServiceMapImpl(); 
	
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
		if(minValue<10){
			minValue=10;
		}
		if(maxValue > 100){
			maxValue = 100;
		}
		
		for(int alter = minValue; alter <= maxValue; alter = alter+10){//currentValue = alter = alter+10){
			currentValue = alter;
			counter = 0;
			this.aufenthaltMap.forEach((String, Aufenthalt) -> {
				if(Aufenthalt.getAlter()>=currentValue && Aufenthalt.getAlter() < currentValue + 10 && currentValue < 100){
					counter++;
				} else if(currentValue==100 && Aufenthalt.getAlter()>=100){
					counter++;
				}
					
			});
			json.put(new JSONObject()
					.put("id", alter)
					.put("value", this.counter));
		}
		return json.toString();
	}

	public String countAufenthaltNachZeiteinheit(Date vonDatum, Date bisDatum, String zeiteinheit){
		if(zeiteinheit.equals("Tage")){
			JSONArray json = new JSONArray();
			temporalField = week.weekOfWeekBasedYear();
	
			Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
					.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum));
			SortedMap<String, Map<LocalDate, Long>> mapGroupedByDay = new ConcurrentSkipListMap<String, Map<LocalDate, Long>>();
			mapGroupedByDay.putAll(stream.collect(Collectors.groupingBy(
					(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
										Aufenthalt::getLocalDate, Collectors.counting()))));
				
			mapGroupedByDay.forEach((einweisungsart, map) -> {
					JSONArray jsonE = new JSONArray();
					jsonE.put(new JSONObject().put("einweisungsart", einweisungsart));
					JSONArray jsonE2 = new JSONArray();
					//map ist keine sortierte Map. Daher uebertrage hier die Daten von map zu mapSorted.
					TreeMap<LocalDate, Long> mapSorted = new TreeMap<LocalDate, Long>(); mapSorted.putAll(map);
					mapSorted.forEach((datum, anzahl) ->{
							jsonE2.put(new JSONObject().put("datum", datum)
													.put("anzahl", anzahl));
					});
					jsonE.put(jsonE2);
					json.put(jsonE);
			});
			return json.toString();
		} else if(zeiteinheit.equals("Monate")){
			JSONArray json = new JSONArray();
			temporalField = week.weekOfWeekBasedYear();

			Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
					.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum));

			SortedMap<String, Map<String, Long>> mapGroupedByMonth = new TreeMap<>();//ConcurrentSkipListMap<>();
			mapGroupedByMonth.putAll(stream.collect(Collectors.groupingBy(
						(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
										(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().getYear())+"_"+String.valueOf(aufenthalt.getLocalDate().getMonthValue()), Collectors.counting()))));
			
			mapGroupedByMonth.forEach((einweisungsart, map) -> {
					JSONArray jsonE = new JSONArray();
					jsonE.put(new JSONObject().put("einweisungsart", einweisungsart));
					JSONArray jsonE2 = new JSONArray();
					//map ist keine sortierte Map. Daher uebertrage hier die Daten von map zu mapSorted und sortiere sie mit einer Comparator-Klasse
					TreeMap<String, Long> mapSorted = new TreeMap<String, Long>(new NumberAwareStringComparator()); mapSorted.putAll(map);
					mapSorted.forEach((monat, anzahl) ->{
							jsonE2.put(new JSONObject().put("monat", monat)
													.put("anzahl", anzahl));
					});
					jsonE.put(jsonE2);
					json.put(jsonE);
				});
			return json.toString();
		} else if(zeiteinheit.equals("Wochen")){
			JSONArray json = new JSONArray();
			temporalField = week.weekOfWeekBasedYear();

			Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
					.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum));

			SortedMap<String, Map<String, Long>> mapGroupedByWeek = new TreeMap<>();//ConcurrentSkipListMap<>();
					mapGroupedByWeek.putAll(stream.collect(Collectors.groupingBy(
						(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
										(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().get(week.weekBasedYear()))+"_"+String.valueOf(aufenthalt.getLocalDate().get(temporalField)), Collectors.counting()))));
				
				mapGroupedByWeek.forEach((einweisungsart, map) -> {
					JSONArray jsonE = new JSONArray();
					jsonE.put(new JSONObject().put("einweisungsart", einweisungsart));
					JSONArray jsonE2 = new JSONArray();
					//map ist keine sortierte Map. Daher uebertrage hier die Daten von map zu mapSorted und sortiere sie mit einer Comparator-Klasse
					TreeMap<String, Long> mapSorted = new TreeMap<String, Long>(new NumberAwareStringComparator()); mapSorted.putAll(map);
					mapSorted.forEach((woche, anzahl) ->{
						jsonE2.put(new JSONObject().put("woche", woche)
												.put("anzahl", anzahl));
					});
					jsonE.put(jsonE2);
					json.put(jsonE);
				});

			return json.toString();
		} else {
			return "Fehler! Die Zeiteinheit gibt es nicht!";
		}
	}
	
	public String countAufenthaltNachTage(Date vonDatum, Date bisDatum, String einweisungsart){
		JSONArray json = new JSONArray();
		temporalField = week.weekOfWeekBasedYear();

		Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
				.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum))
				.filter(aufenthalt -> aufenthalt.getEinweisungsart().equals(einweisungsart));
//		if(fasseEinweisungsartenZusammen){
			SortedMap<LocalDate, Long> mapGroupedByDay = new ConcurrentSkipListMap<>(Comparator.naturalOrder());
			mapGroupedByDay.putAll(stream.collect(Collectors.groupingBy(
										Aufenthalt::getLocalDate, Collectors.counting())));
			
//			json.put(mapGroupedByDay);
/*		} else{
			SortedMap<String, Map<LocalDate, Long>> mapGroupedByDay = new ConcurrentSkipListMap<>(Comparator.naturalOrder());
			mapGroupedByDay.putAll(stream.collect(Collectors.groupingBy(
						(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
										Aufenthalt::getLocalDate, Collectors.counting()))));
			
			json.put(mapGroupedByDay);
		}*/
		mapGroupedByDay.forEach((datum, anzahl) -> {
			json.put(new JSONObject()
					.put("datum", datum)
					.put("anzahl", anzahl)
					.put("einweisungsart", einweisungsart));
		});
		return json.toString();
		
		}

	public String countAufenthaltNachWochen(Date vonDatum, Date bisDatum, String einweisungsart){
		JSONArray json = new JSONArray();
		temporalField = week.weekOfWeekBasedYear();

		Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
				.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum))
				.filter(aufenthalt -> aufenthalt.getEinweisungsart().equals(einweisungsart));
		
//		if(fasseEinweisungsartenZusammen){
			SortedMap<String, Long> mapGroupedByWeek = new TreeMap<>();//ConcurrentSkipListMap<>();
			mapGroupedByWeek.putAll(stream.collect(Collectors.groupingBy(
									(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().get(week.weekBasedYear()))+"_"+String.valueOf(aufenthalt.getLocalDate().get(temporalField)), Collectors.counting())));
			
			mapGroupedByWeek.forEach((datum, anzahl) -> {
				json.put(new JSONObject()
						.put("datum", datum)
						.put("anzahl", anzahl)
						.put("einweisungsart", einweisungsart));
			});

/*		} else{
			SortedMap<String, Map<String, Long>> mapGroupedByWeek = new TreeMap<>();//ConcurrentSkipListMap<>();
			mapGroupedByWeek.putAll(stream.collect(Collectors.groupingBy(
						(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
									(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().get(week.weekBasedYear()))+"_"+String.valueOf(aufenthalt.getLocalDate().get(temporalField)), Collectors.counting()))));
			
			json.put(mapGroupedByWeek);
		}*/
		return json.toString();
		
		}
	
	public String countAufenthaltNachMonaten(Date vonDatum, Date bisDatum, String einweisungsart){
		JSONArray json = new JSONArray();
		temporalField = week.weekOfWeekBasedYear();

		Stream <Aufenthalt> stream = aufenthaltMap.values().stream()
				.filter(aufenthalt -> aufenthalt.getStartdate().after(vonDatum) && aufenthalt.getStartdate().before(bisDatum))
				.filter(aufenthalt -> aufenthalt.getEinweisungsart().equals(einweisungsart));
		
//		if(fasseEinweisungsartenZusammen){
			SortedMap<String, Long> mapGroupedByMonth = new TreeMap<>();//ConcurrentSkipListMap<>();
			mapGroupedByMonth.putAll(stream.collect(Collectors.groupingBy(
											(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().getYear())+"_"+String.valueOf(aufenthalt.getLocalDate().getMonthValue()), Collectors.counting())));
			mapGroupedByMonth.forEach((datum, anzahl) -> {
				json.put(new JSONObject()
						.put("datum", datum)
						.put("anzahl", anzahl)
						.put("einweisungsart", einweisungsart));
			});
/*		} else{
			SortedMap<String, Map<String, Long>> mapGroupedByWeek = new TreeMap<>();//ConcurrentSkipListMap<>();
			mapGroupedByWeek.putAll(stream.collect(Collectors.groupingBy(
									(aufenthalt) -> aufenthalt.getEinweisungsart(), Collectors.groupingBy(
											(aufenthalt) -> String.valueOf(aufenthalt.getLocalDate().getYear())+"_"+String.valueOf(aufenthalt.getLocalDate().getMonthValue()), Collectors.counting()))));
			json.put(mapGroupedByWeek);
		}*/
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

	//aggregieren nach Tagen
	@Override
	public ArrayList<Aufenthalt> getAufenthaltNachZeit(Date start, Date end) {
		ArrayList<Aufenthalt> list = new ArrayList<Aufenthalt>(aufenthaltMap.values());
		ArrayList<Aufenthalt> resultListDates = new ArrayList<>();
//		ArrayList<Aufenthalt> resultListCount = new ArrayList<>();
		
		for(Aufenthalt i : list){
			if(i.getStartdate().after(start) && i.getStartdate().before(end)){ // Aufenthalte zwischen den start-&enddatum filtern
				resultListDates.add(i);
			}
		} 
		
//		for (Aufenthalt j : resultListDates) {
//			for (Aufenthalt k: resultListCount) {
//			 if(j.getStartdate() != k.getStartdate() ) { //überprüfung ob datum in der Liste schon vrohanden ist
//				 resultListCount.add(j); 				//datum wird hinzugefügt, falls noch nicht vorhanden ist
//			 }  
//			}
//			
//		}
		
//		ArrayList<Diagnose> diagnoseList = new ArrayList<Diagnose>(ds.getDiagnoseMap().values());
//		for (Aufenthalt i: resultList) {
//			for(Diagnose j: diagnoseList) {
//				if (i.getAufenthaltID() == j.getDiagnoseID()) {	
//				} resultList2.add(j);
//			}
//		}
		return resultListDates;
	}

	
	//aggregieren nach den 5 einlieferungsarten 
	@Override
	public ArrayList<Aufenthalt> getAufenthaltNachTyp(String einlieferungsart) { 
		ArrayList<Aufenthalt> list = new ArrayList<Aufenthalt>(aufenthaltMap.values());
		ArrayList<Aufenthalt> resultList = new ArrayList<>();
		List<Diagnose> resultList2 = new ArrayList<>();
		for(Aufenthalt i : list){
			if(i.getEinweisungsart().equals(einlieferungsart)){
				resultList.add(i);
			}
		} 
		
//		ArrayList<Diagnose> diagnoseList = new ArrayList<Diagnose>(ds.getDiagnoseMap().values());
//		for (Aufenthalt i: resultList) {
//			for(Diagnose j: diagnoseList) {
//				if (i.getAufenthaltID() == j.getDiagnoseID()) {	
//				} resultList2.add(j);
//			}
//		}
//		
//		return resultList2;
		return resultList;
	}

	

	@Override
	public List<Aufenthalt> getAufenthaltNachZeitUndTyp(Date start, Date end, String einlieferungsart) {
		ArrayList<Aufenthalt> list = new ArrayList<Aufenthalt>(aufenthaltMap.values());
		ArrayList<Aufenthalt> resultList = new ArrayList<>();
//		List<Diagnose> resultList2 = new ArrayList<>();
		for(Aufenthalt i : list){
			if(i.getEinweisungsart().equals(einlieferungsart) && i.getStartdate().after(start) && i.getStartdate().before(end)){
				resultList.add(i);
			}
		} 
		
//		ArrayList<Diagnose> diagnoseList = new ArrayList<Diagnose>(ds.getDiagnoseMap().values());
//		for (Aufenthalt i: resultList) {
//			for(Diagnose j: diagnoseList) {
//				if (i.getAufenthaltID() == j.getDiagnoseID()) {	
//				} resultList2.add(j);
//			}
//		}
//		
//		return resultList2;
		return resultList;
	}


	
	
	
	
}
