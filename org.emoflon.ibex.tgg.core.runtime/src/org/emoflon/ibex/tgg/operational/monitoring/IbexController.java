package org.emoflon.ibex.tgg.operational.monitoring;

import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.updatepolicy.IUpdatePolicy;

public abstract class IbexController implements IbexObserver, IUpdatePolicy {

    public void register(OperationalStrategy pOperationalStrategy) {
	pOperationalStrategy.registerObserver(this);
	pOperationalStrategy.setUpdatePolicy(this);
    }
}
