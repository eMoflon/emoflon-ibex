/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Unary Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getUnaryOperator()
 * @model
 * @generated
 */
public enum UnaryOperator implements Enumerator {
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
	 * The '<em><b>BRACKET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BRACKET_VALUE
	 * @generated
	 * @ordered
	 */
	BRACKET(5, "BRACKET", "BRACKET"),

	/**
	 * The '<em><b>NEGATIVE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEGATIVE_VALUE
	 * @generated
	 * @ordered
	 */
	NEGATIVE(6, "NEGATIVE", "NEGATIVE"),

	/**
	 * The '<em><b>EXPONENTIAL DISTRIBUTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_DISTRIBUTION_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIAL_DISTRIBUTION(7, "EXPONENTIAL_DISTRIBUTION", "EXPONENTIAL_DISTRIBUTION");

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
	 * The '<em><b>BRACKET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BRACKET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BRACKET_VALUE = 5;

	/**
	 * The '<em><b>NEGATIVE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NEGATIVE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NEGATIVE_VALUE = 6;

	/**
	 * The '<em><b>EXPONENTIAL DISTRIBUTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_DISTRIBUTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIAL_DISTRIBUTION_VALUE = 7;

	/**
	 * An array of all the '<em><b>Unary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final UnaryOperator[] VALUES_ARRAY = new UnaryOperator[] { SQRT, ABSOLUTE, SIN, COS, TAN, BRACKET,
			NEGATIVE, EXPONENTIAL_DISTRIBUTION, };

	/**
	 * A public read-only list of all the '<em><b>Unary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<UnaryOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			UnaryOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryOperator get(int value) {
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
		case BRACKET_VALUE:
			return BRACKET;
		case NEGATIVE_VALUE:
			return NEGATIVE;
		case EXPONENTIAL_DISTRIBUTION_VALUE:
			return EXPONENTIAL_DISTRIBUTION;
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
	private UnaryOperator(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
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

} //UnaryOperator
