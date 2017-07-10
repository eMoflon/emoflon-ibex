package testsuite1.familiestopersons.modelgen;

import java.io.IOException;

import org.junit.Test;

public class TestContextFromRefinement extends ModelGenTestCase {
	
	public TestContextFromRefinement(boolean flatten) {
		super(flatten);
	}

	/**
	 * Tests if a corr-context node from a super-rule that is missing in the match
	 * correctly prevents application of the rule.
	 * @throws IOException
	 */
	@Test
	public void testCorrContextFromRefinement() throws IOException {
		stop.setMaxRuleCount("HandleRegistersLoose", 1);
		stop.setMaxRuleCount("NewFamilyDaughterToFemale", 1);
		runGenerator(stop);
		assertPostcondition("singleFamilyReg", "singlePersonReg");
	}

	/**
	 * Tests if a trg-context node from a super-rule that is missing in the match
	 * correctly prevents application of the rule.
	 * @throws IOException
	 */
	@Test
	public void testTrgContextFromRefinement() throws IOException {
		stop.setMaxRuleCount("HandleFamilyReg", 1);
		stop.setMaxRuleCount("IgnoreFamilyLoose", 1);
		stop.setMaxRuleCount("FatherToNothing", 1);
		runGenerator(stop);
		assertPostconditionOnSrc("familyRegWithFamily");
	}

	/**
	 * Checks if green elements that overwrite black elements
	 * erroneously remove black elements from pattern matcher.
	 * @throws IOException
	 */
	@Test
	public void testContextWithSuperfluousGreenElements() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("SonToMaleWithSuperfluousGreenElements", 1);
		runGenerator(stop);
		assertPostcondition("singleFamilyReg", "singlePersonReg");
	}


	/**
	 * Checks if green elements that overwrite black elements are erroneously created when rule is applied.
	 * @throws IOException
	 */
	@Test
	public void testCreationOfSuperfluousGreenElements() throws IOException {
		stop.setMaxRuleCount("HandleRegisters", 1);
		stop.setMaxRuleCount("IgnoreFamily", 1);
		stop.setMaxRuleCount("CreateFather", 1);
		stop.setMaxRuleCount("ConnectFatherWithSuperfluousGreenElements", 1);
		runGenerator(stop);
		assertPostcondition("singleFatherFamily", "singleMalePersonReg");
	}
}
