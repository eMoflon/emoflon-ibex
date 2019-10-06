package org.emoflon.ibex.tgg.operational.monitoring.data;

public interface Edge {
    public String getLabel();

    public Node getSrcNode();

    public Node getTrgNode();

    public EdgeType getType();

    public Action getAction();
}