package org.emoflon.ibex.tgg.operational.strategies.integrate;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.MatchClassificationComponent;

public class Mismatch {
	
	private final IMatch brokenMatch;
	private final MatchClassificationComponent integrationFragment;
	private final Map<EObject, EltClassifier> classifiedElts;

	public Mismatch(IMatch brokenMatch, MatchClassificationComponent integrationFragment) {
		this.brokenMatch = brokenMatch;
		this.integrationFragment = integrationFragment;
		classifiedElts = new HashMap<>();
	}
	
	public IMatch getBrokenMatch() {
		return brokenMatch;
	}

	public MatchClassificationComponent getIF() {
		return integrationFragment;
	}

	public Map<EObject, EltClassifier> getClassifiedElts() {
		return classifiedElts;
	}

	public void addElement(EObject element, EltClassifier classification) {
		classifiedElts.put(element, classification);
	}

}
