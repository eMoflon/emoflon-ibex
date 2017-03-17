package org.emoflon.ibex.tgg.compiler;

public class PatternSuffixes {
	
	//FIXME[anjorin] Extract "_" as global separation character. Then change to "__" instead of "_"!
	
	// These patterns contain source and target elements of a rule
	public static final String SRC = "_SRC";
	
	public static final String TRG = "_TRG";
	
	// These patterns are rule part patterns meaning that they consist of the context elements of their specific domain
	public static final String SRC_CONTEXT = "_SRC_CONTEXT";
	
	public static final String TRG_CONTEXT = "_TRG_CONTEXT";
	
	public static final String CORR_CONTEXT = "_CORR_CONTEXT";
	
	// A pattern that calls for the Whole pattern + consists of protocol nodes to check if created/translated elements have already been translated
	public static final String PROTOCOL = "_PROTOCOL";
	
	// This suffix indicates a pattern that is used to find consistency relations between two models
	public static final String CC = "_CC";
	
	// Forward and Backward rule suffix, e.g. for sync
	public static final String FWD = "_FWD";
	
	public static final String BWD = "_BWD";
	
	// This indicates a pattern that is used to generate models that conform to the rule specifications
	public static final String MODELGEN = "_MODELGEN";

	// Rules with this suffices are meant to cheat if translated elements in rules have not yet been marked
	public static final String SRC_PROTOCOL_NACS = "_SRC_PROTOCOL_NACS";
	
	public static final String TRG_PROTOCOL_NACS = "_TRG_PROTOCOL_NACS";
	
	// TODO
	public static final String SRC_PROTOCOL_PACS = "_SRC_PROTOCOL_PACS";
	
	public static final String TRG_PROTOCOL_PACS = "_TRG_PROTOCOL_PACS";
	
	// A pattern that consists of the whole tgg rule as a pattern
	public static final String WHOLE = "_WHOLE";

	// This suffix indicatzes a MarkedPattern where the protocol node is not placed in the signature
	// Such a rule calls for another MarkedRule (without this suffix) to find this protocol
	// Note that such pattern are, in contrast to those above, not generated for each rule both only once
	public static final String LOCAL_MARKED = "_LocalMarked";
	
	public static String removeSuffix(String name) {
		return name.substring(0, name.indexOf("_"));
	}	
}
