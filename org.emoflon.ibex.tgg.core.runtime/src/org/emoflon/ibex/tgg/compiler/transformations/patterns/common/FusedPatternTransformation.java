package org.emoflon.ibex.tgg.compiler.transformations.patterns.common;

import org.emoflon.ibex.tgg.compiler.transformations.patterns.ContextPatternTransformation;
import org.emoflon.ibex.tgg.operational.defaults.IbexOptions;
import org.emoflon.ibex.tgg.util.MAUtil;

import IBeXLanguage.IBeXContextPattern;
import language.TGGComplementRule;
import language.TGGRule;

public abstract class FusedPatternTransformation extends OperationalPatternTransformation {
	protected TGGComplementRule crule;
	protected OperationalPatternTransformation kernelTransformer;
	protected OperationalPatternTransformation complementTransformer;

	public FusedPatternTransformation(ContextPatternTransformation parent, IbexOptions options, TGGRule rule) {
		super(parent, options, rule);
		crule = (TGGComplementRule) rule;
		createTransformers();
	}
	
	protected abstract void createTransformers();

	@Override
	protected String getPatternName() {
		return MAUtil.setFusedName(crule.getName(), crule.getKernel().getName());
	}
	
	@Override
	protected void handleComplementRules(IBeXContextPattern ibexPattern) {
		// Nothing
	}

	@Override
	protected void transformNodes(IBeXContextPattern ibexPattern) {
		kernelTransformer.transformNodes(ibexPattern);
		complementTransformer.transformNodes(ibexPattern);
	}

	@Override
	protected void transformEdges(IBeXContextPattern ibexPattern) {
		kernelTransformer.transformEdges(ibexPattern);
		complementTransformer.transformEdges(ibexPattern);
	}

	@Override
	protected void transformNACs(IBeXContextPattern ibexPattern) {
		kernelTransformer.transformNACs(ibexPattern);
		complementTransformer.transformNACs(ibexPattern);
	}
}
