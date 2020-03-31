/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXAttributeExpression;
import IBeXLanguage.IBeXLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XAttribute Expression</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXAttributeExpressionTest extends IBeXAttributeValueTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXAttributeExpressionTest.class);
	}

	/**
	 * Constructs a new IBe XAttribute Expression test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXAttributeExpressionTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this IBe XAttribute Expression test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IBeXAttributeExpression getFixture() {
		return (IBeXAttributeExpression) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBeXLanguageFactory.eINSTANCE.createIBeXAttributeExpression());
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

} //IBeXAttributeExpressionTest
