package services;


import java.util.Collection;
import java.util.List;

import entity.Diagnose;

public interface IDiagnoseService {

	public void addDiagnose(Diagnose diagnose);
	public Collection<Diagnose> getDiagnose();
	public Collection<Diagnose> getDiagnosen();
	public Diagnose getDiagnose(String id);
	public List<Diagnose> getDiagnosenPaginiert(int start, int size);
	public boolean diagnoseExists(String id);

}
