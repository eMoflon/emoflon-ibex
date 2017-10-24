package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.NacPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class FWDPattern extends RulePartPattern {
	protected PatternFactory factory;

	public FWDPattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory);
	}

	private FWDPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		
		createPatternNetwork();
	}
	
	protected void createPatternNetwork() {
		// Rule Patterns
		addTGGPositiveInvocation(factory.create(FWDRefinementPattern.class));
		
		// Translation Patterns
		addTGGPositiveInvocation(factory.create(SrcTranslationAndFilterACsPattern.class));
		
		// NACs
		addTGGNegativeInvocations(collectGeneratedNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}
	
	protected Collection<IbexPattern> collectGeneratedNACs() {
		Collection<IbexPattern> nacs = factory.createPatternsForMultiplicityConstraints();
		nacs.addAll(factory.createPatternsForContainmentReferenceConstraints());
		
		return nacs.stream().filter(n -> {
			Optional<TGGRuleElement> e = ((NacPattern)n).getSignatureElements().stream().findAny();
			DomainType domain = DomainType.SRC;
			if (e.isPresent()) {
				domain = e.get().getDomainType();
			}
			
			return domain.equals(DomainType.TRG);
		}).collect(Collectors.toList());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getDomainType() == DomainType.SRC || e.getBindingType() == BindingType.CONTEXT;
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
		return PatternSuffixes.FWD;
	}

	@Override
	public boolean ignored() {
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream())
				.noneMatch(e -> e.getDomainType() == DomainType.SRC && e.getBindingType() == BindingType.CREATE);
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return node1.getDomainType() == node2.getDomainType();
	}

}
