/**
 */
package IBeXLanguage.tests;

import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXLanguagePackage;
import IBeXLanguage.IBeXNode;

import java.util.Map;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>IBe XNode To Node Mapping</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class IBeXNodeToNodeMappingTest extends TestCase {

	/**
	 * The fixture for this IBe XNode To Node Mapping test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Map.Entry<IBeXNode, IBeXNode> fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(IBeXNodeToNodeMappingTest.class);
	}

	/**
	 * Constructs a new IBe XNode To Node Mapping test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBeXNodeToNodeMappingTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this IBe XNode To Node Mapping test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Map.Entry<IBeXNode, IBeXNode> fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this IBe XNode To Node Mapping test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Map.Entry<IBeXNode, IBeXNode> getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		setFixture((Map.Entry<IBeXNode, IBeXNode>) IBeXLanguageFactory.eINSTANCE
				.create(IBeXLanguagePackage.Literals.IBE_XNODE_TO_NODE_MAPPING));
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

} //IBeXNodeToNodeMappingTest
