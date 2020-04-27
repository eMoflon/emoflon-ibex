package org.emoflon.ibex.gt.transformations;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EDataType;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.SGTPatternModel.GTStochasticRange;
import org.emoflon.ibex.gt.editor.gT.ArithmeticCalculationExpression;
import org.emoflon.ibex.gt.editor.gT.EditorAttribute;
import org.emoflon.ibex.gt.editor.gT.EditorAttributeExpression;
import org.emoflon.ibex.gt.editor.gT.EditorEnumExpression;
import org.emoflon.ibex.gt.editor.gT.EditorExpression;
import org.emoflon.ibex.gt.editor.gT.EditorLiteralExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;
import org.emoflon.ibex.gt.editor.gT.EditorParameterExpression;
import org.emoflon.ibex.gt.editor.gT.EditorRelation;
import org.emoflon.ibex.gt.editor.gT.StochasticFunctionExpression;
import org.emoflon.ibex.gt.editor.utils.GTEditorAttributeUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXArithmeticValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeAssignment;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeValue;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXConstant;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEnumLiteral;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXStochasticAttributeValue;

/**
 * Helper to transform attributes from the editor to the IBeX model.
 */
public class EditorToIBeXAttributeHelper {
	/**
	 * A comparator for editor attributes.
	 */
	private static final Comparator<EditorAttribute> sortAttribute = (a, b) -> {
		int compareAttributes = a.getAttribute().getName().compareTo(b.getAttribute().getName());
		if (compareAttributes != 0) {
			return compareAttributes;
		}

		return a.getRelation().compareTo(b.getRelation());
	};

	/**
	 * Checks whether the editor attribute is an assignment.
	 */
	private static final Predicate<EditorAttribute> isAssignment = a -> a.getRelation() == EditorRelation.ASSIGNMENT;

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
	 * Transforms each attribute condition of the editor node to an
	 * {@link IBeXAttributeConstraint} and adds them to the given context pattern.
	 * 
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	public void transformAttributeConditions(final IBeXContextPattern ibexContextPattern) {
		Objects.requireNonNull(ibexContextPattern, "ibexContextPattern must not be null!");

		Optional<IBeXNode> ibexNode = IBeXPatternUtils.findIBeXNodeWithName(ibexContextPattern, editorNode.getName());
		if (!ibexNode.isPresent()) {
			transformation.logError("Node %s missing!", editorNode.getName());
			return;
		}

		for (final EditorAttribute editorAttribute : filterAttributes(editorNode, isAssignment.negate())) {
			transformAttributeCondition(editorAttribute, ibexNode.get(), ibexContextPattern);
		}
	}

	/**
	 * Transforms an attribute condition to an {@link IBeXAttributeConstraint}.
	 * 
	 * @param editorAttribute
	 *            the editor attribute to transform
	 * @param ibexNode
	 *            the IBeXNode
	 * @param ibexContextPattern
	 *            the context pattern
	 */
	private void transformAttributeCondition(final EditorAttribute editorAttribute, final IBeXNode ibexNode,
			final IBeXContextPattern ibexContextPattern) {
		IBeXAttributeConstraint ibexAttrConstraint = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(ibexNode);
		ibexAttrConstraint.setType(editorAttribute.getAttribute());

		IBeXRelation ibexRelation = convertRelation(editorAttribute.getRelation());
		ibexAttrConstraint.setRelation(ibexRelation);
		convertAttributeValue(editorAttribute, ibexContextPattern).ifPresent(v -> ibexAttrConstraint.setValue(v));
		ibexContextPattern.getAttributeConstraint().add(ibexAttrConstraint);
	}

	/**
	 * Transforms each attribute assignment of the editor node to an
	 * {@link IBeXAttributeAssignment} and adds them to the given create pattern.
	 *
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	public void transformAttributeAssignments(final IBeXCreatePattern ibexCreatePattern) {
		List<EditorAttribute> attributeAssignments = filterAttributes(editorNode, isAssignment);
		if (attributeAssignments.size() == 0) {
			return;
		}

		IBeXNode ibexNode = EditorToIBeXPatternHelper.addIBeXNodeToContextNodes(editorNode,
				ibexCreatePattern.getCreatedNodes(), ibexCreatePattern.getContextNodes());
		for (EditorAttribute editorAttribute : attributeAssignments) {
			transformAttributeAssignment(editorAttribute, ibexNode, ibexCreatePattern);
		}
	}

	/**
	 * Transforms an attribute assignment to an {@link IBeXAttributeAssignment}.
	 * 
	 * @param editorAttribute
	 *            the editor attribute to transform
	 * @param ibexNode
	 *            the IBeXNode
	 * @param ibexCreatePattern
	 *            the create pattern
	 */
	private void transformAttributeAssignment(final EditorAttribute editorAttribute, final IBeXNode ibexNode,
			final IBeXCreatePattern ibexCreatePattern) {
		IBeXAttributeAssignment ibexAssignment = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeAssignment();
		ibexAssignment.setNode(ibexNode);
		ibexAssignment.setType(editorAttribute.getAttribute());
		convertAttributeValue(editorAttribute, ibexCreatePattern).ifPresent(v -> ibexAssignment.setValue(v));
		ibexCreatePattern.getAttributeAssignments().add(ibexAssignment);
	}

	/**
	 * Returns the attribute assignments of the given node.
	 * 
	 * @param editorNode
	 *            the editor node
	 * @param filterCondition
	 *            the condition for filtering attributes
	 * @return the attribute assignments
	 */
	private static List<EditorAttribute> filterAttributes(final EditorNode editorNode,
			final Predicate<EditorAttribute> filterCondition) {
		return editorNode.getAttributes().stream() //
				.filter(filterCondition) //
				.sorted(sortAttribute) //
				.collect(Collectors.toList());
	}

	/**
	 * Converts the relation.
	 * 
	 * @param relation
	 *            the relation from the editor model
	 * @return the IBeXRelation
	 */
	private static IBeXRelation convertRelation(final EditorRelation relation) {
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
	private Optional<IBeXAttributeValue> convertAttributeValue(final EditorAttribute editorAttribute,
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
		} else if(value instanceof StochasticFunctionExpression) {
			return Optional.of(convertAttributeValue((StochasticFunctionExpression) value));
		} else if(value instanceof ArithmeticCalculationExpression) {
			return Optional.of(convertAttributeValue((ArithmeticCalculationExpression) value));
		}
		else {
			transformation.logError("Invalid attribute value: %s", editorAttribute.getValue());
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
		IBeXAttributeExpression ibexAttributeExpression = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeExpression();
		ibexAttributeExpression.setAttribute(editorExpression.getAttribute());
		Optional<IBeXNode> ibexExistingNode = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern,
				editorExpression.getNode().getName());

		if (!ibexExistingNode.isPresent() && ibexPattern instanceof IBeXCreatePattern) {
			IBeXNode ibexNode = EditorToIBeXPatternHelper.transformNode(editorExpression.getNode());
			ibexExistingNode = Optional.of(ibexNode);
			((IBeXCreatePattern) ibexPattern).getContextNodes().add(ibexNode);
		}

		return ibexExistingNode.map(ibexNode -> {
			ibexAttributeExpression.setNode(ibexNode);
			return ibexAttributeExpression;
		});
	}

	/**
	 * Sets the attribute value for the given attribute to the enum expression.
	 * 
	 * @param editorExpression
	 *            the enum expression
	 * @return the IBeXEnumLiteral
	 */
	private static IBeXEnumLiteral convertAttributeValue(final EditorEnumExpression editorExpression) {
		IBeXEnumLiteral ibexEnumLiteral = IBeXPatternModelFactory.eINSTANCE.createIBeXEnumLiteral();
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
			IBeXConstant ibexConstant = IBeXPatternModelFactory.eINSTANCE.createIBeXConstant();
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
		IBeXAttributeParameter parameter = IBeXPatternModelFactory.eINSTANCE.createIBeXAttributeParameter();
		parameter.setName(editorExpression.getParameter().getName());
		return parameter;
	}
	
	/**
	 * Sets the stochastic attribute which later generates a numeric value
	 * 
	 * @param stochasticExpression
	 * 			the stochastic expression
	 * @return the IBeXStochasticAttributeValue
	 */
	private static IBeXStochasticAttributeValue convertAttributeValue(final StochasticFunctionExpression stochasticExpression) {
		IBeXStochasticAttributeValue value = IBeXPatternModelFactory.eINSTANCE.createIBeXStochasticAttributeValue();
		value.setFunction(EditorToStochasticExtensionHelper
				.transformStochasticFunction(stochasticExpression));
		// the value of the enums from GTStochasticRange and OperatorRange need to be the same
		value.setRange(GTStochasticRange.get(stochasticExpression.getOperatorRange().getValue()));
		return value;
	}
	/**
	 * partly calculates and transforms the arithmetic expression
	 * 
	 * @param expression the arithmetic expression
	 * @return the IBeXArithmeticValue
	 */
	private static IBeXArithmeticValue convertAttributeValue(final ArithmeticCalculationExpression expression) {
		IBeXArithmeticValue value= IBeXPatternModelFactory.eINSTANCE.createIBeXArithmeticValue();		;
		value.setExpression(EditorToArithmeticExtensionHelper.transformToGTArithmetics(expression.getExpression()));
		return value;
	}
}
