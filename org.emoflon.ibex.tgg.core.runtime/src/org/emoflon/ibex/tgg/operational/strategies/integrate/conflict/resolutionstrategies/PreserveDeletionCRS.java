package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;

public class PreserveDeletionCRS extends DeleteConflictResStrategy {

	public PreserveDeletionCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public List<Notification> apply(INTEGRATE integrate) {
		List<Notification> undos = new ArrayList<>();
		conflict.getSubjects().forEach(subject -> {
			subject.getAdditions().forEach(n -> undos.addAll(integrate.getModelChangeProtocol().util.undo(n, true)));
			subject.getChanges().forEach(n -> undos.addAll(integrate.getModelChangeProtocol().util.undo(n, true)));
			subject.getCrossRefs().forEach(n -> undos.addAll(integrate.getModelChangeProtocol().util.undo(n, false)));
		});
		return undos;
	}

}
