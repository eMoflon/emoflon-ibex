package org.emoflon.ibex.tgg.operational;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;

import language.TGG;

public class BWD extends TGGRuntimeUtil {

	public BWD(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	public OperationStrategy getStrategy() {
		return OperationStrategy.PROTOCOL_NACS;
	}


}
