package testsuite1.familiestopersons;

import java.io.IOException;

import org.junit.Test;

public class TestMultiplicities extends ModelGenTestCase {

	@Test
	public void testZeroToOneMultiplicityWithOneFather() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToMale", 1);
		runGenerator(stop);
		assertPostcondition("singleFatherFamily", "singleMalePersonReg");
	}
	
	@Test
	public void testZeroToOneMultiplicityWithManyFathers() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("FatherToMale", 10);
		runGenerator(stop);
		assertPostcondition("singleFatherFamilies", "multipleMalePersonReg");
	}

	@Test
	public void testZeroToOneMultiplicityWithOneFatherWithoutPersons() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToNothing", 1);
		runGenerator(stop);
		assertPostcondition("singleFatherFamily", "singlePersonReg");
	}

	@Test
	public void testZeroToOneMultiplicityWithManyFathersWithoutPersons() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("FatherToNothing", 10);
		runGenerator(stop);
		assertPostcondition("singleFatherFamilies", "singlePersonReg");
	}

	@Test
	public void testZeroToNMultiplicityWithManyFamilies() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 8);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndFiveFamilies", "singlePersonReg");
	}

	@Test
	public void testZeroToNMultiplicityWithFewFamilies() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		runGenerator(stop);
		assertPostcondition("singleFamilyRegAndTwoFamilies", "singlePersonReg");
	}

}
