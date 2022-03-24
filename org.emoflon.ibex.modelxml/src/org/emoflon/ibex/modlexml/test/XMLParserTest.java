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
import org.emoflon.ibex.modelxml.parser.XMLParser;
import org.junit.Test;

public class XMLParserTest {

	@Test
	public void loadTestXML() {
		XMLModel actualModel = XMLParser.parseXMLFile("misc/Beispiel.xml");// load an xml model into the actual model
		Writer.createXMLFile(actualModel, "misc/output.xml"); //Write the content of the actual model into a xml file
		XMLModel expectModel = XMLParser.parseXMLFile("misc/output.xml"); //Parse the content of output.xml file into the expected model
		assertEquals(expectModel.getHeader(), actualModel.getHeader());//Compares the header of the models
		assertTrue(compareNodes(expectModel.getRoot(), actualModel.getRoot()));//Compares the content of the models
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


	private boolean compareNodes(Node expect, Node actual) {
		if (XMLTestUtil.generateStringOfNode(actual).equals(XMLTestUtil.generateStringOfNode(expect)))
			return true;
		return false;
	}

}
