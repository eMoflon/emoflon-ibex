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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import Families.FamilyRegister;
import Persons.PersonRegister;

public class ModelGenTestCase {
	protected MODELGEN_App generator;
	protected Comparator<FamilyRegister> familiesComp;
	protected Comparator<PersonRegister> personsComp;
	protected MODELGENStopCriterion stop;
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void createGenerator() throws IOException {
		generator = new MODELGEN_App("testsuite1_familiestopersons", "./../", false);
		stop = new MODELGENStopCriterion(generator.getTGG());
		familiesComp = new FamiliesComparator();
		personsComp = new PersonsComparator();
	}

	protected void runGenerator(MODELGENStopCriterion stop) throws IOException {
		generator.setStopCriterion(stop); 
		generator.run();
		generator.terminate();
	}

	protected void assertPostcondition(String src, String trg) {
		Resource srcExp = EMFUtil.loadExpectedResource(src, generator.getResourceSet());
		Resource trgExp = EMFUtil.loadExpectedResource(trg, generator.getResourceSet());

		Assert.assertEquals(srcExp.getContents().size(), generator.getSourceResource().getContents().size());
		for (int i = 0; i < srcExp.getContents().size(); i++) {
			familiesComp.assertEquals((FamilyRegister)srcExp.getContents().get(i), 
					                  (FamilyRegister)generator.getSourceResource().getContents().get(i));
		}
		for (int i = 0; i < trgExp.getContents().size(); i++) {
			personsComp.assertEquals((PersonRegister)trgExp.getContents().get(i), 
					                 (PersonRegister)generator.getTargetResource().getContents().get(i));
		}
	}
}
