package org.emoflon.ibex.tgg.operational.strategies.integrate.delta;

import org.emoflon.ibex.common.emf.EMFEdge;
import org.emoflon.ibex.tgg.operational.strategies.integrate.INTEGRATE;
import org.emoflon.ibex.tgg.operational.strategies.integrate.util.ModelChangeProtocol.GroupKey;

import delta.AttributeDelta;
import delta.Delta;
import delta.DeltaContainer;
import delta.Link;
import delta.StructuralDelta;

public class ModelChangeInterface {

	private INTEGRATE integrate;

	public ModelChangeInterface(INTEGRATE integrate) {
		this.integrate = integrate;
	}

	public GroupKey applyUserDelta(DeltaContainer deltas) {
		GroupKey key = integrate.getModelChangeProtocol().new GroupKey();
		integrate.getModelChangeProtocol().setGroupKey(key);

		deltas.getDeltas().forEach(delta -> apply(delta));

		integrate.getModelChangeProtocol().unsetGroupKey();
		return key;
	}

	private void apply(Delta delta) {
		delta.getAttributeDeltas().forEach(attrDelta -> applyAttributeDelta(attrDelta));
		StructuralDelta strDelta = delta.getStructuralDelta();
		if (strDelta != null)
			applyStructuralDelta(strDelta);
	}

	private void applyAttributeDelta(AttributeDelta attrDelta) {
		attrDelta.getObject().eSet(attrDelta.getAttribute(), attrDelta.getNewValue());
	}

	private void applyStructuralDelta(StructuralDelta strDelta) {
		strDelta.getDeletedObjects().forEach(obj -> integrate.getModelChangeProtocol().util.deleteElement(obj, false));
		strDelta.getDeletedLinks().forEach(link -> {
			EMFEdge edge = createEMFEdgeFromLink(link);
			if (!integrate.getModelChangeProtocol().getCurrentModelChanges().isDeleted(edge))
				integrate.getModelChangeProtocol().util.deleteEdge(edge);
		});

		strDelta.getCreatedLinks()
				.forEach(link -> integrate.getModelChangeProtocol().util.createEdge(createEMFEdgeFromLink(link)));
	}

	private EMFEdge createEMFEdgeFromLink(Link link) {
		return new EMFEdge(link.getSrc(), link.getTrg(), link.getType());
	}

}
