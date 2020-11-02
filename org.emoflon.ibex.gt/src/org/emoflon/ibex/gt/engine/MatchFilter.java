package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.SimpleMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeExpression;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextTransitive;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXTransitiveEdge;

/**
 * Utility methods to filter match streams.
 */
public class MatchFilter {

	/**
	 * Returns a stream of matches for the pattern such that the parameter values of
	 * the matches are equal to the given parameters.
	 * 
	 * @param pattern
	 *            the context pattern or context alternatives
	 * @param parameters
	 *            the parameter map
	 * @param matches
	 *            the matches
	 * @return a stream containing matches
	 */
	public static Stream<IMatch> getFilteredMatchStream(final IBeXContext pattern, final Map<String, Object> parameters,
			final Map<String, Collection<IMatch>> matches) {
		if (pattern instanceof IBeXContextPattern) {
			return MatchFilter.getFilteredMatchStream((IBeXContextPattern) pattern, parameters, matches);
		} else if (pattern instanceof IBeXContextAlternatives) {
			IBeXContextAlternatives alternatives = (IBeXContextAlternatives) pattern;
			Function<IMatch, IMatch> renameMatchToAlternativePattern = m -> {
				m.setPatternName(alternatives.getName());
				return m;
			};

			Stream<IMatch> matchStream = Stream.empty();
			for (IBeXContextPattern alternativePattern : alternatives.getAlternativePatterns()) {
				Stream<IMatch> matchesForAlterative = MatchFilter.getFilteredMatchStream(alternativePattern, parameters, matches)
						.map(renameMatchToAlternativePattern);
				matchStream = Stream.concat(matchStream, matchesForAlterative);
			}
			return matchStream.distinct();
		} else if (pattern instanceof IBeXContextTransitive) {
			IBeXContextTransitive transitivePattern = (IBeXContextTransitive)pattern;
			return MatchFilter.getFilteredMatchStream(transitivePattern, parameters, matches);
		}
		throw new IllegalArgumentException("Invalid pattern " + pattern);
	}
	
	private static Stream<IMatch>  getFilteredMatchStream(final IBeXContextTransitive transitivePattern, final Map<String, Object> parameters,
			final Map<String, Collection<IMatch>> matches) {
		Map<IBeXNode, IBeXContextPattern> node2Pattern = new HashMap<>();
		Map<IBeXContextPattern, Map<EObject, Set<IMatch>>> node2Match = Collections.synchronizedMap(new HashMap<>());
		for(IBeXContextPattern subPattern : transitivePattern.getSubPatterns()) {
			for(IBeXNode node : subPattern.getSignatureNodes()) {
				node2Pattern.put(node, subPattern);
			}
		}
		for(IBeXTransitiveEdge edge : transitivePattern.getTransitiveEdges()) {
			IBeXContextPattern srcPattern = node2Pattern.get(edge.getTargetNode());
			IBeXContextPattern trgPattern = node2Pattern.get(edge.getTargetNode());
			Map<EObject, Set<IMatch>> srcMatches = node2Match.get(srcPattern);
			Map<EObject, Set<IMatch>> trgMatches = node2Match.get(trgPattern);
			if(srcMatches == null) {
				srcMatches = Collections.synchronizedMap(new HashMap<>());
				node2Match.put(srcPattern, srcMatches);
			}
			if(trgMatches == null) {
				trgMatches = Collections.synchronizedMap(new HashMap<>());
				node2Match.put(trgPattern, trgMatches);
			}
			for(IMatch match : matches.get(srcPattern.getName())) {
				EObject node = (EObject) match.get(edge.getSourceNode().getName());
				Set<IMatch> nodeMatches = srcMatches.get(node);
				if(nodeMatches == null) {
					nodeMatches = Collections.synchronizedSet(new HashSet<>());
					srcMatches.put(node, nodeMatches);
				}
				nodeMatches.add(match);
			}
			for(IMatch match : matches.get(trgPattern.getName())) {
				EObject node = (EObject) match.get(edge.getTargetNode().getName());
				Set<IMatch> nodeMatches = trgMatches.get(node);
				if(nodeMatches == null) {
					nodeMatches = Collections.synchronizedSet(new HashSet<>());
					trgMatches.put(node, nodeMatches);
				}
				nodeMatches.add(match);
			}
		}
		
		IBeXTransitiveEdge initialEdge = transitivePattern.getTransitiveEdges().get(0);	
		Stream<IMatch> matchesForPattern = findTransitiveMatches(matches.get(node2Pattern.get(initialEdge.getSourceNode()).getName()).parallelStream(), 
				transitivePattern.getTransitiveEdges(), 0, node2Pattern, node2Match, matches);
		matchesForPattern = MatchFilter.filterNodeBindings(matchesForPattern, transitivePattern.getBasePattern(), parameters);
		matchesForPattern = MatchFilter.filterAttributeConstraintsWithParameter(matchesForPattern, transitivePattern.getBasePattern(), parameters);
		return matchesForPattern;
	}
	
	private static Stream<IMatch> findTransitiveMatches(final Stream<IMatch> found, final List<IBeXTransitiveEdge> edges, int currentEdge, 
			final Map<IBeXNode, IBeXContextPattern> node2Pattern, final Map<IBeXContextPattern, Map<EObject, Set<IMatch>>> node2Match,
			final Map<String, Collection<IMatch>> matches) {
		
		if(currentEdge >= edges.size()) {
			return found;
		}
		
		IBeXTransitiveEdge edge = edges.get(currentEdge);
		Stream<IMatch> connectedMatches = found
				.flatMap(match -> findTransitiveNodes(Stream.empty(), Collections.synchronizedSet(new HashSet<>()), 
						(EObject) match.get(edge.getSourceNode().getName()), edge.getType())
						.map(node -> {
							SimpleMatch simpleMatch = new SimpleMatch(match);
							simpleMatch.put(edge.getTargetNode().getName(), node);
							return (IMatch)simpleMatch;
						}))
				.distinct()
				.filter(match -> node2Match.get(node2Pattern.get(edge.getTargetNode())).containsKey(match.get(edge.getTargetNode().getName())))
				.flatMap(match -> node2Match.get(node2Pattern.get(edge.getTargetNode())).get(match.get(edge.getTargetNode().getName())).parallelStream()
						.map(trgMatch -> {
							IBeXContextPattern trgPattern = node2Pattern.get(edge.getTargetNode());
							SimpleMatch simpleMatch = new SimpleMatch(match);
							for(IBeXNode ibexNode : trgPattern.getSignatureNodes()) {
								simpleMatch.put(ibexNode.getName(), trgMatch.get(ibexNode.getName()));
							}
							return (IMatch)simpleMatch;
						}))
				.distinct();

		return findTransitiveMatches(connectedMatches, edges, currentEdge+1, node2Pattern, node2Match, matches);
	}
	
	@SuppressWarnings("unchecked")
	private static Stream<EObject> findTransitiveNodes(final Stream<EObject> found, final Set<EObject> visitedNodes, final EObject src, final EReference edge) {
		if(visitedNodes.contains(src))
			return found;
		
		visitedNodes.add(src);
		if(edge.getUpperBound() != 1) {
			EList<EObject> otherNodes = (EList<EObject>)src.eGet(edge);
			if(otherNodes == null || otherNodes.isEmpty())
				return found;
			
			return otherNodes.parallelStream().flatMap(otherNode -> findTransitiveNodes(Stream.concat(found, otherNodes.parallelStream()), visitedNodes, otherNode, edge));
		}else {
			EObject otherNode = (EObject)src.eGet(edge);
			if(otherNode == null)
				return found;
			
			Stream.of(otherNode);
			return findTransitiveNodes(Stream.concat(found, Stream.of(otherNode)), visitedNodes, otherNode, edge);
		}
	}

	/**
	 * Returns a stream of matches for the pattern such that the parameter values of
	 * the matches are equal to the given parameters.
	 * 
	 * @param pattern
	 *            the context pattern
	 * @param parameters
	 *            the parameter map
	 * @param matches
	 *            the matches
	 * @return a stream containing matches
	 */
	private static Stream<IMatch> getFilteredMatchStream(final IBeXContextPattern pattern,
			final Map<String, Object> parameters, final Map<String, Collection<IMatch>> matches) {
		if (!matches.containsKey(pattern.getName())) {
			return Stream.empty();
		}

		Stream<IMatch> matchesForPattern = matches.get(pattern.getName()).parallelStream();
		matchesForPattern = MatchFilter.filterNodeBindings(matchesForPattern, pattern, parameters);
		matchesForPattern = MatchFilter.filterAttributeConstraintsWithParameter(matchesForPattern, pattern, parameters);
		return matchesForPattern;
	}

	/**
	 * Filters the given matches for the ones whose node bindings are conform to the
	 * binding required by the parameters.
	 * 
	 * @param matches
	 *            the matches for the pattern
	 * @param pattern
	 *            the pattern
	 * @param parameters
	 *            the parameters
	 * @return the filtered stream
	 */
	public static Stream<IMatch> filterNodeBindings(Stream<IMatch> matches, final IBeXContextPattern pattern,
			final Map<String, Object> parameters) {
		List<String> nodeNames = pattern.getSignatureNodes().stream() //
				.map(node -> node.getName()) //
				.collect(Collectors.toList());

		Iterator<String> parameterIterator = parameters.keySet().iterator();
		while (parameterIterator.hasNext()) {
			String parameterName = parameterIterator.next();
			if (nodeNames.contains(parameterName)) {
				matches = matches.filter(
						m -> m.isInMatch(parameterName) && parameters.get(parameterName).equals(m.get(parameterName)));
			}
		}
		return matches;
	}

	/**
	 * Filters the matches for the ones which fulfill the parameterized attribute
	 * constraints of the pattern.
	 * 
	 * @param matches
	 *            the matches for the pattern
	 * @param pattern
	 *            the pattern
	 * @param parameters
	 *            the parameters
	 * @return the filtered stream
	 */
	public static Stream<IMatch> filterAttributeConstraintsWithParameter(Stream<IMatch> matches,
			final IBeXContextPattern pattern, final Map<String, Object> parameters) {
		for (IBeXAttributeConstraint ac : pattern.getAttributeConstraint()) {
			if (ac.getLhs() instanceof IBeXAttributeExpression && ac.getRhs() instanceof IBeXAttributeParameter) {
				IBeXAttributeExpression lhs = (IBeXAttributeExpression)ac.getLhs();
				IBeXAttributeParameter rhs = (IBeXAttributeParameter)ac.getRhs();
				String nodeName = lhs.getNode().getName();
				String parameterName = rhs.getName();
				if (!parameters.containsKey(parameterName)) {
					throw new IllegalArgumentException("Missing required parameter " + parameterName);
				}
				matches = matches.filter(m -> {
					EObject node = (EObject) m.get(nodeName);
					Object currentValue = node.eGet(lhs.getAttribute());
					return compare(currentValue, parameters.get(parameterName), ac.getRelation());
				});
			} else if(ac.getRhs() instanceof IBeXAttributeExpression && ac.getLhs() instanceof IBeXAttributeParameter) {
				IBeXAttributeExpression lhs = (IBeXAttributeExpression)ac.getRhs();
				IBeXAttributeParameter rhs = (IBeXAttributeParameter)ac.getLhs();
				String nodeName = lhs.getNode().getName();
				String parameterName = rhs.getName();
				if (!parameters.containsKey(parameterName)) {
					throw new IllegalArgumentException("Missing required parameter " + parameterName);
				}
				matches = matches.filter(m -> {
					EObject node = (EObject) m.get(nodeName);
					Object currentValue = node.eGet(lhs.getAttribute());
					return compare(currentValue, parameters.get(parameterName), ac.getRelation());
				});
			}
		}
		return matches;
	}

	/**
	 * Compares two objects according to the given relation.
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param relation
	 *            the relation
	 * @return the result of the comparison
	 */
	private static boolean compare(final Object a, final Object b, final IBeXRelation relation) {
		switch (relation) {
		case GREATER_OR_EQUAL:
			return compareTo(a, b, x -> x >= 0);
		case EQUAL:
			return a.equals(b);
		case GREATER:
			return compareTo(a, b, x -> x > 0);
		case SMALLER:
			return compareTo(a, b, x -> x < 0);
		case SMALLER_OR_EQUAL:
			return compareTo(a, b, x -> x <= 0);
		case UNEQUAL:
			return !a.equals(b);
		default:
			return false;
		}
	}

	/**
	 * Compares to objects according to the given condition.
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param condition
	 *            the condition
	 * @return the result of the comparison. For any objects which are not
	 *         {@link Comparable}, <code>false</code> will be returned.
	 */
	@SuppressWarnings("unchecked")
	private static boolean compareTo(final Object a, final Object b, final Predicate<Integer> condition) {
		return a instanceof Comparable && b instanceof Comparable //
				&& condition.test(((Comparable<Object>) a).compareTo(b));
	}
}
