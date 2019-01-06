package services;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.gson.JsonElement;

import entity.Aufenthalt;
import entity.Diagnose;

public interface IDiagnoseService {

	public void addDiagnose(Diagnose diagnose);
	public Collection<Diagnose> getDiagnose();
	public Collection<Diagnose> getDiagnosen();
	public Diagnose getDiagnose(String id);
	public List<Diagnose> getDiagnosenPaginiert(int start, int size);
	public boolean diagnoseExists(String id);
	public HashMap<String, Diagnose> getDiagnoseMap();
	

}
