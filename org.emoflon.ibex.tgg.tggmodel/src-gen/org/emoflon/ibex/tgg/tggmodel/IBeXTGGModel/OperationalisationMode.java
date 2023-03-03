/**
 */
package org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Operationalisation Mode</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelPackage#getOperationalisationMode()
 * @model
 * @generated
 */
public enum OperationalisationMode implements Enumerator {
	/**
	 * The '<em><b>GENERATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERATE_VALUE
	 * @generated
	 * @ordered
	 */
	GENERATE(0, "GENERATE", "GENERATE"),

	/**
	 * The '<em><b>FORWARD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FORWARD_VALUE
	 * @generated
	 * @ordered
	 */
	FORWARD(1, "FORWARD", "FORWARD"),

	/**
	 * The '<em><b>BACKWARD</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BACKWARD_VALUE
	 * @generated
	 * @ordered
	 */
	BACKWARD(2, "BACKWARD", "BACKWARD"),

	/**
	 * The '<em><b>CONSISTENCY CHECK</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONSISTENCY_CHECK_VALUE
	 * @generated
	 * @ordered
	 */
	CONSISTENCY_CHECK(3, "CONSISTENCY_CHECK", "CONSISTENCY_CHECK"),

	/**
	 * The '<em><b>CHECK ONLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHECK_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	CHECK_ONLY(4, "CHECK_ONLY", "CHECK_ONLY"),
	/**
	 * The '<em><b>SOURCE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	 * @see #SOURCE_VALUE
	 * @generated
	 * @ordered
	 */
	SOURCE(5, "SOURCE", "SOURCE"),
	/**
	 * The '<em><b>TARGET</b></em>' literal object.
	 * <!-- begin-user-doc -->
	* <!-- end-user-doc -->
	 * @see #TARGET_VALUE
	 * @generated
	 * @ordered
	 */
	TARGET(6, "TARGET", "TARGET");

	/**
	 * The '<em><b>GENERATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GENERATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GENERATE_VALUE = 0;

	/**
	 * The '<em><b>FORWARD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FORWARD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FORWARD_VALUE = 1;

	/**
	 * The '<em><b>BACKWARD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BACKWARD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BACKWARD_VALUE = 2;

	/**
	 * The '<em><b>CONSISTENCY CHECK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CONSISTENCY_CHECK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONSISTENCY_CHECK_VALUE = 3;

	/**
	 * The '<em><b>CHECK ONLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHECK_ONLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CHECK_ONLY_VALUE = 4;

	/**
	 * The '<em><b>SOURCE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SOURCE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SOURCE_VALUE = 5;

	/**
	 * The '<em><b>TARGET</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TARGET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TARGET_VALUE = 6;

	/**
	 * An array of all the '<em><b>Operationalisation Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OperationalisationMode[] VALUES_ARRAY = new OperationalisationMode[] { GENERATE, FORWARD,
			BACKWARD, CONSISTENCY_CHECK, CHECK_ONLY, SOURCE, TARGET, };

	/**
	 * A public read-only list of all the '<em><b>Operationalisation Mode</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OperationalisationMode> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Operationalisation Mode</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OperationalisationMode get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OperationalisationMode result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Operationalisation Mode</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OperationalisationMode getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OperationalisationMode result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Operationalisation Mode</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static OperationalisationMode get(int value) {
		switch (value) {
		case GENERATE_VALUE:
			return GENERATE;
		case FORWARD_VALUE:
			return FORWARD;
		case BACKWARD_VALUE:
			return BACKWARD;
		case CONSISTENCY_CHECK_VALUE:
			return CONSISTENCY_CHECK;
		case CHECK_ONLY_VALUE:
			return CHECK_ONLY;
		case SOURCE_VALUE:
			return SOURCE;
		case TARGET_VALUE:
			return TARGET;
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
	private OperationalisationMode(int value, String name, String literal) {
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

} //OperationalisationMode
