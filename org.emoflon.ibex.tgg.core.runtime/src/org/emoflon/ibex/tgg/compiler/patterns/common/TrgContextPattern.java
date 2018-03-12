package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class TrgContextPattern extends IbexBasePattern {

	public TrgContextPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getRule(), factory.getCompiler().getOptions().setCorrContextNodesAsLocalNodes());
		createPatternNetwork(factory);
	}

	protected void initialise(TGGRule rule, boolean setCorrContextNodesAsLocalNodes) {
		String name = rule.getName() + PatternSuffixes.TRG_CONTEXT;

		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream().filter(this::isSignatureNode)
				.collect(Collectors.toList());

		Collection<TGGRuleEdge> localEdges = rule.getEdges().stream().filter(this::isLocalEdge)
				.collect(Collectors.toList());

		Collection<TGGRuleNode> localNodes;

		if (setCorrContextNodesAsLocalNodes) {
			localNodes = rule.getNodes().stream().filter(this::isContextCorr).collect(Collectors.toList());

			rule.getNodes().stream().filter(this::isContextCorr).map(TGGRuleCorr.class::cast)
					.forEach(corr -> extractTargetEdges(corr, localEdges));
		} else {
			localNodes = Collections.emptyList();
		}
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.TRG && n.getBindingType() == BindingType.CONTEXT;
	}

	protected boolean isLocalEdge(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.TRG && e.getBindingType() == BindingType.CONTEXT;
	}

	private void createPatternNetwork(BlackPatternFactory factory) {
		// Leaf pattern
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		// Leaf pattern so we have to check injectivity here
		return false;
	}

	protected boolean isContextCorr(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CONTEXT && n instanceof TGGRuleCorr;
	}

	public static void extractTargetEdges(TGGRuleCorr corr, Collection<TGGRuleEdge> localEdges) {
		TGGRuleEdge target = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		target.setBindingType(corr.getBindingType());
		target.setDomainType(DomainType.TRG);
		target.setName("target");
		target.setType((EReference) corr.getType().getEStructuralFeature("target"));
		target.setSrcNode(corr);
		target.setTrgNode(corr.getTarget());
		localEdges.add(target);
	}
}
