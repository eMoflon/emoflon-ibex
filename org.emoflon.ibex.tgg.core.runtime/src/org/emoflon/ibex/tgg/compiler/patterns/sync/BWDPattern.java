package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.NacPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class BWDPattern extends RulePartPattern {
	protected PatternFactory factory;

	public BWDPattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory);
	}

	private BWDPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		
		createPatternNetwork();
	}
	
	protected void createPatternNetwork() {
		// Rule Patterns
		addTGGPositiveInvocation(factory.create(BWDRefinementPattern.class));

		// Marked Patterns
		createMarkedInvocations(false);

		// FilterNACs
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		
		// NACs
		addTGGNegativeInvocations(collectGeneratedNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
	}
	
	protected Collection<IbexPattern> collectGeneratedNACs() {
		Collection<IbexPattern> nacs = factory.createPatternsForMultiplicityConstraints();
		nacs.addAll(factory.createPatternsForContainmentReferenceConstraints());
		
		return nacs.stream().filter(n -> {
			Optional<TGGRuleElement> e = ((NacPattern)n).getSignatureElements().stream().findAny();
			DomainType domain = DomainType.TRG;
			if (e.isPresent()) {
				domain = e.get().getDomainType();
			}
			
			return domain.equals(DomainType.SRC);
		}).collect(Collectors.toList());
	}

	protected void createMarkedInvocations(boolean positive) {
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && node.getDomainType().equals(DomainType.TRG)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().findFirst().get();

				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (positive)
					addCustomPositiveInvocation(markedPattern, mapping);
				else
					addCustomNegativeInvocation(markedPattern, mapping);
			}
		}
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.TRG || e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.BWD;
	}
	
	@Override
	public boolean ignored() {
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.noneMatch(e -> e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CREATE);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

}
