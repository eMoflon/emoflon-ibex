package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Node {
    // ##### graph management
    private Collection<Edge> corrEdges = new HashSet<>();
    private Collection<Edge> outEdges = new HashSet<>();
    private Collection<Edge> inEdges = new HashSet<>();

    final void addCorrEdge(Edge pEdge) {
	corrEdges.add(pEdge);
    }

    final void addOutEdge(Edge pEdge) {
	outEdges.add(pEdge);
    }

    final void addInEdge(Edge pEdge) {
	inEdges.add(pEdge);
    }

    public final Collection<Edge> getCorrEdges() {
	return Collections.unmodifiableCollection(corrEdges);
    }

    public final Collection<Edge> getOutgoingEdges() {
	return Collections.unmodifiableCollection(outEdges);
    }

    public final Collection<Edge> getIncomingEdges() {
	return Collections.unmodifiableCollection(inEdges);
    }

    public final Collection<Edge> getInOutEdges() {
	Collection<Edge> combinedEdges = new HashSet<Edge>(outEdges);
	combinedEdges.addAll(inEdges);
	return Collections.unmodifiableCollection(combinedEdges);
    }

    // ##### data
    private String type;
    private String label;
    private Collection<String> attributes;

    public Node(String pType, String pLabel, Collection<String> pAttributes) {
	type = pType;
	label = pLabel;
	attributes = pAttributes;
    }

    public String getType() {
	return type;
    }

    public void setType(String pType) {
	type = pType;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String pLabel) {
	label = pLabel;
    }

    public Collection<String> getAttributes() {
	return attributes;
    }

    public void setLabel(Collection<String> pAttributes) {
	attributes = pAttributes;
    }
}
