package org.emoflon.ibex.gt.engine;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeConstraint;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXAttributeParameter;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContext;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextAlternatives;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXRelation;

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
		}
		throw new IllegalArgumentException("Invalid pattern " + pattern);
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

		Stream<IMatch> matchesForPattern = matches.get(pattern.getName()).stream();
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
			if (ac.getValue() instanceof IBeXAttributeParameter) {
				String nodeName = ac.getNode().getName();
				String parameterName = ((IBeXAttributeParameter) ac.getValue()).getName();
				if (!parameters.containsKey(parameterName)) {
					throw new IllegalArgumentException("Missing required parameter " + parameterName);
				}
				matches = matches.filter(m -> {
					EObject node = (EObject) m.get(nodeName);
					Object currentValue = node.eGet(ac.getType());
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean compareTo(final Object a, final Object b, final Predicate<Integer> condition) {
		return a instanceof Comparable && b instanceof Comparable //
				&& condition.test(((Comparable) a).compareTo(b));
	}
}
