package org.emoflon.ibex.tgg.operational.strategies.integrate.classification;

import static org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification.COMPL_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification.PART_DEL;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification.UNCHANGED;
import static org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification.UNSPECIFIED;

import java.util.List;

import org.emoflon.ibex.tgg.operational.strategies.integrate.util.Modification;

public enum MCPattern {

	// DEL-Complete
	DEL_COMPLETE(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),

	// DEL-OneSided
	DEL_ONESIDED_FWD(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED),
	DEL_ONESIDED_BWD(UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),
	
	// DEL-PartlyOneSided
	DEL_PARTLYONESIDED_FWD(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, UNCHANGED, UNSPECIFIED),
	DEL_PARTLYONESIDED_BWD(UNCHANGED, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED),

	// DEL-OneSideIncomplete
	DEL_ONESIDEINCOMPL_FWD(COMPL_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED),
	DEL_ONESIDEINCOMPL_BWD(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, COMPL_DEL, UNSPECIFIED),

	// DEL-Partly
	DEL_PARTLY(PART_DEL, UNSPECIFIED, UNSPECIFIED, UNSPECIFIED, PART_DEL, UNSPECIFIED),

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
	private MCPattern(Modification srcCre, Modification srcCon, //
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
