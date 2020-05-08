package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

/**
 * A {@link DeletePropCRS} that preserves the deletion and revokes
 * all created elements, links or attribute changes that cause the conflict.
 *
 */
public class PreserveDeletionCRS extends DeletePropCRS {

	public PreserveDeletionCRS(DeletePropConflict conflict) {
		super(conflict);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		if (conflict instanceof DeletePropEdgeConflict) {
			((DeletePropEdgeConflict) conflict).getSubjects().forEach(subject -> {
				subject.getCreatedEdges().forEach(ce -> {
					if (ce.getType().isContainment()) {
						ModelChangeUtil.deleteElement(ce.getTarget(), true);
					} else {
						ModelChangeUtil.deleteEdge(ce);
					}
				});
			});
		} else if (conflict instanceof DeletePropAttrConflict) {
			((DeletePropAttrConflict) conflict).getSubjects().forEach(subject -> {
				subject.getAttributeChanges().forEach(ac -> ModelChangeUtil.revertAttributeChange(ac));
			});
		}
	}

}
