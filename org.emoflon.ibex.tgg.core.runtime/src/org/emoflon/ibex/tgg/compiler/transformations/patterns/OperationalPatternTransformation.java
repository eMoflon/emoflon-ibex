package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFilterNACPatternName;

import java.util.ArrayList;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import IBeXLanguage.IBeXContextPattern;
import IBeXLanguage.IBeXLanguageFactory;
import IBeXLanguage.IBeXNode;
import IBeXLanguage.IBeXPatternInvocation;
import language.TGGRule;
import language.TGGRuleNode;

public abstract class OperationalPatternTransformation {
	protected static final String NODE_NAME = "FILTER_NAC_NODE";
	
	protected ContextPatternTransformation parent;
	protected IbexOptions options;

	public OperationalPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		this.parent = parent;
		this.options = options;
	}

	protected abstract String getPatternName(TGGRule rule);

	protected abstract void handleComplementRules(TGGRule rule, IBeXContextPattern ibexPattern);

	protected abstract void transformNodes(IBeXContextPattern ibexPattern, TGGRule rule);

	protected abstract void transformEdges(IBeXContextPattern ibexPattern, TGGRule rule);
	
	protected abstract void transformNACs(IBeXContextPattern ibexPattern, TGGRule rule);

	public void transform(TGGRule rule) {
		String patternName = getPatternName(rule);

		if (parent.isTransformed(patternName))
			return;

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);
		parent.addContextPattern(ibexPattern, rule);

		// Transform nodes.
		transformNodes(ibexPattern, rule);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(ibexPattern);

		// Transform edges.
		transformEdges(ibexPattern, rule);

		// Transform NACs
		transformNACs(ibexPattern, rule);
		
		// Complement rule
		handleComplementRules(rule, ibexPattern);
	}

	protected IBeXContextPattern createFilterNAC(IBeXContextPattern ibexPattern, FilterNACCandidate candidate,
			TGGRule rule) {
		// Root pattern
		IBeXContextPattern nacPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		TGGRuleNode firstNode = candidate.getNodeInRule();
		IBeXNode firstIBeXNode = parent.transformNode(nacPattern, firstNode);

		addNodesOfSameTypeFromInvoker(rule, nacPattern, candidate, firstIBeXNode);

		IBeXNode secondIBeXNode = IBeXPatternFactory.createNode(NODE_NAME, getOtherNodeType(candidate));
		nacPattern.getSignatureNodes().add(secondIBeXNode);

		// Transform edges
		if (candidate.getEDirection() == EdgeDirection.OUTGOING)
			parent.transformEdge(candidate.getEdgeType(), firstIBeXNode, secondIBeXNode, nacPattern, false);
		else
			parent.transformEdge(candidate.getEdgeType(), secondIBeXNode, firstIBeXNode, nacPattern, false);

		nacPattern.setName(getFilterNACPatternName(candidate, rule));

		// Invoke NAC from parent: nodes with/without pre-image are signature/local
		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(false);
		ArrayList<IBeXNode> localNodes = new ArrayList<>();

		for (IBeXNode node : nacPattern.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(ibexPattern, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else
				localNodes.add(node);
		}

		nacPattern.getLocalNodes().addAll(localNodes);

		invocation.setInvokedPattern(nacPattern);
		ibexPattern.getInvocations().add(invocation);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);
		
		return nacPattern;
	}
	
	private void addNodesOfSameTypeFromInvoker(TGGRule rule, IBeXContextPattern nacPattern,
			FilterNACCandidate candidate, IBeXNode inRuleNode) {
		TGGRuleNode nodeInRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
		case INCOMING:
			nodeInRule.getIncomingEdges()
				.stream()//
				.filter(e -> e.getType().equals(candidate.getEdgeType()))//
				.forEach(e -> {
					IBeXNode node = parent.transformNode(nacPattern, e.getSrcNode());
					parent.transformEdge(candidate.getEdgeType(), node, inRuleNode, nacPattern, false);
				});
			break;

		case OUTGOING:
			nodeInRule.getOutgoingEdges()
				.stream()//
				.filter(e -> e.getType().equals(candidate.getEdgeType()))//
				.forEach(e -> {
					IBeXNode node = parent.transformNode(nacPattern, e.getTrgNode());
					parent.transformEdge(candidate.getEdgeType(), inRuleNode, node, nacPattern, false);
				});
			break;

		default:
			break;
		}
	}

	private EClass getOtherNodeType(FilterNACCandidate candidate) {
		return candidate.getEDirection() == EdgeDirection.OUTGOING ? (EClass) candidate.getEdgeType().getEType()
				: (EClass) candidate.getEdgeType().eContainer();
	}
}
