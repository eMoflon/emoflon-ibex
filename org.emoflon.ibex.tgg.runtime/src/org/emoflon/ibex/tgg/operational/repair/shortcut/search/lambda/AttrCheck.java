package org.emoflon.ibex.tgg.operational.repair.shortcut.search.lambda;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface AttrCheck {
	boolean checkAttributes(EObject source, Map<String, EObject> candidates);
}
