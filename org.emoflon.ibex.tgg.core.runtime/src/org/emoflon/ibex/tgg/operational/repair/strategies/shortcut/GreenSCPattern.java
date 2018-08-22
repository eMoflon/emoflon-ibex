package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class GreenSCPattern implements IGreenPattern {

	private OperationalShortcutRule oscRule;
	private OperationalStrategy strategy;
	
	
	public GreenSCPattern(OperationalStrategy strategy, OperationalShortcutRule oscRule) {
		this.oscRule = oscRule;
		this.strategy = strategy;
	}
	
	@Override
	public Collection<TGGRuleNode> getSrcNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.SRC);
	}

	@Override
	public Collection<TGGRuleEdge> getSrcEdges() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.SRC);
	}

	@Override
	public Collection<TGGRuleNode> getTrgNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.TRG);
	}

	@Override
	public Collection<TGGRuleEdge> getTrgEdges() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), DomainType.TRG);
	}

	@Override
	public Collection<TGGRuleCorr> getCorrNodes() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), DomainType.CORR).stream().map(n -> (TGGRuleCorr) n).collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleNode> getNodesMarkedByPattern() {
		return TGGCollectionUtil.filterNodes(oscRule.getScRule().getNodes(), BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getEdgesMarkedByPattern() {
		return TGGCollectionUtil.filterEdges(oscRule.getScRule().getEdges(), BindingType.CREATE);
	}

	@Override
	public Collection<TGGRuleEdge> getMarkedContextEdges() {
		return Collections.emptyList();
	}

	@Override
	public IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match) {
		return null;
	}

	@Override
	public boolean isToBeIgnored(IMatch match) {
		return false;
	}

	@Override
	public void createMarkers(String ruleName, IMatch match) {
		String oldRAName = ConsistencyPattern.getProtocolNodeName(oscRule.getScRule().getSourceRule().getName());
		TGGRuleApplication ra = (TGGRuleApplication) match.get(oldRAName);
		ra.setName(oscRule.getScRule().getTargetRule().getName());
		
		Stream<TGGRuleNode> greenSrcNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.SRC, BindingType.CREATE).stream();
		Stream<TGGRuleNode> greenTrgNodes =	oscRule.getScRule().getTargetRuleMappings(DomainType.TRG, BindingType.CREATE).stream();
		Stream<TGGRuleNode> greenCorrNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.CORR, BindingType.CREATE).stream();
		
		greenSrcNodes.forEach(n -> ra.getCreatedSrc().add((EObject) match.get(n.getName())));
		greenTrgNodes.forEach(n -> ra.getCreatedTrg().add((EObject) match.get(n.getName())));
		greenCorrNodes.forEach(n -> ra.getCreatedCorr().add((EObject) match.get(n.getName())));
		
		ra.getContextSrc().clear();
		ra.getContextTrg().clear();
		
		Collection<TGGRuleNode> blackSrcNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.SRC, BindingType.CONTEXT);
		Collection<TGGRuleNode> blackTrgNodes = oscRule.getScRule().getTargetRuleMappings(DomainType.TRG, BindingType.CONTEXT);
		
		blackSrcNodes.stream().forEach(n -> ra.getContextSrc().add((EObject) match.get(n.getName())));
		blackTrgNodes.stream().forEach(n -> ra.getContextTrg().add((EObject) match.get(n.getName())));
		
		match.getParameterNames().stream()
			.filter(n -> !IbexBasePattern.isAttrNode(n))
			.forEach(n -> ra.getNodeMappings().put(n, (EObject) match.get(n)));

		strategy.setIsRuleApplicationFinal(ra);
		match.put(ConsistencyPattern.getProtocolNodeName(oscRule.getScRule().getTargetRule().getName()), ra);
	}

}
