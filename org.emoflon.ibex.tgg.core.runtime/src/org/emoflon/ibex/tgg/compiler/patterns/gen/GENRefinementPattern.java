package org.emoflon.ibex.tgg.compiler.patterns.gen;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.PatternFactory;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.LanguageFactory;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.TGGLiteralExpression;
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.RuntimePackage;

public class GENRefinementPattern extends GENPattern {

	public GENRefinementPattern(PatternFactory factory) {
		super(factory);		
	}
	
	@Override
	protected void createPatternNetwork() {
		addTGGPositiveInvocation(factory.create(SrcContextPattern.class));
		addTGGPositiveInvocation(factory.create(CorrContextPattern.class));
		addTGGPositiveInvocation(factory.create(TrgContextPattern.class));
		
		if (isComplementRule()) 
			addTGGPositiveInvocation(factory.getFactory(((TGGComplementRule) rule).getKernel()).create(ConsistencyPattern.class));
		
		for (TGGRule superRule : factory.getRule().getRefines())
			addTGGPositiveInvocation(factory.getFactory(superRule).create(GENRefinementPattern.class));
	
	}

	@Override
	protected String getPatternNameSuffix() {
		return PatternSuffixes.GEN_REFINEMENT_INVOCATIONS;
	}
	
	@Override
	protected boolean injectivityIsAlreadyChecked(TGGRuleNode node1, TGGRuleNode node2) {
		if (!rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)) {
			// if both nodes are from super-rule, then super-rule takes care of injectivity
			return true;
		} else if (rule.getNodes().contains(node1) && !rule.getNodes().contains(node2)
				|| rule.getNodes().contains(node2) && !rule.getNodes().contains(node1)) {
			// if one node is from super-rule while the other is not, injectivity has probably (depending on invocations) not been checked yet
			return false;
		} else if (node1.getDomainType() != node2.getDomainType()) {
			// if nodes are from different domains, injectivity cannot have been checked yet
			return false;
		} else {
			// if both are from this rule and from the same domain, they have been checked in context-patterns
			return true;
		}
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		
		if (isComplementRule()) {
			embedKernelConsistencyPatternNodes();
		}
	}

	private void embedKernelConsistencyPatternNodes() {
		Collection<TGGRuleNode> kernelNodes = ((TGGComplementRule) rule).getKernel().getNodes();
			
		this.getBodyNodes().add(createProtocolNode());
		
		for (TGGRuleElement kernelNode : kernelNodes) {
			if(kernelNodeIsNotInComplement(kernelNode) && kernelNode instanceof TGGRuleNode)
				this.getBodyNodes().add(createProxyNode((TGGRuleNode) kernelNode));
			}
		}
	
	private boolean kernelNodeIsNotInComplement(TGGRuleElement kernelNode) {
		return getSignatureNodes(rule).stream().noneMatch(re -> re.getName().equals(kernelNode.getName()));
	}

	/* Creates a simple node with just the same name and type of kernelNode */
	private TGGRuleNode createProxyNode(TGGRuleNode kernelNode) {
		TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
		node.setName(kernelNode.getName());
		node.setType(kernelNode.getType());

		return node;
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
