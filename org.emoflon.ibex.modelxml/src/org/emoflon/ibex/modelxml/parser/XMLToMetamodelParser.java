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
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.jdom2.Element;

public class XMLToMetamodelParser extends ParserUtil{
	public static void main(String[] args) {
		parseXMLFileToMetaModel("misc/output.xml");
	}

	public static void parseXMLFileToMetaModel(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);
		
		final Element rootElement = getRootElement(fileName);
		
		// TODO: persistieren
		var rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		var r = rs.createResource(URI.createURI(fileNameWoEnd+".ecore"));
		var container = EcoreFactory.eINSTANCE.createEPackage();
		
		container.getEClassifiers().add(parseNodeMeta(rootElement, container));
		container.setName(fileNameWoEnd);
		container.setNsPrefix(fileNameWoEnd);
		container.setNsURI("platform:/resource/org.emoflon.ibex.modelxml/" + fileNameWoEnd + ".ecore");
		
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

		var eAttributes = eclass.getEStructuralFeatures();
		for (var att: element.getAttributes()) {
			var a = factory.createEAttribute();
			a.setName(att.getName());
			a.setEType(getDataType(att.getValue()));
			eAttributes.add(a);
		}  
//		for(var att : eclass.getEAttributes())
//			System.out.println(att.getName()+ " -> " + att.getEType().toString());

		// map children by their name into lists
		var childMap = new HashMap<String, List<EClass>>();
		for (var child: element.getChildren()) {
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
		
		// merge children with the same name
		for (var c: childMap.entrySet()) {
			var mergedClass = mergeNodes(c.getValue());
			container.getEClassifiers().add(mergedClass);
			System.out.println(mergedClass.getName() + " " + mergedClass.getEAttributes().toString());
			// TODO: set containment for mergedClass to eclass
			var eref = EcoreFactory.eINSTANCE.createEReference();
			eref.setName(mergedClass.getName());
			eref.setContainment(true);
			eref.setEType(mergedClass);
			eclass.getEStructuralFeatures().add(eref);
		}
//		for(var ref : eclass.getEReferences())
//			System.out.println(ref.getName() + " " + ref.getClass().toString());
//		container.getEClassifiers().add(eclass);
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
		
		var attributes = new HashMap<String, EAttribute>();
		for (var ch: nodes) {
			var mem = ch.getEStructuralFeatures();
			var attributesNode = mem.stream().filter(e -> e instanceof EAttribute).map(e -> (EAttribute) e).toList();
			for(var attr : attributesNode) {
				if(!attributes.containsKey(attr.getName())) {
					attributes.put(attr.getName(), attr);
				}
			}
		}
		for(var att:attributes.entrySet()) {
			eclass.getEStructuralFeatures().add(att.getValue());
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
					Boolean.parseBoolean(value);
					return EcorePackage.Literals.EBOOLEAN;
				}catch(NumberFormatException e3) {
					
				}
			}
			
		}
		
		return EcorePackage.Literals.ESTRING;
	}
}
