package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

public enum OverlapCategory {
	
	/**
	 * If no other category fits
	 */
	OTHER,

	/**
	 * Switches from one to another similar structure remaining with the same rule
	 * application
	 */
	MOVER,

	/**
	 * Changes the rule application and keeps in place
	 */
	CHANGER,

	/**
	 * Switches from one to another similar structure and changes the rule
	 * application
	 */
	COMBI,

	/**
	 * Cuts one structure in two
	 */
	CUTTER,

	/**
	 * Connects two structures
	 */
	JOINER,

	/**
	 * Switches from one to another similar structure remaining with the same rule
	 * application (advanced overlap strategy)
	 */
	ADV_MOVER;

}
