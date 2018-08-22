package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.lambda;

import org.eclipse.emf.ecore.EObject;

public interface NACNodeCheck {
	boolean checkConstraint(EObject source);
}
