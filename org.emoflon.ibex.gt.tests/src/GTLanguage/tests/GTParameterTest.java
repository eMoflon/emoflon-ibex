/**
 */
package GTLanguage.tests;

import GTLanguage.GTLanguageFactory;
import GTLanguage.GTParameter;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GT Parameter</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTParameterTest extends GTNamedElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GTParameterTest.class);
	}

	/**
	 * Constructs a new GT Parameter test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTParameterTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this GT Parameter test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GTParameter getFixture() {
		return (GTParameter) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GTLanguageFactory.eINSTANCE.createGTParameter());
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

} //GTParameterTest
