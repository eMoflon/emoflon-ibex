package org.emoflon.ibex.tgg.operational.edge;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class RuntimeEdge {

	
	protected EObject src;
	protected EObject trg;
	protected EReference ref;

	public RuntimeEdge(EObject src, EObject trg, EReference ref) {
		this.src = src;
		this.trg = trg;
		this.ref = ref;
	}
	
	public EObject getSrc() {
		return src;
	}

	public EObject getTrg() {
		return trg;
	}

	public EReference getRef() {
		return ref;
	}




}
