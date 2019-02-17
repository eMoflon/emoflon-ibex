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
		if(observer!= null)
		{
			observers.add(observer);
		}
		
	}
	
	@Override
	public void unregisterObserver(IbexObserver observer) {
		if(observer!= null)
		{
			observers.remove(observer);
		}
	}

	@Override
	public void notifyStartLoading() {
		observers.forEach(o -> o.update(ObservableEvent.STARTLOADING));
	}
	
	
	@Override
	public void notifyLoadingFinished() {
		observers.forEach(o -> o.update(ObservableEvent.LOADINGFINISHED));
	}
	

	@Override
	public void notifyStartInit() {
		observers.forEach(o -> o.update(ObservableEvent.STARTINIT));
	}
	
	
	@Override
	public void notifyDoneInit() {
		observers.forEach(o -> o.update(ObservableEvent.DONEINIT));
	}

	@Override
	public void notifyMatchApplied(IMatch match) {
		observers.forEach(o -> o.update(ObservableEvent.MATCHAPPLIED));
		
	}

	/**
	 * @return the updatePolicy
	 */
	public IUpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}

	/**
	 * @param updatePolicy the updatePolicy to set
	 */
	public void setUpdatePolicy(IUpdatePolicy updatePolicy) {
		if (updatePolicy == null)
			throw new NullPointerException("UpdatePolicy must not be set to null.");
		else
			this.updatePolicy = updatePolicy;
	}

	@Override
	public IMatch notifyChooseMatch(ImmutableMatchContainer matchContainer) {
		observers.forEach(o -> o.update(ObservableEvent.CHOOSEMATCH, matchContainer));
		if(this.updatePolicy == null) {
			throw new RuntimeException("No update strategy configured");
		} else {
			return this.updatePolicy.chooseOneMatch(matchContainer);
		}
	}
}
