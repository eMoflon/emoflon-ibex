package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import language.TGG;

/**
 * Different than other TGGRuntimeUtil subtypes, MODELGEN
 * 
 * (i) additionally has a stop criterion (see MODELGENStopCriterion) (ii) does
 * not remove processed matches from its MatchContainer in
 * processOperationalRuleMatches() (iii) gets matches randomly from
 * MatchContainer (iv) does not create a protocol for scalability
 * 
 * @author leblebici
 *
 */
public class MODELGEN extends TGGRuntimeUtil {

	private MODELGENStopCriterion stopCriterion;

	public MODELGEN(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR,
			MODELGENStopCriterion stopCriterion,String pluginID) {
		super(tgg, srcR, corrR, trgR, protocolR, pluginID);
		this.stopCriterion = stopCriterion;
	}
	
	public MODELGEN(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR,
			MODELGENStopCriterion stopCriterion) {
		super(tgg, srcR, corrR, trgR, protocolR);
		this.stopCriterion = stopCriterion;
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.MODELGEN;
	}

	public MODELGENStopCriterion getStopCriterion() {
		return stopCriterion;
	}

	public boolean stop() {
		return stopCriterion.dont();
	}

	@Override
	public OperationStrategy getStrategy() {
		return OperationStrategy.PROTOCOL_NACS;
	}

	/**
	 * differently from the super class implementation, MODELGEN
	 * (i) does not remove successful matches (but uses them repeatedly)
	 * (ii) updates the state of its stop criterion 
	 */
	@Override
	protected void processOperationalRuleMatches() {
		while (!stop()) {
			IPatternMatch match = operationalMatchContainer.getNextRandom();
			String ruleName = operationalMatchContainer.getRuleName(match);
			if (!stopCriterion.dont(ruleName)) {
				if (processOperationalRuleMatch(ruleName, match))
					stopCriterion.update(ruleName,
							ruleInfos.getGreenSrcNodes(ruleName).size() + ruleInfos.getGreenSrcEdges(ruleName).size(),
							ruleInfos.getGreenTrgNodes(ruleName).size() + ruleInfos.getGreenTrgEdges(ruleName).size());
				else
					removeOperationalRuleMatch(match);
			}
		}
	}

	@Override
	protected boolean protocol() {
		return false;
	}

}
