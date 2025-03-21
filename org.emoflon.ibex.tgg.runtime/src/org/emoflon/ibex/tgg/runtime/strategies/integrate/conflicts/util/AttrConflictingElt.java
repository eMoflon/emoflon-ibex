package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.util;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.AttributeChange;

public class AttrConflictingElt extends ConflictingElement {
	
	private final Set<AttributeChange> attributeChanges;

	public AttrConflictingElt(EObject element, Set<AttributeChange> attributeChanges) {
		super(element);
		this.attributeChanges = attributeChanges;
	}
	
	public Set<AttributeChange> getAttributeChanges() {
		return attributeChanges;
	}

}
