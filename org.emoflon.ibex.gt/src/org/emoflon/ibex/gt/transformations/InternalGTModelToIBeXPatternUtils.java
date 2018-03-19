package org.emoflon.ibex.gt.transformations;

import GTLanguage.GTAttributeValue;
import GTLanguage.GTBinaryRelation;
import GTLanguage.GTConstant;
import GTLanguage.GTEnumLiteral;
import GTLanguage.GTParameterReference;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXAttributeValue;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXEnumLiteral;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXRelation;

/**
 * Utility methods for the internal GT model to IBeXPatterns transformation.
 */
public class InternalGTModelToIBeXPatternUtils {

	/**
	 * Converts an GTRelation to an IBeXRelation.
	 * 
	 * @param gtRelation
	 *            the GTRelation
	 * @return the IBeXRelation
	 */
	public static IBeXRelation convertRelation(final GTBinaryRelation gtRelation) {
		switch (gtRelation) {
		case GREATER_OR_EQUAL:
			return IBeXRelation.GREATER_OR_EQUAL;
		case GREATER:
			return IBeXRelation.GREATER;
		case EQUAL:
			return IBeXRelation.EQUAL;
		case UNEQUAL:
			return IBeXRelation.UNEQUAL;
		case SMALLER:
			return IBeXRelation.SMALLER;
		case SMALLER_OR_EQUAL:
			return IBeXRelation.SMALLER_OR_EQUAL;
		default:
			throw new IllegalArgumentException("Invalid value " + gtRelation);
		}
	}

	/**
	 * Converts attribute values.
	 * 
	 * @param gtAttrValue
	 *            the GTAttributeValue
	 * @return the IBeXAttributeValue
	 */
	public static IBeXAttributeValue convertAttributeValue(final GTAttributeValue gtAttrValue) {
		if (gtAttrValue instanceof GTConstant) {
			IBeXConstant ibexConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
			ibexConstant.setValue(((GTConstant) gtAttrValue).getValue());
			ibexConstant.setStringValue(((GTConstant) gtAttrValue).getStringValue());
			return ibexConstant;
		}
		if (gtAttrValue instanceof GTEnumLiteral) {
			IBeXEnumLiteral ibexEnumLiteral = IBeXLanguageFactory.eINSTANCE.createIBeXEnumLiteral();
			ibexEnumLiteral.setLiteral(((GTEnumLiteral) gtAttrValue).getLiteral());
			return ibexEnumLiteral;
		}
		if (gtAttrValue instanceof GTParameterReference) {
			IBeXAttributeParameter ibexParameter = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeParameter();
			ibexParameter.setName(((GTParameterReference) gtAttrValue).getParameter().getName());
			return ibexParameter;
		}
		throw new IllegalArgumentException("Invalid value " + gtAttrValue);
	}
}
