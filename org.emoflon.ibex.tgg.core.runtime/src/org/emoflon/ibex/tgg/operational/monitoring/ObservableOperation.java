package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.ArrayList;
import java.util.List;

public class ObservableOperation {
	private List<IbexObserver> observers = new ArrayList<IbexObserver>();
	String observerName;
	
	public void setObseverName(String observerName) {
		this.observerName = observerName;
		notifyAllObservers();
	}
	
	public void attach(IbexObserver observer) {
		observers.add(observer);
	}
	
	public void dettach(IbexObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyAllObservers() {
		for(IbexObserver observer : observers) {
			observer.update();
		}
	}
}
