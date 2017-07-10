package testsuite1.ClassInhHier2DB.modelgen;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

public class SyncInstancesTest extends ModelGenTestCase {

	public SyncInstancesTest(Boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testPackageToDatabase() throws IOException {
		stop.setMaxRuleCount("PackageToDatabaseRule", 1);
		runGenerator(stop);
		assertPostcondition("in/PackageToDatabase_FWD", "expected/PackageToDatabase_FWD");
	}
	
	@Test
	public void testClassToTable() throws IOException {
		stop.setMaxRuleCount("PackageToDatabaseRule", 1);
		stop.setMaxRuleCount("ClassToTableRule", 1);
		runGenerator(stop);
		assertPostcondition("in/ClassToTable_FWD", "expected/ClassToTable_FWD");
	}
	
	@Test
	public void testAttributeToColumn() throws IOException {
		stop.setMaxRuleCount("PackageToDatabaseRule", 1);
		stop.setMaxRuleCount("ClassToTableRule", 1);
		stop.setMaxRuleCount("AttributeToColumnRule", 2);
		runGenerator(stop);
		assertPostcondition("in/AttributeToColumn_FWD", "expected/AttributeToColumn_FWD");
	}
	
	@Test
	public void testSubClassToTable() throws IOException {
		stop.setMaxRuleCount("PackageToDatabaseRule", 1);
		stop.setMaxRuleCount("ClassToTableRule", 1);
		stop.setMaxRuleCount("SubClassToTable", 1);
		runGenerator(stop);
		assertPostcondition("in/SubClassToTable_FWD", "expected/SubClassToTable_FWD");
	}

}
