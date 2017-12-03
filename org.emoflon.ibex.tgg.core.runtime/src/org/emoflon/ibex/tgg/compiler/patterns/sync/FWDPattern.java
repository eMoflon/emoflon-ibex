package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.NacPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACHelper;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.TGG;
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
		
		// Marked Patterns
		createMarkedInvocations(false);

		// FilterNACs
		if(PatternFactory.strategy != FilterACStrategy.NONE)
			addFilterNACPatterns(DomainType.SRC);
		
		// NACs
		addTGGNegativeInvocations(collectGeneratedNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}
	
	protected Collection<IPattern> collectGeneratedNACs() {
		Collection<IPattern> nacs = factory.createPatternsForMultiplicityConstraints();
		nacs.addAll(factory.createPatternsForContainmentReferenceConstraints());
		
		return nacs.stream().filter(n -> {
			Optional<TGGRuleNode> e = ((NacPattern)n).getSignatureNodes().stream().findAny();
			DomainType domain = DomainType.SRC;
			if (e.isPresent()) {
				domain = e.get().getDomainType();
			}
			
			return domain.equals(DomainType.TRG);
		}).collect(Collectors.toList());
	}

	protected void createMarkedInvocations(boolean positive) {
		for (TGGRuleElement el : getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && node.getDomainType().equals(DomainType.SRC)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
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
	
	protected void addFilterNACPatterns(DomainType domain) {
		final Collection<IPattern> filterNACs = new ArrayList<>();
		
		for (TGGRuleNode n : rule.getNodes()) {
			EClass nodeClass = n.getType();

			if (nodeIsNotTranslatedByThisRule(n)) continue;
			if (nodeIsNotRelevant(domain, n)) continue;

			// Create DECPatterns as negative children in the network
			for (EReference eType : FilterACHelper.extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					if (typeDoesNotFitToDirection(n, eType, eDirection)) continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(n, eType, eDirection)) continue;
					if (edgeIsNeverTranslatedInTGG(domain, eType, eDirection, tgg)) continue;
		
					// Collect all Filter NACs, but do not add them yet as negative invocations
					if(thereIsNoSavingRule(domain, eType, eDirection, tgg))
						filterNACs.add(factory.createFilterACPattern(n, eType, eDirection));					
				}
			}
		}
		
		// Use optimiser to remove some of the filter NACs
		final Collection<IPattern> optimisedFilterNACs = filterNACs.stream()
							   .filter(nac -> !optimiser.isRedundantDueToEMFContainmentSemantics(nac))
							   .collect(Collectors.toList());
		
		optimisedFilterNACs.removeAll(optimiser.ignoreDueToEOppositeSemantics(optimisedFilterNACs));
		
		// Add all remaining filter NACs now as negative invocations
		addTGGNegativeInvocations(optimisedFilterNACs);
	}

	private boolean thereIsNoSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return determineSavingRules(domain, eType, eDirection, tgg).isEmpty();
	}

	private boolean edgeIsNeverTranslatedInTGG(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return !FilterACHelper.isEdgeInTGG(tgg, eType, eDirection, false, domain);
	}

	private boolean onlyPossibleEdgeIsAlreadyTranslatedInRule(TGGRuleNode n, EReference eType, EdgeDirection eDirection) {
		int numOfEdges = FilterACHelper.countEdgeInRule(rule, n, eType, eDirection, false, DomainType.SRC).getLeft();
		return eType.getUpperBound() == 1 && numOfEdges == 1;
	}

	private boolean typeDoesNotFitToDirection(TGGRuleNode n, EReference eType, EdgeDirection eDirection) {
		return !FilterACHelper.getType(eType, eDirection).equals(n.getType());
	}

	private boolean nodeIsNotTranslatedByThisRule(TGGRuleNode n) {
		return !n.getBindingType().equals(BindingType.CREATE);
	}

	private boolean nodeIsNotRelevant(DomainType domain, TGGRuleNode n) {
		return !n.getDomainType().equals(domain) || n.getDomainType().equals(DomainType.CORR);
	}

	private List<TGGRule> determineSavingRules(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return tgg.getRules().stream()
				.filter(r -> isSavingRule(domain, eType, eDirection, r))
				.collect(Collectors.toList());
	}

	private boolean isSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGGRule r) {
		return FilterACHelper.countEdgeInRule(r, eType, eDirection, true, domain).getLeft() > 0;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleNode e) {
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
