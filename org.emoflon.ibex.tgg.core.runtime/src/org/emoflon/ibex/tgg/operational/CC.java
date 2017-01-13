package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class CC extends TGGRuntimeUtil_ILP {

	public CC(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	public OperationMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OperationStrategy getStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

}
