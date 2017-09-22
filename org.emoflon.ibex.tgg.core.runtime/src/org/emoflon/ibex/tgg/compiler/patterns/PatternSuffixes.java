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
	 * Contains the entire source of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String SRC_REFINEMENT_INVOCATIONS = SEP + "SRC_REFINEMENT_INVOCATIONS";
	
	/** Contains the entire target component of the pattern */
	public static final String TRG = SEP + "TRG";

	/**
	 * Contains the entire target of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String TRG_REFINEMENT_INVOCATIONS = SEP + "TRG_REFINEMENT_INVOCATIONS";
	
	/** Contains the context of the source component of the pattern */
	public static final String SRC_CONTEXT = SEP + "CONTEXT_SRC";
	
	/** Contains the context of the target component of the pattern */
	public static final String TRG_CONTEXT = SEP + "CONTEXT_TRG";
	
	/** Contains the context of the corr component of the pattern */
	public static final String CORR_CONTEXT = SEP + "CONTEXT_CORR";

	/** Used for constraints, currently only multiplicity NACs */
	public static final String CONSTRAINT = SEP + "CONSTRAINT";

	/** 
	 * Contains the entire context of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */	
	public static final String MODELGEN_REFINEMENT_INVOCATIONS = SEP + "MODELGEN_REFINEMENT_INVOCATIONS";

	/** 
	 * Contains the entire context of the original TGG rule together with
	 * multiplicity NACs.
	 * Used for applying the original TGG rules, i.e., for the model generator. 
	 */
	public static final String GEN = SEP + "GEN";
	
	/**
	 * Forbid this pattern to ensure that all source elements created by the original
	 * rule have not yet been translated (marked)
	 */
	public static final String SRC_TRANSLATION_NACS = SEP + "TRANSLATION_NACS_SRC";
	
	/**
	 * Same meaning as SRC_PROTOCOL_NACS but this pattern is generated with a minor amount of signature elements.
	 * The intention is to be able to call SRC_PROTOCOL_NACS without generating to many local variables in the calling pattern
	 */
	public static final String LOCAL_SRC_TRANSLATION_NACS = SEP + "LOCAL_TRANSLATION_NACS_SRC";
	
	/**
	 * Forbid this pattern to ensure that all target elements created by the original
	 * rule have not yet been translated (marked)
	 */
	public static final String TRG_TRANSLATION_NACS = SEP + "TRANSLATION_NACS_TRG";
	
	/**
	 * Same meaning as TRG_PROTOCOL_NACS but this pattern is generated with a minor amount of signature elements.
	 * The intention is to be able to call TRG_PROTOCOL_NACS without generating to many local variables in the calling pattern
	 */
	public static final String LOCAL_TRG_TRANSLATION_NACS = SEP + "LOCAL_TRANSLATION_NACS_TRG";
	
	
	/**
	 * Used to check that all source context elements of the original rule are
	 * marked and that DEC is not violated.
	 */
	public static final String TRANSLATION_FILTER_AC_SRC = SEP + "TRANSLATION_FILTER_AC_SRC";

	/**
	 * Used to check that all target context elements of the original rule are
	 * marked and that DEC is not violated.
	 */
	public static final String TRANSLATION_FILTER_AC_TRG = SEP + "TRANSLATION_FILTER_AC_TRG";
	
	/**
	 * These patterns are used to enforce DEC in a domain
	 */
	public static String NO_FILTER_ACs(DomainType domain) {
		return SEP + "NO_FILTER_ACs_" + domain.getName();
	}	
	
	/**
	 * If this pattern matches, then the corresponding TGG forward rule is
	 * applicable. This means that (i) the entire source component,
	 * corr-context, and target context can be matched and have been marked, and
	 * (ii) the source elements created by the rule can be translated, i.e., are
	 * not already marked.
	 */
	public static final String FWD = SEP + "FWD";


	/**
	 * Contains the entire corr-context and target-context of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String FWD_REFINEMENT_INVOCATIONS= SEP + "FWD_REFINEMENT_INVOCATIONS";
	
	/**
	 * If this pattern matches, then the corresponding TGG backward rule is
	 * applicable. This means that (i) the entire target component,
	 * corr-context, and source context can be matched and have been marked, and
	 * (ii) the target elements created by the rule can be translated, i.e., are
	 * not already marked.
	 */
	public static final String BWD = SEP + "BWD";

	/**
	 * Contains the entire corr-context and source-context of the original TGG rule
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String BWD_REFINEMENT_INVOCATIONS= SEP + "BWD_REFINEMENT_INVOCATIONS";
	
	/**
	 * Matches of these patterns are collected as soon as a TGG rule has been
	 * successfully applied. Used to react to broken matches in the revoke phase
	 * of synchronisation, and also to start-up after loading the model triple
	 * and its protocol.
	 */
	public static final String CONSISTENCY = SEP + "CONSISTENCY";

	/**
	 * Used as part of PROTOCOL patterns.
	 */
	public static final String WHOLE = SEP + "WHOLE";
	
	/**
	 * Used for consistency checking. Represents the context for creating
	 * potential correspondence links between existing source and target
	 * fragments.
	 */
	public static final String CC = SEP + "CC";

	/**
	 * Contains the entire original TGG rule except for the created corr-elements
	 * and invocations to the corresponding patterns of super-rules.
	 */
	public static final String CC_REFINEMENT_INVOCATIONS = SEP + "CC_REFINEMENT_INVOCATIONS";

	/**
	 * This suffix indicates a marked pattern with a local protocol node, i.e.,
	 * the protocol node is not in the signature of the pattern. Such a pattern
	 * can be used to check if a certain element has been marked.
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
		return name.substring(0, name.indexOf(SEP));
	}

} 
