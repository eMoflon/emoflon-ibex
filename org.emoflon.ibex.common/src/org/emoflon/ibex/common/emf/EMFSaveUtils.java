package org.emoflon.ibex.common.emf;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.moflon.smartemf.persistence.SmartEMFResource;
import org.moflon.smartemf.runtime.util.SmartEMFUtil;

/**
 * Utility methods for resource-related operations.
 * 
 * TODO Rename this class to EMFResourceUtils
 */
public class EMFSaveUtils {

	private static final String fileExtension = "xmi";

	/**
	 * Saves the given list in a file with the given name.
	 * 
	 * @param elements the list to save
	 * @param fileName the target file name
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

	public static void resolveAll(ResourceSet resourceSet) {
		if (checkForSmartEMFResources(resourceSet))
			SmartEMFUtil.resolveAll(resourceSet);
		else
			EcoreUtil.resolveAll(resourceSet);
	}

	public static void resolveAll(Resource resource) {
		ResourceSet resourceSet = resource.getResourceSet();

		boolean isSmartEMF = false;
		if (resourceSet != null) {
			isSmartEMF = checkForSmartEMFResources(resourceSet);
		} else {
			if (resource instanceof SmartEMFResource)
				isSmartEMF = true;
		}

		if (isSmartEMF)
			SmartEMFUtil.resolveAll(resource);
		else
			EcoreUtil.resolveAll(resource);
	}

	private static boolean checkForSmartEMFResources(ResourceSet resourceSet) {
		final String heterogeneousResources = "To function properly SmartEMF resources cannot be combined with non-SmartEMF resources!";

		boolean isSmartEMF = false;
		boolean notSmartEMF = false;
		for (Resource resource : resourceSet.getResources()) {
			if (resource instanceof SmartEMFResource) {
				if (notSmartEMF)
					throw new IllegalStateException(heterogeneousResources);
				isSmartEMF = true;
			} else if (isSmartEMF) {
				throw new IllegalStateException(heterogeneousResources);
			} else {
				notSmartEMF = true;
			}
		}

		return isSmartEMF;
	}

}
