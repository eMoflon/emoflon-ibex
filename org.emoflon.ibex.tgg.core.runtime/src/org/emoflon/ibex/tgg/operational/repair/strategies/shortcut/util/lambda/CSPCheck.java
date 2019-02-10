package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface CSPCheck {
	boolean checkConstraint(Map<String, EObject> name2candidates);
}
