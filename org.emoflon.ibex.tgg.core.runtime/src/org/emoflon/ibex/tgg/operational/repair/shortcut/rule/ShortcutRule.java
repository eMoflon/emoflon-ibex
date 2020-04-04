package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGAttributeExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.impl.LanguageFactoryImpl;

/**
 * This class contains a shortcut rule which applied transforms a original rule
 * application into a replacing rule application. It is created by a TGGOverlap
 * element which contains the information about which elements of the former
 * original rule application are to be preserved. Elements that are created in
 * the original rule but not in the replacing rule are deleted by the shortcut
 * rule. Elements created by the replacing rule but not by the original rule are
 * created. Elements created by both rules are preserved.
 * 
 * @author lfritsche
 *
 */
public class ShortcutRule {
	private TGGRule originalRule;
	private TGGRule replacingRule;

	private Map<String, TGGRuleNode> name2newNode;

	private Collection<TGGRuleNode> nodes;
	private Collection<TGGRuleEdge> edges;
	private Collection<TGGRuleNode> mergedNodes;
	private Collection<TGGRuleNode> preservedNodes;

	private TGGOverlap overlap;
	private boolean relaxedPatternMatching;

	private Collection<String> nodeNames;
	private Collection<String> edgeNames;

	private Map<TGGRuleNode, TGGRuleNode> original2newNodes;
	private Map<TGGRuleNode, TGGRuleNode> replacing2newNodes;

	private Map<String, TGGRuleNode> originalName2oldNodes;
	private Map<String, TGGRuleNode> replacingName2oldNodes;

	public ShortcutRule(TGGOverlap overlap, boolean relaxedPatternMatching) {
		this.overlap = overlap;
		this.relaxedPatternMatching = relaxedPatternMatching;

		original2newNodes = cfactory.createObjectToObjectHashMap();
		replacing2newNodes = cfactory.createObjectToObjectHashMap();

		originalName2oldNodes = cfactory.createObjectToObjectHashMap();
		replacingName2oldNodes = cfactory.createObjectToObjectHashMap();
		nodeNames = cfactory.createObjectSet();
		edgeNames = cfactory.createObjectSet();

		name2newNode = cfactory.createObjectToObjectHashMap();
		nodes = cfactory.createObjectSet();
		edges = cfactory.createObjectSet();
		mergedNodes = cfactory.createObjectSet();
		preservedNodes = cfactory.createObjectSet();

		originalRule = overlap.originalRule;
		replacingRule = overlap.replacingRule;

		initialize(overlap);
	}

	public ShortcutRule copy() {
		return new ShortcutRule(overlap, relaxedPatternMatching);
	}

	private void initialize(TGGOverlap overlap) {
		initializeContext(overlap);
		initializeCreate(overlap);
		initializeDelete(overlap);
		adaptInplaceAttrExprs();
	}

	private void initializeDelete(TGGOverlap overlap) {
		for (TGGRuleNode node : extractNodes(overlap.deletions))
			createNewNode(node, BindingType.DELETE, SCInputRule.ORIGINAL);
		for (TGGRuleEdge edge : extractEdges(overlap.deletions))
			createNewEdge(edge, BindingType.DELETE, SCInputRule.ORIGINAL);
	}

	private void initializeCreate(TGGOverlap overlap) {
		for (TGGRuleNode node : extractNodes(overlap.creations))
			createNewNode(node, BindingType.CREATE, SCInputRule.REPLACING);
		for (TGGRuleEdge edge : extractEdges(overlap.creations))
			createNewEdge(edge, BindingType.CREATE, SCInputRule.REPLACING);
	}

	private void initializeContext(TGGOverlap overlap) {
		for (TGGRuleNode node : extractNodes(overlap.unboundOriginalContext))
			createNewNode(node, relaxedPatternMatching ? BindingType.RELAXED : BindingType.CONTEXT, SCInputRule.ORIGINAL);
		for (TGGRuleNode node : extractNodes(overlap.unboundReplacingContext))
			createNewNode(node, BindingType.CONTEXT, SCInputRule.REPLACING);
		for (TGGRuleNode node : extractNodes(overlap.mappings.keySet()))
			createNewMergedNode(node, (TGGRuleNode) overlap.mappings.get(node));

		for (TGGRuleEdge edge : extractEdges(overlap.unboundOriginalContext))
			createNewEdge(edge, relaxedPatternMatching ? BindingType.RELAXED : BindingType.CONTEXT, SCInputRule.ORIGINAL);
		for (TGGRuleEdge edge : extractEdges(overlap.unboundReplacingContext))
			createNewEdge(edge, BindingType.CONTEXT, SCInputRule.REPLACING);
		for (TGGRuleEdge edge : extractEdges(overlap.mappings.keySet()))
			createNewEdge(edge, BindingType.CONTEXT);
	}

	private void adaptInplaceAttrExprs() {
		nodes.stream() //
				.flatMap(n -> n.getAttrExpr().stream()) //
				.filter(e -> e.getValueExpr() instanceof TGGAttributeExpression) //
				.map(e -> (TGGAttributeExpression) e.getValueExpr()) //
				.forEach(attrExpr -> attrExpr.setObjectVar(replacing2newNodes.get(attrExpr.getObjectVar())));
	}

	private void createNewNode(TGGRuleNode oldNode, BindingType binding, SCInputRule scInput) {
		TGGRuleNode newNode = createNode(oldNode.eClass(), oldNode.getName(), binding, oldNode.getDomainType(),
				oldNode.getType(), scInput == SCInputRule.REPLACING ? oldNode.getAttrExpr() : Collections.emptyList());
		registerNewNode(oldNode, newNode, scInput);
	}

	private void createNewMergedNode(TGGRuleNode originalNode, TGGRuleNode replacingNode) {
		EClass newType = originalNode.getType().isSuperTypeOf(replacingNode.getType()) ? //
				replacingNode.getType() : originalNode.getType();
		TGGRuleNode newNode = createNode(originalNode.eClass(), originalNode.getName(), BindingType.CONTEXT,
				originalNode.getDomainType(), newType, replacingNode.getAttrExpr());
		registerNewMergedNode(originalNode, replacingNode, newNode);
	}

	private void registerNewNode(TGGRuleNode oldNode, TGGRuleNode newNode, SCInputRule scInput) {
		if (scInput == SCInputRule.ORIGINAL) {
			original2newNodes.put(oldNode, newNode);
			originalName2oldNodes.put(oldNode.getName(), oldNode);
		} else {
			replacing2newNodes.put(oldNode, newNode);
			replacingName2oldNodes.put(oldNode.getName(), oldNode);
		}
		nodes.add(newNode);
		name2newNode.put(newNode.getName(), newNode);
	}

	private void registerNewMergedNode(TGGRuleNode originalNode, TGGRuleNode replacingNode, TGGRuleNode newNode) {
		original2newNodes.put(originalNode, newNode);
		originalName2oldNodes.put(originalNode.getName(), originalNode);
		replacing2newNodes.put(replacingNode, newNode);
		replacingName2oldNodes.put(replacingNode.getName(), replacingNode);

		nodes.add(newNode);
		name2newNode.put(newNode.getName(), newNode);
		mergedNodes.add(newNode);
		if (originalNode.getBindingType() == BindingType.CREATE)
			preservedNodes.add(newNode);
	}

	private TGGRuleNode createNode(EClass nodeType, String name, BindingType binding, DomainType domain, EClass type,
			List<TGGInplaceAttributeExpression> attrExprs) {
		TGGRuleNode node = (TGGRuleNode) LanguageFactory.eINSTANCE.create(nodeType);

		String adjustedName = name;
		if (nodeNames.contains(adjustedName)) {
			int i = 2;
			while (nodeNames.contains(adjustedName + i)) {
				i++;
			}
			adjustedName += i;
		}
		nodeNames.add(adjustedName);
		node.setName(adjustedName);
		node.setBindingType(binding);
		node.setDomainType(domain);
		node.setType(type);
		node.getAttrExpr().addAll(EcoreUtil.copyAll(attrExprs));

		return node;
	}

	public Collection<TGGRuleNode> getReplacingRuleMappings(DomainType dType, BindingType bType) {
		Collection<TGGRuleNode> nodes = replacingRule.getNodes().stream() //
				.filter(n -> replacing2newNodes.containsKey(n)) //
				.map(n -> replacing2newNodes.get(n)) //
				.collect(Collectors.toList());
		return TGGFilterUtil.filterNodes(nodes, dType, bType);
	}

	// This method is only used for mapped edges so we do not have to care about source or target rule
	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding) {
		return createNewEdge(edge, binding, SCInputRule.ORIGINAL);
	}

	private TGGRuleEdge createNewEdge(TGGRuleEdge edge, BindingType binding, SCInputRule scInput) {
		TGGRuleEdge newEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setType(edge.getType());
		newEdge.setSrcNode(mapRuleNodeToSCRuleNode(edge.getSrcNode(), scInput));
		newEdge.setTrgNode(mapRuleNodeToSCRuleNode(edge.getTrgNode(), scInput));
		if (newEdge.getSrcNode() == null || newEdge.getTrgNode() == null) {
			throw new RuntimeException("Shortcutrules - new edge must have src and trg unequals null");
		}

		String name = newEdge.getSrcNode().getName() + "__" + edge.getType().getName() + "__"
				+ newEdge.getTrgNode().getName();
		if (edgeNames.contains(name)) {
			int i = 2;
			while (edgeNames.contains(name + "_" + i)) {
				i++;
			}
			name += "_" + i;
		}
		newEdge.setName(name);

		edges.add(newEdge);
		if (edgeNames.contains(newEdge.getName())) {
			throw new RuntimeException("Shortcutrules are not allowed to have duplicate edges");
		}
		edgeNames.add(newEdge.getName());
		return newEdge;
	}

	private Collection<TGGRuleNode> extractNodes(Collection<TGGRuleElement> elements) {
		return elements.stream() //
				.filter(e -> e instanceof TGGRuleNode) //
				.map(e -> (TGGRuleNode) e) //
				.collect(Collectors.toList());
	}

	private Collection<TGGRuleEdge> extractEdges(Collection<TGGRuleElement> elements) {
		return elements.stream() //
				.filter(e -> e instanceof TGGRuleEdge) //
				.map(e -> (TGGRuleEdge) e) //
				.collect(Collectors.toList());
	}

	public TGGRuleNode getNode(String name) {
		return name2newNode.getOrDefault(name, null);
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

	public TGGRule getOriginalRule() {
		return originalRule;
	}

	public TGGRule getReplacingRule() {
		return replacingRule;
	}

	public boolean isApplicable(String ruleName) {
		return ruleName.equals(originalRule.getName());
	}

	public TGGRuleNode mapRuleNodeToSCRuleNode(TGGRuleNode node, SCInputRule scInput) {
		if (scInput == SCInputRule.ORIGINAL)
			return original2newNodes.get(node);
		return replacing2newNodes.getOrDefault(node, null);
	}

	public TGGRuleNode mapOriginalToSCNodeNode(String name) {
		return original2newNodes.getOrDefault(originalName2oldNodes.getOrDefault(name, null), null);
	}

	public TGGRuleNode mapReplacingToSCNodeNode(String name) {
		return replacing2newNodes.getOrDefault(replacingName2oldNodes.getOrDefault(name, null), null);
	}

	public Collection<TGGRuleNode> getNewOriginalNodes() {
		return original2newNodes.values();
	}

	public Collection<TGGRuleNode> getNewReplacingNodes() {
		return replacing2newNodes.values();
	}

	public TGGOverlap getOverlap() {
		return overlap;
	}

	@Override
	public String toString() {
		String name = "Shortcut-Rule - originalRule: " + originalRule.getName() + " replacingRule: " + replacingRule.getName() + "\n";
		name += "nodes: \n";
		for (TGGRuleNode node : nodes) {
			if (node.getName().contains("eMoflon_ProtocolNode"))
				continue;
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		name += "edges: \n";
		for (TGGRuleEdge edge : edges) {
			if (edge.getName().contains("eMoflon_ProtocolNode"))
				continue;
			name += "    " + edge.getName() + " : " + edge.getType().getName() + " - " + edge.getBindingType().getName() + "\n";
		}
		return name;
	}

	public String getName() {
		return originalRule.getName() + "_SC_" + replacingRule.getName();
	}

	public enum SCInputRule {
		ORIGINAL, REPLACING
	}
}
