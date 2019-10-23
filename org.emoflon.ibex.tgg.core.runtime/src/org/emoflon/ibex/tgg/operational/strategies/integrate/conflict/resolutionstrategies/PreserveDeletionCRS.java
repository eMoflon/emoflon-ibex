package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.DeleteConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;

public class PreserveDeletionCRS extends DeleteConflictResStrategy {

	public PreserveDeletionCRS(DeleteConflict conflict, ConflResStratToken token) {
		super(conflict, token);
	}

	@Override
	public List<Notification> apply(INTEGRATE integrate) {
		List<Notification> undos = new ArrayList<>();
		conflict.getSubjects().forEach(subject -> {
			subject.getAdditions().forEach(n -> integrate.getModelChangeProtocol().undo(n));
			subject.getChanges().forEach(n -> integrate.getModelChangeProtocol().undo(n));
			subject.getCrossRefs().forEach(n -> integrate.getModelChangeProtocol().undo(n));
			undos.addAll(subject.getAdditions());
			undos.addAll(subject.getChanges());
			undos.addAll(subject.getCrossRefs());
		});
		return undos;
	}

}
