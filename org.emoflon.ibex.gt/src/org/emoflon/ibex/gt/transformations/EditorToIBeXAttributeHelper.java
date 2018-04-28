package org.emoflon.ibex.gt.transformations;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EDataType;
import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;

import IBeXLanguage.IBeXAttribute;
import IBeXLanguage.IBeXAttributeAssignment;
import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXRelation;

/**
 * Helper to transform attributes from the editor to the IBeX model.
 */
public class EditorToIBeXAttributeHelper {
	/**
	 * The transformation.
	 */
	private final EditorToIBeXPatternTransformation transformation;

	/**
	 * The editor node whose attributes are transformed.
	 */
	private final EditorNode editorNode;

	/**
	 * Creates a new EditorToIBeXAttributeHelper.
	 * 
	 * @param transformation
	 *            the transformation
	 * @param editorNode
	 *            the editor node
	 */
	public EditorToIBeXAttributeHelper(final EditorToIBeXPatternTransformation transformation,
			final EditorNode editorNode) {
		Objects.requireNonNull(editorNode, "editorAttribute must not be null!");

		this.transformation = transformation;
		this.editorNode = editorNode;
	}

	/**
	 * Transforms an editor attribute constraint to an IBeXAttributeConstraint.
	 * 
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	public void transformAttributeConditions(final IBeXContextPattern ibexContextPattern) {
		Objects.requireNonNull(ibexContextPattern, "ibexContextPattern must not be null!");

		Optional<IBeXNode> ibexNode = IBeXPatternUtils.findIBeXNodeWithName(ibexContextPattern, editorNode.getName());
		if (!ibexNode.isPresent()) {
			transformation.logError("Node " + editorNode.getName() + " missing!");
			return;
		}

		EditorModelUtils.getAttributeConditions(editorNode).forEach(editorAttribute -> {
			IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
			ibexAttrConstraint.setNode(ibexNode.get());
			ibexAttrConstraint.setType(editorAttribute.getAttribute());

			IBeXRelation ibexRelation = EditorToIBeXAttributeHelper.convertRelation(editorAttribute.getRelation());
			ibexAttrConstraint.setRelation(ibexRelation);
			setAttributeValue(ibexAttrConstraint, editorAttribute, ibexContextPattern);
			ibexContextPattern.getAttributeConstraint().add(ibexAttrConstraint);
		});
	}

	/**
	 * Transforms the attribute assignments of the given node and adds them to the
	 * given create pattern
	 *
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	public void transformAttributeAssignments(final IBeXCreatePattern ibexCreatePattern) {
		List<EditorAttribute> attributeAssignments = EditorModelUtils.getAttributeAssignments(editorNode)
				.collect(Collectors.toList());
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
			transformation.logError("Invalid attribute value: " + editorAttribute.getValue());
		}
	}

	/**
	 * Converts the relation.
	 * 
	 * @param relation
	 *            the relation from the editor model
	 * @return the IBeXRelation
	 */
	public static IBeXRelation convertRelation(final EditorRelation relation) {
		switch (relation) {
		case GREATER:
			return IBeXRelation.GREATER;
		case GREATER_OR_EQUAL:
			return IBeXRelation.GREATER_OR_EQUAL;
		case EQUAL:
			return IBeXRelation.EQUAL;
		case UNEQUAL:
			return IBeXRelation.UNEQUAL;
		case SMALLER:
			return IBeXRelation.SMALLER;
		case SMALLER_OR_EQUAL:
			return IBeXRelation.SMALLER_OR_EQUAL;
		default:
			throw new IllegalArgumentException("Cannot convert relation: " + relation);
		}
	}

	/**
	 * Convert the value of the editor attribute.
	 * 
	 * @param editorAttribute
	 *            the editor attribute
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @return an {@link Optional} for the IBeXAttributeValue
	 */
	public static Optional<IBeXAttributeValue> convertAttributeValue(final EditorAttribute editorAttribute,
			final IBeXPattern ibexPattern) {
		EditorExpression value = editorAttribute.getValue();
		if (value instanceof EditorAttributeExpression) {
			return convertAttributeValue((EditorAttributeExpression) value, ibexPattern);
		} else if (value instanceof EditorEnumExpression) {
			return Optional.of(convertAttributeValue((EditorEnumExpression) value));
		} else if (value instanceof EditorLiteralExpression) {
			return Optional.of(convertAttributeValue((EditorLiteralExpression) value,
					editorAttribute.getAttribute().getEAttributeType()));
		} else if (value instanceof EditorParameterExpression) {
			return Optional.of(convertAttributeValue((EditorParameterExpression) value));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the attribute expression.
	 * 
	 * @param editorExpression
	 *            the attribute expression
	 * @param ibexPattern
	 *            the IBeXPattern
	 * @return the IBeXAttributeExpression
	 */
	private static Optional<IBeXAttributeValue> convertAttributeValue(final EditorAttributeExpression editorExpression,
			final IBeXPattern ibexPattern) {
		IBeXAttributeExpression ibexAttributeExpression = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeExpression();
		ibexAttributeExpression.setAttribute(editorExpression.getAttribute());
		Optional<IBeXNode> ibexExistingNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				editorExpression.getNode().getName());
		if (ibexExistingNode.isPresent()) {
			ibexAttributeExpression.setNode(ibexExistingNode.get());
			return Optional.of(ibexAttributeExpression);
		} else {
			if (ibexPattern instanceof IBeXCreatePattern) {
				IBeXNode ibexNode = EditorToIBeXPatternHelper.transformNode(editorExpression.getNode());
				ibexAttributeExpression.setNode(ibexNode);
				((IBeXCreatePattern) ibexPattern).getContextNodes().add(ibexNode);
				return Optional.of(ibexAttributeExpression);
			}
			return Optional.empty();
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the enum expression.
	 * 
	 * @param editorExpression
	 *            the enum expression
	 * @return the IBeXEnumLiteral
	 */
	private static IBeXEnumLiteral convertAttributeValue(final EditorEnumExpression editorExpression) {
		IBeXEnumLiteral ibexEnumLiteral = IBeXLanguageFactory.eINSTANCE.createIBeXEnumLiteral();
		ibexEnumLiteral.setLiteral(editorExpression.getLiteral());
		return ibexEnumLiteral;
	}

	/**
	 * Sets the attribute value for the given attribute to the literal expression.
	 * 
	 * @param editorAttribute
	 *            the attribute constraint of the editor model
	 * @return the IBeXConstant
	 */
	private static IBeXConstant convertAttributeValue(final EditorLiteralExpression editorExpression,
			final EDataType type) {
		String s = editorExpression.getValue();
		Optional<Object> object = GTEditorAttributeUtils.convertEDataTypeStringToObject(type,
				editorExpression.getValue());
		if (object.isPresent()) {
			IBeXConstant ibexConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
			ibexConstant.setValue(object.get());
			ibexConstant.setStringValue(object.get().toString());
			return ibexConstant;
		} else {
			throw new IllegalArgumentException("Invalid attribute value: " + s);
		}
	}

	/**
	 * Sets the attribute value for the given attribute to the parameter expression.
	 * 
	 * @param editorExpression
	 *            the parameter expression
	 * @return the IBeXAttributeParameter
	 */
	private static IBeXAttributeParameter convertAttributeValue(final EditorParameterExpression editorExpression) {
		IBeXAttributeParameter parameter = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeParameter();
		parameter.setName(editorExpression.getParameter().getName());
		return parameter;
	}
}
