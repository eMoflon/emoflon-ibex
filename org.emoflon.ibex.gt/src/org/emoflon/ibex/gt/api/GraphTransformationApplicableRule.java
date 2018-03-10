package org.emoflon.ibex.gt.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * This is the abstraction for all applicable rules. Via <code>apply()</code>
 * and its variants, rules can be applied.
 * 
 * If no pushout approach is explicitly defined when calling a
 * <code>apply</code> method, the default pushout approach of the API which
 * created the rule will be used.
 * 
 * Concrete Implementations of this class should provide typed methods for
 * binding the parameters.
 * 
 * @param <M>
 *            the type of matches returned by this rule
 * @param <R>
 *            the own type
 */
public abstract class GraphTransformationApplicableRule<M extends GraphTransformationMatch<M, R>, R extends GraphTransformationRule<M, R>>
		extends GraphTransformationRule<M, R> {

	/**
	 * Creates a new executable rule.
	 * 
	 * @param api
	 *            the API the rule belongs to
	 * @param interpreter
	 *            the interpreter
	 * @param ruleName
	 *            the name of the rule
	 */
	public GraphTransformationApplicableRule(final GraphTransformationAPI api,
			final GraphTransformationInterpreter interpreter, final String ruleName) {
		super(api, interpreter, ruleName);
	}

	/**
	 * Applies the rule on an arbitrary match with the default pushout approach if a
	 * match can be found.
	 * 
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply() {
		return this.apply(this.api.getDefaultPushoutApproach());
	}

	/**
	 * Applies the rule on the given match using the default pushout approach.
	 * 
	 * @param match
	 *            the match
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final M match) {
		return this.apply(match, this.api.getDefaultPushoutApproach());
	}

	/**
	 * Applies the rule on the given match using the given pushout approach.
	 * 
	 * @param po
	 *            the pushout approach to use
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final PushoutApproach po) {
		Optional<M> match = this.findAnyMatch();
		if (!match.isPresent()) {
			return Optional.empty();
		}
		return this.apply(match.get(), po);
	}

	/**
	 * Applies the rule on the given match using the given pushout approach.
	 * 
	 * @param match
	 *            the match
	 * @param po
	 *            the pushout approach to use
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final M match, final PushoutApproach po) {
		Objects.requireNonNull(match, "The match must not be null!");
		return this.interpreter.apply(match.toIMatch(), po).map(m -> this.convertMatch(m));
	}

	/**
	 * Applies the rule on at most <code>max</code> arbitrary matches using the
	 * default pushout approach.
	 * 
	 * @param max
	 *            the maximal number of rule applications
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final int max) {
		return this.apply(max, this.api.getDefaultPushoutApproach());
	}

	/**
	 * Applies the rule on at most <code>max</code> arbitrary matches using the
	 * given pushout approach.
	 * 
	 * @param count
	 *            the maximal number of rule applications
	 * @param po
	 *            the pushout approach to use
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final int count, final PushoutApproach po) {
		return this.apply(matches -> matches.size() < count && this.hasMatches());
	}

	/**
	 * Applies the rule as long as the given condition is fulfilled using the
	 * default pushout approach.
	 * 
	 * @param condition
	 *            the condition to check, based on the matches of rule applications
	 *            so far
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final Predicate<Collection<M>> condition) {
		return this.apply(condition, this.api.getDefaultPushoutApproach());
	}

	/**
	 * Applies the rule as long as the given condition is fulfilled using the given
	 * pushout approach.
	 * 
	 * @param condition
	 *            the condition to check, based on the matches of rule applications
	 *            so far
	 * @param po
	 *            the pushout approach to use
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final Predicate<Collection<M>> condition, final PushoutApproach po) {
		Collection<M> matches = new ArrayList<M>();
		while (condition.test(matches)) {
			this.apply(po).ifPresent(m -> matches.add(m));
		}
		return matches;
	}

	/**
	 * Applies the rule after binding its parameters to the values given by the
	 * match.
	 * 
	 * @param match
	 *            the match
	 * @return the match after rule application
	 */
	public final Optional<M> bindAndApply(final IMatch match) {
		this.bind(match);
		return this.apply();
	}

	/**
	 * Applies the rule after binding its parameters to the values given by the
	 * match.
	 * 
	 * @param match
	 *            the match
	 * @return the match after rule application
	 */
	public final Optional<M> bindAndApply(final GraphTransformationMatch<?, ?> match) {
		this.bind(match);
		return this.apply();
	}

	/**
	 * Applies the rule after binding its parameters to the values given by the
	 * match the supplier returns as long as the the match is not <code>null</code>.
	 * 
	 * @param matchSupplier
	 *            the supplier for a match whose bindings shall be used
	 * @return the matches after rule application
	 */
	public final Collection<M> bindAndApply(final Supplier<? extends GraphTransformationMatch<?, ?>> matchSupplier) {
		Collection<M> matches = new ArrayList<M>();
		GraphTransformationMatch<?, ?> match = matchSupplier.get();
		while (match != null) {
			this.bind(match);
			this.apply().ifPresent(m -> matches.add(m));
			match = matchSupplier.get();
		}
		return matches;
	}
}
