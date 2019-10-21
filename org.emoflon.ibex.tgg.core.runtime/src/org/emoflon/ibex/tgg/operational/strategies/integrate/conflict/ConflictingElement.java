package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

public class ConflictingElement {

	private EObject element;
	private List<Notification> additions;
	private List<Notification> changes;
	private List<Notification> crossRefs;

	ConflictingElement(EObject element, List<Notification> additions, List<Notification> changes,
			List<Notification> crossRefs) {
		this.element = element;
		this.additions = additions;
		this.changes = changes;
		this.crossRefs = crossRefs;
	}

	public EObject getElement() {
		return element;
	}

	public List<Notification> getAdditions() {
		return additions;
	}

	public List<Notification> getChanges() {
		return changes;
	}

	public List<Notification> getCrossRefs() {
		return crossRefs;
	}

}
