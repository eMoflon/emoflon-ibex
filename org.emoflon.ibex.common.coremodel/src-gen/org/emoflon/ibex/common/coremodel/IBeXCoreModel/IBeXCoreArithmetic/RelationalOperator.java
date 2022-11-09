/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Relational Operator</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.IBeXCoreArithmeticPackage#getRelationalOperator()
 * @model
 * @generated
 */
public enum RelationalOperator implements Enumerator {
	/**
	 * The '<em><b>GREATER OR EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_OR_EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER_OR_EQUAL(0, "GREATER_OR_EQUAL", "GREATER_OR_EQUAL"),

	/**
	 * The '<em><b>GREATER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER(1, "GREATER", "GREATER"),

	/**
	 * The '<em><b>EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	EQUAL(2, "EQUAL", "EQUAL"),

	/**
	 * The '<em><b>UNEQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNEQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	UNEQUAL(3, "UNEQUAL", "UNEQUAL"),

	/**
	 * The '<em><b>SMALLER OR EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_OR_EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	SMALLER_OR_EQUAL(4, "SMALLER_OR_EQUAL", "SMALLER_OR_EQUAL"),

	/**
	 * The '<em><b>SMALLER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_VALUE
	 * @generated
	 * @ordered
	 */
	SMALLER(5, "SMALLER", "SMALLER"),

	/**
	 * The '<em><b>OBJECT EQUALS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_EQUALS_VALUE
	 * @generated
	 * @ordered
	 */
	OBJECT_EQUALS(6, "OBJECT_EQUALS", "OBJECT_EQUALS"),

	/**
	 * The '<em><b>OBJECT NOT EQUALS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_NOT_EQUALS_VALUE
	 * @generated
	 * @ordered
	 */
	OBJECT_NOT_EQUALS(7, "OBJECT_NOT_EQUALS", "OBJECT_NOT_EQUALS"),
	/**
	* The '<em><b>OBJECT GREATER OR EQUAL</b></em>' literal object.
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @see #OBJECT_GREATER_OR_EQUAL_VALUE
	* @generated
	* @ordered
	*/
	OBJECT_GREATER_OR_EQUAL(8, "OBJECT_GREATER_OR_EQUAL", "OBJECT_GREATER_OR_EQUAL"),
	/**
	* The '<em><b>OBJECT GREATER</b></em>' literal object.
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @see #OBJECT_GREATER_VALUE
	* @generated
	* @ordered
	*/
	OBJECT_GREATER(9, "OBJECT_GREATER", "OBJECT_GREATER"),
	/**
	* The '<em><b>OBJECT SMALLER OR EQUAL</b></em>' literal object.
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @see #OBJECT_SMALLER_OR_EQUAL_VALUE
	* @generated
	* @ordered
	*/
	OBJECT_SMALLER_OR_EQUAL(10, "OBJECT_SMALLER_OR_EQUAL", "OBJECT_SMALLER_OR_EQUAL"),
	/**
	* The '<em><b>OBJECT SMALLER</b></em>' literal object.
	* <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	* @see #OBJECT_SMALLER_VALUE
	* @generated
	* @ordered
	*/
	OBJECT_SMALLER(11, "OBJECT_SMALLER", "OBJECT_SMALLER");

	/**
	 * The '<em><b>GREATER OR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_OR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_OR_EQUAL_VALUE = 0;

	/**
	 * The '<em><b>GREATER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_VALUE = 1;

	/**
	 * The '<em><b>EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EQUAL_VALUE = 2;

	/**
	 * The '<em><b>UNEQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNEQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNEQUAL_VALUE = 3;

	/**
	 * The '<em><b>SMALLER OR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER_OR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SMALLER_OR_EQUAL_VALUE = 4;

	/**
	 * The '<em><b>SMALLER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SMALLER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SMALLER_VALUE = 5;

	/**
	 * The '<em><b>OBJECT EQUALS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_EQUALS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_EQUALS_VALUE = 6;

	/**
	 * The '<em><b>OBJECT NOT EQUALS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_NOT_EQUALS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_NOT_EQUALS_VALUE = 7;

	/**
	 * The '<em><b>OBJECT GREATER OR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_GREATER_OR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_GREATER_OR_EQUAL_VALUE = 8;

	/**
	 * The '<em><b>OBJECT GREATER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_GREATER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_GREATER_VALUE = 9;

	/**
	 * The '<em><b>OBJECT SMALLER OR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_SMALLER_OR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_SMALLER_OR_EQUAL_VALUE = 10;

	/**
	 * The '<em><b>OBJECT SMALLER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OBJECT_SMALLER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OBJECT_SMALLER_VALUE = 11;

	/**
	 * An array of all the '<em><b>Relational Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final RelationalOperator[] VALUES_ARRAY = new RelationalOperator[] { GREATER_OR_EQUAL, GREATER,
			EQUAL, UNEQUAL, SMALLER_OR_EQUAL, SMALLER, OBJECT_EQUALS, OBJECT_NOT_EQUALS, OBJECT_GREATER_OR_EQUAL,
			OBJECT_GREATER, OBJECT_SMALLER_OR_EQUAL, OBJECT_SMALLER, };

	/**
	 * A public read-only list of all the '<em><b>Relational Operator</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<RelationalOperator> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Relational Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RelationalOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RelationalOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Relational Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RelationalOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			RelationalOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Relational Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static RelationalOperator get(int value) {
		switch (value) {
		case GREATER_OR_EQUAL_VALUE:
			return GREATER_OR_EQUAL;
		case GREATER_VALUE:
			return GREATER;
		case EQUAL_VALUE:
			return EQUAL;
		case UNEQUAL_VALUE:
			return UNEQUAL;
		case SMALLER_OR_EQUAL_VALUE:
			return SMALLER_OR_EQUAL;
		case SMALLER_VALUE:
			return SMALLER;
		case OBJECT_EQUALS_VALUE:
			return OBJECT_EQUALS;
		case OBJECT_NOT_EQUALS_VALUE:
			return OBJECT_NOT_EQUALS;
		case OBJECT_GREATER_OR_EQUAL_VALUE:
			return OBJECT_GREATER_OR_EQUAL;
		case OBJECT_GREATER_VALUE:
			return OBJECT_GREATER;
		case OBJECT_SMALLER_OR_EQUAL_VALUE:
			return OBJECT_SMALLER_OR_EQUAL;
		case OBJECT_SMALLER_VALUE:
			return OBJECT_SMALLER;
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
	private RelationalOperator(int value, String name, String literal) {
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

} //RelationalOperator
