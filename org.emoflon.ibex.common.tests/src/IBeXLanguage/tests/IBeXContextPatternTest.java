/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XContext Pattern</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXContextPatternTest extends IBeXContextTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXContextPatternTest.class);
	}

	/**
	 * Constructs a new IBe XContext Pattern test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXContextPatternTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this IBe XContext Pattern test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected IBeXContextPattern getFixture() {
		return (IBeXContextPattern) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern());
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

} //IBeXContextPatternTest
