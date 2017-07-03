package testsuite1.familiestopersons.cc;

import java.io.IOException;

import org.junit.Test;

public class TestRefinementNegative extends CCTestCase {
	
	public TestRefinementNegative(boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testPersonsToDifferentRegisters() throws IOException {
		createGenerator("oneSonRegOneDaughterReg", "twoPersonRegZeroPersonReg");
		runGenerator();
		assert !generator.modelsAreConsistent();
	}

}
