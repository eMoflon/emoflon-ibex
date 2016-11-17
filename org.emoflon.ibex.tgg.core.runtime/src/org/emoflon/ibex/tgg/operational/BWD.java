package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class BWD extends TGGRuntimeUtil {

	public BWD(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR, OperationStrategy strategy) {
		super(tgg, srcR, corrR, trgR, protocolR, strategy);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.BWD;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{trgR};
	}

	@Override
	protected boolean manipulateTrg() {
		return false;
	}

	@Override
	protected boolean manipulateSrc() {
		return true;
	}

}
