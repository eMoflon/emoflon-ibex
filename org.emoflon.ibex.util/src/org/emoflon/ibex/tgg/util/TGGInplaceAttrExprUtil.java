package org.emoflon.ibex.tgg.util;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import language.TGGAttributeExpression;
import language.TGGEnumExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGLiteralExpression;

public class TGGInplaceAttrExprUtil {

	/**
	 * Checks if the {@code value} holds the given {@code attributeExpression}. To (optionally) check
	 * attribute expressions with references to other node's attributes (namely
	 * {@link TGGAttributeExpression}), provide appropriate {@code nodeReferences} mapping node names to
	 * their actual model objects. If {@code nodeReferences} are not provided, this check will always
	 * evaluate conditions with {@link TGGAttributeExpression}s to {@code true}.
	 * 
	 * @param attributeExpression
	 * @param value
	 * @param nodeReferences      (may be {@code null})
	 * @return
	 */
	public static boolean checkInplaceAttributeCondition(TGGInplaceAttributeExpression attributeExpression, Object value,
			Map<String, EObject> nodeReferences) {
		if (attributeExpression.getValueExpr() instanceof TGGLiteralExpression litExpr) {
			if (value == null)
				return false;

			Object literal = String2EPrimitive.convertLiteral( //
					litExpr.getValue(), attributeExpression.getAttribute().getEAttributeType());

			switch (attributeExpression.getOperator()) {
			case EQUAL:
				return value.equals(literal);
			case UNEQUAL:
				return !value.equals(literal);
			default:
				break;
			}

			int compareResult = comparePrimitives(value, literal);
			switch (attributeExpression.getOperator()) {
			case GREATER:
				return compareResult > 0;
			case GR_EQUAL:
				return compareResult >= 0;
			case LESSER:
				return compareResult < 0;
			case LE_EQUAL:
				return compareResult <= 0;
			default:
				break;
			}
		} else if (attributeExpression.getValueExpr() instanceof TGGEnumExpression enumExpr) {
			if (value == null)
				return false;

			if (!value.equals(enumExpr.getLiteral().getInstance()))
				return false;
		} else if (nodeReferences != null && attributeExpression.getValueExpr() instanceof TGGAttributeExpression attrExpr) {
			EObject obj = nodeReferences.get(attrExpr.getObjectVar().getName());
			if (obj == null)
				return false;
			Object objectAttr = obj.eGet(attrExpr.getAttribute());

			if (value == null) {
				if (objectAttr != null)
					return false;
			} else if (!value.equals(objectAttr))
				return false;
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static int comparePrimitives(Object p1, Object p2) {
		if (p1 instanceof Comparable && p2 instanceof Comparable) {
			return ((Comparable) p1).compareTo((Comparable) p2);
		}
		return 0;
	}

}
