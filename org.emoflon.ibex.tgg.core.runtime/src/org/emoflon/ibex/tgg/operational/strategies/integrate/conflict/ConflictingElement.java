package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;

public class ConflictingElement {

	private EObject element;
	private Set<EMFEdge> createdEdges;
	private Set<AttributeChange> attributeChanges;

	public ConflictingElement(EObject element, Set<EMFEdge> createdEdges, Set<AttributeChange> attributeChanges) {
		this.element = element;
		this.createdEdges = createdEdges;
		this.attributeChanges = attributeChanges;
	}

	public EObject getElement() {
		return element;
	}

	public Set<EMFEdge> getCreatedEdges() {
		return createdEdges;
	}

	public Set<AttributeChange> getAttributeChanges() {
		return attributeChanges;
	}

}
