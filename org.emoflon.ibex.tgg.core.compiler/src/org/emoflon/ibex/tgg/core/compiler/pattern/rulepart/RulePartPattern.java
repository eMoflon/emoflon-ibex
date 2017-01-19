package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.TGGRule;
import language.TGGRuleNode;

public abstract class RulePartPattern extends Pattern {

	
	public RulePartPattern(TGGRule rule){
		super(rule);	
	}


	public Collection<Pair<TGGRuleNode, TGGRuleNode>> getInjectivityChecks() {
		List<TGGRuleNode> signatureNodes = signatureElements.stream().filter(e -> e instanceof TGGRuleNode)
				.map(e -> (TGGRuleNode) e).collect(Collectors.toList());
		Collection<Pair<TGGRuleNode, TGGRuleNode>> injectivityCheckPairs = new ArrayList<>();
		for(int i = 0; i < signatureNodes.size(); i++){
			for(int j = i+1; j < signatureNodes.size(); j++){
				TGGRuleNode nodeI = signatureNodes.get(i);
				TGGRuleNode nodeJ = signatureNodes.get(j);
				if(compatibleTypes(nodeI.getType(), nodeJ.getType())){
					if(!injectivityIsAlreadyCheckedBySubpattern(nodeI, nodeJ)){
						injectivityCheckPairs.add(MutablePair.of(nodeI, nodeJ));
					}
				}
			}
		}
		return injectivityCheckPairs;
	}
	
	abstract protected boolean injectivityIsAlreadyCheckedBySubpattern(TGGRuleNode node1, TGGRuleNode node2);
	

	private boolean compatibleTypes(EClass class1, EClass class2){
		return class1 == class2 || class1.getEAllSuperTypes().contains(class2) || class2.getEAllSuperTypes().contains(class1);
	}

}
