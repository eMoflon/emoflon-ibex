package org.emoflon.ibex.tgg.operational.monitoring.data;

public class Edge {
    private String label;
    private Node srcNode;
    private Node trgNode;

    public Edge(String pLabel, Node pSrcNode, Node pTrgNode) {
	label = pLabel;
	srcNode = pSrcNode;
	trgNode = pTrgNode;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String pLabel) {
	label = pLabel;
    }

    public Node getSrcNode() {
	return srcNode;
    }

    public void setSrcNode(Node pSrcNode) {
	srcNode = pSrcNode;
    }

    public Node getTrgNode() {
	return trgNode;
    }

    public void setTrgNode(Node pTrgNode) {
	trgNode = pTrgNode;
    }
}
