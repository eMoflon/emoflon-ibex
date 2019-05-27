package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.IFPattern;

public class DEL extends MatchIntegrationFragment {

	private final IFPattern pattern = IFPattern.DEL;

	@Override
	public boolean apply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		if (!pattern.matches(analysedMatch.getModPattern()))
			return false;
		
		IbexRedInterpreter interpreter = getIbexRedInterpreter(integrate);

		// Delete green corr nodes
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		delGreenCorr(analysedMatch, interpreter, nodesToRevoke, edgesToRevoke);

		interpreter.revoke(nodesToRevoke, edgesToRevoke);

		return true;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return pattern.matches(analysedMatch.getModPattern());
	}

}
