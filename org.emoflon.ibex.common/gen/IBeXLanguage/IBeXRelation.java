/**
 */
package IBeXLanguage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>IBe XRelation</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see IBeXLanguage.IBeXLanguagePackage#getIBeXRelation()
 * @model
 * @generated
 */
public enum IBeXRelation implements Enumerator {
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
	SMALLER(5, "SMALLER", "SMALLER");

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
	 * An array of all the '<em><b>IBe XRelation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final IBeXRelation[] VALUES_ARRAY = new IBeXRelation[] { GREATER_OR_EQUAL, GREATER, EQUAL, UNEQUAL,
			SMALLER_OR_EQUAL, SMALLER, };

	/**
	 * A public read-only list of all the '<em><b>IBe XRelation</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<IBeXRelation> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>IBe XRelation</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXRelation get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXRelation result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XRelation</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXRelation getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXRelation result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XRelation</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXRelation get(int value) {
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
	private IBeXRelation(int value, String name, String literal) {
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

} //IBeXRelation
