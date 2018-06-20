package org.emoflon.ibex.common.operational;

import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;

import IBeXLanguage.IBeXPatternSet;

/**
 * Interface for a pattern matcher.
 */
public interface IContextPatternInterpreter {

	/**
	 * Initializes the patterns of the engine with the given IBeXPatterns, which
	 * need to be transformed into the patterns of the concrete pattern matcher.
	 * 
	 * @param ibexPatternSet
	 *            the IBeXPatternSet to transform
	 */
	public void initPatterns(IBeXPatternSet ibexPatternSet);

	/**
	 * Initializes the pattern interpreter.
	 * 
	 * @param registry
	 *            ??
	 * @param matchObserver
	 *            the observer to notify of matches
	 */
	public void initialise(Registry registry, IMatchObserver matchObserver);

	/**
	 * Creates a resource set.
	 * 
	 * @param workspacePath
	 *            the path to the workspace root
	 * @return the resource set
	 */
	public ResourceSet createAndPrepareResourceSet(String workspacePath);

	/**
	 * Monitors the given resource set.
	 * 
	 * @param resourceSet
	 *            the resource set
	 */
	public void monitor(ResourceSet resourceSet);

	/**
	 * Updates the matches.
	 */
	public void updateMatches();

	/**
	 * Terminates the pattern interpreter.
	 */
	public void terminate();

	/**
	 * Sets the debug path.
	 * 
	 * @param debugPath
	 *            the path for the debugging output. If it is <code>null</null>,
	 *            debugging is disabled.
	 */
	public void setDebugPath(String debugPath);
}
