package org.emoflon.ibex.tgg.operational.repair.shortcut.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.lambda.CSPCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.lambda.EdgeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.lambda.Lookup;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.lambda.NACNodeCheck;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.lambda.NodeCheck;

import language.TGGRuleNode;

/**
 * This class is a container for a search plan consisting of an ordered list of lookup operation and
 * maps for node checks, edge checks and nac checks
 * @author lfritsche
 *
 */
public class SearchPlan {

	public List<Pair<SearchKey, Lookup>> lookUpPlan;
	public Map<TGGRuleNode, NodeCheck> key2nodeCheck;
	public Map<SearchKey, EdgeCheck> key2edgeCheck;
	public Map<SearchKey, NACNodeCheck> key2nacNodeCheck;
	public CSPCheck cspCheck;

	public SearchPlan(List<Pair<SearchKey, Lookup>> lookUpPlan, 
			Map<TGGRuleNode, NodeCheck> key2nodeCheck, 
			Map<SearchKey, EdgeCheck> key2edgeCheck, 
			Map<SearchKey, NACNodeCheck> key2nacNodeCheck,
			CSPCheck cspCheck) {
		this.lookUpPlan = lookUpPlan;
		this.key2nodeCheck = key2nodeCheck;
		this.key2edgeCheck = key2edgeCheck;
		this.key2nacNodeCheck = key2nacNodeCheck;
		this.cspCheck = cspCheck;
	}
}
