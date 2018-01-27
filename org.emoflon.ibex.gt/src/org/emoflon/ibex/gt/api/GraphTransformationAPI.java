package org.emoflon.ibex.gt.api;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.ibex.gt.engine.GTEngine;

/**
 * This abstract API is the super class for all concrete APIs generated for a
 * set of models.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GraphTransformationAPI {
	protected GTEngine engine;
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
		this.engine = engine;
		this.model = model;
	}
	
	/**
	 * Creates a new GraphTransformationAPI with the given engine for the given
	 * model file.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param filePath
	 *            the resource set containing the model file
	 */
	public GraphTransformationAPI(final GTEngine engine, final String filePath) {
		this.engine = engine;
		ResourceSetImpl model = new ResourceSetImpl();
		model.createResource(URI.createURI(filePath));
		this.model = model;
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
