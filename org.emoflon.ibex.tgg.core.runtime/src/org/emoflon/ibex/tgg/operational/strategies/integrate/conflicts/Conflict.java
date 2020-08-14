package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public abstract class Conflict {

	protected ConflictContainer container;
	protected boolean resolved;

	public Conflict(ConflictContainer container) {
		this.container = container;
		this.resolved = false;
		container.addConflict(this);
	}

	public BrokenMatch getBrokenMatch() {
		return container.getBrokenMatch();
	}

	public boolean isResolved() {
		return resolved;
	}

	protected INTEGRATE integrate() {
		return container.integrate;
	}

	protected void restoreMatch(BrokenMatch brokenMatch) {
		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		EltFilter filter = new EltFilter().create();
		integrate().getMatchUtil().getEMFEdges(brokenMatch.getMatch(), filter).forEach(edge -> {
			if (integrate().getGeneralModelChanges().isDeleted(edge))
				if (edge.getType().isContainment())
					deletedContainmentEdges.add(edge);
				else
					deletedCrossEdges.add(edge);
		});
		integrate().getMatchUtil().getObjects(brokenMatch.getMatch(), filter).forEach(node -> {
			if (integrate().getGeneralModelChanges().isDeleted(node))
				deletedNodes.add(node);
		});
		TGGRuleApplication ruleApplication = integrate().getRuleApplicationNode(brokenMatch.getMatch());
		deletedCrossEdges.addAll(integrate().getGeneralModelChanges().getDeletedEdges(ruleApplication));

		deletedContainmentEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
		deletedNodes.forEach(node -> {
			Resource resource = integrate().getGeneralModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});
		deletedCrossEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
	}

	protected void restoreDomain(BrokenMatch brokenMatch, DomainType domain) {
		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		EltFilter filter = new EltFilter().create().domains(domain, DomainType.CORR);
		integrate().getMatchUtil().getEMFEdges(brokenMatch.getMatch(), filter).forEach(edge -> {
			if (integrate().getGeneralModelChanges().isDeleted(edge))
				if (edge.getType().isContainment()) {
					deletedContainmentEdges.add(edge);
				} else {
					TGGRuleEdge tggEdge = brokenMatch.util().getEdge(edge);
					if (tggEdge.getDomainType() != DomainType.CORR || tggEdge.getTrgNode().getDomainType() == domain)
						deletedCrossEdges.add(edge);
				}
		});
		integrate().getMatchUtil().getObjects(brokenMatch.getMatch(), filter).forEach(node -> {
			if (integrate().getGeneralModelChanges().isDeleted(node))
				deletedNodes.add(node);
		});
		TGGRuleApplication ruleApplication = integrate().getRuleApplicationNode(brokenMatch.getMatch());
		integrate().getGeneralModelChanges().getDeletedEdges(ruleApplication).stream() //
		.filter(edge -> {
			TGGRuleNode trgNode = brokenMatch.util().getNode(edge.getTarget());
			return trgNode.getDomainType() == domain;
		}) //
		.forEach(edge -> deletedCrossEdges.add(edge));
		
		deletedContainmentEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
		deletedNodes.forEach(node -> {
			Resource resource = integrate().getGeneralModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});
		deletedCrossEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
	}

}
