package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import language.TGG;
import runtime.TGGRuleApplication;

/**
 * Different than other TGGRuntimeUtil subtypes, MODELGEN 
 * 
 * (i) additionally has a stop criterion (see MODELGENStopCriterion)
 * (ii) does not remove processed matches from its MatchContainer in processOperationalRuleMatches() 
 * (iii) gets matches randomly from MatchContainer
 * (iv) does not create a protocol for scalability
 * 
 * @author leblebici
 *
 */
public class MODELGEN extends TGGRuntimeUtil {

	private MODELGENStopCriterion stopCriterion;

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

	@Override
	protected void processOperationalRuleMatches() {
		while (!stop()) {
			IPatternMatch match = matchContainer.getNextRandom();
			String ruleName = matchContainer.getRuleName(match);
			if (!stopCriterion.dont(ruleName)) {
				processOperationalRuleMatch(ruleName, match);
				stopCriterion.update(ruleName, greenSrcNodes.get(ruleName).size() + greenSrcEdges.get(ruleName).size(),
						greenTrgNodes.get(ruleName).size() + greenTrgEdges.get(ruleName).size());
			}
		}
	}

	@Override
	protected boolean protocol() {
		return false;
	}

}
