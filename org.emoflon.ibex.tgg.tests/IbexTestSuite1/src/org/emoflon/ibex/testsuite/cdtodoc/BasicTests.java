package org.emoflon.ibex.testsuite.cdtodoc;

import org.benchmarx.BXTool;
import org.junit.Test;

import CD.CDFactory;
import CD.CDPackage;
import CD.Clazz;
import Doc.DocFactory;
import Doc.File;
import Doc.Folder;
import Families.FamiliesFactory;
import Families.FamiliesPackage;
import Families.Family;
import Families.FamilyMember;
import Families.FamilyRegister;
import Persons.Male;
import Persons.PersonRegister;
import Persons.PersonsFactory;

public class BasicTests extends CDToDocTestCase {
	
	public BasicTests(BXTool<CD.Package, Folder, NoDecisions> tool) {
		super(tool);
	}

	@Test
	public void testInitialiseSynchronisation()
	{
		// No precondition!
		//------------
		tool.initiateSynchronisationDialogue();
		//------------
		util.assertPostcondition("EmptyPackage", "EmptyFolder");
	}
	
	@Test
	public void testAddSubpackageToSubfolderFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createSubPackage("sub", 2, p)); 
		
		util.assertPostcondition("SubPackage", "SubFolder");
	}
	
	@Test
	public void testAddSubpackageToSubfolderBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(f -> helperDoc.createSubFolder("sub", f)); 
		
		util.assertPostcondition("SubPackage", "SubFolder");
	}
	
	@Test
	public void testAddClazzToFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createClazz("c1", p)); 
		
		util.assertPostcondition("SingleClazz", "SingleFile");
	}
	
	@Test
	public void testAddComplexBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(folder -> helperDoc.createComplexExample(folder)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
	}
	
	@Test 
	public void testAddComplexFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(p -> helperDoc.createComplexExample(p)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
	}
	
	@Test
	public void testDeleteSubpackageToSubfolderFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createSubPackage("sub", 2, p)); 
		
		util.assertPostcondition("SubPackage", "SubFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.deleteElement(p, "sub"));
		
		util.assertPostcondition("EmptyPackage", "EmptyFolder");
	}
	
	@Test
	public void testDeleteSubpackageToSubfolderBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(f -> helperDoc.createSubFolder("sub", f)); 
		
		util.assertPostcondition("SubPackage", "SubFolder");
	
		tool.performAndPropagateTargetEdit(p -> helperDoc.deleteElement(p, "sub"));
		
		util.assertPostcondition("EmptyPackage", "EmptyFolder");
	}
	
	@Test
	public void testDeleteClazzToFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createClazz("c1", p)); 
		
		util.assertPostcondition("SingleClazz", "SingleFile");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.deleteElement(p, "c1"));
		
		util.assertPostcondition("EmptyPackage", "EmptyFolder");
	}
	
	@Test
	public void testDeleteClazzToFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(f -> helperDoc.createFile("c1", f)); 
		
		util.assertPostcondition("SingleClazz", "SingleFile");
	
		tool.performAndPropagateTargetEdit(p -> helperDoc.deleteElement(p, "c1"));
		
		util.assertPostcondition("EmptyPackage", "EmptyFolder");
	}
	
	@Test
	public void testDeleteClazzToFileComplexFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createComplexExample(p)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.deleteElement(p, "rootClazz"));
		
		util.assertPostcondition("ComplexCD-RootClazz", "ComplexDoc-RootClazz");
	}
	
	@Test
	public void testDeleteClazzToFileComplexBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(f -> helperDoc.createComplexExample(f)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
	
		tool.performAndPropagateTargetEdit(p -> helperDoc.deleteElement(p, "rootClazz"));
		
		util.assertPostcondition("ComplexCD-RootClazz", "ComplexDoc-RootClazz");
	}
	
	@Test
	public void testDeleteSubPackageToSubFolderComplexFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createComplexExample(p)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.deleteElement(p, "sub1"));
		
		util.assertPostcondition("ComplexCD-SubPackage", "ComplexDoc-SubFolder");
	}
	
	@Test
	public void testDeleteSubPackageToSubFolderComplexBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(f -> helperDoc.createComplexExample(f)); 
		
		util.assertPostcondition("ComplexCD", "ComplexDoc");
	
		tool.performAndPropagateTargetEdit(p -> helperDoc.deleteElement(p, "sub1"));
		
		util.assertPostcondition("ComplexCD-SubPackage", "ComplexDoc-SubFolder");
	}
}
