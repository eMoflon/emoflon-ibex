/**
 */
package StochasticLanguage.tests;

import StochasticLanguage.GTNumber;
import StochasticLanguage.StochasticLanguageFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GT Number</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTNumberTest extends TestCase {

	/**
	 * The fixture for this GT Number test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTNumber fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GTNumberTest.class);
	}

	/**
	 * Constructs a new GT Number test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTNumberTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this GT Number test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(GTNumber fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this GT Number test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GTNumber getFixture() {
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
		setFixture(StochasticLanguageFactory.eINSTANCE.createGTNumber());
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

} //GTNumberTest
