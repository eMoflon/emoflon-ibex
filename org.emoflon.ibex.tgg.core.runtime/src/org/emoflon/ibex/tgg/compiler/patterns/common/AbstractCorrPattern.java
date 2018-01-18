package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EReference;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class AbstractCorrPattern extends IbexBasePattern {

	protected void extractSourceAndTargetEdges(TGGRuleCorr corr) {
		extractSourceAndTargetEdges(corr, localEdges);
	}
	
	public static void extractSourceAndTargetEdges(TGGRuleCorr corr, Collection<TGGRuleEdge> localEdges) {
		TGGRuleEdge source = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		source.setBindingType(corr.getBindingType());
		source.setDomainType(DomainType.SRC);
		source.setName("source");
		source.setType((EReference) corr.getType().getEStructuralFeature("source"));
		source.setSrcNode(corr);
		source.setTrgNode(corr.getSource());
		localEdges.add(source);

		TGGRuleEdge target = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		target.setBindingType(corr.getBindingType());
		target.setDomainType(DomainType.TRG);
		target.setName("target");
		target.setType((EReference) corr.getType().getEStructuralFeature("target"));
		target.setSrcNode(corr);
		target.setTrgNode(corr.getTarget());

		localEdges.add(target);
	}
	
	protected boolean isConnectedToACorr(TGGRuleNode n) {
		return Stream.concat(n.getIncomingCorrsSource().stream(), n.getIncomingCorrsTarget().stream())
			         .anyMatch(this::isCorr);
	}

	protected boolean isCorr(TGGRuleNode n) {
		return n instanceof TGGRuleCorr;
	}
	
	protected boolean isConnectedToAContextCorr(TGGRuleNode n) {
		return Stream.concat(n.getIncomingCorrsSource().stream(), n.getIncomingCorrsTarget().stream())
			         .anyMatch(this::isContextCorr);
	}

	protected boolean isContextCorr(TGGRuleNode n) {
		return n.getBindingType() == BindingType.CONTEXT && n instanceof TGGRuleCorr;
	}
}
