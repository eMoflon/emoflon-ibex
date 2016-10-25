package org.moflon.tgg.mosl;

import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.mwe2.language.Mwe2StandaloneSetup;
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Launcher;
import org.eclipse.emf.mwe2.launch.runtime.Mwe2Runner;
import org.junit.Ignore;
import org.junit.Test;

import com.google.inject.Injector;

public class Mwe2LauncherTest {

	@Ignore ("Just for testing how to invoke MWE2 workflows programmatically (rkluge)")
	@Test
	public void testWithMweLauncher() throws Exception {
		new Mwe2Launcher().run(new String[] { "src/org/moflon/tgg/mosl/GenerateTGG.mwe2" });
	}

	@Ignore ("Just for testing how to invoke MWE2 workflows programmatically (rkluge)")
	@Test
	public void testWithMweRunner() throws Exception {
		final Injector injector = new Mwe2StandaloneSetup().createInjectorAndDoEMFRegistration();
		final Mwe2Runner mweRunner = injector.getInstance(Mwe2Runner.class);
		mweRunner.run(URI.createURI("src/org/moflon/tgg/mosl/GenerateTGG.mwe2", true), new HashMap<>());
		
		// IFile file = ...;
		// mweRunner.run(new
		// String[]{URI.createPlatformResourceURI(file.getFullPath().toString(),
		// true).toString()});
	}
}
