package org.emoflon.ibex.tgg.operational.defaults;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.utils.EMFEdge;
import org.emoflon.ibex.common.utils.EMFManipulationUtils;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.IMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class IbexRedInterpreter implements IRedInterpreter {
	private final OperationalStrategy strategy;

	public IbexRedInterpreter(final OperationalStrategy operationalStrategy) {
		this.strategy = operationalStrategy;
	}

	@Override
	public void revokeOperationalRule(final IMatch match) {
		TGGRuleApplication ra = strategy.getRuleApplicationNode(match);
		IGreenPattern pattern = strategy.revokes(match);

		// Revoke nodes and edges in the correspondence model.
		revokeCorrs(match, pattern);

		// Revoke nodes and edges in the source and target model.
		revoke(getNodesToRevoke(match, pattern), getEdgesToRevoke(match, pattern));

		EcoreUtil.delete(ra);
	}

	/**
	 * Collects the edges to revoke.
	 * 
	 * @param match
	 *            the match
	 * @param pattern
	 *            the create pattern
	 * @return the edges to revoke
	 */
	private Set<EMFEdge> getEdgesToRevoke(final IMatch match, final IGreenPattern pattern) {
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		// Collect created edges to revoke.
		pattern.getSrcTrgEdgesCreatedByPattern().forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeCreatedEdge(runtimeEdge);
			edgesToRevoke.add(new EMFEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType()));
		});

		// Remove marked edges.
		pattern.getEdgesMarkedByPattern().forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeMarkedEdge(runtimeEdge);
		});

		return edgesToRevoke;
	}

	/**
	 * Collects the nodes to revoke
	 * 
	 * @param match
	 *            the match
	 * @param pattern
	 *            the create pattern
	 * @return the nodes to revoke
	 */
	private Set<EObject> getNodesToRevoke(final IMatch match, final IGreenPattern pattern) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();

		pattern.getSrcTrgNodesCreatedByPattern().stream() //
				.map(TGGRuleNode::getName) //
				.map(match::get) //
				.map(EObject.class::cast) //
				.forEach(n -> nodesToRevoke.add(n));

		return nodesToRevoke;
	}

	/**
	 * Revokes nodes and edges in the correspondence model.
	 * 
	 * @param match
	 *            the match
	 * @param pattern
	 *            the pattern
	 */
	private void revokeCorrs(final IMatch match, final IGreenPattern pattern) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		pattern.getCorrNodes().stream() //
				.map(TGGRuleNode::getName) //
				.map(match::get) //
				.map(EObject.class::cast) //
				.forEach(corr -> {
					EReference srcFeature = (EReference) corr.eClass().getEStructuralFeature("source");
					EReference trgFeature = (EReference) corr.eClass().getEStructuralFeature("target");

					EObject sourceObject = (EObject) corr.eGet(srcFeature);
					if (sourceObject != null) {
						edgesToRevoke.add(new EMFEdge(corr, sourceObject, srcFeature));
						if (EMFManipulationUtils.isDanglingNode(Optional.of(sourceObject))) {
							strategy.addToTrash(sourceObject);
						}
					}

					EObject targetObject = (EObject) corr.eGet(trgFeature);
					if (targetObject != null) {
						edgesToRevoke.add(new EMFEdge(corr, targetObject, trgFeature));
						if (EMFManipulationUtils.isDanglingNode(Optional.of(targetObject))) {
							strategy.addToTrash(targetObject);
						}
					}

					nodesToRevoke.add(corr);
				});

		revoke(nodesToRevoke, edgesToRevoke);
	}

	/**
	 * Revokes (i. e. deletes) the given nodes and edges.
	 * 
	 * @param nodesToRevoke
	 *            the nodes to revoke
	 * @param edgesToRevoke
	 *            the edges to revoke
	 */
	private void revoke(final Set<EObject> nodesToRevoke, final Set<EMFEdge> edgesToRevoke) {
		EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, node -> strategy.addToTrash(node));
	}
}
