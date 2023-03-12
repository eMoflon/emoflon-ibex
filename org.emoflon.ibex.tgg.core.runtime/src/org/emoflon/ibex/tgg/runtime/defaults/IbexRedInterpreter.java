package org.emoflon.ibex.tgg.runtime.defaults;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.emoflon.ibex.tgg.util.TGGEdgeUtil.getRuntimeEdge;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.emf.EMFManipulationUtils;
import org.emoflon.ibex.tgg.runtime.IRedInterpreter;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.matches.ITGGMatch;
import org.emoflon.ibex.tgg.runtime.strategies.OperationalStrategy;
import org.emoflon.ibex.tgg.runtime.strategies.modules.TGGResourceHandler;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.Correspondence;
import org.emoflon.ibex.tgg.runtimemodel.TGGRuntimeModel.TGGRuleApplication;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

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
		TGGRuleApplication ra = match.getRuleApplicationNode();
		TGGOperationalRule operationalRule = options.tgg.ruleHandler().getOperationalRule(match.getRuleName());

		// Revoke nodes and edges in the correspondence model.
		revokeCorrs(match, operationalRule);

		// Revoke nodes and edges in the source and target model.
		revoke(getNodesToRevoke(match, operationalRule), getEdgesToRevoke(match, operationalRule));

		EMFManipulationUtils.delete(ra);
	}

	/**
	 * Collects the edges to revoke.
	 * 
	 * @param match
	 *            the match
	 * @param operationalRule
	 *            the create pattern
	 * @return the edges to revoke
	 */
	private Set<EMFEdge> getEdgesToRevoke(final ITGGMatch match, final TGGOperationalRule operationalRule) {
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		// Collect created edges to revoke.
		operationalRule.getCreateSource().getEdges().forEach(e -> {
			EMFEdge runtimeEdge = getRuntimeEdge(match, e);
			edgesToRevoke.add(new EMFEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType()));
		});
		
		operationalRule.getCreateTarget().getEdges().forEach(e -> {
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
	 * @param operationalRule
	 *            the create pattern
	 * @return the nodes to revoke
	 */
	private Set<EObject> getNodesToRevoke(final ITGGMatch match, final TGGOperationalRule operationalRule) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();

		operationalRule.getCreateSource().getNodes().stream() //
			.map(IBeXNode::getName) //
			.map(match::get) //
			.map(EObject.class::cast) //
			.forEach(n -> nodesToRevoke.add(n));
		
		operationalRule.getCreateTarget().getNodes().stream() //
			.map(IBeXNode::getName) //
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
	 * @param operationalRule
	 *            the pattern
	 */
	private void revokeCorrs(final ITGGMatch match, final TGGOperationalRule operationalRule) {
		Set<EObject> nodesToRevoke = new HashSet<EObject>();
		Set<EMFEdge> edgesToRevoke = new HashSet<EMFEdge>();

		operationalRule.getCreateCorrespondence().getNodes().stream() //
				.map(IBeXNode::getName) //
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
			if (n instanceof Correspondence)
				numOfDeletedCorrNodes++;
			else
				numOfDeletedNodes++;
		});
		
		EMFManipulationUtils.delete(nodesToRevoke, edgesToRevoke, false, true);
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
