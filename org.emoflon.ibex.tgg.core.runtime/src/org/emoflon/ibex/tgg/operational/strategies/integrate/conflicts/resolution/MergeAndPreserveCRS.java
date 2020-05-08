package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis;

import language.TGGRuleNode;

/**
 * A {@link DeletePropCRS} that revokes as few as possible deletions
 * to resolve the conflict.
 *
 */
public class MergeAndPreserveCRS extends DeletePropCRS {

	public MergeAndPreserveCRS(DeletePropConflict conflict) {
		super(conflict);
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
