package org.emoflon.ibex.modelxml.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.jdom2.Element;

public class XMLToMetamodelParser extends Parser{
	public static void main(String[] args) {
		parseXMLFileToMetaModel("misc/note.xml");
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
