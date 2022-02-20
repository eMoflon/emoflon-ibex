package org.emoflon.ibex.modelxml.parser;

import java.io.*;
import java.util.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.modelxml.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


public class Parser {

	private static List<XMLObject> xmlObjects;
	
	public static XMLModel parseXMLFile(String fileName) {
		return parseXMLFile(fileName, false);
	}
	
	public static void parseXMLFileToMetaModel(String filename) {
		parseXMLFile(filename, true);
	}
	
	private static XMLModel parseXMLFile(String fileName, boolean meta) {
		xmlObjects = new ArrayList<XMLObject>();
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		File file = new File(fileName);
		Document document = null;

		try {
			document = new SAXBuilder().build(file);
		} catch (org.jdom2.JDOMException | IOException e1) {
			e1.printStackTrace();
		}

		final Element rootElement = document.getRootElement();

		// Setup RessourceSet
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createURI(fileNameWoEnd+"Test.xmi"));

//		Generate XML Model
		XMLModel container = ModelxmlFactory.eINSTANCE.createXMLModel();
		
		if (meta) {
			parseNodeMeta(rootElement);
		} else {
			parseHeader(file, container);
			var root = parseNode(rootElement);
			container.setRoot(root);
		}
		
		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException er) {
			er.printStackTrace();
		}
		return container;
	}
	



	public static Resource parseXMLFile(URI uri) {
		var fileName = uri.toFileString();
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

		parseNode(rootFile);
		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException er) {
			er.printStackTrace();
		}
		return r;
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
		
		var childMap = new HashMap<String, List<EClass>>();
		for (var child: element.getChildren()) {
			var eChild = parseNodeMeta(child);
			
			// merge nodes with the same name
			if (childMap.containsKey(child.getName())) {
				childMap.get(eChild.getName())
			}
			try {
				var child2 = childMap.stream().filter(c -> c.getName().equals(eChild.getName())).findFirst().get();
				var cAttributes = new HashSet<>(child2.getEAttributes());
				var childAttributes = new HashSet<>(eChild.getEAttributes());
				cAttributes.addAll(childAttributes);
				child2.getEAttributes().clear();
				child2.getEAllAttributes().addAll(cAttributes);				
			} catch(NoSuchElementException e) {
				// child with unique name
				childMap.add(eChild);				
			}
			// TODO: something with value
		}
		return eclass;
	}
	
	private static boolean addOrMerge(XMLObject obj) {
		if (xmlObjects == null) return false;
		
		if (xmlObjects.contains(obj)) return false;
		for (var o: xmlObjects) {
			// if obj with same name exists, merge attributes
			if (o.name.equals(obj.name)) {
				return o.attributes.addAll(obj.attributes);
			}
		}
		return xmlObjects.add(obj);
	}
}
