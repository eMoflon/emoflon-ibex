package org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public interface NACNodeCheck {
	boolean checkConstraint(EObject source, Set<EObject> currentCandidates);
}
