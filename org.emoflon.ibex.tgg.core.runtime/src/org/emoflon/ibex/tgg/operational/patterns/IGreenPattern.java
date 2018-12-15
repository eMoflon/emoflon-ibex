package org.emoflon.ibex.tgg.operational.patterns;

import java.util.ArrayList;
import java.util.Collection;

import org.emoflon.ibex.tgg.operational.csp.IRuntimeTGGAttrConstrContainer;
import org.emoflon.ibex.tgg.operational.matches.IMatch;

import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public interface IGreenPattern {

	/** The src nodes to be created by applying this pattern **/
	Collection<TGGRuleNode> getSrcNodes();

	/** The src edges to be created by applying this pattern **/
	Collection<TGGRuleEdge> getSrcEdges();

	/** The trg nodes to be created by applying this pattern **/
	Collection<TGGRuleNode> getTrgNodes();

	/** The trg edges to be created by applying this pattern **/
	Collection<TGGRuleEdge> getTrgEdges();

	/** The corr nodes to be created by applying this pattern **/
	Collection<TGGRuleCorr> getCorrNodes();
	
	// TODO lfritsche: always generate these links in the future
	/** The corr edges to be created by applying this pattern **/
	Collection<TGGRuleEdge> getCorrEdges();
	
	/** All nodes to be marked by applying this pattern **/
	Collection<TGGRuleNode> getNodesMarkedByPattern();

	/** All context nodes that already have to be marked to apply this pattern **/
	Collection<TGGRuleNode> getMarkedContextNodes();

	/** All edges marked by applying this pattern **/
	Collection<TGGRuleEdge> getEdgesMarkedByPattern();

	/** All context edges that already have to be marked to apply this pattern **/
	Collection<TGGRuleEdge> getMarkedContextEdges();
	
	/** The set of attribute constraints to be solved when applying this pattern **/
	IRuntimeTGGAttrConstrContainer getAttributeConstraintContainer(IMatch match);

	/** Decide at runtime if the match is to be ignored for this pattern **/
	boolean isToBeIgnored(IMatch match);

	/** Decide how to create markers for match **/
	void createMarkers(String ruleName, IMatch match);
	
	default Collection<TGGRuleEdge> getSrcTrgEdgesCreatedByPattern(){
		ArrayList<TGGRuleEdge> allCreatedEdges = new ArrayList<>();
		allCreatedEdges.addAll(getSrcEdges());
		allCreatedEdges.addAll(getTrgEdges());
		return allCreatedEdges;
	}

	default Collection<TGGRuleNode> getSrcTrgNodesCreatedByPattern(){
		ArrayList<TGGRuleNode> allCreatedNodes = new ArrayList<>();
		allCreatedNodes.addAll(getSrcNodes());
		allCreatedNodes.addAll(getTrgNodes());
		return allCreatedNodes;
	}


}
