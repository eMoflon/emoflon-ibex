package org.emoflon.ibex.tgg.operational.strategies.integrate.util;

import static org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification.*;

import java.util.List;

public enum IFPattern {

	// DEL
	DEL(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),

	// DEL-Prop
	DEL_PROP_FWD(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED),
	DEL_PROP_BWD(UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),

	// DEL-Delay
	DEL_DELAY_FWD(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED),
	DEL_DELAY_BWD(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),

	// DELAY
	DELAY(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED),

	// DEL-Corr
	DEL_CORR(UNCHANGED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNCHANGED, UNSPECIFIED);

	private MatchModPattern pattern;

	/**
	 * @param srcCre  Modification of green part in source domain
	 * @param srcCon  Modification of black part in source domain
	 * @param corrCre Modification of green part in correspondence domain
	 * @param corrCon Modification of black part in correspondence domain
	 * @param trgCre  Modification of green part in target domain
	 * @param trgCon  Modification of black part in target domain
	 */
	private IFPattern(Modification srcCre, Modification srcCon, //
			Modification corrCre, Modification corrCon, //
			Modification trgCre, Modification trgCon) {
		pattern = new MatchModPattern(srcCre, srcCon, corrCre, corrCon, trgCre, trgCon);
	}

	public boolean matches(MatchModPattern pattern) {
		List<Modification> thisPattern = this.pattern.serialise();
		List<Modification> otherPattern = pattern.serialise();

		for (int i = 0; i < thisPattern.size(); i++) {
			Modification thisMod = thisPattern.get(i);
			Modification otherMod = otherPattern.get(i);
			if (!thisMod.equals(otherMod)) {
				if (!thisMod.equals(UNSPECIFIED))
					return false;
			}
		}

		return true;
	}

}
