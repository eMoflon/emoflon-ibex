package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

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

		return undos;
	}

}
