package org.emoflon.ibex.tgg.core.compiler;

import org.eclipse.emf.ecore.EReference;

public class EdgePatternNaming {
	
	private static final String EMFEdge = "EMFEdge";
	
	private static final String EdgeWrapper = "EdgeWrapper";
	
	private static final String CreateEdgeWrapper = "Create" + EdgeWrapper;
	
	private static final String DeleteEdgeWrapper = "Delete" + EdgeWrapper;
	
	public static String getSuffix(EReference ref){
		return ref.getEContainingClass().getName() + "_" + ref.getEType().getName() + "_" + ref.getName();
	}
	
	public static String getEMFEdge(EReference ref){
		return EMFEdge + getSuffix(ref);
	}
	
	public static String getEdgeWrapper(EReference ref){
		return EdgeWrapper + getSuffix(ref);
	}
	
	public static String getCreateEdgeWrapper(EReference ref){
		return CreateEdgeWrapper + getSuffix(ref);
	}
	
	public static String getDeleteEdgeWrapper(EReference ref){
		return DeleteEdgeWrapper + getSuffix(ref);
	}
	
}
