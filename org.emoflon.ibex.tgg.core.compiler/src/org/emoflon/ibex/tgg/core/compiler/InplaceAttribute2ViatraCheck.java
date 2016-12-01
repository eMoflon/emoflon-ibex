package org.emoflon.ibex.tgg.core.compiler;

import language.basic.expressions.TGGExpression;
import language.basic.expressions.TGGLiteralExpression;

public class InplaceAttribute2ViatraCheck {
	
	public static String extractViatraCheck(String attributeName, TGGExpression expression) {
		if (expression instanceof TGGLiteralExpression) {
			TGGLiteralExpression tle = (TGGLiteralExpression) expression;
			return attributeName + ".equals(" + tle.getValue() + ")";			
		}
		return null;
	}
}
