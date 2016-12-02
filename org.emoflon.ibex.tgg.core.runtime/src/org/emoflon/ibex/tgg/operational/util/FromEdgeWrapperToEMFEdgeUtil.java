package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import runtime.Edge;

/**
 * @author leblebici
 * Util class for creating (deleting) an EMF-Edge (EstructuralFeature) for a created (deleted) Edge
 */

public class FromEdgeWrapperToEMFEdgeUtil {

	public static void applyEdges(Collection<Edge> edges) {
		edges.forEach(e -> performApplyEdgeActionOnFeature(e, (f, o) -> ((EList) e.getSrc().eGet(f)).add(o),
				e.getSrc()::eSet));
	}

	public static void revokeEdge(Edge edge) {
		performRevokeEdgeActionOnFeature(edge, (f,o) -> ((EList)edge.getSrc().eGet(f)).remove(o), edge.getSrc()::eUnset);
	}

	private static void performApplyEdgeActionOnFeature(Edge e, BiConsumer<EStructuralFeature, EObject> actionMany,
			BiConsumer<EStructuralFeature, EObject> actionOne) {
		EStructuralFeature feature = e.getSrc().eClass().getEStructuralFeature(e.getName());
		if (!feature.isDerived()) {

			if (((EReference) feature).isContainment()) {
				e.getTrg().eResource().getContents().remove(e.getTrg());
			}

			if (feature.isMany()) {
				actionMany.accept(feature, e.getTrg());
			} else
				actionOne.accept(feature, e.getTrg());
		}

	}

	private static void performRevokeEdgeActionOnFeature(Edge e, BiConsumer<EStructuralFeature, EObject> actionMany,
			Consumer<EStructuralFeature> actionOne) {
		EStructuralFeature feature = e.getSrc().eClass().getEStructuralFeature(e.getName());

		EObject src = e.getSrc();
		EObject trg = e.getTrg();

		if (!feature.isDerived()) {
			if (feature.isMany() && ((EList) src.eGet(feature)).contains(trg)) {
				actionMany.accept(feature, trg);
			} else if(src.eGet(feature) == trg){
				actionOne.accept(feature);
			}				
		}
	}
}
