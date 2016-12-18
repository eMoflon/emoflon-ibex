package org.emoflon.ibex.tgg.operational;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.csp.constraints.factories.RuntimeTGGAttrConstraintFactory;

import gnu.trove.set.hash.TIntHashSet;
import language.TGG;
import runtime.Edge;
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
	protected Collection<Edge> getOutputEdgesOf(TGGRuleApplication ra) {
		ArrayList<Edge> result = new ArrayList<>();
		ra.getCreatedSrc().forEach(e -> {
			if(e instanceof Edge)
				result.add((Edge) e);
		});
		return result;
	}


}
