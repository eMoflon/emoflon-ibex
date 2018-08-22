package org.emoflon.ibex.tgg.operational.repair.strategies.shortcut;

import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.DECCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACHelper;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.ForbidAllFilterACsPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;
import org.emoflon.ibex.tgg.operational.repair.strategies.shortcut.util.SyncDirection;
import org.emoflon.ibex.tgg.operational.repair.strategies.util.TGGCollectionUtil;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class InterfaceShortcutRule extends OperationalShortcutRule {
	
	public InterfaceShortcutRule(SyncDirection direction, ShortcutRule scRule) {
		super(direction, scRule.copy());

		createConstraintChecks();
		operationalize();
	}

	@Override
	protected void operationalize() {
		//TODO lfritsche : extend to correspondence interfaces
		switch(direction) {
		case FORWARD:
			transformEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
			transformNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
			
			removeEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE));
			removeNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.DELETE));
			
			addNACforCreate(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG));
			createDECNacs(scRule.getSourceRule(), DomainType.SRC);
			break;
		case BACKWARD:
			transformEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
			transformNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.CREATE), BindingType.CONTEXT);
			
			removeEdges(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.TRG, BindingType.DELETE));
			removeNodes(TGGCollectionUtil.filterNodes(scRule.getNodes(), DomainType.TRG, BindingType.DELETE));
			
			addNACforCreate(TGGCollectionUtil.filterEdges(scRule.getEdges(), DomainType.SRC));
			createDECNacs(scRule.getSourceRule(), DomainType.TRG);
			break;
		default: throw new RuntimeException("Shortcut Rules can only be operationalized for FORWARD and BACKWARD operations");
		}
		createRuleApplicationNode();
	}
	
	private void createRuleApplicationNode() {
		TGGRuleNode raNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		raNode.setName(ConsistencyPattern.getProtocolNodeName(scRule.getSourceRule().getName()));
		raNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		raNode.setBindingType(BindingType.CONTEXT);
		
		Collection<TGGRuleNode> greenSrcNodes = TGGCollectionUtil.filterNodes(scRule.getSourceRule().getNodes(), DomainType.SRC, BindingType.CREATE);
		Collection<TGGRuleNode> greenTrgNodes = TGGCollectionUtil.filterNodes(scRule.getSourceRule().getNodes(), DomainType.TRG, BindingType.CREATE);
		Collection<TGGRuleNode> greenCorrNodes = TGGCollectionUtil.filterNodes(scRule.getSourceRule().getNodes(), DomainType.CORR, BindingType.CREATE);
		
		Collection<TGGRuleNode> blackSrcNodes = TGGCollectionUtil.filterNodes(scRule.getSourceRule().getNodes(), DomainType.SRC, BindingType.CONTEXT);
		Collection<TGGRuleNode> blackTrgNodes = TGGCollectionUtil.filterNodes(scRule.getSourceRule().getNodes(), DomainType.TRG, BindingType.CONTEXT);

		greenSrcNodes.forEach(n -> createRuleApplicationEdge(RuntimePackage.eINSTANCE.getTGGRuleApplication_CreatedSrc(), raNode, n));
		greenTrgNodes.forEach(n -> createRuleApplicationEdge(RuntimePackage.eINSTANCE.getTGGRuleApplication_CreatedTrg(), raNode, n));
		greenCorrNodes.forEach(n -> createRuleApplicationEdge(RuntimePackage.eINSTANCE.getTGGRuleApplication_CreatedCorr(), raNode, n));	
		
		blackSrcNodes.forEach(n -> createRuleApplicationEdge(RuntimePackage.eINSTANCE.getTGGRuleApplication_ContextSrc(), raNode, n));
		blackTrgNodes.forEach(n -> createRuleApplicationEdge(RuntimePackage.eINSTANCE.getTGGRuleApplication_ContextTrg(), raNode, n));
		
		scRule.getNodes().add(raNode);
	}
	
	private void createRuleApplicationEdge(EReference ref, TGGRuleNode raNode, TGGRuleNode targetNode) {
		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setType(ref);
		edge.setBindingType(targetNode.getBindingType());
		edge.setSrcNode(raNode);
		edge.setTrgNode(targetNode);
		
		scRule.getEdges().add(edge);
	}

	private void createDECNacs(TGGRule targetRule, DomainType domain) {
		Collection<DECCandidate> decCandidates = new ForbidAllFilterACsPattern(domain, null).getDECCandidates(targetRule, domain);
		for(DECCandidate dec : decCandidates) {
			TGGRuleNode decNode = scRule.mapRuleNodeToSCRuleNode(dec.node);
			TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			edge.setType(dec.edgeType);
			edge.setBindingType(BindingType.NEGATIVE);
			edge.setDomainType(decNode.getDomainType());
			
			TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
			node.setName("decNode");
			node.setDomainType(decNode.getDomainType());
			node.setType(FilterACHelper.getOppositeType(dec.edgeType, dec.direction));
			node.setBindingType(BindingType.NEGATIVE);
			
			switch(dec.direction) {
			case INCOMING:
				edge.setSrcNode(node);
				edge.setTrgNode(decNode);
				break;
			case OUTGOING:
				edge.setSrcNode(decNode);
				edge.setTrgNode(node);
				break;
			}
			
			scRule.getNodes().add(node);
			scRule.getEdges().add(edge);
		}
	}

	private void transformNodes(Collection<TGGRuleNode> filterNodes, BindingType target) {
		filterNodes.stream().forEach(n -> n.setBindingType(target));
	}
	
	private void transformEdges(Collection<TGGRuleEdge> filteredEdges, BindingType target) {
		markedElements.addAll(filteredEdges.stream()
				.filter(e -> scRule.getEntryNodes().contains(e.getSrcNode()) ^ scRule.getEntryNodes().contains(e.getTrgNode()))
				.collect(Collectors.toList()));
		
		filteredEdges.stream().forEach(n -> n.setBindingType(target));
	}

	// TODO lfritsche: delete -> nac?
	private void removeNodes(Collection<TGGRuleNode> filterNodes) {
		filterNodes.iterator().forEachRemaining(f -> {
			f.getIncomingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			f.getOutgoingEdges().stream().forEach(e -> EcoreUtil.delete(e));
			EcoreUtil.delete(f);
		});
	}
	
	private void removeEdges(Collection<TGGRuleEdge> filterEdges) {
		filterEdges.iterator().forEachRemaining(EcoreUtil::delete);
	}

	private void addNACforCreate(Collection<TGGRuleEdge> edges) {
		for(TGGRuleEdge edge : edges) {
			TGGRuleNode src = edge.getSrcNode();
			TGGRuleNode trg = edge.getTrgNode();
			
			if(scRule.getEntryNodes().contains(src) ^ scRule.getEntryNodes().contains(trg)) {
				edge.setBindingType(BindingType.CREATE);
				
				TGGRuleEdge nac = LanguageFactory.eINSTANCE.createTGGRuleEdge();
				nac.setDomainType(edge.getDomainType());
				nac.setBindingType(BindingType.NEGATIVE);
				nac.setType(edge.getType());
				nac.setSrcNode(edge.getSrcNode());
				nac.setTrgNode(edge.getTrgNode());
				nac.setName(edge.getName());
				scRule.getEdges().add(nac);
			}
		}
	}
	
}
