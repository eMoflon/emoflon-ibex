package org.emoflon.ibex.gt.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.emoflon.ibex.gt.editor.GTStandaloneSetup;
import org.emoflon.ibex.gt.editor.gT.GraphTransformationFile;

import com.google.inject.Injector;

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
		new org.eclipse.emf.mwe.utils.StandaloneSetup().setPlatformUri("../");
		Injector injector = new GTStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		Resource resource = resourceSet.createResource(URI.createURI("dummy:/" + filePath));
		try {
			InputStream in = new FileInputStream(filePath);
			resource.load(in, resourceSet.getLoadOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
		GraphTransformationFile model = (GraphTransformationFile) resource.getContents().get(0);
		if (this.debug) {
			model.getRules().forEach(rule -> System.out.println(rule.getName()));
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
