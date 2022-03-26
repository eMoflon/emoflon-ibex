package org.emoflon.ibex.modelxml.parser;

import java.io.IOException;
import java.util.*;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.jdom2.Attribute;
import org.jdom2.Element;

public class XMLToMetamodelParser extends ParserUtil {
	public static void main(String[] args) {
		parseXMLFileToMetaModel("misc/output.xml");
	}
	
	static Map<String, EClass> classMap = new HashMap<>();
	
	public static EPackage parseXMLFileToMetaModel(String fileName) {
		var end = fileName.lastIndexOf(".");
		var fileNameWoEnd = fileName.substring(0, end);

		final Element rootElement = getRootElement(fileName);

		// TODO: persistieren
		var rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
		var r = rs.createResource(URI.createURI(fileNameWoEnd + ".ecore"));
		var container = EcoreFactory.eINSTANCE.createEPackage();

//		rs.getURIConverter().getURIMap().put(
//				URI.createPlatformResourceURI(fileName, true),
//				URI.createURI(fileName));
		container.setName(fileNameWoEnd.substring(fileNameWoEnd.indexOf("/") +1));
		container.setNsPrefix(fileNameWoEnd.substring(fileNameWoEnd.indexOf("/") +1));
		container.setNsURI("platform:/resource/org.emoflon.ibex.modelxml/" + fileNameWoEnd + ".ecore");
		
		parseNodeMeta(rootElement, "", null);
		
		for (var clss : classMap.entrySet()) {
			container.getEClassifiers().add(clss.getValue());
		}

		r.getContents().add(container);
		try {
			r.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return container;
	}

	private static void parseNodeMeta(Element element, String name, EClass parent) {
		name += element.getName();
		if (classMap.containsKey(name)) {
			// add to existing
			/** check attr
			 *  check its children
			 */
			EClass eclass = classMap.get(name);
			var features = eclass.getEStructuralFeatures();
			var attributes = features.stream().filter(e -> e instanceof EAttribute).map(e -> {
				var a = (EAttribute) e;
				return a.getName() + a.getEAttributeType().getName();
			}).toList();
			for (var att: element.getAttributes()) {
				var a = att.getName() + getDataType(att.getValue()).getName();
				if (!attributes.contains(a)) {
					addAttribute(features, att);
				}
			}
			for (var c: element.getChildren()) {
				parseNodeMeta(c, name, eclass);
			}
			return;
		} 
		
		// new class found
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eclass = factory.createEClass();
		eclass.setName(element.getName());
		
		var<EAttribute> eAttributes = eclass.getEStructuralFeatures();
		for (var att : element.getAttributes()) {
			addAttribute(eAttributes, att);
		}

		for (var child : element.getChildren()) {
			parseNodeMeta(child, name, eclass);
		}		
		classMap.put(name, eclass);
		if (parent != null)
			createReference(parent, eclass);
	}

	private static void addAttribute(EList<EStructuralFeature> features, Attribute att) {
		var factory = EcorePackage.eINSTANCE.getEcoreFactory();
		var eAttr = factory.createEAttribute();
		eAttr.setName(att.getName());
		eAttr.setEType(getDataType(att.getValue()));
		features.add(eAttr);
	}


	private static void createReference(EClass parent, EClass child) {
		var eref = EcoreFactory.eINSTANCE.createEReference();
		eref.setName(child.getName().toLowerCase());
		eref.setContainment(true);
		eref.setEType(child);
		parent.getEStructuralFeatures().add(eref);
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
