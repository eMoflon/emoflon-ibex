package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.repair.util.TGGFilterUtil;
import org.emoflon.ibex.tgg.operational.strategies.PropagatingOperationalStrategy;

import language.BindingType;
import language.DomainType;

public class SRCShortcutRule extends OperationalShortcutRule {

	public SRCShortcutRule(PropagatingOperationalStrategy strategy, ShortcutRule scRule, FilterNACAnalysis filterNACAnalysis) {
		super(strategy, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(scRule.getReplacingRule(), DomainType.SRC);
		
		transformEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);
		
		transformNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		
		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.SRC, BindingType.DELETE));
		
		// delete nodes and edges in corr and trg domains
		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.CORR));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.CORR));
		
		removeEdges(TGGFilterUtil.filterEdges(scRule.getEdges(), DomainType.TRG));
		removeNodes(TGGFilterUtil.filterNodes(scRule.getNodes(), DomainType.TRG));
	}

	@Override
	public PatternType getType() {
		return PatternType.SRC;
	}

}
