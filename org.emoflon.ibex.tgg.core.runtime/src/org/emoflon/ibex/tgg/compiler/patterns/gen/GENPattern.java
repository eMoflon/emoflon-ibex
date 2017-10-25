package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;
import java.util.HashSet;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.BindingType;
import language.TGGComplementRule;
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

public class GENPattern extends RulePartPattern {
	
	protected PatternFactory factory;
	private Collection<TGGRuleElement> signatureElements = new HashSet<TGGRuleElement>();

	public GENPattern(PatternFactory factory) {
		this(factory.getFlattenedVersionOfRule(), factory); 
	}
	
	private GENPattern(TGGRule rule, PatternFactory factory) {
		super(rule);
		this.factory = factory;
		signatureElements = getSignatureElements(getRule());
		
		if (isComplementRule()) {
			signatureElements.add(createProtocolNode());
		}
		createPatternNetwork();
	}

	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(GENForRefinementInvocationsPattern.class));
		
		addTGGNegativeInvocations(factory.createPatternsForMultiplicityConstraints());
		addTGGNegativeInvocations(factory.createPatternsForContainmentReferenceConstraints());
		
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedSourceNACs());
		addTGGNegativeInvocations(factory.createPatternsForUserDefinedTargetNACs());
	}

	@Override
	public boolean isRelevantForSignature(TGGRuleElement e) {
		return e.getBindingType() == BindingType.CONTEXT;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleEdge e) {
		return false;
	}

	@Override
	protected boolean isRelevantForBody(TGGRuleNode n) {
		return false;
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.GEN;
	}

	@Override
	public Collection<TGGRuleElement> getSignatureElements() {
		return signatureElements;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		return true;
	}
	
	protected boolean isComplementRule() {
		return rule instanceof TGGComplementRule;
	}
	
	private TGGRuleNode createProtocolNode() {
		TGGRuleNode node = ConsistencyPattern.createProtocolNode(((TGGComplementRule) rule).getKernel());
		
		TGGInplaceAttributeExpression tae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tae.setAttribute(RuntimePackage.Literals.TGG_RULE_APPLICATION__AMALGAMATED);
		tae.setOperator(TGGAttributeConstraintOperators.EQUAL);
		
		TGGLiteralExpression le = ExpressionsFactory.eINSTANCE.createTGGLiteralExpression();
		le.setValue("false");
		
		tae.setValueExpr(le);
		node.getAttrExpr().add(tae);
		
		return node;
	}
	
}
