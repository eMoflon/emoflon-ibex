package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.EmptyMatch;
import org.emoflon.ibex.tgg.operational.util.IMatch;

import language.BindingType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;
import runtime.TGGRuleApplication;

/**
 * Different than other OperationalStrategy subtypes, MODELGEN
 * (i) additionally has a stop criterion (see MODELGENStopCriterion) (ii) does
 * not remove processed matches from its MatchContainer in
 * processOperationalRuleMatches() (iii) gets matches randomly from
 * MatchContainer (iv) does not create a protocol for scalability
 * 
 * @author leblebici
 *
 */
public abstract class MODELGEN extends OperationalStrategy {

	protected MODELGENStopCriterion stopCriterion;
		
	public MODELGEN(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
	}
	
	public void setStopCriterion(MODELGENStopCriterion stop) {
		this.stopCriterion = stop;
	}

	@Override
	public void saveModels() throws IOException {
		s.save(null);
	 	t.save(null);
	 	c.save(null);
	 	p.save(null);
	}

	@Override
	public void loadModels() throws IOException {
		s = createResource(projectPath + "/instances/src.xmi");
		t = createResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");
		
		EcoreUtil.resolveAll(rs);
	}

	protected abstract void registerUserMetamodels() throws IOException;

	@Override
	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith(PatternSuffixes.GEN);
	}
	
	/**
	 * differently from the super class implementation, MODELGEN
	 * (i) does not remove successful matches (but uses them repeatedly)
	 * (ii) updates the state of its stop criterion 
	 */
	@Override
	protected boolean processOneOperationalRuleMatch() {
		
		if(stopCriterion.dont() || operationalMatchContainer.isEmpty())
			return false;

		IMatch match = chooseOneMatch();
		String ruleName = operationalMatchContainer.getRuleName(match);
		
		if (stopCriterion.dont(ruleName))
			removeOperationalRuleMatch(match);
		else {
			HashMap<String, EObject> comatch = processOperationalRuleMatch(ruleName, match);
			if (comatch != null) {
				updateStopCriterion(ruleName);
				if (isKernelMatch(ruleName)) 
					processComplementRuleMatches(comatch);
			}
		}
		return true;
	}


	private void processComplementRuleMatches(HashMap<String, EObject> comatch) {
		engine.updateMatches();
		Set<IMatch> complementRuleMatches = findAllComplementRuleMatches();
		
		if (! complementRuleMatches.isEmpty()) {
			Set<String> uniqueRulesNames = complementRuleMatches.stream()
												.map(m -> PatternSuffixes.removeSuffix(m.patternName()))
												.distinct()
												.collect(Collectors.toSet());
			HashMap<String, Integer> complementRulesBounds = updatePolicy.getNumberOfApplications(uniqueRulesNames);
			
			checkComplianceWithSchema(complementRulesBounds);
			
			while (! complementRuleMatches.isEmpty()) {
					IMatch match = complementRuleMatches.iterator().next();
					processComplementRuleMatch(match, complementRulesBounds);
					complementRuleMatches.remove(match);
					removeOperationalRuleMatch(match);
				}
		}
		TGGRuleApplication application = (TGGRuleApplication) comatch.get(ConsistencyPattern.getProtocolNodeName());
		application.setAmalgamated(true);
	}

	private void processComplementRuleMatch(IMatch match, HashMap<String, Integer> complementRulesBounds) {
		String ruleName = operationalMatchContainer.getRuleName(match);
		TGGComplementRule rule = (TGGComplementRule) getRule(ruleName);

		if(rule.isBounded()) {
			processOperationalRuleMatch(ruleName, match);
		}
		else {
			IntStream.range(0, complementRulesBounds.get(ruleName))
					.forEach(i -> processOperationalRuleMatch(ruleName, match));
		}
	}

	// TODO: Extract logic into few smaller methods!
	private void checkComplianceWithSchema(HashMap<String, Integer> complementRulesBounds) {
		HashMap<EReference, Integer> egdesToBeCreated = new HashMap<EReference, Integer>();
		
		complementRulesBounds.keySet().stream()
        	.forEach( name -> {
        		if(((TGGComplementRule) getRule(name)).isBounded()) {
        			int number = (int) findAllComplementRuleMatches().stream()
        					.filter(m -> m.patternName().contains(name)).count();
            		getRelevantEdges(((TGGComplementRule) getRule(name))).stream()
            		.forEach( e -> {egdesToBeCreated.put(e.getType(), number);});
        		}
        		else {
        		getRelevantEdges(((TGGComplementRule) getRule(name))).stream()
        		.forEach( e -> {
        			if(! egdesToBeCreated.containsKey(e.getType())) {
        				egdesToBeCreated.put(e.getType(), complementRulesBounds.get(name));
        			}
        			else {
        				egdesToBeCreated.put(e.getType(), egdesToBeCreated.get(e.getType()) + complementRulesBounds.get(name));
        			}
        		});
        		}});
		
		egdesToBeCreated.keySet().stream()
					.filter(e -> e.getUpperBound() != -1 && egdesToBeCreated.get(e) > e.getUpperBound())
					.findAny()
					.ifPresent(e -> {throw new IllegalArgumentException("Cardinalities for " + e.getName() + " are violated");});
	}
	
	private Set<TGGRuleEdge> getRelevantEdges(TGGRule rule) {
		Set<TGGRuleEdge> relevantEdges = rule.getEdges().stream()
				   .filter(e -> e.getBindingType() == BindingType.CREATE
						   && e.getSrcNode().getBindingType() == BindingType.CONTEXT)
				   .collect(Collectors.toSet());
		
        return relevantEdges;
	}
	
	private Set<IMatch> findAllComplementRuleMatches() {
		Set<IMatch> allComplementRuleMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> getComplementRulesNames().contains(PatternSuffixes.removeSuffix(m.patternName())))
				.collect(Collectors.toSet());

		return allComplementRuleMatches;
	}

	private TGGRule getRule(String ruleName) {
		TGGRule rule = getTGG().getRules().stream()
				.filter(r -> r.getName().equals(ruleName)).findFirst().get();
		return rule;
	}
	
	private boolean isKernelMatch(String kernelName) {
		Set<String> kernelRulesNames = getTGG().getRules().stream()
				.filter(r -> r instanceof TGGComplementRule)
				.map(n -> ((TGGComplementRule) n).getKernel().getName())
				.distinct()
				.collect(Collectors.toSet());

		return kernelRulesNames.contains(kernelName);
	}

	private void updateStopCriterion(String ruleName) {
		stopCriterion.update(
				ruleName,
				ruleInfos.getGreenSrcNodes(ruleName).size() + ruleInfos.getGreenSrcEdges(ruleName).size(),
				ruleInfos.getGreenTrgNodes(ruleName).size() + ruleInfos.getGreenTrgEdges(ruleName).size()
		);
	}

	@Override
	protected boolean protocol() {
		return true;
	}
	
	@Override
	protected void wrapUp() {
		
	}
	
	@Override
	public void run() throws IOException {
		collectMatchesForAxioms();
		super.run();
	}

	private void collectMatchesForAxioms() {
		options.tgg().getRules().stream().filter(r -> ruleInfos.isAxiom(r.getName())).forEach(r -> {			
			addOperationalRuleMatch(r.getName(), new EmptyMatch(r));
		});
	}
	
	@Override
	protected boolean manipulateSrc() {
		 return true;	
	}
	
	@Override
	protected boolean manipulateTrg() {
		return true;
	}
	
	@Override
	public List<TGGAttributeConstraint> getConstraints(TGGAttributeConstraintLibrary library) {
		return library.getSorted_MODELGEN();
	}
	
}
