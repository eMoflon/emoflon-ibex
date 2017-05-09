package testsuite1.familiestopersons;

import java.io.IOException;

import org.junit.Test;

public class TestZeroToOneMultiplicities extends ModelGenTestCase {
	
	/**
	 * Tests if the generated NAC for a 0..1 multiplicity does not prohibit
	 * the application of the FatherToMale rule unnecessarily.
	 * @throws IOException
	 */
	@Test
	public void testZeroToOneMultiplicityWithOneFather() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToMale", 1);
		runGenerator(stop);
		assertPostcondition("singleFatherFamily", "singleMalePersonReg");
	}
	
	/**
	 * Tests if the generated NAC for a 0..1 multiplicity prohibits
	 * the application of the FatherToMale rule when necessary.
	 * @throws IOException
	 */
	@Test
	public void testZeroToOneMultiplicityWithManyFathers() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("FatherToMale", 10);
		runGenerator(stop);
		assertPostcondition("singleFatherFamilies", "multipleMalePersonReg");
	}

	/**
	 * Tests if the generated NAC for a 0..1 multiplicity does not prohibit
	 * the application of the FatherToMale rule unnecessarily.
	 * Does not create Persons in the target model.
	 * @throws IOException
	 */
	@Test
	public void testZeroToOneMultiplicityWithOneFatherWithoutPersons() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToNothing", 1);
		runGenerator(stop);
		assertPostcondition("singleFatherFamily", "singlePersonReg");
	}

	/**
	 * Tests if the generated NAC for a 0..1 multiplicity prohibits
	 * the application of the FatherToMale rule when necessary.
	 * Does not create Persons in the target model.
	 * @throws IOException
	 */
	@Test
	public void testZeroToOneMultiplicityWithManyFathersWithoutPersons() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("FatherToNothing", 10);
		runGenerator(stop);
		assertPostcondition("singleFatherFamilies", "singlePersonReg");
	}

	/**
	 * Tests the generation of NACs for a 0..1 multiplicity when target node of
	 * the edge is a context node.
	 * @throws IOException
	 */
	@Test
	public void testZeroToOneMultiplicityWithContextTargetNode() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("CreateFather", 5);
		stop.setMaxRuleCount("ConnectFather", 5);
		runGenerator(stop);
		assertPostconditionOnTrg("multipleMalePersonReg");
	}

}
