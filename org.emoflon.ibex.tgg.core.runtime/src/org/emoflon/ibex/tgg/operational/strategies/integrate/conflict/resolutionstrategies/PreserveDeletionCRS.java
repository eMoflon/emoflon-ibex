package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

/**
 * A {@link DeleteConflictResStrategy} that preserves the deletion and revokes
 * all created elements, links or attribute changes that cause the conflict.
 *
 */
public class PreserveDeletionCRS extends DeleteConflictResStrategy {

	public PreserveDeletionCRS(DeleteConflict conflict) {
		super(conflict);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		conflict.getSubjects().forEach(subject -> {
			subject.getAttributeChanges().forEach(ac -> ModelChangeUtil.revertAttributeChange(ac));
			subject.getCreatedEdges().forEach(ce -> {
				if (ce.getType().isContainment()) {
					ModelChangeUtil.deleteElement(ce.getTarget(), true);
				} else {
					ModelChangeUtil.deleteEdge(ce);
				}
			});
		});
	}

}
