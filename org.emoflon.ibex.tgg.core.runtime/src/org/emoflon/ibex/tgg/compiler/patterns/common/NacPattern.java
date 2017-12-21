package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;

import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class NacPattern extends IbexBasePattern {
	
	/**
	 * Creates a new ConstraintPattern. The body and signature are initialised
	 * with the given parameters.
	 * 
	 * @param rule
	 *            The {@link TGGRule} that this constraint belongs to.
	 * @param signatureElements
	 *            The {@link TGGElements} that shall be part of this
	 *            constraint's signature.
	 * @param bodyElements
	 *            The {@link TGGElements} that shall be part of this constraint,
	 *            but not part of the signature).
	 */
	public NacPattern(TGGRule rule, Collection<TGGRuleNode> signatureNodes, Collection<TGGRuleElement> localElements, String name) {
		initialise(rule, signatureNodes, localElements, name);
	}
	
	protected void initialise(TGGRule rule, Collection<TGGRuleNode> signatureNodes, Collection<TGGRuleElement> localElements, String n) {
		String name = rule.getName() + "_" + n + PatternSuffixes.CONSTRAINT;
		
		Collection<TGGRuleNode> localNodes = localElements.stream()
									 .filter(e -> e instanceof TGGRuleNode)
									 .map(e -> (TGGRuleNode)e)
									 .collect(Collectors.toSet());
		
		Collection<TGGRuleEdge> localEdges = localElements.stream()
				 .filter(e -> e instanceof TGGRuleEdge)
				 .map(e -> (TGGRuleEdge)e)
				 .collect(Collectors.toSet());
				
		super.initialise(name, signatureNodes, localNodes, localEdges); 
	}

	/**
	 * When invoking a NAC we know that all signature elements have already been
	 * checked for injectivity in the invoking rule.
	 */
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return signatureNodes.contains(node1) && signatureNodes.contains(node2);
	}
	
	@Override
	public PatternFactory getPatternFactory() {
		return null;
	}
}
