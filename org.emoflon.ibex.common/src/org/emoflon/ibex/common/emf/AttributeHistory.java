package org.emoflon.ibex.common.emf;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

public class AttributeHistory {
	final public EObject node;
	final public EAttribute feature;
	final public Object initialValue;
	final Collection<Object> changes = Collections.synchronizedList(new LinkedList<>());
	public Object newValue;

	public AttributeHistory(final EObject node, final EAttribute feature, final Object initialValue) {
		this.node = node;
		this.feature = feature;
		this.initialValue = initialValue;
	}

	public AttributeHistory(final EObject node, final EAttribute feature, final Object initialValue,
			final Object newValue) {
		this.node = node;
		this.feature = feature;
		this.initialValue = initialValue;
		changes.add(newValue);
		this.newValue = newValue;
	}

	public void addNewValue(final Object newValue) {
		changes.add(newValue);
		this.newValue = newValue;
	}
}
