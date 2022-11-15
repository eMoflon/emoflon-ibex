package org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface NodeCheck {
	boolean checkConstraint(EObject source);
}
