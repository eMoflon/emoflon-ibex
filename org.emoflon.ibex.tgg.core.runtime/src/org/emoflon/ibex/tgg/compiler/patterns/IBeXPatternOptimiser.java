package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNodePair;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class IBeXPatternOptimiser {

	/**
	 * Simple String comparison
	 * @author NilsWeidmann
	 *
	 */
	public static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
			return arg0.compareTo(arg1);
		}
	}

	/**
	 * This method takes a pair of nodes which potentially need an
	 * unequal-constraint and checks if this is really necessary.
	 * 
	 * @param nodes
	 *            The pair of nodes between which a unequal-constraint might be
	 *            necessary.
	 * @return true, if the pair requires an unequal-constraint.
	 */
	public static boolean unequalConstraintNecessary(TGGRuleNode left, TGGRuleNode right) {
		return !unequalConstantAttributeValues(left, right) && !transitiveContainment(left, right)
				&& !differentContainmentSubTrees(left, right);
	}

	private static boolean unequalConstantAttributeValues(TGGRuleNode left, TGGRuleNode right) {
		for (TGGInplaceAttributeExpression attrExprLeft : left.getAttrExpr()) {
			if (attrExprLeft.getValueExpr() instanceof TGGLiteralExpression)
				for (TGGInplaceAttributeExpression attrExprRight : right.getAttrExpr()) {
					if (attrExprRight.getValueExpr() instanceof TGGLiteralExpression
							&& attrExprLeft.getAttribute().equals(attrExprRight.getAttribute()))
						if (!((TGGLiteralExpression) attrExprLeft.getValueExpr()).getValue()
								.equals(((TGGLiteralExpression) attrExprRight.getValueExpr()).getValue())) {
							return true;
						}
				}
		}

		return false;
	}

	private static boolean transitiveContainment(TGGRuleNode left, TGGRuleNode right) {
		List<TGGRuleNode> leftHierarchy = containmentHierarchyFromNodeToRoot(left);
		List<TGGRuleNode> rightHierarchy = containmentHierarchyFromNodeToRoot(right);

		for (TGGRuleNode leftHierNode : leftHierarchy) {
			if (leftHierNode.equals(right))
				return true;
		}
		for (TGGRuleNode rightHierNode : rightHierarchy) {
			if (rightHierNode.equals(left))
				return true;
		}

		return false;
	}

	private static boolean differentContainmentSubTrees(TGGRuleNode left, TGGRuleNode right) {
		List<TGGRuleNode> leftHierarchy = containmentHierarchyFromNodeToRoot(left);
		List<TGGRuleNode> rightHierarchy = containmentHierarchyFromNodeToRoot(right);

		// if one of the nodes is the root of its tree, both nodes cannot be in
		// different sub-trees
		if (leftHierarchy.size() <= 1 || rightHierarchy.size() <= 1)
			return false;

		// if the roots of both nodes' trees are not equal, they are in unconnected
		// trees,
		// which makes unequal-constraints necessary
		if (!leftHierarchy.get(leftHierarchy.size() - 1).equals(rightHierarchy.get(rightHierarchy.size() - 1)))
			return false;

		// if the parents of both nodes are equal,
		// they have to take care of checking unequality
		if (leftHierarchy.get(1).equals(rightHierarchy.get(1)))
			return false;

		// in the remaining cases, the nodes are in different but connected sub-trees
		// and their parent nodes transitively take care of the unequality
		return true;
	}

	private static List<TGGRuleNode> containmentHierarchyFromNodeToRoot(TGGRuleNode node) {
		List<TGGRuleNode> hierarchy = new ArrayList<>();
		Optional<TGGRuleNode> possibleCurrentNode = Optional.of(node);

		while (possibleCurrentNode.isPresent()) {
			TGGRuleNode currentNode = possibleCurrentNode.get();
			hierarchy.add(currentNode);

			Optional<TGGRuleEdge> possibleContainmentEdge = currentNode.getIncomingEdges().stream()
					.filter(e -> e.getType().isContainment()).findAny();

			possibleCurrentNode = possibleContainmentEdge.map(e -> e.getSrcNode());
		}

		return hierarchy;
	}
	
	public static void optimizeIBeXPattern(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		optimizeEdges(invoker, invokee);
		optimizeInjectivityConstraints(invoker, invokee);
		optimizeAttributeConstraints(invoker, invokee);
	}

	private static void optimizeAttributeConstraints(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		Collection<IBeXAttributeConstraint> revokedConstraints = new ArrayList<>();
		for (IBeXAttributeConstraint constraint : invoker.getAttributeConstraint()) {
			if(invokee.getSignatureNodes().stream().anyMatch(n -> n.getName().equals(constraint.getNode().getName()))) {
				revokedConstraints.add(constraint);
			}
		}
		EcoreUtil.deleteAll(revokedConstraints, true);
		invoker.getAttributeConstraint().removeAll(revokedConstraints);
	}

	private static void optimizeInjectivityConstraints(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		Collection<IBeXNodePair> revokedPairs = new ArrayList<>();

		for(IBeXNodePair injPair : invoker.getInjectivityConstraints()) {
			IBeXNode first = injPair.getValues().get(0);
			IBeXNode second = injPair.getValues().get(1);
			boolean firstFoundInInvokee = invokee.getSignatureNodes().stream().anyMatch(n -> n.getName().equals(first.getName()));
			boolean secondFoundInInvokee = invokee.getSignatureNodes().stream().anyMatch(n -> n.getName().equals(second.getName()));
			
			if(firstFoundInInvokee && secondFoundInInvokee) {
				revokedPairs.add(injPair);
			}
		}
		EcoreUtil.deleteAll(revokedPairs, true);
		invoker.getInjectivityConstraints().removeAll(revokedPairs);
	}

	private static void optimizeEdges(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		Collection<IBeXEdge> revokedEdges = new ArrayList<>();

		for(IBeXEdge edge : invoker.getLocalEdges()) {
			IBeXNode srcNode = edge.getSourceNode();
			IBeXNode trgNode = edge.getTargetNode();
			
			boolean foundInInvokee = invokee.getLocalEdges()
					.stream()
					.anyMatch(e -> e.getSourceNode().getName().equals(srcNode.getName()) && e.getTargetNode().getName().equals(trgNode.getName()));
			
			if(foundInInvokee) {
				revokedEdges.add(edge);
			}
		}
		EcoreUtil.deleteAll(revokedEdges, true);
		invoker.getLocalEdges().removeAll(revokedEdges);
	}
	
	
}