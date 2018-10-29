package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerTypeName;

import java.util.Collection;
import java.util.function.BiFunction;
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
		createRuleApplicationNode();
	}
	
	private void createRuleApplicationNode() {
		TGGRuleNode oldRaNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		oldRaNode.setName(getProtocolNodeName(scRule.getSourceRule().getName()));
		EClass oldRaType = (EClass) strategy.getOptions().getCorrMetamodel().getEClassifier(getMarkerTypeName(this.scRule.getSourceRule().getName()));
		oldRaNode.setType(oldRaType);
		oldRaNode.setBindingType(BindingType.DELETE);

		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  createSrcRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.SRC, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  createCorrRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.CORR, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  createTrgRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CREATE, DomainType.TRG, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  contextSrcRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.SRC, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  contextCorrRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.CORR, n);
		BiFunction<TGGRuleNode, TGGRuleNode, EReference>  contextTrgRef = (raNode, n) -> getProtocolRef(raNode, BindingType.CONTEXT, DomainType.TRG, n);

		createRuleApplicationLink(createSrcRef, contextSrcRef, oldRaNode, DomainType.SRC);
		createRuleApplicationLink(createCorrRef, contextCorrRef, oldRaNode, DomainType.CORR);
		createRuleApplicationLink(createTrgRef, contextTrgRef, oldRaNode, DomainType.TRG);
		
		scRule.getNodes().add(oldRaNode);
	}
	
	private EReference getProtocolRef(TGGRuleNode protocolNode, BindingType bType, DomainType dType, TGGRuleNode node) {
		return (EReference) protocolNode.getType().getEStructuralFeature(getMarkerRefName(bType, dType, node.getName()));
	}
	
	private void createRuleApplicationLink(BiFunction<TGGRuleNode, TGGRuleNode, EReference> createdRef, BiFunction<TGGRuleNode, TGGRuleNode, EReference> contextRef, TGGRuleNode oldRaNode, DomainType dType) {
		TGGOverlap overlap = scRule.getOverlap();
		Stream<TGGRuleNode> deletedNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.deletions), dType).stream();
		Stream<TGGRuleNode> sourceRuleUnboundContextNodes = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.unboundSrcContext), dType).stream().filter(n -> scRule.getSourceRule().getNodes().contains(n));
		Stream<TGGRuleNode> sourceRuleCreatedMappingNodeKeys = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CREATE).stream();
		Stream<TGGRuleNode> sourceRuleContextMappingNodeKeys = TGGCollectionUtil.filterNodes(TGGCollectionUtil.filterNodes(overlap.mappings.keySet()), dType, BindingType.CONTEXT).stream();

		Function<TGGRuleNode, TGGRuleNode> srcToSCNode = n -> scRule.mapRuleNodeToSCRuleNode(n, SCInputRule.SOURCE);
		
		if(createdRef != null) {
			deletedNodes.forEach(n -> createRuleApplicationEdge(createdRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
			sourceRuleCreatedMappingNodeKeys.forEach(n -> createRuleApplicationEdge(createdRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
		}
		if(contextRef != null) {
			sourceRuleUnboundContextNodes.forEach(n -> createRuleApplicationEdge(contextRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
			sourceRuleContextMappingNodeKeys.forEach(n -> createRuleApplicationEdge(contextRef.apply(oldRaNode, n), oldRaNode, n, BindingType.DELETE, srcToSCNode.apply(n)));
		}
	}
	
	private void createRuleApplicationEdge(EReference ref, TGGRuleNode raNode, TGGRuleNode node, BindingType bType, TGGRuleNode scNode) {
		if(!scRule.getNodes().contains(scNode))
			return;
		
		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setName(raNode.getName() + "__" + ref.getName() + "__" + scNode.getName());
		edge.setType(ref);
		edge.setBindingType(bType);
		edge.setDomainType(node.getDomainType());
		edge.setSrcNode(raNode);
		edge.setTrgNode(scNode);
		
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
