package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

public enum DECStrategy {

	// NONE = No checking of DECs at all
	NONE,

	// STATIC = Only filter NACs for the case where no other rule would
	// "potentially" translate a dangling edge.
	// This corresponds to the old implementation via SDMs in eMoflon
	STATIC,

	// DYNAMIC = STATIC + the potential rules must really have a source/target match for the edge
	DYNAMIC

}
