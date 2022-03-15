package org.emoflon.ibex.tgg.operational.repair.shortcut.rule;

import org.emoflon.ibex.tgg.compiler.patterns.ACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternType;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGFilterUtil;

import language.BindingType;
import language.DomainType;

public class SRCShortcutRule extends OperationalShortcutRule {

	public SRCShortcutRule(IbexOptions options, ShortcutRule scRule, ACAnalysis filterNACAnalysis) {
		super(options, scRule, filterNACAnalysis);
	}

	@Override
	protected void operationalize() {
		createFilterNacs(opScRule.getReplacingRule(), DomainType.SRC);
		
		transformEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		transformInterfaceEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.DELETE), BindingType.NEGATIVE);
		
		transformNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.SRC, BindingType.CREATE), BindingType.CONTEXT);
		
		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.SRC, BindingType.DELETE));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.SRC, BindingType.DELETE));
		
		// delete nodes and edges in corr and trg domains
		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.CORR));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.CORR));
		
		removeEdges(TGGFilterUtil.filterEdges(opScRule.getEdges(), DomainType.TRG));
		removeNodes(TGGFilterUtil.filterNodes(opScRule.getNodes(), DomainType.TRG));
	}

	@Override
	public PatternType getType() {
		return PatternType.SRC;
	}

}
