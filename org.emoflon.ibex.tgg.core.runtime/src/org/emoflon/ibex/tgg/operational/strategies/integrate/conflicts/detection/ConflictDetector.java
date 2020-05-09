package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.ElementClassifier;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
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
		for (BrokenMatch brokenMatch : integrate.getBrokenMatches().values()) {
			MatchConflict possibleConflict = detectMatchConflict(brokenMatch);
			if (possibleConflict != null)
				conflicts.add(possibleConflict);
		}
		return conflicts;
	}

	private MatchConflict detectMatchConflict(BrokenMatch brokenMatch) {
		Set<EdgeConflictingElt> edgeConflElts = new HashSet<>();
		Set<AttrConflictingElt> attrConflElts = new HashSet<>();

		brokenMatch.getClassifiedNodes().forEach((element, classifier) -> {
			if (deleteConflictCandidates.contains(classifier)) {
				ModelChanges changes = integrate.getGeneralModelChanges();
				Set<EMFEdge> conflEdges = changes.getCreatedEdges(element);
				Set<AttributeChange> conflAttrChanges = changes.getAttributeChanges(element);
				if (!conflEdges.isEmpty())
					edgeConflElts.add(new EdgeConflictingElt(element, conflEdges));
				if (!conflAttrChanges.isEmpty())
					attrConflElts.add(new AttrConflictingElt(element, conflAttrChanges));
			}
		});

		Set<Conflict> relatedConflicts = new HashSet<>();

		if (!edgeConflElts.isEmpty())
			relatedConflicts.add(new DeletePropEdgeConflict(integrate, brokenMatch.getMatch(), edgeConflElts));
		if (!attrConflElts.isEmpty())
			relatedConflicts.add(new DeletePropAttrConflict(integrate, brokenMatch.getMatch(), attrConflElts));

		if (relatedConflicts.size() > 1)
			return new RelatedConflict(relatedConflicts);
		if (relatedConflicts.size() == 1)
			return relatedConflicts.iterator().next();
		return null;
	}

}
