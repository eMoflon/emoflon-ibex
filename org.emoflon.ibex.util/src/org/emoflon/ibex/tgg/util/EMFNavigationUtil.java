package org.emoflon.ibex.tgg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class is a util that helps which backward navigation between EMF objects.
 * 
 * @author lfritsche
 *
 */
public class EMFNavigationUtil {
	
	public static List<?> getOppositeReference(final EObject target, final EClass sourceType,
			final String targetRoleName) {
		Collection<Setting> settings = getInverseReferences(target);

		List<EObject> returnList = new ArrayList<EObject>();
		for (Setting setting : settings) {
			EObject candidate = getCandidateObject(sourceType, targetRoleName, setting);
			if (candidate != null)
				returnList.add(candidate);
		}

		EObject eContainer = target.eContainer();
		if (eContainer != null) {
			Setting setting = (((InternalEObject) eContainer).eSetting(target.eContainmentFeature()));
			EObject candidate = getCandidateObject(sourceType, targetRoleName, setting);
			if (candidate != null)
				returnList.add(candidate);
		}

		return returnList;
	}
	
	private static EObject getCandidateObject(final EClass sourceType, final String targetRoleName,
			final Setting setting) {
		if (setting.getEStructuralFeature().getName().equals(targetRoleName)) {
			EClassifier clazz = setting.getEObject().eClass();

			if (clazz.getName().equals(sourceType.getName()))
				return setting.getEObject();
		}

		return null;
	}

	private static Collection<Setting> getInverseReferences(final EObject target) {
		ECrossReferenceAdapter adapter = getCRAdapter(target);
		return adapter.getNonNavigableInverseReferences(target, true);
	}
	
	private static ECrossReferenceAdapter getCRAdapter(final EObject target) {
		// Determine context
		Notifier context = null;

		EObject root = EcoreUtil.getRootContainer(target, true);
		Resource resource = root.eResource();

		if (resource != null) {
			ResourceSet resourceSet = resource.getResourceSet();
			if (resourceSet != null)
				context = resourceSet;
			else
				context = resource;
		} else
			context = root;

		// Retrieve adapter and create+add on demand
		ECrossReferenceAdapter adapter = ECrossReferenceAdapter.getCrossReferenceAdapter(context);
		if (adapter == null) {
			adapter = new ECrossReferenceAdapter();
			context.eAdapters().add(adapter);
		}

		return adapter;
	}
}
