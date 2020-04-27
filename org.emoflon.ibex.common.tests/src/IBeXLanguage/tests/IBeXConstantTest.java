/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XConstant</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXConstantTest extends IBeXAttributeValueTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXConstantTest.class);
	}

	/**
	 * Constructs a new IBe XConstant test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXConstantTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this IBe XConstant test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IBeXConstant getFixture() {
		return (IBeXConstant) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBeXLanguageFactory.eINSTANCE.createIBeXConstant());
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

} //IBeXConstantTest
