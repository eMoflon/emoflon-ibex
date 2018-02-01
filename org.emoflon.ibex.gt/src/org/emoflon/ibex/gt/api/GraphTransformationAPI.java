package org.emoflon.ibex.gt.api;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.ibex.gt.engine.GTEngine;

/**
 * This abstract API is the super class for all concrete APIs generated for a
 * set of models.
 * 
 * Concrete Implementations must implement loading rules from .gt files into the
 * engine.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GraphTransformationAPI {
	/**
	 * The engine to use for queries and transformations.
	 */
	protected GTEngine engine;

	/**
	 * The resource set containing the model file.
	 */
	protected ResourceSet model;

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing the model file
	 */
	public GraphTransformationAPI(final GTEngine engine, final ResourceSet model) {
		this.initialize(engine, model, false);
	}

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing the model file
	 * @param debug
	 *            <code>true</code> enables debugging, <code>false</code> to disable
	 */
	public GraphTransformationAPI(final GTEngine engine, final ResourceSet model, final boolean debug) {
		this.initialize(engine, model, debug);
	}

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * model file.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param filePath
	 *            the path to the model file
	 */
	public GraphTransformationAPI(final GTEngine engine, final String filePath) {
		this.initialize(engine, filePath, false);
	}

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * model file.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param filePath
	 *            the path to the model file
	 * @param debug
	 *            <code>true</code> enables debugging, <code>false</code> to disable
	 */
	public GraphTransformationAPI(final GTEngine engine, final String filePath, final boolean debug) {
		this.initialize(engine, filePath, debug);
	}

	private void initialize(final GTEngine engine, final ResourceSet model, final boolean debug) {
		this.engine = engine;
		this.engine.setDebug(debug);
		this.model = model;
	}

	private void initialize(final GTEngine engine, final String filePath, final boolean debug) {
		ResourceSetImpl model = new ResourceSetImpl();
		model.createResource(URI.createURI(filePath));
		this.initialize(engine, model, debug);
	}

	/**
	 * Returns the resource set opened for transformations with the API.
	 * 
	 * @return the resource set containing the model file
	 */
	public ResourceSet getModel() {
		return model;
	}
}
