package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
/**
 * This observer class gives the size of the input and output model
 *
 */
public class ModelSizeObserver extends AbstractIbexObserver {

	private final static Logger logger = Logger.getLogger(ModelSizeObserver.class);
	private int currentSize;

	public ModelSizeObserver(IbexObservable observable) {
		super(observable);
	}
	/**
	 * Called whenever the observer object is changed. Calculates and sets the currentSize of the input and output model
	 * Calculates the model size on event MATCHAPPLIED of {@link ObservableEvent}
	 */
	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch(eventType) {
			case DONEINIT: 
			case MATCHAPPLIED:
				if(this.getObservable() instanceof OperationalStrategy) {
					OperationalStrategy op = (OperationalStrategy) this.getObservable();
					this.currentSize = this.getNumberOfObjectsInModels(op.getSourceResource(), op.getCorrResource(), op.getTargetResource());
					logger.info("Size of input models is " + this.currentSize + " elements");
					logger.info("***********************");
					break;
				}
		default:
			break;	
		}
	}
/**
 * Returns the size of the model. It uses Source, Target and Correspodence model to calculate 
 * the size of the model. Adds the size of all three models to get the size of the whole model
 * @param s type of Source Resource
 * @param c type of Corr Resource
 * @param t type of Target Resource
 * @return currentSize
 */
	private int getNumberOfObjectsInModels(Resource s, Resource c, Resource t) {
		int sizeS = 0;
		TreeIterator<EObject> treeIterator = s.getAllContents();
		while (treeIterator.hasNext()) {
			treeIterator.next();
			sizeS++;
		}
		int sizeC = 0;
		treeIterator = c.getAllContents();
		while (treeIterator.hasNext()) {
			treeIterator.next();
			sizeC++;
		}
		int sizeT = 0;
		treeIterator = t.getAllContents();
		while (treeIterator.hasNext()) {
			treeIterator.next();
			sizeT++;
		}
		return sizeS + sizeC + sizeT;
	}
	
	public int getCurrentSize() {
		return currentSize;
	}

}
