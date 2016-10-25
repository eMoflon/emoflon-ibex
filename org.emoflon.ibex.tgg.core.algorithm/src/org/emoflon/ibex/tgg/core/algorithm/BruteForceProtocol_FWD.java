package org.emoflon.ibex.tgg.core.algorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.emf.ecore.EObject;

import gnu.trove.set.hash.TIntHashSet;

public class BruteForceProtocol_FWD extends BruteForceProtocol {

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

}
