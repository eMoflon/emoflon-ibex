package org.emoflon.ibex.modelxml.parser;

import java.io.*;
import java.util.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.modelxml.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class Parser {

	public static XMLModel parseXMLFile() {
		return parseXMLFile("misc/xample.xml");
	}
	
	public static XMLModel parseXMLFile(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		final Element rootElement = getRootElement(fileName);
		
		// Setup RessourceSet
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createURI(fileNameWoEnd+"Test.xmi"));
		
		// Generate XML Model
		XMLModel container = ModelxmlFactory.eINSTANCE.createXMLModel();
		
		parseHeader(new File(fileName), container);
		var root = parseNode(rootElement);
		container.setRoot(root);
		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException er) {
			er.printStackTrace();
		}
		return container;
	}
	
	public static void parseXMLFileToMetaModel(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		final Element rootElement = getRootElement(fileName);
		
		// TODO: persistieren
		var rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		var r = rs.createResource(URI.createURI(fileNameWoEnd+"Ecore.idk"));
		//var container = SomeFactory.createContainer();
		
		var root = parseNodeMeta(rootElement);
		//container.setRoot(root);
		//r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return container;
	}

	private static Element getRootElement(String fileName) {
		File file = new File(fileName);
		Document document = null;

		try {
			document = new SAXBuilder().build(file);
		} catch (org.jdom2.JDOMException | IOException e1) {
			e1.printStackTrace();
		}

		return document.getRootElement();
	}
	

	public static Resource parseXMLFile(URI uri) {
		var fileName = uri.toFileString();
		return parseXMLFile(fileName).eResource();
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
	
	private static Node parseNode(final Element element) {
		Node node = ModelxmlFactory.eINSTANCE.createNode();
		
		node.setName(element.getName());
		
		Value val = ModelxmlFactory.eINSTANCE.createValue();
		val.setText(element.getValue());
		node.setValue(val);
		
		for (var attr : element.getAttributes()) {
			var a = ModelxmlFactory.eINSTANCE.createAttribute();
			a.setName(attr.getName());
			a.setValue(attr.getValue());
			node.getAttributes().add(a);
		}

		for (var child: element.getChildren()) {
			var x = parseNode(child);
			node.getChildren().add(x);
		}
		return node;
	}

	private static EClass parseNodeMeta(Element element) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(element.getName());
		
		var eAttributes = eclass.getEAttributes();
		for (var att: element.getAttributes()) {
			var a = factory.createEAttribute();
			a.setName(att.getName());
			// TODO: something with value
			
			eAttributes.add(a);
		}
		
		// map children by their name into lists
		var childMap = new HashMap<String, List<EClass>>();
		for (var child: element.getChildren()) {
			var eChild = parseNodeMeta(child);
			
			if (childMap.containsKey(child.getName())) {
				var entry = childMap.get(child.getName());
				entry.add(eChild);
			} else {
				var entry = new ArrayList<EClass>();
				entry.add(eChild);
				childMap.put(child.getName(), entry);
			}
		}
		
		// merge children with the same name
		for (var c: childMap.entrySet()) {
			var attributes = new HashSet<EAttribute>();
			for (var ch: c.getValue()) {
				attributes.addAll(ch.getEAttributes());
			}
			// TODO: something with value			
		}
		
		return eclass;
	}
}
