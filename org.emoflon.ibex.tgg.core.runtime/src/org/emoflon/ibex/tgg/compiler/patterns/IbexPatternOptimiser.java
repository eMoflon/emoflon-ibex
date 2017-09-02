package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;

import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class IbexPatternOptimiser {

	/**
	 * This method takes a pair of nodes which potentially need an unequal-constraint
	 * and checks if this is really necessary.
	 * @param nodes The pair of nodes between which a unequal-constraint might be necessary.
	 * @return true, if the pair requires an unequal-constraint.
	 */
	public boolean unequalConstraintNecessary(Pair<TGGRuleNode, TGGRuleNode> nodes) {
		return !equalConstantAttributeValues(nodes)
			&& !transitiveContainment(nodes)
			&& !differentContainmentSubTrees(nodes);
	}

	private boolean equalConstantAttributeValues(Pair<TGGRuleNode, TGGRuleNode> nodes) {
		for (TGGInplaceAttributeExpression attrExprLeft : nodes.getLeft().getAttrExpr()) {
			if (attrExprLeft.getValueExpr() instanceof TGGLiteralExpression)
				for (TGGInplaceAttributeExpression attrExprRight : nodes.getRight().getAttrExpr()) {
					if (attrExprRight.getValueExpr() instanceof TGGLiteralExpression && attrExprLeft.getAttribute().equals(attrExprRight.getAttribute()))
						if (((TGGLiteralExpression)attrExprLeft.getValueExpr()).getValue().equals(((TGGLiteralExpression)attrExprRight.getValueExpr()).getValue())) {
							return true;
						}
				}
		}
		
		return false;
	}

	private boolean transitiveContainment(Pair<TGGRuleNode, TGGRuleNode> nodes) {
		List<TGGRuleNode> leftHierarchy = containmentHierarchyFromNodeToRoot(nodes.getLeft());
		List<TGGRuleNode> rightHierarchy = containmentHierarchyFromNodeToRoot(nodes.getRight());
		
		for (TGGRuleNode left : leftHierarchy) {
			if (left.equals(nodes.getRight()))
				return true;
		}
		for (TGGRuleNode right : rightHierarchy) {
			if (right.equals(nodes.getLeft()))
				return true;
		}
		
		return false;
	}

	private boolean differentContainmentSubTrees(Pair<TGGRuleNode, TGGRuleNode> nodes) {
		List<TGGRuleNode> leftHierarchy = containmentHierarchyFromNodeToRoot(nodes.getLeft());
		List<TGGRuleNode> rightHierarchy = containmentHierarchyFromNodeToRoot(nodes.getRight());
		
		// if one of the nodes is the root of its tree, both nodes cannot be in different sub-trees
		if (leftHierarchy.size() <= 1 || rightHierarchy.size() <= 1)
			return false;
		
		// if the roots of both nodes' trees are not equal, they are in unconnected trees,
		// which makes unequal-constraints necessary
		if (!leftHierarchy.get(leftHierarchy.size()-1).equals(rightHierarchy.get(rightHierarchy.size()-1)))
			return false;

		// if the parents of both nodes are equal,
		// they have to take care of checking unequality
		if (leftHierarchy.get(1).equals(rightHierarchy.get(1)))
			return false;
		
		// in the remaining cases, the nodes are in different but connected sub-trees
		// and their parent nodes transitively take care of the unequality
		return true;
	}
	
	private List<TGGRuleNode> containmentHierarchyFromNodeToRoot(TGGRuleNode node) {
		List<TGGRuleNode> hierarchy = new ArrayList<>();
		Optional<TGGRuleNode> possibleCurrentNode = Optional.of(node);
		
		while (possibleCurrentNode.isPresent()) {
			TGGRuleNode currentNode = possibleCurrentNode.get();
			hierarchy.add(currentNode);
			
			Optional<TGGRuleEdge> possibleContainmentEdge = currentNode.getIncomingEdges().stream()
																	.filter(e -> e.getType().isContainment())
																	.findAny();

			possibleCurrentNode = possibleContainmentEdge.map(e -> e.getSrcNode());
		}
		
		return hierarchy;
	}

	/**
	 * Used to avoid redundant reference constraints due to EMF eOpposite
	 * semantics.
	 * 
	 * @param e
	 * @param ibexPattern 
	 * @return true if e either does not have an eOpposite in the body edges, or
	 *         if it has an eOpposite but it is alphabetically "larger" than it.
	 */
	public boolean retainAsOpposite(TGGRuleEdge edge, IbexPattern ibexPattern) {
		EReference eOpposite = edge.getType().getEOpposite();
		
		// No eOpposite possible anyway
		if(eOpposite == null) return true;
				
		List<TGGRuleEdge> eOpposites = ibexPattern.getBodyEdges()
				.stream()
				.filter(otherEdge -> eOpposite.equals(otherEdge.getType()) &&
						             edge.getSrcNode().equals(otherEdge.getTrgNode()) &&
									 edge.getTrgNode().equals(otherEdge.getSrcNode()))
				.collect(Collectors.toList());
		
		// No eOpposite amongst bodyEdges
		if(eOpposites.isEmpty()) return true;
		
		// Not alphabetically larger than eOpposite
		if(edge.getType().getName().compareTo(eOpposite.getName()) > 0)
			return true;
		
		// If not can be skipped!
		return false;
	}

	/**
	 * If the premise of a filter NAC consists of three nodes and two edges of
	 * the same type and the edge type is containment then it can never be
	 * violated as a node cannot have two containers. In this case it is
	 * redundant.
	 * 
	 * @param filterNAC
	 * @return true if filter NAC is redundant
	 */
	public boolean isRedundantDueToEMFContainmentSemantics(IbexPattern filterNAC) {
		// Premise is the only positive invocation
		assert(filterNAC.getPositiveInvocations().size() == 1);
		IbexPattern premise = filterNAC.getPositiveInvocations().iterator().next();
		
		// Check for expected structure:  three nodes and two edges
		if(premise.getBodyNodes().size() == 3 && premise.getBodyEdges().size() == 2){
			Iterator<TGGRuleEdge> iterator = premise.getBodyEdges().iterator();
			TGGRuleEdge edge1 = iterator.next();
			TGGRuleEdge edge2 = iterator.next();
		
			// Edges must be of same type and be containment
			if(edge1.getType().equals(edge2.getType()) && edge1.getType().isContainment()){
				// Edges must contain the same node (which is impossible so filter NAC can be ignored)
				if(edge1.getTrgNode().equals(edge2.getTrgNode()))
					return true;
			}
		}
		
		return false;
	}

	/**
	 * If the premise of a filter NAC consists of two nodes connected by an edge e, and there
	 * exists another filter NAC with a premise with the same two nodes but with e' = eOpposte(e)
	 * then one of these filter NACs can be removed.
	 * 
	 * @param nac
	 * @return true If there exists another "eOpposite" 
	 */
	public boolean ignoreDueToEOppositeSemantics(IbexPattern filterNAC, Collection<IbexPattern> allFilterNACs) {
		// TODO[Anjorin]
		return false;
	}
	
}
