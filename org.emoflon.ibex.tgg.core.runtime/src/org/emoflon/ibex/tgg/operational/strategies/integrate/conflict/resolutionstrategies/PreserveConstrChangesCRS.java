package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.AnalysedMatch.EltFilter;

import language.TGGRuleNode;

public class PreserveConstrChangesCRS extends DeleteConflictResStrategy {

	public PreserveConstrChangesCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public List<Notification> apply(INTEGRATE integrate) {
		List<Notification> undos = new ArrayList<>();

		conflict.getDeletionChain().foreach(m -> {
			undos.addAll(restoreMatch(integrate, m));
		});

		AnalysedMatch analysedMatch = integrate.getAnalysedMatches().get(conflict.getMatch());
		analysedMatch.getElts(new EltFilter().srcAndTrg().create().deleted()).forEach(elt -> {
			if (elt instanceof TGGRuleNode) {
				EObject eObj = (EObject) conflict.getMatch().get(elt.getName());
				eObj.eContents().forEach(c -> {
					undos.addAll(integrate.getModelChangeProtocol().util.deleteNode(c));
				});
			}
		});

		return undos;
	}

}
