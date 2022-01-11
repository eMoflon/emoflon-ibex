package xml;

import java.io.*;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import modelXML.ModelXMLFactory;
import modelXML.Value;
import modelXML.XMLModel;

public class Parser {

	public static XMLModel parseXMLFile(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		File file = new File(fileName);
		Document document = null;

		try {
			document = new SAXBuilder().build(file);
		} catch (JDOMException | IOException e1) {
			e1.printStackTrace();
		}

		final Element rootFile = document.getRootElement();

		// Setup RessourceSet
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createURI(fileNameWoEnd+"Test.xmi"));

//		Generate XML Model
		modelXML.XMLModel container = ModelXMLFactory.eINSTANCE.createXMLModel();
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

	private static void parseHeader(File file, modelXML.XMLModel container) {
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

	private static void parseContent(final Element element, modelXML.XMLModel container) {
		modelXML.Node root = ModelXMLFactory.eINSTANCE.createNode();
		container.setRoot(root);
		root.setName(element.getName());
		parseAttributes(element, root);

		parseChildren(element, root);
	}

	private static void parseChildren(final Element element, modelXML.Node node) {
		var children = element.getChildren();
		for (var child : children) {
			var childNode = modelXML.ModelXMLFactory.eINSTANCE.createNode();
//			System.out.println(child.getName());
			childNode.setName(child.getName());
//			System.out.println(child.getValue());
			Value val = ModelXMLFactory.eINSTANCE.createValue();
			val.setText(child.getValue());
			childNode.setValue(val);
			parseAttributes(child, childNode);
			parseChildren(child, childNode);
			node.getChildren().add(childNode);
		}
	}

	private static void parseAttributes(final Element rootFile, modelXML.Node root) {
		var attributesFile = rootFile.getAttributes();
		for (var attr : attributesFile) {
			var a = modelXML.ModelXMLFactory.eINSTANCE.createAttribute();
			a.setName(attr.getName());
			a.setValue(attr.getValue());
			root.getAttributes().add(a);
		}
	}
}
