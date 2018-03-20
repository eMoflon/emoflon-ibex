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

public class SrcContextPattern extends IbexBasePattern {

	public SrcContextPattern(BlackPatternFactory factory) {
		super(factory);
		initialise(factory.getRule(), factory.getOptions().setCorrContextNodesAsLocalNodes());
		createPatternNetwork(factory);
	}

	protected void initialise(TGGRule rule, boolean setCorrContextNodesAsLocalNodes) {
		String name = rule.getName() + PatternSuffixes.SRC_CONTEXT;

		Collection<TGGRuleNode> signatureNodes = rule.getNodes().stream().filter(this::isSignatureNode)
				.collect(Collectors.toList());

		Collection<TGGRuleEdge> localEdges = rule.getEdges().stream().filter(this::isLocalEdge)
				.collect(Collectors.toList());

		Collection<TGGRuleNode> localNodes;

		if (setCorrContextNodesAsLocalNodes) {
			localNodes = rule.getNodes().stream().filter(this::isContextCorr).collect(Collectors.toList());

			rule.getNodes().stream().filter(this::isContextCorr).map(TGGRuleCorr.class::cast)
					.forEach(corr -> extractSourceEdges(corr, localEdges));
		} else {
			localNodes = Collections.emptyList();
		}

		super.initialise(name, signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode n) {
		return n.getDomainType() == DomainType.SRC && n.getBindingType() == BindingType.CONTEXT;
	}

	private boolean isLocalEdge(TGGRuleEdge e) {
		return e.getDomainType() == DomainType.SRC && e.getBindingType() == BindingType.CONTEXT;
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

	public static void extractSourceEdges(TGGRuleCorr corr, Collection<TGGRuleEdge> localEdges) {
		TGGRuleEdge source = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		source.setBindingType(corr.getBindingType());
		source.setDomainType(DomainType.SRC);
		source.setName("source");
		source.setType((EReference) corr.getType().getEStructuralFeature("source"));
		source.setSrcNode(corr);
		source.setTrgNode(corr.getSource());
		localEdges.add(source);
	}
}
