package org.emoflon.ibex.tgg.operational.monitoring;

import java.util.Set;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import language.TGGRule;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
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
            return op.getOptions().flattenedTGG().getRules().stream().filter(r -> r.getName().equals(pRuleName)).findFirst().get();
        }
        catch (Exception e) {
            logger.error(e);
            return null;
        }
    }
    
    @Override
    public Set<EObject> getMatchNeighbourhood(IMatch match, int k) {
	// TODO: match neighborhood implementation
	return null;
    }
    
    @Override
    public Set<IMatch> getMatches() {
    	try {
    		return op.getMatchContainer().getMatches();
    	}
        catch (Exception e) {
            logger.error(e);
            return null;
        }    	
    }
    
    @Override
    public Set<IMatch> getMatches(String pRuleName) {
    	try {
    		return this.getMatches().stream().filter(r -> r.getRuleName().equals(pRuleName)).collect(Collectors.toSet());
    	}
        catch (Exception e) {
            logger.error(e);
            return null;
        }    	
    }
    
    @Override
    public Set<IMatch> getMatches(IMatch match) {
    	try {
    		return this.getMatches(match.getRuleName());
    	}
        catch (Exception e) {
            logger.error(e);
            return null;
        }
    }    
}

