package org.emoflon.ibex.tgg.compiler.patterns;

import java.util.Collection;

import org.emoflon.ibex.tgg.compiler.patterns.common.IPattern;
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

	/* Creates a simple node with just the same name and type of kernelNode */
	private static TGGRuleNode createProxyNode(TGGRuleNode kernelNode) {
		TGGRuleNode node = LanguageFactory.eINSTANCE.createTGGRuleNode();
		node.setName(kernelNode.getName());
		node.setType(kernelNode.getType());
		return node;
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
}
