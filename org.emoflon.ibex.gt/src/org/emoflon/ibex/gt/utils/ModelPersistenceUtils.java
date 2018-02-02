package org.emoflon.ibex.gt.utils;

import java.io.IOException;
import java.util.Collections;

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
	 * @param content
	 *            the content to save
	 * @param fileName
	 *            the target file name
	 * @return the resource set which contains the created resource
	 */
	public static ResourceSet saveModel(final EObject content, final String fileName) {
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(fileName + "." + fileExtension));
		resource.getContents().add(content);
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resourceSet;
	}
}
