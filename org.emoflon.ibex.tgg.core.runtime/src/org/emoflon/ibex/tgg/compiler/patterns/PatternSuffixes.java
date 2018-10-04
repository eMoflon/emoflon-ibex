package org.emoflon.ibex.tgg.compiler.patterns;

import language.DomainType;

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

	/** Contains the entire source component of the pattern */
	public static final String SRC = SEP + "SRC";

	/**
	 * Contains the entire source of the original TGG rule and invocations to the
	 * corresponding patterns of super-rules.
	 */
	public static final String SRC_REFINEMENT_INVOCATIONS = SEP + "SRC_REFINEMENT_INVOCATIONS";

	/** Contains the entire target component of the pattern */
	public static final String TRG = SEP + "TRG";

	/** Contains the entire correspondence component of the pattern */
	public static final String CORR = SEP + "CORR";

	/**
	 * Contains the entire target of the original TGG rule and invocations to the
	 * corresponding patterns of super-rules.
	 */
	public static final String TRG_REFINEMENT_INVOCATIONS = SEP + "TRG_REFINEMENT_INVOCATIONS";

	/** Contains the context of the source component of the pattern */
	public static final String SRC_CONTEXT = SEP + "CONTEXT_SRC";

	/** Contains the context of the target component of the pattern */
	public static final String TRG_CONTEXT = SEP + "CONTEXT_TRG";

	/** Contains the context of the corr component of the pattern */
	public static final String CORR_CONTEXT = SEP + "CONTEXT_CORR";

	/** Used for constraints, currently only multiplicity NACs */
	public static final String CONSTRAINT = SEP + "CONSTRAINT_NAC";

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
	 * These patterns are used to enforce DEC in a domain
	 */
	public static String NO_FILTER_ACs(DomainType domain) {
		return SEP + "NO_FILTER_ACs_" + domain.getName();
	}

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
	 * Contains the entire corr-context and target-context of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String FWD_REFINEMENT_INVOCATIONS = SEP + "FWD_REFINEMENT_INVOCATIONS";

	/**
	 * As forward refinement invocations pattern, but applicable for linear
	 * optimization techniques.
	 */
	public static final String FWD_OPT_REFINEMENT_INVOCATIONS = SEP + "FWD_OPT_REFINEMENT_INVOCATIONS";

	/**
	 * If this pattern matches, then the corresponding TGG backward rule is
	 * applicable. This means that (i) the entire target component, corr-context,
	 * and source context can be matched and have been marked, and (ii) the target
	 * elements created by the rule can be translated, i.e., are not already marked.
	 */
	public static final String BWD = SEP + "BWD";

	/**
	 * As backward pattern, but applicable for linear optimization techniques.
	 */
	public static final String BWD_OPT = SEP + "BWD_OPT";

	/**
	 * Contains the entire corr-context and source-context of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String BWD_REFINEMENT_INVOCATIONS = SEP + "BWD_REFINEMENT_INVOCATIONS";

	/**
	 * As backward refinement invocations pattern, but applicable for linear
	 * optimization techniques.
	 */
	public static final String BWD_OPT_REFINEMENT_INVOCATIONS = SEP + "BWD_OPT_REFINEMENT_INVOCATIONS";

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
	 * Contains the entire original TGG rule except for the created corr-elements
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String CC_REFINEMENT_INVOCATIONS = SEP + "CC_REFINEMENT_INVOCATIONS";

	/**
	 * Contains the entire GEN pattern (together with all its invocations) of the
	 * original TGG rule. This pattern is needed to ensure maximality for CC of
	 * complement rules is met.
	 */
	public static final String GENForCC = "_GenForCC";
	
	/**
	 * Used for consistency checking of existing links. Represents the context for
	 * correspondence links between existing source and target fragments.
	 */
	public static final String CO = SEP + "CO";

	/**
	 * Contains the entire original TGG rule except for invocations to the
	 * corresponding patterns of super-rules.
	 */
	public static final String CO_REFINEMENT_INVOCATIONS = SEP + "CO_REFINEMENT_INVOCATIONS";

	/**
	 * Contains the entire GEN pattern (together with all its invocations) of the
	 * original TGG rule. This pattern is needed to ensure maximality for CO of
	 * complement rules is met.
	 */
	public static final String GENForCO = "_GenForCO";

	/**
	 * This suffix indicates a marked pattern with a local protocol node, i.e., the
	 * protocol node is not in the signature of the pattern. Such a pattern can be
	 * used to check if a certain element has been marked.
	 */
	public static final String LOCAL_MARKED = SEP + "LOCAL_MARKED";

	/**
	 * Removes the suffix of a given pattern name.
	 * 
	 * @param name
	 *            Name of the pattern
	 * @return Name without suffix
	 */
	public static String removeSuffix(String name) {
		if (name.indexOf(SEP) == -1)
			return name;
		return name.substring(0, name.indexOf(SEP));
	}

}
