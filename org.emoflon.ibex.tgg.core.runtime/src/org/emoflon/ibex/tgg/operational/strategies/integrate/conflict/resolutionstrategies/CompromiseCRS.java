package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

import language.TGGRuleNode;

public class CompromiseCRS extends DeleteConflictResStrategy {

	public CompromiseCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(conflict.getMatch());

		conflict.getDeletionChain().foreachReverse(m -> {
			restoreMatch(integrate, m);
		});

		analysedMatch.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				EObject eObj = (EObject) conflict.getMatch().get(elt.getName());
				eObj.eContents().forEach(child -> {
					if (!analysedMatch.getEObjectToNode().containsKey(child))
						integrate.getModelChangeProtocol().util.deleteElement(child, true);
				});
			}
		});
	}

}
