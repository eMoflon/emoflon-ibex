package org.emoflon.ibex.tgg.operational.strategies;

import java.io.IOException;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.OperationMode;
import org.emoflon.ibex.tgg.operational.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.util.EmptyMatch;
import org.emoflon.ibex.tgg.operational.util.IMatch;

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
		
	public MODELGEN(String projectName) {
		super(projectName);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.MODELGEN;
	}
	
	protected void setStopCriterion(MODELGENStopCriterion stop) {
		this.stopCriterion = stop;
	}

	protected void saveModels() throws IOException {
		s.save(null);
	 	t.save(null);
	 	c.save(null);
	 	p.save(null);
	}

	protected void loadModels() throws IOException {
		s = createResource(projectPath + "/instances/src.xmi");
		t = createResource(projectPath + "/instances/trg.xmi");
		c = createResource(projectPath + "/instances/corr.xmi");
		p = createResource(projectPath + "/instances/protocol.xmi");
		
		EcoreUtil.resolveAll(rs);
	}

	protected abstract void registerUserMetamodels() throws IOException;

	public boolean isPatternRelevant(String patternName) {
		return patternName.endsWith("_MODELGEN");
	}
	
	/**
	 * differently from the super class implementation, MODELGEN
	 * (i) does not remove successful matches (but uses them repeatedly)
	 * (ii) updates the state of its stop criterion 
	 */
	@Override
	protected void processOperationalRuleMatches() {
		engine.updateMatches();
		if(stopCriterion.dont() || operationalMatchContainer.isEmpty())
			return;
		
		IMatch match = operationalMatchContainer.getNextRandom();
		String ruleName = operationalMatchContainer.getRuleName(match);
		if (!stopCriterion.dont(ruleName)) {
			if (processOperationalRuleMatch(ruleName, match))
				stopCriterion.update(ruleName,
						ruleInfos.getGreenSrcNodes(ruleName).size() + ruleInfos.getGreenSrcEdges(ruleName).size(),
						ruleInfos.getGreenTrgNodes(ruleName).size() + ruleInfos.getGreenTrgEdges(ruleName).size());
			else
				removeOperationalRuleMatch(match);
		}
		
		processOperationalRuleMatches();
	}

	@Override
	protected boolean protocol() {
		return false;
	}
	
	@Override
	protected void finalize() {
		
	}
	
	@Override
	protected void run() throws IOException {
		collectMatchesForAxioms();
		super.run();
	}

	private void collectMatchesForAxioms() {
		tgg.getRules().stream().filter(r -> ruleInfos.isAxiom(r.getName())).forEach(r -> {			
			addOperationalRuleMatch(r.getName(), new EmptyMatch(r));
		});
	}
}
