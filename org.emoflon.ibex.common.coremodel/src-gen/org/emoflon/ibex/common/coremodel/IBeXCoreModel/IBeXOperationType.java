/**
 */
package org.emoflon.ibex.common.coremodel.IBeXCoreModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>IBe XOperation Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelPackage#getIBeXOperationType()
 * @model
 * @generated
 */
public enum IBeXOperationType implements Enumerator {
	/**
	 * The '<em><b>CONTEXT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTEXT_VALUE
	 * @generated
	 * @ordered
	 */
	CONTEXT(0, "CONTEXT", "CONTEXT"),

	/**
	 * The '<em><b>CREATION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATION_VALUE
	 * @generated
	 * @ordered
	 */
	CREATION(1, "CREATION", "CREATION"),

	/**
	 * The '<em><b>DELETION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETION_VALUE
	 * @generated
	 * @ordered
	 */
	DELETION(2, "DELETION", "DELETION");

	/**
	 * The '<em><b>CONTEXT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONTEXT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONTEXT_VALUE = 0;

	/**
	 * The '<em><b>CREATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CREATION_VALUE = 1;

	/**
	 * The '<em><b>DELETION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DELETION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DELETION_VALUE = 2;

	/**
	 * An array of all the '<em><b>IBe XOperation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final IBeXOperationType[] VALUES_ARRAY = new IBeXOperationType[] { CONTEXT, CREATION, DELETION, };

	/**
	 * A public read-only list of all the '<em><b>IBe XOperation Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<IBeXOperationType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>IBe XOperation Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXOperationType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXOperationType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XOperation Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXOperationType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			IBeXOperationType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>IBe XOperation Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static IBeXOperationType get(int value) {
		switch (value) {
		case CONTEXT_VALUE:
			return CONTEXT;
		case CREATION_VALUE:
			return CREATION;
		case DELETION_VALUE:
			return DELETION;
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
	private IBeXOperationType(int value, String name, String literal) {
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

} //IBeXOperationType
