package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferSource;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.CRS_PreferTarget;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

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
		integrate().repairOneMatch(integrate().getAttributeRepairStrategy(), getMatch(), PatternType.FWD);
	}

	@Override
	public void crs_preferTarget() {
		ModelChangeUtil.revertAttributeChange(srcChange);
		integrate().repairOneMatch(integrate().getAttributeRepairStrategy(), getMatch(), PatternType.BWD);
	}

}
