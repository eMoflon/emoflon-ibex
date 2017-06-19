package testsuite1.ClassInhHier2DB.modelgen;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.benchmarx.classInheritanceHierarchy.core.ClassInheritanceHierarchyComparator;
import org.benchmarx.database.core.DatabaseComparator;
import org.benchmarx.emf.Comparator;
import org.benchmarx.util.EMFUtil;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.strategies.gen.MODELGENStopCriterion;
import org.emoflon.ibex.tgg.run.MODELGEN_App;
import org.emoflon.ibex.tgg.runtime.engine.DemoclesEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import ClassInheritanceHierarchy.ClassPackage;
import Database.DB;

@RunWith(Parameterized.class)
public abstract class ModelGenTestCase {
	protected MODELGEN_App generator;
	protected Comparator<ClassPackage> classComp;
	protected Comparator<DB> dbComp;
	protected MODELGENStopCriterion stop;
	protected boolean flatten;
	
	protected ModelGenTestCase(Boolean flatten) {
		this.flatten = flatten;
	}
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}
	
	@Before
	public void createGenerator() throws IOException {
		generator = new MODELGEN_App("testsuite1_ClassInhHier2DB", "./../", flatten, false);
		generator.registerPatternMatchingEngine(new DemoclesEngine());
		stop = new MODELGENStopCriterion(generator.getTGG());
		
		stop.setMaxRuleCount("PackageToDatabaseRule", 0);
		stop.setMaxRuleCount("ClassToTableRule", 0);
		stop.setMaxRuleCount("SubClassToTable", 0);
		stop.setMaxRuleCount("AttributeToColumnRule", 0);
		
		classComp = new ClassInheritanceHierarchyComparator();
		dbComp = new DatabaseComparator();
	}

	protected void runGenerator(MODELGENStopCriterion stop) throws IOException {
//		long startTime = System.nanoTime();
		generator.setStopCriterion(stop); 
		generator.run();
		generator.terminate();
//		System.out.println((System.nanoTime() - startTime)/1000000.0);
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
			classComp.assertEquals((ClassPackage)srcExp.getContents().get(i), 
					                  (ClassPackage)generator.getSourceResource().getContents().get(i));
		}
	}

	protected void assertPostconditionOnTrg(String trg) {
		Resource trgExp = EMFUtil.loadExpectedResource(trg, generator.getResourceSet());
		
		Assert.assertNotEquals("Resource is empty", 0, trgExp.getContents().size());
		
		Assert.assertEquals(trgExp.getContents().size(), generator.getTargetResource().getContents().size());
		for (int i = 0; i < trgExp.getContents().size(); i++) {
			dbComp.assertEquals((DB)trgExp.getContents().get(i), 
					                  (DB)generator.getTargetResource().getContents().get(i));
		}
	}
	

	@Parameters
	public static Collection<Boolean> flattening() throws IOException {
		return Arrays.asList(false, true);
	}
}
