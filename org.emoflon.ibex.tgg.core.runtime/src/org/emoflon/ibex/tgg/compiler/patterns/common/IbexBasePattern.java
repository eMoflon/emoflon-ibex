package org.emoflon.ibex.tgg.compiler.patterns.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.IbexPatternOptimiser;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class IbexBasePattern implements IBlackPattern {
	protected String name;
	
	// These are the nodes that need to be mapped when invoking this pattern
	// Signature nodes are also the nodes that can be accessed from matches of this pattern
	protected Collection<TGGRuleNode> signatureNodes;
	
	protected BlackPatternFactory factory;
	
	// Local nodes and edges are all other elements in the pattern
	// Note that every node in the pattern is xor signature or local!
	protected Collection<TGGRuleNode> localNodes;
	protected Collection<TGGRuleEdge> localEdges;
	
	protected Collection<PatternInvocation> positiveInvocations;
	protected Collection<PatternInvocation> negativeInvocations;
	
	protected IbexPatternOptimiser optimiser;

	protected IbexBasePattern(BlackPatternFactory factory) {
		this.factory = factory;
		this.name = "NO_NAME";
		
		signatureNodes = new ArrayList<>();
		
		localNodes = new ArrayList<>();
		localEdges = new ArrayList<>();
		
		positiveInvocations = new ArrayList<>();
		negativeInvocations = new ArrayList<>();
		
		optimiser = new IbexPatternOptimiser();
	}

	/* Getters and setters */

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<TGGRuleNode> getSignatureNodes() {
		return signatureNodes;
	}
	
	@Override
	public Collection<TGGRuleNode> getLocalNodes() {
		return localNodes;
	}

	@Override
	public Collection<TGGRuleCorr> getAllCorrNodes() {
		return getAllNodes().stream()
						    .filter(TGGRuleCorr.class::isInstance)
						    .map(TGGRuleCorr.class::cast)
				            .collect(Collectors.toList());
	}

	@Override
	public Collection<TGGRuleEdge> getLocalEdges() {
		return localEdges;
	}

	@Override
	public Collection<TGGRuleNode> getAllNodes(){
		Collection<TGGRuleNode> allNodes = new ArrayList<>(signatureNodes);
		allNodes.addAll(localNodes);
		return allNodes;
	}
	
	@Override
	public Collection<PatternInvocation> getPositiveInvocations() {
		return positiveInvocations;
	}

	@Override
	public Collection<PatternInvocation> getNegativeInvocations() {
		return negativeInvocations;
	}
	
	public void addPositiveInvocation(IBlackPattern pattern, Map<TGGRuleNode, TGGRuleNode> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		positiveInvocations.add(pi);
	}

	public void addNegativeInvocation(IBlackPattern pattern, Map<TGGRuleNode, TGGRuleNode> mapping) {
		PatternInvocation pi = new PatternInvocation(this, pattern, mapping);
		negativeInvocations.add(pi);
	}
	
	public void addPositiveInvocation(IBlackPattern pattern) {
		PatternInvocation pi = new PatternInvocation(this, pattern, getNameBasedMapping(this, pattern));
		positiveInvocations.add(pi);
	}

	public void addNegativeInvocation(IBlackPattern pattern) {
		PatternInvocation pi = new PatternInvocation(this, pattern, getNameBasedMapping(this, pattern));
		negativeInvocations.add(pi);
	}
	
	public void addPositiveInvocations(Collection<IBlackPattern> patterns) {
		for (IBlackPattern p : patterns) addPositiveInvocation(p);
	}
	
	public void addNegativeInvocations(Collection<IBlackPattern> patterns) {
		for (IBlackPattern p : patterns) addNegativeInvocation(p);
	}

	/* Pattern initialisation */
	
	protected void initialise(String name, Collection<TGGRuleNode> signatureNodes, Collection<TGGRuleNode> localNodes, Collection<TGGRuleEdge> localEdges) {
		// Validation: local and signature nodes must be disjunct
		Collection<TGGRuleNode> intersection = new ArrayList<>(signatureNodes);
		intersection.retainAll(localNodes);
		if(!intersection.isEmpty())
			throw new IllegalArgumentException("Signature and local nodes must be disjunct but have an intersection: " + intersection);
		
		// Initialise pattern
		this.name = name;
		this.signatureNodes.addAll(signatureNodes);
		this.localNodes.addAll(localNodes);
		
		// Optimise: only add one of two opposite edges
		this.localEdges.addAll(
			localEdges.stream()
				     .filter(e -> optimiser.retainAsOpposite(e, this))
				     .collect(Collectors.toList()));		
	}
	
	/* Extra functionality */
	
	@Override
	public Collection<Pair<TGGRuleNode, TGGRuleNode>> getInjectivityChecks() {
		List<TGGRuleNode> nodes = new ArrayList<TGGRuleNode>(localNodes);
		nodes.addAll(signatureNodes);
		
		Collection<Pair<TGGRuleNode, TGGRuleNode>> injectivityCheckPairs = new ArrayList<>();
		IbexPatternOptimiser optimiser = new IbexPatternOptimiser();
		for(int i = 0; i < nodes.size(); i++){
			for(int j = i+1; j < nodes.size(); j++){
				TGGRuleNode nodeI = nodes.get(i);
				TGGRuleNode nodeJ = nodes.get(j);
				if(compatibleTypes(nodeI.getType(), nodeJ.getType()))
					if(!injectivityIsAlreadyChecked(nodeI, nodeJ))
						if (optimiser.unequalConstraintNecessary(nodeI, nodeJ))
							injectivityCheckPairs.add(MutablePair.of(nodeI, nodeJ));
			}
		}
		
		return injectivityCheckPairs;
	}
	
	private boolean compatibleTypes(EClass class1, EClass class2){
		return class1 == class2 || class1.getEAllSuperTypes().contains(class2) || class2.getEAllSuperTypes().contains(class1);
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
	
	public Map<TGGRuleNode, TGGRuleNode> getNameBasedMapping(IBlackPattern invoker, IBlackPattern invokee) {
		Map<TGGRuleNode, TGGRuleNode> mapping = new HashMap<>();
		Collection<TGGRuleNode> preimages = invoker.getAllNodes();
		Collection<TGGRuleNode> images = invokee.getSignatureNodes();

		for (TGGRuleNode image : images) {
			List<TGGRuleNode> preimage = preimages.stream()
			         .filter(preimg -> image.getName().equals(preimg.getName()))
			         .collect(Collectors.toList());
			
			if(preimage.isEmpty())
				throw new IllegalStateException("The node " + image.getName() + " is missing in the Pattern " + invoker.getName() + " [" + preimages + "]");
			
			if(preimage.size() != 1)
				throw new IllegalStateException("The node " + image.getName() + " does not have a unique preimage in the Pattern " + invoker.getName() + " [" + preimages + "]");
			
			mapping.put(preimage.get(0), image);
		}
		
		return mapping;
	}
	
	public final static String getVarName(TGGRuleNode node, EAttribute attr) {
		return "__" + node.getName() + "__" + attr.getName() + "__";
	}
	
	public final static Optional<Pair<String, String>> getNodeAndAttrFromVarName(String varName){
		String[] node_attr = varName.split("__");
		
		if(node_attr.length != 3)
			return Optional.empty();
		
		return Optional.of(Pair.of(node_attr[1], node_attr[2]));
	}
	
	public final static boolean isAttrNode(String nodeName) {
		return nodeName.split("__").length == 3;
	}
	
	@Override
	public final BlackPatternFactory getPatternFactory() {
		return factory;
	}
}
