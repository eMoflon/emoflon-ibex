package org.emoflon.ibex.testsuite.familiestopersonsdeterministic;

import java.util.Arrays;
import java.util.Collection;

import org.benchmarx.BXTool;
import org.benchmarx.BenchmarxUtil;
import org.benchmarx.Comparator;
import org.benchmarx.families.core.FamilyComparator;
import org.benchmarx.families.core.FamilyHelper;
import org.benchmarx.persons.core.PersonComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Families.FamilyRegister;
import Persons.PersonRegister;

@RunWith(Parameterized.class)
public abstract class FamiliesToPersonsDeterministicTestCase {

	protected BXTool<FamilyRegister, PersonRegister, NoDecisions> tool;
	protected Comparator<FamilyRegister> familiesComparator;
	protected Comparator<PersonRegister> personsComparator;
	protected BenchmarxUtil<FamilyRegister, PersonRegister, NoDecisions> util;
	protected FamilyHelper helperFamily;

	@Before
	public void initialise() {
		// Initialise all helpers
		familiesComparator = new FamilyComparator();
		personsComparator = new PersonComparator();
		util = new BenchmarxUtil<>(tool);
		helperFamily = new FamilyHelper();
		util.setFolder("FamiliesToPersonsDeterministic");
	}
	
	@After
	public void finalize() {
		tool.disposeSynchronisationDialogue();
	}

	@Parameters
	public static Collection<BXTool<FamilyRegister, PersonRegister, NoDecisions>> tools() {
		return Arrays.asList(
				new IbexTestRunner()
			);
	}
	
	protected FamiliesToPersonsDeterministicTestCase(BXTool<FamilyRegister, PersonRegister, NoDecisions> tool) {
		this.tool = tool; 
	}
}
