package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.ShortcutRule.SCInputRule;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGOverlap;
import org.emoflon.ibex.tgg.operational.strategies.sync.SYNC;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

/**
 * This class implements a special type of operationalized shortcut rules.
 * It focusses on interface edges, i.e. edges between created and context nodes.
 * These edges get deleted when the context is deleted and often trigger an avalanche of invalidated rule applications
 * when a rule application is removed.
 * To circumvent this, InterfaceShortcutRules do not simply revoke a rule application with broken interface edges but try to 
 * restore consistency by fixing them. 
 * 
 * @author lfritsche
 *
 */
public class InterfaceShortcutRule extends OperationalShortcutRule {
	
	private SYNC strategy;
	
	public InterfaceShortcutRule(SYNC strategy, SyncDirection direction, ShortcutRule scRule) {
		super(direction, scRule.copy());
		this.strategy = strategy;

		operationalize();
		createConstraintChecks();
	}

	@Override
	protected void operationalize() {
		//TODO lfritsche : extend to correspondence interfaces
//		removeNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.CORR, BindingType.CONTEXT));
		switch(direction) {
		case FORWARD:
			createDECNacs(scRule.getTargetRule(), DomainType.SRC);

			transformEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
			transformInterfaceEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);
			
			transformNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
			
			removeEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE));
			removeNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.DELETE));
			
			addNACforCreatedInterface(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG));
			break;
		case BACKWARD:
			createDECNacs(scRule.getTargetRule(), DomainType.TRG);

			transformEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
			transformInterfaceEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE), BindingType.NEGATIVE);
			
			transformNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
			
			removeEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE));
			removeNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.DELETE));
			
			addNACforCreatedInterface(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC));
			break;
		default: throw new RuntimeException("Shortcut Rules can only be operationalized for FORWARD and BACKWARD operations");
		}
		createRuleApplicationNode(scRule.getTargetRule());
	}
	
	private void createRuleApplicationNode(TGGRule targetRule) {
		TGGRuleNode raNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		raNode.setName(getProtocolNodeName(scRule.getSourceRule().getName()));
		EClass type = (EClass) strategy.getOptions().getCorrMetamodel().getEClassifier(getMarkerTypeName(targetRule.getName()));
		raNode.setType(type);
		raNode.setBindingType(BindingType.CONTEXT);


		Function<TGGRuleNode, EReference>  createSrcRef = n -> getProtocolRef(raNode, BindingType.CREATE, DomainType.SRC, n);
		Function<TGGRuleNode, EReference>  createCorrRef = n -> getProtocolRef(raNode, BindingType.CREATE, DomainType.CORR, n);
		Function<TGGRuleNode, EReference>  createTrgRef = n -> getProtocolRef(raNode, BindingType.CREATE, DomainType.TRG, n);
		Function<TGGRuleNode, EReference>  contextSrcRef = n -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.SRC, n);
		Function<TGGRuleNode, EReference>  contextTrgRef = n -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.TRG, n);

		createRuleApplicationLink(createSrcRef, contextSrcRef, raNode, DomainType.SRC);
		createRuleApplicationLink(createCorrRef, null, raNode, DomainType.CORR);
		createRuleApplicationLink(createTrgRef, contextTrgRef, raNode, DomainType.TRG);
		
		scRule.getNodes().add(raNode);
	}
	
	private EReference getProtocolRef(TGGRuleNode protocolNode, BindingType bType, DomainType dType, TGGRuleNode node) {
		return (EReference) protocolNode.getType().getEStructuralFeature(getMarkerRefName(bType, dType, node.getName()));
	}
	
	private void createRuleApplicationLink(Function<TGGRuleNode, EReference> createdRef, Function<TGGRuleNode, EReference> contextRef, TGGRuleNode raNode, DomainType dType) {
		TGGOverlap overlap = scRule.getOverlap();
		Stream<TGGRuleNode> createdNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.creations), dType).stream();
		Stream<TGGRuleNode> deletedNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.deletions), dType).stream();
		Stream<TGGRuleNode> sourceRuleUnboundContextNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.unboundSrcContext), dType).stream().filter(n -> scRule.getSourceRule().getNodes().contains(n));
		Stream<TGGRuleNode> targetRuleUnboundContextNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.unboundTrgContext), dType).stream().filter(n -> scRule.getTargetRule().getNodes().contains(n));
		Stream<TGGRuleNode> createdMappingNodeKeys = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CREATE).stream();
		Stream<TGGRuleNode> contextMappingNodeKeys = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CONTEXT).stream();

		deletedNodes = deletedNodes.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.SOURCE)).filter(n -> scRule.getNodes().contains(n));
		createdNodes = createdNodes.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.TARGET)).filter(n -> scRule.getNodes().contains(n));
		sourceRuleUnboundContextNodes = sourceRuleUnboundContextNodes.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.SOURCE)).filter(n -> scRule.getNodes().contains(n));
		targetRuleUnboundContextNodes = targetRuleUnboundContextNodes.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.TARGET)).filter(n -> scRule.getNodes().contains(n));
		createdMappingNodeKeys = createdMappingNodeKeys.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.SOURCE)).filter(n -> scRule.getNodes().contains(n));
		contextMappingNodeKeys = contextMappingNodeKeys.map(n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.SOURCE)).filter(n -> scRule.getNodes().contains(n));

		if(createdRef != null) {
			createdNodes.forEach(n -> createRuleApplicationEdge(createdRef.apply(n), raNode, n, BindingType.CREATE));
			deletedNodes.forEach(n -> createRuleApplicationEdge(createdRef.apply(n), raNode, n, BindingType.DELETE));
			createdMappingNodeKeys.forEach(n -> createRuleApplicationEdge(createdRef.apply(n), raNode, n, BindingType.CONTEXT));
		}
		if(contextRef != null) {
			sourceRuleUnboundContextNodes.forEach(n -> createRuleApplicationEdge(contextRef.apply(n), raNode, n, BindingType.DELETE));
			targetRuleUnboundContextNodes.forEach(n -> createRuleApplicationEdge(contextRef.apply(n), raNode, n, BindingType.CREATE));
			contextMappingNodeKeys.forEach(n -> createRuleApplicationEdge(contextRef.apply(n), raNode, n, BindingType.CONTEXT));
		}
	}
	
	private void createRuleApplicationEdge(EReference ref, TGGRuleNode raNode, TGGRuleNode targetNode, BindingType bType) {
		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + targetNode.getName());
		edge.setType(ref);
		edge.setBindingType(bType);
		edge.setDomainType(targetNode.getDomainType());
		edge.setSrcNode(raNode);
		edge.setTrgNode(targetNode);
		
		scRule.getEdges().add(edge);
	}

	private void createDECNacs(TGGRule targetRule, DomainType domain) {
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(domain, targetRule, strategy.getOptions());

		Collection<FilterNACCandidate> decCandidates = filterNACAnalysis.computeFilterNACCandidates();
		for(FilterNACCandidate dec : decCandidates) {
			TGGRuleNode decNode = scRule.mapRuleNodeToSCRuleNode(dec.getNodeInRule(), SCInputRule.TARGET);
			TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			edge.setType(dec.getEdgeType());
			edge.setBindingType(BindingType.NEGATIVE);
			edge.setDomainType(decNode.getDomainType());
			
			TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
			node.setName("decNode");
			node.setDomainType(decNode.getDomainType());
			node.setType(dec.getOtherNodeType());
			node.setBindingType(BindingType.NEGATIVE);
			
			switch(dec.getEDirection()) {
			case INCOMING:
				edge.setSrcNode(node);
				edge.setTrgNode(decNode);
				break;
			case OUTGOING:
				edge.setSrcNode(decNode);
				edge.setTrgNode(node);
				break;
			}
			
			edge.setName(edge.getSrcNode().getName() + "__" + dec.getEdgeType().getName() + "__" + edge.getTrgNode().getName());
			
			scRule.getNodes().add(node);
			scRule.getEdges().add(edge);
		}
	}
	
	private void transformNodes(Collection<TGGRuleNode> filterNodes, BindingType target) {
		filterNodes.stream().forEach(n -> n.setBindingType(target));
	}

	private void transformEntryNodes(Collection<TGGRuleNode> filterNodes, BindingType target) {
		filterNodes.stream().filter(n -> scRule.getMergedNodes().contains(n)).forEach(n -> n.setBindingType(target));
	}
	
	private void transformEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		filteredEdges.forEach(e -> e.setBindingType(target));
	}
	
	private void transformInterfaceEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		markedElements.addAll(filteredEdges.stream()
				.filter(e -> scRule.getPreservedNodes().contains(e.getSrcNode()) ^ scRule.getPreservedNodes().contains(e.getTrgNode()))
				.collect(Collectors.toList()));
		
		for(TGGRuleEdge edge : filteredEdges) {
			if(!scRule.getPreservedNodes().contains(edge.getSrcNode()) && !scRule.getPreservedNodes().contains(edge.getTrgNode()))
				continue;
			
			if(edge.getSrcNode().getBindingType() != BindingType.CONTEXT || edge.getTrgNode().getBindingType() != BindingType.CONTEXT)
				continue;
			
			edge.setBindingType(target);
		}
	}

	// TODO lfritsche: delete -> nac?
	private void removeNodes(Collection<TGGRuleNode> filterNodes) {
		filterNodes.iterator().forEachRemaining(f -> {
			scRule.getEdges().removeAll(f.getIncomingEdges());
			scRule.getEdges().removeAll(f.getOutgoingEdges());
			f.getIncomingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			f.getOutgoingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			EcoreUtil.delete(f);
		});
		scRule.getNodes().removeAll(filterNodes);
	}
	
	private void removeEdges(Collection<TGGRuleEdge> filterEdges) {
		filterEdges.iterator().forEachRemaining(EcoreUtil::delete);
	}

	private void addNACforCreatedInterface(Collection<TGGRuleEdge> edges) {
		for(TGGRuleEdge edge : edges) {
			TGGRuleNode src = edge.getSrcNode();
			TGGRuleNode trg = edge.getTrgNode();
			
			if(scRule.getPreservedNodes().contains(src) ^ scRule.getPreservedNodes().contains(trg)) {
				if(edge.getBindingType() != BindingType.CREATE)
					continue;
				
				TGGRuleEdge nac = LanguageFactory.eINSTANCE.createTGGRuleEdge();
				nac.setDomainType(edge.getDomainType());
				nac.setBindingType(BindingType.NEGATIVE);
				nac.setType(edge.getType());
				nac.setSrcNode(edge.getSrcNode());
				nac.setTrgNode(edge.getTrgNode());
				nac.setName(edge.getSrcNode().getName() + "_" + edge.getType().getName() + "_" + edge.getTrgNode().getName());
				scRule.getEdges().add(nac);
			}
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Interface-" + scRule;
	}
	
}
