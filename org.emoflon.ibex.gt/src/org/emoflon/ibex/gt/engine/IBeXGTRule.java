package org.emoflon.ibex.gt.engine;

import java.util.Iterator;

import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

public abstract class IBeXGTRule<R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>>
		extends IBeXGTPattern<P, M> {

	public final String ruleName;
	public final GTRule rule;
	protected CP coPattern;

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

	/**
	 * Checks whether the rule is applicable.
	 * 
	 * @return <code>true</code> if there is at least one match
	 */
	public boolean isApplicable(boolean doUpdate) {
		return hasMatches(doUpdate);
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

	protected abstract CP createCoPattern();

	public abstract boolean hasProbability();

	public abstract double getProbability(final M match);

	public abstract CM apply(final M match);

	public CM apply(final M match, boolean doUpdate) {
		if (doUpdate)
			gtEngine.updateMatches();

		return apply(match);
	}

	public CM applyAny() {
		Iterator<M> it = getMatches(false).iterator();
		if (it.hasNext())
			return apply(it.next());
		else
			return null;
	}

	public CM applyAny(boolean doUpdate) {
		if (doUpdate)
			gtEngine.updateMatches();

		return applyAny();
	}

	// TODO: This might be interesting in the future!
//	public abstract Optional<M> applyReverse(final CM coMatch);
}
