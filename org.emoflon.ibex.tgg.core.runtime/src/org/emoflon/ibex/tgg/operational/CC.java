package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class CC extends TGGRuntimeUtil {

	public CC(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR, OperationStrategy strategy) {
		super(tgg, srcR, corrR, trgR, protocolR, strategy);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.CC;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{srcR, trgR};
	}

	@Override
	protected boolean manipulateTrg() {
		return false;
	}

	@Override
	protected boolean manipulateSrc() {
		return false;
	}

}
