package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.utils.IBeXPatternUtils;

import GTLanguage.GTAttributeAssignment;
import GTLanguage.GTAttributeCondition;
import GTLanguage.GTBindingType;
import GTLanguage.GTEdge;
import GTLanguage.GTNode;
import GTLanguage.GTRule;
import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXAttributeAssignment;
import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNamedElement;
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
	 * All IBeXCreatePatterns.
	 */
	private List<IBeXCreatePattern> ibexCreatePatterns;

	/**
	 * All IBeXDeletePatterns.
	 */
	private List<IBeXDeletePattern> ibexDeletePatterns;

	/**
	 * All (black) IBeXPatterns created during the transformation.
	 */
	private List<IBeXPattern> ibexPatterns;

	/**
	 * Mapping between pattern names and the created IBeXPatterns.
	 */
	private HashMap<String, IBeXPattern> nameToIBeXPattern = new HashMap<String, IBeXPattern>();

	@Override
	public IBeXPatternSet transform(final GTRuleSet gtRuleSet) {
		Objects.requireNonNull(gtRuleSet, "Rule set must not be null!");

		// Transform patterns.
		this.ibexPatterns = new ArrayList<IBeXPattern>();
		this.ibexCreatePatterns = new ArrayList<IBeXCreatePattern>();
		this.ibexDeletePatterns = new ArrayList<IBeXDeletePattern>();
		gtRuleSet.getRules().forEach(gtRule -> {
			this.transformRule(gtRule, true);
			this.transformRuleToCreatePattern(gtRule);
			this.transformRuleToDeletePattern(gtRule);
		});

		// Sort pattern lists alphabetically.
		final Comparator<IBeXNamedElement> sortByName = (a, b) -> a.getName().compareTo(b.getName());
		this.ibexPatterns.sort(sortByName);
		this.ibexCreatePatterns.sort(sortByName);
		this.ibexDeletePatterns.sort(sortByName);

		// Create pattern set with alphabetically pattern lists.
		IBeXPatternSet ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		ibexPatternSet.getPatterns().addAll(ibexPatterns);
		ibexPatternSet.getCreatePatterns().addAll(ibexCreatePatterns);
		ibexPatternSet.getDeletePatterns().addAll(ibexDeletePatterns);
		return ibexPatternSet;
	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add, must not be <code>null</code>
	 */
	private void addPattern(final IBeXPattern ibexPattern) {
		Objects.requireNonNull(ibexPattern, "Pattern must not be null!");
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

		// Transform nodes and attributes.
		InternalGTModelUtils.filterNodesByBindingTypes(gtRule, GTBindingType.CONTEXT, GTBindingType.DELETE)
				.forEach(gtNode -> {
					IBeXNode ibexNode = this.transformNode(gtNode);
					if (gtNode.isLocal()) {
						ibexPattern.getLocalNodes().add(ibexNode);
					} else {
						ibexPattern.getSignatureNodes().add(ibexNode);
					}

					gtNode.getAttributeConditions().forEach(gtAttrCond -> {
						IBeXAttributeConstraint ibexAttribute = this.transformAttributeCondition(gtAttrCond, ibexNode);
						ibexPattern.getAttributeConstraint().add(ibexAttribute);
					});
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

		// Transform edges.
		if (useInvocations) {
			InternalGTModelUtils.filterEdgesByBindingTypes(gtRule, GTBindingType.CONTEXT, GTBindingType.DELETE)
					.forEach(gtEdge -> {
						this.transformEdgeToPatternInvocation(gtEdge, ibexPattern);
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
		Objects.requireNonNull(edgeType, "Edge type must not be null!");

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
	 * Transforms an edge to a pattern invocation.
	 * 
	 * @param gtEdge
	 *            the edge to transform
	 * @param ibexPattern
	 *            the pattern where the pattern invocation shall be added
	 */
	private void transformEdgeToPatternInvocation(final GTEdge gtEdge, final IBeXPattern ibexPattern) {
		IBeXPattern edgePattern = this.createEdgePattern(gtEdge.getType());
		Optional<IBeXNode> ibexSignatureSourceNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "src");
		Optional<IBeXNode> ibexSignatureTargetNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "trg");
		if (!ibexSignatureSourceNode.isPresent() || !ibexSignatureTargetNode.isPresent()) {
			this.logError("Invalid signature nodes for edge pattern!");
			return;
		}

		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);

		Optional<IBeXNode> ibexLocalSourceNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				gtEdge.getSourceNode().getName());
		Optional<IBeXNode> ibexLocalTargetNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				gtEdge.getTargetNode().getName());

		if (!ibexLocalSourceNode.isPresent()) {
			this.logError("Could not find node " + gtEdge.getSourceNode().getName() + "!");
			return;
		}
		if (!ibexLocalTargetNode.isPresent()) {
			this.logError("Could not find node " + gtEdge.getTargetNode().getName() + "!");
			return;
		}
		invocation.getMapping().put(ibexLocalSourceNode.get(), ibexSignatureSourceNode.get());
		invocation.getMapping().put(ibexLocalTargetNode.get(), ibexSignatureTargetNode.get());
		invocation.setInvokedPattern(edgePattern);
		ibexPattern.getInvocations().add(invocation);
	}

	/**
	 * Transforms a GTNode into an IBeXNode.
	 * 
	 * @param gtNode
	 *            the GTNode
	 * @return the IBeXNode
	 */
	private IBeXNode transformNode(final GTNode gtNode) {
		Objects.requireNonNull(gtNode, "Node must not be null!");

		IBeXNode ibexNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		ibexNode.setName(gtNode.getName());
		ibexNode.setType(gtNode.getType());
		return ibexNode;
	}

	/**
	 * Transforms a GTAttributeCondition to an IBeXAttributeConstraint.
	 * 
	 * @param gtAttrCond
	 *            the GTAttributeCondition
	 * @param ibexNode
	 *            the IBeXNode the constraint holds for
	 * @return the IBeXAttributeConstraint
	 */
	private IBeXAttributeConstraint transformAttributeCondition(final GTAttributeCondition gtAttrCond,
			final IBeXNode ibexNode) {
		Objects.requireNonNull(gtAttrCond, "gtAttrCond must not be null!");
		Objects.requireNonNull(ibexNode, "ibexNode must not be null!");

		IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(ibexNode);
		ibexAttrConstraint.setType(gtAttrCond.getType());
		ibexAttrConstraint.setRelation(InternalGTModelToIBeXPatternUtils.convertRelation(gtAttrCond.getRelation()));
		ibexAttrConstraint.setValue(InternalGTModelToIBeXPatternUtils.convertAttributeValue(gtAttrCond.getValue()));
		return ibexAttrConstraint;
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
		Objects.requireNonNull(gtEdge, "Edge must not be null!");

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(gtEdge.getType());
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, gtEdge.getSourceNode().getName())
				.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, gtEdge.getTargetNode().getName())
				.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
		return ibexEdge;
	}

	/**
	 * Transforms a GTRule into an IBeXPattern for the created parts if there are
	 * any elements to create.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 */
	private void transformRuleToCreatePattern(final GTRule gtRule) {
		Objects.requireNonNull(gtRule, "rule must not be null!");
		if (!InternalGTModelUtils.hasElementsOfBindingType(gtRule, GTBindingType.CREATE)) {
			return;
		}

		IBeXCreatePattern ibexCreatePattern = IBeXLanguageFactory.eINSTANCE.createIBeXCreatePattern();
		ibexCreatePattern.setName(gtRule.getName());

		// Transform created nodes.
		InternalGTModelUtils.filterNodesByBindingTypes(gtRule, GTBindingType.CREATE).forEach(gtNode -> {
			ibexCreatePattern.getCreatedNodes().add(this.transformNode(gtNode));
		});

		// Transform created edges (and their source/target nodes if necessary).
		InternalGTModelUtils.filterEdgesByBindingTypes(gtRule, GTBindingType.CREATE).forEach(gtEdge -> {
			IBeXEdge ibexEdge = this.transformEdge(gtEdge, ibexCreatePattern.getCreatedNodes(),
					ibexCreatePattern.getContextNodes());
			ibexCreatePattern.getCreatedEdges().add(ibexEdge);
		});

		// Transform attribute assignments.
		gtRule.getGraph().getNodes().forEach(gtNode -> {
			IBeXNode ibexNode = null;
			Optional<IBeXNode> ibexNodeOptional = IBeXPatternUtils.findIBeXNodeWithName(
					ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes(), gtNode.getName());
			if (ibexNodeOptional.isPresent()) {
				ibexNode = ibexNodeOptional.get();
			} else {
				ibexNode = this.transformNode(gtNode);
				ibexCreatePattern.getContextNodes().add(ibexNode);
			}

			for (GTAttributeAssignment gtAssignment : gtNode.getAttributeAssignments()) {
				IBeXAttributeAssignment ibexAssignment = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeAssignment();
				ibexAssignment.setNode(ibexNode);
				ibexAssignment.setType(gtAssignment.getType());
				ibexAssignment
						.setValue(InternalGTModelToIBeXPatternUtils.convertAttributeValue(gtAssignment.getValue()));
				ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
			}
		});
		this.ibexCreatePatterns.add(ibexCreatePattern);
	}

	/**
	 * Transforms a GTRule into an IBeXPattern for the deleted parts if there are
	 * any elements to delete.
	 * 
	 * @param gtRule
	 *            the rule, must not be <code>null</code>
	 */
	private void transformRuleToDeletePattern(final GTRule gtRule) {
		Objects.requireNonNull(gtRule, "rule must not be null!");
		if (!InternalGTModelUtils.hasElementsOfBindingType(gtRule, GTBindingType.DELETE)) {
			return;
		}

		IBeXDeletePattern ibexDeletePattern = IBeXLanguageFactory.eINSTANCE.createIBeXDeletePattern();
		ibexDeletePattern.setName(gtRule.getName());

		// Transform deleted nodes.
		InternalGTModelUtils.filterNodesByBindingTypes(gtRule, GTBindingType.DELETE).forEach(gtNode -> {
			ibexDeletePattern.getDeletedNodes().add(this.transformNode(gtNode));
		});

		// Transform deleted edges (and their source/target nodes if necessary).
		InternalGTModelUtils.filterEdgesByBindingTypes(gtRule, GTBindingType.DELETE).forEach(gtEdge -> {
			IBeXEdge ibexEdge = this.transformEdge(gtEdge, ibexDeletePattern.getDeletedNodes(),
					ibexDeletePattern.getContextNodes());
			ibexDeletePattern.getDeletedEdges().add(ibexEdge);
		});
		this.ibexDeletePatterns.add(ibexDeletePattern);
	}

	/**
	 * Transforms the given edge into an {@link IBeXEdge}. If a source or target
	 * node does not exist in the lists of changed or context nodes, the node will
	 * be added to the context nodes.
	 * 
	 * @param gtEdge
	 *            the edge
	 * @param changedNodes
	 *            the list of nodes
	 * @param contextNodes
	 *            the list of nodes where
	 * @return the transformed edge
	 */
	private IBeXEdge transformEdge(final GTEdge gtEdge, final List<IBeXNode> changedNodes,
			final List<IBeXNode> contextNodes) {
		Objects.requireNonNull(gtEdge, "Edge must not be null!");
		Objects.requireNonNull(gtEdge.getSourceNode(), "Edge must have a source node!");
		Objects.requireNonNull(gtEdge.getTargetNode(), "Edge must have a source node!");
		Objects.requireNonNull(changedNodes, "Changed node must not be null!");
		Objects.requireNonNull(contextNodes, "Context node must not be null!");

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(gtEdge.getType());

		Optional<IBeXNode> ibexSourceNode = IBeXPatternUtils.findIBeXNodeWithName(changedNodes, contextNodes,
				gtEdge.getSourceNode().getName());
		if (ibexSourceNode.isPresent()) {
			ibexEdge.setSourceNode(ibexSourceNode.get());
		} else {
			IBeXNode node = this.transformNode(gtEdge.getSourceNode());
			contextNodes.add(node);
			ibexEdge.setSourceNode(node);
		}

		Optional<IBeXNode> ibexTargetNode = IBeXPatternUtils.findIBeXNodeWithName(changedNodes, contextNodes,
				gtEdge.getTargetNode().getName());
		if (ibexTargetNode.isPresent()) {
			ibexEdge.setTargetNode(ibexTargetNode.get());
		} else {
			IBeXNode node = this.transformNode(gtEdge.getTargetNode());
			contextNodes.add(node);
			ibexEdge.setTargetNode(node);
		}

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
		Objects.requireNonNull(class1);
		Objects.requireNonNull(class2);
		return class1.getName().equals(class2.getName()) || class1.getEAllSuperTypes().contains(class2)
				|| class2.getEAllSuperTypes().contains(class1);
	}
}
