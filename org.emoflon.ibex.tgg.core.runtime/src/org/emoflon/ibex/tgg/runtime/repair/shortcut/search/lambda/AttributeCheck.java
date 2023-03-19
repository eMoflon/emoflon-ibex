package org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface AttributeCheck {
	boolean checkAttributes(Map<String, EObject> name2candidates);
}
