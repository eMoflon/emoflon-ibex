package testsuite1.familiestopersons;

import java.io.IOException;

import org.junit.Test;

public class TestNoCorrespondence extends ModelGenTestCase {

	@Test
	public void testFathersAndPersonsWithoutCorr() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 2);
		stop.setMaxRuleCount("FatherAndMale", 2);
		runGenerator(stop);
		assertPostcondition("singleFatherFamilies", "multipleMalePersonReg");
	}

}
