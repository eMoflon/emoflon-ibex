package org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts;

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
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.rule.RuntimeShortcutRule;
import org.emoflon.ibex.tgg.runtime.repair.strategies.ShortcutRepairStrategy.RepairableMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.classification.ClassifiedMatch;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util.ConflictElements;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.conflicts.resolution.util.ConflictEltFilter;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.modelchange.ModelChangeUtil;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.EltFilter;
import org.emoflon.ibex.tgg.runtime.strategies.integrate.util.TGGMatchUtil;
import org.emoflon.ibex.tgg.runtime.strategies.modules.RuleHandler;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.util.TGGEdgeUtil;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.util.debug.LoggerConfig;

import TGGRuntimeModel.TGGRuleApplication;

public abstract class Conflict {

	protected ConflictContainer container;
	protected boolean resolved;

	protected Set<ITGGMatch> conflictMatches;
	protected Set<ITGGMatch> scopeMatches;
	
	protected RuleHandler ruleHandler;

	public Conflict(ConflictContainer container) {
		this.container = container;
		this.resolved = false;
		container.addConflict(this);
		conflictMatches = null;
		scopeMatches = null;

		ruleHandler = container.integrate.getOptions().tgg.ruleHandler();
		
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

	protected void restoreMatch(ITGGMatch match) {
		// FIXME adrianm: wrong deletion order may lead to non perceived deletions! (ModelChangeProtocol)
		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		EltFilter filter = new EltFilter().create();
		integrate().matchUtils().get(match).getEMFEdges(filter).forEach(edge -> {
			if (!integrate().generalModelChanges().isDeleted(edge))
				return;
			if (edge.getType().isContainment())
				deletedContainmentEdges.add(edge);
			else
				deletedCrossEdges.add(edge);
		});
		integrate().matchUtils().get(match).getEObjects(filter).forEach(node -> {
			if (integrate().generalModelChanges().isDeleted(node))
				deletedNodes.add(node);
		});
		TGGRuleApplication ruleApplication = match.getRuleApplicationNode();
		deletedCrossEdges.addAll(integrate().generalModelChanges().getDeletedEdges(ruleApplication));

		recreateElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Conflict resolution: restored " //
				+ match.getPatternName() + "(" + match.hashCode() + ")");
	}

	protected void restoreDomain(ClassifiedMatch classifiedMatch, DomainType domain) {
		// TODO adrianm: fix filterNAC violations!

		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();

		// Collect elements to restore
		EltFilter filter = new EltFilter().create().domains(domain, DomainType.CORRESPONDENCE);
		TGGMatchUtil matchUtil = integrate().matchUtils().get(classifiedMatch.getMatch());
		matchUtil.getEMFEdges(filter).forEach(edge -> {
			if (!integrate().generalModelChanges().isDeleted(edge))
				return;
			if (edge.getType().isContainment()) {
				deletedContainmentEdges.add(edge);
			} else {
				TGGEdge tggEdge = classifiedMatch.util().getEdge(edge);
				// Restore only those corr edges that point to the restored domain
				if (tggEdge.getDomainType() != DomainType.CORRESPONDENCE || ((TGGNode) tggEdge.getTarget()).getDomainType() == domain)
					deletedCrossEdges.add(edge);
			}
		});
		matchUtil.getEObjects(filter).forEach(node -> {
			if (integrate().generalModelChanges().isDeleted(node))
				deletedNodes.add(node);
		});
		TGGRuleApplication ruleApplication = classifiedMatch.getMatch().getRuleApplicationNode();
		integrate().generalModelChanges().getDeletedEdges(ruleApplication).stream() //
				.filter(edge -> {
					TGGNode trgNode = classifiedMatch.util().getNode(edge.getTarget());
					return trgNode.getDomainType() == domain;
				}) //
				.forEach(edge -> deletedCrossEdges.add(edge));

		// Restore elements
		recreateElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Conflict resolution: restored " + domain + "-domain of " //
				+ classifiedMatch.getMatch().getPatternName() + "(" + classifiedMatch.getMatch().hashCode() + ")");
	}

	protected void revokeMatch(ITGGMatch match) {
		Set<EObject> deletedNodes = null;
		Set<EMFEdge> deletedContainmentEdges = null;
		Set<EMFEdge> deletedCrossEdges = null;

		TGGMatchUtil matchUtil = integrate().matchUtils().get(match);
		switch (match.getType()) {
			case FWD, SOURCE -> {
				TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.FORWARD);
				deletedNodes = operationalRule.getToBeMarked().getNodes().stream() //
						.map(n -> (EObject) match.get(n.getName())) //
						.collect(Collectors.toSet());
				deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
				deletedCrossEdges = operationalRule.getToBeMarked().getEdges().stream() //
						.filter(e -> !e.getType().isContainment()) //
						.map(e -> TGGEdgeUtil.getRuntimeEdge(match, e)) //
						.collect(Collectors.toSet());
			}
			case BWD, TARGET -> {
				TGGOperationalRule operationalRule = ruleHandler.getOperationalRule(match.getRuleName(), OperationalisationMode.BACKWARD);
				deletedNodes = operationalRule.getToBeMarked().getNodes().stream() //
						.map(n -> (EObject) match.get(n.getName())) //
						.collect(Collectors.toSet());
				deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
				deletedCrossEdges = operationalRule.getToBeMarked().getEdges().stream() //
						.filter(e -> !e.getType().isContainment()) //
						.map(e -> TGGEdgeUtil.getRuntimeEdge(match, e)) //
						.collect(Collectors.toSet());
			}
			case CONSISTENCY -> {
				EltFilter filter = new EltFilter().create();
				deletedNodes = matchUtil.getEObjects(filter);
				deletedContainmentEdges = Collections.emptySet(); // do we need containment edges here?
				deletedCrossEdges = matchUtil.getEMFEdgeStream(filter) //
						.filter(e -> !e.getType().isContainment()) //
						.collect(Collectors.toSet());
				TGGRuleApplication ra = match.getRuleApplicationNode();
				ra.eClass().getEAllReferences().forEach(r -> ra.eSet(r, null));
				ra.eResource().getContents().remove(ra);
			}
			default -> {
				deletedNodes = Collections.emptySet();
				deletedContainmentEdges = Collections.emptySet();
				deletedCrossEdges = Collections.emptySet();
			}
		}

		revokeElements(deletedNodes, deletedContainmentEdges, deletedCrossEdges);

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Conflict resolution: revoked " //
				+ match.getPatternName() + "(" + match.hashCode() + ")");
	}

	protected void revertRepairable(RepairableMatch repairableMatch, DomainType revertedDomain) {
		RuntimeShortcutRule shortcutRule = repairableMatch.opSCR.getRawShortcutRule();

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
		integrate().matchUtils().get(getMatch()).getEMFEdges(new EltFilter().corr().create()).stream() //
				.filter(e -> deletedNodes.contains(e.getTarget())) //
				.forEach(e -> deletedCrossEdges.add(e));
		TGGRuleApplication ruleApplication = getMatch().getRuleApplicationNode();
		integrate().generalModelChanges().getDeletedEdges(ruleApplication).stream() //
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

		LoggerConfig.log(LoggerConfig.log_conflicts(), () -> "Conflict resolution: reverted repairable " + revertedDomain + "-delta "//
				+ repairableMatch.opSCR.toString());
	}

	protected void recreateElements(Collection<EObject> nodes, Collection<EMFEdge> containmentEdges, Collection<EMFEdge> crossEdges) {
		// used to eliminate opposite edges
		Set<EReference> processedRefs = new HashSet<>();

		for (EMFEdge edge : containmentEdges) {
			ModelChangeUtil.createEdge(edge);
			if (edge.getType().getEOpposite() != null)
				processedRefs.add(edge.getType());
		}

		for (EObject node : nodes) {
			Resource resource = integrate().generalModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);

			// searches for deleted incoming marker edges and adds them to crossEdges
			for (EMFEdge edge : integrate().generalModelChanges().getDeletedIncomingEdges(node)) {
				if (edge.getSource() instanceof TGGRuleApplication)
					crossEdges.add(edge);
			}
		}

		for (EMFEdge edge : crossEdges) {
			boolean isBidirectional = edge.getType().getEOpposite() != null;
			if (isBidirectional && processedRefs.contains(edge.getType().getEOpposite()))
				continue;
			ModelChangeUtil.createEdge(edge);
			if (isBidirectional)
				processedRefs.add(edge.getType());
		}
	}

	protected void revokeElements(Collection<EObject> nodes, Collection<EMFEdge> containmentEdges, Collection<EMFEdge> crossEdges) {
		// used to eliminate opposite edges
		Set<EReference> processedRefs = new HashSet<>();

		for (EMFEdge edge : containmentEdges) {
			ModelChangeUtil.deleteEdge(edge);
			if (edge.getType().getEOpposite() != null)
				processedRefs.add(edge.getType());
		}

		for (EObject node : nodes)
			ModelChangeUtil.deleteElement(node, true);

		for (EMFEdge edge : crossEdges) {
			boolean isBidirectional = edge.getType().getEOpposite() != null;
			if (isBidirectional && processedRefs.contains(edge.getType().getEOpposite()))
				continue;
			ModelChangeUtil.deleteEdge(edge);
			if (isBidirectional)
				processedRefs.add(edge.getType());
		}
	}

	protected String printConflictIdentification() {
		return this.getClass().getSimpleName() + " at match " + getMatch().getPatternName() + "(" + getMatch().hashCode() + ")";
	}

	public List<Class<?>> getAllImplementedCRSInterfaces() {
		return ClassUtils.getAllInterfaces(this.getClass());
	}

}
