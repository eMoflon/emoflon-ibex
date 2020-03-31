/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XDelete Pattern</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXDeletePatternTest extends IBeXPatternTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXDeletePatternTest.class);
	}

	/**
	 * Constructs a new IBe XDelete Pattern test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXDeletePatternTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this IBe XDelete Pattern test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IBeXDeletePattern getFixture() {
		return (IBeXDeletePattern) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBeXLanguageFactory.eINSTANCE.createIBeXDeletePattern());
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

} //IBeXDeletePatternTest
