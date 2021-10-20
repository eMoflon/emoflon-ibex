package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import static org.emoflon.ibex.common.patterns.IBeXPatternUtils.findIBeXNodeWithName;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.patterns.IBeXPatternFactory;
import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.gt.transformations.EditorToIBeXPatternHelper;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXEdge;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.ConclusionRule;
import org.emoflon.ibex.tgg.compiler.patterns.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.common.OperationalPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.SRCPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.TRGPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;

import language.DomainType;
import language.TGGRule;
import language.TGGRuleNode;

public class ACPatternTransformation {

	protected static final String FILTER_NAC_NODE_NAME = "FILTER_NAC_NODE";
	protected static final String PAC_NODE_NAME = "PAC_NODE";

	private IbexOptions options;
	private ContextPatternTransformation parent;

	public ACPatternTransformation(ContextPatternTransformation parent, IbexOptions options) {
		this.parent = parent;
		this.options = options;
	}

	protected IBeXContextPattern createFilterNAC(TGGRule rule, IBeXContextPattern ibexPattern, FilterNACCandidate candidate) {
		TGGRuleNode firstNode = candidate.getNodeInRule();

		BiFunction<FilterNACCandidate, TGGRule, String> getFilterNACPatternName;
		if (firstNode.getDomainType() == DomainType.SRC)
			getFilterNACPatternName = TGGPatternUtil::getFilterNACSRCPatternName;
		else
			getFilterNACPatternName = TGGPatternUtil::getFilterNACTRGPatternName;

		if (parent.isTransformed(getFilterNACPatternName.apply(candidate, rule))) {
			IBeXContextPattern nacPattern = parent.getPattern(getFilterNACPatternName.apply(candidate, rule));
			createNegativeInvocation(ibexPattern, nacPattern);
			return nacPattern;
		}

		// Root pattern
		IBeXContextPattern nacPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();

		// Transform nodes
		IBeXNode firstIBeXNode = parent.transformNode(nacPattern, firstNode);

		addNodesOfSameTypeFromInvoker(nacPattern, candidate, firstIBeXNode);

		IBeXNode secondIBeXNode = IBeXPatternFactory.createNode(FILTER_NAC_NODE_NAME, getOtherNodeType(candidate));
		nacPattern.getSignatureNodes().add(secondIBeXNode);

		// Transform edges
		if (candidate.getEDirection() == EdgeDirection.OUTGOING)
			parent.transformEdge(candidate.getEdgeType(), firstIBeXNode, secondIBeXNode, nacPattern);
		else
			parent.transformEdge(candidate.getEdgeType(), secondIBeXNode, firstIBeXNode, nacPattern);

		nacPattern.setName(getFilterNACPatternName.apply(candidate, rule));

		// Invoke NAC from parent: nodes with/without pre-image are signature/local
		createNegativeInvocation(ibexPattern, nacPattern);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);

		return nacPattern;
	}

	private void createNegativeInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(false);
		ArrayList<IBeXNode> localNodes = new ArrayList<>();

		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else if (!invokee.getSignatureNodes().contains(node))
				localNodes.add(node);
		}

		invokee.getLocalNodes().addAll(localNodes);

		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
	}

	private void addNodesOfSameTypeFromInvoker(IBeXContextPattern nacPattern, FilterNACCandidate candidate, IBeXNode inRuleNode) {
		TGGRuleNode nodeInRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
		case INCOMING:
			nodeInRule.getIncomingEdges().stream() //
					.filter(e -> e.getType().equals(candidate.getEdgeType())) //
					.forEach(e -> {
						IBeXNode node = parent.transformNode(nacPattern, e.getSrcNode());
						parent.transformEdge(candidate.getEdgeType(), node, inRuleNode, nacPattern);
					});
			break;

		case OUTGOING:
			nodeInRule.getOutgoingEdges().stream() //
					.filter(e -> e.getType().equals(candidate.getEdgeType())) //
					.forEach(e -> {
						IBeXNode node = parent.transformNode(nacPattern, e.getTrgNode());
						parent.transformEdge(candidate.getEdgeType(), inRuleNode, node, nacPattern);
					});
			break;

		default:
			break;
		}
	}

	protected IBeXContextPattern createPAC(TGGRule rule, IBeXContextPattern ibexPattern, DomainType domain, PACCandidate pacCandidate,
			ACAnalysis filterNACAnalysis) {
		IBeXContextPattern premisePattern = createFilterNAC(rule, ibexPattern, pacCandidate.getPremise());
		// FWD/BWD and Consistency are calling create PAC so it is possible that the PAC was already created
		if (!premisePattern.getInvocations().isEmpty())
			return premisePattern;

		// if there is only one conclusion there is no need for a extra PAC-Pattern, a single negative
		// pattern invocation is enough
		if (pacCandidate.getConclusionRules().size() == 1) {
			ConclusionRule conclusion = pacCandidate.getConclusionRules().get(0);
			if (!parent.isTransformed(conclusion.getConclusionRule().getName() + "__" + domain)) {
				OperationalPatternTransformation transformer;
				if (domain == DomainType.SRC)
					transformer = new SRCPatternTransformation(parent, options, conclusion.getConclusionRule(), filterNACAnalysis);
				else
					transformer = new TRGPatternTransformation(parent, options, conclusion.getConclusionRule(), filterNACAnalysis);
				transformer.transform();
			}
			IBeXContextPattern conclusionPattern = parent.getPattern(conclusion.getConclusionRule().getName() + "__" + domain);
			if (conclusionPattern == null) {
				return premisePattern;
			} else {
				createPACPatternInvocation(conclusionPattern, premisePattern, pacCandidate, conclusion, false, false);
			}
		} else if (pacCandidate.getConclusionRules().size() > 1) {
			IBeXContextPattern pacPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
			pacPattern.setName(rule.getName() + "_" + pacCandidate.getPremise() + PatternSuffixes.PAC);
			IBeXPatternInvocation pacInv = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
			pacInv.setPositive(true);
			pacInv.setInvokedPattern(pacPattern);
			pacInv.setInvokedBy(premisePattern);

			IBeXNode pacNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
			pacNode.setName(pacCandidate.getPremise().getNodeInRule().getName());
			pacNode.setType(pacCandidate.getPremise().getNodeInRule().getType());
			pacPattern.getSignatureNodes().add(pacNode);
			Optional<IBeXNode> premiseNode = IBeXPatternUtils.findIBeXNodeWithName( //
					premisePattern, pacCandidate.getPremise().getNodeInRule().getName());
			if (premiseNode.isPresent())
				pacInv.getMapping().put(premiseNode.get(), pacNode);

			IBeXNode otherPACNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
			otherPACNode.setName(PAC_NODE_NAME);
			otherPACNode.setType(pacCandidate.getPremise().getOtherNodeType());
			pacPattern.getLocalNodes().add(otherPACNode);
			Optional<IBeXNode> premiseFilterNACNode = IBeXPatternUtils.findIBeXNodeWithName(premisePattern, FILTER_NAC_NODE_NAME);
			if (premiseFilterNACNode.isPresent())
				pacInv.getMapping().put(premiseFilterNACNode.get(), otherPACNode);

			premisePattern.getInvocations().add(pacInv);

			for (ConclusionRule conclusionRule : pacCandidate.getConclusionRules()) {
				if (!parent.isTransformed(conclusionRule.getConclusionRule().getName() + "__" + domain)) {
					OperationalPatternTransformation transformer;
					if (domain == DomainType.SRC)
						transformer = new SRCPatternTransformation(parent, options, conclusionRule.getConclusionRule(), filterNACAnalysis);
					else
						transformer = new TRGPatternTransformation(parent, options, conclusionRule.getConclusionRule(), filterNACAnalysis);

					transformer.transform();
				}
				IBeXContextPattern conclusionPattern = parent.getPattern(conclusionRule.getConclusionRule().getName() + "__" + domain);
				createPACPatternInvocation(conclusionPattern, pacPattern, pacCandidate, conclusionRule, false, true);
			}
			parent.addContextPattern(pacPattern);
		}
		return premisePattern;
	}

	/*
	 * creates a Pattern-Invocation from the conclusion with the PacPattern if there's only one
	 * conclusion the Filter_Nac-Pattern will be used instead of the PacPattern
	 */
	private void createPACPatternInvocation(IBeXContextPattern conclusionPattern, IBeXContextPattern pacPattern, PACCandidate pacCandidate,
			ConclusionRule conclusionRule, boolean pos, boolean pacPatternNeeded) {
		IBeXPatternInvocation inv = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		inv.setPositive(pos);
		inv.setInvokedPattern(conclusionPattern);
		inv.setInvokedBy(pacPattern);
		Optional<IBeXNode> pacNode = IBeXPatternUtils.findIBeXNodeWithName( //
				pacPattern, pacCandidate.getPremise().getNodeInRule().getName());
		Optional<IBeXNode> conclusionNode = IBeXPatternUtils.findIBeXNodeWithName( //
				conclusionPattern, conclusionRule.getPremiseConclusionNode().getName());
		if (pacNode.isPresent() && conclusionNode.isPresent()) {
			inv.getMapping().put(pacNode.get(), conclusionNode.get());

			Optional<IBeXNode> otherNodeConclusion = Optional.of( //
					findOtherNode(pacCandidate.getPremise().getEDirection(), conclusionNode.get(), pacCandidate.getPremise().getEdgeType()) //
			);
			Optional<IBeXNode> otherNodePremise;
			// If only one conclusion exist, there is no need for a separate pacPattern (NAC_Pattern can be
			// used) but the mapping is changing
			if (!pacPatternNeeded) {
				otherNodePremise = Optional.of( //
						findOtherNode(pacCandidate.getPremise().getEDirection(), pacNode.get(), pacCandidate.getPremise().getEdgeType()) //
				);
			} else {
				otherNodePremise = IBeXPatternUtils.findIBeXNodeWithName(pacPattern, PAC_NODE_NAME);
			}
			if (otherNodeConclusion.isPresent() && otherNodePremise.isPresent())
				inv.getMapping().put(otherNodePremise.get(), otherNodeConclusion.get());
			// To map all nodes from the conclusion with the pacPattern
			// example special case-> TGG: FamiliesToPersons_V0, Rule: ReplaceFatherWithSon, Pattern:
			// ReplaceFatherWithSon_son_father_incoming_SRC__FILTER_NAC_SRC
			for (IBeXNode n : conclusionPattern.getSignatureNodes()) {
				// already mapped previously
				if (n.equals(conclusionNode.get()) || n.equals(otherNodeConclusion.get()))
					continue;
				Optional<IBeXNode> optNode = pacPattern.getLocalNodes().stream() //
						.filter(node -> node.getType().equals(n.getType()) && !inv.getMapping().containsKey(node)) //
						.findAny();
				if (optNode.isPresent()) {
					inv.getMapping().put(optNode.get(), n);
				} else {
					IBeXNode pN = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
					pN.setName("PAC_NODE_" + n.getName());
					pN.setType(n.getType());
					optNode = Optional.of(pN);
					pacPattern.getLocalNodes().add(optNode.get());
					inv.getMapping().put(optNode.get(), n);
				}
			}
		}

		pacPattern.getInvocations().add(inv);
	}

	/*
	 * finds the other node from a edge with the information about the node and the EReference of the
	 * edge
	 */
	private IBeXNode findOtherNode(EdgeDirection direction, IBeXNode node, EReference edgeType) {
		if (direction.equals(EdgeDirection.OUTGOING))
			for (IBeXEdge edge : node.getOutgoingEdges()) {
				if (edge.getType().equals(edgeType)) {
					return edge.getTargetNode();
				}
			}
		else
			for (IBeXEdge edge : node.getIncomingEdges()) {
				if (edge.getType().equals(edgeType)) {
					return edge.getSourceNode();
				}
			}
		return null;
	}

	private EClass getOtherNodeType(FilterNACCandidate candidate) {
		return candidate.getEDirection() == EdgeDirection.OUTGOING ? //
				(EClass) candidate.getEdgeType().getEType() : //
				(EClass) candidate.getEdgeType().eContainer();
	}
}
