package org.emoflon.ibex.tgg.operational.strategies.gen;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.EmptyMatch;
import org.emoflon.ibex.tgg.operational.util.IMatch;

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
		
	public MODELGEN(String projectName, String workspacePath, boolean flatten, boolean debug) throws IOException {
		super(projectName, workspacePath, flatten, debug);
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
		return patternName.endsWith(PatternSuffixes.MODELGEN);
	}
	
	/**
	 * differently from the super class implementation, MODELGEN
	 * (i) does not remove successful matches (but uses them repeatedly)
	 * (ii) updates the state of its stop criterion 
	 */
	@Override
	protected boolean processOperationalRuleMatches() {
		if(stopCriterion.dont() || operationalMatchContainer.isEmpty())
			return false;
		
		IMatch match = operationalMatchContainer.getNextRandom();
		String ruleName = operationalMatchContainer.getRuleName(match);
		if(stopCriterion.dont(ruleName)) //TODO[FStolte] rule refinement: matches of abstract rules must be removed
			removeOperationalRuleMatch(match);
		else if (processOperationalRuleMatch(ruleName, match))
			updateStopCriterion(ruleName);
			
		return true;
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
		return false;
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
