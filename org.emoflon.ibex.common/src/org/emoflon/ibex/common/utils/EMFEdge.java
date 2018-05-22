package org.emoflon.ibex.common.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * A representation for an edge in a EMF model.
 */
public class EMFEdge {
	private final EObject source;
	private final EObject target;
	private final EReference type;

	/**
	 * Creates an edge.
	 * 
	 * @param source
	 *            the source object
	 * @param target
	 *            the target object
	 * @param type
	 *            the edge type
	 */
	public EMFEdge(final EObject source, final EObject target, final EReference type) {
		this.source = source;
		this.target = target;
		this.type = type;
	}

	/**
	 * Returns the source.
	 * 
	 * @return the source object
	 */
	public EObject getSource() {
		return source;
	}

	/**
	 * Returns the target
	 * 
	 * @return the target object
	 */
	public EObject getTarget() {
		return target;
	}

	/**
	 * Returns the edge type.
	 * 
	 * @return the edge type
	 */
	public EReference getType() {
		return type;
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof EMFEdge) {
			EMFEdge e = (EMFEdge) o;
			return getSource().equals(e.getSource()) && getTarget().equals(e.getTarget())
					&& getType().equals(e.getType());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * source.hashCode() + 31 * target.hashCode() + type.hashCode();
	}

	@Override
	public String toString() {
		return source.toString() + " -- " + type.getName() + " -> " + target.toString();
	}
}
