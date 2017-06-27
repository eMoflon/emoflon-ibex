package testsuite1.ClassInhHier2DB.cc;

import java.io.IOException;

import org.junit.Test;

public class TestSimplePositive extends CCTestCase {
	
	public TestSimplePositive(boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testPackageToDatabase() throws IOException {
		createGenerator("in/PackageToDatabase_FWD", "expected/PackageToDatabase_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testClassToTable() throws IOException {
		createGenerator("in/ClassToTable_FWD", "expected/ClassToTable_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testAttributeToColumn() throws IOException {
		createGenerator("in/AttributeToColumn_FWD", "expected/AttributeToColumn_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testAttributeToColumn2() throws IOException {
		createGenerator("in/AttributeToColumn2_FWD", "expected/AttributeToColumn2_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testSubClassToTable() throws IOException {
		createGenerator("in/SubClassToTable_FWD", "expected/SubClassToTable_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testLargeExample() throws IOException {
		createGenerator("in/LargeExample_FWD", "expected/LargeExample_FWD");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
}