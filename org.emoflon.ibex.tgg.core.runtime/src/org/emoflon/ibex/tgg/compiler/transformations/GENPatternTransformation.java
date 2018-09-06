package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGENBlackPatternName;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.util.String2EPrimitive;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXRelation;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class GENPatternTransformation {
	ContextPatternTransformation parent;

	public GENPatternTransformation(ContextPatternTransformation parent) {
		this.parent = parent;
	}

	public void transform(TGGRule rule) {
		String patternName = getGENBlackPatternName(rule.getName());

		if (parent.isTransformed(patternName))
			return;

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes.
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		for (final TGGRuleNode node : contextNodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		List<TGGRuleEdge> edges = TGGModelUtils.getReferencesByOperator(rule, BindingType.CONTEXT);
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);

		// NACs
		for (NAC nac : rule.getNacs())
			parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);

		// Complement rule
		if (rule instanceof TGGComplementRule)
			handleComplementRuleForGEN((TGGComplementRule) rule, ibexPattern);
	}

	/**
	 * Complement rules require a positive invocation to the consistency pattern of
	 * their kernel rule.
	 * 
	 * @param rule
	 * @param ibexPattern
	 */
	private void handleComplementRuleForGEN(TGGComplementRule crule, IBeXContextPattern ibexPattern) {
		String nameOfKernelRule = crule.getKernel().getName();
		createConsistencyPattern(crule.getKernel());
		IBeXContextPattern consistencyPatternOfKernel = parent.getPattern(getConsistencyPatternName(nameOfKernelRule));

		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);

		// Creating mapping for invocation: missing signature nodes of the invoked
		// pattern are added as local nodes to the invoking pattern
		for (IBeXNode node : consistencyPatternOfKernel.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else {
				IBeXNode newLocalNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
				newLocalNode.setName(node.getName());
				newLocalNode.setType(node.getType());
				ibexPattern.getLocalNodes().add(newLocalNode);

				invocation.getMapping().put(newLocalNode, node);
			}
		}

		// Add additional attribute condition for "closing the kernel"
		Optional<IBeXNode> node = ibexPattern.getLocalNodes()//
				.stream()//
				.filter(n -> n.getName().equals(TGGPatternUtil.getProtocolNodeName(crule.getKernel().getName())))//
				.findAny();

		node.ifPresent(protocolNode -> {
			IBeXAttributeConstraint tae = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
			tae.setType(RuntimePackage.Literals.TGG_RULE_APPLICATION__AMALGAMATED);
			tae.setRelation(IBeXRelation.EQUAL);

			IBeXConstant le = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
			le.setValue(false);
			le.setStringValue("false");

			tae.setValue(le);
			tae.setNode(protocolNode);
			ibexPattern.getAttributeConstraint().add(tae);
		});

		invocation.setInvokedPattern(consistencyPatternOfKernel);
		ibexPattern.getInvocations().add(invocation);
	}

	private void createConsistencyPattern(TGGRule rule) {
		String patternName = getConsistencyPatternName(rule.getName());

		if (parent.isTransformed(patternName))
			return;

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes
		List<TGGRuleNode> contextNodes = rule.getNodes();
		for (final TGGRuleNode node : contextNodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform attributes
		for (final TGGRuleNode node : contextNodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}

		// Ensure that all nodes must be disjoint even if they have the same type
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges
		List<TGGRuleEdge> edges = rule.getEdges();
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		// Create protocol node and connections to nodes in pattern
		createAndConnectProtocolNode(rule, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);
	}

	private void createAndConnectProtocolNode(TGGRule rule, IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		protocolNode.setName(TGGPatternUtil.getProtocolNodeName(rule.getName()));
		protocolNode.setType(RuntimePackage.eINSTANCE.getTGGRuleApplication());
		ibexPattern.getSignatureNodes().add(protocolNode);

		IBeXAttributeConstraint ibexAttrConstraint = IBeXLanguageFactory.eINSTANCE.createIBeXAttributeConstraint();
		ibexAttrConstraint.setNode(protocolNode);
		ibexAttrConstraint.setType(RuntimePackage.Literals.TGG_RULE_APPLICATION__NAME);

		ibexAttrConstraint.setRelation(IBeXRelation.EQUAL);
		String s = "\"" + rule.getName() + "\"";
		Object object = String2EPrimitive.convertLiteral(s, EcorePackage.Literals.ESTRING);
		IBeXConstant ibexConstant = IBeXLanguageFactory.eINSTANCE.createIBeXConstant();
		ibexConstant.setValue(object);
		ibexConstant.setStringValue(object.toString());
		ibexAttrConstraint.setValue(ibexConstant);

		ibexPattern.getAttributeConstraint().add(ibexAttrConstraint);

		connectProtocolNode(ibexPattern, protocolNode, rule);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC,
				RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG,
				RuntimePackage.Literals.TGG_RULE_APPLICATION__CONTEXT_TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC,
				RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG,
				RuntimePackage.Literals.TGG_RULE_APPLICATION__CREATED_TRG);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, TGGRule rule, IBeXNode protocolNode,
			BindingType type, DomainType domain, EReference connectionType) {
		Collection<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperatorAndDomain(rule, type, domain);
		for (TGGRuleNode node : nodes)
			parent.transformEdge(connectionType, protocolNode, parent.transformNode(ibexPattern, node), ibexPattern);
	}
}
