package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.ecore.util.InternalEList;

public class FixingCopier extends Copier {

	private static final long serialVersionUID = 1L;

	public static void fixAll(Resource t, Resource c, String structuralFeature) {
		// NOTE: The variables are named according to their role for forward transformation.
		// For backward transformation, flip source and target!
		
		Collection<EObject> trgObjects = t.getContents();
		Collection<EObject> corrObjects = c.getContents();
		
		FixingCopier copier = new FixingCopier();

		// Collect new target objects
		copier.copyAll(trgObjects);
				
		// Change correspondences to new target objects
		Collection<EObject> corrResult = new ArrayList<>();
		for (EObject cOld : corrObjects) {
			if (!copier.containsKey(cOld)) {
				EStructuralFeature trgFeature = cOld.eClass().getEStructuralFeature(structuralFeature);
				EObject tOld = (EObject)cOld.eGet(trgFeature);
				EObject tNew = copier.get(tOld);
			
				cOld.eSet(trgFeature, tNew);
				corrResult.add(cOld);
			}
		}
		
		copier.copyContainments();
		copier.copyReferences();
		
		Collection<EObject> trgResult = copier.values()
				.stream()
				.filter(o -> o.eContainer() == null)
				.collect(Collectors.toList());
		
		t.getContents().clear();
		t.getContents().addAll(trgResult);		
		c.getContents().clear();
		c.getContents().addAll(corrResult);
	}

	private Collection<Runnable> copyContainments = new ArrayList<Runnable>();
	
	private void copyContainments() {
		copyContainments.forEach(r -> r.run());
	}

	@Override
	protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
		copyContainments.add(() -> {
			int dynamicFeatureID = eObject.eClass().getFeatureID(eReference);
			if (eObject.eIsSet(eReference)) {
				EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
				if (setting != null) {
					DynamicEObjectImpl dynObj = ((DynamicEObjectImpl) eObject);
					Object value = dynObj.dynamicGet(dynamicFeatureID);

					if (eReference.isMany()) {
						@SuppressWarnings("unchecked")
						List<EObject> target = (List<EObject>) value;
						setting.set(target.stream()
										  .map(this::get)
										  .collect(Collectors.toList()));
					} else {
						// Fixing has to be done here as value is a list with exactly one element!
						@SuppressWarnings("unchecked")
						List<EObject> target = (List<EObject>) value;
						if(!target.isEmpty())
							setting.set(get((EObject) target.get(0)));
					}
				}
			}
		});
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
