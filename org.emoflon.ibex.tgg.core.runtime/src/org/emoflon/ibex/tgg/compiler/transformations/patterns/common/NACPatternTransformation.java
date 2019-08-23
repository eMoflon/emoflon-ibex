package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import java.util.List;


import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getNACPatternName;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACCandidate;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import IBeXLanguage.IBeXContextPattern;

import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

//usability-team 

public class NACPatternTransformation extends OperationalPatternTransformation{
	private NAC nac;
	
	public NACPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, NAC nac) {
		super(parent, options, rule);
		this.nac = nac;
	}
	
	protected String getPatternName() {
	        return getNACPatternName(nac.getName());
	    }
  
    protected void transformNodes(IBeXContextPattern ibexPattern) {
	        List<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperator(nac, BindingType.CONTEXT);
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(nac, BindingType.NEGATIVE, DomainType.SRC));
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(nac, BindingType.NEGATIVE, DomainType.TRG));
	        //nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.CORR));
	        
	        for (final TGGRuleNode node : nodes) {
	            parent.transformNode(ibexPattern, node);
	        }

	        // Transform in-node attributes
	        for (final TGGRuleNode node : nodes) {
	            parent.transformInNodeAttributeConditions(ibexPattern, node);
	        }
	        
	    }

	protected void transformEdges(IBeXContextPattern ibexPattern) {
	        List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.CONTEXT);
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.SRC));
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.TRG));
	        //edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.CORR));
	        
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

	    
//    protected void transformNACs(IBeXContextPattern ibexPattern) {
//    	for (NAC nac : rule.getNacs()) {
//			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC)) 
//				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
//    	}
//    	
//    	for (NAC nac : rule.getNacs()) {
//			if (TGGModelUtils.isOfDomain(nac, DomainType.TRG)) 
//				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
//    	}
//	        
//	    }
    
//    protected void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.CORR);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.CORR);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.SRC);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.TRG);
//		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.CORR);
//	}
    


}