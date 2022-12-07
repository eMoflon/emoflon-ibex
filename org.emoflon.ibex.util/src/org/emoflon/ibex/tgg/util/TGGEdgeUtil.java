package org.emoflon.ibex.tgg.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.common.engine.SimpleMatch;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;


public class TGGEdgeUtil {
	public static EMFEdge getRuntimeEdge(SimpleMatch match, TGGEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSource().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTarget().getName());
		EReference ref = specificationEdge.getType();
		if(src == null || trg == null)
			return null;
		return new EMFEdge(src, trg, ref);
	}
}
