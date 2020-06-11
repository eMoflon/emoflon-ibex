package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.Conflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DeletePropEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.GeneralConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.MatchConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.PartlyDelConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.RelatedConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.EdgeConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeExpression;

public class ConflictDetector {

	protected final static Logger logger = Logger.getLogger(ConflictDetector.class);

	private INTEGRATE integrate;

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public Set<GeneralConflict> detectConflicts() {
		Set<GeneralConflict> conflicts = new HashSet<>();
		for (BrokenMatch brokenMatch : integrate.getClassifiedBrokenMatches().values()) {
			MatchConflict possibleMatchConflict = detectMatchConflict(brokenMatch);
			if (possibleMatchConflict != null)
				conflicts.add(possibleMatchConflict);
			conflicts.addAll(detectAttributeConflict(brokenMatch));
		}
		return conflicts;
	}

	private MatchConflict detectMatchConflict(BrokenMatch brokenMatch) {
		EltFilter filter = new EltFilter().create().notDeleted();
		if (DeletionType.getPropFWDCandidates().contains(brokenMatch.getDeletionType()))
			filter.trg();
		else if (DeletionType.getPropBWDCandidates().contains(brokenMatch.getDeletionType()))
			filter.src();
		else
			return null;

		Set<EdgeConflictingElt> edgeConflElts = new HashSet<>();
		Set<AttrConflictingElt> attrConflElts = new HashSet<>();
		ModelChanges changes = integrate.getGeneralModelChanges();

		integrate.getMatchUtil().getObjects(brokenMatch.getMatch(), filter).forEach(element -> {
			Set<EMFEdge> conflEdges = changes.getCreatedEdges(element);
			Set<AttributeChange> conflAttrChanges = changes.getAttributeChanges(element);
			if (!conflEdges.isEmpty())
				edgeConflElts.add(new EdgeConflictingElt(element, conflEdges));
			if (!conflAttrChanges.isEmpty())
				attrConflElts.add(new AttrConflictingElt(element, conflAttrChanges));
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

	private Set<AttributeConflict> detectAttributeConflict(BrokenMatch brokenMatch) {
		Set<AttributeConflict> attrConflicts = new HashSet<>();

		for (ConstrainedAttributeChanges constrAttrChanges : brokenMatch.getConstrainedAttrChanges()) {
			TGGAttributeConstraintDefinition def = constrAttrChanges.constraint.getDefinition();
			if (def.isUserDefined() || !def.getName().startsWith("eq_")) {
				logger.error(
						"Conflicted AttributeConstraints that are not equality constraints are currently not supported!");
				continue;
			}
			if (constrAttrChanges.constraint.getParameters().size() > 2) {
				logger.error("Conflicted AttributeConstraints with more than 2 parameters are currently not supported!");
				continue;
			}

			if (constrAttrChanges.affectedParams.size() > 1) {
				AttributeChange srcChange = null;
				AttributeChange trgChange = null;

				for (TGGAttributeExpression param : constrAttrChanges.affectedParams.keySet()) {
					switch (param.getObjectVar().getDomainType()) {
					case SRC:
						srcChange = constrAttrChanges.affectedParams.get(param);
						break;
					case TRG:
						trgChange = constrAttrChanges.affectedParams.get(param);
					default:
						break;
					}
				}

				if (srcChange == null || trgChange == null)
					logger.error("User attribute edit was not domain conform!");

				attrConflicts.add(
						new AttributeConflict(integrate, brokenMatch.getMatch(), constrAttrChanges, srcChange, trgChange));
			}
		}

		return attrConflicts;
	}

	private PartlyDelConflict inconsistentDelConflict(BrokenMatch brokenMatch) {
		brokenMatch.getDeletionType();

		return null;
	}

}
