package testsuite1.ClassInhHier2DB.cc;

import java.io.IOException;

import org.junit.Test;

public class TestSimpleNegative extends CCTestCase {
	
	public TestSimpleNegative(boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testPackageToTable() throws IOException {
		createGenerator("in/PackageToDatabase_FWD", "expected/ClassToTable_FWD");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testClassToColumn() throws IOException {
		createGenerator("in/ClassToTable_FWD", "expected/AttributeToColumn_FWD");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testAttributeToTable() throws IOException {
		createGenerator("in/AttributeToColumn_FWD", "expected/SubClassToTable_FWD");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testLargeExample() throws IOException {
		createGenerator("in/LargeExample_FWD", "expected/AttributeToColumn2_FWD");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
}