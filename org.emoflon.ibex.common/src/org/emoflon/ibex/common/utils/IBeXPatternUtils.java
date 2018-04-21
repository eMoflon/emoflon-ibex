package org.emoflon.ibex.common.utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternSet;

/**
 * Utility for working with {@link IBeXPattern}s.
 */
public class IBeXPatternUtils {

	/**
	 * Checks whether the given pattern is empty.
	 * 
	 * @param ibexPattern
	 *            the pattern
	 * @return true if the pattern contains no nodes
	 */
	public static boolean isEmptyPattern(final IBeXContextPattern ibexPattern) {
		return ibexPattern.getSignatureNodes().isEmpty() && ibexPattern.getLocalNodes().isEmpty();
	}

	/**
	 * Finds an {@link IBeXNode} with the given name in the given IBeXPattern.
	 * 
	 * @param ibexPattern
	 *            the IBeXPattern, must not be <code>null</code>
	 * @param name
	 *            the name to search for, must not be <code>null</code>
	 * @return an Optional for a local IBeXNode
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final IBeXContextPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return findIBeXNodeWithName(ibexPattern.getLocalNodes(), ibexPattern.getSignatureNodes(), name);
	}

	/**
	 * Searches for an {@link IBeXNode} with the given name in the given list of
	 * nodes.
	 * 
	 * @param nodes
	 *            the nodes
	 * @param name
	 *            the name to search for
	 * @return an {@link Optional} for a node with the given name
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final List<IBeXNode> nodes, final String name) {
		Objects.requireNonNull(nodes, "nodes must not be null!");
		Objects.requireNonNull(name, "name must not be null!");
		return nodes.stream().filter(node -> name.equals(node.getName())).findAny();
	}

	/**
	 * Searches for an {@link IBeXNode} with the given name in the given lists of
	 * nodes.
	 * 
	 * @param nodes
	 *            the nodes
	 * @param nodes2
	 *            more nodes
	 * @param name
	 *            the name to search for
	 * @return an {@link Optional} for a node with the given name
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final List<IBeXNode> nodes, final List<IBeXNode> nodes2,
			final String name) {
		Optional<IBeXNode> node = findIBeXNodeWithName(nodes, name);
		if (node.isPresent()) {
			return node;
		} else {
			return findIBeXNodeWithName(nodes2, name);
		}
	}

	/**
	 * Returns the context pattern with the given name.
	 * 
	 * @param patternSet
	 *            the pattern set
	 * @param name
	 *            the name to search
	 * @return the context pattern with the given name
	 * @throws NoSuchElementException
	 *             if no context pattern with the given name exists
	 */
	public static IBeXContextPattern getContextPattern(final IBeXPatternSet patternSet, final String name) {
		Optional<IBeXContextPattern> pattern = patternSet.getContextPatterns().stream() //
				.filter(p -> p.getName().equals(name)) //
				.findAny();
		if (!pattern.isPresent()) {
			throw new NoSuchElementException(String.format("No context pattern called %s", name));
		}
		return pattern.get();
	}

	/**
	 * Returns the create pattern with the given name.
	 * 
	 * @param patternSet
	 *            the pattern set
	 * @param name
	 *            the name to search
	 * @return the create pattern with the given name
	 * @throws NoSuchElementException
	 *             if no create pattern with the given name exists
	 */
	public static IBeXCreatePattern getCreatePattern(final IBeXPatternSet patternSet, final String name) {
		Optional<IBeXCreatePattern> pattern = patternSet.getCreatePatterns().stream() //
				.filter(p -> p.getName().equals(name)) //
				.findAny();
		if (!pattern.isPresent()) {
			throw new NoSuchElementException(String.format("No create pattern called %s", name));
		}
		return pattern.get();
	}

	/**
	 * Returns the a delete pattern with the given name.
	 * 
	 * @param patternSet
	 *            the pattern set
	 * @param name
	 *            the name to search
	 * @return the delete pattern with the given name
	 * @throws NoSuchElementException
	 *             if no delete pattern with the given name exists
	 */
	public static IBeXDeletePattern getDeletePattern(final IBeXPatternSet patternSet, final String name) {
		Optional<IBeXDeletePattern> pattern = patternSet.getDeletePatterns().stream() //
				.filter(p -> p.getName().equals(name)) //
				.findAny();
		if (!pattern.isPresent()) {
			throw new NoSuchElementException(String.format("No delete pattern called %s", name));
		}
		return pattern.get();
	}
}
