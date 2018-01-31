package org.emoflon.ibex.tgg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.common.AbstractCorrPattern;
import org.emoflon.ibex.tgg.compiler.patterns.common.IBlackPattern;
import org.emoflon.ibex.tgg.compiler.patterns.sync.ConsistencyPattern;

import language.BindingType;
import language.DomainType;
import language.LanguageFactory;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;
import language.basic.expressions.ExpressionsFactory;
import language.basic.expressions.TGGAttributeExpression;
import language.basic.expressions.TGGEnumExpression;
import language.basic.expressions.TGGLiteralExpression;
import language.basic.expressions.TGGParamValue;
import language.csp.TGGAttributeConstraint;
import language.csp.TGGAttributeVariable;
import language.inplaceAttributes.InplaceAttributesFactory;
import language.inplaceAttributes.TGGAttributeConstraintOperators;
import language.inplaceAttributes.TGGInplaceAttributeExpression;
import runtime.RuntimePackage;

public class MAUtil {
	public static final String FUSED = "FFF";

	public static void embedKernelRuleAppAndConsistencyPatternNodes(TGGComplementRule complementRule, IBlackPattern pattern) {
		pattern.getLocalNodes().add(createProtocolNodeForAmalgamation(complementRule));
		embedKernelConsistencyPatternNodes(complementRule, pattern);
	}
	
	public static void embedKernelConsistencyPatternNodes(TGGComplementRule complementRule, IBlackPattern pattern) {
		Collection<TGGRuleNode> kernelNodes = complementRule.getKernel().getNodes();

		for (TGGRuleNode kernelNode : kernelNodes) {
			if (kernelNodeIsNotInComplement(kernelNode, pattern))
				pattern.getLocalNodes().add(createProxyNode(kernelNode));
		}
	}

	private static boolean kernelNodeIsNotInComplement(TGGRuleElement kernelNode, IBlackPattern pattern) {
		return pattern.getSignatureNodes().stream().noneMatch(re -> re.getName().equals(kernelNode.getName()));
	}

	public static TGGRuleNode createProtocolNodeForAmalgamation(TGGComplementRule rule) {
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
	
	public static void addKernelGivenDomainAndContextNodes(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes, DomainType domain) {
		Collection<TGGRuleNode> kernelNodes = rule.getKernel().getNodes();
		for (TGGRuleNode n : kernelNodes) {
			if(n.getDomainType() == domain || n.getBindingType() == BindingType.CONTEXT)
				signatureNodes.add(createProxyNode(n));
		}
	}
	
	public static Collection<TGGRuleEdge> getComplementGivenDomainAndContextEdgesNotInKernel(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes, DomainType domain) {
		return rule.getEdges().stream()
				.filter(e -> e.getDomainType() == domain)
				.filter(e -> e.getBindingType() == BindingType.CONTEXT)
				.filter(e -> !rule.getKernel().getEdges().contains(e))
				.map(e -> createProxyEdge(signatureNodes, e))
				.collect(Collectors.toList());
	}

	private static TGGRuleEdge createProxyEdge(Collection<TGGRuleNode> signatureNodes, TGGRuleEdge e) {
		TGGRuleEdge edge = LanguageFactory.eINSTANCE.createTGGRuleEdge();
		edge.setName(e.getName());
		edge.setType(e.getType());
		edge.setBindingType(e.getBindingType());
		edge.setDomainType(e.getDomainType());
		edge.setSrcNode(signatureNodes.stream()
				.filter(n -> n.getName().equals(e.getSrcNode().getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Unable to find " + e.getSrcNode().getName() + " in signature nodes!")));
		edge.setTrgNode(signatureNodes.stream()
				.filter(n -> n.getName().equals(e.getTrgNode().getName()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Unable to find " + e.getTrgNode().getName() + " in signature nodes!")));
		return edge;
	}
	
	public static Collection<TGGRuleEdge> getComplementCorrContextEdgesNotInKernel(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes){
		ArrayList<TGGRuleEdge> edges = new ArrayList<>();
		
		rule.getNodes().stream()
				.filter(TGGRuleCorr.class::isInstance)
				.filter(c -> c.getBindingType() == BindingType.CONTEXT)
				.filter(c -> nodeIsNotInKernel(rule, c))
				.map(TGGRuleCorr.class::cast)
				.forEach(c -> AbstractCorrPattern.extractSourceAndTargetEdges(c, edges));

		return edges.stream()
				.map(e -> createProxyEdge(signatureNodes, e))
				.collect(Collectors.toList());
	}
	
	public static void addComplementGivenDomainAndContextNodes(TGGComplementRule rule, Collection<TGGRuleNode> signatureNodes, DomainType domain) {
		for (TGGRuleNode n : rule.getNodes()) {
			if(nodeIsNotInKernel(rule, n) && (n.getDomainType() == domain || n.getBindingType() == BindingType.CONTEXT))
				signatureNodes.add(createProxyNode(n));
		}
	}
	
	public static boolean nodeIsNotInKernel(TGGComplementRule rule, TGGRuleElement node) {
		return rule.getKernel().getNodes().stream().noneMatch(re -> re.getName().equals(node.getName()));
	}
	
	public static void createMarkedInvocations(DomainType domain, TGGComplementRule rule, IBlackPattern pattern) {
		for (TGGRuleElement e : pattern.getSignatureNodes()) {
			TGGRuleNode node = (TGGRuleNode) e;
			if (nodeIsNotInKernel(rule, node) && node.getDomainType().equals(domain)) {
				IBlackPattern markedPattern = pattern.getPatternFactory().getMarkedPattern(node.getDomainType(), true, false);
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
	
	public static String setFusedName(String complementName, String kernelName) {
		return complementName + FUSED + kernelName;
	}
	
	public static String getKernelName(String fusedName) {
		String name = PatternSuffixes.removeSuffix(fusedName);
		return name.split(FUSED)[1];
	}
	
	public static String getComplementName(String fusedName) {
		String name = PatternSuffixes.removeSuffix(fusedName);
		return name.split(FUSED)[0];
	}
	
	public static boolean isFusedPatternMatch(String patternName) {
		return patternName.contains(FUSED);
	}
	
	public static void replaceWith(TGGAttributeConstraint constraint, TGGParamValue toBeReplaced, TGGParamValue useToReplace) {
		assert(constraint.getParameters().contains(toBeReplaced));
		constraint.getParameters().replaceAll(p -> p.equals(toBeReplaced)? useToReplace : p);
	}

	public static boolean equal(TGGParamValue p1, TGGParamValue p2) {
		if(!p1.eClass().getName().equals(p2.eClass().getName()))
			return false;
		
		// Enums
		if(p1 instanceof TGGEnumExpression) {
			return ((TGGEnumExpression) p1).getEenum().equals(((TGGEnumExpression) p2).getEenum()) &&
					((TGGEnumExpression) p1).getLiteral().equals(((TGGEnumExpression) p2).getLiteral());
		}
		
		// Literals
		if(p1 instanceof TGGLiteralExpression) {
			return ((TGGLiteralExpression) p1).getValue().equals(((TGGLiteralExpression) p2).getValue());
		}
		
		// Locals
		if(p1 instanceof TGGAttributeVariable) {
			return ((TGGAttributeVariable) p1).getName().equals(((TGGAttributeVariable) p2).getName());
		}
		
		// Attribute expressions
		if(p1 instanceof TGGAttributeExpression) {
			return attributeAccess(p1).equals(attributeAccess(p2));
		}
		
		return false;
	}

	public static String attributeAccess(TGGParamValue p) {
		assert(p instanceof TGGAttributeExpression);
		TGGAttributeExpression exp = (TGGAttributeExpression) p;
		return exp.getObjectVar().getName() + "." + exp.getAttribute().getName();
	}
}
