package org.emoflon.ibex.gt.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * This is the abstraction for all applicable rules. Via <code>apply()</code>
 * and its variants, rules can be applied.
 * 
 * If no pushout approach is set for the rule before calling an
 * <code>apply</code> method, the pushout approach of the API which created the
 * rule will be used.
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
	 * The consumer for the subscription for enableAutoApply().
	 */
	private Optional<Consumer<M>> autoApply = Optional.empty();

	/**
	 * The pushout approach for the rule.
	 */
	private Optional<PushoutApproach> pushoutApproach = Optional.empty();

	/**
	 * Creates a new executable rule.
	 * 
	 * Per default, the pushout approach which is set on API level is used.
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
	 * Returns the pushout approach. If the pushout approach has not been set for
	 * the rule, the pushout approach defaults to the one set for the API.
	 * 
	 * @return the pushout approach
	 */
	public final PushoutApproach getPushoutApproach() {
		return this.pushoutApproach.orElse(this.api.getDefaultPushoutApproach());
	}

	/**
	 * Sets the pushout approach for the rule.
	 * 
	 * @param pushoutApproach
	 *            the pushout approach
	 */
	@SuppressWarnings("unchecked")
	public final R setPushoutApproach(final PushoutApproach pushoutApproach) {
		Objects.requireNonNull(pushoutApproach, "Pushout approach must not be null!");
		this.pushoutApproach = Optional.of(pushoutApproach);
		return (R) this;
	}

	/**
	 * Sets the pushout approach for the rule to double pushout.
	 */
	public final R setDPO() {
		return this.setPushoutApproach(PushoutApproach.DPO);
	}

	/**
	 * Sets the pushout approach for the rule to single pushout.
	 */
	public final R setSPO() {
		return this.setPushoutApproach(PushoutApproach.SPO);
	}

	/**
	 * Applies the rule on an arbitrary match if a match can be found.
	 * 
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply() {
		Optional<M> match = this.findAnyMatch();
		if (!match.isPresent()) {
			return Optional.empty();
		}
		return this.apply(match.get());
	}

	/**
	 * Applies the rule on the given match.
	 * 
	 * @param match
	 *            the match
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final M match) {
		Objects.requireNonNull(match, "The match must not be null!");
		return this.interpreter.apply(match.toIMatch(), this.getPushoutApproach()).map(m -> this.convertMatch(m));
	}

	/**
	 * Applies the rule on at most <code>max</code> arbitrary matches.
	 * 
	 * @param max
	 *            the maximal number of rule applications
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final int max) {
		return this.apply(matches -> matches.size() < max && this.hasMatches());
	}

	/**
	 * Applies the rule as long as the given condition is fulfilled.
	 * 
	 * @param condition
	 *            the condition to check, based on the matches of successful rule
	 *            applications so far
	 * @return the matches after rule application
	 */
	public final Collection<M> apply(final Predicate<Collection<M>> condition) {
		Collection<M> matches = new ArrayList<M>();
		while (condition.test(matches)) {
			this.apply().ifPresent(m -> matches.add(m));
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

	/**
	 * Applies the rule automatically whenever a match is found.
	 * 
	 * Note: This will not work for rules which are contain only created elements as
	 * the rule is always applicable in this case.
	 */
	public final void enableAutoApply() {
		this.autoApply.ifPresent(c -> this.unsubscribeAppearing(c));
		this.autoApply = Optional.of(m -> this.apply(m));
		this.subscribeAppearing(autoApply.get());
	}

	/**
	 * Stops automatic rule application whenever a match is found.
	 * 
	 * @throws IllegalArgumentException
	 *             if <code>applyWheneverApplicable</code> has not been called
	 *             before
	 */
	public final void disableAutoApply() {
		if (!this.autoApply.isPresent()) {
			throw new IllegalArgumentException("Cannot stop applyWheneverApplicable before start.");
		}
		this.unsubscribeAppearing(autoApply.get());
		this.autoApply = Optional.empty();
	}
}
