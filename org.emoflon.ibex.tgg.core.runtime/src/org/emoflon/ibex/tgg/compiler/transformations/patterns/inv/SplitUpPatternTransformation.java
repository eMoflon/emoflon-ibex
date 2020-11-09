package org.emoflon.ibex.tgg.compiler.transformations.patterns.inv;

import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getEdgesByDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperatorAndDomain;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByDomain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.ACStrategy;
import org.emoflon.ibex.tgg.compiler.patterns.PACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public abstract class SplitUpPatternTransformation extends OperationalPatternTransformation{
	
	protected ContextPatternTransformation parent;
	protected TGGRule rule;
	protected DomainType domain;
	protected ACAnalysis filterNACAnalysis;
	
	LinkedList<TGGRuleNode> patternNodes = new LinkedList<TGGRuleNode>();
	LinkedList<TGGRuleEdge> patternEdges = new LinkedList<TGGRuleEdge>();
	
	public SplitUpPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, ACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
		this.parent = parent;
		this.options = options;
		this.rule = rule;
		this.filterNACAnalysis = filterNACAnalysis;
	}
	
	public IBeXContextPattern createPatternByBindingAndDomain(BindingType binding, DomainType domain, String name) {
		this.domain = domain;
		LinkedList<TGGRuleNode> ruleNodesByOperatorAndDomain = new LinkedList<TGGRuleNode>(getNodesByOperatorAndDomain(rule, binding, domain));
		LinkedList<? extends TGGRuleEdge> ruleEdgesByOperatorAndDomain = new LinkedList<TGGRuleEdge>(getEdgesByOperatorAndDomain(rule, binding, domain));
		Optional<IBeXContextPattern> pattern = transform(ruleNodesByOperatorAndDomain, ruleEdgesByOperatorAndDomain, name);
		if(pattern.isPresent()) {
			// Transform NACs
//			transformNACs(pattern.get());
			parent.addContextPattern(pattern.get());
		}
		return pattern.get();
	}
	
	public IBeXContextPattern createPatternByDomain(DomainType domain, String name) {
		this.domain = domain;
		LinkedList<TGGRuleNode> ruleNodesByDomain = new LinkedList<TGGRuleNode>(getNodesByDomain(rule, domain));
		LinkedList<? extends TGGRuleEdge> ruleEdgesByDomain = new LinkedList<TGGRuleEdge>(getEdgesByDomain(rule, domain));
		Optional<IBeXContextPattern> pattern = transform(ruleNodesByDomain, ruleEdgesByDomain, name);
		if(pattern.isPresent()) {
			// Transform NACs
//			transformNACs(pattern.get());
			parent.addContextPattern(pattern.get());
		}
		return pattern.get();
	}
	
	public void createPatternByBinding(BindingType binding, String ruleName, String suffixe) {
		LinkedList<TGGRuleNode> ruleNodesByOperator = new LinkedList<TGGRuleNode>(getNodesByOperator(rule, binding));
		LinkedList<? extends TGGRuleEdge> ruleEdgesByOperator = new LinkedList<TGGRuleEdge>(getEdgesByOperator(rule, binding));
		buildPattern(ruleNodesByOperator, ruleEdgesByOperator, ruleName, suffixe, 0);
	}
	
	public Optional<IBeXContextPattern> transform(Collection<TGGRuleNode> nodesToTranslate, Collection<? extends TGGRuleEdge> edgesToTranslate, String nameOfPattern) {
		IBeXContextPattern pattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
		String patternName = nameOfPattern;
		//TODO not sure about the purpose
		// Skip if "greater" eOpposite exists
//		if (edgesToTranslate.stream().anyMatch(e -> isGreaterEOpposite(e, edge)))
//			return null;
		for(TGGRuleEdge ruleEdge : edgesToTranslate) {
			TGGRuleNode srcNode = ruleEdge.getSrcNode();
			TGGRuleNode trgNode = ruleEdge.getTrgNode();

			createIBeXEdge(ruleEdge, pattern);
			parent.transformInNodeAttributeConditions(pattern, srcNode);
			parent.transformInNodeAttributeConditions(pattern, trgNode);
			nodesToTranslate.remove(srcNode);
			nodesToTranslate.remove(trgNode);
		}
		for(TGGRuleNode ruleNode : nodesToTranslate) {
			createIBeXNode(ruleNode, pattern);
			parent.transformInNodeAttributeConditions(pattern, ruleNode);
		}
		if(pattern.getSignatureNodes().isEmpty())
			return Optional.empty();
		pattern.setName(patternName);
		return Optional.of(pattern);
	}
	
	public void createEdgeFromNodes(LinkedList<TGGRuleNode> nodes, IBeXContextPattern pattern) {
		if(nodes.size() < 1) 
			createIBeXNode(nodes.getFirst(), pattern);
		else 
			while(nodes.size() > 1) {
				TGGRuleNode firstNode = nodes.remove();
				TGGRuleNode secondNode = nodes.peekFirst();
				for(TGGRuleEdge firstNodeOut : firstNode.getOutgoingEdges()) {
					if(secondNode.getIncomingEdges().contains(firstNodeOut))
						createIBeXEdge(firstNodeOut, pattern);
				}
				for(TGGRuleEdge firstNodeIn : firstNode.getIncomingEdges()) {
					if(secondNode.getOutgoingEdges().contains(firstNodeIn))
						createIBeXEdge(firstNodeIn, pattern);
				}
			}
	}
	
	public IBeXNode createIBeXNode(String name, EClass type, IBeXContextPattern pattern) {
		IBeXNode ibexNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
		ibexNode.setName(name);
		ibexNode.setType(type);
		if(pattern != null)
			pattern.getSignatureNodes().add(ibexNode);
		return ibexNode;
	}
	
	public IBeXNode createIBeXNode(TGGRuleNode ruleNode, IBeXContextPattern pattern) {
		return createIBeXNode(ruleNode.getName(), ruleNode.getType(), pattern);
	}
	
	public IBeXEdge createIBeXEdge(TGGRuleEdge ruleEdge, IBeXContextPattern pattern) {
		TGGRuleNode ruleEdgeSrcNode = ruleEdge.getSrcNode();
		TGGRuleNode ruleEdgeTrgNode = ruleEdge.getTrgNode();
		Optional<IBeXNode> ibexSrcNode = getIBeXNodeByNameFromPattern(ruleEdgeSrcNode.getName(), pattern);
		Optional<IBeXNode> ibexTrgNode = getIBeXNodeByNameFromPattern(ruleEdgeTrgNode.getName(), pattern);
		if(!ibexSrcNode.isPresent())
			ibexSrcNode = Optional.of(createIBeXNode(ruleEdgeSrcNode, pattern));
		if(!ibexTrgNode.isPresent())
			ibexTrgNode = Optional.of(createIBeXNode(ruleEdgeTrgNode, pattern));
		IBeXEdge ibexEdge = IBeXPatternModelFactory.eINSTANCE.createIBeXEdge();
		ibexEdge.setType(ruleEdge.getType());
		connectNodeToEdge(ibexEdge, ibexSrcNode, ibexTrgNode);
		if(pattern != null)
			pattern.getLocalEdges().add(ibexEdge);
		return ibexEdge;
	}
	
	public Optional<IBeXNode> getIBeXNodeByNameFromPattern(String name, IBeXContextPattern pattern) {
		return  pattern.getSignatureNodes().stream().filter(x -> name.equals(x.getName())).findAny();
	}
	
	public IBeXEdge connectNodeToEdge(IBeXEdge edge, Optional<IBeXNode> srcNode, Optional<IBeXNode> trgNode) {
		if(srcNode.isPresent())
			edge.setSourceNode(srcNode.get());
		if(trgNode.isPresent())
			edge.setTargetNode(trgNode.get());
		return edge;
	}
	
	public void buildPattern(LinkedList<TGGRuleNode> nodes, LinkedList<? extends TGGRuleEdge> edges, String ruleName, String suffixe, int patternCounter) {
		//Recursive because checks for disjoint parts
		if(nodes.isEmpty() && edges.isEmpty())
			return;
		String patternName;
		if(patternCounter == 0)
			patternName = ruleName + suffixe;
		else patternName = ruleName + "_" + patternCounter + suffixe;
		graphIterator(nodes, edges);
		Optional<IBeXContextPattern> pattern = transform(patternNodes, patternEdges, patternName);
		pattern.ifPresent(parent::addContextPattern);
		if((nodes.size() == 1 && edges.isEmpty()) || (nodes.isEmpty() && edges.size() == 1))
			return;
		patternEdges.clear();
		patternNodes.clear();
		patternCounter++;
		buildPattern(nodes, edges, ruleName, suffixe, patternCounter);
	}
	
	/*
	 * BFA-shortest path, allEdges will not be empty if there are disjoint parts
	 */
	public void graphIterator(LinkedList<TGGRuleNode> allNodes, LinkedList<? extends TGGRuleEdge> allEdges) {
		LinkedList<TGGRuleNode> queue = new LinkedList<TGGRuleNode>();
		if(allNodes.isEmpty())
			queue.add(allEdges.getFirst().getSrcNode());
		else queue.add(allNodes.removeFirst());
		 
		while(!queue.isEmpty()) {
			
			TGGRuleNode queueNode = queue.remove();
			patternNodes.add(queueNode);
			
			for(TGGRuleEdge edge : queueNode.getOutgoingEdges()) {
				TGGRuleNode nextNode = edge.getTrgNode();
				if(allEdges.remove(edge)) {
					patternEdges.add(edge);
					if(allNodes.remove(nextNode)) {
						queue.add(nextNode);
					}
				}
			}
			for(TGGRuleEdge edge : queueNode.getIncomingEdges()) {
				TGGRuleNode nextNode = edge.getSrcNode();
				if(allEdges.remove(edge)) {
					patternEdges.add(edge);
					if(allNodes.remove(nextNode)) {
						queue.add(nextNode);
					}
				}
			}
		}
	}
}
