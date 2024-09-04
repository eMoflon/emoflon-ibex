package org.emoflon.ibex.tgg.runtime.monitoring;

import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.matches.container.ImmutableMatchContainer;

/**
 * Classes implementing the IBex Observable interface can be observed by the
 * IbexObservers. Registered Observers get notified upon possible status changes
 * in the observed object.
 * 
 */
public interface IbexObservable {

	/**
	 * Called when loading has started. Notifies observers of this event.
	 */
	public void notifyStartLoading();

	/**
	 * Called when loading has ended. Notifies observers of this event.
	 */
	public void notifyLoadingFinished();

	/**
	 * Called when Initialization has started. Notifies observers of this event.
	 */
	public void notifyStartInit();

	/**
	 * Called when Initialization has ended. Notifies observers of this event.
	 */
	public void notifyDoneInit();

	/**
	 * Called when a match has been successfully applied
	 */
	public void notifyMatchApplied(ITGGMatch match, String ruleName);

	/**
	 * Called when a match needs to be chosen. Notifies observers of this event.
	 */
	public ITGGMatch notifyChooseMatch(ImmutableMatchContainer matchContainer);

	/**
	 * Registers the Observer at the Observable.
	 */
	public void registerObserver(IbexObserver observer);

	/**
	 * Unregisters the Observer at the Observable.
	 */
	public void unregisterObserver(IbexObserver observer);

}
