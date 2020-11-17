package org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.debug.LoggerConfig;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.repair.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.operational.repair.shortcut.rule.ShortcutRule;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.classification.BrokenMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictElements;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflicts.resolution.util.ConflictEltFilter;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;

import language.BindingType;
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

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Detected conflict: " + printConflictIdentification());
	}

	abstract protected Set<ITGGMatch> initConflictMatches();

	abstract protected Set<ITGGMatch> initScopeMatches();

	public ITGGMatch getMatch() {
		return container.getMatch();
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
			if (!integrate().getGeneralModelChanges().isDeleted(edge))
				return;
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

		recreateElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);
	}

	protected void restoreDomain(BrokenMatch brokenMatch, DomainType domain) {
		// TODO adrianm: fix filterNAC violations!

		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		// Collect elements to restore
		EltFilter filter = new EltFilter().create().domains(domain, DomainType.CORR);
		integrate().getMatchUtil().getEMFEdges(brokenMatch.getMatch(), filter).forEach(edge -> {
			if (!integrate().getGeneralModelChanges().isDeleted(edge))
				return;
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
		recreateElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);
	}

	protected void revokeMatch(ITGGMatch match) {
		Set<EObject> deletedNodes = null;
		Set<EMFEdge> deletedContainmentEdges = null;
		Set<EMFEdge> deletedCrossEdges = null;

		switch (match.getType()) {
		case FWD:
		case SRC:
			IGreenPattern fwdPattern = integrate().getGreenFactory(match.getRuleName()).create(PatternType.FWD);
			deletedNodes = fwdPattern.getNodesMarkedByPattern().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.collect(Collectors.toSet());
			deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
			deletedCrossEdges = fwdPattern.getEdgesMarkedByPattern().stream() //
					.filter(e -> !e.getType().isContainment()) //
					.map(e -> TGGEdgeUtil.getRuntimeEdge(match, e)) //
					.collect(Collectors.toSet());
			break;
		case BWD:
		case TRG:
			IGreenPattern bwdPattern = integrate().getGreenFactory(match.getRuleName()).create(PatternType.BWD);
			deletedNodes = bwdPattern.getNodesMarkedByPattern().stream() //
					.map(n -> (EObject) match.get(n.getName())) //
					.collect(Collectors.toSet());
			deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
			deletedCrossEdges = bwdPattern.getEdgesMarkedByPattern().stream() //
					.filter(e -> !e.getType().isContainment()) //
					.map(e -> TGGEdgeUtil.getRuntimeEdge(match, e)) //
					.collect(Collectors.toSet());
			break;
		case CONSISTENCY:
			EltFilter filter = new EltFilter().create();
			deletedNodes = integrate().getMatchUtil().getObjects(match, filter);
			deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
			deletedCrossEdges = integrate().getMatchUtil().getEMFEdgeStream(match, filter) //
					.filter(e -> !e.getType().isContainment()) //
					.collect(Collectors.toSet());
			TGGRuleApplication ra = integrate().getRuleApplicationNode(match);
			ra.eClass().getEAllReferences().forEach(r -> ra.eSet(r, null));
			ra.eResource().getContents().remove(ra);
			break;
		default:
			deletedNodes = Collections.emptySet();
			deletedContainmentEdges = Collections.emptySet();
			deletedCrossEdges = Collections.emptySet();
			break;
		}

		revokeElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);
	}

	protected void revertRepairable(RepairableMatch repairableMatch, DomainType revertedDomain) {
		ShortcutRule shortcutRule = repairableMatch.opSCR.getOriginalScRule();

		Set<EObject> deletedNodes = TGGFilterUtil.filterNodes(shortcutRule.getNodes(), revertedDomain, BindingType.DELETE).stream() //
				.map(n -> (EObject) getMatch().get(n.getName())) //
				.collect(Collectors.toSet());
		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();
		TGGFilterUtil.filterEdges(shortcutRule.getEdges(), revertedDomain, BindingType.DELETE).stream() //
				.map(e -> TGGEdgeUtil.getRuntimeEdge(getMatch(), e)) //
				.forEach(e -> {
					if (e.getType().isContainment())
						deletedContainmentEdges.add(e);
					else
						deletedCrossEdges.add(e);
				});
		integrate().getMatchUtil().getEMFEdges(getMatch(), new EltFilter().corr().create()).stream() //
				.filter(e -> deletedNodes.contains(e.getTarget())) //
				.forEach(e -> deletedCrossEdges.add(e));
		TGGRuleApplication ruleApplication = integrate().getRuleApplicationNode(getMatch());
		integrate().getGeneralModelChanges().getDeletedEdges(ruleApplication).stream() //
				.filter(e -> deletedNodes.contains(e.getTarget())) //
				.forEach(e -> deletedCrossEdges.add(e));

		recreateElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);

		Set<EObject> createdNodes = TGGFilterUtil.filterNodes(shortcutRule.getNodes(), revertedDomain, BindingType.CREATE).stream() //
				.map(n -> (EObject) repairableMatch.scMatch.get(n.getName())) //
				.collect(Collectors.toSet());
		Set<EMFEdge> createdCrossEdges = TGGFilterUtil.filterEdges(shortcutRule.getEdges(), revertedDomain, BindingType.CREATE).stream() //
				.map(e -> TGGEdgeUtil.getRuntimeEdge(repairableMatch.scMatch, e)) //
				.filter(e -> !e.getType().isContainment()) //
				.filter(e -> !e.getType().isContainer()) //
				.collect(Collectors.toSet());

		revokeElements(createdNodes, Collections.emptySet(), createdCrossEdges);
	}

	protected void recreateElements(Collection<EObject> nodes, Collection<EMFEdge> containmentEdges, Collection<EMFEdge> crossEdges) {
		// used to eliminate opposite edges
		Set<EReference> processedRefs = new HashSet<>();

		containmentEdges.forEach(edge -> {
			ModelChangeUtil.createEdge(edge);
			if (edge.getType().getEOpposite() != null)
				processedRefs.add(edge.getType());
		});

		nodes.forEach(node -> {
			Resource resource = integrate().getGeneralModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});

		crossEdges.forEach(edge -> {
			boolean isBidirectional = edge.getType().getEOpposite() != null;
			if (isBidirectional && processedRefs.contains(edge.getType().getEOpposite()))
				return;
			ModelChangeUtil.createEdge(edge);
			if (isBidirectional)
				processedRefs.add(edge.getType());
		});
	}

	protected void revokeElements(Collection<EObject> nodes, Collection<EMFEdge> containmentEdges, Collection<EMFEdge> crossEdges) {
		// used to eliminate opposite edges
		Set<EReference> processedRefs = new HashSet<>();

		containmentEdges.forEach(edge -> {
			ModelChangeUtil.deleteEdge(edge);
			if (edge.getType().getEOpposite() != null)
				processedRefs.add(edge.getType());
		});

		nodes.forEach(node -> ModelChangeUtil.deleteElement(node, true));

		crossEdges.forEach(edge -> {
			boolean isBidirectional = edge.getType().getEOpposite() != null;
			if (isBidirectional && processedRefs.contains(edge.getType().getEOpposite()))
				return;
			ModelChangeUtil.deleteEdge(edge);
			if (isBidirectional)
				processedRefs.add(edge.getType());
		});
	}

	protected void deleteCorrs(ITGGMatch match) {
		integrate().deleteGreenCorrs(match);
		integrate().removeBrokenMatch(match);
	}

	protected String printConflictIdentification() {
		return this.getClass().getSimpleName() + " at match " + getMatch().getPatternName() + "(" + getMatch().hashCode() + ")";
	}

	public List<Class<?>> getAllImplementedCRSInterfaces() {
		return ClassUtils.getAllInterfaces(this.getClass());
	}

}
