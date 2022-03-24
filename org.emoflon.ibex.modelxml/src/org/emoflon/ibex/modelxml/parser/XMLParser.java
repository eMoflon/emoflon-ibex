package org.emoflon.ibex.modelxml.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.emoflon.ibex.modelxml.Attribute;
import org.emoflon.ibex.modelxml.ModelxmlFactory;
import org.emoflon.ibex.modelxml.Node;
import org.emoflon.ibex.modelxml.Value;
import org.emoflon.ibex.modelxml.XMLModel;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLParser  extends ParserUtil{
	public static XMLModel parseXMLFile(){
		return parseXMLFile("misc/xample.xml");
	}
	
	/**
	 * This method parses the content of a XML file into {@link org.emoflon.ibex.modelxml.XMLModel} 
	 * @param fileName Give the path of the XML file
	 * @return A XMLModel with the parsed content
	 */
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
	
	/**
	 * This method parses the content of a XML file into the XML metamodel.
	 * @param uri Contains the path of the XML file
	 * @return Give the resource of the parsed model.
	 */
	public static Resource parseXMLFile(URI uri) {
		var fileName = uri.toFileString();
		return parseXMLFile(fileName).eResource();
	}
	

	/**
	 * This method parses the content of a Node from Element into an {@link org.emoflon.ibex.modelxml.Node}
	 * @param element 
	 * @return
	 */
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
	
	/**
	 * This method parses the header of a given xml file into {@link org.emoflon.ibex.modelxml.XMLModel}
	 * @param file Give the path of the XML file
	 * @param container Consists the model, which should be store the parsed haeder
	 */
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
}
