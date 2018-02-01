package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.util.InternalEList;

public class FixingCopier extends Copier {

	private static final long serialVersionUID = 1L;

	public static void fixAll(Resource t, Resource c) {
		
		Collection<EObject> trgObjects = t.getContents();
		Collection<EObject> corrObjects = c.getContents();
		
		FixingCopier copier = new FixingCopier();
		Collection<EObject> trgResult = new ArrayList<>();
		Collection<EObject> corrResult = new ArrayList<>();
		
		//HashMap<EObject,EObject> originalToCopy = new HashMap<EObject, EObject>();

		// Collect new target objects
		for (EObject tOld : trgObjects) {
			if (!copier.containsKey(tOld)) {
				EObject tNew = copier.copy(tOld);
				trgResult.add(tNew);
				//originalToCopy.put(tOld, tNew);
			}
		}

		// Change correspondences to new target objects
		for (EObject cOld : corrObjects) {
			if (!copier.containsKey(cOld)) {
				EStructuralFeature trgFeature = cOld.eClass().getEStructuralFeature("target");
				EObject tOld = (EObject)cOld.eGet(trgFeature);
				EObject tNew = copier.get(tOld);
				
				EStructuralFeature srcFeature = cOld.eClass().getEStructuralFeature("source");
				EObject sOld = (EObject)cOld.eGet(srcFeature);
			
				//EObject cNew = copier.copy(cOld);
				cOld.eSet(trgFeature, tNew);
				//cNew.eSet(srcFeature, sOld);
				corrResult.add(cOld);
			}
		}
		
		copier.copyReferences();
		
		t.getContents().clear();
		t.getContents().addAll(trgResult);		
		c.getContents().clear();
		c.getContents().addAll(corrResult);
	}

	@Override
	protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
		int dynamicFeatureID = eObject.eClass().getFeatureID(eReference);
		if (eObject.eIsSet(eReference)) {
			EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
			if (setting != null) {
				DynamicEObjectImpl dynObj = ((DynamicEObjectImpl) eObject);
				Object value = dynObj.dynamicGet(dynamicFeatureID);

				if (eReference.isMany()) {
					@SuppressWarnings("unchecked")
					List<EObject> target = (List<EObject>) value;
					setting.set(copyAll(target));
				} else {
					// Fixing has to be done here as value is a list with exactly one element!
					@SuppressWarnings("unchecked")
					List<EObject> target = (List<EObject>) value;
					if(!target.isEmpty())
						setting.set(copy((EObject) target.get(0)));
				}
			}
		}
	}

	@Override
	protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
		int dynamicFeatureID = eObject.eClass().getFeatureID(eReference);
		DynamicEObjectImpl dynObj = ((DynamicEObjectImpl) eObject);
		Object value = dynObj.dynamicGet(dynamicFeatureID);
		
		if (eObject.eIsSet(eReference)) {
			EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
			if (setting != null) {
				if (eReference.isMany()) {
					@SuppressWarnings("unchecked")
					InternalEList<EObject> source = (InternalEList<EObject>) value;
					@SuppressWarnings("unchecked")
					InternalEList<EObject> target = (InternalEList<EObject>) setting;
					if (source.isEmpty()) {
						target.clear();
					} else {
						boolean isBidirectional = eReference.getEOpposite() != null;
						int index = 0;
						for (Iterator<EObject> k = resolveProxies ? source.iterator() : source.basicIterator(); k
								.hasNext();) {
							EObject referencedEObject = k.next();
							EObject copyReferencedEObject = get(referencedEObject);
							if (copyReferencedEObject == null) {
								if (useOriginalReferences && !isBidirectional) {
									target.addUnique(index, referencedEObject);
									++index;
								}
							} else {
								if (isBidirectional) {
									int position = target.indexOf(copyReferencedEObject);
									if (position == -1) {
										target.addUnique(index, copyReferencedEObject);
									} else if (index != position) {
										target.move(index, copyReferencedEObject);
									}
								} else {
									target.addUnique(index, copyReferencedEObject);
								}
								++index;
							}
						}
					}
				} else {
					// Feature is 0..1 so a fix might be necessary
					if (value == null) {
						setting.set(null);
					} else if (value instanceof List) {
						// Fix: value is actually the only element in the list!
						@SuppressWarnings("unchecked")
						List<EObject> list = (List<EObject>) value;
						EObject fixedValue = list.isEmpty() ? null : list.get(0);
						Object copyReferencedEObject = get(fixedValue);
						if (copyReferencedEObject == null) {
							if (useOriginalReferences && eReference.getEOpposite() == null) {
								setting.set(fixedValue);
							}
						} else {
							setting.set(copyReferencedEObject);
						}
					}
				}
			}
		}
	}

}
