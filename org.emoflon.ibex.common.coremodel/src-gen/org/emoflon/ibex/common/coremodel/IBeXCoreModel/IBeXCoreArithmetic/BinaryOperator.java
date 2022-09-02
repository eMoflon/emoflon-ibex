/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Binary Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getBinaryOperator()
 * @model
 * @generated
 */
public enum BinaryOperator implements Enumerator {
	/**
	 * The '<em><b>ADD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD_VALUE
	 * @generated
	 * @ordered
	 */
	ADD(0, "ADD", "ADD"),

	/**
	 * The '<em><b>MULTIPLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MULTIPLY_VALUE
	 * @generated
	 * @ordered
	 */
	MULTIPLY(1, "MULTIPLY", "MULTIPLY"),

	/**
	 * The '<em><b>DIVIDE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIVIDE_VALUE
	 * @generated
	 * @ordered
	 */
	DIVIDE(2, "DIVIDE", "DIVIDE"),

	/**
	 * The '<em><b>MOD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOD_VALUE
	 * @generated
	 * @ordered
	 */
	MOD(3, "MOD", "MOD"),

	/**
	 * The '<em><b>POW</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POW_VALUE
	 * @generated
	 * @ordered
	 */
	POW(4, "POW", "POW"),

	/**
	 * The '<em><b>SUBTRACT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBTRACT_VALUE
	 * @generated
	 * @ordered
	 */
	SUBTRACT(5, "SUBTRACT", "SUBTRACT"),

	/**
	 * The '<em><b>MIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MIN_VALUE
	 * @generated
	 * @ordered
	 */
	MIN(6, "MIN", "MIN"),

	/**
	 * The '<em><b>MAX</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAX_VALUE
	 * @generated
	 * @ordered
	 */
	MAX(7, "MAX", "MAX"),

	/**
	 * The '<em><b>NORMAL DISTRIBUTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL_DISTRIBUTION_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL_DISTRIBUTION(8, "NORMAL_DISTRIBUTION", "NORMAL_DISTRIBUTION"),

	/**
	 * The '<em><b>UNIFORM DISTRIBUTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNIFORM_DISTRIBUTION_VALUE
	 * @generated
	 * @ordered
	 */
	UNIFORM_DISTRIBUTION(9, "UNIFORM_DISTRIBUTION", "UNIFORM_DISTRIBUTION");

	/**
	 * The '<em><b>ADD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ADD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ADD_VALUE = 0;

	/**
	 * The '<em><b>MULTIPLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MULTIPLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MULTIPLY_VALUE = 1;

	/**
	 * The '<em><b>DIVIDE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DIVIDE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIVIDE_VALUE = 2;

	/**
	 * The '<em><b>MOD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MOD_VALUE = 3;

	/**
	 * The '<em><b>POW</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #POW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int POW_VALUE = 4;

	/**
	 * The '<em><b>SUBTRACT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUBTRACT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SUBTRACT_VALUE = 5;

	/**
	 * The '<em><b>MIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MIN_VALUE = 6;

	/**
	 * The '<em><b>MAX</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MAX
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MAX_VALUE = 7;

	/**
	 * The '<em><b>NORMAL DISTRIBUTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL_DISTRIBUTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL_DISTRIBUTION_VALUE = 8;

	/**
	 * The '<em><b>UNIFORM DISTRIBUTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNIFORM_DISTRIBUTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNIFORM_DISTRIBUTION_VALUE = 9;

	/**
	 * An array of all the '<em><b>Binary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final BinaryOperator[] VALUES_ARRAY = new BinaryOperator[] { ADD, MULTIPLY, DIVIDE, MOD, POW,
			SUBTRACT, MIN, MAX, NORMAL_DISTRIBUTION, UNIFORM_DISTRIBUTION, };

	/**
	 * A public read-only list of all the '<em><b>Binary Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<BinaryOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Binary Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BinaryOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Binary Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BinaryOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Binary Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryOperator get(int value) {
		switch (value) {
		case ADD_VALUE:
			return ADD;
		case MULTIPLY_VALUE:
			return MULTIPLY;
		case DIVIDE_VALUE:
			return DIVIDE;
		case MOD_VALUE:
			return MOD;
		case POW_VALUE:
			return POW;
		case SUBTRACT_VALUE:
			return SUBTRACT;
		case MIN_VALUE:
			return MIN;
		case MAX_VALUE:
			return MAX;
		case NORMAL_DISTRIBUTION_VALUE:
			return NORMAL_DISTRIBUTION;
		case UNIFORM_DISTRIBUTION_VALUE:
			return UNIFORM_DISTRIBUTION;
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
	private BinaryOperator(int value, String name, String literal) {
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

} //BinaryOperator
