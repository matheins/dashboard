package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import entity.Aufenthalt;

public class AufenthaltServiceMapImpl implements AufenthaltService{
	private HashMap<String, Aufenthalt> aufenthaltMap;
	private int countDringlichkeit = 0;
	
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
	
	//die naechsten drei Methoden funktionieren alle nicht, da innerhalb der foreach-Schleife wohl eine neue
	//"interne" Klasse erzeugt wird und die lokale Variable count/countDringlichkeit nicht zugreifbar ist.
	//Hilfe von Herrn Rauch erbeten.
	public HashMap<Integer,Integer> countDringlichkeit(){
		HashMap<Integer, Integer> map = new HashMap<>();
		
			for(int dringlichkeit = 0; dringlichkeit <= 5; dringlichkeit++){
				int count = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getDringlichkeit()==dringlichkeit){
						count++;
					}
				map.put(dringlichkeit, count);
				});
			}
		return map;
	}
	
	public HashMap<Integer,Integer> countDringlichkeitClassVariable(){
		HashMap<Integer, Integer> map = new HashMap<>();
		
			for(int dringlichkeit = 0; dringlichkeit <= 5; dringlichkeit++){
				countDringlichkeit = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getDringlichkeit()==dringlichkeit){
						this.countDringlichkeit++;
					}
				map.put(dringlichkeit, countDringlichkeit);
				});
			}
		return map;
	}
	
	public ArrayList<Integer> countDringlichkeitArrayList(){
		ArrayList<Integer> list = new ArrayList<>();
		
			for(int dringlichkeit = 0; dringlichkeit <= 5; dringlichkeit++){
				int count = 0;
				this.aufenthaltMap.forEach((String, Aufenthalt) -> {
					if(Aufenthalt.getDringlichkeit()==dringlichkeit){
						count++;
					}
				list.add(count);
				});
			}
		return list;
	}

}
