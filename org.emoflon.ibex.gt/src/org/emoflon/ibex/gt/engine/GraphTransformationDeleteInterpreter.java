package org.emoflon.ibex.gt.engine;

import java.util.Optional;

import org.emoflon.ibex.common.operational.IDeletePatternInterpreter;
import org.emoflon.ibex.common.operational.IMatch;

import IBeXLanguage.IBeXDeletePattern;

/**
 * Interpreter applying deletion of elements for graph transformation.
 */
public class GraphTransformationDeleteInterpreter implements IDeletePatternInterpreter {

	@Override
	public Optional<IMatch> apply(IBeXDeletePattern deletePattern, IMatch match) {
		// TODO Auto-generated method stub
		deletePattern.getDeletedNodes().forEach(node -> {
			System.out.println("Delete node " + node.getName() + ": " + node.getType().getName());
		});
		deletePattern.getDeletedEdges().forEach(edge -> {
			System.out.println("Delete edge " + edge.getType().getName());
		});
		return Optional.of(match);
	}
}
