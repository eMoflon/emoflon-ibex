package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class ModelgenTGGRuntimeUtil extends TGGRuntimeUtil {

	public ModelgenTGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{};
	}

	@Override
	public Direction getDirection() {
		return Direction.MODELGEN;
	}

	@Override
	public Strategy getStrategy() {
		return Strategy.NORMAL;
	}

}
