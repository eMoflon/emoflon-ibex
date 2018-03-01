package org.emoflon.ibex.gt.api;

import java.util.Optional;

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
	 * Executes the rule on the given match.
	 * 
	 * @return an {@link Optional} for the the co-match after rule application
	 */
	public final Optional<M> apply(final M match) {
		return this.interpreter.execute(match.toIMatch()).map(m -> this.convertMatch(m));
	}

	/**
	 * Executes the rule on an arbitrary match if there is a match.
	 * 
	 * @return an {@link Optional} for the the co-match after rule application
	 */
	public final Optional<M> apply() {
		Optional<M> match = this.findAnyMatch();
		if (!match.isPresent()) {
			return Optional.empty();
		}
		return this.apply(match.get());
	}

}
