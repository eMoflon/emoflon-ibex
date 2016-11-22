package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.function.BiConsumer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import runtime.Edge;
import runtime.RuntimeFactory;

public class EdgeUtil {
	
	
	public static void applyEdges(Collection<Edge> edges){
		edges.forEach(e -> performActionOnFeature(e, (f, o) -> ((EList) e.getSrc().eGet(f)).add(o), e.getSrc()::eSet));
	}


	private static void performActionOnFeature(Edge e, BiConsumer<EStructuralFeature, EObject> actionMany, BiConsumer<EStructuralFeature, EObject> actionOne) {
		EStructuralFeature feature = e.getSrc().eClass().getEStructuralFeature(e.getName());
		e.getSrc().eSetDeliver(false);
		e.getTrg().eSetDeliver(false);
		if(!feature.isDerived()){
			
			if(((EReference)feature).isContainment()){
				e.getTrg().eResource().getContents().remove(e.getTrg());
			}
			
			if (feature.isMany()) {
				actionMany.accept(feature, e.getTrg());
			} else
				actionOne.accept(feature, e.getTrg());
		}

	}
}
