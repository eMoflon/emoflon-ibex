package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class BWDTGGRuntimeUtil extends TGGRuntimeUtil {

	public BWDTGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{trgR};
	}

	@Override
	public Direction getDirection() {
		return Direction.BWD;
	}

	@Override
	public Strategy getStrategy() {
		return Strategy.NORMAL;
	}

}
