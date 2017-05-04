package testsuite1.familiestopersons;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.benchmarx.emf.Comparator;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.persons.core.PersonsComparator;
import org.benchmarx.util.EMFUtil;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
import org.emoflon.ibex.tgg.run.MODELGEN_App;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Families.FamilyRegister;
import Persons.PersonRegister;

public class TestAxiom {
	private MODELGEN_App generator;
	private Comparator<FamilyRegister> familiesComp;
	private Comparator<PersonRegister> personsComp;
	
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}

	@Before
	public void createGenerator() throws IOException {
		generator = new MODELGEN_App("testsuite1.familiestopersons", "./../", false);
		familiesComp = new FamiliesComparator();
		personsComp = new PersonsComparator();
	}

	@Test
	public void testOneRoot() throws IOException {
		MODELGENStopCriterion stop = new MODELGENStopCriterion(generator.getTGG());
		stop.setMaxRuleCount("HandleRegisters", 1);
		generator.setStopCriterion(stop); 
		generator.run();
		generator.terminate();

		Resource srcExp = EMFUtil.loadExpectedResource("singleFamilyReg", generator.getResourceSet());
		Resource trgExp = EMFUtil.loadExpectedResource("singlePersonReg", generator.getResourceSet());
		familiesComp.assertEquals((FamilyRegister)srcExp.getContents().get(0), (FamilyRegister)generator.getSourceResource().getContents().get(0));
		personsComp.assertEquals((PersonRegister)trgExp.getContents().get(0), (PersonRegister)generator.getTargetResource().getContents().get(0));
	}

}
