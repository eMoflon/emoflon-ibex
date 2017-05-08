package testsuite1.familiestopersons;

import java.io.IOException;

import org.junit.Test;

public class TestAxiom extends ModelGenTestCase {
	
	@Test
	public void testOneRoot() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		runGenerator(stop);
		assertPostcondition("singleFamilyReg", "singlePersonReg");
	}

	@Test
	public void testOneRootAndTwoFamilies() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndTwoFamilies", "singlePersonReg");
	}
	
	@Test
	public void testMultipleRoots() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 3);
		runGenerator(stop);
		assertPostcondition("multipleFamilyReg", "multiplePersonReg");
	}
}
