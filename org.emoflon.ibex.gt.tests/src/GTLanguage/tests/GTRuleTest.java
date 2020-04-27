/**
 */
package GTLanguage.tests;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTRule;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GT Rule</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTRuleTest extends GTNamedElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GTRuleTest.class);
	}

	/**
	 * Constructs a new GT Rule test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTRuleTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this GT Rule test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GTRule getFixture() {
		return (GTRule) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GTLanguageFactory.eINSTANCE.createGTRule());
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

} //GTRuleTest
