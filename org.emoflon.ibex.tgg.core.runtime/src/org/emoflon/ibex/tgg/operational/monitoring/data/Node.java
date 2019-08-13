package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;

public class Node {
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
