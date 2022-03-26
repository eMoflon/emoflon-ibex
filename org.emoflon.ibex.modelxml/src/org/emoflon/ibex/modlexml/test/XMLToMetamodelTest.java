package org.emoflon.ibex.modlexml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.emoflon.ibex.modelxml.parser.XMLToMetamodelParser;
import org.junit.jupiter.api.Test;

class XMLToMetamodelTest {

	@Test
	public void test2() {

		XMLToMetamodelParser.parseXMLFileToMetaModel("misc/test1.xml");
		// Create a resource set.
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore",
				new EcoreResourceFactoryImpl());

		// Get the URI of the model file.
		URI fileURIAct = URI.createURI("misc/test1.ecore", false);
		URI fileURIExp = URI.createURI("misc/expected.ecore", false);

		// Demand load the resource for this file.
		Resource resourceAct = resourceSet.getResource(fileURIAct, true);
		Resource resourceExp = resourceSet.getResource(fileURIExp, true);

		var actual = resourceToString(resourceAct);
		var expected = resourceToString(resourceExp);
		if (actual.size() != expected.size())
			fail();
		for (int i = 0; i < actual.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
		assertEquals(expected, expected, "not Equal");

	}

	private List<String> resourceToString(Resource resource) {
		var expectContent = resource.getContents();
		EPackage pack;
		if (expectContent.get(0) instanceof EPackageImpl) {
			pack = (EPackage) expectContent.get(0);
		} else
			return null;
		var classifiers = pack.getEClassifiers();
		List<EClass> classes = classifiers.stream().map(e -> (EClass) e).toList();
		List<String> contentList = new ArrayList<String>();
		for (var run : classes) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("%s;", run.getName()));
			List<String> attributes = new ArrayList<>();
			for (var attr : run.getEAttributes()) {
				attributes.add(String.format("%s=>%s,", attr.getName(), attr.getEType().getName()));
			}
			Collections.sort(attributes);
			sb.append(attributes);
			sb.append(";");
			List<String> references = new ArrayList<>();
			for (var ref : run.getEReferences()) {
				references.add(String.format("%s=>%s,", ref.getName(), ref.getEType().getName()));
			}
			Collections.sort(references);
			sb.append(references);
			contentList.add(sb.toString());
		}
		Collections.sort(contentList);
		return contentList;
	}

}
