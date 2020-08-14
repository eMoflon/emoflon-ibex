package org.emoflon.ibex.tgg.compiler.transformations.patterns.fwd;

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
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.TGGModelUtils;

import language.DomainType;
import language.NAC;
import language.TGGRule;

public class FWD_GREENCORRPatternTransformation {
	
	TGGRule rule;
	ContextPatternTransformation parent;
	IbexOptions options;
	FilterNACAnalysis filterNACAnalysis;
	
	public FWD_GREENCORRPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule, FilterNACAnalysis filterNACAnalysis) {
//		super(parent, options, rule, filterNACAnalysis);
		this.rule = rule;
		this.parent = parent;
		this.options = options;
		this.filterNACAnalysis = filterNACAnalysis;
	}
	
	protected String getPatternName() {
		return TGGPatternUtil.generateFWD_GREENCORRBlackPatternName(rule.getName());
	}
	
	public IBeXContextPattern transform() {
		if(options.invocation.usePatternInvocation()) {
			IBeXContextPattern ibexPattern = IBeXPatternModelFactory.eINSTANCE.createIBeXContextPattern();
			ibexPattern.setName(getPatternName());
			IBeXContextPattern tmp = parent.getPattern(rule.getName() + PatternSuffixes.GREENCORR);
			if(tmp != null) {
				createInvocation(ibexPattern, tmp);
			}
			for(int i = 1; ; i++) {
				tmp = parent.getPattern(rule.getName() + "_" + i + PatternSuffixes.GREENCORR);
				if(tmp == null)
					break;
				createInvocation(ibexPattern, tmp);
			}
			tmp = parent.getPattern(TGGPatternUtil.generateFWDBlackPatternName(rule.getName()));
			if(tmp != null)
				createInvocation(ibexPattern, tmp);
//				tmp = parent.createFWDPattern(rule);
//			createInvocation(ibexPattern, tmp);
			return ibexPattern;
		}
		else return new FWDPatternTransformation(parent, options, rule, filterNACAnalysis).transform();
	}
	
	protected void createInvocation(IBeXContextPattern invoker, IBeXContextPattern invokee) {
		IBeXPatternInvocation invocation = IBeXPatternModelFactory.eINSTANCE.createIBeXPatternInvocation();
		invocation.setPositive(true);

		// Creating mapping for invocation: missing signature nodes of the invoked
		// pattern are added as local nodes to the invoking pattern
		for (IBeXNode node : invokee.getSignatureNodes()) {
			Optional<IBeXNode> src = IBeXPatternUtils.findIBeXNodeWithName(invoker, node.getName());

			if (src.isPresent())
				invocation.getMapping().put(src.get(), node);
			else {
				IBeXNode newSignatureNode = IBeXPatternModelFactory.eINSTANCE.createIBeXNode();
				newSignatureNode.setName(node.getName());
				newSignatureNode.setType(node.getType());
				invoker.getSignatureNodes().add(newSignatureNode);

				invocation.getMapping().put(newSignatureNode, node);
			}
		}

		invocation.setInvokedPattern(invokee);
		invoker.getInvocations().add(invocation);
	}
	
}
