package org.emoflon.ibex.tgg.compiler.pattern.protocol;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.InvocationHelper;
import org.emoflon.ibex.tgg.compiler.pattern.PatternFactory;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;

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

	private TGGRuleNode protocolNode;
	
	public ConsistencyPattern(TGGRule rule, PatternFactory factory, List<MarkedPattern> markedPatterns) {
		super(rule);
		addProtocolNode();
		
		// Create pattern network
		createMarkedInvocations(markedPatterns);
		addTGGPositiveInvocation(factory.createWholeRulePattern());
	}
	
	public void addProtocolNode() {
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
	
	public void createMarkedInvocations(List<MarkedPattern> markedPatterns) {
		TGGRuleNode protocolNode = (TGGRuleNode) getSignatureElements().stream().filter(e -> ((TGGRuleNode) e).getType().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication())).findAny().get();
		
		for (TGGRuleElement el : getSignatureElements()) {
			TGGRuleNode node = (TGGRuleNode) el;
			if (node.getBindingType().equals(BindingType.CREATE) && !node.getDomainType().equals(DomainType.CORR)) {
				IbexPattern markedPattern = InvocationHelper.getMarkedPattern(markedPatterns, node.getDomainType(), false);
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
		return PatternSuffixes.PROTOCOL;
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
