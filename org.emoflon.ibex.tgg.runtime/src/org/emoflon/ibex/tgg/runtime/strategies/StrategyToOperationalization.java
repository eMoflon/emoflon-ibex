package org.emoflon.ibex.tgg.runtime.strategies;

import java.util.Collection;
import java.util.LinkedList;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;

public class StrategyToOperationalization {

	public static Collection<OperationalisationMode> getNeededOperationalisationModes(StrategyMode mode) {
		Collection<OperationalisationMode> modes = new LinkedList<>();
		switch(mode) {
		case MODELGEN:
			modes.add(OperationalisationMode.GENERATE);
			break;
		case INITIAL_FWD:
			modes.add(OperationalisationMode.FORWARD);
			break;
		case INITIAL_BWD:
			modes.add(OperationalisationMode.BACKWARD);
			break;
		case FWD_OPT:
			modes.add(OperationalisationMode.FORWARD);
			break;
		case BWD_OPT:
			modes.add(OperationalisationMode.BACKWARD);
			break;
		case SYNC:
			modes.add(OperationalisationMode.FORWARD);
			modes.add(OperationalisationMode.BACKWARD);
			modes.add(OperationalisationMode.CONSISTENCY);
			break;
		case INTEGRATE:
			modes.add(OperationalisationMode.SOURCE);
			modes.add(OperationalisationMode.TARGET);
			modes.add(OperationalisationMode.FORWARD);
			modes.add(OperationalisationMode.BACKWARD);
			modes.add(OperationalisationMode.CONSISTENCY);
			modes.add(OperationalisationMode.CONSISTENCY_CHECK);
			break;
		case CC:
			modes.add(OperationalisationMode.CONSISTENCY_CHECK);
			modes.add(OperationalisationMode.CONSISTENCY);
			break;
		case CHECK_ONLY:
			modes.add(OperationalisationMode.CHECK_ONLY);
			break;
		default:
			break;
		}
		return modes;
	}
	
}
