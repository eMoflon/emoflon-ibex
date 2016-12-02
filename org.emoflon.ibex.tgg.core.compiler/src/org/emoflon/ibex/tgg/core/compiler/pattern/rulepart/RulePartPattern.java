package org.emoflon.ibex.tgg.core.compiler.pattern.rulepart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.core.compiler.pattern.Pattern;

import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class RulePartPattern extends Pattern {

	
	
	/**
	 * each body node n corresponds to the following text:
	 * if n is a TGGRuleCorr:
	 *      <<Type of n>>.source(n, <<src of n>>);
	 *      <<Type of n>>.target(n, <<trg of n>>);
	 *      
	 * if n is not a TGGRuleCorr:
	 *      <<Type of n>>(n)     
	 * 		
	 */
	protected Collection<TGGRuleNode> bodyNodes = new ArrayList<>();
	
	
	/**
	 * each body edge e corresponds to the following text:
	 * 
	 *  Edge.src(e, <<src of n>>);
	 *  Edge.trg(e, <<trg of n>>);
	 *  Edge.name(e, <<type name of e>>);
	 */
	protected Collection<TGGRuleEdge> bodyEdges = new ArrayList<>();
	
	
	public RulePartPattern(TGGRule rule){
		super(rule);
		fillCollections(rule);		
	}

	private void fillCollections(TGGRule rule) {
		bodyNodes = calculateBodyNodes(signatureElements);
		bodyEdges = calculateBodyEdges(signatureElements);		
	}


	private Collection<TGGRuleEdge> calculateBodyEdges(Collection<TGGRuleElement> signatureElements){
		ArrayList<TGGRuleEdge> result = new ArrayList<>();
		signatureElements.stream().filter(e -> e instanceof TGGRuleEdge && isRelevantForBody((TGGRuleEdge)e)).forEach(e -> result.add((TGGRuleEdge) e));
		return result;
	}


	private Collection<TGGRuleNode> calculateBodyNodes(Collection<TGGRuleElement> signatureElements) {
		ArrayList<TGGRuleNode> result = new ArrayList<>();
		signatureElements.stream().filter(e -> e instanceof TGGRuleNode && isRelevantForBody((TGGRuleNode)e)).forEach(e -> result.add((TGGRuleNode) e));;
        return result;
	}

	protected Collection<TGGRuleElement> getSignatureElements(TGGRule rule){
		return Stream.concat(rule.getNodes().stream(), rule.getEdges().stream()).filter(e -> isRelevantForSignature(e)).collect(Collectors.toCollection(LinkedHashSet::new));
	}

	protected abstract boolean isRelevantForSignature(TGGRuleElement e);
	protected abstract boolean isRelevantForBody(TGGRuleEdge e);
	protected abstract boolean isRelevantForBody(TGGRuleNode n);
	protected abstract String getPatternNameSuffix();
	

	public Collection<TGGRuleNode> getBodyNodes() {
		return bodyNodes;
	}
	
	public Collection<TGGRuleCorr> getBodyCorrNodes(){
		Collection<TGGRuleCorr> corrs = new HashSet<>();
		bodyNodes.stream().filter(n -> n instanceof TGGRuleCorr).forEach(n -> corrs.add((TGGRuleCorr)n));
		return corrs;
	}
	
	public Collection<TGGRuleNode> getBodySrcTrgNodes(){
		Collection<TGGRuleNode> srcTrgNodes = new HashSet<TGGRuleNode>(bodyNodes);
		srcTrgNodes.removeAll(getBodyCorrNodes());
		return srcTrgNodes;
	}

	public Collection<TGGRuleEdge> getBodyEdges() {
		return bodyEdges;
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
