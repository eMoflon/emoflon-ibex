package org.emoflon.ibex.tgg.operational.monitoring;

import org.apache.log4j.Logger;
import language.TGGRule;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

public class VictoryDataProvider implements IVictoryDataProvider {
    
    private final static Logger logger = Logger.getLogger(VictoryDataProvider.class);
    
    OperationalStrategy op;
    public VictoryDataProvider(OperationalStrategy pOperationalStrategy) {
        this.op = pOperationalStrategy;
    }

    @Override
    public TGGRule getRule(String pRuleName) {
        try {
            return op.getTGG().getRules().stream().filter(r -> r.getName().equals(pRuleName)).findFirst().get();
        }
        catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
}