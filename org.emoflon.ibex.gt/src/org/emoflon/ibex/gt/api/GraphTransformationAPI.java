package org.emoflon.ibex.gt.api;

import java.util.Objects;

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
		this.initialize(engine, model, null);
	}

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing the model file
	 * @param debugPath
	 *            the path for the debugging output
	 */
	public GraphTransformationAPI(final GTEngine engine, final ResourceSet model, final String debugPath) {
		Objects.requireNonNull(debugPath, "The debug path must not be null!");
		this.initialize(engine, model, debugPath);
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
		this.initialize(engine, filePath, null);
	}

	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * model file.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param filePath
	 *            the path to the model file
	 * @param debugPath
	 *            the path for the debugging output
	 */
	public GraphTransformationAPI(final GTEngine engine, final String filePath, final String debugPath) {
		Objects.requireNonNull(debugPath, "The debug path must not be null!");
		this.initialize(engine, filePath, debugPath);
	}

	private void initialize(final GTEngine engine, final ResourceSet model, final String debugPath) {
		this.engine = engine;
		this.engine.setDebug(debugPath);
		this.model = model;
	}

	private void initialize(final GTEngine engine, final String filePath, final String debugPath) {
		ResourceSetImpl model = new ResourceSetImpl();
		model.createResource(URI.createURI(filePath));
		this.initialize(engine, model, debugPath);
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
