package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.BlackPatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexBasePattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.RuntimePackage;

public class ConsistencyPattern extends IbexBasePattern {
	protected BlackPatternFactory factory;
	private TGGRuleNode protocolNode;
	
	public ConsistencyPattern(BlackPatternFactory factory) {
		this.factory = factory;
		initialise(factory.getFlattenedVersionOfRule());
		createPatternNetwork();
	}
	
	protected void initialise(TGGRule rule) {
		String name = getName(rule.getName());

		protocolNode = createProtocolNode(rule);

		Collection<TGGRuleNode> signatureNodes = new ArrayList<>(rule.getNodes());
		signatureNodes.add(protocolNode);
		
		Collection<TGGRuleEdge> localEdges = Collections.emptyList();
		Collection<TGGRuleNode> localNodes = Collections.emptyList();
		
		super.initialise(name, signatureNodes, localNodes, localEdges);
	}
	
	protected void createPatternNetwork() {
		createMarkedInvocations();
		addPositiveInvocation(factory.createBlackPattern(WholeRulePattern.class));
		
		if (BlackPatternFactory.strategy != FilterACStrategy.NONE) {
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
		}
	}
	
	public static TGGRuleNode createProtocolNode(TGGRule rule) {
		TGGRuleNode protocolNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		protocolNode.setName(getProtocolNodeName());
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		
		TGGInplaceAttributeExpression tae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tae.setAttribute(RuntimePackage.Literals.TGG_RULE_APPLICATION__NAME);
		tae.setOperator(TGGAttributeConstraintOperators.EQUAL);
		
		TGGLiteralExpression le = ExpressionsFactory.eINSTANCE.createTGGLiteralExpression();
		le.setValue("\"" + rule.getName() + "\"");
		
		tae.setValueExpr(le);
		protocolNode.getAttrExpr().add(tae);
		return protocolNode;
	}
	
	public void createMarkedInvocations() {
		TGGRuleNode ruleApplicationNode = getRuleApplicationNode(getSignatureNodes());
		
		signatureNodes
		.stream()
		.filter(e -> !e.equals(ruleApplicationNode))
		.forEach(el ->
		{
			TGGRuleNode node = (TGGRuleNode) el;
			if (nodeIsConnectedToRuleApplicationNode(node)) {
				IBlackPattern markedPattern = BlackPatternFactory.getMarkedPattern(node.getDomainType(), false, node.getBindingType().equals(BindingType.CONTEXT));
				TGGRuleNode invokedRuleApplicationNode = getRuleApplicationNode(markedPattern.getSignatureNodes());
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureNodes()
						.stream()
						.filter(e -> !e.equals(invokedRuleApplicationNode))
						.findFirst()
						.get();
				
				Map<TGGRuleNode, TGGRuleNode> mapping = new HashMap<>();
				mapping.put(ruleApplicationNode, invokedRuleApplicationNode);
				mapping.put(node, invokedObject);
				
				addPositiveInvocation(markedPattern, mapping);
			}
		});
	}

	private boolean nodeIsConnectedToRuleApplicationNode(TGGRuleNode node) {
		return !node.getDomainType().equals(DomainType.CORR);
	}

	private TGGRuleNode getRuleApplicationNode(Collection<TGGRuleNode> elements) {
		return elements.stream()
				.filter(this::isRuleApplicationNode)
				.findAny()
				.get();
	}

	private boolean isRuleApplicationNode(TGGRuleNode n) {
		return n.getType().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication());
	}
	
	public static String getProtocolNodeName() {
		return "eMoflon_ProtocolNode";
	}

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	@Override
	public BlackPatternFactory getPatternFactory() {
		return factory;
	}

	public static String getName(String ruleName) {
		return ruleName + PatternSuffixes.CONSISTENCY;
	}
}
