package org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds;

public enum FilterNACStrategy {

	// NONE = No checking of DECs at all
	NONE,

	// Filter NACs for the case where no other rule would "potentially" translate a dangling edge.
	// This corresponds to the old implementation (with SDMs) in eMoflon
	FILTER_NACS,

	// Filter NACs and, additionally, PACs so that the potential rules must really have a source/target
	// match for the edge
	FILTER_NACS_AND_PACS
}
