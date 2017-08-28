package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.translation_app_conds.CheckTranslationStatePattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.RuntimePackage;

public class ConsistencyPattern extends IbexPattern {
	protected PatternFactory factory;
	private TGGRuleNode protocolNode;
	
	public ConsistencyPattern(PatternFactory factory) {
		this(factory.getRule(), factory);
	}
	
	public ConsistencyPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		addProtocolNode();
		
		createPatternNetwork();
	}
	
	private void createPatternNetwork() {
		createMarkedInvocations(PatternFactory.getMarkedPatterns());
		addTGGPositiveInvocation(factory.create(WholeRulePattern.class));
		addContextEdges();
	}
	
	private void addProtocolNode() {
		protocolNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		protocolNode.setName(getProtocolNodeName());
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		
		TGGInplaceAttributeExpression tae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tae.setAttribute(RuntimePackage.Literals.TGG_RULE_APPLICATION__NAME);
		tae.setOperator(TGGAttributeConstraintOperators.EQUAL);
		
		TGGLiteralExpression le = ExpressionsFactory.eINSTANCE.createTGGLiteralExpression();
		le.setValue("\"" + rule.getName() + "\"");
		
		tae.setValueExpr(le);
		protocolNode.getAttrExpr().add(tae);
		this.getBodyNodes().add(protocolNode);
	}
	
	private void addContextEdges() {
		// concat all elements of this pattern into one collection
		Collection<TGGRuleNode> ruleEls = getBodySrcTrgNodes();
		ruleEls.addAll(getSignatureElements().stream().filter(e -> e instanceof TGGRuleNode).map(e -> (TGGRuleNode) e).collect(Collectors.toList()));
		
		// for all elements that are not the protocol node
		for(TGGRuleNode el : ruleEls.stream().distinct().filter(e -> !e.equals(protocolNode)).collect(Collectors.toList())) {
			if(el.getDomainType() == DomainType.CORR || el.getBindingType() != BindingType.CONTEXT)
				continue;
			
			// create a contextSrc or contextTrg edge
			TGGRuleEdge contextEdge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
			contextEdge.setName(el.getDomainType() == DomainType.SRC ? "contextSrc" : "contextTrg");
			contextEdge.setType(el.getDomainType() == DomainType.SRC ? RuntimePackage.eINSTANCE.getTGGRuleApplication_ContextSrc() : RuntimePackage.eINSTANCE.getTGGRuleApplication_ContextTrg());
			contextEdge.setSrcNode(protocolNode);
			contextEdge.setTrgNode(el);
			this.getBodyEdges().add(contextEdge);
		}
	}
	
	protected void createMarkedInvocations(Collection<CheckTranslationStatePattern> markedPatterns) {
		TGGRuleNode protocolNode = (TGGRuleNode) getSignatureElements().stream().filter(e -> ((TGGRuleNode) e).getType().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication())).findAny().get();
		
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(BindingType.CREATE) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), false);
				TGGRuleNode invokedProtocol = (TGGRuleNode) markedPattern.getSignatureElements().stream().filter(e -> ((TGGRuleNode) e).getType().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication())).findAny().get();
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureElements().stream().filter(e -> !e.equals(invokedProtocol)).findFirst().get();
				
				Map<TGGRuleElement, TGGRuleElement> mapping = new HashMap<>();
				mapping.put(protocolNode, invokedProtocol);
				mapping.put(node, invokedObject);
				
				addCustomPositiveInvocation(markedPattern, mapping);
			}
		}
	}
	
	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return true;
	}
	
	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
	 Collection<TGGRuleElement> signatureElements = super.getSignatureElements();
	 signatureElements.add(protocolNode);
	 return signatureElements;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CONSISTENCY;
	}

	public String getProtocolNodeName() {
		return "eMoflon_ProtocolNode";
	}

	public String getRuleName() {
		return rule.getName();
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return n.getDomainType() != DomainType.CORR;
	}

	

}
