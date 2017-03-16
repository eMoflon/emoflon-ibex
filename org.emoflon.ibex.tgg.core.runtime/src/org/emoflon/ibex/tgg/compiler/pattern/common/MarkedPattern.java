package org.emoflon.ibex.tgg.compiler.pattern.common;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.operational.util.IbexMatch;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class MarkedPattern extends IbexPattern {
	
	public static final String PROTOCOL_NAME = "protocol";
	public static final String OBJECT_NAME = "checkedObject";
	
	public static final String CREATED_SRC_NAME = "createdSrc";
	public static final String CREATED_TRG_NAME = "createdTrg";
	
	private boolean localProtocol = false;
	
	private DomainType domain;
	
	public MarkedPattern(DomainType domain, boolean localProtocol) {
		super(createMarkedPatternRule(domain, localProtocol));
		this.localProtocol = localProtocol;
		this.domain = domain;
		initialize();
	}
	
	public MarkedPattern(MarkedPattern localPattern, DomainType domain, boolean localProtocol) {
		super(createMarkedPatternRule(localPattern, domain));
		this.localProtocol = localProtocol;
		this.domain = domain;
		initialize();
	}
	
	private static TGGRule createMarkedPatternRule(MarkedPattern localPattern, DomainType domain) {
		return localPattern.getRule();
	}

	private static TGGRule createMarkedPatternRule(DomainType domain, boolean localProtocol) {
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
		
		TGGRuleEdge createdEdge = factory.createTGGRuleEdge();
		createdEdge.setName(domain == DomainType.SRC ? CREATED_SRC_NAME : CREATED_TRG_NAME);
		createdEdge.setType(domain == DomainType.SRC ? RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_SRC : RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_TRG);
		createdEdge.setDomainType(domain);
		createdEdge.setBindingType(BindingType.CONTEXT);
		createdEdge.setSrcNode(protocol);
		createdEdge.setTrgNode(checkedObject);
		
		rule.getNodes().add(protocol);
		rule.getNodes().add(checkedObject);
		rule.getEdges().add(createdEdge);
		
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
	protected boolean isRelevantForSignature(TGGRuleElement e) {
		return localProtocol ? !(e.getName().equals(PROTOCOL_NAME)) : true;
	}

	@Override
	protected String getPatternNameSuffix() {
		return (localProtocol ? "_LocalProtocol" : "") + "_" + domain.getName();
	}
	
	@Override
	public String getName() {
		return "MarkedSubPattern" + getPatternNameSuffix();
	}

	public DomainType getDomain() {
		return domain;
	}
	
	public boolean isLocal() {
		return localProtocol;
	}
}
