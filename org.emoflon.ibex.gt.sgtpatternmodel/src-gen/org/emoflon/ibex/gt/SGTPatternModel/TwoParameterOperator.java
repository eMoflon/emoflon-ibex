/**
 */
package org.emoflon.ibex.gt.SGTPatternModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Two Parameter Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.gt.SGTPatternModel.SGTPatternModelPackage#getTwoParameterOperator()
 * @model
 * @generated
 */
public enum TwoParameterOperator implements Enumerator {
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
	 * The '<em><b>MODULO</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODULO_VALUE
	 * @generated
	 * @ordered
	 */
	MODULO(3, "MODULO", "MODULO"),

	/**
	 * The '<em><b>EXPONENTIAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIAL(4, "EXPONENTIAL", "EXPONENTIAL"),

	/**
	 * The '<em><b>SUBTRACTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBTRACTION_VALUE
	 * @generated
	 * @ordered
	 */
	SUBTRACTION(5, "SUBTRACTION", "SUBTRACTION");

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
	 * The '<em><b>MODULO</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MODULO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MODULO_VALUE = 3;

	/**
	 * The '<em><b>EXPONENTIAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIAL_VALUE = 4;

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
	 * An array of all the '<em><b>Two Parameter Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TwoParameterOperator[] VALUES_ARRAY = new TwoParameterOperator[] { ADDITION, MULTIPLICATION,
			DIVISION, MODULO, EXPONENTIAL, SUBTRACTION, };

	/**
	 * A public read-only list of all the '<em><b>Two Parameter Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TwoParameterOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Two Parameter Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TwoParameterOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TwoParameterOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Two Parameter Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TwoParameterOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TwoParameterOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Two Parameter Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TwoParameterOperator get(int value) {
		switch (value) {
		case ADDITION_VALUE:
			return ADDITION;
		case MULTIPLICATION_VALUE:
			return MULTIPLICATION;
		case DIVISION_VALUE:
			return DIVISION;
		case MODULO_VALUE:
			return MODULO;
		case EXPONENTIAL_VALUE:
			return EXPONENTIAL;
		case SUBTRACTION_VALUE:
			return SUBTRACTION;
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
	private TwoParameterOperator(int value, String name, String literal) {
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

} //TwoParameterOperator
