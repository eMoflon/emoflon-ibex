package org.emoflon.ibex.tgg.operational.repair.shortcut.search;

import java.util.Objects;

import language.TGGRuleEdge;
import language.TGGRuleNode;

/**
 * A SearchKey represents an edge and consists of a sourceNode, a targetNode,
 * the edge plus the information in which direction we are looking
 * (source->target or target->source)
 * 
 * @author lfritsche
 *
 */
public class SearchKey {

	public TGGRuleNode sourceNode;
	public TGGRuleNode targetNode;
	public TGGRuleEdge edge;
	public boolean reverse;

	public SearchKey(TGGRuleNode sourceNode, TGGRuleNode targetNode, TGGRuleEdge edge, boolean reverse) {
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.edge = edge;
		this.reverse = reverse;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SearchKey otherKey) {
			return sourceNode.equals(otherKey.sourceNode) &&
					targetNode.equals(otherKey.targetNode) &&
							edge.equals(otherKey.edge) &&
							reverse == otherKey.reverse;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sourceNode, targetNode, edge, reverse);
	}

	@Override
	public String toString() {
		if (reverse)
			return targetNode.getName() + "__" + edge.getType().getName() + "__" + sourceNode.getName();
		else
			return sourceNode.getName() + "__" + edge.getType().getName() + "__" + targetNode.getName();
	}
}
