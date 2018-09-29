package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getProtocolNodeName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerRefName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getMarkerTypeName;
import static org.emoflon.ibex.tgg.core.util.TGGModelUtils.getNodesByOperatorAndDomain;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import language.BindingType;
import language.DomainType;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public class ConsistencyPatternTransformation extends OperationalPatternTransformation {

	public ConsistencyPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
	}

	private void createAndConnectProtocolNode(IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		protocolNode.setName(getProtocolNodeName(rule.getName()));
		EClass type = (EClass) options.getCorrMetamodel().getEClassifier(getMarkerTypeName(rule.getName()));
		protocolNode.setType(type);
		ibexPattern.getSignatureNodes().add(protocolNode);

		connectProtocolNode(ibexPattern, protocolNode);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode) {
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CONTEXT, DomainType.SRC);
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CONTEXT, DomainType.TRG);
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CREATE, DomainType.SRC);
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CREATE, DomainType.TRG);
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CONTEXT, DomainType.CORR);
		connectProtocolNode(ibexPattern, protocolNode, BindingType.CREATE, DomainType.CORR);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, BindingType type,
			DomainType domain) {
		Collection<TGGRuleNode> nodes = getNodesByOperatorAndDomain(rule, type, domain);

		for (TGGRuleNode node : nodes) {
			EReference ref = (EReference) protocolNode.getType()
					.getEStructuralFeature(getMarkerRefName(type, domain, node.getName()));
			parent.transformEdge(ref, protocolNode, parent.transformNode(ibexPattern, node), ibexPattern, false);
		}
	}

	@Override
	protected String getPatternName() {
		return getConsistencyPatternName(rule.getName());
	}

	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
		// Nothing to do
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		rule.getNodes().forEach(n -> {
			parent.transformNode(ibexPattern, n);
		});

		// Transform attributes
		for (final TGGRuleNode node : rule.getNodes()) {
			parent.transformInNodeAttributeConditions(ibexPattern, node);
		}
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		for (TGGRuleEdge edge : rule.getEdges())
			parent.transformEdge(rule.getEdges(), edge, ibexPattern);

		// Create protocol node and connections to nodes in pattern
		createAndConnectProtocolNode(ibexPattern);

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
