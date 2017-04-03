package org.emoflon.ibex.testsuite.cdtodoc.amalgamation;

import java.util.Arrays;
import java.util.Collection;

import org.benchmarx.BXTool;
import org.benchmarx.BenchmarxUtil;
import org.benchmarx.Comparator;
import org.benchmarx.cd.core.CDComparator;
import org.benchmarx.cd.core.CDHelper;
import org.benchmarx.doc.core.DocComparator;
import org.benchmarx.doc.core.DocHelper;
import org.benchmarx.families.core.FamilyHelper;
import org.benchmarx.persons.core.PersonHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Doc.Folder;

@RunWith(Parameterized.class)
public abstract class CDToDocTestCase {

	protected BXTool<CD.Package, Folder, NoDecisions> tool;
	protected Comparator<CD.Package> cdComparator;
	protected Comparator<Folder> docComparator;
	protected BenchmarxUtil<CD.Package, Folder, NoDecisions> util;
	protected CDHelper helperCD;
	protected DocHelper helperDoc;

	@Before
	public void initialise() {
		// Initialise all helpers
		cdComparator = new CDComparator();
		docComparator = new DocComparator();
		util = new BenchmarxUtil<>(tool);
		util.setFolder("CDToDoc_Amalgamation");

		helperCD = new CDHelper();
		helperDoc = new DocHelper();
	}
	
	@After
	public void finalize() {
		tool.disposeSynchronisationDialogue();
	}

	@Parameters
	public static Collection<BXTool<CD.Package, Folder, NoDecisions>> tools() {
		return Arrays.asList(
				new IbexTestRunner()
			);
	}
	
	protected CDToDocTestCase(BXTool<CD.Package, Folder, NoDecisions> tool) {
		this.tool = tool; 
	}
}
