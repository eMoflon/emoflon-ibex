package org.emoflon.ibex.gt.api;

import java.io.File;

import org.emoflon.ibex.gt.engine.GTEngine;

/**
 * This abstract API is the super class for all concrete APIs.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GraphTransformationAPI {
	protected GTEngine engine;
	protected File model;

	/**
	 * Creates a new GraphTransformationAPI for the given model file.
	 * 
	 * @param model
	 *            the model file
	 */
	public GraphTransformationAPI(final GTEngine engine, final File model) {
		this.engine = engine;
		this.model = model;
	}

	/**
	 * Returns the file opened for transformations with the API.
	 * 
	 * @return the model file.
	 */
	public File getModel() {
		return model;
	}
}
