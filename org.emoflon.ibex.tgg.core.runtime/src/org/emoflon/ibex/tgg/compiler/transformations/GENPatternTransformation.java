package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGENBlackPatternName;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import IBeXLanguage.IBeXRelation;
import language.BindingType;
import language.NAC;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class GENPatternTransformation {
	private ContextPatternTransformation parent;
	private IbexOptions options;

	public GENPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		this.parent = parent;
		this.options = options;
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
		IBeXContextPattern consistencyPatternOfKernel = new ConsistencyPatternTransformation(parent, options).createConsistencyPattern(crule.getKernel());

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
}
