package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

public class AttributeConflict extends Conflict {

	private final ConstrainedAttributeChanges conflictedConstraint;
	private final AttributeChange srcChange;
	private final AttributeChange trgChange;

	public AttributeConflict(ITGGMatch match, ConstrainedAttributeChanges conflictedConstraint, //
			AttributeChange srcChange, AttributeChange trgChange) {
		super(match);
		this.conflictedConstraint = conflictedConstraint;
		this.srcChange = srcChange;
		this.trgChange = trgChange;
	}

	public ConstrainedAttributeChanges getConflictedConstraint() {
		return conflictedConstraint;
	}

	public AttributeChange getSrcChange() {
		return srcChange;
	}

	public AttributeChange getTrgChange() {
		return trgChange;
	}

}
