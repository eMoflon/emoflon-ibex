package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public class TGGObjectGraph {
    private Collection<EObject> srcElements;
    private Collection<EObject> trgElements;
    private Collection<EObject> corrElements;

    public TGGObjectGraph(Collection<EObject> pSrcElements, Collection<EObject> pTrgElements,
	    Collection<EObject> pCorrElements) {
	srcElements = pSrcElements;
	trgElements = pTrgElements;
	corrElements = pCorrElements;
    }

    public Collection<EObject> getSrcElements() {
	return srcElements;
    }

    public Collection<EObject> getTrgElements() {
	return trgElements;
    }

    public Collection<EObject> getCorrElements() {
	return corrElements;
    }
}
