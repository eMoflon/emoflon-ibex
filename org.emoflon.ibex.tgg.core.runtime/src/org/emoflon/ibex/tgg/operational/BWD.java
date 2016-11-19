package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class BWD extends TGGRuntimeUtil {

	public BWD(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.BWD;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{trgR};
	}

}
