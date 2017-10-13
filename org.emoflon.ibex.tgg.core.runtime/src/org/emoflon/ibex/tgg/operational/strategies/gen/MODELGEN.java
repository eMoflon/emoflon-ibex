package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.EmptyMatch;
import org.emoflon.ibex.tgg.operational.util.IMatch;

import language.TGGComplementRule;
import language.TGGRule;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeConstraintLibrary;

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
	private Collection<String> complementRulesNames;
		
	public MODELGEN(String projectName, String workspacePath, boolean debug) throws IOException {
		super(projectName, workspacePath, debug);
		//this.complementRulesNames = getComplementRulesNames();
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
		
		Set<IMatch> matches = new HashSet<IMatch>();
		matches = findAllComplementMatches();
		
		if (! matches.isEmpty()) {
			processAllComplementRuleMatches(matches);
		}
		else {
			IMatch match = chooseOneMatch();
			String ruleName = operationalMatchContainer.getRuleName(match);
			if(stopCriterion.dont(ruleName))
				removeOperationalRuleMatch(match);
			else if (processOperationalRuleMatch(ruleName, match))
				updateStopCriterion(ruleName);
		}
		return true;
	}

	private void processAllComplementRuleMatches(Set<IMatch> matches) {
		while (! matches.isEmpty()) {
				IMatch match = matches.iterator().next();
				processComplementRuleMatch(match);
				matches.remove(match);
				removeOperationalRuleMatch(match);
			}
	}
	
	private void processComplementRuleMatch(IMatch match) {
		String ruleName = operationalMatchContainer.getRuleName(match);
		TGGComplementRule rule = null;
		for (TGGRule r : getTGG().getRules()) {
			if (r instanceof TGGComplementRule && r.getName().equals(ruleName))
				rule = (TGGComplementRule) r;
		}
		/*Optional<TGGComplementRule> Optrule = getTGG().getRules().stream()
											.filter(p -> p instanceof TGGComplementRule && p.getName().equals(ruleName))
											.findAny();
		TGGComplementRule rule = Optrule.orElse(null);*/
		if(rule.isAdditionalContext()) {
			processOperationalRuleMatch(ruleName, match);
		}
		else {
			int upperBoundUpdatePolicy = 10;
			int lowerBoundUpdatePolicy = 0;
			if (stopCriterion.complentRuleBounds.containsKey(ruleName)) {
				upperBoundUpdatePolicy = stopCriterion.complentRuleBounds.get(ruleName).get("upperBound");
				lowerBoundUpdatePolicy = stopCriterion.complentRuleBounds.get(ruleName).get("lowerBound");
			}
			int upperBound = Math.min(rule.getUpperRABound(), upperBoundUpdatePolicy);
			int lowerBound = Math.max(rule.getLowerRABound(), lowerBoundUpdatePolicy);
			Random random = new Random();
			int randomUpperBound = lowerBound + random.nextInt(upperBound - lowerBound + 1);
			for (int i = 0; i < randomUpperBound; i++) {
				processOperationalRuleMatch(ruleName, match);
			}
		}
	}

	private Set<IMatch> findAllComplementMatches() {
		this.complementRulesNames = getComplementRulesNames();
		Set<IMatch> processedMatches = operationalMatchContainer.getMatches().stream()
				.filter(m -> complementRulesNames.contains(m.patternName()))
				/*.filter(m -> m.patternName().contains("Daughter2Female") 
						|| m.patternName().contains("Son2Male")
						|| m.patternName().contains("CreateOneSiblingFamily"))*/
				.collect(Collectors.toSet());
		return processedMatches;
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
