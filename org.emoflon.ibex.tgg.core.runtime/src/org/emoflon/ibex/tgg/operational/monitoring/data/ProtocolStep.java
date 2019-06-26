package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

public class ProtocolStep {
	private int index;
	private Set<EObject> srcElements;
	private Set<EObject> trgElements;
	private Set<EObject> corrElements;

	public ProtocolStep(int pIndex, Set<EObject> pSrcElements, Set<EObject> pTrgElements, Set<EObject> pCorrElements) {
		index = pIndex;
		srcElements = pSrcElements;
		trgElements = pTrgElements;
		corrElements = pCorrElements;
	}

	public int getIndex() {
		return index;
	}

	public Set<EObject> getSrcElements() {
		return srcElements;
	}

	public Set<EObject> getTrgElements() {
		return trgElements;
	}

	public Set<EObject> getCorrElements() {
		return corrElements;
	}
}
