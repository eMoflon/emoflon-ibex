package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

public class ModelSizeObserver extends AbstractIbexObserver {

	private final static Logger logger = Logger.getLogger(ModelSizeObserver.class);
	private int currentSize;

	public ModelSizeObserver(IbexObservable observable) {
		super(observable);
	}

	@Override
	public void update(ObservableEvent eventType, Object... additionalInformation) {
		switch (eventType) {
		case DONEINIT:
		case MATCHAPPLIED:
			if (this.getObservable() instanceof OperationalStrategy) {
				OperationalStrategy op = (OperationalStrategy) this.getObservable();
				TGGResourceHandler rHandler = op.getOptions().resourceHandler();
				this.currentSize = this.getNumberOfObjectsInModels(rHandler.getSourceResource(), rHandler.getCorrResource(),
						rHandler.getTargetResource());
				logger.info("Size of input models is " + this.currentSize + " elements");
				logger.info("***********************");
				break;
			}
		default:
			break;
		}
	}

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
