package org.emoflon.ibex.tgg.compiler.patterns.rulepart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPattern;

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
		for(int i = 0; i < nodes.size(); i++){
			for(int j = i+1; j < nodes.size(); j++){
				TGGRuleNode nodeI = nodes.get(i);
				TGGRuleNode nodeJ = nodes.get(j);
				if(compatibleTypes(nodeI.getType(), nodeJ.getType())){
					if(!injectivityIsAlreadyChecked(nodeI, nodeJ)){
						injectivityCheckPairs.add(MutablePair.of(nodeI, nodeJ));
					}
				}
			}
		}
		
		return injectivityCheckPairs;
	}
	
	abstract protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2);
	

	private boolean compatibleTypes(EClass class1, EClass class2){
		return class1 == class2 || class1.getEAllSuperTypes().contains(class2) || class2.getEAllSuperTypes().contains(class1);
	}

}
