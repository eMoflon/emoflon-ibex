package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ElementClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.Mismatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.MatchConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.RelatedConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.EdgeConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;

public class ConflictDetector {

	private INTEGRATE integrate;

	private final Set<ElementClassifier> deleteConflictCandidates = new HashSet<ElementClassifier>(
			Arrays.asList(ElementClassifier.NO_USE, ElementClassifier.PENAL_USE));

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public Set<GeneralConflict> detectConflicts() {
		Set<GeneralConflict> conflicts = new HashSet<>();
		for (Mismatch mismatch : integrate.getMismatches().values()) {
			MatchConflict possibleConflict = detectMatchConflict(mismatch);
			if (possibleConflict != null)
				conflicts.add(possibleConflict);
		}
		return conflicts;
	}

	private MatchConflict detectMatchConflict(Mismatch mismatch) {
		Set<EdgeConflictingElt> edgeConflElts = new HashSet<>();
		Set<AttrConflictingElt> attrConflElts = new HashSet<>();

		mismatch.getClassifiedNodes().forEach((element, classifier) -> {
			if (deleteConflictCandidates.contains(classifier)) {
				ModelChanges userChanges = integrate.getUserModelChanges();
				Set<EMFEdge> conflEdges = userChanges.getCreatedEdges(element);
				Set<AttributeChange> conflAttrChanges = userChanges.getAttributeChanges(element);
				if (!conflEdges.isEmpty())
					edgeConflElts.add(new EdgeConflictingElt(element, conflEdges));
				if (!conflAttrChanges.isEmpty())
					attrConflElts.add(new AttrConflictingElt(element, conflAttrChanges));
			}
		});

		Set<Conflict> relatedConflicts = new HashSet<>();

		if (!edgeConflElts.isEmpty())
			relatedConflicts.add(new DeletePropEdgeConflict(integrate, mismatch.getMatch(), edgeConflElts));
		if (!attrConflElts.isEmpty())
			relatedConflicts.add(new DeletePropAttrConflict(integrate, mismatch.getMatch(), attrConflElts));

		if (relatedConflicts.size() > 1)
			return new RelatedConflict(relatedConflicts);
		if (relatedConflicts.size() == 1)
			return relatedConflicts.iterator().next();
		return null;
	}

}
