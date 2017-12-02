package org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class CheckTranslationStatePattern extends IbexPattern {
	
	private static final String PROTOCOL_NAME = "protocol";
	private static final String OBJECT_NAME = "checkedObject";
	
	private static final String CREATED_SRC_NAME = "createdSrc";
	private static final String CREATED_TRG_NAME = "createdTrg";
	
	private static final String CONTEXT_SRC_NAME = "contextSrc";
	private static final String CONTEXT_TRG_NAME = "contextTrg";
	
	private boolean localProtocol = false;
	private boolean marksContext = false; 
	
	private DomainType domain;
	
	public CheckTranslationStatePattern(DomainType domain, boolean localProtocol, boolean context) {
		super(createMarkedPatternRule(domain, localProtocol, context));
		this.localProtocol = localProtocol;
		this.marksContext = context;
		this.domain = domain;
		initialize();
	}
	
	public CheckTranslationStatePattern(CheckTranslationStatePattern localPattern, DomainType domain, boolean localProtocol) {
		super(createMarkedPatternRule(localPattern, domain));
		this.localProtocol = localProtocol;
		this.domain = domain;
		initialize();
	}
	
	private static TGGRule createMarkedPatternRule(CheckTranslationStatePattern localPattern, DomainType domain) {
		return localPattern.getRule();
	}

	private static TGGRule createMarkedPatternRule(DomainType domain, boolean localProtocol, boolean context) {
		LanguageFactory factory = LanguageFactory.eINSTANCE;
		TGGRule rule = factory.createTGGRule();
		
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
		
		rule.getNodes().add(protocol);
		rule.getNodes().add(checkedObject);
		rule.getEdges().add(edge);
		
		return rule;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return true;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return true;
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return localProtocol ? !(e.getName().equals(PROTOCOL_NAME)) : true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return (localProtocol ? PatternSuffixes.LOCAL_MARKED : "") + PatternSuffixes.SEP + domain.getName();
	}
	
	@Override
	public String getName() {
		return (marksContext ? "ContextMarkedPattern" : "MarkedPattern") + getPatternNameSuffix();
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
}
