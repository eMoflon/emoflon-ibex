package org.emoflon.ibex.tgg.runtime.strategies;

import java.util.Collection;
import java.util.function.Function;

import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.runtime.patterns.BWDGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.FWDGreenPattern;
import org.emoflon.ibex.tgg.runtime.patterns.IGreenPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;

public class PropagationDirectionHolder {

	public enum PropagationDirection {
		FORWARD(PatternType.FWD, FWDGreenPattern.class, gp -> gp.getTrgNodes()), //
		BACKWARD(PatternType.BWD, BWDGreenPattern.class, gp -> gp.getSrcNodes());

		private final PatternType patternType;
		private final Class<? extends IGreenPattern> greenPatternClass;
		private final Function<IGreenPattern, Collection<TGGNode>> nodesInOutputDomain;

		private PropagationDirection(PatternType patternType, Class<? extends IGreenPattern> greenPatternClass,
				Function<IGreenPattern, Collection<TGGNode>> nodesInOutputDomain) {
			this.patternType = patternType;
			this.greenPatternClass = greenPatternClass;
			this.nodesInOutputDomain = nodesInOutputDomain;
		}

		public PatternType getPatternType() {
			return patternType;
		}

		public Class<? extends IGreenPattern> getGreenPatternClass() {
			return greenPatternClass;
		}

		public Collection<TGGNode> getNodesInOutputDomain(IGreenPattern greenPattern) {
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
