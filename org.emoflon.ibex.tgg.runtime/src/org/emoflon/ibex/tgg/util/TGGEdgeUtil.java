package org.emoflon.ibex.tgg.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.matches.ITGGMatch;

import language.TGGRuleEdge;

public class TGGEdgeUtil {
	public static EMFEdge getRuntimeEdge(ITGGMatch match, TGGRuleEdge specificationEdge) {
		EObject src = (EObject) match.get(specificationEdge.getSrcNode().getName());
		EObject trg = (EObject) match.get(specificationEdge.getTrgNode().getName());
		EReference ref = specificationEdge.getType();
		if(src == null || trg == null)
			return null;
		return new EMFEdge(src, trg, ref);
	}
}
