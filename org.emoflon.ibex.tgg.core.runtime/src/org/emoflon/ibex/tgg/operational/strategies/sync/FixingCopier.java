package org.emoflon.ibex.tgg.operational.strategies.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

public class FixingCopier extends Copier {

	private static final long serialVersionUID = 1L;

	public static Collection<EObject> fixAll(Collection<? extends EObject> eObjects) {
		FixingCopier copier = new FixingCopier();
		Collection<EObject> result = new ArrayList<>();

		for (EObject t : eObjects) {
			if (!copier.containsKey(t))
				result.add(copier.copy(t));
		}

		copier.copyReferences();
		return result;
	}

	@Override
	protected void copyContainment(EReference eReference, EObject eObject, EObject copyEObject) {
		if (eObject.eIsSet(eReference)) {
			EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
			if (setting != null) {
				Object value = eObject.eGet(eReference);
				@SuppressWarnings("unchecked")
				List<EObject> target = (List<EObject>) value;

				if (eReference.isMany()) {
					setting.set(fixAll(target));
				} else {
					// Fixing has to be done here as value is a list with exactly one element!
					setting.set(copy((EObject) target.get(0)));
				}
			}
		}
	}

	protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
		if (eObject.eIsSet(eReference)) {
			EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
			if (setting != null) {
				Object value = eObject.eGet(eReference, resolveProxies);
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
						copyEObject.eSet(eReference, null);
					} else if (value instanceof List) {
						// Fix: value is actually the only element in the list!
						@SuppressWarnings("unchecked")
						List<EObject> list = (List<EObject>) value;
						value = list.isEmpty() ? null : list.get(0);
						Object copyReferencedEObject = get(value);
						if (copyReferencedEObject == null) {
							if (useOriginalReferences && eReference.getEOpposite() == null) {
								copyEObject.eSet(eReference, value);
							}
						} else {
							copyEObject.eSet(eReference, copyReferencedEObject);
						}
					}
				}
			}
		}
	}

}
