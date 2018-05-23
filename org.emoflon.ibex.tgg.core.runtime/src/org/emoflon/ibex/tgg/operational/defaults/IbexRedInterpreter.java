package org.emoflon.ibex.tgg.operational.defaults;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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

	private OperationalStrategy strategy;

	public IbexRedInterpreter(OperationalStrategy operationalStrategy) {
		this.strategy = operationalStrategy;
	}

	@Override
	public void revokeOperationalRule(IMatch match) {
		TGGRuleApplication ra = strategy.getRuleApplicationNode(match);
		IGreenPattern pattern = strategy.revokes(match);

		revokeCorrs(match, pattern);
		revokeNodes(match, pattern);
		revokeEdges(match, pattern);

		EcoreUtil.delete(ra);
	}

	private void revokeEdges(IMatch match, IGreenPattern pattern) {
		pattern.getSrcTrgEdgesCreatedByPattern().forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeCreatedEdge(runtimeEdge);
			EMFManipulationUtils.deleteEdge(runtimeEdge.getSource(), runtimeEdge.getTarget(), runtimeEdge.getType());
		});

		pattern.getEdgesMarkedByPattern().forEach(e -> {
			EMFEdge runtimeEdge = strategy.getRuntimeEdge(match, e);
			strategy.removeMarkedEdge(runtimeEdge);
		});
	}

	private void revokeNodes(IMatch match, IGreenPattern pattern) {
		pattern.getSrcTrgNodesCreatedByPattern().stream().map(TGGRuleNode::getName).map(match::get)
				.map(EObject.class::cast).forEach(this::revokeNode);
	}

	private void revokeNode(EObject n) {
		if (EMFManipulationUtils.isDanglingNode(n)) {
			strategy.addToTrash(n);
		}

		// Now safe to delete
		delete(n);
	}

	private void revokeCorrs(IMatch match, IGreenPattern pattern) {
		pattern.getCorrNodes().stream().map(TGGRuleNode::getName).map(match::get).map(EObject.class::cast)
				.forEach(corr -> {
					EStructuralFeature srcFeature = corr.eClass().getEStructuralFeature("source");
					EStructuralFeature trgFeature = corr.eClass().getEStructuralFeature("target");

					EObject src = (EObject) corr.eGet(srcFeature);
					EObject trg = (EObject) corr.eGet(trgFeature);

					if (EMFManipulationUtils.isDanglingNode(Optional.ofNullable(src))) {
						strategy.addToTrash(src);
					}
					if (EMFManipulationUtils.isDanglingNode(Optional.ofNullable(trg))) {
						strategy.addToTrash(trg);
					}

					corr.eUnset(srcFeature);
					corr.eUnset(trgFeature);

					revokeNode(corr);
				});

	}

	private void delete(EObject eObject) {
		EcoreUtil.delete(eObject);
	}
}
