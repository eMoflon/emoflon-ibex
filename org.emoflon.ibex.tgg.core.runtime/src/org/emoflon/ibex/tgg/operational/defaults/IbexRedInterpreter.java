package org.emoflon.ibex.tgg.operational.defaults;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.operational.IRedInterpreter;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;
import org.emoflon.ibex.tgg.operational.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.operational.strategies.modules.TGGResourceHandler;

import language.TGGRuleNode;
import runtime.TGGRuleApplication;

public class IbexRedInterpreter implements IRedInterpreter {
	private final OperationalStrategy strategy;
	private IbexOptions options;
	
	/**
	 * Number of deleted source & target nodes
	 */
	private int numOfDeletedNodes = 0;
	private int numOfDeletedCorrNodes = 0;
	
	private TGGResourceHandler resourceHandler;

	public IbexRedInterpreter(OperationalStrategy operationalStrategy) {
		this.strategy = operationalStrategy;
		options = strategy.getOptions();
		resourceHandler = options.resourceHandler();
	}

	@Override
	public void revokeOperationalRule(final ITGGMatch match) {
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
	private Set<EMFEdge> getEdgesToRevoke(final ITGGMatch match, final IGreenPattern pattern) {
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		// Collect created edges to revoke.
		pattern.getSrcTrgEdgesCreatedByPattern().forEach(e -> {
			EMFEdge runtimeEdge = getRuntimeEdge(match, e);
			edgesToRevoke.add(new EMFEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType()));
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
	private Set<EObject> getNodesToRevoke(final ITGGMatch match, final IGreenPattern pattern) {
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
	private void revokeCorrs(final ITGGMatch match, final IGreenPattern pattern) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		pattern.getCorrNodes().stream() //
				.map(TGGRuleNode::getName) //
				.map(match::get) //
				.map(EObject.class::cast) //
				.forEach(corr -> revokeCorr(corr, nodesToRevoke, edgesToRevoke));

		revoke(nodesToRevoke, edgesToRevoke);
	}

	public void revokeCorr(EObject corr, Set<EObject> nodesToRevoke, Set<EMFEdge> edgesToRevoke) {
		resourceHandler.removeCorrCachingNode(corr);
		
		EReference srcFeature = (EReference) corr.eClass().getEStructuralFeature("source");
		EReference trgFeature = (EReference) corr.eClass().getEStructuralFeature("target");

		EObject sourceObject = (EObject) corr.eGet(srcFeature);
		if (sourceObject != null) {
			edgesToRevoke.add(new EMFEdge(corr, sourceObject, srcFeature));
			if (EMFManipulationUtils.isDanglingNode(Optional.of(sourceObject))) {
				resourceHandler.addToTrash(sourceObject);
			}
		}

		EObject targetObject = (EObject) corr.eGet(trgFeature);
		if (targetObject != null) {
			edgesToRevoke.add(new EMFEdge(corr, targetObject, trgFeature));
			if (EMFManipulationUtils.isDanglingNode(Optional.of(targetObject))) {
				resourceHandler.addToTrash(targetObject);
			}
		}

		nodesToRevoke.add(corr);
	}

	/**
	 * Revokes (i. e. deletes) the given nodes and edges.
	 * 
	 * @param nodesToRevoke
	 *            the nodes to revoke
	 * @param edgesToRevoke
	 *            the edges to revoke
	 */
	public void revoke(final Set<EObject> nodesToRevoke, final Set<EMFEdge> edgesToRevoke) {
		nodesToRevoke.forEach(n -> {
			Resource r = n.eResource();
			if (resourceHandler.getSourceResource().equals(r) || resourceHandler.getTargetResource().equals(r))
				numOfDeletedNodes++;
			else if (resourceHandler.getCorrResource().equals(r))
				numOfDeletedCorrNodes++;
		});
		
		if(options.blackInterpreter().getProperties().needs_trash_resource()) 
			EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, node -> resourceHandler.addToTrash(node), false, !options.blackInterpreter().getProperties().needs_paranoid_modificiations());
		else
			EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, false, !options.blackInterpreter().getProperties().needs_paranoid_modificiations());
	}

	@Override
	public int getNumOfDeletedNodes() {
		return numOfDeletedNodes;
	}

	@Override
	public int getNumOfDeletedCorrNodes() {
		return numOfDeletedCorrNodes;
	}
}
