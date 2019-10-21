package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.LinkedList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.TGGRuleNode;
import precedencegraph.Node;

public class DeletionChain extends LinkedList<Pair<IMatch, EObject>>  {
	private static final long serialVersionUID = 1L;
	
	private INTEGRATE integrate;
	
	DeletionChain(INTEGRATE integrate, ImmutablePair<IMatch, EObject> startingPoint) {
		super();
		this.integrate = integrate;
		this.add(startingPoint);
		while(!concludeDeletionChain());
	}

	private boolean concludeDeletionChain() {
		EObject nextElement = this.getLast().getValue().eContainer();
		if (nextElement == null)
			return true;

		Node lastNode = integrate.getEPG().getNode(this.getLast().getKey());
		for (Node subNode : lastNode.getBasedOn()) {
			IMatch subMatch = integrate.getEPG().getMatch(subNode);
			if (!integrate.getAnalysedMatches().containsKey(subMatch))
				continue;

			AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(subMatch);
			TGGRuleNode ruleNode = analysedMatch.getEObjectToNode().get(nextElement);
			if (ruleNode == null)
				continue;

			if (analysedMatch.isRuleEltDeleted(ruleNode)) {
				this.add(new ImmutablePair<>(subMatch, nextElement));
				return false;
			}
		}

		return true;
	}
	
}
