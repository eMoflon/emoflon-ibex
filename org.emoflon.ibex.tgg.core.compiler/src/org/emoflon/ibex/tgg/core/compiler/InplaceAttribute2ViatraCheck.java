package org.emoflon.ibex.tgg.core.compiler;

import javax.xml.datatype.DatatypeFactory;

import org.eclipse.emf.ecore.EcorePackage;

import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;

public class InplaceAttribute2ViatraCheck {

	public static String extractViatraCheck(String attributeName, TGGInplaceAttributeExpression expression) {
		if (expression.getOperator().equals(TGGAttributeConstraintOperators.UNEQUAL)) {
			if (expression.getAttribute().getEType().equals(EcorePackage.Literals.ESTRING)
					|| expression.getAttribute().getEType().equals(EcorePackage.Literals.EENUM))
				return "!" + attributeName + ".equals(" + extractViatraEqualCheck(expression) + ")";
		}
		return attributeName + " " + convertOperatorEnumToString(expression.getOperator()) + " " + extractViatraEqualCheck(expression);
	}

	public static String extractViatraEqualCheck(TGGInplaceAttributeExpression expression) {
		if (expression.getValueExpr() instanceof TGGLiteralExpression) {
			TGGLiteralExpression tle = (TGGLiteralExpression) expression.getValueExpr();
			return tle.getValue();
		}
		return null;
	}

	public static boolean simpleExpression(TGGInplaceAttributeExpression expression) {
		return expression.getOperator().equals(TGGAttributeConstraintOperators.EQUAL);
	}

	public static String convertOperatorEnumToString(TGGAttributeConstraintOperators operator) {
		switch (operator) {
		case EQUAL:
			return "==";
		case UNEQUAL:
			return "!=";
		case GR_EQUAL:
			return ">=";
		case LE_EQUAL:
			return "<=";
		case GREATER:
			return ">";
		case LESSER:
			return "<";
		default:
			return null;
		}
	}
}
