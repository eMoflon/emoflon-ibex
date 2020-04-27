package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ElementClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;

public class ConflictDetector {

	private INTEGRATE integrate;

	private final Set<ElementClassifier> deleteConflictCandidates = new HashSet<ElementClassifier>(
			Arrays.asList(ElementClassifier.NO_USE, ElementClassifier.PENAL_USE));

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public Set<DeleteConflict> detectDeleteConflicts() {
		Set<DeleteConflict> conflicts = new HashSet<>();
		for (Mismatch mismatch : integrate.getMismatches().values()) {
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
				ModelChanges userChanges = integrate.getUserModelChanges();
				Set<EMFEdge> conflEdges = userChanges.getCreatedEdges(element);
				Set<AttributeChange> conflAttrChanges = userChanges.getAttributeChanges(element);
				if (!conflEdges.isEmpty() || !conflAttrChanges.isEmpty())
					conflictingElements.add(new ConflictingElement(element, conflEdges, conflAttrChanges));
			}
		});

		if (conflictingElements.isEmpty())
			return null;

		return new DeleteConflict(integrate, mismatch.getMatch(), conflictingElements);
	}

}
