package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;

public class AttributeConflict extends Conflict {
	
	private final AttrConflictingElt srcElement;
	private final AttrConflictingElt trgElement;

	public AttributeConflict(ITGGMatch match, AttrConflictingElt srcElement, AttrConflictingElt trgElement) {
		super(match);
		this.srcElement = srcElement;
		this.trgElement = trgElement;
	}

	public AttrConflictingElt getConflictingSrcElement() {
		return srcElement;
	}

	public AttrConflictingElt getConflictingTrgElement() {
		return trgElement;
	}

}
