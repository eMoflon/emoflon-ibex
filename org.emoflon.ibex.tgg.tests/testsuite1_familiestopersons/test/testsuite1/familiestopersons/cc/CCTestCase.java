package testsuite1.familiestopersons.cc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.benchmarx.emf.Comparator;
import org.benchmarx.families.core.FamiliesComparator;
import org.benchmarx.persons.core.PersonsComparator;
import org.benchmarx.util.EMFUtil;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.run.CC_App;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import SimpleFamilies.FamilyRegister;
import SimplePersons.PersonRegister;

@RunWith(Parameterized.class)
public abstract class CCTestCase {
	protected CC_App generator;
	protected Comparator<FamilyRegister> familiesComp;
	protected Comparator<PersonRegister> personsComp;
	protected boolean flatten;
	
	protected CCTestCase(boolean flatten) {
		this.flatten = flatten;
	}
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}
	
	public void createGenerator(String srcInstance, String trgInstance) throws IOException {
		generator = new CC_App("testsuite1_familiestopersons", "./../", flatten, false, srcInstance, trgInstance);
	}

	protected void runGenerator() throws IOException {
//		long startTime = System.nanoTime();
		generator.run();
		generator.terminate();
		generator.saveModels();
//		System.out.println((System.nanoTime() - startTime)/1000000.0);
	}
	
	@Parameters
	public static Collection<Boolean> flattening() throws IOException {
		return Arrays.asList(true, false);
	}
}
