package org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.InvocationHelper;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.LocalSrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.LocalTrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import language.TGGRule;
import language.TGGRuleElement;

public class DECTrackingHelper {

	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns;
	private Map<IbexPattern, Collection<Triple<String, String, IbexPattern>>> localProtocolMapping;

	// Map<DecPattern, Map<RuleName, Pair<entryPointName, DECNodeName>>>
	private Map<DECPattern, Map<String, Pair<String, String>>> signatureMapping;
	private Map<DECPattern, Pair<String, String>> entryAndDecMap;

	public DECTrackingHelper(Map<TGGRule, Collection<IbexPattern>> ruleToPatterns) {
		this.ruleToPatterns = ruleToPatterns;
		this.signatureMapping = new HashMap<>();
		this.entryAndDecMap = new HashMap<>();
		this.localProtocolMapping = new HashMap<>();
	}

	private void addLocalProtocolMapping(IbexPattern globalProtocolPattern, String entryName, String edgeName, EdgeDirection direction, IbexPattern localProtocolPattern) {
		localProtocolMapping.putIfAbsent(globalProtocolPattern, new ArrayList<>());
		localProtocolMapping.get(globalProtocolPattern).add(Triple.of(entryName, edgeName + "_" + direction.name(), localProtocolPattern));
	}

	/**
	 * returns a local protocol pattern. It may find one within
	 * localProtocolMapping or it creates a new one
	 */
	public IbexPattern getLocalProtocolPattern(IbexPattern decPattern, IbexPattern globalProtocolPattern, String entryName, String edgeName, EdgeDirection direction) {
		localProtocolMapping.putIfAbsent(globalProtocolPattern, new ArrayList<>());
		Optional<IbexPattern> localProtocol = localProtocolMapping.get(globalProtocolPattern).stream().filter(tr -> tr.getLeft().equals(entryName) && tr.getMiddle().equals(edgeName + "_" + direction.name()))
				.map(tr -> tr.getRight()).findFirst();
		if (localProtocol.isPresent())
			return localProtocol.get();
		IbexPattern newLocalProtocol = createLocalProtocolPattern(decPattern, globalProtocolPattern, entryName, edgeName, direction);
		return newLocalProtocol;
	}

	private IbexPattern createLocalProtocolPattern(IbexPattern decPattern, IbexPattern globalProtocolPattern, String entryName, String edgeName, EdgeDirection direction) {
		IbexPattern localProtocolPattern;

		Pair<TGGRuleElement, TGGRuleElement> entryPair = getMappedEntry((DECPattern) decPattern, globalProtocolPattern);
		Pair<TGGRuleElement, TGGRuleElement> decPair = getMappedDEC((DECPattern) decPattern, globalProtocolPattern);

		if (globalProtocolPattern instanceof SrcProtocolNACsPattern)
			localProtocolPattern = new LocalSrcProtocolNACsPattern(globalProtocolPattern, Arrays.asList(new TGGRuleElement[] { entryPair.getRight(), decPair.getRight() }));
		else if (globalProtocolPattern instanceof TrgProtocolNACsPattern)
			localProtocolPattern = new LocalTrgProtocolNACsPattern(globalProtocolPattern, Arrays.asList(new TGGRuleElement[] { entryPair.getRight(), decPair.getRight() }));
		else
			throw new RuntimeException("Local*ProtocolNACsPattern can only be generated for *ProtocolNACsPattern!");

		addLocalProtocolMapping(globalProtocolPattern, entryName, edgeName, direction, localProtocolPattern);

		return localProtocolPattern;
	}

	public Map<TGGRule, Collection<IbexPattern>> getRuleToPatternsMap() {
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

	public Collection<String> getMappedSignature(DECPattern pattern, IbexPattern subPattern) {
		String entryName = getEntryPointName(pattern);
		String decName = getDECNodeName(pattern);

		return subPattern.getSignatureElements().stream().map(
				e -> e.getName().equals(getMappedEntryPointName(pattern, subPattern.getRule().getName())) ? entryName : e.getName().equals(getMappedDECNodeName(pattern, subPattern.getRule().getName())) ? decName : "_")
				.collect(Collectors.toList());
	}

	public Map<TGGRuleElement, TGGRuleElement> getMapping(DECPattern pattern, IbexPattern subPattern) {
		Pair<TGGRuleElement, TGGRuleElement> mappedEntry = getMappedEntry(pattern, subPattern);
		Pair<TGGRuleElement, TGGRuleElement> mappedDEC = getMappedDEC(pattern, subPattern);
		Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
		mapping.put(mappedEntry.getLeft(), mappedEntry.getRight());
		mapping.put(mappedDEC.getLeft(), mappedDEC.getRight());
		return mapping;
	}

	public Pair<TGGRuleElement, TGGRuleElement> getMappedEntry(DECPattern pattern, IbexPattern subPattern) {
		String entryName = getEntryPointName(pattern);

		TGGRuleElement entryElement = pattern.getSignatureElements().stream().filter(n -> n.getName().equals(entryName)).findFirst().get();
		TGGRuleElement subEntryElement = subPattern.getSignatureElements().stream().filter(e -> e.getName().equals(getMappedEntryPointName(pattern, subPattern.getRule().getName()))).findFirst().get();

		return Pair.of(entryElement, subEntryElement);
	}

	public Pair<TGGRuleElement, TGGRuleElement> getMappedDEC(DECPattern pattern, IbexPattern subPattern) {
		String decName = getDECNodeName(pattern);

		TGGRuleElement decElement = pattern.getBodyNodes().stream().filter(n -> n.getName().equals(decName)).findFirst().get();
		TGGRuleElement subDecElement = subPattern.getSignatureElements().stream().filter(e -> e.getName().equals(getMappedDECNodeName(pattern, subPattern.getRule().getName()))).findFirst().get();

		return Pair.of(decElement, subDecElement);
	}

}
