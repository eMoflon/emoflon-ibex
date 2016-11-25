package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import language.TGG;
import runtime.TGGRuleApplication;

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

	@Override
	public void apply(String ruleName, IPatternMatch match) {

		if (!stopCriterion.dont(ruleName)) {
			super.apply(ruleName, match);
			stopCriterion.update(ruleName, greenSrcNodes.get(ruleName).size() + greenSrcEdges.get(ruleName).size(),
					greenTrgNodes.get(ruleName).size() + greenTrgEdges.get(ruleName).size());
		}
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
	 * Differences of MODELGEN::run from super::run are - the next match is
	 * randomly chosen - applied matches are not removed from the match
	 * container
	 */
	@Override
	public void run() {
		while (!matchContainer.isEmpty() && !stop()) {
			IPatternMatch match = matchContainer.getNextRandom();
			String ruleName = matchContainer.getRuleName(match);
			apply(ruleName, match);
		}
	}
	
	@Override
	protected boolean protocol() {
		return false;
	}

}
