package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;

import GTLanguage.GTEdge;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXPatternSet;

/**
 * Transformation from the internal GT model to IBeX Patterns.
 */
public class InternalGTModelToIBeXPatternTransformation extends AbstractModelTransformation<GTRuleSet, IBeXPatternSet> {
	/**
	 * All IBeXPatterns created during the transformation.
	 */
	private List<IBeXPattern> ibexPatterns;

	/**
	 * Mapping between pattern names and the created IBeXPatterns.
	 */
	private HashMap<String, IBeXPattern> nameToIBeXPattern = new HashMap<String, IBeXPattern>();

	@Override
	public IBeXPatternSet transform(final GTRuleSet gtRuleSet) {
		this.ibexPatterns = new ArrayList<IBeXPattern>();
		gtRuleSet.getRules().forEach(gtRule -> this.transformRule(gtRule, true));

		IBeXPatternSet ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		ibexPatternSet.getPatterns().addAll(ibexPatterns.stream() //
				.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())) //
				.collect(Collectors.toList()));
		return ibexPatternSet;
	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add
	 */
	private void addPattern(final IBeXPattern ibexPattern) {
		this.ibexPatterns.add(ibexPattern);
		this.nameToIBeXPattern.put(ibexPattern.getName(), ibexPattern);
	}

	/**
	 * Transforms a GTRule into IBeXPatterns.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 * @param useInvocations
	 *            whether to use invocations or not. If set to <code>false</code>,
	 *            one large pattern will be created, otherwise the pattern will use
	 *            invocations.
	 */
	private void transformRule(final GTRule gtRule, final boolean useInvocations) {
		Objects.requireNonNull(gtRule, "rule must not be null!");

		IBeXPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		ibexPattern.setName(gtRule.getName());

		gtRule.getGraph().getNodes().stream() //
				.sorted((n1, n2) -> n1.getName().compareTo(n2.getName())) // alphabetically by name
				.forEach(gtNode -> {
					IBeXNode ibexNode = this.transformNode(gtNode);
					if (gtNode.isLocal()) {
						ibexPattern.getLocalNodes().add(ibexNode);
					} else {
						ibexPattern.getSignatureNodes().add(ibexNode);
					}
				});

		// Ensure that all nodes must be disjoint even if they have the same type.
		List<IBeXNode> allNodes = new ArrayList<IBeXNode>();
		allNodes.addAll(ibexPattern.getLocalNodes());
		allNodes.addAll(ibexPattern.getSignatureNodes());
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				IBeXNode node1 = allNodes.get(i);
				IBeXNode node2 = allNodes.get(j);
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
						IBeXPattern edgePattern = this.createEdgePattern(edgeType);
						Optional<IBeXNode> ibexSignatureSourceNode = findSignatureIBeXNodeWithName(edgePattern, "src");
						Optional<IBeXNode> ibexSignatureTargetNode = findSignatureIBeXNodeWithName(edgePattern, "trg");
						if (!ibexSignatureSourceNode.isPresent() || !ibexSignatureTargetNode.isPresent()) {
							this.logError("Invalid signature nodes for edge pattern!");
							return;
						}

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
										this.logError("Could not find node " + gtEdge.getSourceNode().getName() + "!");
										return;
									}
									if (!ibexLocalTargetNode.isPresent()) {
										this.logError("Could not find node " + gtEdge.getTargetNode().getName() + "!");
										return;
									}
									invocation.getMapping().put(ibexLocalSourceNode.get(),
											ibexSignatureSourceNode.get());
									invocation.getMapping().put(ibexLocalTargetNode.get(),
											ibexSignatureTargetNode.get());
									invocation.setInvokedPattern(edgePattern);
									ibexPattern.getInvocations().add(invocation);
								});
					});
		} else {
			// No invocations, so include all edges as well.
			gtRule.getGraph().getEdges().forEach(gtEdge -> {
				ibexPattern.getLocalEdges().add(this.transformEdge(gtEdge, ibexPattern));
			});
		}

		this.addPattern(ibexPattern);
	}

	/**
	 * Create an {@link IBeXPattern} for the given edge. If an {@link IBeXPattern}
	 * for the given {@link EReference} exists already, the existing pattern is
	 * returned.
	 * 
	 * @param edgeType
	 *            the EReference to create a pattern for
	 * @return the created IBeXPattern
	 */
	private IBeXPattern createEdgePattern(final EReference edgeType) {
		String name = "edge-" + edgeType.getEContainingClass().getName() + "-" + edgeType.getName() + "-"
				+ edgeType.getEReferenceType().getName();
		if (this.nameToIBeXPattern.containsKey(name)) {
			return this.nameToIBeXPattern.get(name);
		}

		IBeXPattern edgePattern = IBeXLanguageFactory.eINSTANCE.createIBeXPattern();
		edgePattern.setName(name);
		this.addPattern(edgePattern);

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

		return edgePattern;
	}

	/**
	 * Transforms a GTNode into an IBeXNode.
	 * 
	 * @param gtNode
	 *            the GTNode
	 * @return the IBeXNode
	 */
	private IBeXNode transformNode(final GTNode gtNode) {
		IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		ibexNode.setName(gtNode.getName());
		ibexNode.setType(gtNode.getType());
		return ibexNode;
	}

	/**
	 * Transforms a GTEdge into an IBeXEdge.
	 * 
	 * @param gtEdge
	 *            the gtEdge
	 * @param ibexPattern
	 *            the pattern the edge belongs to.
	 * @return the IBeXEdge
	 */
	private IBeXEdge transformEdge(final GTEdge gtEdge, final IBeXPattern ibexPattern) {
		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(gtEdge.getType());
		findIBeXNodeWithName(ibexPattern, gtEdge.getSourceNode().getName())
				.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
		findIBeXNodeWithName(ibexPattern, gtEdge.getTargetNode().getName())
				.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
		return ibexEdge;
	}

	/**
	 * Checks whether the given EClasses are the same or one is a super class of the
	 * other.
	 * 
	 * @param class1
	 *            an EClass
	 * @param class2
	 *            another EClass
	 * @return true if and only if an object could be an instance of both classes
	 */
	private static boolean canInstancesBeTheSame(final EClass class1, final EClass class2) {
		return class1.getName().equals(class2.getName()) || class1.getEAllSuperTypes().contains(class2)
				|| class2.getEAllSuperTypes().contains(class1);
	}

	/**
	 * Finds an {@link IBeXNode} with the given name in the given IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param name
	 *            the name to search for, must not be <code>null</code>
	 * @return an Optional for a local IBeXNode
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		Optional<IBeXNode> node = ibexPattern.getLocalNodes().stream().filter(n -> name.equals(n.getName())).findAny();
		if (node.isPresent()) {
			return node;
		} else {
			return findSignatureIBeXNodeWithName(ibexPattern, name);
		}
	}

	/**
	 * Finds an {@link IBeXNode} with the given name in the signature nodes of the
	 * given IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param name
	 *            the name to search for, must not be <code>null</code>
	 * @return an Optional for a signature IBeXNode
	 */
	private static Optional<IBeXNode> findSignatureIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return ibexPattern.getSignatureNodes().stream().filter(n -> name.equals(n.getName())).findAny();
	}
}
