package org.emoflon.ibex.tgg.operational.strategies.opt;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.emoflon.ibex.common.collections.ObjectToIntMap;

public class MetamodelRelaxer {
	
	// Hash maps to save the old metamodel state
	protected ObjectToIntMap<EReference> referenceToUpperBound = cfactory.createObjectToIntHashMap();
	protected ObjectToIntMap<EReference> referenceToLowerBound = cfactory.createObjectToIntHashMap();
	protected Map<EReference, EReference> referenceToEOpposite = cfactory.createObjectToObjectHashMap();
	protected Map<EReference, Boolean> referenceToContainment = cfactory.createObjectToObjectHashMap();

	public void unrelaxReferences(final EList<EPackage> model) {

		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						// Get old metamodel values
						int upperBound = referenceToUpperBound.getInt(reference);
						int lowerBound = referenceToLowerBound.getInt(reference);
						boolean containment = referenceToContainment.get(reference);
						EReference eOpposite = referenceToEOpposite.get(reference);

						// Change metamodel values
						reference.setUpperBound(upperBound);
						reference.setLowerBound(lowerBound);
						reference.setContainment(containment);
						reference.setEOpposite(eOpposite);

						// Reset setting for reference
						((EStructuralFeatureImpl) reference).setSettingDelegate(null);
					}
				}
			}
		}
	}
	
	public void relaxReferences(final EList<EPackage> model) {
		EPackage[] packages = (EPackage[]) model.toArray();

		for (EPackage p : packages) {
			TreeIterator<EObject> it = p.eAllContents();

			while (it.hasNext()) {
				EObject next = it.next();
				if (next instanceof EClassImpl) {
					EClassImpl nextEClassImpl = (EClassImpl) next;

					for (EReference reference : nextEClassImpl.getEAllReferences()) {
						if (referenceToUpperBound.containsKey(reference)
								&& referenceToLowerBound.containsKey(reference)
								&& referenceToContainment.containsKey(reference)
								&& referenceToEOpposite.containsKey(reference)) {
							// Reference already exists, values must not be overwritten
							continue;
						}

						// Save metamodel values
						referenceToUpperBound.put(reference, reference.getUpperBound());
						referenceToLowerBound.put(reference, reference.getLowerBound());
						referenceToContainment.put(reference, reference.isContainment());
						referenceToEOpposite.put(reference, reference.getEOpposite());

						// Change metamodel values
						reference.setUpperBound(-1);
						reference.setLowerBound(0);
						reference.setContainment(false);
						reference.setEOpposite(null);
					}
				}
			}
		}
	}
}
