package testsuite1.familiestopersons.cc;

import java.io.IOException;

import org.junit.Test;

public class TestSimplePositive extends CCTestCase {
	
	public TestSimplePositive(boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testOneFatherToMale() throws IOException {
		createGenerator("singleFatherFamily", "singleMalePersonReg");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testMultipleRegToReg() throws IOException {
		createGenerator("multipleFamilyReg", "multiplePersonReg");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
	@Test
	public void testSixteenChildrenToPersons() throws IOException {
		createGenerator("sixteenChildFamily", "eightFemaleEightMalePersons");
		runGenerator();
		assert generator.modelsAreConsistent();
	}
	
}