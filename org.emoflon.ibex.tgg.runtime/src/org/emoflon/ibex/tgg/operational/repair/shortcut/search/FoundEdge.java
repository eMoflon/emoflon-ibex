package org.emoflon.ibex.tgg.runtime.repair.shortcut.search;

import java.util.Objects;

import org.eclipse.emf.ecore.EReference;

public class FoundEdge {

	public Object sourceNode;
	public Object targetNode;
	public EReference edgeType;
	
	public FoundEdge(Object sourceNode, Object targetNode, EReference edgeType) {
		this.sourceNode = sourceNode;
		this.targetNode = targetNode;
		this.edgeType = edgeType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof FoundEdge otherKey) {
			return sourceNode.equals(otherKey.sourceNode) &&
					targetNode.equals(otherKey.targetNode) &&
						edgeType.equals(otherKey.edgeType);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(sourceNode, targetNode, edgeType);
	}
}
 