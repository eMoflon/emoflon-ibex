package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGOverlap;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;

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
	private Map<TGGRuleNode, TGGRuleNode> old2newNodes;
	private Map<String, TGGRuleNode> sourceName2oldNodes;
	private Map<String, TGGRuleNode> targetName2oldNodes;
	
	public ShortcutRule(TGGOverlap overlap) {
		this.overlap = overlap;
		
		old2newNodes = new Object2ObjectOpenHashMap<>();
		sourceName2oldNodes = new Object2ObjectOpenHashMap<>();
		targetName2oldNodes = new Object2ObjectOpenHashMap<>();
		nodeNames = new ObjectOpenHashSet<>();
		edgeNames = new ObjectOpenHashSet<>();
		
		name2node = new Object2ObjectOpenHashMap<>();
		nodes = new ObjectArrayList<>();
		edges = new ObjectArrayList<>();
		mergedNodes = new ObjectArrayList<>();
		preservedNodes = new ObjectArrayList<>();
		
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
			createNewNode(node, BindingType.DELETE);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.deletions)) {
			createNewEdge(edge, BindingType.DELETE);
		}
	}

	private void initializeCreate(TGGOverlap overlap) {
		for(TGGRuleNode node : extractNodes(overlap.creations)) {
			createNewNode(node, BindingType.CREATE);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.creations)) {
			createNewEdge(edge, BindingType.CREATE);
		}
	}
	
	private void initializeContext(TGGOverlap overlap) {
		for(TGGRuleNode node : extractNodes(overlap.unboundContext)) {
			createNewNode(node, BindingType.CONTEXT);
		}
		for(TGGRuleNode node : extractNodes(overlap.mappings.keySet())) {
			TGGRuleNode newNode = createNewNode(node, BindingType.CONTEXT);
			mergedNodes.add(newNode);
			if(node.getBindingType() == BindingType.CREATE)
				preservedNodes.add(newNode);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.unboundContext)) {
			createNewEdge(edge, BindingType.CONTEXT);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.mappings.keySet())) {
			createNewEdge(edge, BindingType.CONTEXT);
		}
	}
	
	private TGGRuleNode createNewNode(TGGRuleNode node, BindingType binding) {
		TGGRuleNode newNode = node instanceof TGGRuleCorr ? LanguageFactoryImpl.eINSTANCE.createTGGRuleCorr() : LanguageFactoryImpl.eINSTANCE.createTGGRuleNode();
		old2newNodes.put(node, newNode);
		if(overlap.mappings.containsKey(node)) {
			old2newNodes.put((TGGRuleNode) overlap.mappings.get(node), newNode);
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
		Collection<TGGRuleNode> nodes = targetRule.getNodes().stream().filter(n -> old2newNodes.containsKey(n)).map(n -> old2newNodes.get(n)).collect(Collectors.toList());
		return TGGCollectionUtil.filterNodes(nodes, dType, bType);
	}
	
	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding) {
		TGGRuleEdge newEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setSrcNode(old2newNodes.get(edge.getSrcNode()));
		newEdge.setTrgNode(old2newNodes.get(edge.getTrgNode()));
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
	
	public TGGRuleNode mapRuleNodeToSCRuleNode(TGGRuleNode node) {
		return old2newNodes.getOrDefault(node, null);
	}
	
	public TGGRuleNode mapSrcToSCNodeNode(String name) {
		return mapRuleNodeToSCRuleNode(sourceName2oldNodes.getOrDefault(name, null));
	}
	
	public TGGRuleNode mapTrgToSCNodeNode(String name) {
		return mapRuleNodeToSCRuleNode(targetName2oldNodes.getOrDefault(name, null));
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
}
