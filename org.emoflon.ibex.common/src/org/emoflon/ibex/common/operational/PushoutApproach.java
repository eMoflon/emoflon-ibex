package org.emoflon.ibex.common.operational;

/**
 * Defines constants for the different pushout approaches.
 */
public enum PushoutApproach {
	/**
	 * Double pushout: If the edges of deleted nodes are not deleted as well, the
	 * rule cannot be applied successfully.
	 */
	DPO,

	/**
	 * Single pushout: Just delete dangling edges.
	 */
	SPO
}
