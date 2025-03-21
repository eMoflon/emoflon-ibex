package org.emoflon.ibex.tgg.patterns;

import java.util.HashMap;
import java.util.Map;

public class PatternUtil {

	private static Map<String, String> patternName2ruleName = new HashMap<>();
	private static Map<String, PatternType> patternName2type = new HashMap<>();
	
	public synchronized static void registerPattern(String patternName, PatternType type) {
		patternName2type.put(patternName, type);
		patternName2ruleName.put(patternName, PatternSuffixes.removeSuffix(patternName));
	}

	public static PatternType resolve(String patternName) {
		return patternName2type.get(patternName);
	}
	
	public static String getRuleName(String patternName) {
		return patternName2ruleName.get(patternName);
	}
}
