package org.emoflon.ibex.tgg.operational.strategies.integrate.fragments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.defaults.IbexRedInterpreter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.IFPattern;

import language.BindingType;
import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class DELProp extends MatchIntegrationFragment {

	private final IFPattern fwd = IFPattern.DEL_PROP_FWD;
	private final IFPattern bwd = IFPattern.DEL_PROP_BWD;

	@Override
	public boolean apply(AnalysedMatch analysedMatch, INTEGRATE integrate) {
		DomainType domainToBeDel;
		if (fwd.matches(analysedMatch.getModPattern())) {
			domainToBeDel = DomainType.TRG;
		} else if (bwd.matches(analysedMatch.getModPattern())) {
			domainToBeDel = DomainType.SRC;
		} else
			return false;

		IbexRedInterpreter interpreter = getIbexRedInterpreter(integrate);

		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		delGreenCorr(analysedMatch, interpreter, nodesToRevoke, edgesToRevoke);

		// Propagate deletion
		List<TGGRuleElement> toBeDeleted = analysedMatch.getGroupedElements().get(domainToBeDel)
				.get(BindingType.CREATE);
		toBeDeleted.stream() //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(n -> (EObject) analysedMatch.getMatch().get(n.getName())) //
				.forEach(o -> nodesToRevoke.add(o));
		toBeDeleted.stream() //
				.filter(el -> el instanceof TGGRuleEdge) //
				.map(el -> (TGGRuleEdge) el) //
				.map(e -> {
					EObject src = (EObject) analysedMatch.getMatch().get(e.getSrcNode().getName());
					EObject trg = (EObject) analysedMatch.getMatch().get(e.getTrgNode().getName());
					EReference ref = e.getType();
					return new EMFEdge(src, trg, ref);
				}) //
				.forEach(o -> edgesToRevoke.add(o));

		interpreter.revoke(nodesToRevoke, edgesToRevoke);

		return true;
	}

	@Override
	public boolean isApplicable(AnalysedMatch analysedMatch) {
		return fwd.matches(analysedMatch.getModPattern()) || bwd.matches(analysedMatch.getModPattern());
	}

}
