package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;


public class IBeXDisjunctPatternFinder {
	private Set<Set<IBeXNode>> currentSubgraphs;	
	private IBeXContextPattern thisPattern;
	private boolean isDisjunct;
	private Set<Set<IBeXNode>> allSubgraphs;

public IBeXDisjunctPatternFinder(IBeXContext pattern) {
	
	allSubgraphs = new HashSet<Set<IBeXNode>>();
	if(pattern instanceof IBeXContextAlternatives) {
		throw new IllegalArgumentException("alternative patterns can not be disjunct");
	}if(pattern instanceof IBeXDisjunctContextPattern) {
		isDisjunct = true;
		return;
	}
	thisPattern = (IBeXContextPattern) pattern;
	calculateGraph(thisPattern, thisPattern.getSignatureNodes());
	currentSubgraphs = checkDisjunctPatterns(thisPattern);
	isDisjunct = currentSubgraphs.size() >1;
}

/**
 * finds all nodes in a graph an maps the nodes if necessary; then finds the corresponding subgraphs
 * @param pattern
 * @return a map with the nodes and the connected subggraph
 */
private Map<IBeXNode, Pair<Set<IBeXNode>,Boolean>> calculateGraph(final IBeXContextPattern pattern, List<IBeXNode> signatureNodes) {
	Map<IBeXNode, Pair<Set<IBeXNode>, Boolean>> flattenedGraph = new HashMap<IBeXNode, Pair<Set<IBeXNode>, Boolean>>();
	//insert the pattern nodes into the flattened graph
	
	for(final IBeXNode node: pattern.getSignatureNodes()) {
		flattenedGraph.put(node, new Pair<Set<IBeXNode>, Boolean>(findReferences(node, pattern), Boolean.valueOf(false)));
	}
	for(final IBeXNode node: pattern.getLocalNodes()) {
		flattenedGraph.put(node, new Pair<Set<IBeXNode>, Boolean>(findReferences(node, pattern), Boolean.valueOf(false)));
	}
	
	//insert the condition nodes into the flattened graph
	if(!pattern.getInvocations().isEmpty()) {
		for(final IBeXPatternInvocation conditionPattern: pattern.getInvocations()){
			//recursively search for the subgraphs of the conditions and finds all nodes of the conditionGraph that are signature nodes of the pattern
			for(final Entry<IBeXNode, Pair<Set<IBeXNode>, Boolean>> entry: calculateGraph(conditionPattern.getInvokedPattern(), new ArrayList<IBeXNode>(conditionPattern.getMapping().keySet())).entrySet()) {
				List<IBeXNode> keyNode = flattenedGraph.keySet().stream()
					.filter(n -> n.getName().equals(entry.getKey().getName()) && signatureNodes.contains(n)).collect(Collectors.toList());
				
				if(keyNode.size() >1) System.out.println("There was an error when calculating subpatterns");
				else if(keyNode.size() == 1) {
					flattenedGraph.computeIfPresent(keyNode.get(0), (k, v) ->{
						Set<IBeXNode> references = v.getLeft();
						references.addAll(entry.getValue().getLeft());
						//the local node is also put into the references for later use
						references.add(entry.getKey());
						v.setLeft(references);
						return v;
					});
				}
				else {
					flattenedGraph.put(entry.getKey(), entry.getValue());
				}
			}			
			
		}
	}
	findDisjunctSubpatterns(flattenedGraph, signatureNodes);
	return flattenedGraph;
}
	
/**
 * finds all disjunct patterns in a pattern
 * @return
 */
private void findDisjunctSubpatterns(final Map<IBeXNode, Pair<Set<IBeXNode>, Boolean>> flattenedGraph, final List<IBeXNode> signatureNodes) {
	for(final IBeXNode node: flattenedGraph.keySet()) {
		//checks if node is already in a subgraph
		if(flattenedGraph.get(node).getRight().booleanValue()) continue;
		else {
			//do a breadth-first search
			searchSubgraph(flattenedGraph, signatureNodes, node);
		}
	}						
}

/**
 * searches all connected nodes in a graph that the node can access
 * 
 * @param allNodes all nodes in the graph
 * @param node the source node
 * @return all nodes that the source node can access directly or indirectly
 */
private void searchSubgraph(final Map<IBeXNode, Pair<Set<IBeXNode>, Boolean>> flattenedGraph, final List<IBeXNode> signatureNodes, final IBeXNode node) {
	Queue<IBeXNode> nodeQueue = new LinkedList<IBeXNode>();
	Set<IBeXNode> subGraph = new HashSet<IBeXNode>();
	//saves all visited nodes which were visited to avoid cycles
	Set<IBeXNode> visitedNodes = new HashSet<IBeXNode>();
	nodeQueue.add(node);
	
	while(!nodeQueue.isEmpty()) {
		IBeXNode currentNode = nodeQueue.poll();
		//breaks free from cycles and if the node cant be found in the graph
		if(visitedNodes.contains(currentNode)||!flattenedGraph.containsKey(currentNode)&&
				!signatureNodes.stream().anyMatch(n -> n.getName().equals(currentNode.getName()))) continue;
		else {
			visitedNodes.add(currentNode);
			Pair<Set<IBeXNode>, Boolean> currentValue = findNodeInGraph(flattenedGraph, currentNode, signatureNodes);
			
			//check if the node was visited in another subgraph
			if(currentValue.getRight().booleanValue()) {
				subGraph.addAll(currentValue.getLeft());
				visitedNodes.addAll(currentValue.getLeft());
				allSubgraphs.remove(currentValue.getLeft());
			}
			else {
				nodeQueue.addAll(currentValue.getLeft());
				setTrue(currentNode, flattenedGraph, signatureNodes);
				
				subGraph.add(currentNode);	
			}

		}
	}
	subGraph.forEach(n -> {
		if(flattenedGraph.containsKey(n)) {
			flattenedGraph.replace(n, new Pair<Set<IBeXNode>, Boolean>(subGraph,Boolean.valueOf(true)));
		}		
	});
	allSubgraphs.add(subGraph);		
}	

/**
 * finds the value of a node even if it is a local node overwritten by a signature node
 */
private Pair<Set<IBeXNode>, Boolean> findNodeInGraph(final Map<IBeXNode, Pair<Set<IBeXNode>, Boolean>> flattenedGraph,
		final IBeXNode node, final List<IBeXNode> signatureNodes) {
	if(flattenedGraph.containsKey(node)) {
		return flattenedGraph.get(node);
	}
	else {
		List<IBeXNode> signatureNode = signatureNodes.stream().filter(n -> n.getName().equals(node.getName())).collect(Collectors.toList());
		//find in all signature nodes which has the node
		if(signatureNode.size() == 1) {
			return flattenedGraph.get(signatureNode.get(0));
		}
	}
	throw new IllegalArgumentException("Something is wrong with the flattened Graph");
}

/**
 * finds the value of a node and sets that the node was visited
 */
void setTrue(final IBeXNode node, final Map<IBeXNode, Pair<Set<IBeXNode>, Boolean>> flattenedGraph, final List<IBeXNode> signatureNodes) {
	if(flattenedGraph.containsKey(node)) {
		flattenedGraph.compute(node, (k, v) -> {
			v.setRight(Boolean.valueOf(true));
			return v;
		});
	}
	else {
		List<IBeXNode> signatureNode = signatureNodes.stream().filter(n -> n.getName().equals(node.getName())).collect(Collectors.toList());
		//find in all signature nodes which has the node
		if(signatureNode.size() == 1) {
			flattenedGraph.compute(signatureNode.get(0), (k, v) -> {
				v.setRight(Boolean.valueOf(true));
				return v;
			});
		}else {
		throw new IllegalArgumentException("Something is wrong with the flattened Graph");
		}
	}
}

/**
 * find all subpatterns that have signatureNodes of the main pattern
 */
private Set<Set<IBeXNode>> checkDisjunctPatterns(final IBeXContextPattern pattern) {
	List<IBeXNode> signatureNodes = pattern.getSignatureNodes();
	// removes all subgraphs that do not have any signature nodes
	Set<Set<IBeXNode>> subgraphs = new HashSet<Set<IBeXNode>>();
	for(Set<IBeXNode> subpattern: allSubgraphs) {
		//subgraph should have at least one signature node
		if(!subpattern.stream().filter(n -> signatureNodes.contains(n)).collect(Collectors.toList()).isEmpty()) {
			subgraphs.add(subpattern);
		}
	}
	return subgraphs;
}

/**
 * finds all references that a node has to other nodes that will be later used in a context pattern
 */
private Set<IBeXNode> findReferences(final IBeXNode node, final IBeXContextPattern pattern) {
	Set<IBeXNode> references = new HashSet<IBeXNode>();
	references.addAll(node.getIncomingEdges().stream().map(edge -> edge.getSourceNode()).collect(Collectors.toSet()));
	references.addAll(node.getOutgoingEdges().stream().map(edge -> edge.getTargetNode()).collect(Collectors.toSet()));
	for(IBeXPatternInvocation invocation: pattern.getInvocations()) {
		references.addAll(invocation.getMapping().entrySet().stream().filter(entry -> entry.getKey().equals(node)).map(entry -> entry.getValue()).collect(Collectors.toSet()));
	}
	return references;
}

public boolean isDisjunct() {
	return isDisjunct;
}

public Set<Set<IBeXNode>> getSubgraphs(){
	return currentSubgraphs;
}
}
