package testsuite1.ClassInhHier2DB.modelgen;

import java.io.IOException;

import org.junit.Test;

public class SyncInstancesTest extends ModelGenTestCase {

	public SyncInstancesTest(Boolean flatten) {
		super(flatten);
	}
	
	@Test
	public void testAttributeToColumn_BWD() throws IOException {
		stop.setMaxRuleCount("PackageToDatabaseRule", 1);
		stop.setMaxRuleCount("ClassToTableRule", 1);
		stop.setMaxRuleCount("AttributeToColumnRule", 2);
		runGenerator(stop);
		assertPostcondition("expected/AttributeToColumn_BWD", "in/AttributeToColumn_BWD");
	}

}
