package org.emoflon.ibex.gt.engine;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.common.engine.MatchFilter;
import org.emoflon.ibex.common.engine.PatternMatchingEngine;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTModel;

public abstract class IBeXGTPatternMatcher extends PatternMatchingEngine<GTModel> {

	public IBeXGTPatternMatcher(GTModel ibexModel, ResourceSet model) {
		super(ibexModel, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MatchFilter<GTModel> createMatchFilter() {
		// TODO Auto-generated method stub
		return null;
	}

}
