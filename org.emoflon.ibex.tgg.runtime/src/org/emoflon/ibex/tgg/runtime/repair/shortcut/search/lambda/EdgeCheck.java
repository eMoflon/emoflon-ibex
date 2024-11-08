package org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface EdgeCheck {
	boolean checkConstraint(EObject source, EObject target);
}
