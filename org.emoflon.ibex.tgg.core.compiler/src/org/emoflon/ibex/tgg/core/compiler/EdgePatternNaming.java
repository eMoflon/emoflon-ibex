package org.emoflon.ibex.tgg.core.compiler;

import org.eclipse.emf.ecore.EReference;

public class EdgePatternNaming {
	
	private static final String EdgePattern = "EdgePattern_";
	
	public static String getSuffix(EReference ref){
		return ref.getEContainingClass().getName() + "_" + ref.getEType().getName() + "_eMoflonEdge_" + ref.getName();
	}
	
	public static String getEdgePatternNaming(EReference ref){
		return EdgePattern + getSuffix(ref);
	}
	
}
