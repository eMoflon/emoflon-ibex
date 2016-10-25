package org.emoflon.ibex.tgg.core.algorithm;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.set.hash.TIntHashSet;

public abstract class Protocol {

	protected TIntObjectHashMap<TGGRuleApplication> intToMatch = new TIntObjectHashMap<>();
	protected TObjectIntHashMap<TGGRuleApplication> matchToInt = new TObjectIntHashMap<>();

	protected HashMap<EObject, TIntHashSet> contextSrcToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdSrcToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> contextCorrToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdCorrToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> contextTrgToMatch = new HashMap<>();
	protected HashMap<EObject, TIntHashSet> createdTrgToMatch = new HashMap<>();

	private int idCounter = 1;
	
	public Protocol(){
		
	}
	
	public Protocol(Resource protocolResource){
		//TODO: load from EMF
	}

	public void calculateTables(TGGRuleApplication m) {
		matchToInt.put(m, idCounter);
		intToMatch.put(idCounter, m);
		idCounter++;
		
		m.getContextSrc().forEach(e -> addMatchToTable(contextSrcToMatch, e, m));
		m.getCreatedSrc().forEach(e -> addMatchToTable(createdSrcToMatch, e, m));

		m.getContextTrg().forEach(e -> addMatchToTable(contextTrgToMatch, e, m));
		m.getCreatedTrg().forEach(e -> addMatchToTable(createdTrgToMatch, e, m));
		
		m.getContextCorr().forEach(e -> addMatchToTable(contextCorrToMatch, e, m));
		m.getCreatedCorr().forEach(e -> addMatchToTable(createdCorrToMatch, e, m));
	}
	
	protected TGGRuleApplication intToMatch(int id) {
		return intToMatch.get(id);
	}
	
	protected int matchToInt(TGGRuleApplication m){
		return matchToInt.get(m);
	}
	
	private void addMatchToTable(HashMap<EObject, TIntHashSet> table, EObject e, TGGRuleApplication m){
		TIntHashSet creatorsOfE = null;
		if(table.containsKey(e))
			creatorsOfE = table.get(e);
		else{
			creatorsOfE = new TIntHashSet();
			table.put(e, creatorsOfE);
		}
		creatorsOfE.add(matchToInt(m));
	}
	
	protected TIntHashSet getCreatorsOf(EObject e) {
		if (createdSrcToMatch.containsKey(e))
			return createdSrcToMatch.get(e);
		if (createdTrgToMatch.containsKey(e))
			return createdTrgToMatch.get(e);
		if (createdCorrToMatch.containsKey(e))
			return createdCorrToMatch.get(e);
		return null;
	}
	
	public void toEMF(Resource protocolResource){
		//TODO: convert to EMF
	}
}
