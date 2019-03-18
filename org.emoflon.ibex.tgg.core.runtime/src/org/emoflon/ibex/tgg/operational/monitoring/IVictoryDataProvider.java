package org.emoflon.ibex.tgg.operational.monitoring;

import language.TGGRule;

public interface IVictoryDataProvider {
    public TGGRule getRule(String pRuleName);
}
