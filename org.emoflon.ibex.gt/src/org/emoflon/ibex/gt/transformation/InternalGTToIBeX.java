package org.emoflon.ibex.gt.transformation;

import java.util.Objects;
import java.util.Optional;

import GTLanguage.GTEdge;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternInvocation;

/**
 * Transformation from the internal GT model to IBeX Patterns.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class InternalGTToIBeX {

	/**
	 * Transforms a GTRule into an IBeXPattern with pattern invocations.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @return the IBeXPattern
	 */
	public static IBeXPattern transformRule(final GTRule gtRule) {
		return transformRule(gtRule, true);
	}

	/**
	 * Transforms a GTRule into an IBeXPattern.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @param useInvocations
	 *            whether to use invocations or not. If set to <code>false</false>,
	 *            one large pattern will be created, otherwise the pattern will use
	 *            invocations.
	 * @return the IBeXPattern
	 */
	public static IBeXPattern transformRule(final GTRule gtRule, final boolean useInvocations) {
		Objects.requireNonNull(gtRule, "rule must not be null!");
		IBeXPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		ibexPattern.setName(gtRule.getName());

		gtRule.getGraph().getNodes().forEach(gtNode -> {
			ibexPattern.getLocalNodes().add(transformNode(gtNode));
		});

		if (useInvocations) {
			gtRule.getGraph().getEdges().stream().map(edge -> edge.getType()).distinct() // all types of EReference
					.forEach(edgeType -> {
						IBeXPattern edgePattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
						edgePattern.setName("edge-" + edgeType.getEContainingClass().getName() + "-"
								+ edgeType.getName() + "-" + edgeType.getEReferenceType().getName());

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

		return ibexPattern;
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
