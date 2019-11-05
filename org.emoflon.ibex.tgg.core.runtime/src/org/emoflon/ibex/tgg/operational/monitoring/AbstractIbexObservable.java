package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.IbexObserver.ObservableEvent;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class AbstractIbexObservable implements IbexObservable {

	private Set<IbexObserver> observers = new HashSet<IbexObserver>();

	private IUpdatePolicy updatePolicy;

	@Override
	public void registerObserver(IbexObserver observer) {
		if (observer != null) {
			getObservers().add(observer);
		}

	}

	@Override
	public void unregisterObserver(IbexObserver observer) {
		if (observer != null) {
			getObservers().remove(observer);
		}
	}

	@Override
	public void notifyStartLoading() {
		getObservers().forEach(o -> o.update(ObservableEvent.STARTLOADING));
	}

	@Override
	public void notifyLoadingFinished() {
		getObservers().forEach(o -> o.update(ObservableEvent.LOADINGFINISHED));
	}

	@Override
	public void notifyStartInit() {
		getObservers().forEach(o -> o.update(ObservableEvent.STARTINIT));
	}

	@Override
	public void notifyDoneInit() {
		getObservers().forEach(o -> o.update(ObservableEvent.DONEINIT));
	}

	@Override
	public void notifyMatchApplied(IMatch match, String ruleName) {
		getObservers().forEach(o -> o.update(ObservableEvent.MATCHAPPLIED, match));
		this.getUpdatePolicy().notifyMatchHasBeenApplied(match, ruleName); // TODO JaneJ update the way this is called
	}

	/**
	 * @return the updatePolicy
	 */
	public IUpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}

	/**
	 * @param updatePolicy
	 *            the updatePolicy to set
	 */
	public void setUpdatePolicy(IUpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}

	@Override
	public IMatch notifyChooseMatch(ImmutableMatchContainer matchContainer) { // tells observer a match needs to be
																				// chosen and choses a match by using
																				// the update policy
		getObservers().forEach(o -> o.update(ObservableEvent.CHOOSEMATCH, matchContainer));
		if (this.updatePolicy == null) {
			throw new RuntimeException("No update strategy configured");
		} else {
			return this.updatePolicy.chooseOneMatch(matchContainer);
		}
	}

	/**
	 * @return the observers
	 */
	public final Set<IbexObserver> getObservers() {
		return observers;
	}
}
