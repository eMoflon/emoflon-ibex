/**
 */
package StochasticLanguage.tests;

import StochasticLanguage.GTAttribute;
import StochasticLanguage.StochasticLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GT Attribute</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTAttributeTest extends GTStochasticNodeTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GTAttributeTest.class);
	}

	/**
	 * Constructs a new GT Attribute test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTAttributeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this GT Attribute test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GTAttribute getFixture() {
		return (GTAttribute) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(StochasticLanguageFactory.eINSTANCE.createGTAttribute());
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

} //GTAttributeTest
