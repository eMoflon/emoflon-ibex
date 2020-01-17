package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.TGGRuleNode;

public class CompromiseCRS extends DeleteConflictResStrategy {

	public CompromiseCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		MatchAnalysis analysis = integrate.getMatchAnalyser().getAnalysis(conflict.getMatch());

		conflict.getDeletionChain().foreachReverse(m -> restoreMatch(integrate, m));

		analysis.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				analysis.getObject((TGGRuleNode) elt).eContents().forEach(child -> {
					if (!analysis.getObjects().contains(child))
						ModelChangeUtil.deleteElement(child, true);
				});
			}
		});
	}

}
