package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNodeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;

public class IBeXPatternOptimiser {

	/**
	 * Simple String comparison
	 * 
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
	 * This method takes a pair of nodes which potentially need an unequal-constraint and checks if this
	 * is really necessary.
	 * 
	 * @param nodes The pair of nodes between which a unequal-constraint might be necessary.
	 * @return true, if the pair requires an unequal-constraint.
	 */
	public static boolean unequalConstraintNecessary(IBeXNode left, IBeXNode right) {
		return 
				//!unequalConstantAttributeValues(left, right) && 
				!transitiveContainment(left, right) //
				&& !differentContainmentSubTrees(left, right);
	}

//	private static boolean unequalConstantAttributeValues(IBeXNode left, IBeXNode right) {
//		for (TGGInplaceAttributeExpression attrExprLeft : left.getAttrExpr()) {
//			if (attrExprLeft.getValueExpr() instanceof TGGLiteralExpression leftValExpr) {
//				for (TGGInplaceAttributeExpression attrExprRight : right.getAttrExpr()) {
//					if (attrExprRight.getValueExpr() instanceof TGGLiteralExpression rightValExpr
//							&& attrExprLeft.getAttribute().equals(attrExprRight.getAttribute())) {
//						if (!leftValExpr.getValue().equals(rightValExpr.getValue()))
//							return true;
//					}
//				}
//			}
//		}
//
//		return false;
//	}

	private static boolean transitiveContainment(IBeXNode left, IBeXNode right) {
		List<IBeXNode> leftHierarchy = containmentHierarchyFromNodeToRoot(left);
		List<IBeXNode> rightHierarchy = containmentHierarchyFromNodeToRoot(right);

		for (IBeXNode leftHierNode : leftHierarchy) {
			if (leftHierNode.equals(right))
				return true;
		}
		for (IBeXNode rightHierNode : rightHierarchy) {
			if (rightHierNode.equals(left))
				return true;
		}

		return false;
	}

	private static boolean differentContainmentSubTrees(IBeXNode left, IBeXNode right) {
		List<IBeXNode> leftHierarchy = containmentHierarchyFromNodeToRoot(left);
		List<IBeXNode> rightHierarchy = containmentHierarchyFromNodeToRoot(right);

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
		// they have to take care of checking inequality
		if (leftHierarchy.get(1).equals(rightHierarchy.get(1)))
			return false;

		// in the remaining cases, the nodes are in different but connected sub-trees
		// and their parent nodes transitively take care of the inequality
		return true;
	}

	private static List<IBeXNode> containmentHierarchyFromNodeToRoot(IBeXNode node) {
		List<IBeXNode> hierarchy = new ArrayList<>();
		Optional<IBeXNode> possibleCurrentNode = Optional.of(node);

		while (possibleCurrentNode.isPresent()) {
			IBeXNode currentNode = possibleCurrentNode.get();
			hierarchy.add(currentNode);

			Optional<IBeXEdge> possibleContainmentEdge = currentNode.getIncomingEdges().stream() //
					.filter(e -> e.getType().isContainment()) //
					.findAny();

			possibleCurrentNode = possibleContainmentEdge.map(e -> e.getSource());
		}

		return hierarchy;
	}

	public static void optimizeIBeXPattern(IBeXPattern invoker, IBeXPatternInvocation invocation) {
		optimizeEdges(invoker, invocation);
		optimizeInjectivityConstraints(invoker, invocation);
//		optimizeAttributeConstraints(invoker, invokee);
	}

//	private static void optimizeAttributeConstraints(IBeXPattern invoker, IBeXPattern invokee) {
//		Collection<IBeXAttributeConstraint> revokedConstraints = new ArrayList<>();
//		List<IBeXAttributeConstraint> constraints = invoker.getAttributeConstraint().stream() //
//				.filter(constraint -> (constraint.getLhs() instanceof IBeXAttributeExpression)) //
//				.collect(Collectors.toList());
//		for (IBeXAttributeConstraint constraint : constraints) {
//			if (invokee.getSignatureNodes().stream()
//					.anyMatch(n -> n.getName().equals(((IBeXAttributeExpression) constraint.getLhs()).getNode().getName()))) {
//				revokedConstraints.add(constraint);
//			}
//		}
//		EcoreUtil.deleteAll(revokedConstraints, true);
//		invoker.getAttributeConstraint().removeAll(revokedConstraints);
//	}

	private static void optimizeInjectivityConstraints(IBeXPattern invoker, IBeXPatternInvocation invocation) {
		Collection<BooleanExpression> revokedPairs = new ArrayList<>();
		Collection<IBeXNode> mappedElements = invocation.getMapping().stream().map(i -> i.getKey()).toList();
		
		for (var condition : invoker.getConditions()) {
			if(condition instanceof RelationalExpression rel) {
				if(rel.getLhs() instanceof IBeXNodeValue left && rel.getRhs() instanceof IBeXNodeValue right) {			
					if (mappedElements.contains(left.getNode()) && mappedElements.contains(right.getNode())) {
						revokedPairs.add(condition);
					}
				}
			}
		}
		EcoreUtil.deleteAll(revokedPairs, true);
		invoker.getConditions().removeAll(revokedPairs);
	}

	private static void optimizeEdges(IBeXPattern invoker, IBeXPatternInvocation invocation) {
		var revokedEdges = new ArrayList<EObject>();
		var invokee = invocation.getInvocation();

		for (IBeXEdge edge : invoker.getEdges()) {
			IBeXNode srcNode = edge.getSource();
			IBeXNode trgNode = edge.getTarget();

			boolean foundInInvokee = invokee.getEdges().stream() //
					.anyMatch(e -> e.getSource().getName().equals(srcNode.getName()) && e.getTarget().getName().equals(trgNode.getName()));

			if (foundInInvokee) {
				revokedEdges.add(edge);
			}
		}
		EcoreUtil.deleteAll(revokedEdges, true);
		invoker.getEdges().removeAll(revokedEdges);
	}

}