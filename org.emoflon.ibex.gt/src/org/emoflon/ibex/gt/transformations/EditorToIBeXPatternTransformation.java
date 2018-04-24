package org.emoflon.ibex.gt.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.utils.EcoreUtils;
import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;

import IBeXLanguage.IBeXAttribute;
import IBeXLanguage.IBeXAttributeAssignment;
import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXPatternSet;
import IBeXLanguage.IBeXRelation;

/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractEditorModelTransformation<IBeXPatternSet> {
	/**
	 * All context patterns.
	 */
	private List<IBeXContextPattern> ibexContextPatterns = new ArrayList<IBeXContextPattern>();

	/**
	 * All create patterns.
	 */
	private List<IBeXCreatePattern> ibexCreatePatterns = new ArrayList<IBeXCreatePattern>();;

	/**
	 * All delete patterns.
	 */
	private List<IBeXDeletePattern> ibexDeletePatterns = new ArrayList<IBeXDeletePattern>();

	/**
	 * Mapping between pattern names and the context patterns.
	 */
	private HashMap<String, IBeXContextPattern> nameToPattern = new HashMap<String, IBeXContextPattern>();

	@Override
	public IBeXPatternSet transform(final EditorGTFile file) {
		Objects.requireNonNull(file, "The editor file must not be null!");

		// Transform patterns.
		file.getPatterns().forEach(editorPattern -> transformPattern(editorPattern));

		// Sort pattern lists alphabetically.
		ibexContextPatterns.sort(EditorToIBeXPatternHelper.sortByName);
		ibexCreatePatterns.sort(EditorToIBeXPatternHelper.sortByName);
		ibexDeletePatterns.sort(EditorToIBeXPatternHelper.sortByName);

		// Create pattern set with alphabetically pattern lists.
		IBeXPatternSet ibexPatternSet = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		ibexPatternSet.getContextPatterns().addAll(ibexContextPatterns);
		ibexPatternSet.getCreatePatterns().addAll(ibexCreatePatterns);
		ibexPatternSet.getDeletePatterns().addAll(ibexDeletePatterns);
		return ibexPatternSet;
	}

	/**
	 * Transforms the given pattern to a context, a create and a delete pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern to transform
	 */
	private void transformPattern(final EditorPattern editorPattern) {
		Objects.requireNonNull(editorPattern, "The pattern must not be null!");

		if (nameToPattern.containsKey(editorPattern.getName())) {
			// Already transformed.
			return;
		}

		getFlattenedPattern(editorPattern).ifPresent(flattenedPattern -> {
			transformToContextPattern(flattenedPattern, true);
			if (editorPattern.getType() == EditorPatternType.RULE) {
				transformToCreatePattern(flattenedPattern);
				transformToDeletePattern(flattenedPattern);
			}
		});
	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add, must not be <code>null</code>
	 */
	private void addContextPattern(final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(ibexPattern, "The pattern must not be null!");
		ibexContextPatterns.add(ibexPattern);
		nameToPattern.put(ibexPattern.getName(), ibexPattern);
	}

	/**
	 * Returns the IBeXContextPattern for the given editor pattern.
	 * 
	 * @param editorPattern
	 *            the editor pattern
	 * @return the IBeXContextPattern
	 */
	public IBeXContextPattern getContextPattern(final EditorPattern editorPattern) {
		if (!nameToPattern.containsKey(editorPattern.getName())) {
			transformPattern(editorPattern);
		}
		return nameToPattern.get(editorPattern.getName());
	}

	/**
	 * Transforms a GTRule into IBeXPatterns.
	 * 
	 * @param editorPattern
	 *            the editor pattern, must not be <code>null</code>
	 * @param useInvocations
	 *            whether to use invocations or not. If set to <code>false</code>,
	 *            one large pattern will be created, otherwise the pattern will use
	 *            invocations.
	 */
	private void transformToContextPattern(final EditorPattern editorPattern, final boolean useInvocations) {
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(editorPattern.getName());

		// Transform nodes and attributes.
		EditorModelUtils.getNodesByOperator(editorPattern, EditorOperator.CONTEXT, EditorOperator.DELETE)
				.forEach(editorNode -> {
					IBeXNode ibexNode = EditorToIBeXPatternHelper.transformNode(editorNode);
					if (EditorModelUtils.isLocal(editorNode)) {
						ibexPattern.getLocalNodes().add(ibexNode);
					} else {
						ibexPattern.getSignatureNodes().add(ibexNode);
					}

					transformAttributeConditions(editorNode, ibexNode, ibexPattern);
				});

		// Ensure that all nodes must be disjoint even if they have the same type.
		List<IBeXNode> allNodes = IBeXPatternUtils.getAllNodes(ibexPattern);
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				IBeXNode node1 = allNodes.get(i);
				IBeXNode node2 = allNodes.get(j);
				if (EcoreUtils.areTypesCompatible(node1.getType(), node2.getType())) {
					IBeXNodePair nodePair = IBeXLanguageFactory.eINSTANCE.createIBeXNodePair();
					nodePair.getValues().add(node1);
					nodePair.getValues().add(node2);
					ibexPattern.getInjectivityConstraints().add(nodePair);
				}
			}
		}

		// Transform edges.
		if (useInvocations) {
			EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT, EditorOperator.DELETE)
					.forEach(gtEdge -> transformEdgeToPatternInvocation(gtEdge, ibexPattern));
		} else {
			// No invocations, so include all edges as well.
			EditorModelUtils.getReferencesByOperator(editorPattern, EditorOperator.CONTEXT, EditorOperator.DELETE)
					.forEach(editorReference -> {
						ibexPattern.getLocalEdges().add(transformEdge(editorReference, ibexPattern));
					});
		}

		// Transform conditions to patterns.s
		new EditorToIBeXConditionHelper(this, editorPattern, ibexPattern).transformConditions();

		addContextPattern(ibexPattern);
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
	private IBeXContextPattern createEdgePattern(final EReference edgeType) {
		Objects.requireNonNull(edgeType, "Edge type must not be null!");

		String name = "edge-" + edgeType.getEContainingClass().getName() + "-" + edgeType.getName() + "-"
				+ edgeType.getEReferenceType().getName();
		if (nameToPattern.containsKey(name)) {
			return nameToPattern.get(name);
		}

		IBeXContextPattern edgePattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		edgePattern.setName(name);
		addContextPattern(edgePattern);

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
	 * @param editorReference
	 *            the edge to transform
	 * @param ibexPattern
	 *            the pattern where the pattern invocation shall be added
	 */
	private void transformEdgeToPatternInvocation(final EditorReference editorReference,
			final IBeXContextPattern ibexPattern) {
		IBeXContextPattern edgePattern = createEdgePattern(editorReference.getType());
		Optional<IBeXNode> ibexSignatureSourceNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "src");
		Optional<IBeXNode> ibexSignatureTargetNode = //
				IBeXPatternUtils.findIBeXNodeWithName(edgePattern.getSignatureNodes(), "trg");
		if (!ibexSignatureSourceNode.isPresent() || !ibexSignatureTargetNode.isPresent()) {
			logError("Invalid signature nodes for edge pattern!");
			return;
		}

		Optional<IBeXNode> ibexLocalSourceNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				EditorModelUtils.getSourceNode(editorReference).getName());
		Optional<IBeXNode> ibexLocalTargetNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				editorReference.getTarget().getName());

		if (!ibexLocalSourceNode.isPresent()) {
			logError("Could not find node " + EditorModelUtils.getSourceNode(editorReference).getName() + "!");
			return;
		}
		if (!ibexLocalTargetNode.isPresent()) {
			logError("Could not find node " + editorReference.getTarget().getName() + "!");
			return;
		}

		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);
		invocation.getMapping().put(ibexLocalSourceNode.get(), ibexSignatureSourceNode.get());
		invocation.getMapping().put(ibexLocalTargetNode.get(), ibexSignatureTargetNode.get());
		invocation.setInvokedPattern(edgePattern);
		ibexPattern.getInvocations().add(invocation);
	}

	/**
	 * Transforms an editor attribute constraint to an IBeXAttributeConstraint.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @param ibexNode
	 *            the IBeXNode the constraint holds for
	 * @param ibexContextPattern
	 *            the context pattern
	 * @return the IBeXAttributeConstraint
	 */
	private void transformAttributeConditions(final EditorNode editorNode, final IBeXNode ibexNode,
			final IBeXContextPattern ibexContextPattern) {
		Objects.requireNonNull(editorNode, "editorAttribute must not be null!");
		Objects.requireNonNull(ibexNode, "ibexNode must not be null!");

		EditorModelUtils.getAttributeConditions(editorNode).forEach(editorAttribute -> {
			IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
			ibexAttrConstraint.setNode(ibexNode);
			ibexAttrConstraint.setType(editorAttribute.getAttribute());

			IBeXRelation ibexRelation = EditorToIBeXAttributeHelper.convertRelation(editorAttribute.getRelation());
			ibexAttrConstraint.setRelation(ibexRelation);
			setAttributeValue(ibexAttrConstraint, editorAttribute, ibexContextPattern);
			ibexContextPattern.getAttributeConstraint().add(ibexAttrConstraint);
		});
	}

	/**
	 * Transforms a GTEdge into an IBeXEdge.
	 * 
	 * @param editorReference
	 *            the editor reference
	 * @param ibexPattern
	 *            the pattern the edge belongs to.
	 * @return the IBeXEdge
	 */
	private IBeXEdge transformEdge(final EditorReference editorReference, final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(editorReference, "The editor reference must not be null!");

		IBeXEdge ibexEdge = IBeXLanguageFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(editorReference.getType());
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, EditorModelUtils.getSourceNode(editorReference).getName())
				.ifPresent(sourceNode -> ibexEdge.setSourceNode(sourceNode));
		IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, editorReference.getTarget().getName())
				.ifPresent(targetNode -> ibexEdge.setTargetNode(targetNode));
		return ibexEdge;
	}

	/**
	 * Transforms an editor pattern to an IBeXCreatePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private void transformToCreatePattern(final EditorPattern editorPattern) {
		IBeXCreatePattern ibexCreatePattern = IBeXLanguageFactory.eINSTANCE.createIBeXCreatePattern();
		ibexCreatePattern.setName(editorPattern.getName());
		EditorToIBeXPatternHelper.transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.CREATE,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes(),
				ibexCreatePattern.getCreatedEdges());
		for (EditorNode editorNode : editorPattern.getNodes()) {
			transformAttributeAssignmentsOfNode(editorNode, ibexCreatePattern);
		}
		ibexCreatePatterns.add(ibexCreatePattern);
	}

	/**
	 * Transforms the attribute assignments of the given node and adds them to the
	 * given create pattern
	 * 
	 * @param editorNode
	 *            the node whose assignments shall be transformed
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	private void transformAttributeAssignmentsOfNode(final EditorNode editorNode,
			final IBeXCreatePattern ibexCreatePattern) {
		List<EditorAttribute> attributeAssignments = editorNode.getAttributes().stream()
				.filter(a -> a.getRelation().equals(EditorRelation.ASSIGNMENT)).collect(Collectors.toList());
		if (attributeAssignments.size() == 0) {
			return;
		}

		IBeXNode ibexNode = EditorToIBeXPatternHelper.addIBeXNodeToContextNodes(editorNode,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes());
		for (EditorAttribute editorAttribute : attributeAssignments) {
			IBeXAttributeAssignment ibexAssignment = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeAssignment();
			ibexAssignment.setNode(ibexNode);
			ibexAssignment.setType(editorAttribute.getAttribute());
			setAttributeValue(ibexAssignment, editorAttribute, ibexCreatePattern);
			ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
		}
	}

	/**
	 * Sets the attribute value of the IBeXAttribute.
	 * 
	 * @param ibexAttribute
	 *            the IBeXAttribute
	 * @param editorAttribute
	 *            the editor attribute
	 * @param ibexPattern
	 *            the IBeXPattern
	 */
	private void setAttributeValue(final IBeXAttribute ibexAttribute, final EditorAttribute editorAttribute,
			final IBeXPattern ibexPattern) {
		Optional<IBeXAttributeValue> value = EditorToIBeXAttributeHelper.convertAttributeValue(editorAttribute,
				ibexPattern);
		if (value.isPresent()) {
			ibexAttribute.setValue(value.get());
		} else {
			logError("Invalid attribute value: " + editorAttribute.getValue());
		}
	}

	/**
	 * Transforms an editor pattern into an IBeXDeletePattern.
	 * 
	 * @param editorPattern
	 *            the rule, must not be <code>null</code>
	 */
	private void transformToDeletePattern(final EditorPattern editorPattern) {
		IBeXDeletePattern ibexDeletePattern = IBeXLanguageFactory.eINSTANCE.createIBeXDeletePattern();
		ibexDeletePattern.setName(editorPattern.getName());
		EditorToIBeXPatternHelper.transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.DELETE,
				ibexDeletePattern.getDeletedNodes(), ibexDeletePattern.getContextNodes(),
				ibexDeletePattern.getDeletedEdges());
		ibexDeletePatterns.add(ibexDeletePattern);
	}
}
