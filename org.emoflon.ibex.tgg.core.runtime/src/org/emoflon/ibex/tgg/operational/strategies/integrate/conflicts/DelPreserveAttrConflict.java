package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

import language.DomainType;

public class DelPreserveAttrConflict extends DeletePreserveConflict {

	private final Set<AttrConflictingElt> subjects;

	public DelPreserveAttrConflict(ConflictContainer container,
			Set<AttrConflictingElt> subjects, DomainType domainToBePreserved) {
		super(container, domainToBePreserved);
		this.subjects = subjects;
	}

	public Set<AttrConflictingElt> getSubjects() {
		return subjects;
	}

	//// CRS ////

	@Override
	public void crs_revokeAddition() {
		subjects.forEach(subject -> subject.getAttributeChanges().forEach(ac -> ModelChangeUtil.revertAttributeChange(ac)));
		resolved = true;
	}

}
