package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.SimpleMatch;

/**
 * Represents a match for a operationalized shortcut rule application
 * 
 * @author lfritsche
 *
 */
public class SCMatch extends SimpleMatch {

	public SCMatch(String patternName, Map<String, EObject> name2candidates) {
		super(patternName);
		initialize(name2candidates);
	}
	
	private void initialize(Map<String, EObject> name2candidates) {
		for(String name : name2candidates.keySet()) {
			put(name, name2candidates.get(name));
		}
	}
}
