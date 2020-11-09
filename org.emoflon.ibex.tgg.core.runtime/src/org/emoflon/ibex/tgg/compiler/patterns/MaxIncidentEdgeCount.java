package org.emoflon.ibex.tgg.compiler.patterns;

import language.TGGRuleNode;

public class MaxIncidentEdgeCount {
	private Integer edgeCount;
	private TGGRuleNode oneNode;
	private TGGRuleNode otherNode;

	public MaxIncidentEdgeCount(Integer edgeCount, TGGRuleNode oneNode, TGGRuleNode otherNode) {
		this.edgeCount = edgeCount;
		this.oneNode = oneNode;
		this.otherNode = otherNode;
	}

	public int getEdgeCount() {
		return edgeCount;
	}

	public TGGRuleNode getOtherNode() {
		return otherNode;
	}

	public TGGRuleNode getOneNode() {
		return oneNode;
	}
	
	public void setOneNode(TGGRuleNode oneNode) {
		this.oneNode = oneNode;
	}
	
	public void setOtherNode(TGGRuleNode otherNode) {
		this.otherNode = otherNode;
	}
	
	public void setEdgeCount(int edgeCount) {
		this.edgeCount = edgeCount;
	}
}
