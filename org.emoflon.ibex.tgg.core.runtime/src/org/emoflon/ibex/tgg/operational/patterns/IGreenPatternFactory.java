package org.emoflon.ibex.tgg.operational.patterns;

import java.util.Collection;
import java.util.List;

import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.operational.strategies.OperationalStrategy;

import language.TGGAttributeConstraint;
import language.TGGAttributeConstraintLibrary;
import language.TGGParamValue;
import language.TGGRuleCorr;
import language.TGGRuleEdge;
import language.TGGRuleNode;

public interface IGreenPatternFactory {

	public IGreenPattern create(String patternName);
	
	public IGreenPattern createGreenPattern(Class<? extends IGreenPattern> c);

	public IbexOptions getOptions();
	
	public OperationalStrategy getStrategy();

	public Collection<TGGRuleNode> getGreenSrcNodesInRule();
	
	public Collection<TGGRuleNode> getGreenTrgNodesInRule();
	
	public Collection<TGGRuleCorr> getGreenCorrNodesInRule();
	
	public Collection<TGGRuleEdge> getGreenCorrEdgesInRule();
	
	public Collection<TGGRuleEdge> getGreenSrcEdgesInRule();
	
	public Collection<TGGRuleEdge> getGreenTrgEdgesInRule();
	
	public Collection<TGGRuleNode> getBlackSrcNodesInRule();

	public Collection<TGGRuleNode> getBlackTrgNodesInRule();

	public Collection<TGGRuleCorr> getBlackCorrNodesInRule();

	public Collection<TGGRuleEdge> getBlackSrcEdgesInRule();

	Collection<TGGRuleEdge> getBlackTrgEdgesInRule();
	
	public boolean isAxiom();
	
	public boolean isComplementRule();

	public List<TGGAttributeConstraint> getAttributeConstraints();

	public List<TGGParamValue> getAttributeCSPVariables();
	
	public TGGAttributeConstraintLibrary getAttributeLibrary();

}
