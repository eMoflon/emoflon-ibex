package org.emoflon.ibex.tgg.patterns;

import java.util.Collection;
import java.util.LinkedList;

public enum PatternType {
		FILTER_NAC_SRC, FILTER_NAC_TRG, GEN, GEN_AXIOM_NAC, FWD, FWD_OPT, BWD, BWD_OPT, CONSISTENCY,
		CC, GENForCC, CO, GENForCO, SOURCE, TARGET, PAC;

	public static Collection<PatternType> getModelGENTypes() {
		Collection<PatternType> patternTypes = new LinkedList<>();
		patternTypes.add(PatternType.GEN);
		patternTypes.add(PatternType.GEN_AXIOM_NAC);
		return patternTypes;
	}

	public static Collection<PatternType> getIntegrateTypes() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.SOURCE);
		types.add(PatternType.TARGET);
		types.add(PatternType.FWD);
		types.add(PatternType.BWD);
		types.add(PatternType.CONSISTENCY);
//		types.add(PatternType.CC);
		types.add(PatternType.FILTER_NAC_SRC);
		types.add(PatternType.FILTER_NAC_TRG);
		return types;
	}

	public static Collection<PatternType> getSYNCTypes() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.BWD);
		types.add(PatternType.FWD);
		types.add(PatternType.CONSISTENCY);
		return types;
	}

	public static Collection<PatternType> getCCTypes() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.CC);
		types.add(PatternType.GENForCC);
		types.add(PatternType.CONSISTENCY);
		return types;
	}

	public static Collection<PatternType> getCOTypes() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.CO);
		types.add(PatternType.GENForCO);
		return types;
	}

	public static Collection<PatternType> getFWD_Op() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.FWD_OPT);
		return types;
	}

	public static Collection<PatternType> getBWD_Op() {
		Collection<PatternType> types = new LinkedList<>();
		types.add(PatternType.BWD_OPT);
		return types;
	}
}
  