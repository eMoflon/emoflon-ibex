package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.TGGRule;

public class DECTrackingContainer {

	
	private Map<TGGRule, Collection<Pattern>> ruleToPatterns;
	
	// Map<DecPattern, Map<RuleName, Pair<entryPointName, DECNodeName>>>
	private Map<DECPattern, Map<String, Pair<String, String>>> signatureMapping;

	public DECTrackingContainer(Map<TGGRule, Collection<Pattern>> ruleToPatterns) {
		this.ruleToPatterns = ruleToPatterns;
		this.signatureMapping = new HashMap<>();
	}
	
	public Map<TGGRule, Collection<Pattern>> getRuleToPatternsMap() {
		return ruleToPatterns;
	}
	
	public void addToSignatureMapping(DECPattern pattern, String ruleName, String entryPointName, String DECNodeName) {
		Map<String, Pair<String, String>> mapping = signatureMapping.getOrDefault(pattern, new HashMap<>());
		signatureMapping.put(pattern, mapping);
		mapping.put(ruleName, Pair.of(entryPointName, DECNodeName));
	}
	
	public String getEntryPointName(DECPattern pattern, String ruleName) {
		return signatureMapping.getOrDefault(pattern, new HashMap<>()).getOrDefault(ruleName, Pair.of("", "")).getLeft();
	}
	
	public String getDECNodeName(DECPattern pattern, String ruleName) {
		return signatureMapping.getOrDefault(pattern, new HashMap<>()).getOrDefault(ruleName, Pair.of("", "")).getRight();
	}
}
