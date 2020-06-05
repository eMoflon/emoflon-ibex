package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

public class DeletePropAttrConflict extends DeletePropConflict {

	private final Set<AttrConflictingElt> subjects;

	public DeletePropAttrConflict(INTEGRATE integrate, ITGGMatch match, Set<AttrConflictingElt> subjects) {
		super(integrate, match);
		this.subjects = subjects;
	}

	public Set<AttrConflictingElt> getSubjects() {
		return subjects;
	}
	
	//// CRS ////

	@Override
	public void crs_revokeAddition() {
		subjects.forEach(subject -> subject.getAttributeChanges().forEach(ac -> ModelChangeUtil.revertAttributeChange(ac)));
	}

}
