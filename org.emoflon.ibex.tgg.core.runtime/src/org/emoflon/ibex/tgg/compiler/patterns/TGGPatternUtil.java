package org.emoflon.ibex.tgg.compiler.patterns;

public class TGGPatternUtil {
	public static final String protocolNodeSuffix = "_eMoflon_ProtocolNode";

	public static String getProtocolNodeName(String ruleName) {
		return ruleName + protocolNodeSuffix;
	}

	public final static boolean isAttrNode(String nodeName) {
		return nodeName.split("__").length == 3;
	}

	public static String getBWDFusedPatternName(String ruleName) {
		return ruleName + PatternSuffixes.BWD;
	}

	public static String getFWDBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.FWD;
	}

	public static String getBWDBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.BWD;
	}

	public static String getCOBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.CO;
	}

	public static String getCCBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.CC;
	}

	public static String getGENBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.GEN;
	}

	public static String getFWDOptBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.FWD_OPT;
	}

	public static String getBWDOptBlackPatternName(String ruleName) {
		return ruleName + PatternSuffixes.BWD_OPT;
	}

	/**
	 * Returns the axiom name encoded in the name of the rule
	 * 
	 * @param name The name of the pattern
	 * @return The part of the name representing the axiom name
	 */
	public static String extractGENAxiomNacName(String name) {
		return name.substring(0, name.indexOf(PatternSuffixes.SEP));
	}

	/**
	 * Checks if the given name matches the naming structure of a
	 * {@link GENAxiomNacPattern} by checking if the suffix matches.
	 * 
	 * @param name The name of the pattern to check
	 * @return true if the name has the suffix {@link PatternSuffixes}.GEN_AXIOM_NAC
	 */
	public static boolean isGENAxiomNacPattern(String name) {
		return name.endsWith(PatternSuffixes.GEN_AXIOM_NAC);
	}

	public static String getAxiomNACPatternName(String ruleName, String nacName) {
		return ruleName + PatternSuffixes.SEP + nacName + PatternSuffixes.GEN_AXIOM_NAC;
	}

	public static String getConsistencyPatternName(String ruleName) {
		return ruleName + PatternSuffixes.CONSISTENCY;
	}

	public static String getNACPatternName(String nacName) {
		return nacName + PatternSuffixes.NAC;
	}
}
