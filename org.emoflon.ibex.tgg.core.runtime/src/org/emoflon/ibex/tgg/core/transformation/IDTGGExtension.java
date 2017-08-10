package org.emoflon.ibex.tgg.core.transformation;

import org.apache.commons.lang3.tuple.Triple;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.ibex.tgg.operational.strategies.cc.CC;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

public class IDTGGExtension {
	public CC cc;
	public Resource srcRes;
	public Resource trgRes;
	
	public IDTGGExtension(Resource srcRes, Resource trgRes) {
		this.srcRes = srcRes;
		this.trgRes = trgRes;
	}
	
	public IDTGGExtension(Resource srcRes, Resource trgRes, CC cc) {
		this.srcRes = srcRes;
		this.trgRes = trgRes;
		this.cc = cc;
	}
	
	public CC performCC() {
		return (CC) Platform.getAdapterManager().loadAdapter(this, "org.emoflon.ibex.tgg.operational.strategies.cc.CC");
	}
	
	public SYNC performSYNC() {
		return (SYNC) Platform.getAdapterManager().loadAdapter(this, "org.emoflon.ibex.tgg.operational.strategies.sync.SYNC");
	}
}
