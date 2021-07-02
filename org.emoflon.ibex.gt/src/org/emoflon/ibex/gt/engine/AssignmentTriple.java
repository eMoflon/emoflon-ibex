package org.emoflon.ibex.gt.engine;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * An AssignmentTriple consists of the object, the attribute type and the new
 * value for the attribute.
 */
public class AssignmentTriple {
	private final EObject object;
	private final EAttribute attribute;
	private final Object value;

	public AssignmentTriple(final EObject object, final EAttribute attribute, final Object value) {
		this.object = object;
		this.attribute = attribute;
		this.value = value;
	}

	public EObject getObject() {
		return object;
	}

	public EAttribute getAttribute() {
		return attribute;
	}

	public Object getValue() {
		return value;
	}
}
