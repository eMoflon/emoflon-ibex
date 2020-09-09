/**
 */
package org.emoflon.ibex.patternmodel.IBeXPatternModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>IBe XBinary Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelPackage#getIBeXBinaryOperator()
 * @model
 * @generated
 */
public enum IBeXBinaryOperator implements Enumerator {
	/**
	 * The '<em><b>ADDITION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADDITION_VALUE
	 * @generated
	 * @ordered
	 */
	ADDITION(0, "ADDITION", "ADDITION"),

	/**
	 * The '<em><b>MULTIPLICATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MULTIPLICATION_VALUE
	 * @generated
	 * @ordered
	 */
	MULTIPLICATION(1, "MULTIPLICATION", "MULTIPLICATION"),

	/**
	 * The '<em><b>DIVISION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIVISION_VALUE
	 * @generated
	 * @ordered
	 */
	DIVISION(2, "DIVISION", "DIVISION"),

	/**
	 * The '<em><b>MODULUS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODULUS_VALUE
	 * @generated
	 * @ordered
	 */
	MODULUS(3, "MODULUS", "MODULUS"),

	/**
	 * The '<em><b>EXPONENTIATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIATION_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIATION(4, "EXPONENTIATION", "EXPONENTIATION"),

	/**
	 * The '<em><b>SUBTRACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBTRACTION_VALUE
	 * @generated
	 * @ordered
	 */
	SUBTRACTION(5, "SUBTRACTION", "SUBTRACTION"),

	/**
	 * The '<em><b>MINIMUM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MINIMUM(6, "MINIMUM", "MINIMUM"),

	/**
	 * The '<em><b>MAXIMUM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM_VALUE
	 * @generated
	 * @ordered
	 */
	MAXIMUM(7, "MAXIMUM", "MAXIMUM");

	/**
	 * The '<em><b>ADDITION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADDITION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ADDITION_VALUE = 0;

	/**
	 * The '<em><b>MULTIPLICATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MULTIPLICATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MULTIPLICATION_VALUE = 1;

	/**
	 * The '<em><b>DIVISION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIVISION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIVISION_VALUE = 2;

	/**
	 * The '<em><b>MODULUS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODULUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODULUS_VALUE = 3;

	/**
	 * The '<em><b>EXPONENTIATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIATION_VALUE = 4;

	/**
	 * The '<em><b>SUBTRACTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBTRACTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SUBTRACTION_VALUE = 5;

	/**
	 * The '<em><b>MINIMUM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MINIMUM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MINIMUM_VALUE = 6;

	/**
	 * The '<em><b>MAXIMUM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAXIMUM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MAXIMUM_VALUE = 7;

	/**
	 * An array of all the '<em><b>IBe XBinary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final IBeXBinaryOperator[] VALUES_ARRAY = new IBeXBinaryOperator[] { ADDITION, MULTIPLICATION,
			DIVISION, MODULUS, EXPONENTIATION, SUBTRACTION, MINIMUM, MAXIMUM, };

	/**
	 * A public read-only list of all the '<em><b>IBe XBinary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<IBeXBinaryOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>IBe XBinary Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXBinaryOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXBinaryOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XBinary Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXBinaryOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXBinaryOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XBinary Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXBinaryOperator get(int value) {
		switch (value) {
		case ADDITION_VALUE:
			return ADDITION;
		case MULTIPLICATION_VALUE:
			return MULTIPLICATION;
		case DIVISION_VALUE:
			return DIVISION;
		case MODULUS_VALUE:
			return MODULUS;
		case EXPONENTIATION_VALUE:
			return EXPONENTIATION;
		case SUBTRACTION_VALUE:
			return SUBTRACTION;
		case MINIMUM_VALUE:
			return MINIMUM;
		case MAXIMUM_VALUE:
			return MAXIMUM;
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
	private IBeXBinaryOperator(int value, String name, String literal) {
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

} //IBeXBinaryOperator
