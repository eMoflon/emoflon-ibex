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
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorGTFile;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorOperator;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.gT.EditorPatternType;
import org.emoflon.ibex.gt.editor.gT.EditorReference;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;
import org.emoflon.ibex.gt.editor.utils.GTFlattener;

import IBeXLanguage.IBeXAttribute;
import IBeXLanguage.IBeXAttributeAssignment;
import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXNodePair;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXPatternSet;
import IBeXLanguage.IBeXRelation;

/**
 * Transformation from the editor model to IBeX Patterns.
 */
public class EditorToIBeXPatternTransformation extends AbstractModelTransformation<EditorGTFile, IBeXPatternSet> {
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
		ibexContextPatterns.sort(EditorToIBeXPatternUtils.sortByName);
		ibexCreatePatterns.sort(EditorToIBeXPatternUtils.sortByName);
		ibexDeletePatterns.sort(EditorToIBeXPatternUtils.sortByName);

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
		EditorPattern flattened = editorPattern;
		if (!editorPattern.getSuperPatterns().isEmpty()) {
			GTFlattener flattener = new GTFlattener(editorPattern);
			flattened = flattener.getFlattenedPattern();

			if (flattener.hasErrors()) {
				flattener.getErrors().forEach(e -> logError(e));
				return;
			}
		}

		transformToContextPattern(flattened, true);
		if (editorPattern.getType() == EditorPatternType.RULE) {
			transformToCreatePattern(flattened);
			transformToDeletePattern(flattened);
		}
	}

	/**
	 * Add the given pattern to the list.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add, must not be <code>null</code>
	 */
	private void addPattern(final IBeXContextPattern ibexPattern) {
		Objects.requireNonNull(ibexPattern, "Pattern must not be null!");
		ibexContextPatterns.add(ibexPattern);
		nameToPattern.put(ibexPattern.getName(), ibexPattern);
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
					IBeXNode ibexNode = EditorToIBeXPatternUtils.transformNode(editorNode);
					if (EditorModelUtils.isLocal(editorNode)) {
						ibexPattern.getLocalNodes().add(ibexNode);
					} else {
						ibexPattern.getSignatureNodes().add(ibexNode);
					}

					EditorModelUtils.getAttributeConditions(editorNode).forEach(editorAttribute -> {
						IBeXAttributeConstraint ibexAttribute = transformAttributeCondition(editorAttribute, ibexNode);
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
			EditorModelUtils.getReferences(editorPattern).forEach(editorReference -> {
				ibexPattern.getLocalEdges().add(transformEdge(editorReference, ibexPattern));
			});
		}

		addPattern(ibexPattern);
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
		addPattern(edgePattern);

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
	 * Transforms a GTAttributeCondition to an IBeXAttributeConstraint.
	 * 
	 * @param editorAttribute
	 *            the GTAttributeCondition
	 * @param ibexNode
	 *            the IBeXNode the constraint holds for
	 * @return the IBeXAttributeConstraint
	 */
	private IBeXAttributeConstraint transformAttributeCondition(final EditorAttribute editorAttribute,
			final IBeXNode ibexNode) {
		Objects.requireNonNull(editorAttribute, "gtAttrCond must not be null!");
		Objects.requireNonNull(ibexNode, "ibexNode must not be null!");

		IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(ibexNode);
		ibexAttrConstraint.setType(editorAttribute.getAttribute());

		IBeXRelation ibexRelation = EditorToInternalGTModelUtils.convertRelation(editorAttribute.getRelation());
		ibexAttrConstraint.setRelation(ibexRelation);
		setAttributeValue(ibexAttrConstraint, editorAttribute);
		return ibexAttrConstraint;
	}

	/**
	 * Sets the attribute value for the given attribute.
	 * 
	 * @param ibexAttribute
	 *            the attribute whose value shall be set
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @param gtParameters
	 *            the parameters of rule (internal model)
	 */
	private void setAttributeValue(final IBeXAttribute ibexAttribute, final EditorAttribute editorAttribute) {
		EditorExpression editorValue = editorAttribute.getValue();
		if (editorValue instanceof EditorAttributeExpression) {
			setAttributeValueToAttributeExpression(ibexAttribute, (EditorAttributeExpression) editorValue);
		} else if (editorValue instanceof EditorEnumExpression) {
			setAttributeValueToEnumExpression(ibexAttribute, (EditorEnumExpression) editorValue);
		} else if (editorValue instanceof EditorLiteralExpression) {
			setAttributeValueToLiteralExpression(ibexAttribute, (EditorLiteralExpression) editorValue);
		} else if (editorValue instanceof EditorParameterExpression) {
			setAttributeValueToParameterExpression(ibexAttribute, (EditorParameterExpression) editorValue);
		} else {
			logError("Invalid attribute value: " + editorValue);
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the attribute expression.
	 * 
	 * @param ibexAttribute
	 *            the attribute constraint whose value shall be set
	 * @param editorAttributeExpression
	 *            the attribute expression
	 */
	private static void setAttributeValueToAttributeExpression(final IBeXAttribute ibexAttribute,
			final EditorAttributeExpression editorAttributeExpression) {
		IBeXAttributeExpression ibexAttributeExpression = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeExpression();
		ibexAttributeExpression.setNodeName(editorAttributeExpression.getNode().getName());
		ibexAttributeExpression.setAttribute(editorAttributeExpression.getAttribute());
		ibexAttribute.setValue(ibexAttributeExpression);
	}

	/**
	 * Sets the attribute value for the given attribute to the enum expression.
	 * 
	 * @param ibexAttribute
	 *            the attribute constraint whose value shall be set
	 * @param editorEnumExpression
	 *            the enum expression
	 */
	private static void setAttributeValueToEnumExpression(final IBeXAttribute ibexAttribute,
			final EditorEnumExpression editorEnumExpression) {
		IBeXEnumLiteral gtEnumLiteral = IBeXLanguageFactory.eINSTANCE.createIBeXEnumLiteral();
		gtEnumLiteral.setLiteral(editorEnumExpression.getLiteral());
		ibexAttribute.setValue(gtEnumLiteral);
	}

	/**
	 * Sets the attribute value for the given attribute to the literal expression.
	 * 
	 * @param ibexAttribute
	 *            the attribute constraint whose value shall be set
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @param gtParameters
	 *            the parameters of rule (internal model)
	 */
	private void setAttributeValueToLiteralExpression(final IBeXAttribute ibexAttribute,
			final EditorLiteralExpression editorLiteralExpression) {
		String s = editorLiteralExpression.getValue();
		Optional<Object> object = GTEditorAttributeUtils
				.convertEDataTypeStringToObject(ibexAttribute.getType().getEAttributeType(), s);
		object.ifPresent(o -> {
			IBeXConstant gtConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
			gtConstant.setValue(o);
			gtConstant.setStringValue(o.toString());
			ibexAttribute.setValue(gtConstant);
		});
		if (!object.isPresent()) {
			logError("Invalid attribute value: " + s);
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the parameter expression.
	 * 
	 * @param ibexAttribute
	 *            the attribute constraint whose value shall be set
	 * @param editorParameterExpression
	 *            the parameter expression
	 */
	private void setAttributeValueToParameterExpression(final IBeXAttribute ibexAttribute,
			final EditorParameterExpression editorParameterExpression) {
		IBeXAttributeParameter parameter = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeParameter();
		parameter.setName(editorParameterExpression.getParameter().getName());
		ibexAttribute.setValue(parameter);
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
		EditorToIBeXPatternUtils.transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.CREATE,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes(),
				ibexCreatePattern.getCreatedEdges());
		editorPattern.getNodes().forEach(editorNode -> {
			transformAttributeAssignmentsOfNode(editorNode, ibexCreatePattern);
		});
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

		IBeXNode ibexNode = EditorToIBeXPatternUtils.addIBeXNodeToContextNodes(editorNode,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes());
		for (EditorAttribute editorAttribute : attributeAssignments) {
			IBeXAttributeAssignment ibexAssignment = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeAssignment();
			ibexAssignment.setNode(ibexNode);
			ibexAssignment.setType(editorAttribute.getAttribute());
			setAttributeValue(ibexAssignment, editorAttribute);
			ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
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
		EditorToIBeXPatternUtils.transformNodesAndEdgesOfOperator(editorPattern, EditorOperator.DELETE,
				ibexDeletePattern.getDeletedNodes(), ibexDeletePattern.getContextNodes(),
				ibexDeletePattern.getDeletedEdges());
		ibexDeletePatterns.add(ibexDeletePattern);
	}
}
