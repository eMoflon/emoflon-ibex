/**
 */
package language;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Domain Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see language.LanguagePackage#getDomainType()
 * @model
 * @generated
 */
public enum DomainType implements Enumerator {
	/**
	 * The '<em><b>SRC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SRC_VALUE
	 * @generated
	 * @ordered
	 */
	SRC(0, "SRC", "SRC"),

	/**
	 * The '<em><b>TRG</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRG_VALUE
	 * @generated
	 * @ordered
	 */
	TRG(1, "TRG", "TRG"),

	/**
	 * The '<em><b>CORR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORR_VALUE
	 * @generated
	 * @ordered
	 */
	CORR(2, "CORR", "CORR");

	/**
	 * The '<em><b>SRC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SRC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SRC_VALUE = 0;

	/**
	 * The '<em><b>TRG</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TRG
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TRG_VALUE = 1;

	/**
	 * The '<em><b>CORR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CORR_VALUE = 2;

	/**
	 * An array of all the '<em><b>Domain Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DomainType[] VALUES_ARRAY = new DomainType[] { SRC, TRG, CORR, };

	/**
	 * A public read-only list of all the '<em><b>Domain Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DomainType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Domain Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DomainType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DomainType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Domain Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DomainType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DomainType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Domain Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DomainType get(int value) {
		switch (value) {
		case SRC_VALUE:
			return SRC;
		case TRG_VALUE:
			return TRG;
		case CORR_VALUE:
			return CORR;
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
	private DomainType(int value, String name, String literal) {
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

} //DomainType
