package org.emoflon.ibex.modlexml.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.emoflon.ibex.modelxml.Node;

public class XMLNodeGenerator {
	/**
	 * 
	 * This method generate a String, which contains all information of the given
	 * node
	 * 
	 * @param node wihch should be represented as string
	 * @return String with all informations
	 */
	static String generateStringOfNode(Node node) {
		// empty node returns empty string
		if (node == null)
			return "";
		// Format of string: <name>;<attributes>;<children>;<crossref>;
		StringBuilder sb = new StringBuilder();
		if(node.getValue()!=null && node.getValue().getText() != "")
			sb.append(String.format(node.getName() + "=%s;", node.getValue().getText()));
		else
			sb.append(String.format("%s;", node.getName()));
		// Format of attributes: <attribute1.name>=<attribute1.value>,
		// <attribute2.name>=<attribute2.value>...;
		if (node.getAttributes().isEmpty())
			sb.append(" ;");
		else {
			List<String> mem = new ArrayList<>();
			for (var attr : node.getAttributes()) {
				if(attr.isId())
					mem.add(String.format(attr.getName() + "#=%s,", attr.getValue()));
				else
					mem.add(String.format(attr.getName() + "=%s,", attr.getValue()));
			}

			Collections.sort(mem);

			for (var a : mem)
				sb.append(a);
			sb.append(";");
		}
		//Format of children: (<child1>),(<child2>), ...;
		//in Brackets is a whole node given
		if (node.getChildren().isEmpty()) {
			sb.append(";");
		} else {
			List<String> mem = new ArrayList<>();
			for (var child : node.getChildren())
				mem.add(String.format("(%s),", generateStringOfNode(child)));
			Collections.sort(mem);
			for (var child : mem)
				sb.append(child);
			sb.append(";");
		}
		//Format of crossreferences: (<crossref1>),(<crossref2>), ...;
		if (node.getCrossref().isEmpty()) {
			sb.append(";");
		} else {
			List<String> mem = new ArrayList<>();
			for (var child : node.getCrossref())
				mem.add(String.format("(%s),", generateStringOfNode(child)));
			Collections.sort(mem);
			for (var child : mem)
				sb.append(child);
			sb.append(";");
		}
		return sb.toString();
	}
}
