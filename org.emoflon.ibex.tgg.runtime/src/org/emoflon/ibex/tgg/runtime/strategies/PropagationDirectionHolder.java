package org.emoflon.ibex.tgg.runtime.strategies;

import java.util.Collection;
import java.util.function.Function;

import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.tgg.patterns.PatternType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.OperationalisationMode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;

/**
 * A container to hold the current propagation direction for sequential synchronization.
 * 
 * @author adrian moeller
 *
 */
public class PropagationDirectionHolder {

	public enum PropagationDirection {
		FORWARD(OperationalisationMode.FORWARD, PatternType.FWD, opr -> opr.getTarget().getNodes()), //
		BACKWARD(OperationalisationMode.BACKWARD, PatternType.BWD, opr -> opr.getSource().getNodes());

		private final OperationalisationMode operationalisationMode;
		private final PatternType patternType;
		private final Function<TGGOperationalRule, Collection<IBeXNode>> nodesInOutputDomain;

		private PropagationDirection(OperationalisationMode operationalisationMode, PatternType patternType, //
				Function<TGGOperationalRule, Collection<IBeXNode>> nodesInOutputDomain) {
			this.operationalisationMode = operationalisationMode;
			this.patternType = patternType;
			this.nodesInOutputDomain = nodesInOutputDomain;
		}

		public OperationalisationMode getOperationalisationMode() {
			return operationalisationMode;
		}
		
		public PatternType getPatternType() {
			return patternType;
		}

		public Collection<IBeXNode> getNodesInOutputDomain(TGGOperationalRule greenPattern) {
			return nodesInOutputDomain.apply(greenPattern);
		}

		public static PropagationDirection byPatternType(PatternType patternType) {
			if (patternType == PatternType.FWD)
				return FORWARD;
			else if (patternType == PatternType.BWD)
				return BACKWARD;
			else
				return null;
		}
	}

	private PropagationDirection direction;

	public PropagationDirectionHolder() {
		this.direction = null;
	}

	public PropagationDirectionHolder(PropagationDirection direction) {
		this.direction = direction;
	}

	public PropagationDirection get() {
		return direction;
	}

	public void set(PropagationDirection direction) {
		this.direction = direction;
	}

}
