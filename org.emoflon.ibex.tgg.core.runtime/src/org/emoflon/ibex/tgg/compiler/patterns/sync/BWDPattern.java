package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class BWDPattern extends IbexBasePattern {
	protected PatternFactory factory;

	public BWDPattern(PatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork();
	}

	protected void initialise(TGGRule rule) {
		String name = rule.getName() + PatternSuffixes.BWD;

		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork() {
		// Rule Patterns
		addPositiveInvocation(factory.create(BWDRefinementPattern.class));

		// Marked Patterns
		createMarkedInvocations(false);

		// FilterNACs
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.TRG, factory, optimiser);
		
		// NACs
		addNegativeInvocations(collectGeneratedNACs());
		addNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
	}

	protected void createMarkedInvocations(boolean positive) {
		for (TGGRuleElement el : getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && node.getDomainType().equals(DomainType.TRG)) {
				IPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureNodes().stream().findFirst().get();

				Map<TGGRuleNode, TGGRuleNode> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (positive)
					addPositiveInvocation(markedPattern, mapping);
				else
					addNegativeInvocation(markedPattern, mapping);
			}
		}
	}
	
	private Collection<IPattern> collectGeneratedNACs() {
		Collection<IPattern> pattern = SyncACUtil.collectGeneratedNACs(factory, DomainType.TRG, DomainType.SRC);
		return pattern;
	}
	
	private void addFilterNACPatterns(DomainType domain, PatternFactory factory, IbexPatternOptimiser optimiser) {
		Collection<IPattern> optimisedFilterNACs = SyncACUtil.addFilterNACPatterns(domain, factory, optimiser);
		addNegativeInvocations(optimisedFilterNACs);
	}
	
	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.TRG || n.getBindingType() == BindingType.CONTEXT;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

}
