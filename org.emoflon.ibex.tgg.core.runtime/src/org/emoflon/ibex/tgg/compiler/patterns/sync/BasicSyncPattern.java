package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.common.IBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.NacPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACHelper;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class BasicSyncPattern extends IbexBasePattern{
	
	protected BasicSyncPattern(BlackPatternFactory factory) {
		super(factory);
	}

	protected void addFilterNACPatterns(DomainType domain, BlackPatternFactory factory, IbexPatternOptimiser optimiser) {
		final Collection<IBlackPattern> filterNACs = new ArrayList<>();
		TGGRule rule = factory.getFlattenedVersionOfRule();
		
		for (TGGRuleNode n : rule.getNodes()) {
			EClass nodeClass = n.getType();

			if (nodeIsNotTranslatedByThisRule(n)) continue;
			if (nodeIsNotRelevant(domain, n)) continue;

			// Create DECPatterns as negative children in the network
			for (EReference eType : FilterACHelper.extractEReferences(nodeClass)) {
				for (EdgeDirection eDirection : EdgeDirection.values()) {
					TGG tgg = (TGG) rule.eContainer();

					if (typeDoesNotFitToDirection(n, eType, eDirection)) continue;
					if (onlyPossibleEdgeIsAlreadyTranslatedInRule(n, eType, eDirection, domain, factory)) continue;
					if (edgeIsNeverTranslatedInTGG(domain, eType, eDirection, tgg)) continue;
		
					// Collect all Filter NACs, but do not add them yet as negative invocations
					if(thereIsNoSavingRule(domain, eType, eDirection, tgg))
						filterNACs.add(factory.createFilterACPattern(n, eType, eDirection));					
				}
			}
		}
		
		// Use optimiser to remove some of the filter NACs
		final Collection<IBlackPattern> optimisedFilterNACs = filterNACs.stream()
							   .filter(nac -> !optimiser.isRedundantDueToEMFContainmentSemantics(nac))
							   .collect(Collectors.toList());
		
		optimisedFilterNACs.removeAll(optimiser.ignoreDueToEOppositeSemantics(optimisedFilterNACs));
		
		// Add all remaining filter NACs now as negative invocations
		addNegativeInvocations(optimisedFilterNACs);
	}

	private boolean thereIsNoSavingRule(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return determineSavingRules(domain, eType, eDirection, tgg).isEmpty();
	}

	private boolean edgeIsNeverTranslatedInTGG(DomainType domain, EReference eType, EdgeDirection eDirection, TGG tgg) {
		return !FilterACHelper.isEdgeInTGG(tgg, eType, eDirection, false, domain);
	}

	private boolean onlyPossibleEdgeIsAlreadyTranslatedInRule(TGGRuleNode n, EReference eType, EdgeDirection eDirection, DomainType domain, BlackPatternFactory factory) {
		int numOfEdges = FilterACHelper.countEdgeInRule(factory.getFlattenedVersionOfRule(), n, eType, eDirection, false, domain).getLeft();
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
	
	protected Collection<IBlackPattern> collectGeneratedNACs(BlackPatternFactory factory, DomainType domain1, DomainType domain2) {
		Collection<IBlackPattern> nacs = factory.createPatternsForMultiplicityConstraints();
		nacs.addAll(factory.createPatternsForContainmentReferenceConstraints());
		
		return nacs.stream().filter(n -> {
			Optional<TGGRuleNode> e = ((NacPattern)n).getSignatureNodes().stream().findAny();
			DomainType domain = domain1;
			if (e.isPresent()) {
				domain = e.get().getDomainType();
			}
			
			return domain.equals(domain2);
		}).collect(Collectors.toList());
	}
	
	protected void createMarkedInvocations(boolean positive, DomainType domain) {
		for (TGGRuleElement el : getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(positive ? BindingType.CONTEXT : BindingType.CREATE) && node.getDomainType().equals(domain)) {
				IBlackPattern markedPattern = getPatternFactory().getMarkedPattern(node.getDomainType(), true, false);
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

}
