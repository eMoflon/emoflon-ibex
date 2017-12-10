package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class CheckTranslationStatePattern extends IbexBasePattern {
	private static final String PROTOCOL_NAME = "protocol";
	private static final String OBJECT_NAME = "checkedObject";
	
	private static final String CREATED_SRC_NAME = "createdSrc";
	private static final String CREATED_TRG_NAME = "createdTrg";
	
	private static final String CONTEXT_SRC_NAME = "contextSrc";
	private static final String CONTEXT_TRG_NAME = "contextTrg";
	
	private boolean localProtocol;
	private boolean marksContext; 
	private DomainType domain;
	
	public CheckTranslationStatePattern(DomainType domain, boolean localProtocol, boolean context) {
		this.localProtocol = localProtocol;
		this.marksContext = context;
		this.domain = domain;
		
		initialise(domain, localProtocol, context);
	}
	
	public CheckTranslationStatePattern(CheckTranslationStatePattern localPattern, DomainType domain, boolean localProtocol) {
		this.localProtocol = localProtocol;
		this.marksContext = false;
		this.domain = domain;
		
		initialise(localPattern);
	}
	
	private void initialise(CheckTranslationStatePattern localPattern) {
		Collection<TGGRuleNode> signatureNodes = localPattern.getSignatureNodes().stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = localPattern.getLocalEdges();
	
		Collection<TGGRuleNode> localNodes = localPattern.getSignatureNodes().stream()
				   .filter(node -> !isSignatureNode(node))
				   .collect(Collectors.toList());
	
		super.initialise(determineName(), signatureNodes, localNodes, localEdges);
	}

	private boolean isSignatureNode(TGGRuleNode node) {
		return localProtocol ? !(node.getName().equals(PROTOCOL_NAME)) : true;
	}
	
	private void initialise(DomainType domain, boolean localProtocol, boolean context) {
		LanguageFactory factory = LanguageFactory.eINSTANCE;
		
		TGGRuleNode protocol = factory.createTGGRuleNode();
		TGGRuleNode checkedObject = factory.createTGGRuleNode();
		
		protocol.setBindingType(BindingType.CONTEXT);
		checkedObject.setBindingType(BindingType.CONTEXT);
		
		protocol.setDomainType(domain);
		checkedObject.setDomainType(domain);
		
		protocol.setName(PROTOCOL_NAME);
		checkedObject.setName(OBJECT_NAME);
		
		protocol.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		checkedObject.setType(EcorePackage.Literals.EOBJECT);
		
		TGGRuleEdge edge = factory.createTGGRuleEdge();
		
		switch (domain) {
		case SRC:
			edge.setName(context? CONTEXT_SRC_NAME : CREATED_SRC_NAME);
			edge.setType(context? RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_SRC : RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_SRC);
			break;
			
		case TRG:
			edge.setName(context? CONTEXT_TRG_NAME : CREATED_TRG_NAME);
			edge.setType(context? RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_TRG : RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_TRG);
			break;
		default:
			throw new IllegalArgumentException("Domain can only be src or trg!");
		}
		
		edge.setDomainType(domain);
		edge.setBindingType(BindingType.CONTEXT);
		edge.setSrcNode(protocol);
		edge.setTrgNode(checkedObject);
		
		Collection<TGGRuleNode> signatureNodes = Arrays.asList(protocol, checkedObject).stream()
				   .filter(this::isSignatureNode)
				   .collect(Collectors.toList());

		Collection<TGGRuleNode> localNodes = Arrays.asList(protocol, checkedObject).stream()
				   .filter(node -> !isSignatureNode(node))
				   .collect(Collectors.toList());
	
		Collection<TGGRuleEdge> localEdges = Arrays.asList(edge);
	
		super.initialise(determineName(), signatureNodes, localNodes, localEdges);
	}

	public String determineName() {
		return (marksContext ? "ContextMarkedPattern" : "MarkedPattern") +
			   (localProtocol ? PatternSuffixes.LOCAL_MARKED : "") +
			   PatternSuffixes.SEP + domain.getName();
	}

	public DomainType getDomain() {
		return domain;
	}
	
	public boolean isLocal() {
		return localProtocol;
	}
	
	public boolean marksContext() {
		return marksContext;
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	@Override
	public PatternFactory getPatternFactory() {
		return null;
	}
}
