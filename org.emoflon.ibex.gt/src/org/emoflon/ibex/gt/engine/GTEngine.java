package org.emoflon.ibex.gt.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.common.utils.ModelPersistenceUtils;
import org.emoflon.ibex.gt.editor.GTStandaloneSetup;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.engine.transformations.EditorToInternalGT;
import org.emoflon.ibex.gt.engine.transformations.InternalGTToIBeX;

import com.google.inject.Injector;

import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternSet;

/**
 * Engine for Unidirectional Graph Transformations which needs to be implemented
 * for a concrete pattern matcher.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GTEngine {
	protected Optional<String> debugPath = Optional.ofNullable(null);

	/**
	 * Enables the debugging mode if the given path is not <code>null</null>.
	 * 
	 * @param debugPath
	 *            the path for the debugging output
	 */
	public void setDebug(final String debugPath) {
		this.debugPath = Optional.ofNullable(debugPath);
	}

	/**
	 * Loads rules from an .gt file.
	 * 
	 * @param filePath
	 *            the path to the given file.
	 */
	public void loadRulesFromFile(final String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IllegalArgumentException(String.format("File %s does not exist", file.getAbsolutePath()));
		}
		if (!file.getName().endsWith(".gt")) {
			throw new IllegalArgumentException(String.format("File %s does not have extension .gt", file.getName()));
		}

		// Prepare parsing gt file with Xtext parser.
		new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../");
		Injector injector = new GTStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.createResource(URI.createURI("dummy:/" + filePath));

		// Parse .gt file.
		try {
			InputStream in = new FileInputStream(file);
			resource.load(in, resourceSet.getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
		GraphTransformationFile model = (GraphTransformationFile) resource.getContents().get(0);

		// Transform into internal GT model.
		GTRuleSet gtRuleSet = EditorToInternalGT.transformRuleSet(model);
		this.debugPath.ifPresent(path -> {
			ModelPersistenceUtils.saveModel(gtRuleSet, path + "/gt/" + file.getName());
		});

		// Transform into IBeXPatterns.
		IBeXPatternSet ibexPatterns = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		gtRuleSet.getRules().forEach(gtRule -> {
			EList<IBeXPattern> ibexPatternsForRule = InternalGTToIBeX.transformRule(gtRule).getPatterns();
			ibexPatterns.getPatterns().addAll(ibexPatternsForRule);
		});
		this.debugPath.ifPresent(path -> {
			ModelPersistenceUtils.saveModel(ibexPatterns, path + "/ibex-patterns");
		});

		// TODO: transform into patterns of the concrete engine.
		ibexPatterns.getPatterns().forEach(ibexPattern -> {
			this.createPattern(ibexPattern);
		});
	}

	/**
	 * Adds the IBeXPattern to the patterns of the engine.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add
	 */
	public abstract void createPattern(final IBeXPattern ibexPattern);
}
