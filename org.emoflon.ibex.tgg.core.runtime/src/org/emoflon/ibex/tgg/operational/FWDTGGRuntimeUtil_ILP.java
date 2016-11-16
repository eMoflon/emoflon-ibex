package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class FWDTGGRuntimeUtil_ILP extends FWDTGGRuntimeUtil {

	public FWDTGGRuntimeUtil_ILP(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}
	
	@Override
	public Strategy getStrategy() {
		return Strategy.ILP;
	}

}
