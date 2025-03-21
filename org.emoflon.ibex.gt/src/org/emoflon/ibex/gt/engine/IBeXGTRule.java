package org.emoflon.ibex.gt.engine;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

public abstract class IBeXGTRule<R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>>
		extends IBeXGTPattern<P, M> {

	public final String ruleName;
	public final GTRule rule;
	protected CP coPattern;

	protected Set<BiConsumer<M, CM>> subscriptions = Collections.synchronizedSet(new LinkedHashSet<>());

	/**
	 * The number of rule applications until now.
	 */
	protected int ruleApplicationCount = 0;

	public IBeXGTRule(final IBeXGtAPI<? extends IBeXGTPatternMatcher<?>, ?, ?> api, GTRule rule) {
		super(api, (GTPattern) rule.getPrecondition());
		this.ruleName = rule.getName();
		this.rule = rule;
		gtEngine.registerTypedRule(this);
		coPattern = createCoPattern();
	}

	protected abstract CP createCoPattern();

	public abstract boolean hasProbability();

	public abstract double getProbability(final M match);

	protected abstract CM applyInternal(final M match);

	/**
	 * Checks whether the rule is applicable.
	 * 
	 * @return <code>true</code> if there is at least one match
	 */
	public boolean isApplicable(boolean doUpdate) {
		return hasMatches(doUpdate);
	}

	public boolean isApplicable() {
		return hasMatches();
	}

	/**
	 * Returns the number of successful rule applications, i. e. apply() returned an
	 * Optional which was not empty.
	 * 
	 * @return the number of successful rule applications
	 */
	public int countRuleApplications() {
		return ruleApplicationCount;
	}

	public CM apply(final M match) {
		if (gtEngine.alwaysUpdatePrior)
			gtEngine.updateMatches();

		final CM coMatch = applyInternal(match);
		if (gtEngine.alwaysUpdateAfter)
			gtEngine.updateMatches();

		invokeSubscribers(match, coMatch);

		return coMatch;
	}

	public CM apply(final M match, boolean doUpdate) {
		if (doUpdate)
			gtEngine.updateMatches();

		final CM coMatch = applyInternal(match);
		if (gtEngine.alwaysUpdateAfter)
			gtEngine.updateMatches();

		invokeSubscribers(match, coMatch);

		return coMatch;
	}

	public CM applyAny() {
		if (gtEngine.alwaysUpdatePrior)
			gtEngine.updateMatches();

		Iterator<M> it = getMatches(false).iterator();

		if (it.hasNext()) {
			final M match = it.next();
			final CM coMatch = applyInternal(match);
			if (gtEngine.alwaysUpdateAfter)
				gtEngine.updateMatches();

			invokeSubscribers(match, coMatch);

			return coMatch;
		} else {
			return null;
		}

	}

	public CM applyAny(boolean doUpdate) {
		if (doUpdate)
			gtEngine.updateMatches();

		Iterator<M> it = getMatches(false).iterator();

		if (it.hasNext()) {
			final M match = it.next();
			final CM coMatch = applyInternal(match);
			if (gtEngine.alwaysUpdateAfter)
				gtEngine.updateMatches();

			invokeSubscribers(match, coMatch);

			return coMatch;
		} else {
			return null;
		}
	}

	public Set<CM> applyAsLongAsPossible(int upperBound) {
		boolean prior = gtEngine.isAlwaysUpdatePrior();
		boolean after = gtEngine.isAlwaysUpdateAfter();
		gtEngine.setAlwaysUpdatePrior(false);
		gtEngine.setAlwaysUpdateAfter(true);

		Set<CM> results = Collections.synchronizedSet(new LinkedHashSet<>());
		while (upperBound > 0 && hasMatches(true)) {
			upperBound--;
			final CM coMatch = applyAny();
			if (coMatch != null)
				results.add(coMatch);
		}

		gtEngine.setAlwaysUpdatePrior(prior);
		gtEngine.setAlwaysUpdateAfter(after);

		return results;
	}

	public BiConsumer<M, CM> subscribeApplications(final BiConsumer<M, CM> consumer) {
		if (subscriptions.add(consumer))
			return consumer;

		return null;
	}

	public BiConsumer<M, CM> unsubscribeApplications(final BiConsumer<M, CM> consumer) {
		if (subscriptions.remove(consumer))
			return consumer;

		return null;
	}

	public Set<BiConsumer<M, CM>> unsubscribeApplications() {
		Set<BiConsumer<M, CM>> subscriptions = Collections.synchronizedSet(new LinkedHashSet<>(this.subscriptions));
		this.subscriptions.clear();
		return subscriptions;
	}

	protected void invokeSubscribers(final M match, final CM coMatch) {
		if (subscriptions.isEmpty())
			return;

		for (BiConsumer<M, CM> consumer : subscriptions) {
			consumer.accept(match, coMatch);
		}
	}

	// TODO: This might be interesting in the future!
//	public abstract Optional<M> applyReverse(final CM coMatch);
}
