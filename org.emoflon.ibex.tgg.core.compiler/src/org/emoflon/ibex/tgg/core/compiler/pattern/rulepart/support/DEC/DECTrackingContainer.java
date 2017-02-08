package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart.support.DEC;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.TGGRule;

public class DECTrackingContainer {

	private Map<TGGRule, Collection<Pattern>> ruleToPatterns;

	// Map<DecPattern, Map<RuleName, Pair<entryPointName, DECNodeName>>>
	private Map<DECPattern, Map<String, Pair<String, String>>> signatureMapping;
	private Map<DECPattern, Pair<String, String>> entryAndDecMap;

	public DECTrackingContainer(Map<TGGRule, Collection<Pattern>> ruleToPatterns) {
		this.ruleToPatterns = ruleToPatterns;
		this.signatureMapping = new HashMap<>();
		this.entryAndDecMap = new HashMap<>();
	}

	public Map<TGGRule, Collection<Pattern>> getRuleToPatternsMap() {
		return ruleToPatterns;
	}

	public void addToSignatureMapping(DECPattern pattern, String ruleName, String entryPointName, String DECNodeName) {
		Map<String, Pair<String, String>> mapping = signatureMapping.getOrDefault(pattern, new HashMap<>());
		signatureMapping.put(pattern, mapping);
		mapping.put(ruleName, Pair.of(entryPointName, DECNodeName));
	}

	public String getMappedEntryPointName(DECPattern pattern, String ruleName) {
		return signatureMapping.getOrDefault(pattern, new HashMap<>()).getOrDefault(ruleName, Pair.of("", "")).getLeft();
	}

	public String getMappedDECNodeName(DECPattern pattern, String ruleName) {
		return signatureMapping.getOrDefault(pattern, new HashMap<>()).getOrDefault(ruleName, Pair.of("", "")).getRight();
	}

	public void addEntryAndDec(DECPattern pattern, String entryPointName, String DECName) {
		entryAndDecMap.put(pattern, Pair.of(entryPointName, DECName));
	}

	public String getEntryPointName(DECPattern pattern) {
		return entryAndDecMap.get(pattern).getLeft();
	}

	public String getDECNodeName(DECPattern pattern) {
		return entryAndDecMap.get(pattern).getRight();
	}

	public Collection<String> getMapping(DECPattern pattern, Pattern subPattern) {
		String entryName = getEntryPointName(pattern);
		String decName = getDECNodeName(pattern);

		return subPattern.getSignatureElements().stream().map(e -> e.getName().equals(getMappedEntryPointName(pattern, subPattern.getRule().getName())) ? entryName
				: e.getName().equals(getMappedDECNodeName(pattern, subPattern.getRule().getName())) ? decName : "_").collect(Collectors.toList());
	}
}
