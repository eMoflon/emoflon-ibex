package org.emoflon.ibex.tgg.compiler.patterns.sync;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.IbexPattern;
import org.emoflon.ibex.tgg.compiler.patterns.filter_app_conds.FilterACStrategy;

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
		this(factory.getFlattenedVersionOfRule(), factory);
	}
	
	private ConsistencyPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		protocolNode = createProtocolNode(rule);
		this.getLocalNodes().add(protocolNode);
		
		createPatternNetwork();
	}
	
	protected void createPatternNetwork() {
		createMarkedInvocations();
		addTGGPositiveInvocation(factory.create(WholeRulePattern.class));
		
		if (PatternFactory.strategy != FilterACStrategy.NONE) {
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.SRC));
			addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.TRG));
			//addTGGPositiveInvocation(factory.createFilterACPatterns(DomainType.CORR));
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
		
		getSignatureNodes()
		.stream()
		.filter(e -> !e.equals(ruleApplicationNode))
		.forEach(el ->
		{
			TGGRuleNode node = (TGGRuleNode) el;
			if (nodeIsConnectedToRuleApplicationNode(node)) {
				IbexPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), false, node.getBindingType().equals(BindingType.CONTEXT));
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
		return (TGGRuleNode) elements.stream()
					   			     .filter(this::isRuleApplicationNode)
					   			     .findAny()
					   			     .get();
	}

	private boolean isRuleApplicationNode(TGGRuleElement e) {
		return ((TGGRuleNode) e).getType().equals(RuntimePackage.eINSTANCE.getTGGRuleApplication());
	}
	
	@Override
	public boolean isRelevantForSignature(TGGRuleNode e) {
		return true;
	}
	
	@Override
	public Collection<TGGRuleNode> getSignatureNodes() {
	 Collection<TGGRuleNode> signatureElements = super.getSignatureNodes();
	 signatureElements.add(protocolNode);
	 return signatureElements;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.CONSISTENCY;
	}

	public static String getProtocolNodeName() {
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

	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
}
