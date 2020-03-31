/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXCSP;
import IBeXLanguage.IBeXLanguageFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XCSP</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXCSPTest extends TestCase {

	/**
	 * The fixture for this IBe XCSP test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXCSP fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXCSPTest.class);
	}

	/**
	 * Constructs a new IBe XCSP test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXCSPTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this IBe XCSP test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(IBeXCSP fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this IBe XCSP test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IBeXCSP getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBeXLanguageFactory.eINSTANCE.createIBeXCSP());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //IBeXCSPTest
