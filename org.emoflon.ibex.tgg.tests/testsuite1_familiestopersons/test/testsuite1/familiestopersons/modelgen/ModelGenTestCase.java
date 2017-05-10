package testsuite1.familiestopersons.modelgen;

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

import SimpleFamilies.FamilyRegister;
import SimplePersons.PersonRegister;

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
		
		stop.setMaxRuleCount("HandleRegisters", 0);
		stop.setMaxRuleCount("IgnoreFamily", 0);
		stop.setMaxRuleCount("FatherToMale", 0);
		stop.setMaxRuleCount("MotherToFemale", 0);
		stop.setMaxRuleCount("DaughterToFemale", 0);
		stop.setMaxRuleCount("SonToMale", 0);
		stop.setMaxRuleCount("FatherToNothing", 0);
		stop.setMaxRuleCount("FatherAndMale", 0);
		stop.setMaxRuleCount("IgnoreTwoFamilies", 0);
		stop.setMaxRuleCount("CreateFourthFamily", 0);
		stop.setMaxRuleCount("CreateFourthAndFifthFamily", 0);
		stop.setMaxRuleCount("CreateFather", 0);
		stop.setMaxRuleCount("ConnectFather", 0);
		
		familiesComp = new FamiliesComparator();
		personsComp = new PersonsComparator();
	}

	protected void runGenerator(MODELGENStopCriterion stop) throws IOException {
		generator.setStopCriterion(stop); 
		generator.run();
		generator.terminate();
	}

	protected void assertPostcondition(String src, String trg) {
		assertPostconditionOnSrc(src);
		assertPostconditionOnTrg(trg);
	}

	protected void assertPostconditionOnSrc(String src) {
		Resource srcExp = EMFUtil.loadExpectedResource(src, generator.getResourceSet());
		
		Assert.assertNotEquals("Resource is empty", 0, srcExp.getContents().size());
		
		Assert.assertEquals(srcExp.getContents().size(), generator.getSourceResource().getContents().size());
		for (int i = 0; i < srcExp.getContents().size(); i++) {
			familiesComp.assertEquals((FamilyRegister)srcExp.getContents().get(i), 
					                  (FamilyRegister)generator.getSourceResource().getContents().get(i));
		}
	}

	protected void assertPostconditionOnTrg(String trg) {
		Resource trgExp = EMFUtil.loadExpectedResource(trg, generator.getResourceSet());
		
		Assert.assertNotEquals("Resource is empty", 0, trgExp.getContents().size());
		
		Assert.assertEquals(trgExp.getContents().size(), generator.getTargetResource().getContents().size());
		for (int i = 0; i < trgExp.getContents().size(); i++) {
			personsComp.assertEquals((PersonRegister)trgExp.getContents().get(i), 
					                  (PersonRegister)generator.getTargetResource().getContents().get(i));
		}
	}
}
