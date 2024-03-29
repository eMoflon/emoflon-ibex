package org.emoflon.ibex.common.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;


/**
 * Utility for working with {@link IBeXPattern}s.
 */
public class IBeXPatternUtils {
	/**
	 * A comparator for IBeXNamedElements.
	 */
	public static final Comparator<IBeXNamedElement> sortByName = (a, b) -> a.getName().compareTo(b.getName());
	
	/**
	 * Checks whether the given pattern is empty.
	 * 
	 * @param ibexPattern the pattern
	 * @return <code>true</code> if the pattern contains no nodes
	 */
	public static boolean isEmptyPattern(final IBeXContext ibexPattern) {
		return ibexPattern instanceof IBeXContextPattern ibexContextPattern && isEmptyPattern(ibexContextPattern);
	}

	/**
	 * Checks whether the given pattern is empty.
	 * 
	 * @param ibexPattern the pattern
	 * @return <code>true</code> if the pattern contains no nodes
	 */
	public static boolean isEmptyPattern(final IBeXContextPattern ibexPattern) {
		return ibexPattern.getSignatureNodes().isEmpty() && ibexPattern.getLocalNodes().isEmpty();
	}

	/**
	 * Finds a node with the given name in the given pattern.
	 * 
	 * @param ibexPattern the IBeXPattern, must not be <code>null</code>
	 * @param name        the name to search for
	 * @return an Optional for a local IBeXNode
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final IBeXPattern ibexPattern, final String name) {
		Objects.requireNonNull(ibexPattern, "pattern must not be null!");

		if (ibexPattern instanceof IBeXContextPattern ibexContextPattern) {
			return findIBeXNodeWithName(ibexContextPattern, name);
		} else if (ibexPattern instanceof IBeXCreatePattern ibexCreatePattern) {
			return findIBeXNodeWithName(ibexCreatePattern, name);
		} else if (ibexPattern instanceof IBeXDeletePattern ibexDeletePattern) {
			return findIBeXNodeWithName(ibexDeletePattern, name);
		} else {
			throw new IllegalArgumentException();
		}
	}

	private static Optional<IBeXNode> findIBeXNodeWithName(final IBeXContextPattern ibexPattern, final String name) {
		return findIBeXNodeWithName(ibexPattern.getLocalNodes(), ibexPattern.getSignatureNodes(), name);
	}

	private static Optional<IBeXNode> findIBeXNodeWithName(final IBeXCreatePattern ibexPattern, final String name) {
		return findIBeXNodeWithName(ibexPattern.getCreatedNodes(), ibexPattern.getContextNodes(), name);
	}

	private static Optional<IBeXNode> findIBeXNodeWithName(final IBeXDeletePattern ibexPattern, final String name) {
		return findIBeXNodeWithName(ibexPattern.getDeletedNodes(), ibexPattern.getContextNodes(), name);
	}

	/**
	 * Searches for an {@link IBeXNode} with the given name in the given list of
	 * nodes.
	 * 
	 * @param nodes the nodes
	 * @param name  the name to search for
	 * @return an {@link Optional} for a node with the given name
	 */
	public static Optional<IBeXNode> findIBeXNodeWithName(final List<IBeXNode> nodes, final String name) {
		Objects.requireNonNull(nodes, "nodes must not be null!");
		if (name == null) {
			return Optional.empty();
		}
		return nodes.stream().filter(node -> name.equals(node.getName())).findAny();
	}

	/**
	 * Searches for an {@link IBeXNode} with the given name in the given lists of
	 * nodes.
	 * 
	 * @param nodes  the nodes
	 * @param nodes2 more nodes
	 * @param name   the name to search for
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
	 * Returns all nodes of a context pattern.
	 * 
	 * @param ibexPattern the context pattern
	 * @return all signature and local nodes
	 */
	public static List<IBeXNode> getAllNodes(final IBeXContextPattern ibexPattern) {
		List<IBeXNode> allNodes = new ArrayList<IBeXNode>();
		allNodes.addAll(ibexPattern.getLocalNodes());
		allNodes.addAll(ibexPattern.getSignatureNodes());
		allNodes.sort(sortByName);
		return allNodes;
	}

	

	public static Collection<Optional<IBeXNode>> findIBexNodes(IBeXPattern ibexPattern, Collection<String> nodes) {
		return nodes.stream()//
				.map(name -> findIBeXNodeWithName(ibexPattern, name)).collect(Collectors.toList());
	}
}
