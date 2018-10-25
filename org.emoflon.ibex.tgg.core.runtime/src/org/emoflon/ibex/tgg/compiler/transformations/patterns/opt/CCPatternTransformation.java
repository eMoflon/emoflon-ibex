package org.emoflon.ibex.tgg.compiler.transformations.patterns.opt;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getCCBlackPatternName;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import language.BindingType;
import language.DomainType;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class CCPatternTransformation extends OperationalPatternTransformation {

	public CCPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	@Override
	protected String getPatternName() {
		return getCCBlackPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
		if (rule instanceof TGGComplementRule)
			handleComplementRuleForCC((TGGComplementRule) rule, ibexPattern);
	}

	/**
	 * Complement rules require a positive invocation to the consistency pattern of
	 * their kernel rule.
	 * 
	 * @param rule
	 * @param ibexPattern
	 */
	private void handleComplementRuleForCC(TGGComplementRule crule, IBeXContextPattern ibexPattern) {
		parent.createConsistencyPattern(crule.getKernel());
		IBeXContextPattern consistencyPatternOfKernel = parent
				.getPattern(TGGPatternUtil.getConsistencyPatternName(crule.getKernel().getName()));

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

		invocation.setInvokedPattern(consistencyPatternOfKernel);
		ibexPattern.getInvocations().add(invocation);
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperator(rule, BindingType.CONTEXT);
		nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (final TGGRuleNode node : nodes) {
			parent.transformNode(ibexPattern, node);
		}

		// Transform in-node attributes
		for (final TGGRuleNode node : nodes) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
		edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.TRG));

		for (TGGRuleEdge edge : edges)
			parent.transformEdge(edges, edge, ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		FilterNACAnalysis filterNACAnalysis = new FilterNACAnalysis(DomainType.SRC, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}

		filterNACAnalysis = new FilterNACAnalysis(DomainType.TRG, rule, options);
		for (FilterNACCandidate candidate : filterNACAnalysis.computeFilterNACCandidates()) {
			parent.addContextPattern(createFilterNAC(ibexPattern, candidate));
		}
	}
}
