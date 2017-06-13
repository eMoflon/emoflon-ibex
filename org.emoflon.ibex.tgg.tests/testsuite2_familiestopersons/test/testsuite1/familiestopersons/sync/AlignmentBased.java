package testsuite1.familiestopersons.sync;

import org.benchmarx.BXTool;
import org.junit.Ignore;
import org.junit.Test;

import SimpleFamilies.FamilyRegister;
import SimplePersons.PersonRegister;
import testsuite1.familiestopersons.util.Decisions;
import testsuite1.familiestopersons.util.SyncTestCase;


public class AlignmentBased extends SyncTestCase {

	public AlignmentBased(BXTool<FamilyRegister, PersonRegister, Decisions> tool) {
		super(tool);
	}
	
	@Ignore
	public void testRenameMother()
	{
		// No precondition!
		//------------
		tool.performAndPropagateTargetEdit(r -> helperPerson.createMotherAsFemale(r, "Lisa"));
		tool.performAndPropagateSourceEdit(r -> helperFamily.createDaughterInSingleTestFamily(r, "Maria"));
		tool.performAndPropagateSourceEdit(r -> helperFamily.createDaughterInSingleTestFamily(r, "Magdalena"));
		tool.performAndPropagateTargetEdit(r -> helperPerson.renamePerson(r, "Lisa", "Theresa"));
		
		//------------
		util.assertPostcondition("singleFamilyWithDaughters", "singlePersonRegisterWithFemalePersons");
	}
	
	
	@Test
	public void testDeleteDaughter()
	{
		tool.performAndPropagateTargetEdit(r -> helperPerson.createMotherAsFemale(r, "Theresa"));
		tool.performAndPropagateSourceEdit(r -> helperFamily.createDaughterInSingleTestFamily(r, "Maria"));
		tool.performAndPropagateSourceEdit(r -> helperFamily.createDaughterInSingleTestFamily(r, "Magdalena"));
		tool.performAndPropagateSourceEdit(r -> helperFamily.createDaughterInSingleTestFamily(r, "Lisa"));
				
		//------------
		tool.performAndPropagateTargetEdit(r -> helperPerson.deletePerson(r, "Lisa"));
		//------------

		util.assertPrecondition("singleFamilyWithDaughters", "singlePersonRegisterWithFemalePersons");
	}
}
