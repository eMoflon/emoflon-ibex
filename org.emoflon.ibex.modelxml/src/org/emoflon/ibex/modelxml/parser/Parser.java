package org.emoflon.ibex.modelxml.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.modelXML.ModelxmlFactory;
import org.emoflon.ibex.modelXML.Node;
import org.emoflon.ibex.modelXML.Value;
import org.emoflon.ibex.modelXML.XMLModel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class Parser {

	public static XMLModel parseXMLFile(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		File file = new File(fileName);
		Document document = null;

		try {
			document = new SAXBuilder().build(file);
		} catch (org.jdom2.JDOMException | IOException e1) {
			e1.printStackTrace();
		}

		final Element rootFile = document.getRootElement();

		// Setup RessourceSet
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createURI(fileNameWoEnd+"Test.xmi"));

//		Generate XML Model
		XMLModel container = ModelxmlFactory.eINSTANCE.createXMLModel();
		parseHeader(file, container);

		parseContent(rootFile, container);
		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException er) {
			er.printStackTrace();
		}
		return container;
	}
	
	public static XMLModel parseXMLFile() {
		return parseXMLFile("misc/xample.xml");
	}

	private static void parseHeader(File file, XMLModel container) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String header = br.readLine();
			if (header.charAt(1) == '?')
				container.setHeader(header);
			br.close();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private static void parseContent(final Element element, XMLModel container) {
		Node root = ModelxmlFactory.eINSTANCE.createNode();
		container.setRoot(root);
		root.setName(element.getName());
		parseAttributes(element, root);

		parseChildren(element, root);
	}

	private static void parseChildren(final Element element, Node node) {
		var children = element.getChildren();
		for (var child : children) {
			var childNode = ModelxmlFactory.eINSTANCE.createNode();
//			System.out.println(child.getName());
			childNode.setName(child.getName());
//			System.out.println(child.getValue());
			Value val = ModelxmlFactory.eINSTANCE.createValue();
			val.setText(child.getValue());
			childNode.setValue(val);
			parseAttributes(child, childNode);
			parseChildren(child, childNode);
			node.getChildren().add(childNode);
		}
	}

	private static void parseAttributes(final Element rootFile, Node root) {
		var attributesFile = rootFile.getAttributes();
		for (var attr : attributesFile) {
			var a = ModelxmlFactory.eINSTANCE.createAttribute();
			a.setName(attr.getName());
			a.setValue(attr.getValue());
			root.getAttributes().add(a);
		}
	}
}
