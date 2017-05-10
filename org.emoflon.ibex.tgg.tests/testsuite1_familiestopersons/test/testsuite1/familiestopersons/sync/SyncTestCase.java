package testsuite1.familiestopersons.sync;

import java.util.Arrays;
import java.util.Collection;

import org.benchmarx.BXTool;
import org.benchmarx.emf.Comparator;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.families.core.SimpleFamiliesHelper;
import org.benchmarx.persons.core.PersonsComparator;
import org.benchmarx.persons.core.SimplePersonsHelper;
import org.benchmarx.util.BenchmarxUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import SimpleFamilies.FamilyRegister;
import SimpleFamilies.SimpleFamiliesPackage;
import SimplePersons.PersonRegister;
import SimplePersons.SimplePersonsPackage;

@RunWith(Parameterized.class)
public abstract class SyncTestCase {

	protected BXTool<FamilyRegister, PersonRegister, Decisions> tool;
	protected Comparator<FamilyRegister> familiesComparator;
	protected Comparator<PersonRegister> personsComparator;
	protected BenchmarxUtil<FamilyRegister, PersonRegister, Decisions> util;
	protected SimpleFamiliesHelper helperFamily;
	protected SimplePersonsHelper helperPerson;
	
	@Before
	public void initialise() {
		// Make sure packages are registered
		SimpleFamiliesPackage.eINSTANCE.getName();
		SimplePersonsPackage.eINSTANCE.getName();
		
		// Initialise all helpers
		familiesComparator = new FamiliesComparator();
		personsComparator = new PersonsComparator();
		util = new BenchmarxUtil<>(tool);
		helperFamily = new SimpleFamiliesHelper();
		helperPerson = new SimplePersonsHelper();
		
		// Initialise the bx tool
		tool.initiateSynchronisationDialogue();
	}

	@After
	public void terminate(){
		tool.terminateSynchronisationDialogue();
	}
	
	@Parameters
	public static Collection<BXTool<FamilyRegister, PersonRegister, Decisions>> tools() {
		return Arrays.asList(
				new EMoflonSimpleFamiliesToPersons()
			);
	}
	
	protected SyncTestCase(BXTool<FamilyRegister, PersonRegister, Decisions> tool) {
		this.tool = tool; 
	}
}
