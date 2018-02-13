package org.emoflon.ibex.common.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Utility methods for saving models as xmi file.
 * 
 * @author Patrick Robrecht
 * @version 0.1
 */
public class ModelPersistenceUtils {
	private static String fileExtension = "xmi";

	/**
	 * Saves the given content in a file with the given name.
	 * 
	 * @param element
	 *            the element to save
	 * @param fileName
	 *            the target file name
	 * @return the resource set which contains the created resource
	 */
	public static ResourceSet saveModel(final EObject element, final String fileName) {
		return saveModel(r -> r.add(element), fileName);
	}

	/**
	 * Saves the given list in a file with the given name.
	 * 
	 * @param elements
	 *            the list to save
	 * @param fileName
	 *            the target file name
	 * @return the resource set which contains the created resource
	 */
	public static ResourceSet saveModel(final Collection<? extends EObject> elements, final String fileName) {
		return saveModel(r -> r.addAll(elements), fileName);
	}

	/**
	 * Creates a resource set containing a resource with the content provided by the
	 * Consumer.
	 * 
	 * @param contentProvider
	 *            a Consumer which adds content to the resource
	 * @param fileName
	 *            the target file name
	 * @return the resource set which contains the created resource
	 */
	private static ResourceSet saveModel(final Consumer<EList<EObject>> contentProvider, final String fileName) {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(fileName + "." + fileExtension));
		contentProvider.accept(resource.getContents());
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resourceSet;
	}
}
