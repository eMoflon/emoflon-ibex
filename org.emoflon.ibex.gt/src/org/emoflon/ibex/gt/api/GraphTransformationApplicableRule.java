package org.emoflon.ibex.gt.api;

import java.util.Objects;
import java.util.Optional;

import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;

/**
 * This is the abstraction for all applicable rules.
 * 
 * Concrete Implementations must have a constructor to set the parameters
 * required for rule application and getters for all parameters.
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
	 * @param interpreter
	 *            the interpreter
	 * @param ruleName
	 *            the name of the rule
	 */
	public GraphTransformationApplicableRule(GraphTransformationInterpreter interpreter, String ruleName) {
		super(interpreter, ruleName);
	}

	/**
	 * Applies the rule on an arbitrary match with SPO semantics if a match can be
	 * found.
	 * 
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply() {
		return this.apply(PushoutApproach.SPO);
	}

	/**
	 * Applies the rule on the given match with SPO semantics.
	 * 
	 * @param match
	 *            the match
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final M match) {
		return this.apply(match, PushoutApproach.SPO);
	}

	/**
	 * Applies the rule on the given match with the given pushout semantics.
	 * 
	 * @param po
	 *            the pushout semantics
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
	 * Applies the rule on the given match with the given pushout semantics.
	 * 
	 * @param match
	 *            the match
	 * @param po
	 *            the pushout semantics
	 * @return an {@link Optional} for the the match after rule application
	 */
	public final Optional<M> apply(final M match, final PushoutApproach po) {
		Objects.requireNonNull(match, "The match must not be null!");
		return this.interpreter.apply(match.toIMatch(), po).map(m -> this.convertMatch(m));
	}

}
