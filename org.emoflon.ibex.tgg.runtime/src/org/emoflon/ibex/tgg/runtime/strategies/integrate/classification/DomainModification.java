package org.emoflon.ibex.tgg.runtime.strategies.integrate.classification;

public enum DomainModification {
	COMPL_DEL(false), PART_DEL(false), ANY_DEL(true), UNCHANGED(false), UNSPECIFIED(true), UNDEFINED(false);

	private boolean coversMultiple;

	private DomainModification(boolean coversMultiple) {
		this.coversMultiple = coversMultiple;
	}

	public boolean coversMultiple() {
		return coversMultiple;
	}

	public boolean covers(DomainModification other) {
		if (this == UNDEFINED)
			return false;

		if (!this.coversMultiple)
			return this == other;

		if (this == ANY_DEL)
			return switch (other) {
				case UNCHANGED, UNDEFINED -> false;
				default -> true;
			};

		if (this == UNSPECIFIED)
			return true;

		return false;
	}
}