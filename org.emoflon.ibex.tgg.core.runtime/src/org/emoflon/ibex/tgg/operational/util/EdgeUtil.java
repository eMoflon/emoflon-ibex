package org.emoflon.ibex.tgg.operational.util;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.EList;
import org.moflon.core.utilities.eMoflonEMFUtil;

import runtime.Edge;
import runtime.RuntimeFactory;

public class EdgeUtil {
	
	public static void createEdgeWrappers(Resource from, Resource to){
		from.getAllContents().forEachRemaining(node -> {

			if (node.eContainmentFeature() != null && node.eContainmentFeature().getEOpposite() != null) {
				Edge edge = RuntimeFactory.eINSTANCE.createEdge();
				edge.setName(node.eContainmentFeature().getEOpposite().getName());
				edge.setSrc(node);
				edge.setTrg(node.eContainer());
				to.getContents().add(edge);
			}

			// Retrieve all EReference instances
			Set<EStructuralFeature> references = eMoflonEMFUtil.getAllReferences(node);

			// Iterate through all references
			for (EStructuralFeature reference : references) {
				if (!reference.isDerived()) {
					// Check if the reference to be handled is a containment
					// edge
					// (i.e.,
					// node contains s.th.)
					if (reference.getUpperBound() != 1)
						// Edge is n-ary: edge exists only once, but points to
						// many
						// contained EObjects
						for (EObject containedObject : (EList<EObject>) node.eGet(reference, true)) {
							// Create the wrapper and set the appropriate values
							Edge edge = RuntimeFactory.eINSTANCE.createEdge();
							edge.setName(reference.getName());
							edge.setSrc(node);
							edge.setTrg(containedObject);
							to.getContents().add(edge);

						}
					// else a standard reference was found
					else {
						// Create the wrapper and set the appropriate values
						Edge edge = RuntimeFactory.eINSTANCE.createEdge();
						edge.setName(reference.getName());
						edge.setSrc(node);
						edge.setTrg((EObject) node.eGet(reference, true));
						to.getContents().add(edge);
					}
				}

			}

		});
	}
	
	
	public static void applyEdges(Collection<Edge> edges){
		edges.forEach(e -> performActionOnFeature(e, (f, o) -> ((EList) e.getSrc().eGet(f)).add(o), e.getSrc()::eSet));
	}


	private static void performActionOnFeature(Edge e, BiConsumer<EStructuralFeature, EObject> actionMany, BiConsumer<EStructuralFeature, EObject> actionOne) {
		EStructuralFeature feature = e.getSrc().eClass().getEStructuralFeature(e.getName());
		if(!feature.isDerived()){
			if (feature.isMany()) {
				actionMany.accept(feature, e.getTrg());
			} else
				actionOne.accept(feature, e.getTrg());
		}

	}
}
