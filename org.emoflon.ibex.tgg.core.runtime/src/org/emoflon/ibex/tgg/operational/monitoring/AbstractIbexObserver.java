package org.emoflon.ibex.tgg.operational.monitoring;

public abstract class AbstractIbexObserver implements IbexObserver {
	
	private final IbexObservable observable;
	
	public AbstractIbexObserver(IbexObservable observable) {
		this.observable = observable;
		observable.registerObserver(this);
	}

	/**
	 * @return the observable
	 */
	protected IbexObservable getObservable() {
		return observable;
	}

}
