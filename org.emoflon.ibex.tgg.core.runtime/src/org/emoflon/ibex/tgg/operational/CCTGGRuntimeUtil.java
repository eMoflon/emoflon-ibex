package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;

import language.TGG;

public class CCTGGRuntimeUtil extends TGGRuntimeUtil {

	public CCTGGRuntimeUtil(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{srcR,trgR};
	}

	@Override
	public Direction getDirection() {
		return Direction.CC;
	}

	@Override
	public Strategy getStrategy() {
		return Strategy.ILP;
	}

}
