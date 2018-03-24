package org.emoflon.ibex.gt.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXAttributeParameter;
import IBeXLanguage.IBeXPattern;

/**
 * Utility methods to filter match streams.
 */
public class MatchFilter {

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
	public static Stream<IMatch> filterNodeBindings(Stream<IMatch> matches, final IBeXPattern pattern,
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
			final IBeXPattern pattern, final Map<String, Object> parameters) {
		for (IBeXAttributeConstraint ac : pattern.getAttributeConstraint()) {
			if (ac.getValue() instanceof IBeXAttributeParameter) {
				String nodeName = ac.getNode().getName();
				String parameterName = ((IBeXAttributeParameter) ac.getValue()).getName();
				if (!parameters.containsKey(parameterName)) {
					throw new IllegalArgumentException("Missing required parameter " + parameterName);
				}
				Object parameterValue = parameters.get(parameterName);

				matches = matches.filter(m -> {
					EObject node = (EObject) m.get(nodeName);
					Object currentValue = node.eGet(ac.getType());

					switch (ac.getRelation()) {
					case GREATER_OR_EQUAL:
						return compareTo(currentValue, parameterValue, x -> x >= 0);
					case EQUAL:
						return currentValue.equals(parameterValue);
					case GREATER:
						return compareTo(currentValue, parameterValue, x -> x > 0);
					case SMALLER:
						return compareTo(currentValue, parameterValue, x -> x < 0);
					case SMALLER_OR_EQUAL:
						return compareTo(currentValue, parameterValue, x -> x <= 0);
					case UNEQUAL:
						return !currentValue.equals(parameterValue);
					default:
						return false;
					}
				});
			}
		}
		return matches;
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
		if ((a instanceof Comparable) && (b instanceof Comparable)) {
			return condition.test(((Comparable) a).compareTo(b));
		}
		return false;
	}
}
