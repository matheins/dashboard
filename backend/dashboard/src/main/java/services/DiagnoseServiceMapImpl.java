package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import entity.Aufenthalt;
import entity.Diagnose;

public class DiagnoseServiceMapImpl implements IDiagnoseService{
	private HashMap<String, Diagnose> diagnoseMap;
	

	public DiagnoseServiceMapImpl(){
		diagnoseMap = new HashMap<>();
	}
	
	public void addDiagnose(Diagnose diagnose){
		diagnoseMap.put(diagnose.getDiagnoseID(), diagnose);
	}
	
	public Collection<Diagnose> getDiagnosen(){
		return diagnoseMap.values();
	}
	
	public Diagnose getDiagnose(String id){
		return diagnoseMap.get(id);
	}
	
	public List<Diagnose> getDiagnosenPaginiert(int start, int size){
		ArrayList<Diagnose> list = new ArrayList<Diagnose>(diagnoseMap.values());
		if(start + size > list.size()) return new ArrayList<Diagnose>();
		return list.subList(start, start + size);
	}
	
	public boolean diagnoseExists(String id){
		return diagnoseMap.containsKey(id);
	}

	@Override
	public Collection<Diagnose> getDiagnose() {
		return diagnoseMap.values();
	}

	
	public HashMap<String, Diagnose> getDiagnoseMap() {
		return diagnoseMap;
	}

	public void setDiagnoseMap(HashMap<String, Diagnose> diagnoseMap) {
		this.diagnoseMap = diagnoseMap;
	}



}
