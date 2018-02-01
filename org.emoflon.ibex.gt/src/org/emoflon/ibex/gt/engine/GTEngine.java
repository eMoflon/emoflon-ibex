package org.emoflon.ibex.gt.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.gt.editor.GTStandaloneSetup;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;
import org.emoflon.ibex.gt.transformation.EditorToInternalGT;
import org.emoflon.ibex.gt.utils.ModelPersistenceUtils;

import com.google.inject.Injector;

import GTLanguage.GTRuleSet;
import IBeXLanguage.IBeXPattern;

/**
 * Engine for Unidirectional Graph Transformations which needs to be implemented
 * for a concrete pattern matcher.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public abstract class GTEngine {
	protected boolean debug = false;

	/**
	 * Enables or disables the debugging mode.
	 * 
	 * @param debug
	 *            the new value for the debugging flag
	 */
	public void setDebug(final boolean debug) {
		this.debug = debug;
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
		if (this.debug) {
			ModelPersistenceUtils.saveModel(gtRuleSet, "debug/gt/" + file.getName());
		}
	}

	/**
	 * Adds the IBeXPattern to the patterns of the engine.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add
	 */
	public abstract void createPattern(final IBeXPattern ibexPattern);
}
