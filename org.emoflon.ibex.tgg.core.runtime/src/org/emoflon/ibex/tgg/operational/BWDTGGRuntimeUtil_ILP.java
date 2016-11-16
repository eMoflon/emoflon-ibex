package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class BWDTGGRuntimeUtil_ILP extends BWDTGGRuntimeUtil {

	public BWDTGGRuntimeUtil_ILP(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}
	
	@Override
	public Strategy getStrategy() {
		return Strategy.ILP;
	}

}
