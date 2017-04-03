package org.emoflon.ibex.testsuite.familiestopersonsdeterministic;

import org.benchmarx.BXTool;
import org.junit.Test;

import Families.FamiliesFactory;
import Families.FamiliesPackage;
import Families.Family;
import Families.FamilyMember;
import Families.FamilyRegister;
import Persons.Male;
import Persons.PersonRegister;
import Persons.PersonsFactory;

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
	
	@Test
	public void testFatherToMaleFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyFamilyRegister", "EmptyPersonRegister");
		
		Family vader = FamiliesFactory.eINSTANCE.createFamily();
		vader.setName("Vader");
		
		FamilyMember darth = FamiliesFactory.eINSTANCE.createFamilyMember();
		darth.setName("Darth");
		vader.setFather(darth);
		
		tool.performAndPropagateSourceEdit(f -> f.getFamilies().add(vader)); 
		
		util.assertPostcondition("SingleFather", "SingleMale");
	}
	
	@Test
	public void testFatherToMaleBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyFamilyRegister", "EmptyPersonRegister");
		
		Male vader = PersonsFactory.eINSTANCE.createMale();
		vader.setName("Darth Vader");
		
		tool.performAndPropagateTargetEdit(f -> f.getPersons().add(vader)); 
		
		util.assertPostcondition("SingleFather", "SingleMale");
	}
	
//	@Test
//	public void testMotherToFemaleFWD() {
//		tool.initiateSynchronisationDialogue();
//		
//		util.assertPrecondition("EmptyFamilyRegister", "EmptyPersonRegister");
//		
//		Family vader = FamiliesFactory.eINSTANCE.createFamily();
//		vader.setName("Vader");
//		
//		FamilyMember darth = FamiliesFactory.eINSTANCE.createFamilyMember();
//		darth.setName("Darth");
//		vader.setFather(darth);
//		
//		tool.performAndPropagateSourceEdit(f -> f.getFamilies().add(vader)); 
//		
//		util.assertPostcondition("SingleMother", "SingleMother");
//	}
//	
//	@Test
//	public void testMotherToFemaleBWD() {
//		tool.initiateSynchronisationDialogue();
//		
//		util.assertPrecondition("EmptyFamilyRegister", "EmptyPersonRegister");
//		
//		Male vader = PersonsFactory.eINSTANCE.createMale();
//		vader.setName("Darth Vader");
//		
//		tool.performAndPropagateTargetEdit(f -> f.getPersons().add(vader)); 
//		
//		util.assertPostcondition("SingleMother", "SingleMother");
//	}
}
