package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import com.sun.javafx.css.CalculatedValue;

import language.TGGRule;
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
			System.out.println("Rule " + rule.getName());
			// TODO:  "flatten kernel into complement rule"
			Collection<TGGRuleNode> kernelBodyNodes = rule.getKernel().getNodes();
			System.out.println("Kernel has " + kernelBodyNodes.size());
			Collection<TGGRuleNode> complementBodyNodes = rule.getNodes();
			System.out.println("Complement has " + rule.getNodes().size());
			for (TGGRuleNode kernalBodyNode : kernelBodyNodes) {
				System.out.println("Kernel node " + kernalBodyNode.getName());
				if(!complementBodyNodes.contains(kernalBodyNode)) {
					System.out.println("Unutra" + kernalBodyNode.getName());
					complementBodyNodes.add(kernalBodyNode);
					rule.getNodes().add(kernalBodyNode);
				}
			}
			
			//addTGGPositiveInvocation(factory.getFactory(kernelRule).create(ConsistencyPattern.class));
		}
		
	}
	
}
