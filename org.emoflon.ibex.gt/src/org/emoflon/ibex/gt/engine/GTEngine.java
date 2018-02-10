package org.emoflon.ibex.gt.engine;

import java.util.Optional;

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
		IBeXPatternSet ibexPatterns = IBeXLanguageFactory.eINSTANCE.createIBeXPatternSet();
		// TODO: load patterns from file

		// Transform into patterns of the concrete engine.
		ibexPatterns.getPatterns().forEach(ibexPattern -> {
			this.transformPattern(ibexPattern);
		});
		this.savePatternsForDebugging();
	}

	/**
	 * Transforms the IBeXPattern to the patterns of the engine.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern to add
	 */
	protected abstract void transformPattern(final IBeXPattern ibexPattern);
	
	/**
	 * Serializes the output for debugging.
	 */
	protected abstract void savePatternsForDebugging();
}
