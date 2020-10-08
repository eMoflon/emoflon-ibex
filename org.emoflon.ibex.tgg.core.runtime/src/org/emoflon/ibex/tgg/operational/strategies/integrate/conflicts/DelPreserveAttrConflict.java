package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.List;

import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;

import language.DomainType;

public class DelPreserveAttrConflict extends DeletePreserveConflict {

	private final AttributeChange attrChange;

	public DelPreserveAttrConflict(ConflictContainer container, AttributeChange attrChange, DomainType domainToBePreserved,
			List<ITGGMatch> causingMatches) {
		super(container, domainToBePreserved, causingMatches);
		this.attrChange = attrChange;
	}

	public AttributeChange getAttributeChange() {
		return attrChange;
	}

	//// CRS ////

	@Override
	public void crs_revokeAddition() {
		ModelChangeUtil.revertAttributeChange(attrChange);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by REVOKE_ADDITION");
		resolved = true;
	}

}
