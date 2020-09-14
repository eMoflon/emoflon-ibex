package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictElements;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictEltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;

import language.DomainType;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public abstract class Conflict {

	protected ConflictContainer container;
	protected boolean resolved;

	protected Set<ITGGMatch> conflictMatches;
	protected Set<ITGGMatch> scopeMatches;

	public Conflict(ConflictContainer container) {
		this.container = container;
		this.resolved = false;
		container.addConflict(this);
		conflictMatches = null;
		scopeMatches = null;
	}

	abstract protected Set<ITGGMatch> initConflictMatches();

	abstract protected Set<ITGGMatch> initScopeMatches();

	public BrokenMatch getBrokenMatch() {
		return container.getBrokenMatch();
	}

	public Set<ITGGMatch> getConflictMatches() {
		if (conflictMatches == null)
			conflictMatches = initConflictMatches();
		return conflictMatches;
	}

	public Set<ITGGMatch> getScopeMatches() {
		if (scopeMatches == null)
			scopeMatches = initScopeMatches();
		return scopeMatches;
	}

	public boolean isResolved() {
		return resolved;
	}

	public INTEGRATE integrate() {
		return container.integrate;
	}

	/**
	 * Collects and filters the elements involved in this conflict.
	 * 
	 * @param filter       used to filter the elements by different criteria described in
	 *                     {@link ConflictEltFilter}
	 * @param includeScope if <code>true</code>, the result includes the elements from the conflict
	 *                     scope
	 * @return a container that contains the filtered objects and edges
	 */
	public ConflictElements filterConflictElements(ConflictEltFilter filter, boolean includeScope) {
		return new ConflictElements(this, filter, includeScope);
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
		// TODO adrianm: fix filterNAC violations!
		// TODO adrianm: avoid eopposites!

		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		// Collect elements to restore
		EltFilter filter = new EltFilter().create().domains(domain, DomainType.CORR);
		integrate().getMatchUtil().getEMFEdges(brokenMatch.getMatch(), filter).forEach(edge -> {
			if (integrate().getGeneralModelChanges().isDeleted(edge))
				if (edge.getType().isContainment()) {
					deletedContainmentEdges.add(edge);
				} else {
					TGGRuleEdge tggEdge = brokenMatch.util().getEdge(edge);
					// Restore only those corr edges that point to the restored domain
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

		// Restore elements
		deletedContainmentEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
		deletedNodes.forEach(node -> {
			Resource resource = integrate().getGeneralModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});
		deletedCrossEdges.forEach(edge -> ModelChangeUtil.createEdge(edge));
	}

}
