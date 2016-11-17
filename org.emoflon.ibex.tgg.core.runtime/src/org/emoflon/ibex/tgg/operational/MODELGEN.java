package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra.query.runtime.api.IPatternMatch;

import language.TGG;
import runtime.TGGRuleApplication;

public class MODELGEN extends TGGRuntimeUtil {

	private MODELGENStopCriterion stopCriterion;
	
	public MODELGEN(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR,
			OperationStrategy strategy, MODELGENStopCriterion stopCriterion) {
		super(tgg, srcR, corrR, trgR, protocolR, strategy);
		this.stopCriterion = stopCriterion;
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.MODELGEN;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{};
	}

	@Override
	protected boolean manipulateTrg() {
		return true;
	}

	@Override
	protected boolean manipulateSrc() {
		return true;
	}
	
	@Override
	public TGGRuleApplication apply(String ruleName, IPatternMatch match) {
		
		TGGRuleApplication result = null;
		if(!stopCriterion.dont(ruleName)){
			result = super.apply(ruleName, match);
			stopCriterion.update(result);
		}
		return null;		
	}

}
