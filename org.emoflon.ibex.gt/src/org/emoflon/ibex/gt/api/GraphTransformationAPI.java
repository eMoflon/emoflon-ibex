package org.emoflon.ibex.gt.api;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.gt.engine.GTEngine;

/**
 * This abstract API is the super class for all concrete APIs generated for a
 * set of models.
 * 
 * Concrete Implementations must implement loading rules from .gt files into the
 * engine.
 */
public abstract class GraphTransformationAPI {
	/**
	 * The interpreter to use for queries and transformations.
	 */
	protected GraphTransformationInterpreter interpreter;

	/**
	 * Creates a new GraphTransformationAPI for given engine and resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing the model file
	 */
	public GraphTransformationAPI(final GTEngine engine, final ResourceSet model) {
		this.interpreter = new GraphTransformationInterpreter(engine, model);
	}

	/**
	 * Returns the resource set opened for transformations with the API.
	 * 
	 * @return the resource set containing the model file
	 */
	public final ResourceSet getModel() {
		return this.interpreter.getModel();
	}
}
