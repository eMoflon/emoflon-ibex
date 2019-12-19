package org.emoflon.ibex.tgg.operational.repair.shortcut;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.repair.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.repair.util.TGGUtil;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;

/**
 * This class contains a shortcut rule which applied transforms a source rule application into a target rule application.
 * It is created by a TGGOverlap element which contains the information about which elements of the former source rule application
 * are to be preserved. Elements that are created in the source rule but not in the target rule are deleted by the shortcut rule.
 * Elements created by the target rule but not by the source rule are created. Elements created by both rules are preserved.
 * 
 * @author lfritsche
 *
 */
public class ShortcutRule {
	private TGGRule sourceRule;
	private TGGRule targetRule;
	
	private Map<String, TGGRuleNode> name2node;
	
	private Collection<TGGRuleNode> nodes;
	private Collection<TGGRuleEdge> edges;
	private Collection<TGGRuleNode> mergedNodes;
	private Collection<TGGRuleNode> preservedNodes;
	
	private TGGOverlap overlap;

	private Collection<String> nodeNames;
	private Collection<String> edgeNames;
	
	private Map<TGGRuleNode, TGGRuleNode> src2newNodes;
	private Map<TGGRuleNode, TGGRuleNode> trg2newNodes;
	
	private Map<String, TGGRuleNode> sourceName2oldNodes;
	private Map<String, TGGRuleNode> targetName2oldNodes;

	public ShortcutRule(TGGOverlap overlap) {
		this.overlap = overlap;
		
		src2newNodes = cfactory.createObjectToObjectHashMap();
		trg2newNodes = cfactory.createObjectToObjectHashMap();

		sourceName2oldNodes = cfactory.createObjectToObjectHashMap();
		targetName2oldNodes = cfactory.createObjectToObjectHashMap();
		nodeNames = cfactory.createObjectSet();
		edgeNames = cfactory.createObjectSet();
		
		name2node = cfactory.createObjectToObjectHashMap();
		nodes = cfactory.createObjectSet();
		edges = cfactory.createObjectSet();
		mergedNodes = cfactory.createObjectSet();
		preservedNodes = cfactory.createObjectSet();
		
		sourceRule = overlap.sourceRule;
		targetRule = overlap.targetRule;
		
		initialize(overlap);
	}
	
	public ShortcutRule copy() {
		return new ShortcutRule(overlap);
	}

	private void initialize(TGGOverlap overlap) {
		// TODO lfritsche : implement inheritance concept
		initializeContext(overlap);
		initializeCreate(overlap);
		initializeDeletions(overlap);
	}
	
	private void initializeDeletions(TGGOverlap overlap) {
		for(TGGRuleNode node : extractNodes(overlap.deletions)) {
			createNewNode(node, BindingType.DELETE, SCInputRule.SOURCE);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.deletions)) {
			createNewEdge(edge, BindingType.DELETE, SCInputRule.SOURCE);
		}
	}

	private void initializeCreate(TGGOverlap overlap) {
		for(TGGRuleNode node : extractNodes(overlap.creations)) {
			createNewNode(node, BindingType.CREATE, SCInputRule.TARGET);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.creations)) {
			createNewEdge(edge, BindingType.CREATE, SCInputRule.TARGET);
		}
	}
	
	private void initializeContext(TGGOverlap overlap) {
		for(TGGRuleNode node : extractNodes(overlap.unboundSrcContext)) {
			createNewNode(node, BindingType.CONTEXT, SCInputRule.SOURCE);
		}
		for(TGGRuleNode node : extractNodes(overlap.unboundTrgContext)) {
			createNewNode(node, BindingType.CONTEXT, SCInputRule.TARGET);
		}
		for(TGGRuleNode node : extractNodes(overlap.mappings.keySet())) {
			TGGRuleNode newNode = createNewNode(node, BindingType.CONTEXT);
			mergedNodes.add(newNode);
			if(node.getBindingType() == BindingType.CREATE)
				preservedNodes.add(newNode);
		}
		
		for(TGGRuleEdge edge : extractEdges(overlap.unboundSrcContext)) {
			createNewEdge(edge, BindingType.CONTEXT, SCInputRule.SOURCE);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.unboundTrgContext)) {
			createNewEdge(edge, BindingType.CONTEXT, SCInputRule.TARGET);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.mappings.keySet())) {
			createNewEdge(edge, BindingType.CONTEXT);
		}
	}
	
	private void registerNewNode(TGGRuleNode oldNode, TGGRuleNode newNode, SCInputRule scInput) {
		if(scInput == SCInputRule.SOURCE) 
			src2newNodes.put(oldNode, newNode);
		trg2newNodes.put(oldNode, newNode);
	}
	
	private TGGRuleNode createNewNode(TGGRuleNode node, BindingType binding) {
		return createNewNode(node, binding, SCInputRule.SOURCE);
	}
	
	private TGGRuleNode createNewNode(TGGRuleNode node, BindingType binding, SCInputRule scInput) {
		TGGRuleNode newNode = node instanceof TGGRuleCorr ? LanguageFactoryImpl.eINSTANCE.createTGGRuleCorr() : LanguageFactoryImpl.eINSTANCE.createTGGRuleNode();
		registerNewNode(node, newNode, scInput);
		if(overlap.mappings.containsKey(node)) {
			// TODO larsF, adrianM
			// Target is hardcoded -> we iterate first over the source elements for mappings -> source should already be registered (DIRTY)
			registerNewNode((TGGRuleNode) overlap.mappings.get(node), newNode, SCInputRule.TARGET);
			sourceName2oldNodes.put(node.getName(), node);
			targetName2oldNodes.put(overlap.mappings.get(node).getName(), (TGGRuleNode) overlap.mappings.get(node));
		}
		else {
			if(sourceRule.getNodes().contains(node))
				sourceName2oldNodes.put(node.getName(), node);
			if(targetRule.getNodes().contains(node))
				targetName2oldNodes.put(node.getName(), node);
		}

		String name = node.getName();
		if(nodeNames.contains(name)) {
			int i = 2;
			while(nodeNames.contains(name + i)) {
				i++;
			}
			name += i;
		}
		
		newNode.setBindingType(binding);
		newNode.setDomainType(node.getDomainType());
		newNode.setName(name);
		newNode.setType(node.getType());
		nodes.add(newNode);
		name2node.put(newNode.getName(), newNode);
		if(nodeNames.contains(name)) {
			throw new RuntimeException("Shortcutrules are not allowed to have multiple nodes with the same name");
		}
		nodeNames.add(name);
		return newNode;
	}
	
	public Collection<TGGRuleNode> getTargetRuleMappings(DomainType dType, BindingType bType) {
		Collection<TGGRuleNode> nodes = targetRule.getNodes().stream().filter(n -> trg2newNodes.containsKey(n)).map(n -> trg2newNodes.get(n)).collect(Collectors.toList());
		return TGGUtil.filterNodes(nodes, dType, bType);
	}
	
	// This method is only used for mapped edges so we do not have to care about source or target rule
	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding) {
		return createNewEdge(edge, binding, SCInputRule.SOURCE);
	}
	
	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding, SCInputRule scInput) {
		TGGRuleEdge newEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setSrcNode(mapRuleNodeToSCRuleNode(edge.getSrcNode(), scInput));
		newEdge.setTrgNode(mapRuleNodeToSCRuleNode(edge.getTrgNode(), scInput));
		if(newEdge.getSrcNode() == null || newEdge.getTrgNode() == null) {
			throw new RuntimeException("Shortcutrules - new edge must have src and trg unequals null");
		}
		newEdge.setName(newEdge.getSrcNode().getName() + "__"+ edge.getType().getName() + "__" + newEdge.getTrgNode().getName());
		newEdge.setType(edge.getType());
		edges.add(newEdge);
		if(edgeNames.contains(newEdge.getName())) {
			throw new RuntimeException("Shortcutrules are not allowed to have duplicate edges");
		}
		edgeNames.add(newEdge.getName());
		return newEdge;
	}
	
	private Collection<TGGRuleNode> extractNodes(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleNode).map(e -> (TGGRuleNode) e).collect(Collectors.toList());
	}
	
	private Collection<TGGRuleEdge> extractEdges(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleEdge).map(e -> (TGGRuleEdge) e).collect(Collectors.toList());
	}
	
	public TGGRuleNode getNode(String name) {
		return name2node.getOrDefault(name, null);
	}
	
	public Collection<TGGRuleNode> getNodes() {
		return nodes;
	}
	
	public Collection<TGGRuleEdge> getEdges() {
		return edges;
	}
	
	public Collection<TGGRuleNode> getMergedNodes() {
		return mergedNodes;
	}
	
	public Collection<TGGRuleNode> getPreservedNodes() {
		return preservedNodes;
	}
	
	public TGGRule getSourceRule() {
		return sourceRule;
	}
	
	public TGGRule getTargetRule() {
		return targetRule;
	}
	
	public boolean isApplicable(String ruleName) {
		return ruleName.equals(sourceRule.getName());
	}
	
	public TGGRuleNode mapRuleNodeToSCRuleNode(TGGRuleNode node, SCInputRule scInput) {
		if(scInput == SCInputRule.SOURCE)
			return src2newNodes.get(node);
		return trg2newNodes.getOrDefault(node, null);
	}
	
	public TGGRuleNode mapSrcToSCNodeNode(String name) {
		return src2newNodes.getOrDefault(sourceName2oldNodes.getOrDefault(name, null), null);
	}
	
	public TGGRuleNode mapTrgToSCNodeNode(String name) {
		return trg2newNodes.getOrDefault(targetName2oldNodes.getOrDefault(name, null), null);
	}
	
	public TGGOverlap getOverlap() {
		return overlap;
	}
	
	@Override
	public String toString() {
		String name = "Shortcut-Rule - sourceRule: " + sourceRule.getName()+ " targetRule: " + targetRule.getName() + "\n";
		name += "nodes: \n";
		for(TGGRuleNode node : nodes) {
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		name += "edges: \n";
		for(TGGRuleEdge edge : edges) {
			name += "    " + edge.getName() + " : " + edge.getType().getName() + " - " + edge.getBindingType().getName() + "\n";
		}
		return name;
	}

	public String getName() {
		return sourceRule.getName() + "_SC_" + targetRule.getName();
	}
	
	public enum SCInputRule {
		SOURCE,
		TARGET
	}
}
