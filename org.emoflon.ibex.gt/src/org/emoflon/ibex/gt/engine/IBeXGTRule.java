package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.emoflon.ibex.common.operational.PushoutApproach;
import org.emoflon.ibex.gt.api.IBeXGtAPI;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTPattern;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTRule;

public abstract class IBeXGTRule<R extends IBeXGTRule<R, P, M, CP, CM>, P extends IBeXGTPattern<P, M>, M extends IBeXGTMatch<M, P>, CP extends IBeXGTCoPattern<CP, CM, R, P, M>, CM extends IBeXGTCoMatch<CM, CP, R, P, M>>
		extends IBeXGTPattern<P, M> {

	public final String ruleName;
	protected final GTRule rule;
	protected CP coPattern;

	/**
	 * The pushout approach for the rule.
	 */
	protected Optional<PushoutApproach> pushoutApproach = Optional.empty();

	/**
	 * The number of rule applications until now.
	 */
	protected int ruleApplicationCount = 0;

	public IBeXGTRule(IBeXGtAPI<?, ?, ?> api, GTRule rule) {
		super(api, (GTPattern) rule.getPrecondition());
		this.ruleName = rule.getName();
		this.rule = rule;
		gtEngine.registerTypedRule(this);
		coPattern = createCoPattern();
	}

	/**
	 * Returns the pushout approach. If the pushout approach has not been set for
	 * the rule, the pushout approach defaults to the one set for the API.
	 * 
	 * @return the pushout approach
	 */
	public PushoutApproach getPushoutApproach() {
		return pushoutApproach.orElse(gtEngine.getDefaultPushoutApproach());
	}

	/**
	 * Sets the pushout approach for the rule.
	 * 
	 * @param pushoutApproach the pushout approach
	 */
	public void setPushoutApproach(final PushoutApproach pushoutApproach) {
		this.pushoutApproach = Optional.of(pushoutApproach);
	}

	/**
	 * Sets the pushout approach for the rule to double pushout (see
	 * {@link PushoutApproach}).
	 */
	public void setDPO() {
		setPushoutApproach(PushoutApproach.DPO);
	}

	/**
	 * Sets the pushout approach for the rule to single pushout (see
	 * {@link PushoutApproach}).
	 */
	public void setSPO() {
		setPushoutApproach(PushoutApproach.SPO);
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

	public abstract Optional<CM> apply(final M match);

	// TODO: This might be interesting in the future!
//	public abstract Optional<M> applyReverse(final CM coMatch);
}
