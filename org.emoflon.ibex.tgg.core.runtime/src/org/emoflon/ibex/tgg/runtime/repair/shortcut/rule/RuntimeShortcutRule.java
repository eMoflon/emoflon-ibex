package org.emoflon.ibex.tgg.runtime.repair.shortcut.rule;

import static org.emoflon.ibex.common.collections.CollectionFactory.cfactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeAssignment;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXAttributeValue;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.BooleanExpression;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreArithmetic.RelationalExpression;
import org.emoflon.ibex.tgg.compiler.TGGRuleDerivedFieldsTool;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.runtime.repair.shortcut.util.TGGOverlap;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.BindingType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.IBeXTGGModelFactory;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGCorrespondence;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGEdge;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGPattern;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRuleElement;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGShortcutRule;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

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
public class RuntimeShortcutRule {
	private IbexOptions options;
	
	private TGGShortcutRule shortcutRule;
	private TGGOverlap overlap;

	private boolean relaxedPatternMatching;

	private Collection<TGGNode> mergedNodes;
	private Collection<TGGNode> preservedNodes;

	private Collection<String> nodeNames;
	private Collection<String> edgeNames;

	private Map<String, TGGNode> name2newNode;
	
	private Map<TGGNode, TGGNode> original2newNodes;
	private Map<TGGNode, TGGNode> replacing2newNodes;

	private Map<String, TGGNode> originalName2oldNodes;
	private Map<String, TGGNode> replacingName2oldNodes;

	public RuntimeShortcutRule(TGGOverlap overlap, IbexOptions options) {
		this.options = options;
		this.overlap = overlap;
		this.relaxedPatternMatching = options.repair.relaxedSCPatternMatching();
		
		createShortcutRule(overlap);

		original2newNodes = cfactory.createObjectToObjectHashMap();
		replacing2newNodes = cfactory.createObjectToObjectHashMap();

		originalName2oldNodes = cfactory.createObjectToObjectHashMap();
		replacingName2oldNodes = cfactory.createObjectToObjectHashMap();
		nodeNames = cfactory.createObjectSet();
		edgeNames = cfactory.createObjectSet();

		name2newNode = cfactory.createObjectToObjectHashMap();
		mergedNodes = cfactory.createObjectSet();
		preservedNodes = cfactory.createObjectSet();

		initialize();
	}

	private void createShortcutRule(TGGOverlap overlap) {
		shortcutRule = IBeXTGGModelFactory.eINSTANCE.createTGGShortcutRule();
		shortcutRule.setOriginalRule(overlap.originalRule);
		shortcutRule.setReplacingRule(overlap.replacingRule);
		shortcutRule.setName(shortcutRule.getOriginalRule().getName() + "->" + shortcutRule.getReplacingRule().getName() + "_" + overlap.category);
	}

	public RuntimeShortcutRule copy() {
		return new RuntimeShortcutRule(overlap, options);
	}

	private void initialize() {
		initializeDeleteNodes();
		initializeContextNodes();
		initializeCreateNodes();
		
		initializeDeleteEdges();
		initializeContextEdges();
		initializeCreateEdges();
		
		adaptAttributeAssignments();
		var attributeConditions = initializeAttributeConditions();
		
		shortcutRule.getAllNodes().addAll(shortcutRule.getNodes());
		shortcutRule.getAllEdges().addAll(shortcutRule.getEdges());
		
		TGGRuleDerivedFieldsTool.fillDerivedTGGRuleFields(shortcutRule);
		TGGRuleDerivedFieldsTool.fillDerivedTGGRulePreCondition( //
				shortcutRule, attributeConditions, //
				EcoreUtil.copy(((TGGPattern) getReplacingRule().getPrecondition()).getAttributeConstraints()) //
		);
		
		initializeCSPs();
	}

	private void initializeDeleteNodes() {
		for (TGGNode node : extractNodes(overlap.deletions))
			createNewNode(node, BindingType.DELETE, SCInputRule.ORIGINAL);
	}
	
	private void initializeDeleteEdges() {
		for (TGGEdge edge : extractEdges(overlap.deletions))
			createNewEdge(edge, BindingType.DELETE, SCInputRule.ORIGINAL);
	}

	private void initializeCreateNodes() {
		for (TGGNode node : extractNodes(overlap.creations))
			createNewNode(node, BindingType.CREATE, SCInputRule.REPLACING);
	}
	
	private void initializeCreateEdges() {
		for (TGGEdge edge : extractEdges(overlap.creations))
			createNewEdge(edge, BindingType.CREATE, SCInputRule.REPLACING);
	}

	private void initializeContextNodes() {
		for (TGGNode node : extractNodes(overlap.mappings.keySet()))
			createNewMergedNode(node, (TGGNode) overlap.mappings.get(node));
		for (TGGNode node : extractNodes(overlap.unboundOriginalContext))
			createNewNodeIfNecessary(node, relaxedPatternMatching ? BindingType.RELAXED : BindingType.CONTEXT, SCInputRule.ORIGINAL);
		for (TGGNode node : extractNodes(overlap.unboundReplacingContext))
			createNewNode(node, BindingType.CONTEXT, SCInputRule.REPLACING);

	}
	
	private void initializeContextEdges() {
		for (TGGEdge edge : extractEdges(overlap.unboundOriginalContext))
			createNewEdge(edge, relaxedPatternMatching ? BindingType.RELAXED : BindingType.CONTEXT, SCInputRule.ORIGINAL);
		for (TGGEdge edge : extractEdges(overlap.unboundReplacingContext))
			createNewEdge(edge, BindingType.CONTEXT, SCInputRule.REPLACING);
		for (TGGEdge edge : extractEdges(overlap.mappings.keySet()))
			createNewEdge(edge, BindingType.CONTEXT);
	}

	private void adaptAttributeAssignments() {
		shortcutRule.getAttributeAssignments().stream() //
				.filter(a -> a.getValue() instanceof IBeXAttributeValue) //
				.map(a -> (IBeXAttributeValue) a.getValue()) //
				.forEach(v -> v.setNode(replacing2newNodes.get(v.getNode())));
	}
	
	private Collection<BooleanExpression> initializeAttributeConditions() {
		var attributeConditions = EcoreUtil.copyAll(getReplacingRule().getPrecondition().getConditions());
		
		for (var attributeCondition : attributeConditions) {
			if (!(attributeCondition instanceof RelationalExpression relationalExpression))
				throw new RuntimeException("Attribute conditions must be relational expressions!");
			if (relationalExpression.getLhs() instanceof IBeXAttributeValue attributeValue) {
				attributeValue.setNode(replacing2newNodes.get(attributeValue.getNode()));
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().add(attributeCondition);
			}
			if (relationalExpression.getRhs() instanceof IBeXAttributeValue attributeValue) {
				attributeValue.setNode(replacing2newNodes.get(attributeValue.getNode()));
				((TGGNode) attributeValue.getNode()).getReferencedByConditions().add(attributeCondition);
			}
		}
		
		return attributeConditions;
	}

	private void initializeCSPs() {
		var attributeConstraints = EcoreUtil.copy(((TGGPattern) getReplacingRule().getPrecondition()).getAttributeConstraints());
		
		for (var parameter : attributeConstraints.getParameters()) {
			if (parameter instanceof IBeXAttributeValue attributeParameter)
				attributeParameter.setNode(replacing2newNodes.get(attributeParameter.getNode()));
		}
		
		((TGGPattern) shortcutRule.getPrecondition()).setAttributeConstraints(attributeConstraints);
	}

	private void createNewNodeIfNecessary(TGGNode oldNode, BindingType binding, SCInputRule scInput) {
		if(options.repair.omitUnnecessaryContext()) {
			if(scInput == SCInputRule.ORIGINAL) {
				boolean isNecessary = overlap.deletions.stream() //
						.filter(e -> e instanceof IBeXEdge) //
						.map(e -> (IBeXEdge) e) //
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

	private void createNewNode(TGGNode oldNode, BindingType binding, SCInputRule scInput) {
		TGGNode newNode = createNode(oldNode.eClass(), oldNode.getName(), binding, oldNode.getDomainType(),
				oldNode.getType(), scInput == SCInputRule.REPLACING ? oldNode.getAttributeAssignments() : Collections.emptyList());
		registerNewNode(oldNode, newNode, scInput);
	}

	private void createNewMergedNode(TGGNode originalNode, TGGNode replacingNode) {
		EClass newType = originalNode.getType().isSuperTypeOf(replacingNode.getType()) ? //
				replacingNode.getType() : originalNode.getType();
		TGGNode newNode = createNode(originalNode.eClass(), originalNode.getName(), BindingType.CONTEXT,
				originalNode.getDomainType(), newType, replacingNode.getAttributeAssignments());
		registerNewMergedNode(originalNode, replacingNode, newNode);
	}

	private void registerNewNode(TGGNode oldNode, TGGNode newNode, SCInputRule scInput) {
		if (scInput == SCInputRule.ORIGINAL) {
			original2newNodes.put(oldNode, newNode);
			originalName2oldNodes.put(oldNode.getName(), oldNode);
		} else {
			replacing2newNodes.put(oldNode, newNode);
			replacingName2oldNodes.put(oldNode.getName(), oldNode);
		}
		shortcutRule.getNodes().add(newNode);
		name2newNode.put(newNode.getName(), newNode);
	}

	private void registerNewMergedNode(TGGNode originalNode, TGGNode replacingNode, TGGNode newNode) {
		original2newNodes.put(originalNode, newNode);
		originalName2oldNodes.put(originalNode.getName(), originalNode);
		replacing2newNodes.put(replacingNode, newNode);
		replacingName2oldNodes.put(replacingNode.getName(), replacingNode);

		shortcutRule.getNodes().add(newNode);
		name2newNode.put(newNode.getName(), newNode);
		mergedNodes.add(newNode);
		if (originalNode.getBindingType() == BindingType.CREATE)
			preservedNodes.add(newNode);
	}

	private TGGNode createNode(EClass nodeType, String name, BindingType binding, DomainType domain, EClass type,
			List<IBeXAttributeAssignment> attrAssignments) {
		TGGNode node = (TGGNode) IBeXTGGModelFactory.eINSTANCE.create(nodeType);

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
		var copiedAttributeAssignments = EcoreUtil.copyAll(attrAssignments);
		node.getAttributeAssignments().addAll(copiedAttributeAssignments);
		shortcutRule.getAttributeAssignments().addAll(copiedAttributeAssignments);

		return node;
	}

	public Collection<TGGNode> getReplacingRuleMappings(DomainType dType, BindingType bType) {
		Collection<TGGNode> nodes = shortcutRule.getReplacingRule().getNodes().stream() //
				.filter(n -> replacing2newNodes.containsKey(n)) //
				.map(n -> replacing2newNodes.get(n)) //
				.collect(Collectors.toList());
		return TGGFilterUtil.filterNodes(nodes, dType, bType);
	}

	// This method is only used for mapped edges so we do not have to care about source or target rule
	private void createNewEdge(TGGEdge edge, BindingType binding) {
		createNewEdge(edge, binding, SCInputRule.ORIGINAL);
	}

	private void createNewEdge(TGGEdge edge, BindingType binding, SCInputRule scInput) {
		TGGNode srcSCRuleNode = mapRuleNodeToSCNode(edge.getSource(), scInput);
		TGGNode trgSCRuleNode = mapRuleNodeToSCNode(edge.getTarget(), scInput);
		
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
		TGGEdge newEdge = IBeXTGGModelFactory.eINSTANCE.createTGGEdge();
		newEdge.setBindingType(binding);
		newEdge.setDomainType(edge.getDomainType());
		newEdge.setType(edge.getType());
		newEdge.setSource(srcSCRuleNode);
		newEdge.setTarget(trgSCRuleNode);
		if (newEdge.getSource() == null || newEdge.getTarget() == null) {
			throw new RuntimeException("Shortcutrules - new edge must have src and trg unequals null");
		}

		String name = newEdge.getSource().getName() + "__" + edge.getType().getName() + "__"
				+ newEdge.getTarget().getName();
		// don't change how name allocation is done here, other code depends on it!
		if (edgeNames.contains(name)) {
			int i = 2;
			while (edgeNames.contains(name + "-" + i)) {
				i++;
			}
			name += "-" + i;
		}
		newEdge.setName(name);

		shortcutRule.getEdges().add(newEdge);
		if (edgeNames.contains(newEdge.getName())) {
			throw new RuntimeException("Shortcutrules are not allowed to have duplicate edges");
		}
		edgeNames.add(newEdge.getName());

		// add missing source/target node references for correspondence nodes:
		if (newEdge.getSource() instanceof TGGCorrespondence corrNode) {
			switch (((TGGNode) newEdge.getTarget()).getDomainType()) {
				case SOURCE -> corrNode.setSource((TGGNode) newEdge.getTarget());
				case TARGET -> corrNode.setTarget((TGGNode) newEdge.getTarget());
				default -> throw new IllegalArgumentException("Unexpected value: " + ((TGGNode) newEdge.getTarget()).getDomainType());
			}
		}
	}

	private Collection<TGGNode> extractNodes(Collection<TGGRuleElement> elements) {
		return elements.stream() //
				.filter(e -> e instanceof TGGNode) //
				.map(e -> (TGGNode) e) //
				.collect(Collectors.toList());
	}

	private Collection<TGGEdge> extractEdges(Collection<TGGRuleElement> elements) {
		return elements.stream() //
				.filter(e -> e instanceof TGGEdge) //
				.map(e -> (TGGEdge) e) //
				.collect(Collectors.toList());
	}
	
	public TGGShortcutRule getShortcutRule() {
		return shortcutRule;
	}

	public TGGNode getNode(String name) {
		return name2newNode.getOrDefault(name, null);
	}

	public Collection<TGGNode> getNodes() {
		return shortcutRule.getNodes();
	}

	public Collection<TGGEdge> getEdges() {
		return shortcutRule.getEdges();
	}

	public Collection<TGGNode> getMergedNodes() {
		return mergedNodes;
	}

	public Collection<TGGNode> getPreservedNodes() {
		return preservedNodes;
	}

	public TGGRule getOriginalRule() {
		return shortcutRule.getOriginalRule();
	}

	public TGGRule getReplacingRule() {
		return shortcutRule.getReplacingRule();
	}

	public boolean isApplicable(String ruleName) {
		return ruleName.equals(getOriginalRule().getName());
	}

	public TGGNode mapRuleNodeToSCNode(IBeXNode node, SCInputRule scInput) {
		return switch (scInput) {
			case ORIGINAL -> original2newNodes.get(node);
			case REPLACING -> replacing2newNodes.get(node);
		};
	}

	public TGGNode mapOriginalNodeNameToSCNode(String name) {
		return original2newNodes.getOrDefault(originalName2oldNodes.getOrDefault(name, null), null);
	}

	public TGGNode mapReplacingNodeNameToSCNode(String name) {
		return replacing2newNodes.getOrDefault(replacingName2oldNodes.getOrDefault(name, null), null);
	}

	public Collection<TGGNode> getNewOriginalNodes() {
		return original2newNodes.values();
	}

	public Collection<TGGNode> getNewReplacingNodes() {
		return replacing2newNodes.values();
	}

	public TGGOverlap getOverlap() {
		return overlap;
	}

	public String getName() {
		return shortcutRule.getName();
	}

	@Override
	public String toString() {
		String name = "Shortcut-Rule - " + getName() + "\n";
		name += "Unbound Nodes: \n";
		for (TGGNode node : shortcutRule.getNodes()) {
			if(mergedNodes.contains(node))
				continue;
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		
		name += "Merged Nodes: \n";
		for (TGGNode node : mergedNodes) {
			name += "    " + node.getName() + " : " + node.getType().getName() + " - " + node.getBindingType().getName() + "\n";
		}
		
		name += "Edges: \n";
		for (TGGEdge edge : shortcutRule.getEdges()) {
			name += "    " + edge.getName() + " : " + edge.getType().getName() + " - " + edge.getBindingType().getName() + "\n";
		}
		return name;
	}

	public enum SCInputRule {
		ORIGINAL, REPLACING
	}
}
