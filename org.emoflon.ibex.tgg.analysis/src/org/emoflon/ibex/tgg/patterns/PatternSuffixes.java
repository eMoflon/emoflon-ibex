package org.emoflon.ibex.tgg.patterns;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;

/**
 * All suffixes used to distinguish the different patterns based on their names.
 * 
 * The context of a pattern is the part of the pattern that consists of the
 * context elements of the original TGG rule.
 * 
 * The source/target/corr components of a pattern are the parts of the pattern
 * that consist of source/target/corr elements of the original TGG rule.
 * 
 * DEC refers to the "Dangling Edge Condition", i.e., the presence of edges that
 * can never be translated caused by applying a certain rule (thus violating
 * DEC).
 * 
 * Our synchronisation "protocol" is an extra model that provides markers for a
 * model triple. If all elements are marked (connected to protocol nodes) in
 * this manner then it is consistent.
 * 
 * @author anthony.anjorin
 */
public class PatternSuffixes {

	/** Used to separate the suffix from the name of the pattern */
	public static final String SEP = "__";

	/** Used for user-defined NACs */
	public static final String USER_NAC = SEP + "USER_NAC";

	public static final String FILTER_NAC_SOURCE = SEP + "FILTER_NAC_SRC";
	public static final String FILTER_NAC_TARGET = SEP + "FILTER_NAC_TRG";

	public static final String PAC = SEP + "PAC";

	/** Used for edge patterns */
	public static final String EDGE = SEP + "EDGE";

	/**
	 * Contains the entire context of the original TGG rule and invocations to the
	 * corresponding patterns of super-rules.
	 */
	public static final String GEN_REFINEMENT_INVOCATIONS = SEP + "GEN_REFINEMENT_INVOCATIONS";

	/**
	 * Contains the entire context of the original TGG rule together with
	 * multiplicity NACs. Used for applying the original TGG rules, i.e., for the
	 * model generator.
	 */
	public static final String GEN = SEP + "GEN";

	/**
	 * Identifier for patterns containing a NAC for an axiom rule
	 */
	public static final String GEN_AXIOM_NAC = GEN + "_AXIOM_NAC";

	/**
	 * If this pattern matches, then the corresponding TGG forward rule is
	 * applicable. This means that (i) the entire source component, corr-context,
	 * and target context can be matched and have been marked, and (ii) the source
	 * elements created by the rule can be translated, i.e., are not already marked.
	 */
	public static final String FWD = SEP + "FWD";

	/**
	 * As forward pattern, but applicable for linear optimization techniques.
	 */
	public static final String FWD_OPT = SEP + "FWD_OPT";

	/**
	 * If this pattern matches, then the corresponding TGG backward rule is
	 * applicable. This means that (i) the entire target component, corr-context,
	 * and source context can be matched and have been marked, and (ii) the target
	 * elements created by the rule can be translated, i.e., are not already marked.
	 */
	public static final String BWD = SEP + "BWD";

	/**
	 * As backward pattern, but applicable for linear optimisation techniques.
	 */
	public static final String BWD_OPT = SEP + "BWD_OPT";

	/**
	 * Matches of these patterns are collected as soon as a TGG rule has been
	 * successfully applied. Used to react to broken matches in the revoke phase of
	 * synchronisation, and also to start-up after loading the model triple and its
	 * protocol.
	 */
	public static final String CONSISTENCY = SEP + "CONSISTENCY";

	/**
	 * Used for consistency checking. Represents the context for creating potential
	 * correspondence links between existing source and target fragments.
	 */
	public static final String CC = SEP + "CC";

	/**
	 * Contains the entire GEN pattern (together with all its invocations) of the
	 * original TGG rule. This pattern is needed to ensure maximality for CC of
	 * complement rules is met.
	 */
	public static final String GENForCC = SEP + "GenForCC";

	/**
	 * Used for consistency checking of existing links. Represents the context for
	 * correspondence links between existing source and target fragments.
	 */
	public static final String CO = SEP + "CO";

	/**
	 * Contains the entire GEN pattern (together with all its invocations) of the
	 * original TGG rule. This pattern is needed to ensure maximality for CO of
	 * complement rules is met.
	 */
	public static final String GENForCO = SEP + "GenForCO";

	/**
	 * Used for pattern invocation. Represents the source part of the rule
	 */
	public static final String SOURCE = SEP + "SOURCE";

	/**
	 * Used for pattern invocation. Represents the target part of the rule
	 */
	public static final String TARGET = SEP + "TARGET";


	/**
	 * Removes the suffix of a given pattern name.
	 * 
	 * @param name Name of the pattern
	 * @return Name without suffix
	 */
	public static String removeSuffix(String name) {
		if (name.indexOf(SEP) == -1)
			return name;
		return name.substring(0, name.indexOf(SEP));
	}
	
	public static String getPatternName(String name, OperationalisationMode mode) {
		return name + getPatternSuffix(mode);
	}

	public static String getPatternName(String name, PatternType type) {
		return name + getPatternSuffix(type);
	}

	public static PatternType extractType(String name) {
		if (name.lastIndexOf(SEP) == -1) {
			throw new RuntimeException(name + " is not a valid TGG pattern.");
		}
		String suffix = name.substring(name.lastIndexOf(SEP));
		return switch (suffix) {
		case FILTER_NAC_SOURCE -> PatternType.FILTER_NAC_SRC;
		case FILTER_NAC_TARGET -> PatternType.FILTER_NAC_TRG;
		case GEN -> PatternType.GEN;
		case GEN_AXIOM_NAC -> PatternType.GEN_AXIOM_NAC;
		case FWD -> PatternType.FWD;
		case FWD_OPT -> PatternType.FWD_OPT;
		case BWD -> PatternType.BWD;
		case BWD_OPT -> PatternType.BWD_OPT;
		case CONSISTENCY -> PatternType.CONSISTENCY;
		case CC -> PatternType.CC;
		case GENForCC -> PatternType.GENForCC;
		case CO -> PatternType.CO;
		case GENForCO -> PatternType.GENForCO;
		case SOURCE -> PatternType.SOURCE;
		case TARGET -> PatternType.TARGET;
		case PAC -> PatternType.PAC;
		default -> throw new RuntimeException(suffix + " is an unknown suffix for TGG patterns");
		};
	}
	
	private static String getPatternSuffix(PatternType type) {
		return switch(type) {
		case GEN -> GEN;
		case FWD -> FWD;
		case BWD -> BWD;
		case CO -> CO;
		case CC -> CC;
		case CONSISTENCY -> CONSISTENCY;
		case SOURCE -> SOURCE;
		case TARGET -> TARGET;
		case FILTER_NAC_SRC -> FILTER_NAC_SOURCE;
		case FILTER_NAC_TRG -> FILTER_NAC_TARGET;
		default -> throw new RuntimeException("Unkown pattern type detected " + type);
		};
	}

	private static String getPatternSuffix(OperationalisationMode mode) {
		return switch (mode) {
		case GENERATE -> GEN;
		case FORWARD -> FWD;
		case BACKWARD -> BWD;
		case CHECK_ONLY -> CO;
		case CONSISTENCY_CHECK -> CC;
		case CONSISTENCY -> CONSISTENCY;
		case SOURCE -> SOURCE;
		case TARGET -> TARGET;
		default -> throw new RuntimeException("Unkown operation mode detected " + mode);
		};
	}
}
