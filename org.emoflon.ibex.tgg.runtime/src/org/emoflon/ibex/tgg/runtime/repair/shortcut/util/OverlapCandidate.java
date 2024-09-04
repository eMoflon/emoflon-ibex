package org.emoflon.ibex.tgg.runtime.repair.shortcut.util;

import java.util.Objects;

import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;

abstract class OverlapCandidate {
	TGGRuleElement originalElt;
	TGGRuleElement replacingElt;
	
	public OverlapCandidate(TGGRuleElement originalElt, TGGRuleElement replacingElt) {
		this.originalElt = originalElt;
		this.replacingElt = replacingElt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originalElt, replacingElt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OverlapCandidate other = (OverlapCandidate) obj;
		return Objects.equals(originalElt, other.originalElt) && Objects.equals(replacingElt, other.replacingElt);
	}
}

class NodeCandidate extends OverlapCandidate {
	NodeCandidate(TGGNode originalNode, TGGNode replacingNode) {
		super(originalNode, replacingNode);
	}
	
	TGGNode originalNode() {
		return (TGGNode) originalElt;
	}
	
	TGGNode replacingNode() {
		return (TGGNode) replacingElt;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("NodeCandidate [\n");
		b.append("  originalNode:  ");
		b.append(originalElt);
		b.append("\n  replacingNode: ");
		b.append(replacingElt);
		b.append("\n]");
		return b.toString();
	}
}

class EdgeCandidate extends OverlapCandidate {
	EdgeCandidate(TGGEdge originalEdge, TGGEdge replacingEdge) {
		super(originalEdge, replacingEdge);
	}
	
	TGGEdge originalEdge() {
		return (TGGEdge) originalElt;
	}
	
	TGGEdge replacingEdge() {
		return (TGGEdge) replacingElt;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("EdgeCandidate [\n");
		b.append("  originalEdge:  ");
		b.append(originalElt);
		b.append("\n  replacingEdge: ");
		b.append(replacingElt);
		b.append("\n]");
		return b.toString();
	}
}
