package org.emoflon.ibex.modlexml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import org.apache.xerces.dom3.as.NodeEditAS;
import org.eclipse.emf.common.util.EList;
import org.emoflon.ibex.modelxml.Attribute;
import org.emoflon.ibex.modelxml.Element;
import org.emoflon.ibex.modelxml.ModelxmlFactory;
import org.emoflon.ibex.modelxml.Node;
import org.emoflon.ibex.modelxml.Value;
import org.emoflon.ibex.modelxml.XMLModel;
import org.emoflon.ibex.modelxml.parser.Parser;
import org.emoflon.ibex.modelxml.parser.Writer;
import org.junit.Test;

public class IntegrationTests {

	@Test
	public void loadTestXML() {
		XMLModel actualModel = Parser.parseXMLFile("misc/test.xml");
		Writer.createXMLFile(actualModel, "misc/output.xml");
		XMLModel expectModel = Parser.parseXMLFile("misc/output.xml");
		assertEquals(expectModel.getHeader(), actualModel.getHeader());
		assertTrue(compareNodes(expectModel.getRoot(), actualModel.getRoot()));
	}

	@Test
	public void compareOfAttributesTest() {
		Node actualNode = ModelxmlFactory.eINSTANCE.createNode();
		actualNode.setName("AttributTest");
		Node expectNode = ModelxmlFactory.eINSTANCE.createNode();
		expectNode.setName("AttributTest");
		initializeAttributesForNodes(actualNode, expectNode); 
		assertTrue(compareNodes(expectNode, actualNode)); // Everything is correct
		expectNode.getAttributes().get(0).setId(false);
		assertTrue(!compareNodes(expectNode, actualNode)); // failure in Name
		expectNode.getAttributes().get(0).setId(true);
		actualNode.getAttributes().get(2).setName("type");
		assertTrue(!compareNodes(expectNode, actualNode));
		Attribute tmp = actualNode.getAttributes().remove(2);
		assertTrue(!compareNodes(expectNode, actualNode));
		tmp.setName("veggie");
		actualNode.getAttributes().add(tmp);
		assertTrue(compareNodes(expectNode, actualNode));
		expectNode.getAttributes().remove(expectNode.getAttributes().size() - 1);
		assertTrue(!compareNodes(expectNode, actualNode));
		actualNode.getAttributes().remove(2);
		assertTrue(compareNodes(expectNode, actualNode));
		expectNode.getAttributes().get(1).setValue("HOHO");
		assertTrue(!compareNodes(expectNode, actualNode));
	}
	
	@Test
	public void compareOfNodesTest() {
		Node actualNode = ModelxmlFactory.eINSTANCE.createNode();
		Node expectNode = ModelxmlFactory.eINSTANCE.createNode();
		Node child1 = ModelxmlFactory.eINSTANCE.createNode();
		Node child2 = ModelxmlFactory.eINSTANCE.createNode();
		Node child3 = ModelxmlFactory.eINSTANCE.createNode();
		Node child4 = ModelxmlFactory.eINSTANCE.createNode();
		Value value1 = ModelxmlFactory.eINSTANCE.createValue();
		Value value2 = ModelxmlFactory.eINSTANCE.createValue();
		Value value3 = ModelxmlFactory.eINSTANCE.createValue();
		Value value4 = ModelxmlFactory.eINSTANCE.createValue();
		value1.setText("val1");
		value2.setText("val2");
		value3.setText("val1");
		value4.setText("val2");
		child1.setName("child");
		child2.setName("Child");
		child3.setName("child");
		child4.setName("Child");
		child1.setValue(value1);
		child2.setValue(value2);
		child3.setValue(value3);
		child4.setValue(value4);
		actualNode.getCrossref().add(child4);
		expectNode.getCrossref().add(child4);
		initializeAttributesForNodes(child1, child2);
		initializeAttributesForNodes(child3, child4);
		actualNode.getChildren().add(child1);
		actualNode.getChildren().add(child2);
		expectNode.getChildren().add(child4);
		expectNode.getChildren().add(child3);
		assertTrue(compareNodes(expectNode, actualNode));
		actualNode.getChildren().get(1).setName("child");// Nodes have different Names
		assertTrue(!compareNodes(expectNode, actualNode));
		actualNode.getChildren().get(1).setName("Child");
		actualNode.getChildren().get(0).setValue(value2);// Nodes have different Values
		assertTrue(!compareNodes(expectNode, actualNode));
		actualNode.getChildren().get(0).setValue(value2);
		actualNode.getCrossref().add(expectNode);// Nodes references different Nodes
		assertTrue(!compareNodes(expectNode, actualNode));
		actualNode.getCrossref().remove(actualNode.getCrossref().size()-1);
	}

	private void initializeAttributesForNodes(Node node1, Node node2) {
		Attribute attr1 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr2 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr3 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr4 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr5 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr6 = ModelxmlFactory.eINSTANCE.createAttribute();
		attr1.setName("id");
		attr1.setValue("001");
		attr1.setId(true);
		attr2.setName("typ");
		attr2.setValue("Meal");
		attr3.setName("veggie");
		attr3.setValue("true");
		attr4.setName("id");
		attr4.setValue("001");
		attr4.setId(true);
		attr5.setName("typ");
		attr5.setValue("Meal");
		attr6.setName("veggie");
		attr6.setValue("true");
		node1.getAttributes().add(attr1);
		node1.getAttributes().add(attr2);
		node1.getAttributes().add(attr3);
		node2.getAttributes().add(attr4);
		node2.getAttributes().add(attr5);
		node2.getAttributes().add(attr6);
	}

//	@Test
//	public void generateStringTests() {
//		Node node = ModelxmlFactory.eINSTANCE.createNode();
//		Node node1 = ModelxmlFactory.eINSTANCE.createNode();
//
//		Attribute attr1 = ModelxmlFactory.eINSTANCE.createAttribute();
//		Attribute attr2 = ModelxmlFactory.eINSTANCE.createAttribute();
//		Value val1 = ModelxmlFactory.eINSTANCE.createValue();
//		val1.setText("101");
//		node.setName("node1");
//		node.setValue(val1);
//		attr1.setName("attr");
//		attr2.setValue("bbc");
//		attr2.setName("attr");
//		attr1.setValue("hal");
//		node.getAttributes().add(attr1);
//		node.getAttributes().add(attr2);
//		node.getChildren().add(node1);
//		System.out.println(generateNodeString(node));
//		assertTrue(true);
//	}

//	private boolean compareNodes(Node expect, Node actual) {
//
//		if ((expect == null) && (actual == null)) {
//			return true;
//		}
//		if (!compareElements(expect, actual))
//			return false;
//		if (!compareAttributes(expect.getAttributes(), actual.getAttributes()))
//			return false;
//
//		printNode(expect);
//		printNode(actual);
//
//		// Compare Children
//		if (expect.getChildren().isEmpty() && actual.getChildren().isEmpty()) {
//			return true;
//		} else {
//			Set<Node> tmp = new HashSet<Node>();
//			for (var exp : expect.getChildren()) {
//				boolean successful = false;
//				for (var act : actual.getChildren()) {
//					if (compareElements(exp, act) && compareAttributes(exp.getAttributes(), act.getAttributes()))
//						if (compareNodes(exp, act)) {
//							tmp.add(act);
//							successful = true;
//						}
//				}
//				if (!successful) {
//					System.out.println("Node only in expect");
//					return false;
//				}
//			}
//			if (!tmp.containsAll(actual.getChildren()))
//				return false;
//		}
//
//		// TODO Compare CrossReferences
//		return true;
//	}

	private boolean compareNodes(Node expect, Node actual) {
		System.out.println(XMLNodeGenerator.generateStringOfNode(expect));
		System.out.println(XMLNodeGenerator.generateStringOfNode(actual));
		if (XMLNodeGenerator.generateStringOfNode(actual).equals(XMLNodeGenerator.generateStringOfNode(expect)))
			return true;
		return false;
	}
//	private void printNode(Node node) {
//		System.out.println("----------------" + node.getName() + "----------------");
//		EList<Attribute> attributes = node.getAttributes();
//		if (attributes.isEmpty())
//			System.out.println("leer");
//		for (var attr : attributes)
//			printAttribute(attr);
//	}

//	private void printAttribute(Attribute attr) {
//		System.out.println(attr.getName() + "=" + attr.getValue());
//	}

//	private boolean compareAttributes(EList<Attribute> expect, EList<Attribute> actual) {
//		if (actual == null && expect == null)
//			return true;
//		Set<Attribute> tmp = new HashSet<Attribute>();
//		for (var act : actual) {
////			printAttribute(act);
//			boolean successful = false;
//			for (var exp : expect) {
////				printAttribute(exp);
//				if ((exp.getName().equals(act.getName()) && exp.getValue().equals(act.getValue()))
//						&& (exp.isId() == act.isId())) {
//					tmp.add(exp);
//					successful = true;
//				}
//			}
////			System.out.println("-------------");
//			if (!successful) {
////				System.out.println("Attribut Fehler");
//				return false;
//			}
//		}
//		if (tmp.containsAll(expect))
//			return true;
//		return false;
//	}

	
//	private static void printList(List<String> list) {
//		System.out.println("-----------------printList----------------");
//		for(var a : list)
//			System.out.println(a);
//	}
}
