package org.emoflon.ibex.tgg.compiler.patterns;

public enum FilterNACStrategy {

	// NONE = No checking of DECs at all
	NONE,

	// Filter NACs for the case where no other rule would "potentially" translate a
	// dangling edge.
	// This corresponds to the old implementation (with SDMs) in eMoflon
	FILTER_NACS
}
