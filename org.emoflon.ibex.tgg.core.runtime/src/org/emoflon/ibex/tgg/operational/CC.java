package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;

import gnu.trove.set.hash.TIntHashSet;
import language.TGG;
import runtime.Edge;
import runtime.TGGRuleApplication;

public class CC extends TGGRuntimeUtil_ILP {

	public CC(TGG tgg, Resource srcR, Resource corrR, Resource trgR, Resource protocolR) {
		super(tgg, srcR, corrR, trgR, protocolR);
	}

	@Override
	public OperationMode getMode() {
		return OperationMode.CC;
	}


	@Override
	protected Collection<HashMap<EObject, TIntHashSet>> getInputCreateTables() {
		HashSet<HashMap<EObject, TIntHashSet>> result = new HashSet<>();
		result.add(createdSrcToMatch);
		result.add(createdTrgToMatch);
		return result;
	}

	@Override
	protected void deleteOutputElementsOf(TGGRuleApplication m) {
		deleteElements(m.getCreatedCorr());	
	}

	@Override
	protected int getWeight(TGGRuleApplication m) {
		return m.getCreatedSrc().size() + m.getCreatedTrg().size();
	}
	
	@Override
	protected Collection<Edge> getOutputEdgesOf(TGGRuleApplication ra) {
		return Collections.emptyList();
	}

}
