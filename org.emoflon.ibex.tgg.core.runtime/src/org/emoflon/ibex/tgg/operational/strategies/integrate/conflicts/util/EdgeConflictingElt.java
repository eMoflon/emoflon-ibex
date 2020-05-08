package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;

public class EdgeConflictingElt extends ConflictingElement {
	
	private final Set<EMFEdge> createdEdges;

	public EdgeConflictingElt(EObject element, Set<EMFEdge> createdEdges) {
		super(element);
		this.createdEdges = createdEdges;
	}
	
	public Set<EMFEdge> getCreatedEdges() {
		return createdEdges;
	}

}
