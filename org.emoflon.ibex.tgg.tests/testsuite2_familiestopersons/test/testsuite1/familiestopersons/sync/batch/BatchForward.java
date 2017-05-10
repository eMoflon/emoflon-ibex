package testsuite1.familiestopersons.sync.batch;

import org.benchmarx.BXTool;
import org.junit.Test;

import SimpleFamilies.FamilyRegister;
import SimplePersons.PersonRegister;
import testsuite1.familiestopersons.sync.Decisions;
import testsuite1.familiestopersons.sync.SyncTestCase;


public class BatchForward extends SyncTestCase {

	public BatchForward(BXTool<FamilyRegister, PersonRegister, Decisions> tool) {
		super(tool);
	}
	
	/**
	 * <b>Test</b> for agreed upon starting state.<br/>
	 * <b>Expect</b> root elements of both source and target models.<br/>
	 * <b>Features</b>: fwd
	 */
	@Test
	public void testInitialiseSynchronisation()
	{
		// No precondition!
		//------------
		util.assertPostcondition("singleFamilyReg", "singlePersonReg");
	}
	
	/**
	 * <b>Features</b>: fwd
	 */
	@Test
	public void testMotherToFemale()
	{
		// No precondition!
		//------------
		tool.performAndPropagateSourceEdit(util.execute(helperFamily::createTestFamily)
											   .andThen(helperFamily::createMotherInSingleTestFamily));
		//------------
		util.assertPostcondition("singleFamilyWithMother", "singlePersonRegisterWithFemalePerson");
	}
}
