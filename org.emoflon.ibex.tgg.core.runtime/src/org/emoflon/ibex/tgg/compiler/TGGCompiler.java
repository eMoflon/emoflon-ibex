package org.emoflon.ibex.tgg.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emoflon.ibex.tgg.compiler.pattern.IbexPattern;
import org.emoflon.ibex.tgg.compiler.pattern.common.MarkedPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.ConsistencyPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.SrcProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.protocol.nacs.TrgProtocolNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.BWDPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.CCPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.FWDPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.MODELGENPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.RulePartPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.WholeRulePattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.ConstraintPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.CorrContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.MODELGENNoNACsPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.SrcProtocolAndDECPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgContextPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.TrgProtocolAndDECPattern;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.DECTrackingHelper;
import org.emoflon.ibex.tgg.compiler.pattern.rulepart.support.DEC.NoDECsPatterns;

import language.BindingType;
import language.DomainType;
import language.TGG;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleElement;
import language.TGGRuleNode;

public class TGGCompiler {
	

	private List<MarkedPattern> markedPatterns = new ArrayList<>();
	private Map<TGGRule, Collection<IbexPattern>> ruleToPatterns = new LinkedHashMap<>();

	private TGG tgg;
	private TGG flattenedTgg;
	
	private DECTrackingHelper decTC;

	public TGGCompiler(TGG tgg, TGG flattenedTgg) {
		this.tgg = tgg;
		this.flattenedTgg = flattenedTgg;
		
		// initialise DECTrackingContainer that contains information for DEC
		// generation such as which patterns belongs to which rule or the
		// mapping of signature elements for the calls to external patterns
		decTC = new DECTrackingHelper(ruleToPatterns);
	}
	
	public Map<TGGRule, Collection<IbexPattern>> getRuleToPatternMap(){
		return ruleToPatterns;
	}

	public void preparePatterns() {
		createMarkedPatterns();
		
		for (TGGRule rule : tgg.getRules()) {
			Collection<IbexPattern> patterns = new ArrayList<>();
			
			SrcContextPattern srcContext = new SrcContextPattern(rule);
			patterns.add(srcContext);

			SrcPattern src = new SrcPattern(rule);
			patterns.add(src);
			src.addTGGPositiveInvocation(srcContext);

			SrcProtocolNACsPattern srcProtocolNACs = new SrcProtocolNACsPattern(rule, markedPatterns);
			patterns.add(srcProtocolNACs);
			srcProtocolNACs.addTGGPositiveInvocation(src);
			
			SrcProtocolAndDECPattern srcProtocolDECs = new SrcProtocolAndDECPattern(rule, markedPatterns);
			patterns.add(srcProtocolDECs);
			srcProtocolDECs.addTGGPositiveInvocation(srcProtocolNACs);

			TrgContextPattern trgContext = new TrgContextPattern(rule);
			patterns.add(trgContext);

			TrgPattern trg = new TrgPattern(rule);
			patterns.add(trg);
			trg.addTGGPositiveInvocation(trgContext);

			TrgProtocolNACsPattern trgProtocolNACs = new TrgProtocolNACsPattern(rule, markedPatterns);
			patterns.add(trgProtocolNACs);
			trgProtocolNACs.addTGGPositiveInvocation(trg);

			TrgProtocolAndDECPattern trgProtocolDECs = new TrgProtocolAndDECPattern(rule, markedPatterns);
			patterns.add(trgProtocolDECs);
			trgProtocolDECs.addTGGPositiveInvocation(trgProtocolNACs);
			
			CorrContextPattern corrContext = new CorrContextPattern(rule);
			patterns.add(corrContext);

			//TODO [fstolte]: add invocation for refinement, change CCPattern so that it is able to receive dummyNodes
			CCPattern cc = new CCPattern(rule);
			// add dummy nodes to CCPattern that are necessary for pattern invocation to patterns of super-rule
			addDummyNodes(cc);
			patterns.add(cc);
			cc.addTGGPositiveInvocation(src);
			cc.addTGGPositiveInvocation(trg);
			cc.addTGGPositiveInvocation(corrContext);

			FWDPattern fwd = new FWDPattern(rule);
			patterns.add(fwd);
			fwd.addTGGPositiveInvocation(srcProtocolDECs);
			fwd.addTGGPositiveInvocation(corrContext);
			fwd.addTGGPositiveInvocation(trgContext);

			BWDPattern bwd = new BWDPattern(rule);
			patterns.add(bwd);
			bwd.addTGGPositiveInvocation(trgProtocolDECs);
			bwd.addTGGPositiveInvocation(corrContext);
			bwd.addTGGPositiveInvocation(srcContext);
			
			MODELGENNoNACsPattern modelgenNoNACs = new MODELGENNoNACsPattern(rule);
			// add dummy nodes to MODELGENNoNACsPattern that are necessary for pattern invocation to patterns of super-rule
			addDummyNodes(modelgenNoNACs);
			patterns.add(modelgenNoNACs);
			modelgenNoNACs.addTGGPositiveInvocation(srcContext);
			modelgenNoNACs.addTGGPositiveInvocation(trgContext);
			modelgenNoNACs.addTGGPositiveInvocation(corrContext);

			MODELGENPattern modelgen = new MODELGENPattern(rule);
			// add dummy nodes to MODELGENPattern that are necessary for pattern invocation to patterns of super-rule
			addDummyNodes(modelgen);
			patterns.add(modelgen);
			modelgen.addTGGPositiveInvocation(modelgenNoNACs);
			addPatternInvocationsForMultiplicityConstraints(patterns, modelgen);
			addPatternInvocationsForContainmentReferenceConstraints(patterns, modelgen);

			WholeRulePattern whole = new WholeRulePattern(rule);
			patterns.add(whole);
			whole.addTGGPositiveInvocation(src);
			whole.addTGGPositiveInvocation(trg);
			whole.addTGGPositiveInvocation(corrContext);

			ConsistencyPattern protocol = new ConsistencyPattern(rule, getMarkedPatterns());
			patterns.add(protocol);
			protocol.addTGGPositiveInvocation(whole);

			ruleToPatterns.put(rule, patterns);
		}
		
      // add pattern invocations to MODELGENPatterns for rule refinement
		ruleToPatterns.values().stream()
				   			   .flatMap(p -> p.stream())
				   			   .filter(p -> p instanceof MODELGENNoNACsPattern)
				   			   .forEach(p -> p.getRule().getRefines().stream()
				   					   								 .flatMap(r -> ruleToPatterns.get(r).stream())
				   					   								 .filter(pattern -> pattern instanceof MODELGENNoNACsPattern)
				   					   								 .forEach(r -> p.addTGGPositiveInvocation(r)));
		
      // add pattern invocations to CCPatterns for rule refinement
		ruleToPatterns.values().stream()
				   			   .flatMap(p -> p.stream())
				   			   .filter(p -> p instanceof CCPattern)
				   			   .forEach(p -> p.getRule().getRefines().stream()
				   					   								 .flatMap(r -> ruleToPatterns.get(r).stream())
				   					   								 .filter(pattern -> pattern instanceof CCPattern)
				   					   								 .forEach(r -> p.addTGGPositiveInvocation(r)));



		// add no DEC patterns to Src- and TrgPattern, respectively and register them
		for (TGGRule rule : tgg.getRules()) {
			//FIXME:  CC patterns should invoke NoDEC patterns as well
			addFilterACs(rule, DomainType.SRC, SrcProtocolAndDECPattern.class);
			addFilterACs(rule, DomainType.TRG, TrgProtocolAndDECPattern.class);
		}
	}
	
	private void addFilterACs(TGGRule rule, DomainType domain, Class<?> type) {
		List<IbexPattern> relevantPatterns = ruleToPatterns.get(rule)
			.stream()
			.filter(p -> type.isInstance(p))
			.collect(Collectors.toList());
		
		if(relevantPatterns.isEmpty()) return;
		
		NoDECsPatterns filterACPatterns = new NoDECsPatterns(rule, decTC, domain);
		ruleToPatterns.get(rule).add(filterACPatterns);
		relevantPatterns.forEach(p -> p.addTGGPositiveInvocation(filterACPatterns));
	}

	public List<MarkedPattern> getMarkedPatterns() {
		return markedPatterns;
	}
	
	public static LinkedHashSet<EReference> getEdgeTypes(TGG tgg) {
		return tgg.getRules().stream().flatMap(r -> r.getEdges().stream()).map(e -> e.getType()).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	public void createMarkedPatterns() {
		MarkedPattern signProtocolSrcMarkedPattern = new MarkedPattern(DomainType.SRC, false);
		MarkedPattern signProtocolTrgMarkedPattern = new MarkedPattern(DomainType.TRG, false);
		MarkedPattern localProtocolSrcMarkedPattern = new MarkedPattern(signProtocolSrcMarkedPattern, DomainType.SRC, true);
		MarkedPattern localProtocolTrgMarkedPattern = new MarkedPattern(signProtocolTrgMarkedPattern, DomainType.TRG, true);
		
		localProtocolSrcMarkedPattern.addTGGPositiveInvocation(signProtocolSrcMarkedPattern);
		localProtocolTrgMarkedPattern.addTGGPositiveInvocation(signProtocolTrgMarkedPattern);
		
		markedPatterns.add(localProtocolSrcMarkedPattern);
		markedPatterns.add(localProtocolTrgMarkedPattern);
		markedPatterns.add(signProtocolSrcMarkedPattern);
		markedPatterns.add(signProtocolTrgMarkedPattern);
	}
	
	private void addDummyNodes(RulePartPattern pattern) {
		// This method only works for pattern types whose signatureElements can be altered.
		// This means that changes to the collection returned by getSignatureElements()
		// need to be recognized by the pattern.
		TGGRule rule = pattern.getRule();
		TGGRule flattenedRule = flattenedTgg.getRules().stream()
													   .filter(r -> r.getName().equals(rule.getName()))
													   .findAny().get();
		
		flattenedRule.getNodes().stream()
								.filter(n -> pattern.isRelevantForSignature(n))
								.filter(n -> !pattern.getSignatureElements().stream()
										  									.anyMatch(sigNode -> sigNode.getName().equals(n.getName())))
								.forEach(n -> {
									TGGRuleNode dummy = EcoreUtil.copy(n);
									pattern.getSignatureElements().add(dummy);
								});
	}
	
	/**
	 * This method augments a rule pattern with negative invocations to deal with 0..n multiplicities.
	 * For every created edge in the pattern that has a 0..n multiplicity, a negative invocation
	 * is added which ensures that the multiplicity is not violated by applying the rule.
	 * 
	 * @param patterns The collection of all patterns for the current rule. 
	 * 		  The patterns created for the negative invocations are added here.
	 * @param pattern The pattern to augment with negative invocations.
	 */    
	private void addPatternInvocationsForMultiplicityConstraints(Collection<IbexPattern> patterns, RulePartPattern pattern) {
		TGGRule rule = pattern.getRule();
		TGGRule flattenedRule = flattenedTgg.getRules().stream()
				   .filter(r -> r.getName().equals(rule.getName()))
				   .findAny()
				   .get();
        HashMap<TGGRuleNode, HashSet<EReference>> sourceToProcessedEdgeTypes = new HashMap<TGGRuleNode, HashSet<EReference>>();

        // collect edges that need a multiplicity NAC
        Collection<TGGRuleEdge> relevantEdges = flattenedRule.getEdges().stream()
        													   .filter(e -> e.getType().getUpperBound() > 0
        															   && e.getBindingType() == BindingType.CREATE
        															   && e.getSrcNode().getBindingType() == BindingType.CONTEXT)
        													   .collect(Collectors.toList());

        for (TGGRuleEdge e : relevantEdges) {
			TGGRuleNode src = e.getSrcNode();
			
			// skip this edge if another edge of same type and with same source has already been processed
			Collection<EReference> processedEdgeTypes = sourceToProcessedEdgeTypes.get(src);
			if (processedEdgeTypes != null && processedEdgeTypes.contains(e.getType())) {
				continue;
			}
			
			// add edge to processed edges for its type and source node
			if (sourceToProcessedEdgeTypes.get(src) == null) {
				sourceToProcessedEdgeTypes.put(src, new HashSet<EReference>());
			}
			sourceToProcessedEdgeTypes.get(src).add(e.getType());
			
			// calculate number of create-edges with the same type coming from this source node
			long similarEdgesCount = flattenedRule.getEdges().stream()
															 .filter(edge -> edge.getType() == e.getType()
															 				&& edge.getSrcNode() == src
															 				&& edge.getBindingType() == BindingType.CREATE)
															 .count();

			Collection<TGGRuleElement> signatureElements = new ArrayList<TGGRuleElement>();
			Collection<TGGRuleElement> bodyElements = new ArrayList<TGGRuleElement>();

			// create/add elements to the pattern
			signatureElements.add(src);

			for (int i = 1; i <= e.getType().getUpperBound()+1-similarEdgesCount; i++) {
				TGGRuleNode trg = EcoreUtil.copy(e.getTrgNode());
				TGGRuleEdge edge = EcoreUtil.copy(e);
				
				trg.setName(trg.getName()+i);
				edge.setSrcNode(src);
				edge.setTrgNode(trg);
				
				bodyElements.add(trg);
				bodyElements.add(edge);
			}

			// create pattern and invocation
			ConstraintPattern constraint = new ConstraintPattern(rule, signatureElements, bodyElements, e.getSrcNode().getName()
																										+ "_"
																										+ e.getType().getName()
																										+ "Edge"
																										+ "_Multiplicity");
			patterns.add(constraint);
			pattern.addTGGNegativeInvocation(constraint);
        }
	}

	/**
	 * This method augments a rule pattern with negative invocations to deal with containment references.
	 * For every created containment edge in the pattern with a context node as target, a negative invocation
	 * is added which ensures that the target node is not already contained in another reference.
	 * 
	 * @param patterns The collection of all patterns for the current rule. 
	 * 		  The patterns created for the negative invocations are added here.
	 * @param pattern The pattern to augment with negative invocations.
	 */
	private void addPatternInvocationsForContainmentReferenceConstraints(Collection<IbexPattern> patterns, RulePartPattern pattern) {
		TGGRule rule = pattern.getRule();
		TGGRule flattenedRule = flattenedTgg.getRules().stream()
													   .filter(r -> r.getName().equals(rule.getName()))
													   .findAny()
													   .get();

        // collect edges that need a multiplicity NAC
        Collection<TGGRuleEdge> relevantEdges = flattenedRule.getEdges().stream()
        													   .filter(e -> e.getType().isContainment()
        															   && e.getBindingType() == BindingType.CREATE
        															   && e.getTrgNode().getBindingType() == BindingType.CONTEXT)
        													   .collect(Collectors.toList());

        for (TGGRuleEdge e : relevantEdges) {
			TGGRuleNode trg = e.getTrgNode();
			
			Collection<TGGRuleElement> signatureElements = new ArrayList<TGGRuleElement>();
			Collection<TGGRuleElement> bodyElements = new ArrayList<TGGRuleElement>();

			// create/add elements to the pattern
			TGGRuleNode src = EcoreUtil.copy(e.getSrcNode());
			TGGRuleEdge edge = EcoreUtil.copy(e);
			
			edge.setSrcNode(src);
			edge.setTrgNode(trg);
			
			bodyElements.add(src);
			bodyElements.add(edge);
			signatureElements.add(trg);

			// create pattern and invocation
			ConstraintPattern constraint = new ConstraintPattern(rule, signatureElements, bodyElements, e.getType().getName()
																										+ "Edge_"
																										+ e.getTrgNode().getName()
																										+ "_Containment");
			patterns.add(constraint);
			pattern.addTGGNegativeInvocation(constraint);
        }
	}

}
