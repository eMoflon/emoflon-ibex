package org.emoflon.ibex.testsuite.familiestopersonsdeterministic;

import org.benchmarx.BXTool;
import org.junit.Test;

import Families.FamilyRegister;
import Persons.PersonRegister;

public class BasicTests extends FamiliesToPersonsDeterministicTestCase {
	
	public BasicTests(BXTool<FamilyRegister, PersonRegister, NoDecisions> tool) {
		super(tool);
	}

	@Test
	public void testInitialiseSynchronisation()
	{
		// No precondition!
		//------------
		tool.initiateSynchronisationDialogue();
		//------------
		util.assertPostcondition("EmptyFamilyRegister", "EmptyPersonRegister");
	}
}
