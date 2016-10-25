package org.emoflon.ibex.tgg.core.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public class TGGRuleApplication {
	
	private HashSet<EObject> createdSrc = new HashSet<>();
	
	private HashSet<EObject> createdTrg = new HashSet<>();
	
	private HashSet<EObject> createdCorr = new HashSet<>();
	
	private HashSet<EObject> contextSrc = new HashSet<>();
	
	private HashSet<EObject> contextTrg = new HashSet<>();
	
	private HashSet<EObject> contextCorr = new HashSet<>();
	
	//TODO: node mappings are not provided yet
	private Map<String,EObject> nodeMappings = new HashMap<>();


	private String name;
	
	public TGGRuleApplication(String name){
		this.name = name;
	}
	
	public HashSet<EObject> getCreatedSrc() {
		return createdSrc;
	}

	public HashSet<EObject> getCreatedTrg() {
		return createdTrg;
	}

	public HashSet<EObject> getCreatedCorr() {
		return createdCorr;
	}

	public HashSet<EObject> getContextSrc() {
		return contextSrc;
	}

	public HashSet<EObject> getContextTrg() {
		return contextTrg;
	}

	public HashSet<EObject> getContextCorr() {
		return contextCorr;
	}
	
	public String getName(){
		return name;
	}
	
	public Map<String, EObject> getNodeMappings() {
		return nodeMappings;
	}
	
}
