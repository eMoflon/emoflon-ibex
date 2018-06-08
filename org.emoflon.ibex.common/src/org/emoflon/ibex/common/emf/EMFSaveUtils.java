package org.emoflon.ibex.common.emf;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * Utility methods for saving models as xmi file.
 */
public class EMFSaveUtils {
	private static String fileExtension = "xmi";

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
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(fileName + "." + fileExtension));
		resource.getContents().addAll(elements);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resourceSet;
	}
}
