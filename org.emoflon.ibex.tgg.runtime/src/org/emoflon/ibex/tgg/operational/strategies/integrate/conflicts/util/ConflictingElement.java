package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util;

import org.eclipse.emf.ecore.EObject;

public abstract class ConflictingElement {

	private final EObject element;

	public ConflictingElement(EObject element) {
		this.element = element;
	}

	public EObject getElement() {
		return element;
	}

}
