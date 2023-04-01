package org.emoflon.ibex.tgg.util;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXBooleanValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEnumValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXStringValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.DoubleLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IntegerLiteral;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalOperator;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

public class TGGAttributeCheckUtil {

	/**
	 * Checks if the the given {@code expression} evaluates to {@code true} To check attribute
	 * expressions with references to other node's attributes (namely {@link IBeXAttributeValue}),
	 * appropriate {@code objectReferences} mapping node names to their actual model objects must be
	 * provided.
	 * 
	 * @param expression
	 * @param objectReferences
	 * @return {@code true} if the expression evaluates to {@code true}
	 */
	public static boolean checkAttributeCondition(BooleanExpression expression, Map<String, EObject> objectReferences) {
		if (!(expression instanceof RelationalExpression relationalExpression))
			throw new RuntimeException("Only relational expressions are supported for attribute conditions!");

		Object lhsValue = extractValue(relationalExpression.getLhs(), objectReferences);
		Object rhsValue = extractValue(relationalExpression.getRhs(), objectReferences);

		return compare(lhsValue, rhsValue, relationalExpression.getOperator());
	}

	/**
	 * Compares LHS and RHS by the given operator. If LHS or RHS are not constants, it returns {@code true} in all cases.
	 * 
	 * @param lhs Left hand side
	 * @param operator
	 * @param rhs Right hand side
	 * @return {@code true} if the comparison evaluates to {@code true}
	 */
	public static boolean checkRelation(ValueExpression lhs, RelationalOperator operator, ValueExpression rhs) {
		if (lhs instanceof IBeXAttributeValue || rhs instanceof IBeXAttributeValue)
			return true;

		Object lhsValue = extractValue(lhs, Collections.emptyMap());
		Object rhsValue = extractValue(rhs, Collections.emptyMap());

		return compare(lhsValue, rhsValue, operator);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean compare(Object lhsValue, Object rhsValue, RelationalOperator operator) {
		if (lhsValue instanceof Comparable lhsComp && rhsValue instanceof Comparable rhsComp) {
			int compareResult = lhsComp.compareTo(rhsComp);
			return switch (operator) {
				case EQUAL -> compareResult == 0;
				case UNEQUAL -> compareResult != 0;
				case GREATER -> compareResult > 0;
				case GREATER_OR_EQUAL -> compareResult >= 0;
				case SMALLER -> compareResult < 0;
				case SMALLER_OR_EQUAL -> compareResult <= 0;
				default -> throw new IllegalArgumentException("Unexpected value: " + operator);
			};
		} else {
			boolean compareResult = Objects.equals(lhsValue, rhsValue);
			return switch (operator) {
				case EQUAL -> compareResult;
				case UNEQUAL -> !compareResult;
				default -> throw new IllegalArgumentException("Unexpected value: " + operator);
			};
		}
	}

	private static Object extractValue(ValueExpression expression, Map<String, EObject> objectReferences) {
		if (expression instanceof IBeXStringValue stringValue)
			return stringValue.getValue();
		if (expression instanceof IBeXBooleanValue booleanValue)
			return booleanValue.isValue();
		if (expression instanceof DoubleLiteral doubleValue)
			return doubleValue.getValue();
		if (expression instanceof IntegerLiteral integerValue)
			return integerValue.getValue();
		if (expression instanceof IBeXEnumValue enumValue)
			return enumValue.getLiteral().getInstance();
		if (expression instanceof IBeXAttributeValue attributeValue) {
			EObject obj = objectReferences.get(attributeValue.getNode().getName());
			if (obj == null)
				throw new RuntimeException("No proper object reference provided!");
			return obj.eGet(attributeValue.getAttribute());
		}
		throw new RuntimeException("Not supported value expression!");
	}

}