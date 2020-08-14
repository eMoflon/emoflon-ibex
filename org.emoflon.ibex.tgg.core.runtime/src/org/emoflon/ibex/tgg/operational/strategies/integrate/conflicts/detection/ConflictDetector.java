package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.detection;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.DeletionType;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.AttributeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.ConflictContainer;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveAttrConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.DelPreserveEdgeConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.InconsDelConflict;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.AttrConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.util.EdgeConflictingElt;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceGraph;
import org.emoflon.ibex.tgg.operational.strategies.integrate.matchcontainer.PrecedenceNode;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.AttributeChange;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChanges;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalysis.ConstrainedAttributeChanges;

import language.DomainType;
import language.TGGAttributeConstraintDefinition;
import language.TGGAttributeExpression;

public class ConflictDetector {

	protected final static Logger logger = Logger.getLogger(ConflictDetector.class);

	private INTEGRATE integrate;

	public ConflictDetector(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public Set<ConflictContainer> detectConflicts() {
		Set<ConflictContainer> conflicts = new HashSet<>();
		for (BrokenMatch brokenMatch : integrate.getClassifiedBrokenMatches().values()) {
			ConflictContainer container = detectMatchConflicts(brokenMatch);
			if (container != null)
				conflicts.add(container);
		}
		
		return conflicts;
	}

	private ConflictContainer detectMatchConflicts(BrokenMatch brokenMatch) {
		ConflictContainer container = new ConflictContainer(integrate, brokenMatch);
		detectDeletePreserveConflicts(container, brokenMatch);
		detectAttributeConflicts(container, brokenMatch);
		detectInconsistentDelConflict(container, brokenMatch);

		if (!container.getConflicts().isEmpty())
			return container;
		return null;
	}

	private void detectDeletePreserveConflicts(ConflictContainer container, BrokenMatch brokenMatch) {
		detectDeletePreserveConflict(container, brokenMatch, DomainType.SRC);
		detectDeletePreserveConflict(container, brokenMatch, DomainType.TRG);
	}

	private void detectDeletePreserveConflict(ConflictContainer container, BrokenMatch brokenMatch,
			DomainType domainToBePreserved) {
		PrecedenceGraph pg = integrate.getPrecedenceGraph();

		EltFilter filter = new EltFilter().create().notDeleted();
		if (domainToBePreserved == DomainType.SRC)
			filter.src();
		else
			filter.trg();

		boolean deletionAffected = false;
		PrecedenceNode node = pg.getNode(brokenMatch.getMatch());
		for (PrecedenceNode cn : node.getRollbackCauses()) {
			BrokenMatch cMatch = integrate.getClassifiedBrokenMatches().get(cn.getMatch());
			if (hasDomainSpecificViolations(cMatch, oppositeOf(domainToBePreserved))) {
				deletionAffected = true;
				break;
			}
		}

		if (!deletionAffected)
			return;

		ModelChanges changes = integrate.getGeneralModelChanges();
		Set<EdgeConflictingElt> edgeConflElts = new HashSet<>();
		Set<AttrConflictingElt> attrConflElts = new HashSet<>();

		integrate.getMatchUtil().getObjects(brokenMatch.getMatch(), filter).forEach(element -> {
			Set<EMFEdge> conflEdges = changes.getCreatedEdges(element);
			Set<AttributeChange> conflAttrChanges = changes.getAttributeChanges(element);
			if (!conflEdges.isEmpty())
				edgeConflElts.add(new EdgeConflictingElt(element, conflEdges));
			if (!conflAttrChanges.isEmpty())
				attrConflElts.add(new AttrConflictingElt(element, conflAttrChanges));
		});

		if (!edgeConflElts.isEmpty())
			new DelPreserveEdgeConflict(container, edgeConflElts, domainToBePreserved);
		if (!attrConflElts.isEmpty())
			new DelPreserveAttrConflict(container, attrConflElts, domainToBePreserved);
	}

	private boolean hasDomainSpecificViolations(BrokenMatch cMatch, DomainType domain) {
		switch (domain) {
		case SRC:
			if (DeletionType.getSrcDelCandidates().contains(cMatch.getDeletionType()))
				return true;
			break;
		case TRG:
			if (DeletionType.getTrgDelCandidates().contains(cMatch.getDeletionType()))
				return true;
			break;
		default:
			break;
		}

		if (cMatch.getFilterNacViolations().containsValue(domain))
			return true;

		// TODO adrianm: check attributes

		return false;
	}

	private DomainType oppositeOf(DomainType type) {
		switch (type) {
		case SRC:
			return DomainType.TRG;
		case TRG:
			return DomainType.SRC;
		case CORR:
			return DomainType.CORR;
		default:
			return null;
		}
	}

	private void detectAttributeConflicts(ConflictContainer container, BrokenMatch brokenMatch) {
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

				new AttributeConflict(container, constrAttrChanges, srcChange, trgChange);
			}
		}
	}

	private void detectInconsistentDelConflict(ConflictContainer container, BrokenMatch brokenMatch) {
		if (DeletionType.getInconsDelCandidates().contains(brokenMatch.getDeletionType()))
			new InconsDelConflict(container);
	}

}
