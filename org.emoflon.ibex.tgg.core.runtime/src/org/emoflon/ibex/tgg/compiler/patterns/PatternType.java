package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.Collection;
import java.util.LinkedList;

public enum PatternType {
		USER_NAC, FILTER_NAC, EDGE, GEN_REFINMENT_INVOCATIONS, GEN, GEN_AXIOM_NAC, FWD, FWD_OPT, BWD, BWD_OPT, CONSISTENCY, PROTOCOL, PROTOCOL_CORE,
		CC, GENForCC, CO, GENForCO;
		
		public static Collection<PatternType> getModelGENTypes() {
			Collection<PatternType> patternTypes = new LinkedList<>();
			patternTypes.add(PatternType.GEN);
			patternTypes.add(PatternType.GEN_AXIOM_NAC);
			return patternTypes;
		}
		
		public static Collection<PatternType> getIntegrateTypes() {
			Collection<PatternType> types = new LinkedList<>();
			types.add(PatternType.FWD);
			types.add(PatternType.BWD);
			types.add(PatternType.CONSISTENCY);
			types.add(PatternType.CC);
			types.add(PatternType.FILTER_NAC);
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
