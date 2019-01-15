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
	
	/*public enum Monitor {
		IDLE, MEMORY;
	};
	
	Monitor monitor;
	
	public ObservableOperation(Monitor monitor) {
		this.monitor = monitor;
	}
	
	public Monitor getMonitor() {
		switch(monitor) {
		case MEMORY:
			new MemoryConsumption().getMemoryConsumed();			
		case IDLE:
			setMonitor();
		case SIZEPARRETN:
			new GeneratedPatternsSize().getGeneratedPatternsSize();
		}
		return monitor;
	}
	
	public void setMonitor() {
		System.out.println("Idle Monitor");
	}*/
	
	
}
