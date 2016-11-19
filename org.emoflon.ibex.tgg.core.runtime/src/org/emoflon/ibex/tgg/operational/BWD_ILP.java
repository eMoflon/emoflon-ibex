package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import gnu.trove.set.hash.TIntHashSet;
import language.TGG;
import runtime.TGGRuleApplication;

public class BWD_ILP extends TGGRuntimeUtil_ILP {

	public BWD_ILP(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	protected Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables() {
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(createdTrgToMatch);
		return result;
	}

	@Override
	protected void deleteOutputElementsOf(TGGRuleApplication m) {
		deleteElements(m.getCreatedSrc());
		deleteElements(m.getCreatedCorr());	
	}

	@Override
	protected int getWeight(TGGRuleApplication m) {
		return m.getCreatedTrg().size();
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
