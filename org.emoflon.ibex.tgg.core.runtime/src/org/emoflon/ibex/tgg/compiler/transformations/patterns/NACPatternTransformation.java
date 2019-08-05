package org.emoflon.ibex.tgg.compiler.transformations.patterns;

import java.util.List;


import static org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil.getNACPatternName;


import org.emoflon.ibex.tgg.core.util.TGGModelUtils;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import IBeXLanguage.IBeXContextPattern;

import IBeXLanguage.IBeXNode;
import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleEdge;
import language.TGGRuleNode;

//usability-team 

public class NACPatternTransformation extends ContextPatternTransformation{

	public NACPatternTransformation(IbexOptions options, OperationalStrategy strategy) {
		super(options, strategy);
		// TODO Auto-generated constructor stub
	}
	
	protected ContextPatternTransformation parent;
	protected IbexOptions options;
	protected TGGRule rule;
	
	
	protected String getPatternName() {
	        return getNACPatternName(rule.getName());
	    }
  
    protected void transformNodes(IBeXContextPattern ibexPattern) {
	        // TODO Auto-generated method stub
	        List<TGGRuleNode> nodes = TGGModelUtils.getNodesByOperator(rule, BindingType.NEGATIVE);
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.SRC));
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.TRG));
	        //nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.CORR));
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.SRC));
	        nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.TRG));
	        //nodes.addAll(TGGModelUtils.getNodesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.CORR));
	        
	        for (final TGGRuleNode node : nodes) {
	            parent.transformNode(ibexPattern, node);
	        }

	        // Transform in-node attributes
	        for (final TGGRuleNode node : nodes) {
	            parent.transformInNodeAttributeConditions(ibexPattern, node);
	        }
	        
	    }

	protected void transformEdges(IBeXContextPattern ibexPattern) {
	        // TODO Auto-generated method stub
	        List<TGGRuleEdge> edges = TGGModelUtils.getEdgesByOperator(rule, BindingType.NEGATIVE);
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.SRC));
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.TRG));
	        //edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.NEGATIVE, DomainType.CORR));
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.SRC));
	        edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.TRG));
	        //edges.addAll(TGGModelUtils.getEdgesByOperatorAndDomain(rule, BindingType.CONTEXT, DomainType.CORR));
	        
	        for (TGGRuleEdge edge : edges)
	            parent.transformEdge(edges, edge, ibexPattern);
	    }

	    
    protected void transformNACs(IBeXContextPattern ibexPattern) {
	        // TODO Auto-generated method stub
    	for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.SRC)) 
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
    	}
    	
    	for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.TRG)) 
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
    	}
	        
	    }
    
    protected void connectProtocolNode(IBeXContextPattern ibexPattern, IBeXNode protocolNode, TGGRule rule) {
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CONTEXT, DomainType.CORR);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.CREATE, DomainType.CORR);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.SRC);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.TRG);
		connectProtocolNode(ibexPattern, rule, protocolNode, BindingType.NEGATIVE, DomainType.CORR);
	}
    


}