package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.matches.ImmutableMatchContainer;
import org.emoflon.ibex.tgg.operational.monitoring.IbexObserver.ObservableEvent;
import org.emoflon.ibex.tgg.operational.updatepolicy.UpdatePolicy;

public abstract class AbstractIbexObservable implements IbexObservable {
	
	private Set<IbexObserver> observers = new HashSet<IbexObserver>();
	
	private UpdatePolicy updatePolicy;
	
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
	
	

	/**
	 * @return the updatePolicy
	 */
	public UpdatePolicy getUpdatePolicy() {
		return updatePolicy;
	}

	/**
	 * @param updatePolicy the updatePolicy to set
	 */
	public void setUpdatePolicy(UpdatePolicy updatePolicy) {
		this.updatePolicy = updatePolicy;
	}

	@Override
	public IMatch notifyChooseMatch(ImmutableMatchContainer matchContainer) {
		if(this.updatePolicy == null) {
			throw new RuntimeException("No update strategy configured");
		} else {
			return this.updatePolicy.chooseOneMatch(matchContainer);
		}
	}
}
