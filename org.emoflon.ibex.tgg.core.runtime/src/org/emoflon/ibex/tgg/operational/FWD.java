package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class FWD extends TGGRuntimeUtil {

	public FWD(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR, OperationStrategy strategy) {
		super(tgg, srcR, corrR, trgR, protocolR, strategy);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.FWD;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{srcR};
	}

	@Override
	protected boolean manipulateTrg() {
		return true;
	}

	@Override
	protected boolean manipulateSrc() {
		return false;
	}

}
