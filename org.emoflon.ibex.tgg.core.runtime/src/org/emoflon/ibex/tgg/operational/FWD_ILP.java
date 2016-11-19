package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import gnu.trove.set.hash.TIntHashSet;
import language.TGG;
import runtime.TGGRuleApplication;

public class FWD_ILP extends TGGRuntimeUtil_ILP {

	public FWD_ILP(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	protected Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables() {
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(createdSrcToMatch);
		return result;
	}

	@Override
	protected void deleteOutputElementsOf(TGGRuleApplication m) {
		deleteElements(m.getCreatedTrg());
		deleteElements(m.getCreatedCorr());
	}

	@Override
	protected int getWeight(TGGRuleApplication m) {
		return m.getCreatedSrc().size();
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.FWD;
	}

	@Override
	protected Resource[] getResourcesForEdgeCreation() {
		return new Resource[]{srcR};
	}

}
