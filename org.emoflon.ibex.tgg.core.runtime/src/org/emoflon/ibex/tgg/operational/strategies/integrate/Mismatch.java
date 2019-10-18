package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.fragments.MatchIntegrationFragment;

public class Mismatch {
	
	private final IMatch brokenMatch;
	private final MatchIntegrationFragment integrationFragment;
	private final Map<EObject, ProcessState> classifiedElts;

	public Mismatch(IMatch brokenMatch, MatchIntegrationFragment integrationFragment) {
		this.brokenMatch = brokenMatch;
		this.integrationFragment = integrationFragment;
		classifiedElts = new HashMap<>();
	}
	
	public IMatch getBrokenMatch() {
		return brokenMatch;
	}

	public MatchIntegrationFragment getIF() {
		return integrationFragment;
	}

	public Map<EObject, ProcessState> getClassifiedElts() {
		return classifiedElts;
	}

	public void addElement(EObject element, ProcessState classification) {
		classifiedElts.put(element, classification);
	}

}
