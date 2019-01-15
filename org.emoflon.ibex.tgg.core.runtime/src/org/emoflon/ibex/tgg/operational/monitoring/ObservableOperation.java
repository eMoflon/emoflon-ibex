package org.emoflon.ibex.tgg.operational.monitoring;

public class ObservableOperation {

	public enum Monitor {
		IDLE, MEMORY, SIZEPARRETN;
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
	}
}
