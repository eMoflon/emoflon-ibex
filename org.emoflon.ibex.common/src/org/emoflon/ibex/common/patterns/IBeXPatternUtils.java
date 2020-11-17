package org.emoflon.ibex.common.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXCreatePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDeletePattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXDisjunctContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNamedElement;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternSet;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRuleSet;



/**
 * Utility for working with {@link IBeXPattern}s.
 */
public class IBeXPatternUtils {
	/**
	 * A comparator for IBeXNamedElements.
	 */
	public static final Comparator<IBeXNamedElement> sortByName = (a, b) -> a.getName().compareTo(b.getName());
	
	/**
	 * Removes all IBeXDisjunctContextPatterns in a PatternSet, adds all subpatterns to it and creates a new patternSet for the disjunct context patterns
	 * 
	 * @param ibexPattern the pattern
	 * @return the new, modified PatternSet
	 */
	public static List<IBeXDisjunctContextPattern> transformIBeXPatternSet(IBeXPatternSet ibexPattern) {
		
		List<IBeXContext> disjunctPatterns = ibexPattern.getContextPatterns().stream()
				.filter(pattern -> pattern instanceof IBeXDisjunctContextPattern).collect(Collectors.toList());
		ibexPattern.getContextPatterns().removeAll(disjunctPatterns);
		
		List<IBeXDisjunctContextPattern> patterns = new ArrayList<IBeXDisjunctContextPattern>();
		for(IBeXContext pattern: disjunctPatterns) {
			patterns.add((IBeXDisjunctContextPattern) pattern);
		}	
		return patterns;
	}
	
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
	public static IBeXContext getContextPattern(final IBeXPatternSet patternSet, final List<IBeXDisjunctContextPattern> disjunctContextPatterns , final String name) {
		Optional<IBeXContext> pattern = patternSet.getContextPatterns().stream() //
				.filter(p -> p.getName().equals(name)) //
				.findAny();
		if (!pattern.isPresent()) {
			//maybe it is an IBeXDisjunctPattern
			Optional<IBeXDisjunctContextPattern> disjunctPattern = disjunctContextPatterns.stream().filter(p -> p.getName().equals(name)).findAny();
			if(!disjunctPattern.isPresent()) {
				throw new NoSuchElementException(String.format("No context pattern called %s", name));
			}
			return disjunctPattern.get();
		}
		return pattern.get();
	}

	/**
	 * Returns the create pattern with the given name.
	 * 
	 * @param ruleSet the pattern set
	 * @param name       the name to search
	 * @return the create pattern with the given name
	 * @throws NoSuchElementException if no create pattern with the given name
	 *                                exists
	 */
	public static IBeXCreatePattern getCreatePattern(final IBeXRuleSet ruleSet, final String name) {
		Optional<IBeXCreatePattern> pattern = ruleSet.getRules().stream() //
				.map(rule -> rule.getCreate()) //
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
	 * @param ruleSet the pattern set
	 * @param name       the name to search
	 * @return the delete pattern with the given name
	 * @throws NoSuchElementException if no delete pattern with the given name
	 *                                exists
	 */
	public static IBeXDeletePattern getDeletePattern(final IBeXRuleSet ruleSet, final String name) {
		Optional<IBeXDeletePattern> pattern = ruleSet.getRules().stream() //
				.map(rule -> rule.getDelete()) //
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

	

	public static Collection<Optional<IBeXNode>> findIBexNodes(IBeXPattern ibexPattern, Collection<String> nodes) {
		return nodes.stream()//
				.map(name -> findIBeXNodeWithName(ibexPattern, name)).collect(Collectors.toList());
	}
}
