package org.emoflon.ibex.gt.api;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.operational.IContextPatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.StateModel.State;
import org.emoflon.ibex.gt.arithmetic.Probability;
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
	 * 	Map with all the rules and patterns of the model
	 */
	protected Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> patternMap;
	
	/**
	 *	Map with all the rules that can be applied to Gillespie and their probabilities;
	 * 	array[0] is the probability; array[1] is probability*matchCount
	 */
	protected Map<GraphTransformationRule<?,?>, double[]> gillespieMap;
	
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
	
	protected abstract Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> initiatePatternMap();
	
	protected abstract Map<GraphTransformationRule<?,?>, double[]> initiateGillespieMap();

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
 	* returns all the patterns and rules of the model that do not need an input parameter
 	*/
	public Map<String, Supplier<? extends GraphTransformationPattern<?,?>>> getAllPatterns(){
		return patternMap;
	}
	
	public GraphTransformationPattern<?,?> getPattern(String patternName) {
		return patternMap.get(patternName).get();
	}
	
	/**
	 * Helper method for the Gillespie algorithm; counts all the possible matches
	 * for rules in the graph that have a static probability
	 */
	public double getTotalSystemActivity(boolean doUpdate){
		 gillespieMap.forEach((v,z) -> {
		 	z[0] =((Probability<?,?>) v.getProbability().get()).getProbability();
			 z[1] = v.countMatches(doUpdate)*z[0];
			});
		double totalActivity = 0;
		for(double[] activity : gillespieMap.values()) {
			totalActivity += activity[1];
		}
		return totalActivity;
	}
	
	/**
	 * Returns the probability that the rule will be applied with the
	 * Gillespie algorithm; only works if the rules do not have parameters and the
	 * probability is static
	 */
	public double getGillespieProbability(GraphTransformationRule<?,?> rule, boolean doUpdate){
		if(gillespieMap.containsKey(rule)){
			double totalActivity = getTotalSystemActivity(doUpdate);
			if(totalActivity > 0){
				return gillespieMap.get(rule)[1]/totalActivity;	
			}								
		}
		return 0;
	}
	
	/**
	 * Applies a rule to the graph after the Gillerspie algorithm;
	 * only rules that do not have parameters are counted
	 * @return an {@link Optional} for the the match after rule application
	 */
	@SuppressWarnings("unchecked")
	public final Optional<GraphTransformationMatch<?,?>> applyGillespie(boolean doUpdate){
		double totalActivity = getTotalSystemActivity(doUpdate);
		if(totalActivity != 0){
			Random rnd = new Random();
			double randomValue = totalActivity*rnd.nextDouble();
			double currentActivity = 0;
			for(Entry<GraphTransformationRule<?,?>, double[]> entries : gillespieMap.entrySet()){
			currentActivity += entries.getValue()[1];
				if(currentActivity >= randomValue){
					return (Optional<GraphTransformationMatch<?, ?>>)entries.getKey().apply(doUpdate);
				}						
			}
		}
		return Optional.empty();
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
	
	public GraphTransformationInterpreter getInterpreter() {
		return interpreter;
	}
	
	public void trackModelStates() {
		interpreter.trackModelStates();
	}
	
	public void resetModelStatesTracking() {
		deactivateModelStatesTracking();
		trackModelStates();
	}
	
	public void deactivateModelStatesTracking() {
		interpreter.deactivateModelStatesTracking();
	}
	
	/**
	 * Applies the rule on the given match.
	 * 
	 * @param match
	 *            the match
	 *            
	 * @param doUpdate
	 * 			  true: run pattern matching process on changed model
	 * @return an {@link Optional} for the the match after rule application
	 */
	public Optional<IMatch> revertLastApply(boolean doUpdate) {
		Optional<IMatch> comatch = interpreter.revertApply(doUpdate);
		return comatch;
	}
	
	public State getCurrentModelState() {
		return interpreter.getCurrentModelState();
	}
}
