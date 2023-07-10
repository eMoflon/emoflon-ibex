package org.emoflon.ibex.tgg.analysis;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;

public class FilterNACCandidate {
	private TGGNode nodeInRule;
	private EReference edgeType;
	private EdgeDirection eDirection;

	public FilterNACCandidate(TGGNode nodeInRule, EReference edgeType, EdgeDirection eDirection) {
		this.nodeInRule = nodeInRule;
		this.edgeType = edgeType;
		this.eDirection = eDirection;
	}

	public TGGNode getNodeInRule() {
		return nodeInRule;
	}

	public EReference getEdgeType() {
		return edgeType;
	}

	public EdgeDirection getEDirection() {
		return eDirection;
	}

	@Override
	public String toString() {
		return getNodeInRule().getName() + "_" + getEdgeType().getName() + "_" + edgeType.getFeatureID() + "_" + getEDirection().toString().toLowerCase() + "_"
				+ getNodeInRule().getDomainType().getName();
	}

	public EClass getOtherNodeType() {
		return getEDirection() == EdgeDirection.OUTGOING ? //
				(EClass) getEdgeType().getEType() : //
				(EClass) getEdgeType().eContainer();
	}

	public enum EdgeDirection {
		INCOMING,
		OUTGOING
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FilterNACCandidate otherCandidate) {
			return nodeInRule.equals(otherCandidate.nodeInRule) && 
					edgeType.equals(otherCandidate.edgeType) &&
					eDirection.equals(otherCandidate.eDirection);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] {nodeInRule, edgeType, eDirection});
	}
}


