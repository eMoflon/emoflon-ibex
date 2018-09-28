package org.emoflon.ibex.tgg.compiler.transformations.patterns.gen;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getGENBlackPatternName;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXAttributeConstraint;
import IBeXLanguage.IBeXConstant;
import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXRelation;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;
import runtime.RuntimePackage;

public class GENPatternTransformation extends OperationalPatternTransformation {

	public GENPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getGENBlackPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
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
		parent.createConsistencyPattern(crule.getKernel());
		IBeXContextPattern consistencyPatternOfKernel = parent
				.getPattern(TGGPatternUtil.getConsistencyPatternName(crule.getKernel().getName()));

		createInvocation(ibexPattern, consistencyPatternOfKernel, true);

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
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> contextNodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		for (final TGGRuleNode node : contextNodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
	}
}
