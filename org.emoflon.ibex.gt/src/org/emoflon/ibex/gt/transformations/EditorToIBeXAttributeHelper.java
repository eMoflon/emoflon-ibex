package org.emoflon.ibex.gt.transformations;

import java.util.Optional;

import org.eclipse.emf.ecore.EDataType;
import org.emoflon.ibex.common.utils.IBeXPatternUtils;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;

import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXRelation;

/**
 * Utility methods to transform attributes from the editor model to the
 * IBeXPattern presentation.
 */
public class EditorToIBeXAttributeHelper {

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
