package org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

public class AttributeChange {

	private EObject element;
	private EAttribute attribute;

	private Object oldValue;
	private Object newValue;

	public AttributeChange(EObject element, EAttribute attribute, Object oldValue, Object newValue) {
		this.element = element;
		this.attribute = attribute;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public EObject getElement() {
		return element;
	}

	public EAttribute getAttribute() {
		return attribute;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

}
