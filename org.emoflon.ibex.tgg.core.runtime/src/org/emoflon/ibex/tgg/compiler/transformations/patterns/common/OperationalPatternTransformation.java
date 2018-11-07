package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;
import static org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper.addInjectivityConstraintIfNecessary;
import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getFilterNACPatternName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.tgg.compiler.patterns.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.IBeXPatternOptimiser;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
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
	protected TGGRule rule;

	public OperationalPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		this.parent = parent;
		this.options = options;
		this.rule = rule;
	}

	protected abstract String getPatternName();

	protected abstract void handleComplementRules(IBeXContextPattern ibexPattern);

	protected abstract void transformNodes(IBeXContextPattern ibexPattern);

	protected abstract void transformEdges(IBeXContextPattern ibexPattern);

	protected abstract void transformNACs(IBeXContextPattern ibexPattern);

	public IBeXContextPattern transform() {
		String patternName = getPatternName();

		if (parent.isTransformed(patternName))
			return parent.getPattern(patternName);

		// Root pattern
		IBeXContextPattern ibexPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();
		ibexPattern.setName(patternName);
		parent.addContextPattern(ibexPattern, rule);

		// Transform nodes.
		transformNodes(ibexPattern);

		// Ensure that all nodes must be disjoint even if they have the same type.
		List<TGGRuleNode> allNodes = rule.getNodes();
		for (int i = 0; i < allNodes.size(); i++) {
			for (int j = i + 1; j < allNodes.size(); j++) {
				TGGRuleNode ruleNode_i = allNodes.get(i);
				TGGRuleNode ruleNode_j = allNodes.get(j);
				if(options.ignoreSrcTrgInjectivity() || ruleNode_i.getBindingType() != ruleNode_j.getBindingType())
					continue;
				
				if (IBeXPatternOptimiser.unequalConstraintNecessary(ruleNode_i, ruleNode_j)) {
					findIBeXNodeWithName(ibexPattern, ruleNode_i.getName())//
							.ifPresent(ni -> findIBeXNodeWithName(ibexPattern, ruleNode_j.getName())//
									.ifPresent(nj -> addInjectivityConstraintIfNecessary(ibexPattern, ni, nj)));
				}
			}
		}

		// Transform edges.
		transformEdges(ibexPattern);

		// Transform NACs
		transformNACs(ibexPattern);

		// Complement rule
		handleComplementRules(ibexPattern);
		
		return ibexPattern;
	}

	protected IBeXContextPattern createFilterNAC(IBeXContextPattern ibexPattern, FilterNACCandidate candidate) {
		// Root pattern
		IBeXContextPattern nacPattern = IBeXLanguageFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		TGGRuleNode firstNode = candidate.getNodeInRule();
		IBeXNode firstIBeXNode = parent.transformNode(nacPattern, firstNode);

		addNodesOfSameTypeFromInvoker(nacPattern, candidate, firstIBeXNode);

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
			Optional<IBeXNode> src = findIBeXNodeWithName(ibexPattern, node.getName());

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
	
	protected void createInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee, boolean isPositive) {
		IBeXPatternInvocation invocation = IBeXLanguageFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(isPositive);

		// Creating mapping for invocation: missing signature nodes of the invoked
		// pattern are added as local nodes to the invoking pattern
		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else {
				IBeXNode newLocalNode = IBeXLanguageFactory.eINSTANCE.createIBeXNode();
				newLocalNode.setName(node.getName());
				newLocalNode.setType(node.getType());
				invoker.getLocalNodes().add(newLocalNode);

				invocation.getMapping().put(newLocalNode, node);
			}
		}
		
		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
	}

	private void addNodesOfSameTypeFromInvoker(IBeXContextPattern nacPattern,
			FilterNACCandidate candidate, IBeXNode inRuleNode) {
		TGGRuleNode nodeInRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
		case INCOMING:
			nodeInRule.getIncomingEdges().stream()//
					.filter(e -> e.getType().equals(candidate.getEdgeType()))//
					.forEach(e -> {
						IBeXNode node = parent.transformNode(nacPattern, e.getSrcNode());
						parent.transformEdge(candidate.getEdgeType(), node, inRuleNode, nacPattern, false);
					});
			break;

		case OUTGOING:
			nodeInRule.getOutgoingEdges().stream()//
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
