package testsuite1.familiestopersons.modelgen;

import java.io.IOException;

import org.junit.Test;

public class TestZeroToNMultiplicities extends ModelGenTestCase {
	
	/**
	 * Tests if the generated NAC for a 0..n multiplicity does not prohibit
	 * the application of the IgnoreFamily rule unnecessarily.
	 * @throws IOException
	 */
	@Test
	public void testZeroToNMultiplicityWithFewFamilies() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndTwoFamilies", "singlePersonReg");
	}

	/**
	 * Tests if the generated NAC for a 0..n multiplicity prohibits
	 * the application of the IgnoreFamily rule when necessary.
	 * @throws IOException
	 */
	@Test
	public void testZeroToNMultiplicityWithManyFamilies() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 8);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndFiveFamilies", "singlePersonReg");
	}

	/**
	 * Tests the generation of multiplicity NACs for an edge
	 * if there are create edges with the same type and source
	 * as that edge.
	 * @throws IOException
	 */
	@Test
	public void testZeroToNMultiplicityMultipleCreateEdges() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreTwoFamilies", 10);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndFourFamilies", "singlePersonReg");
	}

	/**
	 * Tests the generation of multiplicity NACs for an edge
	 * if there are context edges with the same type and source
	 * as that edge.
	 * @throws IOException
	 */
	@Test
	public void testZeroToNMultiplicityMultipleContextEdges() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 3);
		stop.setMaxRuleCount("CreateFourthFamily", 5);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndFiveFamilies", "singlePersonReg");
	}

	/**
	 * Tests the generation of multiplicity NACs for an edge
	 * if there are create and context edges with the same type and source
	 * as that edge.
	 * @throws IOException
	 */
	@Test
	public void testZeroToNMultiplicityMultipleCreateAndContextEdges() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 3);
		stop.setMaxRuleCount("CreateFourthAndFifthFamily", 2);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndFiveFamilies", "singlePersonReg");
	}

}
