package org.emoflon.ibex.tgg.operational.monitoring.data;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

public class ProtocolStep {
	private int index;
	private String ruleName;
	private Collection<EObject> srcElements;
	private Collection<EObject> trgElements;
	private Collection<EObject> corrElements;
	private Collection<EObject> matchSrcElements;
	private Collection<EObject> matchTrgElements;

	public ProtocolStep(int pIndex, Collection<EObject> pSrcElements, Collection<EObject> pTrgElements,
			Collection<EObject> pCorrElements, Collection<EObject> matchSrcElements, Collection<EObject> matchTrgElements, String pRuleName) {
		index = pIndex;
		setSrcElements(pSrcElements);
		setTrgElements(pTrgElements);
		setCorrElements(pCorrElements);
		setMatchSrcElements(matchSrcElements);
		setMatchTrgElements(matchTrgElements);
		ruleName = pRuleName;
	}

	public int getIndex() {
		return index;
	}

	public String getRuleName() {
		return ruleName;
	}

	public Collection<EObject> getCorrElements() {
		return corrElements;
	}

	public void setCorrElements(Collection<EObject> corrElements) {
		this.corrElements = corrElements;
	}

	public Collection<EObject> getTrgElements() {
		return trgElements;
	}

	public void setTrgElements(Collection<EObject> trgElements) {
		this.trgElements = trgElements;
	}

	public Collection<EObject> getSrcElements() {
		return srcElements;
	}

	public void setSrcElements(Collection<EObject> srcElements) {
		this.srcElements = srcElements;
	}

	public Collection<EObject> getMatchTrgElements() {
		return matchTrgElements;
	}

	public void setMatchTrgElements(Collection<EObject> matchTrgElements) {
		this.matchTrgElements = matchTrgElements;
	}

	public Collection<EObject> getMatchSrcElements() {
		return matchSrcElements;
	}

	public void setMatchSrcElements(Collection<EObject> matchSrcElements) {
		this.matchSrcElements = matchSrcElements;
	}
}