package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;

import language.BindingType;
import language.DomainType;
import language.TGGRuleCorr;

public abstract class MatchIntegrationFragment { // TODO adrianm: rename to MatchClassificationComponent

	abstract public boolean softApply(AnalysedMatch analysedMatch, INTEGRATE integrate);

	abstract public boolean hardApply(AnalysedMatch analysedMatch, INTEGRATE integrate);
	
	abstract public boolean isApplicable(AnalysedMatch analysedMatch);

	protected IbexRedInterpreter getIbexRedInterpreter(INTEGRATE integrate) {
		try {
			return (IbexRedInterpreter) integrate.getRedInterpreter();
		} catch (Exception e) {
			throw new RuntimeException("IbexRedInterpreter implementation is needed", e);
		}
	}

	/**
	 * <p>
	 * Determines green correspondence elements and adds them to the passed sets
	 * (nodesToRevoke & edgesToRevoke).
	 * </p>
	 * <p>
	 * To delete this elements call
	 * {@link IbexRedInterpreter#revoke(nodesToRevoke, edgesToRevoke)}.
	 * </p>
	 * 
	 * @param analysedMatch Analyzed IMatch
	 * @param interpreter
	 * @param nodesToRevoke
	 * @param edgesToRevoke
	 */
	protected void prepareGreenCorrDeletion(AnalysedMatch analysedMatch, IbexRedInterpreter interpreter,
			Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		analysedMatch.getGroupedElements().get(DomainType.CORR).get(BindingType.CREATE).stream() //
				.filter(e -> e instanceof TGGRuleCorr) //
				.map(c -> (EObject) analysedMatch.getMatch().get(c.getName())) //
				.forEach(c -> interpreter.revokeCorr(c, nodesToRevoke, edgesToRevoke));
	}

	/**
	 * Deletes all green correspondence elements.
	 * 
	 * @param analysedMatch Analyzed IMatch
	 * @param interpreter   RedInterpreter that performs the deletion
	 */
	protected void delGreenCorr(AnalysedMatch analysedMatch, IbexRedInterpreter interpreter) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();
		prepareGreenCorrDeletion(analysedMatch, interpreter, nodesToRevoke, edgesToRevoke);
		interpreter.revoke(nodesToRevoke, edgesToRevoke);
	}

}
