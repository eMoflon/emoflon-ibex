package testsuite1.ClassInhHier2DB.cc;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.emoflon.ibex.tgg.run.CC_App;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public abstract class CCTestCase {
	protected CC_App generator;
	protected boolean flatten;
	
	protected CCTestCase(boolean flatten) {
		this.flatten = flatten;
	}
	
	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
	}
	
	public void createGenerator(String srcInstance, String trgInstance) throws IOException {
		generator = new CC_App("testsuite1_ClassInhHier2DB", "./../", flatten, true, srcInstance, trgInstance);
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
