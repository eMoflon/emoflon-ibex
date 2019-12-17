package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.HashMap;
import java.util.Map;

public class Pattern2Type {

	private static Map<String, PatternType> patternName2type = new HashMap<>();
	
	public static void registerPattern(String patternName, PatternType type) {
		patternName2type.put(patternName, type);
	}

	public static PatternType getType(String patternName) {
		return patternName2type.get(patternName);
	}
}
