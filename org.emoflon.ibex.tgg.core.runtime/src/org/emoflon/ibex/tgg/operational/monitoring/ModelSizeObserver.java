package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class ModelSizeObserver extends IbexObserver {

	protected Resource s;
	protected Resource t;
	protected Resource c;

	ObservableOperation observableOperation = new ObservableOperation();
	int size;

	protected final static Logger logger = Logger.getLogger(ModelSizeObserver.class);

	public ModelSizeObserver(ObservableOperation observableOperation) {
		this.observableOperation = observableOperation;
		this.observableOperation.attach(this);
	}

	@Override
	public void update() {
		logger.info("Size of input models is " + this.size + " elements");
		logger.info("***********************");

	}

	public void getResources(Resource s, Resource c, Resource t) {
		getNumberOfObjectsInModels(s, c, t);
	}

	public int getNumberOfObjectsInModels(Resource s, Resource c, Resource t) {
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
		size = sizeS + sizeC + sizeT;
		return size;
	}

}
