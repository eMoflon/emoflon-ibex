package org.emoflon.ibex.gt.engine.transformations;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;

import GTLanguage.GTEdge;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXPatternSet;

/**
 * Transformation from the internal GT model to IBeX Patterns.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class InternalGTToIBeX {

	/**
	 * Transforms a GTRule into a set of IBeXPatterns with pattern invocations.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @return the IBeXPatternSet
	 */
	public static IBeXPatternSet transformRule(final GTRule gtRule) {
		return transformRule(gtRule, true);
	}

	/**
	 * Transforms a GTRule into a set of IBeXPatterns.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @param useInvocations
	 *            whether to use invocations or not. If set to <code>false</code>,
	 *            one large pattern will be created, otherwise the pattern will use
	 *            invocations.
	 * @return the IBeXPatternSet
	 */
	public static IBeXPatternSet transformRule(final GTRule gtRule, final boolean useInvocations) {
		Objects.requireNonNull(gtRule, "rule must not be null!");

		IBeXPatternSet ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		IBeXPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		ibexPattern.setName(gtRule.getName());
		ibexPatternSet.getPatterns().add(ibexPattern);

		gtRule.getGraph().getNodes().stream() //
				.sorted((n1, n2) -> n1.getName().compareTo(n2.getName())) // alphabetically by name
				.forEach(gtNode -> {
					ibexPattern.getLocalNodes().add(transformNode(gtNode));
				});

		// Ensure that all nodes must be disjoint even if they have the same type.
		for (int i = 0; i < ibexPattern.getLocalNodes().size(); i++) {
			for (int j = i + 1; j < ibexPattern.getLocalNodes().size(); j++) {
				IBeXNode node1 = ibexPattern.getLocalNodes().get(i);
				IBeXNode node2 = ibexPattern.getLocalNodes().get(j);
				if (canInstancesBeTheSame(node1.getType(), node2.getType())) {
					IBeXNodePair nodePair = IBeXLanguageFactory.eINSTANCE.createIBeXNodePair();
					nodePair.getValues().add(node1);
					nodePair.getValues().add(node2);
					ibexPattern.getInjectivityConstraints().add(nodePair);
				}
			}
		}

		if (useInvocations) {
			gtRule.getGraph().getEdges().stream() //
					.map(edge -> edge.getType()).distinct() // all types of EReference
					.sorted((t1, t2) -> t1.getName().compareTo(t2.getName())) // in alphabetical order
					.forEach(edgeType -> {
						IBeXPattern edgePattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
						edgePattern.setName("edge-" + edgeType.getEContainingClass().getName() + "-"
								+ edgeType.getName() + "-" + edgeType.getEReferenceType().getName());
						ibexPatternSet.getPatterns().add(edgePattern);

						IBeXNode ibexSignatureSourceNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
						ibexSignatureSourceNode.setName("src");
						ibexSignatureSourceNode.setType(edgeType.getEContainingClass());
						edgePattern.getSignatureNodes().add(ibexSignatureSourceNode);

						IBeXNode ibexSignatureTargetNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
						ibexSignatureTargetNode.setName("trg");
						ibexSignatureTargetNode.setType(edgeType.getEReferenceType());
						edgePattern.getSignatureNodes().add(ibexSignatureTargetNode);

						IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
						ibexEdge.setSourceNode(ibexSignatureSourceNode);
						ibexEdge.setTargetNode(ibexSignatureTargetNode);
						ibexEdge.setType(edgeType);
						edgePattern.getLocalEdges().add(ibexEdge);

						gtRule.getGraph().getEdges().stream().filter(edge -> edgeType.equals(edge.getType()))
								.forEach(gtEdge -> {
									IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE
											.createIBeXPatternInvocation();
									invocation.setPositive(true);

									Optional<IBeXNode> ibexLocalSourceNode = findIBeXNodeWithName(ibexPattern,
											gtEdge.getSourceNode().getName());
									Optional<IBeXNode> ibexLocalTargetNode = findIBeXNodeWithName(ibexPattern,
											gtEdge.getTargetNode().getName());

									if (!ibexLocalSourceNode.isPresent()) {
										throw new IllegalStateException(
												"Could not find node " + gtEdge.getSourceNode().getName() + "!");
									}
									if (!ibexLocalTargetNode.isPresent()) {
										throw new IllegalStateException(
												"Could not find node " + gtEdge.getTargetNode().getName() + "!");
									}
									invocation.getMapping().put(ibexLocalSourceNode.get(), ibexSignatureSourceNode);
									invocation.getMapping().put(ibexLocalTargetNode.get(), ibexSignatureTargetNode);
									invocation.setInvokedPattern(edgePattern);
									ibexPattern.getInvocations().add(invocation);
								});
					});
		} else {
			// No invocations, so include all edges as well.
			gtRule.getGraph().getEdges().forEach(gtEdge -> {
				ibexPattern.getLocalEdges().add(transformEdge(gtEdge, ibexPattern));
			});
		}

		return ibexPatternSet;
	}

	private static boolean canInstancesBeTheSame(final EClass class1, final EClass class2) {
		return class1 == class2 || class1.getEAllSuperTypes().contains(class2)
				|| class2.getEAllSuperTypes().contains(class1);
	}

	private static IBeXNode transformNode(final GTNode gtNode) {
		IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		ibexNode.setName(gtNode.getName());
		ibexNode.setType(gtNode.getType());
		return ibexNode;
	}

	private static IBeXEdge transformEdge(final GTEdge gtEdge, final IBeXPattern ibexPattern) {
		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(gtEdge.getType());
		findIBeXNodeWithName(ibexPattern, gtEdge.getSourceNode().getName())
				.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
		findIBeXNodeWithName(ibexPattern, gtEdge.getTargetNode().getName())
				.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
		return ibexEdge;
	}

	/**
	 * Finds an IBeXNode with the given name in the given IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param name
	 *            the name to search for, must not be <code>null</code>
	 * @return an Optional for the IBeXNode
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return ibexPattern.getLocalNodes().stream().filter(n -> name.equals(n.getName())).findAny();
	}
}
