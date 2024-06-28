package org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface Lookup {
	Object lookup(EObject source);
}
