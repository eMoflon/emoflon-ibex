package org.emoflon.ibex.modelxml.parser;

import java.io.*;
import java.util.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EMOFLoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.modelxml.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class Parser {

	public static void main(String[] args) {
		parseXMLFileToMetaModel("misc/note.xml");
	}
	
	
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
		var container = EcoreFactory.eINSTANCE.createEPackage();
		
		var node = parseNodeMeta(rootElement, container);
		
		r.getContents().add(container);
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
		
		var listA = new ArrayList<Attribute>();
		for (var attr : element.getAttributes()) {
			var a = ModelxmlFactory.eINSTANCE.createAttribute();
			a.setName(attr.getName());
			a.setValue(attr.getValue());
			listA.add(a);
		}
		node.getAttributes().addAll(listA);

		var list = new ArrayList<Node>();
		for (var child: element.getChildren()) {
			var x = parseNode(child);
			list.add(x);
		}
		node.getChildren().addAll(list);
		return node;
	}

	private static EClass parseNodeMeta(Element element, EPackage container) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(element.getName());
		
		var eAttributes = eclass.getEAttributes();
		var attributes = new HashSet<EAttribute>();
		for (var att: element.getAttributes()) {
			var a = factory.createEAttribute();
			a.setName(att.getName());
			a.setEType(getDataType(att.getValue()));
			attributes.add(a);
		}
		eAttributes.addAll(attributes);
		
		// map children by their name into lists
		var childMap = new HashMap<String, List<EClass>>();
		for (var child: element.getChildren()) {
			var eChild = parseNodeMeta(child, container);
			
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
			var mergedClass = mergeNodes(c.getValue());
			// TODO: set containment for mergedClass to eclass
			var eref = EcoreFactory.eINSTANCE.createEReference();
			eref.setContainment(true);
			eref.setEType(mergedClass);
			eclass.getEReferences().add(eref);
		}
		container.getEClassifiers().add(eclass);
		return eclass;
	}
	
	/**
	 * All elements in nodes have the same name
	 * - they are instances of one generic class
	 * @param nodes
	 * @return
	 */
	private static EClass mergeNodes(List<EClass> nodes) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(nodes.get(0).getName());
		
		
		var attributes = new HashSet<EAttribute>();
		for (var ch: nodes) {
			attributes.addAll(ch.getEAttributes());
		}
		eclass.getEAttributes().addAll(attributes);
		
		return eclass;
	}
	
	
	private static EDataType getDataType(String value) {
		try {
			Integer.parseInt(value);
			return EcorePackage.Literals.EINT;
		} catch (NumberFormatException e) {
			try {
				Boolean.parseBoolean(value);
				return EcorePackage.Literals.EBOOLEAN;
			} catch (NumberFormatException e2) {
				
			}
			
		}
		
		return EcorePackage.Literals.ESTRING;
	}
	
}