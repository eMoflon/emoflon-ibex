package org.emoflon.ibex.tgg.runtime.repair.shortcut.search;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.AttrCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.CSPCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.Lookup;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.search.lambda.NodeCheck;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;

/**
 * This class is a container for a search plan consisting of an ordered list of
 * lookup operation and maps for node checks, edge checks and nac checks
 * 
 * @author lfritsche
 *
 */
public class SearchPlan {

	public List<Pair<SearchKey, Lookup>> lookUpPlan;
	public Map<TGGNode, NodeCheck> key2nodeCheck;
	public Map<TGGNode, AttrCheck> key2AttrCheck;
	public Map<SearchKey, EdgeCheck> key2edgeCheck;
	public Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	public CSPCheck cspCheck;

	public SearchPlan(List<Pair<SearchKey, Lookup>> lookUpPlan, 
			Map<TGGNode, NodeCheck> key2nodeCheck, 
			Map<TGGNode, AttrCheck> key2InplAttrCheck, 
			Map<SearchKey, EdgeCheck> key2edgeCheck, 
			Map<SearchKey, NACNodeCheck> key2nacNodeCheck,
			CSPCheck cspCheck) {
		this.lookUpPlan = lookUpPlan;
		this.key2nodeCheck = key2nodeCheck;
		this.key2AttrCheck = key2InplAttrCheck;
		this.key2edgeCheck = key2edgeCheck;
		this.key2nacNodeCheck = key2nacNodeCheck;
		this.cspCheck = cspCheck;
	}
	
}
