/**
 */
package StochasticLanguage.tests;

import StochasticLanguage.GTStochasticNode;
import StochasticLanguage.StochasticLanguageFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>GT Stochastic Node</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class GTStochasticNodeTest extends GTStochasticNamedElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(GTStochasticNodeTest.class);
	}

	/**
	 * Constructs a new GT Stochastic Node test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GTStochasticNodeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this GT Stochastic Node test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected GTStochasticNode getFixture() {
		return (GTStochasticNode) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(StochasticLanguageFactory.eINSTANCE.createGTStochasticNode());
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

} //GTStochasticNodeTest
