package org.emoflon.ibex.common.transformation;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.ValueExpression;

public final class DataTypeUtil {
	public static EClassifier mergeDataTypes(final ValueExpression lhs, final ValueExpression rhs) {
		if (lhs.getType() == EcorePackage.Literals.EBYTE || lhs.getType() == EcorePackage.Literals.ESHORT
				|| lhs.getType() == EcorePackage.Literals.EINT || lhs.getType() == EcorePackage.Literals.ELONG) {
			return mergeInt(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.EFLOAT || lhs.getType() == EcorePackage.Literals.EDOUBLE) {
			return mergeDouble(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.ESTRING) {
			return mergeString(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.EENUM || lhs.getType() == EcorePackage.Literals.EENUM_LITERAL
				|| lhs.getType() == EcorePackage.Literals.EENUMERATOR) {
			return mergeEnum(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.EBOOLEAN) {
			return mergeBoolean(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.EDATE) {
			return mergeDate(rhs);
		} else if (lhs.getType() == EcorePackage.Literals.EOBJECT) {
			return mergeObject(rhs);
		} else {
			throw new UnsupportedOperationException("Unkown or unsupported data type: " + lhs.getType());
		}
	}

	public static EClassifier mergeInt(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.EBYTE || expr.getType() == EcorePackage.Literals.ESHORT
				|| expr.getType() == EcorePackage.Literals.EINT || expr.getType() == EcorePackage.Literals.ELONG) {
			return EcorePackage.Literals.EINT;
		} else if (expr.getType() == EcorePackage.Literals.EFLOAT || expr.getType() == EcorePackage.Literals.EDOUBLE) {
			return EcorePackage.Literals.EDOUBLE;
		} else if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with Integer data type or unsupported: " + expr.getType());
		}
	}

	public static EClassifier mergeDouble(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.EBYTE || expr.getType() == EcorePackage.Literals.ESHORT
				|| expr.getType() == EcorePackage.Literals.EINT || expr.getType() == EcorePackage.Literals.ELONG) {
			return EcorePackage.Literals.EDOUBLE;
		} else if (expr.getType() == EcorePackage.Literals.EFLOAT || expr.getType() == EcorePackage.Literals.EDOUBLE) {
			return EcorePackage.Literals.EDOUBLE;
		} else if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with Double data type or unsupported: " + expr.getType());
		}
	}

	public static EClassifier mergeString(final ValueExpression expr) {
		return EcorePackage.Literals.ESTRING;
	}

	public static EClassifier mergeEnum(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else if (expr.getType() == EcorePackage.Literals.EENUM
				|| expr.getType() == EcorePackage.Literals.EENUM_LITERAL
				|| expr.getType() == EcorePackage.Literals.EENUMERATOR) {
			return EcorePackage.Literals.EENUM;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with Enum data type or unsupported: " + expr.getType());
		}
	}

	public static EClassifier mergeBoolean(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else if (expr.getType() == EcorePackage.Literals.EBOOLEAN) {
			return EcorePackage.Literals.EBOOLEAN;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with Boolean data type or unsupported: " + expr.getType());
		}
	}

	public static EClassifier mergeDate(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else if (expr.getType() == EcorePackage.Literals.EDATE) {
			return EcorePackage.Literals.EDATE;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with EDate data type or unsupported: " + expr.getType());
		}
	}

	public static EClassifier mergeObject(final ValueExpression expr) {
		if (expr.getType() == EcorePackage.Literals.ESTRING) {
			return EcorePackage.Literals.ESTRING;
		} else if (expr.getType() == EcorePackage.Literals.EOBJECT) {
			return EcorePackage.Literals.EOBJECT;
		} else {
			throw new UnsupportedOperationException(
					"Data type is incompatible with EObjects or unsupported: " + expr.getType());
		}
	}
}
