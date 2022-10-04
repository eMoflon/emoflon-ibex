package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import java.util.Objects;

import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

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
	NodeCandidate(TGGRuleNode originalNode, TGGRuleNode replacingNode) {
		super(originalNode, replacingNode);
	}
	
	TGGRuleNode originalNode() {
		return (TGGRuleNode) originalElt;
	}
	
	TGGRuleNode replacingNode() {
		return (TGGRuleNode) replacingElt;
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
	EdgeCandidate(TGGRuleEdge originalEdge, TGGRuleEdge replacingEdge) {
		super(originalEdge, replacingEdge);
	}
	
	TGGRuleEdge originalEdge() {
		return (TGGRuleEdge) originalElt;
	}
	
	TGGRuleEdge replacingEdge() {
		return (TGGRuleEdge) replacingElt;
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
