package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGOverlap;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import language.BindingType;
import language.TGGRule;
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
	
	private Collection<String> nodeNames;
	private Map<TGGRuleNode, TGGRuleNode> old2newNodes;
	
	public ShortcutRule(TGGOverlap overlap) {
		old2newNodes = new Object2ObjectOpenHashMap<>();
		nodeNames = new ObjectOpenHashSet<>();
		
		name2node = new Object2ObjectOpenHashMap<>();
		nodes = new ObjectArrayList<>();
		edges = new ObjectArrayList<>();
		
		sourceRule = overlap.sourceRule;
		targetRule = overlap.targetRule;
		
		initialize(overlap);
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
		for(TGGRuleNode node : extractNodes(overlap.targetContext)) {
			createNewNode(node, BindingType.CONTEXT);
		}
		for(TGGRuleNode node : extractNodes(overlap.mappings.keySet())) {
			old2newNodes.put((TGGRuleNode) overlap.mappings.get(node), createNewNode(node, BindingType.CONTEXT));
		}
		for(TGGRuleEdge edge : extractEdges(overlap.targetContext)) {
			createNewEdge(edge, BindingType.CONTEXT);
		}
		for(TGGRuleEdge edge : extractEdges(overlap.mappings.keySet())) {
			createNewEdge(edge, BindingType.CONTEXT);
		}
	}
	
	private TGGRuleNode createNewNode(TGGRuleNode node, BindingType binding) {
		TGGRuleNode newNode = LanguageFactoryImpl.eINSTANCE.createTGGRuleNode();
		old2newNodes.put(node, newNode);
		
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
		nodeNames.add(name);
		return newNode;
	}
	
	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding) {
		TGGRuleEdge newEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setName(edge.getName());
		newEdge.setType(edge.getType());
		newEdge.setSrcNode(old2newNodes.get(edge.getSrcNode()));
		newEdge.setTrgNode(old2newNodes.get(edge.getTrgNode()));
		edges.add(newEdge);
		return newEdge;
	}
	
	private Collection<TGGRuleNode> extractNodes(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleNode).map(e -> (TGGRuleNode) e).collect(Collectors.toList());
	}
	
	private Collection<TGGRuleEdge> extractEdges(Collection<TGGRuleElement> elements) {
		return elements.stream().filter(e -> e instanceof TGGRuleEdge).map(e -> (TGGRuleEdge) e).collect(Collectors.toList());
	}
	
	private TGGRuleNode getNode(String name) {
		return name2node.getOrDefault(name, null);
	}
	
	private Collection<TGGRuleNode> getNodes() {
		return nodes;
	}
	
	private Collection<TGGRuleEdge> getEdges() {
		return edges;
	}
	
	private TGGRule getSourceRule() {
		return sourceRule;
	}
	
	private TGGRule getTargetRule() {
		return targetRule;
	}
	
	private boolean isApplicable(String ruleName) {
		return ruleName.equals(sourceRule.getName());
	}
	
	@Override
	public String toString() {
		String name = "Shortcut-Rule - sourceRule: " + sourceRule.getName()+ " targetRule: " + targetRule.getName() + "\n";
		name += "nodes: \n";
		for(TGGRuleNode node : nodes) {
			name += "    " + node.getName() + " : " + node.getType().getName() + "\n";
		}
		name += "edges: \n";
		for(TGGRuleEdge edge : edges) {
			name += "    " + edge.getName() + " : " + edge.getType().getName() + "\n";
		}
		return name;
	}
}
