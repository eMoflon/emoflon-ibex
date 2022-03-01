package org.emoflon.ibex.modlexml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		Writer.createXMLFile(actualModel, "misc/test.xml");
		XMLModel expectModel = Parser.parseXMLFile("misc/test.xml");
		assertEquals(expectModel.getHeader(), actualModel.getHeader());
		assertTrue(compareNodes(expectModel.getRoot(), actualModel.getRoot()));
	}

	@Test
	public void compareAttributesTest() {
		Node actualNode = ModelxmlFactory.eINSTANCE.createNode();
		Node expectNode = ModelxmlFactory.eINSTANCE.createNode();
		Attribute attr1 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr2 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr3 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr4 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr5 = ModelxmlFactory.eINSTANCE.createAttribute();
		Attribute attr6 = ModelxmlFactory.eINSTANCE.createAttribute();
		attr1.setName("id");
		attr1.setValue("001");
		attr1.setId(true);
		attr2.setName("Typ");
		attr2.setValue("Meal");
		attr3.setName("veggie");
		attr3.setValue("true");
		attr4.setName("id");
		attr4.setValue("001");
		attr4.setId(true);
		attr5.setName("Typ");
		attr5.setValue("Meal");
		attr6.setName("veggie");
		attr6.setValue("true");
		actualNode.getAttributes().add(attr1);
		actualNode.getAttributes().add(attr2);
		actualNode.getAttributes().add(attr3);
		expectNode.getAttributes().add(attr4);
		expectNode.getAttributes().add(attr5);
		expectNode.getAttributes().add(attr6);
		//Everything is correct
		assertTrue(compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));
		//failure in Name
		attr6.setName("Type");
		assertTrue(!compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));
		//failure in different number of nodes
		actualNode.getAttributes().remove(2);
		assertTrue(!compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));
		actualNode.getAttributes().add(attr6);
		expectNode.getAttributes().remove(expectNode.getAttributes().size()-1);
		assertTrue(!compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));
		actualNode.getAttributes().remove(2);
		attr4.setId(false);
		assertTrue(!compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));
		attr4.setId(true);
		attr5.setValue("HOHO");
		assertTrue(!compareAttributes(expectNode.getAttributes(), actualNode.getAttributes()));

	}

	private boolean compareNodes(Node expect, Node actual) {


		if ((expect == null) && (actual == null)) {
			return true;
		}
		if (!compareElements(expect, actual))
			return false;
		if (!compareAttributes(expect.getAttributes(), actual.getAttributes()))
			return false;

		printNode(expect);
		printNode(actual);

		// Compare Children
		if (expect.getChildren().isEmpty() && actual.getChildren().isEmpty()) {
			return true;
		} else {
			Set<Node> tmp = new HashSet<Node>();
			for (var exp : expect.getChildren()) {
				boolean successful = false;
				for (var act : actual.getChildren()) {
					if (compareElements(exp, act) && compareAttributes(exp.getAttributes(), act.getAttributes()))
						if (compareNodes(exp, act)) {
							tmp.add(act);
							successful = true;
						}
				}
				if (!successful) {
					System.out.println("Node only in expect");
					return false;
				}
			}
			if(!tmp.containsAll(actual.getChildren()))return false;
		}
		
		// TODO Compare CrossReferences
		return true;
	}

	private void printNode(Node node) {
		System.out.println("----------------" + node.getName() + "----------------");
		EList<Attribute> attributes = node.getAttributes();
		if (attributes.isEmpty())
			System.out.println("leer");
		for (var attr : attributes)
			printAttribute(attr);
	}

	private void printAttribute(Attribute attr) {
		System.out.println(attr.getName() + "=" + attr.getValue());
	}

	private boolean compareAttributes(EList<Attribute> expect, EList<Attribute> actual) {
		if (actual == null && expect == null)
			return true;
		Set<Attribute> tmp = new HashSet<Attribute>();
		for (var act : actual) {
//			printAttribute(act);
			boolean successful = false;
			for (var exp : expect) {
//				printAttribute(exp);
				if ((exp.getName().equals(act.getName()) && exp.getValue().equals(act.getValue()))
						&& (exp.isId() == act.isId())) {
					tmp.add(exp);
					successful = true;
				}
			}
//			System.out.println("-------------");
			if (!successful) {
				System.out.println("Attribut Fehler");
				return false;
			}
		}
		if(tmp.containsAll(expect))return true;
		return false;
	}

	private boolean compareElements(Element expect, Element actual) {
		if (expect == null && actual == null)
			return true;
		if (expect.getName().equals(actual.getName()))
			return true;
		return false;
	}

}
