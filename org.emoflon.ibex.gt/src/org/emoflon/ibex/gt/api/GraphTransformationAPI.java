package org.emoflon.ibex.gt.api;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;


/**
 * This abstract API is the super class for all concrete APIs generated for a
 * set of model resources.
 * 
 * The concrete implementation provide methods to perform queries and rule
 * applications on the given resource set which must not be empty.
 * 
 * All elements created during rule applications are added to the default
 * resource. The default resource can be explicitly set in the constructor,
 * otherwise the first resource in the resource set will be used.
 * 
 * Per default, all rule applications use the single pushout approach. This
 * behavior can be changed for the whole API or for single rules.
 */
public abstract class GraphTransformationAPI {
	/**
	 * The interpreter to use for queries and transformations.
	 */
	protected GraphTransformationInterpreter interpreter;

	/**
	 * The pushout approach to use if no approach is specified.
	 */
	protected PushoutApproach defaultPushoutApproach = PushoutApproach.SPO;

	/**
	 * Creates a new GraphTransformationAPI for given engine and resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing at least one model resource
	 */
	public GraphTransformationAPI(final IContextPatternInterpreter engine, final ResourceSet model) {
		this.interpreter = new GraphTransformationInterpreter(engine, model);
	}

	/**
	 * Creates a new GraphTransformationAPI for given engine and resource set.
	 * 
	 * @param engine
	 *            the engine to use for queries and transformations
	 * @param model
	 *            the resource set containing at least one model resource
	 * @param defaultResource
	 *            the default resource
	 */
	public GraphTransformationAPI(final IContextPatternInterpreter engine, final ResourceSet model,
			final Resource defaultResource) {
		this.interpreter = new GraphTransformationInterpreter(engine, model, defaultResource);
	}

	/**
	 * Returns the resource set opened for transformations with the API.
	 * 
	 * @return the resource set containing the model file
	 */
	public final ResourceSet getModel() {
		return interpreter.getModel();
	}

	/**
	 * Triggers an incremental update of the matches.
	 */
	public final void updateMatches() {
		interpreter.updateMatches();
	}

	/**
	 * Terminates the interpreter.
	 */
	public final void terminate() {
		interpreter.terminate();
	}

	/**
	 * Sets the default pushout approach.
	 * 
	 * @param defaultPushoutApproach
	 *            the pushout approach to set
	 */
	public final void setDefaultPushoutApproach(final PushoutApproach defaultPushoutApproach) {
		this.defaultPushoutApproach = defaultPushoutApproach;
	}

	/**
	 * Sets the pushout approach to double pushout (see {@link PushoutApproach}).
	 */
	public final void setDPO() {
		setDefaultPushoutApproach(PushoutApproach.DPO);
	}

	/**
	 * Sets the pushout approach to single pushout (see {@link PushoutApproach}).
	 */
	public final void setSPO() {
		setDefaultPushoutApproach(PushoutApproach.SPO);
	}

	/**
	 * Returns the default pushout approach
	 * 
	 * @return the default pushout approach
	 */
	public final PushoutApproach getDefaultPushoutApproach() {
		return defaultPushoutApproach;
	}
	
	public IBeXPatternSet getPatternSet() {
		return interpreter.getPatternSet();
	}
}
