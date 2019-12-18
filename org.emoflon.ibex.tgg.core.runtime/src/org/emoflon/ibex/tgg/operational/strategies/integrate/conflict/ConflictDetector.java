package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE.INTEGRATE_Op;
import org.emoflon.ibex.tgg.operational.strategies.integrate.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.EltClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;

public class ConflictDetector {

	private INTEGRATE_Op integrate;

	private final Set<EltClassifier> deleteConflictCandidates = new HashSet<EltClassifier>(
			Arrays.asList(EltClassifier.NO_USE, EltClassifier.PENAL_USE));

	public ConflictDetector(INTEGRATE_Op integrate) {
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
