package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperator;
import static org.emoflon.ibex.tgg.util.TGGModelUtils.getNodesByOperatorAndDomain;

import java.util.List;
import java.util.Optional;

import org.emoflon.ibex.common.patterns.IBeXPatternUtils;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXContextPattern;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternInvocation;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXPatternModelFactory;
import org.emoflon.ibex.tgg.compiler.patterns.FilterNACAnalysis;
import org.emoflon.ibex.tgg.compiler.patterns.PatternSuffixes;
import org.emoflon.ibex.tgg.compiler.patterns.TGGPatternUtil;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.CONTEXTPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.GREENCORRPatternTransformation;
import org.emoflon.ibex.tgg.compiler.transformations.patterns.inv.SRCPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.BindingType;
import language.DomainType;
import language.NAC;
import language.TGGRule;
import language.TGGRuleNode;

public class FWDPatternTransformation extends FWD_OPTPatternTransformation {
	public FWDPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
		super(parent, options, rule, filterNACAnalysis);
	}

	@Override
	protected String getPatternName() {
		return TGGPatternUtil.generateFWDBlackPatternName(rule.getName());
	}
	
	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		List<TGGRuleNode> contextNodes = getNodesByOperator(rule, BindingType.CONTEXT);
		contextNodes.addAll(getNodesByOperatorAndDomain(rule, BindingType.CREATE, DomainType.SRC));
		
		for (final TGGRuleNode node : contextNodes)
			parent.transformNode(ibexPattern, node);

		// Transform attributes.
		for (final TGGRuleNode node : contextNodes)
			parent.transformInNodeAttributeConditions(ibexPattern, node);
	}
	
	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		super.transformNACs(ibexPattern);

		// Output Domain User NACs
		for (NAC nac : rule.getNacs()) {
			if (TGGModelUtils.isOfDomain(nac, DomainType.TRG))
				parent.addContextPattern(parent.transformNac(rule, nac, ibexPattern), nac);
		}
	}
	
	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		if(options.invocation.usePatternInvocation()) {
			
			IBeXContextPattern tmp = parent.getPattern(rule.getName() + PatternSuffixes.CONTEXT);
			if(tmp != null) {
				parent.createInvocation(ibexPattern, tmp);
			}
			for(int i = 1; ; i++) {
				tmp = parent.getPattern(rule.getName() + "_" + i + PatternSuffixes.CONTEXT);
				if(tmp == null)
					break;
				parent.createInvocation(ibexPattern, tmp);
			}
			tmp = parent.getPattern(rule.getName() + PatternSuffixes.SRC);
			if(tmp != null) {
				parent.createInvocation(ibexPattern, tmp);
			}
			//At the moment there can be only one SRC/TRG Pattern
//			for(int i = 1; ; i++) {
//				tmp = parent.getPattern(rule.getName() + PatternSuffixes.SRC + "_" + i);
//				if(tmp == null)
//					break;
//				parent.createInvocation(ibexPattern, tmp);
//			}
		}
		else super.transformEdges(ibexPattern);
	}
}
