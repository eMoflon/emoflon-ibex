/**
 */
package language;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>TGG Attribute Constraint Operators</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see language.LanguagePackage#getTGGAttributeConstraintOperators()
 * @model
 * @generated
 */
public enum TGGAttributeConstraintOperators implements Enumerator {
	/**
	 * The '<em><b>EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	EQUAL(0, "EQUAL", "EQUAL"),

	/**
	 * The '<em><b>UNEQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNEQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	UNEQUAL(1, "UNEQUAL", "UNEQUAL"),

	/**
	 * The '<em><b>GR EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GR_EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	GR_EQUAL(2, "GR_EQUAL", "GR_EQUAL"),

	/**
	 * The '<em><b>LE EQUAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LE_EQUAL_VALUE
	 * @generated
	 * @ordered
	 */
	LE_EQUAL(3, "LE_EQUAL", "LE_EQUAL"),

	/**
	 * The '<em><b>GREATER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER_VALUE
	 * @generated
	 * @ordered
	 */
	GREATER(4, "GREATER", "GREATER"),

	/**
	 * The '<em><b>LESSER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LESSER_VALUE
	 * @generated
	 * @ordered
	 */
	LESSER(5, "LESSER", "LESSER");

	/**
	 * The '<em><b>EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EQUAL_VALUE = 0;

	/**
	 * The '<em><b>UNEQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNEQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int UNEQUAL_VALUE = 1;

	/**
	 * The '<em><b>GR EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GR_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GR_EQUAL_VALUE = 2;

	/**
	 * The '<em><b>LE EQUAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LE_EQUAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LE_EQUAL_VALUE = 3;

	/**
	 * The '<em><b>GREATER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GREATER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GREATER_VALUE = 4;

	/**
	 * The '<em><b>LESSER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LESSER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LESSER_VALUE = 5;

	/**
	 * An array of all the '<em><b>TGG Attribute Constraint Operators</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TGGAttributeConstraintOperators[] VALUES_ARRAY = new TGGAttributeConstraintOperators[] { EQUAL, UNEQUAL, GR_EQUAL, LE_EQUAL, GREATER, LESSER, };

	/**
	 * A public read-only list of all the '<em><b>TGG Attribute Constraint Operators</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TGGAttributeConstraintOperators> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>TGG Attribute Constraint Operators</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TGGAttributeConstraintOperators get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TGGAttributeConstraintOperators result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TGG Attribute Constraint Operators</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TGGAttributeConstraintOperators getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TGGAttributeConstraintOperators result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>TGG Attribute Constraint Operators</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static TGGAttributeConstraintOperators get(int value) {
		switch (value) {
		case EQUAL_VALUE:
			return EQUAL;
		case UNEQUAL_VALUE:
			return UNEQUAL;
		case GR_EQUAL_VALUE:
			return GR_EQUAL;
		case LE_EQUAL_VALUE:
			return LE_EQUAL;
		case GREATER_VALUE:
			return GREATER;
		case LESSER_VALUE:
			return LESSER;
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
	private TGGAttributeConstraintOperators(int value, String name, String literal) {
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

} //TGGAttributeConstraintOperators
