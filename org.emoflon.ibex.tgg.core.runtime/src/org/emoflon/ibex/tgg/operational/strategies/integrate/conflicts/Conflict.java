package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.TGGMatchUtil.EltFilter;

import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public abstract class Conflict {

	protected ConflictContainer container;

	public Conflict(ConflictContainer container) {
		this.container = container;
		container.addConflict(this);
	}

	public BrokenMatch getBrokenMatch() {
		return container.getBrokenMatch();
	}

	protected INTEGRATE integrate() {
		return container.integrate;
	}

	protected void restoreMatch(ITGGMatch match) {
		Set<TGGRuleElement> elements = integrate().getMatchUtil().getElts(match, new EltFilter().create());

		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();
		elements.forEach(elt -> {
			if (elt instanceof TGGRuleEdge) {
				EMFEdge edge = getRuntimeEdge(match, (TGGRuleEdge) elt);
				if (integrate().getGeneralModelChanges().isDeleted(edge))
					if (edge.getType().isContainment())
						deletedContainmentEdges.add(edge);
					else
						deletedCrossEdges.add(edge);
			} else if (elt instanceof TGGRuleNode) {
				EObject node = (EObject) match.get(elt.getName());
				if (integrate().getUserModelChanges().isDeleted(node))
					deletedNodes.add(node);
			}
		});
		TGGRuleApplication ruleApplication = integrate().getRuleApplicationNode(match);
		deletedCrossEdges.addAll(integrate().getGeneralModelChanges().getDeletedEdges(ruleApplication));

		deletedContainmentEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
		deletedNodes.forEach(node -> {
			Resource resource = integrate().getGeneralModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});
		deletedCrossEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
	}

}
