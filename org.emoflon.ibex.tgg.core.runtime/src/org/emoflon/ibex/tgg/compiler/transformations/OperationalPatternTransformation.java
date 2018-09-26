package org.emoflon.ibex.tgg.compiler.transformations;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getConsistencyPatternName;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGComplementRule;
import language.TGGRule;
import language.TGGRuleNode;

public class OperationalPatternTransformation {

	protected ContextPatternTransformation parent;
	protected IbexOptions options;

	public OperationalPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		this.parent = parent;
		this.options = options;
	}

	protected String getPatternName(TGGRule rule) {
		throw new IllegalArgumentException("Method getPatternName must be overridden!");
	}

	protected void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern) {
		throw new IllegalArgumentException("Method handleComplementRules must be overridden!");
	}

	protected void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule) {
		throw new IllegalArgumentException("Method transformNodes must be overridden!");
	}

	protected void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule) {
		throw new IllegalArgumentException("Method transformNodes must be overridden!");
	}

	public void transform(TGGRule rule) {
		String patternName = getPatternName(rule);

		if (parent.isTransformed(patternName))
			return;

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes.
		transformNodes(ibexPattern, rule);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		transformEdges(ibexPattern, rule);

		// NACs
		for (NAC nac : rule.getNacs())
			parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);

		// Complement rule
		handleComplementRules(rule, ibexPattern);
	}

	protected IBeXContextPattern createConsistencyPattern(TGGRule rule, TGGComplementRule crule) {
		String patternName = getConsistencyPatternName(rule.getName());

		if (parent.isTransformed(patternName))
			return parent.getPattern(patternName);

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);

		// Transform nodes
		rule.getNodes().stream()//
				.forEach(n -> {
					parent.transformNode(ibexPattern, n);
				});

		// Ensure that all nodes must be disjoint even if they have the same type
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Create protocol node and connections to nodes in pattern
		createAndConnectProtocolNode(rule, ibexPattern);

		parent.addContextPattern(ibexPattern, rule);

		return ibexPattern;
	}

	private void createAndConnectProtocolNode(TGGRule rule, IBeXContextPattern ibexPattern) {
		IBeXNode protocolNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
		protocolNode.setName(TGGPatternUtil.getProtocolNodeName(rule.getName()));
		EClass type = (EClass) options.getCorrMetamodel()
				.getEClassifier(TGGModelUtils.getMarkerTypeName(rule.getName()));
		protocolNode.setType(type);
		ibexPattern.getSignatureNodes().add(protocolNode);

		connectProtocolNode(ibexPattern, protocolNode, rule);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.CORR);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.CORR);
	}

	private void connectProtocolNode(IBeXContextPattern ibexPattern, TGGRule rule, IBeXNode protocolNode,
			BindingType type, DomainType domain) {
		Collection<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperatorAndDomain(rule, type, domain);

		for (TGGRuleNode node : nodes) {
			EReference ref = (EReference) protocolNode.getType()
					.getEStructuralFeature(TGGModelUtils.getMarkerRefName(type, domain, node.getName()));
			parent.transformEdge(ref, protocolNode, parent.transformNode(ibexPattern, node), ibexPattern,
					rule.getEdges().size()
							+ rule.getNodes().size() > ContextPatternTransformation.MAX_NUM_OF_EDGES_IN_PATTERN);
		}
	}
}