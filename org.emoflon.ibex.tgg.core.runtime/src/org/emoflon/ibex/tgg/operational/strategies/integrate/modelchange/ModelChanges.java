package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;

public class ModelChanges {

	private boolean modified;

	private Set<AttributeChange> attributeChanges;
	private Map<EObject, Set<AttributeChange>> mappedAttributeChanges;

	private Set<EObject> createdElements;
	private Set<EObject> deletedElements;
	private Map<EObject, Resource> containedInResource;

	private Set<EObject> newlyCreatedElements;
	private Set<EObject> newlyDeletedElements;

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
		this.containedInResource = new HashMap<>();

		this.newlyCreatedElements = new HashSet<>();
		this.newlyDeletedElements = new HashSet<>();

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
		if (!deletedElements.remove(createdElement)) {
			createdElements.add(createdElement);
			newlyCreatedElements.add(createdElement);
		} else
			containedInResource.remove(createdElement);
	}

	public void addDeletedElement(EObject deletedElement) {
		modified = true;
		if (!createdElements.remove(deletedElement)) {
			deletedElements.add(deletedElement);
			newlyDeletedElements.add(deletedElement);
		}
	}

	public void addDelEltContainedInRes(EObject deletedElement, Resource resource) {
		modified = true;
		if (!createdElements.remove(deletedElement)) {
			deletedElements.add(deletedElement);
			newlyDeletedElements.add(deletedElement);
			containedInResource.put(deletedElement, resource);
		}
	}

	public void addCreatedEdge(EMFEdge createdEdge) {
		modified = true;
		if (deletedEdges.contains(createdEdge)) {
			deletedEdges.remove(createdEdge);
			srcMappedDeletedEdges.get(createdEdge.getSource()).remove(createdEdge);
			trgMappedDeletedEdges.get(createdEdge.getTarget()).remove(createdEdge);
		} else {
			createdEdges.add(createdEdge);
			srcMappedCreatedEdges.computeIfAbsent(createdEdge.getSource(), k -> new HashSet<>());
			srcMappedCreatedEdges.get(createdEdge.getSource()).add(createdEdge);
			trgMappedCreatedEdges.computeIfAbsent(createdEdge.getTarget(), k -> new HashSet<>());
			trgMappedCreatedEdges.get(createdEdge.getTarget()).add(createdEdge);
		}
	}

	public void addDeletedEdge(EMFEdge deletedEdge) {
		modified = true;
		if (createdEdges.contains(deletedEdge)) {
			createdEdges.remove(deletedEdge);
			srcMappedCreatedEdges.get(deletedEdge.getSource()).remove(deletedEdge);
			trgMappedCreatedEdges.get(deletedEdge.getTarget()).remove(deletedEdge);
		} else {
			deletedEdges.add(deletedEdge);
			srcMappedDeletedEdges.computeIfAbsent(deletedEdge.getSource(), k -> new HashSet<>());
			srcMappedDeletedEdges.get(deletedEdge.getSource()).add(deletedEdge);
			trgMappedDeletedEdges.computeIfAbsent(deletedEdge.getTarget(), k -> new HashSet<>());
			trgMappedDeletedEdges.get(deletedEdge.getTarget()).add(deletedEdge);
		}
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
		cleanUp();
		return Collections.unmodifiableSet(attributeChanges);
	}

	public Set<EObject> getCreatedElements() {
		cleanUp();
		return Collections.unmodifiableSet(createdElements);
	}

	public Set<EObject> getDeletedElements() {
		cleanUp();
		return Collections.unmodifiableSet(deletedElements);
	}

	public Set<EMFEdge> getCreatedEdges() {
		cleanUp();
		return Collections.unmodifiableSet(createdEdges);
	}

	public Set<EMFEdge> getDeletedEdges() {
		cleanUp();
		return Collections.unmodifiableSet(deletedEdges);
	}

	/* GET RAW */

	Set<AttributeChange> getRawAttributeChanges() {
		return attributeChanges;
	}

	Set<EObject> getRawCreatedElements() {
		return createdElements;
	}

	Set<EObject> getRawDeletedElements() {
		return deletedElements;
	}

	Set<EMFEdge> getRawCreatedEdges() {
		return createdEdges;
	}

	Set<EMFEdge> getRawDeletedEdges() {
		return deletedEdges;
	}

	/* BOOLEAN QUERIES */

	public boolean hasAttributeChanges(EObject element) {
		cleanUp();
		return !mappedAttributeChanges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	public boolean isCreated(EObject element) {
		cleanUp();
		return createdElements.contains(element);
	}

	public boolean isDeleted(EObject element) {
		cleanUp();
		return deletedElements.contains(element);
	}
	
	public Resource containedInResource(EObject element) {
		cleanUp();
		return containedInResource.get(element);
	}

	public boolean isCreated(EMFEdge edge) {
		cleanUp();
		return createdEdges.contains(edge);
	}

	public boolean isDeleted(EMFEdge edge) {
		cleanUp();
		return deletedEdges.contains(edge);
	}

	public boolean hasCreatedEdges(EObject element) {
		cleanUp();
		return !srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()).isEmpty()
				|| !srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	public boolean hasDeletedEdges(EObject element) {
		cleanUp();
		return !srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()).isEmpty()
				|| !srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()).isEmpty();
	}

	/* GET QUERIES */

	public Set<AttributeChange> getAttributeChanges(EObject element) {
		cleanUp();
		return Collections.unmodifiableSet(mappedAttributeChanges.getOrDefault(element, Collections.emptySet()));
	}

	public Set<EMFEdge> getCreatedEdges(EObject element) {
		cleanUp();
		Set<EMFEdge> result = new HashSet<>();
		result.addAll(srcMappedCreatedEdges.getOrDefault(element, Collections.emptySet()));
		result.addAll(trgMappedCreatedEdges.getOrDefault(element, Collections.emptySet()));
		return Collections.unmodifiableSet(result);
	}

	public Set<EMFEdge> getDeletedEdges(EObject element) {
		cleanUp();
		Set<EMFEdge> result = new HashSet<>();
		result.addAll(srcMappedDeletedEdges.getOrDefault(element, Collections.emptySet()));
		result.addAll(trgMappedDeletedEdges.getOrDefault(element, Collections.emptySet()));
		return Collections.unmodifiableSet(result);
	}

	synchronized private void cleanUp() {
		if (modified) {
			detectAppendagesOfNewlyCreatedElements();
			newlyCreatedElements.clear();
			newlyDeletedElements.clear();
			modified = false;
		}
	}

	private void detectAppendagesOfNewlyCreatedElements() {
		Set<EObject> traversedElements = new HashSet<>();
		newlyCreatedElements.forEach(elt -> detectAppendages(traversedElements, elt));
	}

	@SuppressWarnings("unchecked")
	private void detectAppendages(Set<EObject> traversedElements, EObject createdElement) {
		if (traversedElements.add(createdElement)) {
			createdElement.eClass().getEAllReferences().forEach(feature -> {
				Object content = createdElement.eGet(feature);
				if (content instanceof Collection) {
					Collection<EObject> contentList = (Collection<EObject>) content;
					contentList.forEach(child -> {
						this.addCreatedEdge(new EMFEdge(createdElement, child, feature));
						if (feature.isContainment()) {
							this.addCreatedElement(child);
							detectAppendages(traversedElements, child);
						}
					});
				} else if (content instanceof EObject) {
					EObject child = (EObject) content;
					this.addCreatedEdge(new EMFEdge(createdElement, child, feature));
					if (feature.isContainment()) {
						this.addCreatedElement(child);
						detectAppendages(traversedElements, child);
					}
				}
			});
		}
	}

}
