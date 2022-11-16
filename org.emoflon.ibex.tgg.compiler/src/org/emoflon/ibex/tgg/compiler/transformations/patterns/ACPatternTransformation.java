package org.emoflon.ibex.tgg.compiler.transformations.patterns;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXCoreModelFactory;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXEdge;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXNode;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXOperationType;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPattern;
import org.emoflon.ibex.common.coremodel.IBeXCoreModel.IBeXPatternInvocation;
import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.ConclusionRule;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate.EdgeDirection;
import org.emoflon.ibex.tgg.compiler.patterns.PACCandidate;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.runtime.config.options.IbexOptions;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.DomainType;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGModel;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGNode;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGOperationalRule;
import org.emoflon.ibex.tgg.tggmodel.IBeXTGGModel.TGGRule;

public class ACPatternTransformation {

	
	protected static final String FILTER_NAC_NODE_NAME = "FILTER_NAC_NODE";
	protected static final String PAC_NODE_NAME = "PAC_NODE";
	
	private IBeXCoreModelFactory factory = IBeXCoreModelFactory.eINSTANCE;

	private TGGModel tgg;
	private IbexOptions options;

	private Map<IBeXNode, IBeXNode> copyToOriginal = new HashMap<>();
	
	public ACPatternTransformation(TGGModel tgg, IbexOptions options) {
		this.tgg = tgg;
		this.options = options;
	}

	/**
	 * Given a filterNACCandidate, we create a filterNAC for a given operationalRule
	 * @param operationalRule
	 * @param candidate
	 */
	protected void addFilterNAC(TGGOperationalRule operationalRule, FilterNACCandidate candidate) {
		TGGNode firstNode = candidate.getNodeInRule();

		BiFunction<FilterNACCandidate, TGGRule, String> getFilterNACPatternName;
		if (firstNode.getDomainType() == DomainType.SOURCE)
			getFilterNACPatternName = TGGPatternUtil::getFilterNACSRCPatternName;
		else
			getFilterNACPatternName = TGGPatternUtil::getFilterNACTRGPatternName;

		// NAC pattern
		var nacPattern = factory.createIBeXPattern();
		nacPattern.setName(getFilterNACPatternName.apply(candidate, operationalRule));

		// Transform nodes
		IBeXNode checkedNode = copyIBeXNode(firstNode);
		nacPattern.getSignatureNodes().add(checkedNode);

		addNodesOfSameTypeFromInvoker(nacPattern, checkedNode, candidate);

		// nac node should be local since when calling this pattern, we are not interested in how many NACs are violated
		IBeXNode nacNode = factory.createIBeXNode();
		nacNode.setName(FILTER_NAC_NODE_NAME);
		nacNode.setOperationType(IBeXOperationType.CONTEXT);
		nacNode.setType(getNACNodeType(candidate));
		nacPattern.getLocalNodes().add(nacNode);

		// create edge to nac node
		IBeXEdge nacEdge = factory.createIBeXEdge();
		nacEdge.setName("nac edge");
		nacEdge.setOperationType(IBeXOperationType.CONTEXT);
		nacEdge.setType(candidate.getEdgeType());
		
		if (candidate.getEDirection() == EdgeDirection.OUTGOING) {
			nacEdge.setSource(checkedNode);
			nacEdge.setTarget(nacNode);
		}
		else {
			nacEdge.setSource(nacNode);
			nacEdge.setTarget(checkedNode);
		}

		// call nac pattern form both the pre and the postcondition
		createNegativeInvocation(operationalRule.getPrecondition(), nacPattern);
		createNegativeInvocation(operationalRule.getPostcondition(), nacPattern);

		// Ensure that all nodes must be disjoint even if they have the same type.
		EditorToIBeXPatternHelper.addInjectivityConstraints(nacPattern);

		return nacPattern;
	}

	/**
	 * Copy the node and register it 
	 * 
	 * @param node
	 * @return
	 */
	private IBeXNode copyIBeXNode(IBeXNode node) {
		var newNode = factory.createIBeXNode();
		newNode.setName(node.getName());
		newNode.setType(node.getType());
		newNode.setOperationType(IBeXOperationType.CONTEXT);
		tgg.getNodeSet().getNodes().add(newNode);
		
		copyToOriginal.put(newNode, node);
		return newNode;
	}
	
	/**
	 * Copy the edge, register it and connect it with two nodes
	 * 
	 * @param edge
	 * @param from
	 * @param to
	 * @return
	 */
	private IBeXEdge copyIBeXEdge(IBeXEdge edge, IBeXNode from, IBeXNode to) {
		var newEdge = factory.createIBeXEdge();
		newEdge.setName(edge.getName());
		newEdge.setType(edge.getType());
		newEdge.setOperationType(IBeXOperationType.CONTEXT);
		newEdge.setSource(from);
		newEdge.setTarget(to);
		tgg.getEdgeSet().getEdges().add(newEdge);
		return newEdge;
	}
	
	/**
	 * Connect both pattern with a negative pattern invocation
	 * 
	 * @param invoker
	 * @param invokee
	 */
	private void createNegativeInvocation(IBeXPattern invoker, IBeXPattern invokee) {
		IBeXPatternInvocation invocation = factory.createIBeXPatternInvocation();
		invocation.setPositive(false);
		invocation.setInvocation(invokee);
		invocation.setInvokedBy(invoker);
		
		// connect all elements with their counterpart in the calling pattern
		for (IBeXNode node : invokee.getSignatureNodes()) {
			var original = copyToOriginal.get(node);
			invocation.getMapping().put(node, original);
		}
	}

	/**
	 * This method adds other nodes to the nac pattern, which have the same type as the nac candidate.
	 * The consequence of not doing this would be that no match could be found as the nac would block all
	 * valid tgg matches.
	 * 
	 * @param nacPattern
	 * @param checkedNode the copy of the node within the nac pattern that is the source of the DEC analysis
	 * @param candidate
	 */
	private void addNodesOfSameTypeFromInvoker(IBeXPattern nacPattern, IBeXNode checkedNode, FilterNACCandidate candidate) {
		TGGNode nodeInOriginalRule = candidate.getNodeInRule();
		switch (candidate.getEDirection()) {
			case INCOMING -> {
				nodeInOriginalRule.getIncomingEdges().stream() //
						.filter(e -> e.getType().equals(candidate.getEdgeType())) //
						.forEach(e -> {
							// make a copy of this node and append it to nac pattern then connect it via edge
							IBeXNode nodeCopy = copyIBeXNode(e.getSource());
							nacPattern.getSignatureNodes().add(nodeCopy);
							
							IBeXEdge edgeCopy = copyIBeXEdge(e, nodeCopy, checkedNode);
							nacPattern.getEdges().add(edgeCopy);
						});
			}
			case OUTGOING -> {
				nodeInOriginalRule.getOutgoingEdges().stream() //
						.filter(e -> e.getType().equals(candidate.getEdgeType())) //
						.forEach(e -> {
							// make a copy of this node and append it to nac pattern then connect it via edge
							IBeXNode nodeCopy = copyIBeXNode(e.getSource());
							nacPattern.getSignatureNodes().add(nodeCopy);
							
							IBeXEdge edgeCopy = copyIBeXEdge(e, checkedNode, nodeCopy);
							nacPattern.getEdges().add(edgeCopy);
						});
			}
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
					return edge.getTarget();
				}
			}
		else
			for (IBeXEdge edge : node.getIncomingEdges()) {
				if (edge.getType().equals(edgeType)) {
					return edge.getSource();
				}
			}
		return null;
	}

	private EClass getNACNodeType(FilterNACCandidate candidate) {
		return candidate.getEDirection() == EdgeDirection.OUTGOING ? //
				(EClass) candidate.getEdgeType().getEType() : //
				(EClass) candidate.getEdgeType().eContainer();
	}
}
