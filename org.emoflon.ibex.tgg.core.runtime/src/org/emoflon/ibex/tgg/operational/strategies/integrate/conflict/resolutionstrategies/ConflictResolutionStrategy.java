package org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.resolutionstrategies;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.conflict.Conflict.ConflResStratToken;
import org.emoflon.ibex.tgg.operational.strategies.integrate.modelchange.ModelChangeProtocol;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.MatchAnalyser.EltFilter;

import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public abstract class ConflictResolutionStrategy {

	public ConflictResolutionStrategy(ConflResStratToken token) {
	}

	protected void restoreMatch(INTEGRATE integrate, ITGGMatch match) {
		ModelChangeProtocol mcp = integrate.getModelChangeProtocol();
		Set<TGGRuleElement> elements = integrate.getMatchAnalyser().getElts(match, new EltFilter().create());

		Set<EMFEdge> deletedContainmentEdges = new HashSet<>();
		Set<EObject> deletedNodes = new HashSet<>();
		Set<EMFEdge> deletedCrossEdges = new HashSet<>();
		elements.forEach(elt -> {
			if (elt instanceof TGGRuleEdge) {
				EMFEdge edge = integrate.getRuntimeEdge(match, (TGGRuleEdge) elt);
				if (integrate.getUserModelChanges().isDeleted(edge))
					if (edge.getType().isContainment())
						deletedContainmentEdges.add(edge);
					else
						deletedCrossEdges.add(edge);
			} else if (elt instanceof TGGRuleNode) {
				EObject node = (EObject) match.get(elt.getName());
				if (integrate.getUserModelChanges().isDeleted(node))
					deletedNodes.add(node);
			}
		});

		deletedContainmentEdges.forEach(edge -> mcp.util.createEdge(edge));
		deletedNodes.forEach(node -> {
			Resource resource = integrate.getUserModelChanges().containedInResource(node);
			if (resource != null)
				resource.getContents().add(node);
		});
		deletedCrossEdges.forEach(edge -> mcp.util.createEdge(edge));
	}

	/**
	 * Applies this conflict resolution strategy to the models.
	 * 
	 * @param integrate INTEGRATE
	 */
	public abstract void apply(INTEGRATE integrate);

}
