package org.emoflon.ibex.gt.engine;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet;

public class IBeXGTEngine<PM extends IBeXGTPatternMatcher> {

	final protected PM patternMatcher;
	final protected GTModel ibexModel;
	final protected ResourceSet model;

	/**
	 * The pushout approach to use if no approach is specified.
	 */
	protected PushoutApproach defaultPushoutApproach = PushoutApproach.SPO;

	/**
	 * TODO: Reimplement this once the rest is complete. Returns the model state
	 * manager
	 * 
	 * @return ModelStateManager
	 */
//	public final ModelStateManager getStateManager() {
//		return interpreter.getStateManager();
//	}

	/**
	 * TODO: Implement this once the new Patter base-types are done. Map with all
	 * the rules and patterns of the model
	 */
	// protected Map<String, Supplier<? extends GraphTransformationPattern<?, ?>>>
	// patternMap;

	/**
	 * TODO: Implement this once the new Patter base-types are done.
	 */
	// protected abstract Map<String, Supplier<? extends
	// GraphTransformationPattern<?, ?>>> initiatePatternMap();

	/**
	 * TODO: Same as the other attributes / methods above...
	 */
//	public Map<String, Supplier<? extends GraphTransformationPattern<?, ?>>> getAllPatterns() {
//		return patternMap;
//	}

//	public GraphTransformationPattern<?, ?> getPattern(String patternName) {
//		return patternMap.get(patternName).get();
//	}

// TODO: Same ...
//	public void trackModelStates(boolean forceNewStates) {
//		interpreter.trackModelStates(forceNewStates);
//	}
//
//	public void resetModelStatesTracking(boolean forceNewStates) {
//		deactivateModelStatesTracking();
//		trackModelStates(forceNewStates);
//	}
//
//	public void deactivateModelStatesTracking() {
//		interpreter.deactivateModelStatesTracking();
//	}
//
//	public void displayModelStates() {
//		interpreter.displayModelStates();
//	}
//
//	public void saveModelStates(String path) {
//		interpreter.saveModelStates(path);
//	}
//
//	public void loadModelStates(String path) {
//		interpreter.loadModelStates(path);
//	}
//
//	/**
//	 * Applies the rule on the given match.
//	 * 
//	 * @param match    the match
//	 * 
//	 * @param doUpdate true: run pattern matching process on changed model
//	 * @return an {@link Optional} for the the match after rule application
//	 */
//	public Optional<IMatch> revertLastApply(boolean doUpdate) {
//		return interpreter.revertApply(doUpdate);
//	}
//
//	public Optional<IMatch> moveToKnownModelState(final State trgState, boolean doUpdate) {
//		return interpreter.moveToKnownModelState(trgState, doUpdate);
//	}
//
//	public State getCurrentModelState() {
//		return interpreter.getCurrentModelState();
//	}

	/**
	 * Creates a new IBeXGTEngine for given engine and resource set.
	 * 
	 * @param engine the engine to use for queries and transformations
	 * @param model  the resource set containing at least one model resource
	 */
	public IBeXGTEngine(final PM patternMatcher, final GTModel ibexModel, final ResourceSet model) {
		this.patternMatcher = patternMatcher;
		this.ibexModel = ibexModel;
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

	/**
	 * Triggers an incremental update of the matches.
	 */
	public void updateMatches() {
		patternMatcher.updateMatches();
	}

	/**
	 * Terminates the interpreter.
	 */
	public void terminate() {
		patternMatcher.terminate();
	}

	/**
	 * Sets the default pushout approach.
	 * 
	 * @param defaultPushoutApproach the pushout approach to set
	 */
	public void setDefaultPushoutApproach(final PushoutApproach defaultPushoutApproach) {
		this.defaultPushoutApproach = defaultPushoutApproach;
	}

	/**
	 * Sets the pushout approach to double pushout (see {@link PushoutApproach}).
	 */
	public void setDPO() {
		setDefaultPushoutApproach(PushoutApproach.DPO);
	}

	/**
	 * Sets the pushout approach to single pushout (see {@link PushoutApproach}).
	 */
	public void setSPO() {
		setDefaultPushoutApproach(PushoutApproach.SPO);
	}

	/**
	 * Returns the default pushout approach
	 * 
	 * @return the default pushout approach
	 */
	public PushoutApproach getDefaultPushoutApproach() {
		return defaultPushoutApproach;
	}

	/**
	 * Returns the currently used set of patterns
	 * 
	 * @return IBeXPatternSet
	 */
	public IBeXPatternSet getPatternSet() {
		return ibexModel.getPatternSet();
	}

	/**
	 * Returns the currently used set of rules
	 * 
	 * @return GTRuleSet
	 */
	public GTRuleSet getRuleSet() {
		return ibexModel.getRuleSet();
	}

	/**
	 * Returns the wrapper object of the underlying pattern matching engine.
	 * 
	 * @return IBeXGTPatternMatcher
	 */
	public PM getPatternMatcher() {
		return patternMatcher;
	}

}
