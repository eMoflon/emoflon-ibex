package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

public class PreserveDeletionCRS extends DeleteConflictResStrategy {

	public PreserveDeletionCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public void apply(INTEGRATE integrate) {
		conflict.getSubjects().forEach(subject -> {
			subject.getAttributeChanges()
					.forEach(ac -> integrate.getModelChangeProtocol().util.revertAttributeChange(ac));
			subject.getCreatedEdges().forEach(ce -> {
				if (ce.getType().isContainment()) {
					integrate.getModelChangeProtocol().util.deleteElement(ce.getTarget(), true);
				} else {
					integrate.getModelChangeProtocol().util.deleteEdge(ce);
				}
			});
		});
	}

}
