/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>IBe XUnary Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXUnaryOperator()
 * @model
 * @generated
 */
public enum IBeXUnaryOperator implements Enumerator {
	/**
	 * The '<em><b>SQRT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SQRT_VALUE
	 * @generated
	 * @ordered
	 */
	SQRT(0, "SQRT", "SQRT"),

	/**
	 * The '<em><b>ABSOLUTE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABSOLUTE_VALUE
	 * @generated
	 * @ordered
	 */
	ABSOLUTE(1, "ABSOLUTE", "ABSOLUTE"),

	/**
	 * The '<em><b>SIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIN_VALUE
	 * @generated
	 * @ordered
	 */
	SIN(2, "SIN", "SIN"),

	/**
	 * The '<em><b>COS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COS_VALUE
	 * @generated
	 * @ordered
	 */
	COS(3, "COS", "COS"),

	/**
	 * The '<em><b>TAN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TAN_VALUE
	 * @generated
	 * @ordered
	 */
	TAN(4, "TAN", "TAN"),

	/**
	 * The '<em><b>EEXPONENTIAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EEXPONENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	EEXPONENTIAL(5, "E_EXPONENTIAL", "E_EXPONENTIAL"),

	/**
	 * The '<em><b>LOG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOG_VALUE
	 * @generated
	 * @ordered
	 */
	LOG(6, "LOG", "LOG"),

	/**
	 * The '<em><b>LG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LG_VALUE
	 * @generated
	 * @ordered
	 */
	LG(7, "LG", "LG"),

	/**
	 * The '<em><b>BRACKET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BRACKET_VALUE
	 * @generated
	 * @ordered
	 */
	BRACKET(8, "BRACKET", "BRACKET");

	/**
	 * The '<em><b>SQRT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SQRT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SQRT_VALUE = 0;

	/**
	 * The '<em><b>ABSOLUTE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABSOLUTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ABSOLUTE_VALUE = 1;

	/**
	 * The '<em><b>SIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SIN_VALUE = 2;

	/**
	 * The '<em><b>COS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COS_VALUE = 3;

	/**
	 * The '<em><b>TAN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TAN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TAN_VALUE = 4;

	/**
	 * The '<em><b>EEXPONENTIAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EEXPONENTIAL
	 * @model name="E_EXPONENTIAL"
	 * @generated
	 * @ordered
	 */
	public static final int EEXPONENTIAL_VALUE = 5;

	/**
	 * The '<em><b>LOG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOG
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOG_VALUE = 6;

	/**
	 * The '<em><b>LG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LG
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LG_VALUE = 7;

	/**
	 * The '<em><b>BRACKET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BRACKET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BRACKET_VALUE = 8;

	/**
	 * An array of all the '<em><b>IBe XUnary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final IBeXUnaryOperator[] VALUES_ARRAY = new IBeXUnaryOperator[] { SQRT, ABSOLUTE, SIN, COS, TAN,
			EEXPONENTIAL, LOG, LG, BRACKET, };

	/**
	 * A public read-only list of all the '<em><b>IBe XUnary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<IBeXUnaryOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>IBe XUnary Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXUnaryOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXUnaryOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XUnary Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXUnaryOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXUnaryOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XUnary Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXUnaryOperator get(int value) {
		switch (value) {
		case SQRT_VALUE:
			return SQRT;
		case ABSOLUTE_VALUE:
			return ABSOLUTE;
		case SIN_VALUE:
			return SIN;
		case COS_VALUE:
			return COS;
		case TAN_VALUE:
			return TAN;
		case EEXPONENTIAL_VALUE:
			return EEXPONENTIAL;
		case LOG_VALUE:
			return LOG;
		case LG_VALUE:
			return LG;
		case BRACKET_VALUE:
			return BRACKET;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private IBeXUnaryOperator(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //IBeXUnaryOperator
