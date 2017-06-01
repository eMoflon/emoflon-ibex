package org.emoflon.ibex.tgg.operational;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sun.xml.internal.txw2.output.StreamSerializer;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.csp.TGGAttributeConstraintLibrary;

public class RuleInfos {

	protected HashMap<String, Collection<TGGRuleNode>> greenSrcNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> greenTrgNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenSrcEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> greenTrgEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleCorr>> greenCorrNodes = new LinkedHashMap<>();

	protected HashMap<String, Collection<TGGRuleNode>> blackSrcNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleNode>> blackTrgNodes = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> blackSrcEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleEdge>> blackTrgEdges = new LinkedHashMap<>();
	protected HashMap<String, Collection<TGGRuleCorr>> blackCorrNodes = new LinkedHashMap<>();
	
	protected HashMap<String, HashMap<String, DomainType>> domainTypes = new HashMap<>();
	
	protected HashMap<String, TGGAttributeConstraintLibrary> rule2constraintLibrary = new LinkedHashMap<>();

	public RuleInfos(TGG tgg) {
		tgg.getRules().forEach(this::prepareRuleInfo);
	}

	private void prepareRuleInfo(TGGRule r) {
		String ruleName = r.getName();
		rule2constraintLibrary.put(r.getName(), r.getAttributeConditionLibrary());

		greenSrcNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));
		greenTrgNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));
		greenCorrNodes.put(ruleName,
				r.getNodes().stream()
						.filter(n -> n.getBindingType() == BindingType.CREATE && n.getDomainType() == DomainType.CORR)
						.map(n -> (TGGRuleCorr) n).collect(Collectors.toCollection(LinkedHashSet::new)));

		greenSrcEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CREATE && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		greenTrgEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CREATE && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackSrcNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackSrcEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.SRC)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackTrgNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackTrgEdges.put(ruleName,
				r.getEdges().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.TRG)
						.collect(Collectors.toCollection(LinkedHashSet::new)));

		blackCorrNodes.put(ruleName,
				r.getNodes().stream()
						.filter(e -> e.getBindingType() == BindingType.CONTEXT && e.getDomainType() == DomainType.CORR)
						.map(n -> (TGGRuleCorr) n).collect(Collectors.toCollection(LinkedHashSet::new)));
		
		HashMap<String, DomainType> domainTypesOfTheNodes = new HashMap<>();
		r.getNodes().forEach(n -> domainTypesOfTheNodes.put(n.getName(), n.getDomainType()));
		domainTypes.put(ruleName, domainTypesOfTheNodes);
		

	}

	protected Collection<TGGRuleNode> getGreenSrcNodes(String ruleName) {
		return greenSrcNodes.get(ruleName);
	}

	protected Collection<TGGRuleEdge> getGreenSrcEdges(String ruleName) {
		return greenSrcEdges.get(ruleName);
	}

	protected Collection<TGGRuleNode> getGreenTrgNodes(String ruleName) {
		return greenTrgNodes.get(ruleName);
	}

	protected Collection<TGGRuleEdge> getGreenTrgEdges(String ruleName) {
		return greenTrgEdges.get(ruleName);
	}

	protected Collection<TGGRuleNode> getBlackSrcNodes(String ruleName) {
		return blackSrcNodes.get(ruleName);
	}

	protected Collection<TGGRuleEdge> getBlackSrcEdges(String ruleName) {
		return blackSrcEdges.get(ruleName);
	}

	protected Collection<TGGRuleNode> getBlackTrgNodes(String ruleName) {
		return blackTrgNodes.get(ruleName);
	}

	protected Collection<TGGRuleEdge> getBlackTrgEdges(String ruleName) {
		return blackTrgEdges.get(ruleName);
	}

	protected Collection<TGGRuleCorr> getGreenCorrNodes(String ruleName) {
		return greenCorrNodes.get(ruleName);
	}

	protected Collection<TGGRuleCorr> getBlackCorrNodes(String ruleName) {
		return blackCorrNodes.get(ruleName);
	}
	
	protected TGGAttributeConstraintLibrary getRuleCSPConstraintLibrary(String ruleName){
		return rule2constraintLibrary.get(ruleName);
	}

	
	protected DomainType getDomainType(String ruleName, String nodeName){
		return domainTypes.get(ruleName).get(nodeName);
	}
}
