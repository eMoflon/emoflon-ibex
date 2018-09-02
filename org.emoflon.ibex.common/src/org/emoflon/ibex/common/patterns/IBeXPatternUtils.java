package org.emoflon.ibex.common.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.moflon.core.utilities.EcoreUtils;

import IBeXLanguage.IBeXContext;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXCreatePattern;
import IBeXLanguage.IBeXDeletePattern;
import IBeXLanguage.IBeXEdge;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNamedElement;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPattern;
import IBeXLanguage.IBeXPatternSet;

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
		return ibexPattern instanceof IBeXContextPattern && isEmptyPattern((IBeXContextPattern) ibexPattern);
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

		if (ibexPattern instanceof IBeXContextPattern) {
			return findIBeXNodeWithName((IBeXContextPattern) ibexPattern, name);
		} else if (ibexPattern instanceof IBeXCreatePattern) {
			return findIBeXNodeWithName((IBeXCreatePattern) ibexPattern, name);
		} else if (ibexPattern instanceof IBeXDeletePattern) {
			return findIBeXNodeWithName((IBeXDeletePattern) ibexPattern, name);
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
	 * Returns the context pattern with the given name.
	 * 
	 * @param patternSet the pattern set
	 * @param name       the name to search
	 * @return the context pattern with the given name
	 * @throws NoSuchElementException if no context pattern with the given name
	 *                                exists
	 */
	public static IBeXContext getContextPattern(final IBeXPatternSet patternSet, final String name) {
		Optional<IBeXContext> pattern = patternSet.getContextPatterns().stream() //
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
	 * @param patternSet the pattern set
	 * @param name       the name to search
	 * @return the create pattern with the given name
	 * @throws NoSuchElementException if no create pattern with the given name
	 *                                exists
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
	 * @param patternSet the pattern set
	 * @param name       the name to search
	 * @return the delete pattern with the given name
	 * @throws NoSuchElementException if no delete pattern with the given name
	 *                                exists
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

	/**
	 * Create an {@link IBeXPattern} for the given edge. If an {@link IBeXPattern}
	 * for the given {@link EReference} exists already, the existing pattern is
	 * returned.
	 * 
	 * @param edgeType the EReference to create a pattern for
	 * @return the created IBeXPattern
	 */
	public static <T extends IBeXContext> Optional<IBeXContextPattern> createEdgePattern(final EReference edgeType,
			HashMap<String, T> nameToPattern, Consumer<String> logError) {
		Objects.requireNonNull(edgeType, "Edge type must not be null!");

		EClass sourceType = edgeType.getEContainingClass();
		EClass targetType = edgeType.getEReferenceType();

		if (sourceType == null || targetType == null) {
			logError.accept("Cannot resolve reference source or target type.");
			return Optional.empty();
		}

		String name = String.format("edge-%s-%s-%s", EcoreUtils.getFQN(sourceType).replace(".", "_"),
				edgeType.getName(), EcoreUtils.getFQN(targetType).replace(".", "_"));

		if (nameToPattern.containsKey(name)) {
			return Optional.of((IBeXContextPattern) nameToPattern.get(name));
		}

		IBeXContextPattern edgePattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		edgePattern.setName(name);

		IBeXNode ibexSignatureSourceNode = IBeXPatternFactory.createNode("src", sourceType);
		edgePattern.getSignatureNodes().add(ibexSignatureSourceNode);

		IBeXNode ibexSignatureTargetNode = IBeXPatternFactory.createNode("trg", targetType);
		edgePattern.getSignatureNodes().add(ibexSignatureTargetNode);

		IBeXEdge ibexEdge = IBeXPatternFactory.createEdge(ibexSignatureSourceNode, ibexSignatureTargetNode, edgeType);
		edgePattern.getLocalEdges().add(ibexEdge);
		return Optional.of(edgePattern);
	}

	public static Collection<Optional<IBeXNode>> findIBexNodes(IBeXPattern ibexPattern, Collection<String> nodes) {
		return nodes.stream()//
				.map(name -> findIBeXNodeWithName(ibexPattern, name)).collect(Collectors.toList());
	}
}
