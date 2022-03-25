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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.jdom2.Element;

public class XMLToMetamodelParser extends ParserUtil {
	public static void main(String[] args) {
		parseXMLFileToMetaModel("misc/test1.xml");
	}

	public static EPackage parseXMLFileToMetaModel(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);

		final Element rootElement = getRootElement(fileName);

		// TODO: persistieren
		var rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		var r = rs.createResource(URI.createURI(fileNameWoEnd + ".ecore"));
		var container = EcoreFactory.eINSTANCE.createEPackage();

		rs.getURIConverter().getURIMap().put(
				URI.createPlatformResourceURI(fileName, true),
				URI.createURI(fileName));
		container.setName(fileNameWoEnd.substring(fileNameWoEnd.indexOf("/") +1));
		container.setNsPrefix(fileNameWoEnd.substring(fileNameWoEnd.indexOf("/") +1));
		container.setNsURI("platform:/resource/org.emoflon.ibex.modelxml/" + fileNameWoEnd + ".ecore");
		EClass clss = parseNodeMeta(rootElement, container);
		
		for(var cl : clss.getEReferences())
			System.out.println(cl.getName() + cl.getEReferenceType().toString());
		container.getEClassifiers().add(clss);

		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return container;
	}

	private static EClass parseNodeMeta(Element element, EPackage container) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(element.getName());

		var eAttributes = eclass.getEStructuralFeatures();
		for (var att : element.getAttributes()) {
			var a = factory.createEAttribute();
			a.setName(att.getName());
			a.setEType(getDataType(att.getValue()));
			eAttributes.add(a);
		}

		// map children by their name into lists
		var childMap = new HashMap<String, List<EClass>>();
		mapChildren(element, container, childMap);

		// merge children with the same name
		eclass = saveMergedChildren(container, eclass, childMap);
//		System.out.println("-----------" +eclass.getName());
//		for(var cl : eclass.getEReferences())
//			System.out.println(cl.getName() + cl.getEReferenceType().toString());
		return eclass;
	}

	/**
	 * This method saves the merged children into container. Furthermore, it creates
	 * a references from eclass to all merged children.
	 * 
	 * @param container Stores the Metamodel
	 * @param eclass    Representation of the current parsed node.
	 * @param childMap  memory for all children sorted by their names
	 * @return
	 */
	private static EClass saveMergedChildren(EPackage container, EClass eclass,
			HashMap<String, List<EClass>> childMap) {
		for (var c : childMap.entrySet()) {
			var mergedClass = mergeNodes(c.getValue());
			container.getEClassifiers().add(mergedClass);
			// TODO: set containment for mergedClass to eclass
			var eref = EcoreFactory.eINSTANCE.createEReference();
			eref.setName(mergedClass.getName().toLowerCase());
			eref.setContainment(true);
			eref.setEType(mergedClass);
			eclass.getEStructuralFeatures().add(eref);
		}
		return eclass;
	}

	/**
	 * This method put children in a map, where children a sorted by their names
	 * 
	 * @param element   Contains the element, which children should be parsed into
	 *                  eclasses
	 * @param container It contains the metamodel objects
	 * @param childMap  Store all parsed children, while store tupel consisting of
	 *                  equal-named classes and their names
	 */
	private static void mapChildren(Element element, EPackage container, HashMap<String, List<EClass>> childMap) {
		for (var child : element.getChildren()) {
			var eChild = parseNodeMeta(child, container);
//			System.out.println(eChild.getName());
			if (childMap.containsKey(child.getName())) {
				var entry = childMap.get(child.getName());
				entry.add(eChild);
			} else {
				var entry = new ArrayList<EClass>();
				entry.add(eChild);
				childMap.put(child.getName(), entry);
			}
		}
	}

	/**
	 * All elements in nodes have the same name - they are instances of one generic
	 * class
	 * 
	 * @param nodes
	 * @return
	 */
	private static EClass mergeNodes(List<EClass> nodes) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(nodes.get(0).getName());

		var attributes = new HashMap<String, EAttribute>();
		var references = new HashMap<String, EReference>();
		for (var ch : nodes) {
			var mem = ch.getEStructuralFeatures();
			var attributesNode = mem.stream().filter(e -> e instanceof EAttribute).map(e -> (EAttribute) e).toList();
			for (var attr : attributesNode) {
				if (!attributes.containsKey(attr.getName())) {
					attributes.put(attr.getName(), attr);
				}
			}
			var refFeatures = mem.stream().filter(e -> e instanceof EReference).map(e -> (EReference) e).toList();
			for(var ref : refFeatures) {
				if(!references.containsKey(ref.getName())) {
					references.put(ref.getName(), ref);
				}
			}
		}
		for (var att : attributes.entrySet()) {
			eclass.getEStructuralFeatures().add(att.getValue());
		}
		for(var ref : references.entrySet()) {
			eclass.getEStructuralFeatures().add(ref.getValue());
		}


		return eclass;
	}

	private static EDataType getDataType(String value) {
		try {
			Integer.parseInt(value);
			return EcorePackage.Literals.EINT;
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(value);
				return EcorePackage.Literals.EDOUBLE;
			} catch (NumberFormatException e2) {
				try {
					if(value.toLowerCase().equals("true") || value.toLowerCase().equals("false"))
						return EcorePackage.Literals.EBOOLEAN;
				} catch (NumberFormatException e3) {

				}
			}

		}

		return EcorePackage.Literals.ESTRING;
	}
}
