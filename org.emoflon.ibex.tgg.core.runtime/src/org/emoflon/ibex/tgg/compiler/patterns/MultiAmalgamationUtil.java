package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.emoflon.ibex.tgg.compiler.patterns.common.IPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.BindingType;
import language.DomainType;
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

public class MultiAmalgamationUtil {

	public static void embedKernelConsistencyPatternNodes(TGGComplementRule complementRule, IPattern pattern) {
		Collection<TGGRuleNode> kernelNodes = complementRule.getKernel().getNodes();
		pattern.getLocalNodes().add(createProtocolNodeForAmalgamation(complementRule));

		for (TGGRuleNode kernelNode : kernelNodes) {
			if (kernelNodeIsNotInComplement(kernelNode, pattern))
				pattern.getLocalNodes().add(createProxyNode(kernelNode));
		}
	}

	private static boolean kernelNodeIsNotInComplement(TGGRuleElement kernelNode, IPattern pattern) {
		return pattern.getSignatureNodes().stream().noneMatch(re -> re.getName().equals(kernelNode.getName()));
	}

	private static TGGRuleNode createProtocolNodeForAmalgamation(TGGComplementRule rule) {
		TGGRuleNode node = ConsistencyPattern.createProtocolNode(rule.getKernel());

		TGGInplaceAttributeExpression tae = InplaceAttributesFactory.eINSTANCE.createTGGInplaceAttributeExpression();
		tae.setAttribute(RuntimePackage.Literals.TGG_RULE_APPLICATION__AMALGAMATED);
		tae.setOperator(TGGAttributeConstraintOperators.EQUAL);

		TGGLiteralExpression le = ExpressionsFactory.eINSTANCE.createTGGLiteralExpression();
		le.setValue("false");

		tae.setValueExpr(le);
		node.getAttrExpr().add(tae);

		return node;
	}

	public static boolean isComplementRule(TGGRule rule) {
		return rule instanceof TGGComplementRule;
	}
	
	public static TGGRuleNode createProxyNode(TGGRuleNode node) {
		TGGRuleNode copiedNode = LanguageFactory.eINSTANCE.createTGGRuleNode();
		copiedNode.setName(node.getName());
		copiedNode.setType(node.getType());
		copiedNode.setBindingType(node.getBindingType());
		copiedNode.setDomainType(node.getDomainType());
		return copiedNode;
	}
	
	public static void addKernelOutputAndContextNodes(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes, DomainType domain) {
		Collection<TGGRuleNode> kernelNodes = rule.getKernel().getNodes();
		for (TGGRuleNode n : kernelNodes) {
			if(n.getDomainType() == domain || n.getBindingType() == BindingType.CONTEXT)
				signatureNodes.add(createProxyNode(n));
		}
	}
	
	public static void addComplementOutputAndContextNodes(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes, DomainType domain) {
		for (TGGRuleNode n : rule.getNodes()) {
			if(nodeIsNotInKernel(rule, n) && (n.getDomainType() == DomainType.SRC || n.getBindingType() == BindingType.CONTEXT))
				signatureNodes.add(createProxyNode(n));
		}
	}
	
	public static boolean nodeIsNotInKernel(TGGComplementRule rule, TGGRuleElement node) {
		return rule.getKernel().getNodes().stream().noneMatch(re -> re.getName().equals(node.getName()));
	}
	
	public static void createMarkedInvocations(DomainType domain, TGGComplementRule rule, IPattern pattern) {
		for (TGGRuleElement e : pattern.getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) e;
			if (nodeIsNotInKernel(rule, node) && node.getDomainType().equals(domain)) {
				IPattern markedPattern = PatternFactory.getMarkedPattern(node.getDomainType(), true, false);
				TGGRuleNode invokedObject = (TGGRuleNode) markedPattern.getSignatureNodes().stream().findAny().get();

				Map<TGGRuleNode, TGGRuleNode> mapping = new HashMap<>();
				mapping.put(node, invokedObject);

				if (node.getBindingType() == BindingType.CREATE)
					pattern.addNegativeInvocation(markedPattern, mapping);
				
				else if (node.getBindingType() == BindingType.CONTEXT) {
					pattern.addPositiveInvocation(markedPattern, mapping);
				}
					
			}
		}
	}
}
