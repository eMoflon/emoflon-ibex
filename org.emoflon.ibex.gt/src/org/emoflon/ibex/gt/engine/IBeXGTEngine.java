package org.emoflon.ibex.gt.engine;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternSet;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRuleSet;

public class IBeXGTEngine<PM extends IBeXGTPatternMatcher<?>> {
	final protected PM patternMatcher;
	final protected GTModel ibexModel;
	final protected ResourceSet model;
	private Resource trashResource;
	protected Map<String, GTRule> name2rule = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, GTPattern> name2pattern = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Random rndGenerator;
	protected boolean alwaysUpdatePrior = false;
	protected boolean alwaysUpdateAfter = false;

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

	protected Map<String, IBeXGTRule<?, ?, ?, ?, ?>> name2typedRule = Collections
			.synchronizedMap(new LinkedHashMap<>());

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

		for (GTRule rule : ibexModel.getRuleSet().getRules()) {
			name2rule.put(rule.getName(), rule);
		}
		for (GTPattern pattern : ibexModel.getPatternSet().getPatterns().stream().map(pattern -> (GTPattern) pattern)
				.collect(Collectors.toList())) {
			name2pattern.put(pattern.getName(), pattern);
		}

		rndGenerator = new Random();

		// Create the trash resource for pattern matchers that cannot handle dangling
		// edges/nodes
		URI trashURI = model.getResources().get(0).getURI().trimFileExtension();
		trashURI = trashURI.trimSegments(1).appendSegment(trashURI.lastSegment() + "-trash").appendFileExtension("xmi");
		trashResource = model.createResource(trashURI);
	}

	protected void registerTypedRule(IBeXGTRule<?, ?, ?, ?, ?> typedRule) {
		name2typedRule.put(typedRule.ruleName, typedRule);
	}

	public boolean isAlwaysUpdatePrior() {
		return alwaysUpdatePrior;
	}

	public void setAlwaysUpdatePrior(boolean alwaysUpdatePrior) {
		this.alwaysUpdatePrior = alwaysUpdatePrior;
	}

	public boolean isAlwaysUpdateAfter() {
		return alwaysUpdateAfter;
	}

	public void setAlwaysUpdateAfter(boolean alwaysUpdateAfter) {
		this.alwaysUpdateAfter = alwaysUpdateAfter;
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
	 * Returns the currently used set of patterns
	 * 
	 * @return IBeXPatternSet
	 */
	public IBeXPatternSet getPatternSet() {
		return ibexModel.getPatternSet();
	}

	public GTPattern getPattern(final String patternName) {
		return name2pattern.get(patternName);
	}

	/**
	 * Returns the currently used set of rules
	 * 
	 * @return GTRuleSet
	 */
	public GTRuleSet getRuleSet() {
		return ibexModel.getRuleSet();
	}

	public GTRule getRule(final String ruleName) {
		return name2rule.get(ruleName);
	}

	/**
	 * Returns the wrapper object of the underlying pattern matching engine.
	 * 
	 * @return IBeXGTPatternMatcher
	 */
	public PM getPatternMatcher() {
		return patternMatcher;
	}

	public void delete(final EObject object) {
		if (patternMatcher.getEngineProperties().needs_trash_resource()) {
			EMFManipulationUtils.deleteNode(object,
					node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)), false, false);
		} else {
			EMFManipulationUtils.deleteNode(object, false, false);
		}
	}

	public void deleteEdge(final EObject src, final EObject trg, final IBeXEdge edge) {
		if (patternMatcher.getEngineProperties().needs_trash_resource()) {
			EMFManipulationUtils.deleteEdge(src, trg, edge.getType(),
					node -> trashResource.getContents().add(EcoreUtil.getRootContainer(node)));
		} else {
			EMFManipulationUtils.deleteEdge(src, trg, edge.getType());
		}
	}

}
