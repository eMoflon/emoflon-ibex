package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;

public class ConflictDetector {

	private INTEGRATE integrate;

	private final Set<EltClassifier> deleteConflictCandidates = new HashSet<EltClassifier>(
			Arrays.asList(EltClassifier.NO_USE, EltClassifier.PENAL_USE));

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public List<DeleteConflict> detectDeleteConflicts() {
		List<DeleteConflict> conflicts = new ArrayList<>();
		for (Mismatch mismatch : integrate.getMismatches()) {
			DeleteConflict possibleConflict = detectDeleteConflict(mismatch);
			if (possibleConflict != null)
				conflicts.add(possibleConflict);
		}
		return conflicts;
	}

	private DeleteConflict detectDeleteConflict(Mismatch mismatch) {
		Set<ConflictingElement> conflictingElements = new HashSet<>();

		mismatch.getClassifiedNodes().forEach((element, classifier) -> {
			if (deleteConflictCandidates.contains(classifier)) {
				List<Notification> conflAdditions = integrate.getModelChangeProtocol().getAdditions(element);
				List<Notification> conflChanges = integrate.getModelChangeProtocol().getChanges(element);
				List<Notification> conflCrossRefs = getConflictingCrossRefs(element);
				if (!conflAdditions.isEmpty() || !conflChanges.isEmpty() || !conflCrossRefs.isEmpty())
					conflictingElements
							.add(new ConflictingElement(element, conflAdditions, conflChanges, conflCrossRefs));
			}
		});

		if (conflictingElements.isEmpty())
			return null;

		return new DeleteConflict(integrate, mismatch.getMatch(), conflictingElements);
	}

	private List<Notification> getConflictingCrossRefs(EObject element) {
		List<Notification> crossRefs = new ArrayList<>();
		crossRefs.addAll(integrate.getModelChangeProtocol().getReverseAdditions(element));
		crossRefs.addAll(integrate.getModelChangeProtocol().getReverseChanges(element));
		return crossRefs;
		// TODO adrianm: include cross refs not captured by eContentAdapter
	}

}
