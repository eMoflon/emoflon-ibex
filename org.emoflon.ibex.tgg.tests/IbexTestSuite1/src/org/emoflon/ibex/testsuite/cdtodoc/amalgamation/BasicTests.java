package org.emoflon.ibex.testsuite.cdtodoc.amalgamation;

import org.benchmarx.BXTool;
import org.benchmarx.BXToolForEMF;
import org.junit.Test;

import CD.CDFactory;
import CD.CDPackage;
import CD.Clazz;
import Doc.DocFactory;
import Doc.DocFile;
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
	public void testAddClazzToDocFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createClazz("super", p, true)); 
		
		util.assertPostcondition("SingleClazz", "SingleDoc");
	}
	
	@Test
	public void testAddClazzToDocFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateTargetEdit(folder -> helperDoc.createDocFile("super", folder)); 
		
		util.assertPostcondition("SingleClazz", "SingleDoc");
	}
	
	@Test
	public void testAddSubClazzToSubDocFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		tool.performAndPropagateSourceEdit(p -> helperCD.createSubClazz("sub", p, helperCD.createClazz("super", p, true))); 
		
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");
	}

	@Test 
	public void testAddSubClazzToSubDocFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		Folder root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getTargetModel();
		DocFile superDoc = helperDoc.createDocFile("super", root);
		DocFile sub = helperDoc.createSubDoc("sub", root, superDoc);
		helperDoc.createAllSuperLinks(root);
		
		tool.performAndPropagateTargetEdit(p -> {}); 
		
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");
	}
	
	@Test
	public void testAddTransitiveSubClazzToSubDocFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		CD.Package root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getSourceModel();
		Clazz superClazz = helperCD.createClazz("super", root, true);
		Clazz subClazz = helperCD.createSubClazz("sub", root, superClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");
			
		Clazz subsubClazz = helperCD.createSubClazz("subsub", root, subClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("TransitiveSubClazz", "TransitiveSubDoc");
	}

	@Test 
	public void testAddTransitiveSubClazzToSubDocFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		Folder root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getTargetModel();
		DocFile superDoc = helperDoc.createDocFile("super", root);
		DocFile subDoc = helperDoc.createSubDoc("sub", root, superDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");

		DocFile subsubDoc = helperDoc.createSubDoc("subsub", root, subDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("TransitiveSubClazz", "TransitiveSubDoc");
	}
	
	@Test
	public void testAddBackwardSubClazzToSubDocFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		CD.Package root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getSourceModel();
		Clazz superClazz = helperCD.createClazz("super", root, true);
		Clazz subClazz = helperCD.createSubClazz("sub", root, superClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");
			
		Clazz supsupClazz = helperCD.createSuperClazz("supsup", root, superClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("BackwardSubClazz", "BackwardSubDoc");
	}

	@Test 
	public void testAddBackwardSubClazzToSubDocFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		Folder root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getTargetModel();
		DocFile superDoc = helperDoc.createDocFile("super", root);
		DocFile subDoc = helperDoc.createSubDoc("sub", root, superDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("SingleSubClazz", "SingleSubDoc");

		DocFile supsupDoc = helperDoc.createSuperDoc("supsup", root, superDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("BackwardSubClazz", "BackwardSubDoc");
	}
	
	@Test
	public void testAddTransitiveBackwardSubClazzToSubDocFileFWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		CD.Package root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getSourceModel();
		Clazz supsupClazz = helperCD.createClazz("supsup", root, true);
		Clazz superClazz = helperCD.createSubClazz("super", root, supsupClazz);
		Clazz subClazz = helperCD.createClazz("sub", root, true);
		Clazz subSubClazz = helperCD.createSubClazz("subsub", root, subClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("2x2SubClazzes", "2x2SubDocs");
			
		subClazz.getSuperClazz().add(superClazz);
		tool.performAndPropagateSourceEdit(p -> {});
		util.assertPostcondition("TransitiveBackwardSubClazz", "TransitiveBackwardSubDoc");
	}

	@Test 
	public void testAddTransitiveBackwardSubClazzToSubDocFileBWD() {
		tool.initiateSynchronisationDialogue();
		
		util.assertPrecondition("EmptyPackage", "EmptyFolder");
		
		Folder root = ((BXToolForEMF<CD.Package, Folder, NoDecisions>) tool).getTargetModel();
		DocFile supsupDoc = helperDoc.createDocFile("supsup", root);
		DocFile superDoc = helperDoc.createSubDoc("super", root, supsupDoc);
		DocFile subDoc = helperDoc.createDocFile("sub", root);
		DocFile subsubDoc = helperDoc.createSubDoc("subsub", root, subDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("2x2SubClazzes", "2x2SubDocs");

		subDoc.setSuperRef(superDoc);
		helperDoc.createAllSuperLinks(root);
		tool.performAndPropagateTargetEdit(p -> {}); 
		util.assertPostcondition("TransitiveBackwardSubClazz", "TransitiveBackwardSubDoc");
	}
}
