package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;

public class ModelChanges {

	private Set<AttributeChange> attributeChanges;

	private Set<EObject> createdElements;
	private Set<EObject> deletedElements;

	private Set<EMFEdge> createdEdges;
	private Set<EMFEdge> deletedEdges;

	public ModelChanges() {
		this.attributeChanges = new HashSet<>();
		this.createdElements = new HashSet<>();
		this.deletedElements = new HashSet<>();
		this.createdEdges = new HashSet<>();
		this.deletedEdges = new HashSet<>();
	}

	public Set<AttributeChange> getAttributeChanges() {
		return attributeChanges;
	}

	public Set<EObject> getCreatedElements() {
		return createdElements;
	}

	public Set<EObject> getDeletedElements() {
		return deletedElements;
	}

	public Set<EMFEdge> getCreatedEdges() {
		return createdEdges;
	}

	public Set<EMFEdge> getDeletedEdges() {
		return deletedEdges;
	}

}
