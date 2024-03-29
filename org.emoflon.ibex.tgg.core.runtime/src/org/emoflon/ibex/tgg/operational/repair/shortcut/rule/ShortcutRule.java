package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGAttributeExpression;
import language.TGGInplaceAttributeExpression;
import language.TGGRule;
import language.TGGRuleCorr;
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
	private IbexOptions options;
	
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

	public ShortcutRule(TGGOverlap overlap, IbexOptions options) {
		this.options = options;
		this.overlap = overlap;
		this.relaxedPatternMatching = options.repair.relaxedSCPatternMatching();

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

		initialize();
	}

	public ShortcutRule copy() {
		return new ShortcutRule(overlap, options);
	}

	private void initialize() {
		initializeDeleteNodes();
		initializeContextNodes();
		initializeCreateNodes();
		
		initializeDeleteEdges();
		initializeContextEdges();
		initializeCreateEdges();
		
		adaptInplaceAttrExprs();
	}

	private void initializeDeleteNodes() {
		for (TGGRuleNode node : extractNodes(overlap.deletions))
			createNewNode(node, BindingType.DELETE, SCInputRule.ORIGINAL);
	}
	
	private void initializeDeleteEdges() {
		for (TGGRuleEdge edge : extractEdges(overlap.deletions))
			createNewEdge(edge, BindingType.DELETE, SCInputRule.ORIGINAL);
	}

	private void initializeCreateNodes() {
		for (TGGRuleNode node : extractNodes(overlap.creations))
			createNewNode(node, BindingType.CREATE, SCInputRule.REPLACING);
	}
	
	private void initializeCreateEdges() {
		for (TGGRuleEdge edge : extractEdges(overlap.creations))
			createNewEdge(edge, BindingType.CREATE, SCInputRule.REPLACING);
	}

	private void initializeContextNodes() {
		for (TGGRuleNode node : extractNodes(overlap.mappings.keySet()))
			createNewMergedNode(node, (TGGRuleNode) overlap.mappings.get(node));
		for (TGGRuleNode node : extractNodes(overlap.unboundOriginalContext))
			createNewNodeIfNecessary(node, relaxedPatternMatching ? BindingType.RELAXED : BindingType.CONTEXT, SCInputRule.ORIGINAL);
		for (TGGRuleNode node : extractNodes(overlap.unboundReplacingContext))
			createNewNode(node, BindingType.CONTEXT, SCInputRule.REPLACING);

	}
	
	private void initializeContextEdges() {
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
	
	private void createNewNodeIfNecessary(TGGRuleNode oldNode, BindingType binding, SCInputRule scInput) {
		if(options.repair.omitUnnecessaryContext()) {
			if(scInput == SCInputRule.ORIGINAL) {
				boolean isNecessary = overlap.deletions.stream() //
						.anyMatch(e -> oldNode.getIncomingEdges().contains(e) || oldNode.getOutgoingEdges().contains(e));
				if(isNecessary)
					createNewNode(oldNode, binding, scInput);
				else {
					// this only works for original nodes as they are processed first
					// block this name of this node
					nodeNames.add(oldNode.getName());
				}
			}
		}
		else
			createNewNode(oldNode, binding, scInput);
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
		// don't change how name allocation is done here, other code depends on it!
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
	private void createNewEdge(TGGRuleEdge edge, BindingType binding) {
		createNewEdge(edge, binding, SCInputRule.ORIGINAL);
	}

	private void createNewEdge(TGGRuleEdge edge, BindingType binding, SCInputRule scInput) {
		TGGRuleNode srcSCRuleNode = mapRuleNodeToSCNode(edge.getSrcNode(), scInput);
		TGGRuleNode trgSCRuleNode = mapRuleNodeToSCNode(edge.getTrgNode(), scInput);
		
		// if src or trg rule were not generated due to optimization, do not create this edge
		if(srcSCRuleNode == null || trgSCRuleNode == null) 
			if(options.repair.omitUnnecessaryContext())
				return;
			else
				throw new RuntimeException("Cannot create edge in short-cut rule because src or target are null!");
		else {
			// if this edge originates from the original rule and is context, we may also omit it
			if(options.repair.omitUnnecessaryContext() && scInput == SCInputRule.ORIGINAL) {
				if(edge.getBindingType() == BindingType.CONTEXT)
					return;
			}
		}
		TGGRuleEdge newEdge = LanguageFactoryImpl.eINSTANCE.createTGGRuleEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setType(edge.getType());
		newEdge.setSrcNode(srcSCRuleNode);
		newEdge.setTrgNode(trgSCRuleNode);
		if (newEdge.getSrcNode() == null || newEdge.getTrgNode() == null) {
			throw new RuntimeException("Shortcutrules - new edge must have src and trg unequals null");
		}

		String name = newEdge.getSrcNode().getName() + "__" + edge.getType().getName() + "__"
				+ newEdge.getTrgNode().getName();
		// don't change how name allocation is done here, other code depends on it!
		if (edgeNames.contains(name)) {
			int i = 2;
			while (edgeNames.contains(name + "-" + i)) {
				i++;
			}
			name += "-" + i;
		}
		newEdge.setName(name);

		edges.add(newEdge);
		if (edgeNames.contains(newEdge.getName())) {
			throw new RuntimeException("Shortcutrules are not allowed to have duplicate edges");
		}
		edgeNames.add(newEdge.getName());

		// add missing source/target node references for correspondence nodes:
		if (newEdge.getSrcNode() instanceof TGGRuleCorr corrNode) {
			switch (newEdge.getTrgNode().getDomainType()) {
				case SRC -> corrNode.setSource(newEdge.getTrgNode());
				case TRG -> corrNode.setTarget(newEdge.getTrgNode());
				default -> throw new IllegalArgumentException("Unexpected value: " + newEdge.getTrgNode().getDomainType());
			}
		}
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

	public TGGRuleNode mapRuleNodeToSCNode(TGGRuleNode node, SCInputRule scInput) {
		return switch (scInput) {
			case ORIGINAL -> original2newNodes.get(node);
			case REPLACING -> replacing2newNodes.get(node);
		};
	}

	public TGGRuleNode mapOriginalNodeNameToSCNode(String name) {
		return original2newNodes.getOrDefault(originalName2oldNodes.getOrDefault(name, null), null);
	}

	public TGGRuleNode mapReplacingNodeNameToSCNode(String name) {
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
		String name = "Shortcut-Rule - " + getName() + "\n";
		name += "Unbound Nodes: \n";
		for (TGGRuleNode node : nodes) {
			if(mergedNodes.contains(node))
				continue;
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		
		name += "Merged Nodes: \n";
		for (TGGRuleNode node : mergedNodes) {
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		
		name += "Edges: \n";
		for (TGGRuleEdge edge : edges) {
			name += "    " + edge.getName() + " : " + edge.getType().getName() + " - " + edge.getBindingType().getName() + "\n";
		}
		return name;
	}

	public String getName() {
		return originalRule.getName() + "->" + replacingRule.getName() + "_" + overlap.category;
	}

	public enum SCInputRule {
		ORIGINAL, REPLACING
	}
}
