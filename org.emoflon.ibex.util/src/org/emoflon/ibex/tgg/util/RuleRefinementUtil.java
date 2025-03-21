package org.emoflon.ibex.tgg.util;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class RuleRefinementUtil {
	public static boolean checkInjectivityInSubRule(TGGRule subrule, TGGNode node1, TGGNode node2) {
		if (bothNodesAreOnlyInSuperRules(subrule, node1, node2)) 
			return true;
		else if (oneNodeIsInSubOneInSuperRules(subrule, node1, node2))
			return false;
		else if (node1.getDomainType() != node2.getDomainType()) {
			return false;
		} else {
			// if both are from this rule and from the same domain, they have been checked in context-patterns
			return true;
		}
	}

	private static boolean oneNodeIsInSubOneInSuperRules(TGGRule subrule, TGGNode node1, TGGNode node2) {
		return subrule.getNodes().contains(node1) && !subrule.getNodes().contains(node2) || 
			   subrule.getNodes().contains(node2) && !subrule.getNodes().contains(node1);
	}

	private static boolean bothNodesAreOnlyInSuperRules(TGGRule subrule, TGGNode node1, TGGNode node2) {
		return !subrule.getNodes().contains(node1) && !subrule.getNodes().contains(node2);
	}
}