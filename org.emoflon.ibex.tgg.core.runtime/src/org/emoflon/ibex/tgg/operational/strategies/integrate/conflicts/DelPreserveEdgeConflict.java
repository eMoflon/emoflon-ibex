package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.EdgeConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

import language.DomainType;

public class DelPreserveEdgeConflict extends DeletePreserveConflict {

	private final Set<EdgeConflictingElt> subjects;

	public DelPreserveEdgeConflict(ConflictContainer container, Set<EdgeConflictingElt> subjects,
			DomainType domainToBePreserved) {
		super(container, domainToBePreserved);
		this.subjects = subjects;
	}

	public Set<EdgeConflictingElt> getSubjects() {
		return subjects;
	}

	//// CRS ////

	@Override
	public void crs_revokeAddition() {
		subjects.forEach(subject -> {
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
