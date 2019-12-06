package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;

public class ModelChanges {

	private boolean modified;

	private Set<AttributeChange> attributeChanges;
	private Map<EObject, Set<AttributeChange>> mappedAttributeChanges;

	private Set<EObject> createdElements;
	private Set<EObject> deletedElements;

	private Set<EMFEdge> createdEdges;
	private Map<EObject, Set<EMFEdge>> srcMappedCreatedEdges;
	private Map<EObject, Set<EMFEdge>> trgMappedCreatedEdges;

	private Set<EMFEdge> deletedEdges;
	private Map<EObject, Set<EMFEdge>> srcMappedDeletedEdges;
	private Map<EObject, Set<EMFEdge>> trgMappedDeletedEdges;

	public ModelChanges() {
		this.modified = false;

		this.attributeChanges = new HashSet<>();
		this.mappedAttributeChanges = new HashMap<>();

		this.createdElements = new HashSet<>();
		this.deletedElements = new HashSet<>();

		this.createdEdges = new HashSet<>();
		this.srcMappedCreatedEdges = new HashMap<>();
		this.trgMappedCreatedEdges = new HashMap<>();

		this.deletedEdges = new HashSet<>();
		this.srcMappedDeletedEdges = new HashMap<>();
		this.trgMappedDeletedEdges = new HashMap<>();
	}

	/* ADD */

	public void addAttributeChange(AttributeChange attributeChange) {
		modified = true;
		attributeChanges.add(attributeChange);
		mappedAttributeChanges.computeIfAbsent(attributeChange.getElement(), k -> new HashSet<>());
		mappedAttributeChanges.get(attributeChange.getElement()).add(attributeChange);
	}

	public void addCreatedElement(EObject createdElement) {
		modified = true;
		createdElements.add(createdElement);
	}

	public void addDeletedElement(EObject deletedElement) {
		modified = true;
		deletedElements.add(deletedElement);
	}

	public void addCreatedEdge(EMFEdge createdEdge) {
		modified = true;
		createdEdges.add(createdEdge);
		srcMappedCreatedEdges.computeIfAbsent(createdEdge.getSource(), k -> new HashSet<>());
		srcMappedCreatedEdges.get(createdEdge.getSource()).add(createdEdge);
		trgMappedCreatedEdges.computeIfAbsent(createdEdge.getTarget(), k -> new HashSet<>());
		trgMappedCreatedEdges.get(createdEdge.getTarget()).add(createdEdge);
	}

	public void addDeletedEdge(EMFEdge deletedEdge) {
		modified = true;
		deletedEdges.add(deletedEdge);
		srcMappedDeletedEdges.computeIfAbsent(deletedEdge.getSource(), k -> new HashSet<>());
		srcMappedDeletedEdges.get(deletedEdge.getSource()).add(deletedEdge);
		trgMappedDeletedEdges.computeIfAbsent(deletedEdge.getTarget(), k -> new HashSet<>());
		trgMappedDeletedEdges.get(deletedEdge.getTarget()).add(deletedEdge);
	}

	/* ADDALL */

	public void addAllAttributeChanges(Collection<AttributeChange> attributeChanges) {
		attributeChanges.forEach(ac -> this.addAttributeChange(ac));
	}

	public void addAllCreatedElements(Collection<EObject> createdElements) {
		createdElements.forEach(ce -> this.addCreatedElement(ce));
	}

	public void addAllDeletedElements(Collection<EObject> deletedElements) {
		deletedElements.forEach(de -> this.addDeletedElement(de));
	}

	public void addAllCreatedEdges(Collection<EMFEdge> createdEdges) {
		createdEdges.forEach(ce -> this.addCreatedEdge(ce));
	}

	public void addAllDeletedEdges(Collection<EMFEdge> deletedEdges) {
		deletedEdges.forEach(de -> this.addDeletedEdge(de));
	}

	/* GET */

	public Set<AttributeChange> getAttributeChanges() {
		clean();
		return Collections.unmodifiableSet(attributeChanges);
	}

	public Set<EObject> getCreatedElements() {
		clean();
		return Collections.unmodifiableSet(createdElements);
	}

	public Set<EObject> getDeletedElements() {
		clean();
		return Collections.unmodifiableSet(deletedElements);
	}

	public Set<EMFEdge> getCreatedEdges() {
		clean();
		return Collections.unmodifiableSet(createdEdges);
	}

	public Set<EMFEdge> getDeletedEdges() {
		clean();
		return Collections.unmodifiableSet(deletedEdges);
	}

	/* BOOLEAN QUERIES */

	public boolean hasAttributeChanges(EObject element) {
		clean();
		return !mappedAttributeChanges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	public boolean isCreated(EObject element) {
		clean();
		return createdElements.contains(element);
	}

	public boolean isDeleted(EObject element) {
		clean();
		return deletedElements.contains(element);
	}

	public boolean isCreated(EMFEdge edge) {
		clean();
		return createdEdges.contains(edge);
	}

	public boolean isDeleted(EMFEdge edge) {
		clean();
		return deletedEdges.contains(edge);
	}

	public boolean hasCreatedEdges(EObject element) {
		clean();
		return !srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()).isEmpty()
				|| !srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	public boolean hasDeletedEdges(EObject element) {
		clean();
		return !srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()).isEmpty()
				|| !srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	/* GET QUERIES */

	public Set<AttributeChange> getAttributeChanges(EObject element) {
		clean();
		return Collections.unmodifiableSet(mappedAttributeChanges.getOrDefault(element, Collections.emptySet()));
	}

	public Set<EMFEdge> getCreatedEdges(EObject element) {
		clean();
		Set<EMFEdge> result = new HashSet<>();
		result.addAll(srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()));
		result.addAll(trgMappedCreatedEdges.getOrDefault(element, Collections.emptySet()));
		return Collections.unmodifiableSet(result);
	}

	public Set<EMFEdge> getDeletedEdges(EObject element) {
		clean();
		Set<EMFEdge> result = new HashSet<>();
		result.addAll(srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()));
		result.addAll(trgMappedDeletedEdges.getOrDefault(element, Collections.emptySet()));
		return Collections.unmodifiableSet(result);
	}

	private void clean() {
		if (modified) {
			conflateEntries();
			modified = false;
		}
	}

	private void conflateEntries() {
		Iterator<EObject> eltIt = createdElements.iterator();
		while (eltIt.hasNext()) {
			EObject element = eltIt.next();
			if (deletedElements.contains(element)) {
				eltIt.remove();
				deletedElements.remove(element);
			}
		}

		Iterator<EMFEdge> edgeIt = createdEdges.iterator();
		while (edgeIt.hasNext()) {
			EMFEdge edge = edgeIt.next();
			if (deletedEdges.contains(edge)) {
				eltIt.remove();
				deletedEdges.remove(edge);
				srcMappedCreatedEdges.get(edge.getSource()).remove(edge);
				trgMappedCreatedEdges.get(edge.getTarget()).remove(edge);
				srcMappedDeletedEdges.get(edge.getSource()).remove(edge);
				trgMappedDeletedEdges.get(edge.getTarget()).remove(edge);
			}
		}
	}

}
