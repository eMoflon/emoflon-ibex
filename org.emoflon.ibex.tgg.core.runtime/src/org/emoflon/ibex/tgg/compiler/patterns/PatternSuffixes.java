package org.emoflon.ibex.tgg.compiler.patterns;

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

	public static final String FILTER_NAC = SEP + "FILTER_NAC";
	
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
	 * This pattern contains the protocol node and all marked elements (marking edges)
	 */
	public static final String PROTOCOL = SEP + "PROTOCOL";
	
	/**
	 * This pattern contains a protocol node
	 */
	public static final String PROTOCOL_CORE = SEP + "PROTOCOL_CORE";
	
	/**
	 * Used as part of PROTOCOL patterns.
	 */
	public static final String WHOLE = SEP + "WHOLE";

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
	public static final String SRC = SEP + "SRC";
	
	/**
	 * Used for pattern invocation. Represents the target part of the rule
	 */
	public static final String TRG = SEP + "TRG";
	
	/**
	 * Used for pattern invocation. Represents the context part of the rule
	 */
	public static final String CONTEXT = SEP + "CONTEXT";
	
	/**
	 * Used for pattern invocation. Represents the create correspondence part of the rule
	 */
	public static final String GREENCORR = SEP + "GREENCORR";
	
	/**
	 * Used for pattern invocation. Represents a FWD-pattern and a GEN-pattern
	 */
	public static final String FWD_GREENCORR = SEP + "FWD_GREENCORR";
	
	/**
	 * Used for pattern invocation. Represents a BWD-pattern and a GEN-pattern
	 */
	public static final String BWD_GREENCORR = SEP + "BWD_GREENCORR";
	
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

	public static PatternType extractType(String name) {
		if (name.lastIndexOf(SEP) == -1) {
			throw new RuntimeException(name + " is not a valid TGG pattern.");
		}
		String suffix = name.substring(name.lastIndexOf(SEP));
		switch(suffix) {
		case USER_NAC: return PatternType.USER_NAC;
		case FILTER_NAC: return PatternType.FILTER_NAC;
		case EDGE: return PatternType.EDGE;
		case GEN_REFINEMENT_INVOCATIONS: return PatternType.GEN_REFINEMENT_INVOCATIONS;
		case GEN: return PatternType.GEN;
		case GEN_AXIOM_NAC: return PatternType.GEN_AXIOM_NAC;
		case FWD: return PatternType.FWD;
		case FWD_OPT: return PatternType.FWD_OPT;
		case BWD: return PatternType.BWD;
		case BWD_OPT: return PatternType.BWD_OPT;
		case CONSISTENCY: return PatternType.CONSISTENCY;
		case PROTOCOL: return PatternType.PROTOCOL;
		case PROTOCOL_CORE: return PatternType.PROTOCOL_CORE;
		case CC: return PatternType.CC;
		case GENForCC: return PatternType.GENForCC;
		case CO: return PatternType.CO;
		case GENForCO: return PatternType.GENForCO;
		case SRC: return PatternType.SRC;
		case TRG: return PatternType.TRG;
		case CONTEXT: return PatternType.CONTEXT;
		case FWD_GREENCORR: return PatternType.FWD_GREENCORR;
		case BWD_GREENCORR: return PatternType.BWD_GREENCORR;
		case GREENCORR: return PatternType.GREENCORR;
		default: throw new RuntimeException(suffix + "is an unknown suffix for TGG patterns");
		}
	}
}
