package org.emoflon.ibex.tgg.compiler;

import language.DomainType;

/**
 * All suffixes used to distinguish the different patterns based on their names.
 * 
 * The context of a pattern is is the part of the pattern that consists of the
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
	
	/** Contains the entire target component of the pattern */
	public static final String TRG = SEP + "TRG";
	
	/** Contains the context of the source component of the pattern */
	public static final String SRC_CONTEXT = SEP + "CONTEXT_SRC";
	
	/** Contains the context of the target component of the pattern */
	public static final String TRG_CONTEXT = SEP + "CONTEXT_TRG";
	
	/** Contains the context of the corr component of the pattern */
	public static final String CORR_CONTEXT = SEP + "CONTEXT_CORR";

	public static final String CONSTRAINT = SEP + "CONSTRAINT";
	
	/** 
	 * Contains the entire context of the original TGG rule.  
	 * Used for applying the original TGG rules, i.e., for the model generator. 
	 */
	public static final String MODELGEN = SEP + "MODELGEN";

	/**
	 * Forbid this pattern to ensure that all source elements created by the original
	 * rule have not yet been translated (marked)
	 */
	public static final String SRC_PROTOCOL_NACS = SEP + "PROTOCOL_NACS_SRC";
	
	/**
	 * Forbid this pattern to ensure that all target elements created by the original
	 * rule have not yet been translated (marked)
	 */
	public static final String TRG_PROTOCOL_NACS = SEP + "PROTOCOL_NACS_TRG";
	
	/**
	 * Used to check that all source context elements of the original rule are
	 * marked and that DEC is not violated.
	 */
	public static final String PROTOCOL_DEC_SRC = SEP + "PROTOCOL_DEC_SRC";

	/**
	 * Used to check that all target context elements of the original rule are
	 * marked and that DEC is not violated.
	 */
	public static final String PROTOCOL_DEC_TRG = SEP + "PROTOCOL_DEC_TRG";
	
	/**
	 * These patterns are used to enforce DEC in a domain
	 */
	public static String NO_DEC(DomainType domain) {
		return SEP + "_NO_DECs_" + domain.getName();
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
	 * If this pattern matches, then the corresponding TGG backward rule is
	 * applicable. This means that (i) the entire target component,
	 * corr-context, and source context can be matched and have been marked, and
	 * (ii) the target elements created by the rule can be translated, i.e., are
	 * not already marked.
	 */
	public static final String BWD = SEP + "BWD";
	
	/**
	 * Matches of these patterns are collected as soon as a TGG rule has been
	 * successfully applied. Used to react to broken matches in the revoke phase
	 * of synchronisation, and also to start-up after loading the model triple
	 * and its protocol.
	 */
	public static final String PROTOCOL = SEP + "PROTOCOL";

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
