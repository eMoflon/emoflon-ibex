package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class GENForRefinementInvocationsPattern extends GENPattern {

	public GENForRefinementInvocationsPattern(PatternFactory factory) {
		super(factory);		
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(TrgContextPattern.class));
		
		if (rule.getKernel() != null) 
			addTGGPositiveInvocation(factory.getFactory(rule.getKernel()).create(ConsistencyPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(GENForRefinementInvocationsPattern.class));
	
	}
	
	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.GEN_REFINEMENT_INVOCATIONS;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		if (!rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)) {
			// if both nodes are from super-rule, then super-rule takes care of injectivity
			return true;
		} else if (rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)
				|| rule.getNodes().contains(node2) && !rule.getNodes().contains(node1)) {
			// if one node is from super-rule while the other is not, injectivity has probably (depending on invocations) not been checked yet
			return false;
		} else if (node1.getDomainType() != node2.getDomainType()) {
			// if nodes are from different domains, injectivity cannot have been checked yet
			return false;
		} else {
			// if both are from this rule and from the same domain, they have been checked in context-patterns
			return true;
		}
	}
	
	@Override
	protected void initialize() {

		super.initialize();
		
		if (rule.getKernel() != null) {
			Collection<TGGRuleElement> kernelContextNodes = getSignatureElements(rule.getKernel());
			kernelContextNodes.add(ConsistencyPattern.createProtocolNode(rule));
						
			for (TGGRuleElement kernelContextNode : kernelContextNodes) {
					this.getBodyNodes().add((TGGRuleNode)kernelContextNode); 
				
			}
		}
		
	}
	
}
