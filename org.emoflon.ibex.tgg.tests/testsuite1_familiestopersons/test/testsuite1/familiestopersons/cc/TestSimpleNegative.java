package testsuite1.familiestopersons.cc;

import java.io.IOException;

import org.junit.Test;

public class TestSimpleNegative extends CCTestCase {
	
	public TestSimpleNegative(boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testOneFatherToFemale() throws IOException {
		createGenerator("singleFatherFamily", "singleFemalePersonReg");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testSingleRegToMultipleRegs() throws IOException {
		createGenerator("singleFamilyReg", "multiplePersonReg");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testSixteenChildrenToSixtyPersons() throws IOException {
		createGenerator("sixteenChildFamily", "thirtyFemaleThirtyMalePersons");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
	@Test
	public void testDifferentNames() throws IOException {
		createGenerator("singleFatherFamily", "singleMalePersonCalledSteve");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}
	
}