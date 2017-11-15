package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;

import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleNode;

public abstract class RulePartPattern extends IbexPattern {

	protected RulePartPattern(){
		super();
	}
	
	public RulePartPattern(TGGRule rule){
		super(rule);
	}


	public Collection<Pair<TGGRuleNode, TGGRuleNode>> getInjectivityChecks() {
		List<TGGRuleNode> nodes = new ArrayList<TGGRuleNode>(this.getBodyNodes());
		nodes.addAll(this.getSignatureElements().stream()
												.filter(e -> e instanceof TGGRuleNode)
												.map(TGGRuleNode.class::cast)
												.collect(Collectors.toList()));
		
		// Remove duplicates
		nodes = nodes.stream().distinct().collect(Collectors.toList());
		
		Collection<Pair<TGGRuleNode, TGGRuleNode>> injectivityCheckPairs = new ArrayList<>();
		IbexPatternOptimiser optimiser = new IbexPatternOptimiser();
		for(int i = 0; i < nodes.size(); i++){
			for(int j = i+1; j < nodes.size(); j++){
				TGGRuleNode nodeI = nodes.get(i);
				TGGRuleNode nodeJ = nodes.get(j);
				if(compatibleTypes(nodeI.getType(), nodeJ.getType())){
					if(!injectivityIsAlreadyChecked(nodeI, nodeJ)){
						if (optimiser.unequalConstraintNecessary(nodeI, nodeJ)) {
							injectivityCheckPairs.add(MutablePair.of(nodeI, nodeJ));
						}
					}
				}
			}
		}
		
		return injectivityCheckPairs;
	}
	
	/**
	 * Based on knowledge about how this pattern is invoked or used, you can
	 * choose to filter out pairs for which you know injectivity has already
	 * been checked. This speeds up the matching process as this pair of
	 * variables is excluded from {@link #getInjectivityChecks()} and does not
	 * have to be checked.
	 * 
	 * @param node1
	 * @param node2
	 * @return true if this pair can be excluded from the injectivity check for
	 *         this pattern.
	 */
	abstract protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2);
	

	private boolean compatibleTypes(EClass class1, EClass class2){
		return class1 == class2 || class1.getEAllSuperTypes().contains(class2) || class2.getEAllSuperTypes().contains(class1);
	}
	
	protected boolean isComplementRule() {
		return rule instanceof TGGComplementRule;
	}

}
