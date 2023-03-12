package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchAnalyzer.ConstrainedAttributeChanges;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

public class AttributeConflict extends Conflict implements CRS_PreferSource, CRS_PreferTarget {

	private final ConstrainedAttributeChanges conflictedConstraint;
	private final AttributeChange srcChange;
	private final AttributeChange trgChange;

	public AttributeConflict(ConflictContainer container, ConstrainedAttributeChanges conflictedConstraint, //
			AttributeChange srcChange, AttributeChange trgChange) {
		super(container);
		this.conflictedConstraint = conflictedConstraint;
		this.srcChange = srcChange;
		this.trgChange = trgChange;
	}
	
	@Override
	protected Set<ITGGMatch> initConflictMatches() {
		Set<ITGGMatch> matches = new HashSet<>();
		matches.add(getMatch());
		return matches;
	}

	@Override
	protected Set<ITGGMatch> initScopeMatches() {
		return new HashSet<>();
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

	//// CRS ////

	@Override
	public void crs_preferSource() {
		ModelChangeUtil.revertAttributeChange(trgChange);
		integrate().repair().attributeRepairOneMatch(getMatch(), PatternType.FWD);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_SOURCE");
		resolved = true;
	}

	@Override
	public void crs_preferTarget() {
		ModelChangeUtil.revertAttributeChange(srcChange);
		integrate().repair().attributeRepairOneMatch(getMatch(), PatternType.BWD);
		
		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Resolved conflict: " + printConflictIdentification() + " by PREFER_TARGET");
		resolved = true;
	}

}
