package testsuite1.familiestopersons;

import java.io.IOException;

import org.junit.Test;

public class TestComplexFamilies extends ModelGenTestCase {
	
	@Test
	public void testFamilyWithSixChildren() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToMale", 1);
		stop.setMaxRuleCount("MotherToFemale", 1);
		stop.setMaxRuleCount("DaughterToFemale", 3);
		stop.setMaxRuleCount("SonToMale", 3);
		runGenerator(stop);
		assertPostcondition("sixChildFamily", "fourFemaleFourMalePersons");
	}

	@Test
	public void testFamilyWithSixteenChildren() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToMale", 1);
		stop.setMaxRuleCount("MotherToFemale", 1);
		stop.setMaxRuleCount("DaughterToFemale", 8);
		stop.setMaxRuleCount("SonToMale", 8);
		runGenerator(stop);
		assertPostcondition("sixteenChildFamily", "eightFemaleEightMalePersons");
	}

	@Test
	public void testFamilyWithSixtyChildren() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("FatherToMale", 1);
		stop.setMaxRuleCount("MotherToFemale", 1);
		stop.setMaxRuleCount("DaughterToFemale", 30);
		stop.setMaxRuleCount("SonToMale", 30);
		runGenerator(stop);
		assertPostcondition("sixtyChildFamily", "thirtyFemaleThirtyMalePersons");
	}

}
